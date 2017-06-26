package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.MyHolidayNoteAdapter;
import iok.la.com.medicaltreatmentapplication.bean.HolidayDataBean;
import iok.la.com.medicaltreatmentapplication.bean.HolidayDepartment;
import iok.la.com.medicaltreatmentapplication.bean.PostScheduleData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.RefreshExpandableListView;
import okhttp3.Call;

public class MyHolidayActivity extends BaseActivity {

    @BindView(R.id.turn_last)
    ImageButton turnLast;
    @BindView(R.id.add_holiday)
    ImageButton addHoliday;
    @BindView(R.id.myholidays)
    RefreshExpandableListView myholidays;
    View headView;
    private LinearLayout office_name,start_date,end_date;
    private String DepartmentId = "";
    private String startDate = "";
    private String endDate = "";
    private Calendar calendar;
    private TextView officename,startdate,enddate;
    private PopupWindow office_popupWindow;
    private Button querydata;
    List<HolidayDepartment.ResultsBean.ExcludesBean> excludess;
    int page = 1;
    MyHolidayNoteAdapter noteAdapter;
    Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_my_holiday;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_my_holiday;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(MyHolidayActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        calendar = Calendar.getInstance();
        headView = LayoutInflater.from(MyHolidayActivity.this).inflate(R.layout.holiday_note_headview,null);
        office_name = (LinearLayout) headView.findViewById(R.id.office_name);
        start_date = (LinearLayout) headView.findViewById(R.id.start_date);
        end_date = (LinearLayout) headView.findViewById(R.id.end_date);
        officename = (TextView) headView.findViewById(R.id.officename);
        startdate = (TextView) headView.findViewById(R.id.startdate);
        enddate = (TextView) headView.findViewById(R.id.enddate);
        querydata = (Button) headView.findViewById(R.id.querydata);
        myholidays.addHeaderView(headView);
    }

