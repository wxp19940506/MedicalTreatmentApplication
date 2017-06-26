package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.MyStudentScheduleListAdapter;
import iok.la.com.medicaltreatmentapplication.bean.MyScheduleDate;
import iok.la.com.medicaltreatmentapplication.bean.MyStudentScheduleListBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import okhttp3.Call;

public class StudentScheduleActivity extends BaseActivity {
    @BindView(R.id.student_schedule_list)
    ListView studentScheduleList;
    private int employeeid;
    private View head_View;
    private Context context;
    private String name;
    private TextView student_name,tomonth,last_month,date,next_month;
    String selectDate;
    private long currentTime;
    Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_student_schedule;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_student_schedule;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(StudentScheduleActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy-MM-dd");
        currentTime = System.currentTimeMillis();
        Date curDate = new  Date();//获取当前时间
        selectDate  =   formatter.format(curDate);
        context = this;
        employeeid = getIntent().getIntExtra("employeeid", 1209);
        name = getIntent().getStringExtra("name");
        head_View = LayoutInflater.from(context).inflate(R.layout.my_student_schedule_headview, null);
        student_name = (TextView) head_View.findViewById(R.id.student_name);
        tomonth = (TextView) head_View.findViewById(R.id.tomonth);
        last_month = (TextView) head_View.findViewById(R.id.last_month);
        date = (TextView) head_View.findViewById(R.id.date);
        next_month = (TextView) head_View.findViewById(R.id.next_month);
        studentScheduleList.addHeaderView(head_View);
        student_name.setText(name);
    }

    @Override
    public void initData() {
        loadData();
        initLinister();
    }

    private void initLinister() {
        tomonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy-MM-dd");
                currentTime = System.currentTimeMillis();
                Date curDate = new  Date();//获取当前时间
                selectDate  =   formatter.format(curDate);
                loadData();
            }
        });
        last_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.LastMonthl(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.NextMonthl(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadData();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL + "AppPersonelCenter/MyStudentShifts?employeeid=" + employeeid +"&date="+selectDate+ "&Token=" + PrefUtils.getString(getBaseContext(), "token", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyStudentScheduleListBean studentScheduleListBean= gson.fromJson(response,MyStudentScheduleListBean.class);
                        if (studentScheduleListBean.isStatus()){
                            proDialog.dismiss();
                            date.setText(studentScheduleListBean.getResults().getDatetable().getTitle());
                            student_name.setText(studentScheduleListBean.getResults().getEmpName());
                            List<MyStudentScheduleListBean.ResultsBean.DatetableBean.RowsBean.CellsBean> items = studentScheduleListBean.getResults().getDatetable().getRows().get(0).getCells();
                            List datal = new ArrayList();
                            for (int i = 0; i <items.get(0).getDayOfWeek() ; i++) {
                                MyStudentScheduleListBean.ResultsBean.DatetableBean.RowsBean.CellsBean bean = null;
                                datal.add(bean);
                            }
                            for (int i = 0; i < items.size(); i++) {
                                datal.add(items.get(i));
                            }
                            for (int i = 0; i <6 - items.get(items.size()-1).getDayOfWeek() ; i++) {
                                MyStudentScheduleListBean.ResultsBean.DatetableBean.RowsBean.CellsBean bean = null;
                                datal.add(bean);
                            }
                            List<List<MyStudentScheduleListBean.ResultsBean.DatetableBean.RowsBean.CellsBean>> dataList = new ArrayList();
                            List dataItem = null;
                            for (int i = 0; i < datal.size(); i++) {
                                if (i % 7 == 0){
                                    dataItem = new ArrayList();
                                }
                                if (dataItem != null){
                                    dataItem.add(datal.get(i));
                                }
                                if (i % 7 == 6){
                                    dataList.add(dataItem);
                                }
                            }
                            MyStudentScheduleListAdapter adapter = new MyStudentScheduleListAdapter(StudentScheduleActivity.this,dataList);
                            studentScheduleList.setAdapter(adapter);
                        }
                    }
                });
    }

    @OnClick(R.id.turn_last)
    public void onClick() {
        finish();
    }
}
