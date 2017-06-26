package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleDateListAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleNameListAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfoName;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfoTime;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.util.Content;
import okhttp3.Call;

public class OfficeScheduleDetailActivity extends BaseActivity {
    @BindView(R.id.schedule_date_list)
    ExpandableListView scheduleDateList;
    private List<List<Map<String, String>>> childs ;
    private List<String> groups;
    private View headView;
    @BindView(R.id.turn_last)
    ImageButton turn_last;
    private int currentDepartmentID,currentShiftTypeID;
    private String mydate,officeName,shiftName,cuurrentDate,todaydate;
    private TextView timerange,office_name,shift_name,now,last_week,next_week;
    private RadioGroup dateOrname_radiogroup;
    private RadioButton use_date,use_name;
    String url_date= Content.BASE_URL+"AppShifts/ShiftPartialInDate";
    String url_name= Content.BASE_URL+"AppShifts/ShiftPartialInName";
    Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_office_schedule_detail;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_office_schedule_detail;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(OfficeScheduleDetailActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        childs= new ArrayList<>();
        groups = new ArrayList<>();
        scheduleDateList.setGroupIndicator(null);
        headView = LayoutInflater.from(OfficeScheduleDetailActivity.this).inflate(R.layout.scheduledetail_headview,null);
        dateOrname_radiogroup = (RadioGroup) headView.findViewById(R.id.dateOrname_radiogroup);
        dateOrname_radiogroup = (RadioGroup) headView.findViewById(R.id.dateOrname_radiogroup);
        use_date = (RadioButton) headView.findViewById(R.id.use_date);
        use_name = (RadioButton) headView.findViewById(R.id.use_name);
        timerange = (TextView) headView.findViewById(R.id.timerange);
        office_name = (TextView) headView.findViewById(R.id.office_name);
        shift_name = (TextView) headView.findViewById(R.id.shift_name);
        now = (TextView) headView.findViewById(R.id.now);
        last_week = (TextView) headView.findViewById(R.id.last_week);
        next_week = (TextView) headView.findViewById(R.id.next_week);
        scheduleDateList.addHeaderView(headView);
        currentDepartmentID = getIntent().getIntExtra("currentDepartmentID",1053);
        currentShiftTypeID = getIntent().getIntExtra("currentShiftTypeID",17);
        mydate = getIntent().getStringExtra("currentTime");
        todaydate = getIntent().getStringExtra("currentTime");
        officeName = getIntent().getStringExtra("officeName");
        shiftName = getIntent().getStringExtra("shiftName");
        office_name.setText(officeName);
        shift_name.setText(shiftName);
    }

    @Override
    public void initData() {
        downloadInfoUseTime();
        initLinister();
    }

    private void downloadInfoUseTime() {
        buttonDownloadInfoUseTime(todaydate);
    }
    private void buttonDownloadInfoUseTime(String date) {
        OkHttpUtils
                .post()
                .url(url_date)
                .addParams("DepartmentID", currentDepartmentID+"")
                .addParams("ShiftTypeID", currentShiftTypeID+"")
                .addParams("Date", date)
                .addParams("Token", PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToastSafe(OfficeScheduleDetailActivity.this,"连接超时，请先连接网络！");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        if (response != null){
                            Gson gson = new Gson();
                            OfficeScheduleInfoTime InfoTime = gson.fromJson(response, OfficeScheduleInfoTime.class);
                            if (InfoTime.isStatus()){
                                proDialog.dismiss();
                                timerange.setText(InfoTime.getResults().getTitle());
                                ScheduleDateListAdapter adapter = new ScheduleDateListAdapter(OfficeScheduleDetailActivity.this,InfoTime.getResults().getModules(),officeName,currentDepartmentID,currentShiftTypeID);
                                scheduleDateList.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                scheduleDateList.expandGroup(0);
                            }
                        }
                    }
                });
    }

    private void initLinister() {
        dateOrname_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.use_date:
                        proDialog.show();
                        downloadInfoUseTime();//按时间查询数据请求
                        break;
                    case R.id.use_name://按名字查询数据请求
                        proDialog.show();
                        downloadInfoUseName();
                        break;
                }
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scheduleDateList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        scheduleDateList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    mydate = getIntent().getStringExtra("currentTime");
                    cuurrentDate = TimeOrLongUtil.LastWeeks(mydate);
                    downloadInfoUseTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        last_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    cuurrentDate = TimeOrLongUtil.LastWeeks(mydate);
                    mydate = cuurrentDate;
                    buttonDownloadInfoUseTime(cuurrentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        next_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    cuurrentDate = TimeOrLongUtil.NextWeeks(mydate);
                    mydate = cuurrentDate;
                    buttonDownloadInfoUseTime(cuurrentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void downloadInfoUseName() {
        OkHttpUtils
                .post()
                .url(url_name)
                .addParams("DepartmentID", currentDepartmentID+"")
                .addParams("ShiftTypeID", currentShiftTypeID+"")
                .addParams("Date", mydate)
                .addParams("Token", token+"")
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToastSafe(OfficeScheduleDetailActivity.this,"连接超时，请先连接网络！");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        OfficeScheduleInfoName InfoName = gson.fromJson(response, OfficeScheduleInfoName.class);
                        if (InfoName.isStatus()){
                            proDialog.dismiss();
                            timerange.setText(InfoName.getResults().getTitle());
                            ScheduleNameListAdapter adapter = new ScheduleNameListAdapter(OfficeScheduleDetailActivity.this,InfoName.getResults().getModules(),officeName,currentDepartmentID,currentShiftTypeID);
                            scheduleDateList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            scheduleDateList.expandGroup(0);
                        }
                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //dateOrname_radiogroup.check(R.id.use_date);
        proDialog = new Dialog(OfficeScheduleDetailActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();         if (use_date.isChecked()){
            downloadInfoUseTime();//按时间查询数据请求
        }
        if (use_name.isChecked()){
            downloadInfoUseName();//按姓名查询数据请求
        }

    }
}