    @Override
    public void initData() {
        loadData();
        initLinister();
    }
    private void initLinister() {
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        String startTime = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        startdate.setText(startTime);
                        startDate = startTime;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        String endTime =year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        enddate.setText(endTime);
                        endDate = endTime;
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        office_name.setOnClickListener(new View.OnClickListener() {
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
                office_popupWindow.showAsDropDown(officename);
            }
        });
        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                page = 1;
                loadDataPost();
            }
        });
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/Vocations?Token="+PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToastSafe(MyHolidayActivity.this,"请检查网络1");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final HolidayDepartment holidayDepartment = gson.fromJson(response,HolidayDepartment.class);
                        if (holidayDepartment.isStatus()){
                            proDialog.dismiss();
                            initPopupWindow(officename,holidayDepartment);
                            loadDataPost();
                        }else {
                            ToastUtils.showToastSafe(MyHolidayActivity.this,"参数错误");
                        }
                        addHoliday.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MyHolidayActivity.this, HolidayAddActivity.class);
                                HolidayDataBean dataBean = new HolidayDataBean();
                                List<HolidayDepartment.ResultsBean.DepartsAddBean> departsAdd = holidayDepartment.getResults().getDepartsAdd();
                                dataBean.setDepartsAdd(departsAdd);
                                List<HolidayDepartment.ResultsBean.EetBean> eetBeanList = holidayDepartment.getResults().getEet();
                                dataBean.setEetBeanList(eetBeanList);
                                dataBean.setId(holidayDepartment.getResults().getEmp().getId());
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data",dataBean);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }
    private void loadDataPost() {
        OkHttpUtils
                .post()
                .url(Content.BASE_URL+"AppPersonelCenter/Vocations")
                .addParams("DepartmentId",DepartmentId)
                .addParams("start",startDate)
                .addParams("end",endDate)
                .addParams("page",page+"")
                .addParams("Token", PrefUtils.getString(MyHolidayActivity.this,"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        proDialog.dismiss();
                        ToastUtils.showToastSafe(MyHolidayActivity.this,"请检查网络2");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        HolidayDepartment holidayDepartment = gson.fromJson(response,HolidayDepartment.class);
                        if (holidayDepartment.isStatus()){
                            proDialog.dismiss();
                            excludess = holidayDepartment.getResults().getExcludes();
                            final List<HolidayDepartment.ResultsBean.ExcludesBean> excludes = holidayDepartment.getResults().getExcludes();
                            if (!excludess.toString().trim().equals("")){
                                noteAdapter = new MyHolidayNoteAdapter(MyHolidayActivity.this,excludess);
                                myholidays.setAdapter(noteAdapter);
                                myholidays.expandGroup(0);
                                noteAdapter.notifyDataSetChanged();
                                //myholidays.onRefreshComplete();
                                //刷新
                                myholidays.setRefreshListener(new RefreshExpandableListView.OnRefreshListener() {
                                    @Override
                                    public void onRefresh() {

                                    }

                                    @Override
                                    public void onLoadMore() {
                                        page++;
                                        loadUpdateData(excludes);
                                    }
                                });
                            }else {
                                proDialog.dismiss();
                                myholidays.onRefreshComplete();
                                ToastUtils.showToastSafe(MyHolidayActivity.this,"暂无更多数据");
                                page -- ;
                            }

                        }else {
                            proDialog.dismiss();
                            myholidays.onRefreshComplete();
                            page -- ;
                            ToastUtils.showToastSafe(MyHolidayActivity.this,"参数错误");
                        }
                    }
                });
    }

    private void loadUpdateData(List<HolidayDepartment.ResultsBean.ExcludesBean> excludes) {
        OkHttpUtils
                .post()
                .url(Content.BASE_URL+"AppPersonelCenter/Vocations")
                .addParams("DepartmentId",DepartmentId)
                .addParams("start",startDate)
                .addParams("end",endDate)
                .addParams("page",page+"")
                .addParams("Token", PrefUtils.getString(MyHolidayActivity.this,"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        proDialog.dismiss();
                        ToastUtils.showToastSafe(MyHolidayActivity.this,"请检查网络2");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        HolidayDepartment holidayDepartment = gson.fromJson(response,HolidayDepartment.class);
                        if (holidayDepartment.isStatus()){
                            proDialog.dismiss();
                            excludess = holidayDepartment.getResults().getExcludes();
                            final List<HolidayDepartment.ResultsBean.ExcludesBean> excludes = holidayDepartment.getResults().getExcludes();
                                if (excludes != null && excludes.toString() !="[]"&&page != 1){
                                    for (int i = 0; i < excludes.size(); i++) {
                                        excludess.add(excludes.get(i));
                                    }
                                    noteAdapter.notifyDataSetChanged();
                                    myholidays.onRefreshComplete();
                                } else {
                                myholidays.onRefreshComplete();
                                    proDialog.dismiss();
                                    ToastUtils.showToastSafe(MyHolidayActivity.this,"暂无更多数据");
                                page -- ;
                            }

                            myholidays.setRefreshListener(new RefreshExpandableListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {

                                }

                                @Override
                                public void onLoadMore() {
                                    page++;
                                    loadUpdateData(excludes);
                                }
                            });
                        }else {
                            proDialog.dismiss();
                            myholidays.onRefreshComplete();
                            page -- ;
                            ToastUtils.showToastSafe(MyHolidayActivity.this,"参数错误");
                        }
                    }
                });
    }

    @OnClick({R.id.turn_last, R.id.add_holiday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turn_last:
                finish();
                break;
//            case R.id.add_holiday:
//                Intent intent = new Intent(MyHolidayActivity.this, HolidayAddActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    private void initPopupWindow(final TextView text, final HolidayDepartment data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getDpts().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getDpts().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getDpts().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(MyHolidayActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(MyHolidayActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                DepartmentId = list.get(position).get("id");
                office_popupWindow.dismiss();

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proDialog = new Dialog(MyHolidayActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        page = 1;
        loadData();
        initLinister();
    }
}
