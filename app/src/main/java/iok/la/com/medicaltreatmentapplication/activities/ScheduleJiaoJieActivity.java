package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.android.datetimepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleJiaoJieNoteListAdapter;
import iok.la.com.medicaltreatmentapplication.bean.PostScheduleData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.RefreshExpandableListView;
import okhttp3.Call;

public class ScheduleJiaoJieActivity extends BaseActivity {
    private RefreshExpandableListView jioaban_notelist;
    private View headView;
    private EditText jiaoban_person;
    private TextView jiaoban_time,jiaoban_zhuangt;
    private Button query;
    private String transferDate,transferEmployee,atCode;
    int page = 1;
    private Calendar calendar;
    private PopupWindow office_popupWindow;
    private ImageButton add_jiaonote,turn_last;
    List<PostScheduleData.ResultsBean.TransfersBean> transfers;
    ScheduleJiaoJieNoteListAdapter noteListAdapter;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_jiao_jie;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_jiao_jie;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(ScheduleJiaoJieActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        add_jiaonote = (ImageButton) findViewById(R.id.add_jiaonote);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        calendar = Calendar.getInstance();
        headView = LayoutInflater.from(ScheduleJiaoJieActivity.this).inflate(R.layout.jiaoban_note_headview,null);
        jioaban_notelist = (RefreshExpandableListView) findViewById(R.id.jioaban_notelist);
        jiaoban_person = (EditText) headView.findViewById(R.id.jiaoban_person);
        jiaoban_time = (TextView) headView.findViewById(R.id.jiaoban_time);
        jiaoban_zhuangt = (TextView) headView.findViewById(R.id.jiaoban_zhuangt);
        query = (Button) headView.findViewById(R.id.query);
        jioaban_notelist.addHeaderView(headView);
    }

    @Override
    public void initData() {
        loadData();
        initLinister();
    }

    private void initLinister() {
        jiaoban_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        jiaoban_time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        add_jiaonote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleJiaoJieActivity.this,ScheduleAddJiaoJieActivity.class);
                startActivity(intent);
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                page = 1;
                if (jiaoban_time.getText().toString().trim().equals("交班时间")){
                    transferDate = "";
                }else {
                    transferDate = jiaoban_time.getText().toString().trim();
                }
                if (jiaoban_person.getText().toString().trim().equals("交班人")){
                    transferEmployee = "";
                }else {
                    transferEmployee = jiaoban_person.getText().toString().trim();
                }
                if (jiaoban_zhuangt.getText().toString().trim().equals("交班状态")){
                    atCode = "";
                }
                loadData();
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/TransfersIndex?page="+page+"&TransferDate="+transferDate+"&TransferEmployee"+transferEmployee+"&TransferStatusID="+atCode+"&Token="+PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final PostScheduleData data = gson.fromJson(response,PostScheduleData.class);
                        if (data.isStatus()){
                            proDialog.dismiss();
                            transfers = data.getResults().getTransfers();
                            initPopupWindow(jiaoban_zhuangt,data);
                            noteListAdapter = new ScheduleJiaoJieNoteListAdapter(ScheduleJiaoJieActivity.this,transfers);
                            jioaban_notelist.setAdapter(noteListAdapter);
                            if (data.getResults().getTransfers().size() !=0 && data.getResults().getTransfers() != null)
                                jioaban_notelist.expandGroup(0);
                            jioaban_notelist.setRefreshListener(new RefreshExpandableListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {

                                }

                                @Override
                                public void onLoadMore() {
                                    page ++;
                                    loadUpdateData();
                                }
                            });
                            jiaoban_zhuangt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    office_popupWindow.setFocusable(true);
                                    office_popupWindow.setOutsideTouchable(true);
                                    office_popupWindow.setTouchable(true);
                                    office_popupWindow.showAsDropDown(jiaoban_zhuangt);
                                    office_popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                                office_popupWindow.dismiss();
                                                return true;
                                            }
                                            return false;
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
    }

    private void loadUpdateData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/TransfersIndex?page="+page+"&TransferDate="+transferDate+"&TransferEmployee"+transferEmployee+"&TransferStatusID="+atCode+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("newBean",response);
                        Gson gson = new Gson();
                        final PostScheduleData data = gson.fromJson(response,PostScheduleData.class);
                        if (data.isStatus()){
                            proDialog.dismiss();
                            List<PostScheduleData.ResultsBean.TransfersBean> transfers1 = data.getResults().getTransfers();
                            if (transfers1 != null && transfers1.toString() !="[]"&&page != 1){
                                for (int i = 0; i < transfers1.size(); i++) {
                                    transfers.add(transfers1.get(i));
                                }
                                noteListAdapter.notifyDataSetChanged();
                                jioaban_notelist.onRefreshComplete();
                            }else {
                                page -- ;
                                jioaban_notelist.onRefreshComplete();
                                ToastUtils.showToastSafe(ScheduleJiaoJieActivity.this,"没有其他数据");
                            }

                            if (data.getResults().getTransfers().size() !=0 && data.getResults().getTransfers() != null)
                                jioaban_notelist.expandGroup(0);
                            jioaban_notelist.setRefreshListener(new RefreshExpandableListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {

                                }

                                @Override
                                public void onLoadMore() {
                                    page++;
                                    loadUpdateData();
                                }
                            });
                            initPopupWindow(jiaoban_zhuangt,data);
                            jiaoban_zhuangt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    office_popupWindow.setFocusable(true);
                                    office_popupWindow.setOutsideTouchable(true);
                                    office_popupWindow.setTouchable(true);
                                    office_popupWindow.showAsDropDown(jiaoban_zhuangt);
                                    office_popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                                office_popupWindow.dismiss();
                                                return true;
                                            }
                                            return false;
                                        }
                                    });
                                }
                            });
                        }else {
                            ToastUtils.showToastSafe(ScheduleJiaoJieActivity.this,"参数错误");
                        }

                    }
                });
    }


    private void initPopupWindow(final TextView text, final PostScheduleData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getStatus().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getStatus().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getStatus().get(i).getID()));
            list.add(map);
        }
        View view = LayoutInflater.from(ScheduleJiaoJieActivity.this).inflate(R.layout.office_list, null);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(ScheduleJiaoJieActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                atCode = list.get(position).get("id");
                if (office_popupWindow != null && office_popupWindow.isShowing()) {
                    office_popupWindow.dismiss();
                    office_popupWindow.setTouchable(false);
                    office_popupWindow.setFocusable(false);
                }

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }
}
