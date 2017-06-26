package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleNoteListAdapter;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.RefreshExpandableListView;
import okhttp3.Call;

public class ScheduleNoteDisPlayActivity extends BaseActivity {
    private RefreshExpandableListView tatolschenote;
    private ImageButton add_schedulel,turn_last;
    private LinearLayout office_select;
    private PopupWindow office_popupWindow;
    private TextView schedule_name;
    private String DepartmentID = "";
    int pager =1;
    int mFooterViewHeight;
    View mFooterView;
    ScheduleNoteListAdapter adapter;
    List<ScheduleNoteData.ResultsBean.RotasBean> rotaspager1;
    int departmentId;
    Dialog proDialog;

    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_note_dis_play;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_note_dis_play;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(ScheduleNoteDisPlayActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        tatolschenote = (RefreshExpandableListView) findViewById(R.id.tatolschenote);
        add_schedulel = (ImageButton) findViewById(R.id.add_schedule);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        View headView = LayoutInflater.from(ScheduleNoteDisPlayActivity.this).inflate(R.layout.displayschedule_headview,null);
        office_select = (LinearLayout) headView.findViewById(R.id.office_select);
        schedule_name = (TextView) headView.findViewById(R.id.schedule_name);
        tatolschenote.addHeaderView(headView);
    }

    @Override
    public void initData() {
        loadData();
        //loadUpdateData();
        initLinister();
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/Rotas?page="+pager+"&departmentId="+DepartmentID+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final ScheduleNoteData noteData= gson.fromJson(response,ScheduleNoteData.class);
                        if (noteData.isStatus()){
                            proDialog.dismiss();
                            initPopupWindow(noteData);
                            rotaspager1 = noteData.getResults().getRotas();
                            showSelectScheduleData(rotaspager1);
                            tatolschenote.setRefreshListener(new RefreshExpandableListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    //pager++;
                                }

                                @Override
                                public void onLoadMore() {
                                    pager++;
                                    loadUpdateData();
                                }
                            });
                        }else {
                            pager--;
                            tatolschenote.onRefreshComplete();
                            ToastUtils.showToastSafe(ScheduleNoteDisPlayActivity.this,"没有其他数据");
                        }

                    }
                });
    }
    private void loadUpdateData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/Rotas?page="+pager+"&departmentId="+DepartmentID+"&Token="+PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final ScheduleNoteData noteData= gson.fromJson(response,ScheduleNoteData.class);
                        if (noteData.isStatus()){
                            List<ScheduleNoteData.ResultsBean.RotasBean> rotas = noteData.getResults().getRotas();
                            if (rotas != null && rotas.toString() !="[]"&&pager != 1){
                                for (int i = 0; i <rotas.size() ; i++) {
                                    rotaspager1.add(rotas.get(i));
                                }
                                //showSelectScheduleData(rotas);
                                adapter.notifyDataSetChanged();
                                tatolschenote.onRefreshComplete();
                            }else {
                                pager -- ;
                                tatolschenote.onRefreshComplete();
                                ToastUtils.showToastSafe(ScheduleNoteDisPlayActivity.this,"没有其他数据");
                            }
                            tatolschenote.setRefreshListener(new RefreshExpandableListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                }

                                @Override
                                public void onLoadMore() {
                                    pager ++;
                                    loadUpdateData();
                                }
                            });
                        }else {
                            pager -- ;
                            tatolschenote.onRefreshComplete();
                            ToastUtils.showToastSafe(ScheduleNoteDisPlayActivity.this,"没有其他数据");
                        }
                    }
                });
    }

    private void showSelectScheduleData(List<ScheduleNoteData.ResultsBean.RotasBean> rotas) {
        if (!rotas.toString().equals("[]")){
            adapter = new ScheduleNoteListAdapter(ScheduleNoteDisPlayActivity.this,rotas);
            tatolschenote.setAdapter(adapter);
            if (rotas != null && rotas.size() >= 0)
                tatolschenote.expandGroup(0);
        }else {
            ToastUtils.showToastSafe(ScheduleNoteDisPlayActivity.this,"暂无数据");
            rotas.clear();
            adapter = new ScheduleNoteListAdapter(ScheduleNoteDisPlayActivity.this,rotas);
            tatolschenote.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void initLinister() {
        office_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                office_popupWindow.setFocusable(true);
                office_popupWindow.setOutsideTouchable(true);
                office_popupWindow.setTouchable(true);
                office_popupWindow .setBackgroundDrawable(new BitmapDrawable());
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
                office_popupWindow.showAsDropDown(office_select);
            }
        });
        add_schedulel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleNoteDisPlayActivity.this,ScheduleNoteActivity.class);
                startActivity(intent);
                finish();
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //选择科室的pop数据
    private void initPopupWindow(final ScheduleNoteData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getManageDepartments().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getManageDepartments().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getManageDepartments().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(ScheduleNoteDisPlayActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(ScheduleNoteDisPlayActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                proDialog.show();
                schedule_name.setText(list.get(position).get("name"));
                DepartmentID = list.get(position).get("id");
                pager = 1;
                loadData();
                office_popupWindow.dismiss();

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proDialog = new Dialog(ScheduleNoteDisPlayActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        loadData();
    }
}
