package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleXunJianAdapter;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleXunJian;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.RefreshExpandableListView;
import iok.la.com.medicaltreatmentapplication.widget.RefreshListView;
import okhttp3.Call;

public class ScheduleXunJianActivity extends BaseActivity {
    private ImageButton add_schedulexunjian;
    private RefreshListView xunjianlist;
    TextView schedule_name;
    String ymday;
    private Calendar calendar;
    int page = 1;
    List<ScheduleXunJian.ResultsBean.PatrolsBean> transfers;
    ScheduleXunJianAdapter adapter;
    String date = "";
    @BindView(R.id.turn_last)
    ImageButton turn_last;
    Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_xun_jian;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_xun_jian;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(ScheduleXunJianActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        calendar = Calendar.getInstance();
        add_schedulexunjian = (ImageButton) findViewById(R.id.add_schedulexunjian);
        xunjianlist = (RefreshListView) findViewById(R.id.xunjianlist);
        View headView = LayoutInflater.from(ScheduleXunJianActivity.this).inflate(R.layout.displayschedule_headview,null);
        schedule_name = (TextView) headView.findViewById(R.id.schedule_name);
        schedule_name.setText("请选择时间");
        xunjianlist.addHeaderView(headView);
    }

    @Override
    public void initData() {
        loadDataXunJian(date);
        initLinister();
    }

    private void loadDataXunJian(final String thisDate) {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/PatrolsIndex?page="+page+"&date="+thisDate+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ScheduleXunJian xunJian  = gson.fromJson(response, ScheduleXunJian.class);
                        if (xunJian.isStatus()){
                            proDialog.dismiss();
                            transfers = xunJian.getResults().getPatrols();
                            adapter = new ScheduleXunJianAdapter(ScheduleXunJianActivity.this,transfers);
                            xunjianlist.setAdapter(adapter);
                            xunjianlist.setRefreshListener(new RefreshListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                }

                                @Override
                                public void onLoadMore() {
                                    loadDataXunJianUpdate(date);
                                }
                            });
                        }
                    }
                });
    }

    private void loadDataXunJianUpdate(final String thisDate) {
        page ++;
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/PatrolsIndex"+"?page="+page+"&date="+thisDate+"&Token="+PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ScheduleXunJian xunJian  = gson.fromJson(response, ScheduleXunJian.class);
                        if (xunJian.isStatus()){
                            proDialog.dismiss();
                            List<ScheduleXunJian.ResultsBean.PatrolsBean> transferss = xunJian.getResults().getPatrols();
                            if (transferss != null && transferss.toString() !="[]"&&page != 1){
                                for (int i = 0; i < transferss.size(); i++) {
                                    transfers.add(transferss.get(i));
                                }
                                adapter.notifyDataSetChanged();
                                xunjianlist.onRefreshComplete();;
                            }else {
                                page --;
                                xunjianlist.onRefreshComplete();
                                ToastUtils.showToastSafe(ScheduleXunJianActivity.this,"没有其他数据");
                            }

                            xunjianlist.setRefreshListener(new RefreshListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                }

                                @Override
                                public void onLoadMore() {
                                    loadDataXunJianUpdate(date);
                                }
                            });
                        }
                    }
                });
    }

    private void initLinister() {
        add_schedulexunjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleXunJianActivity.this,ScheduleAddXunJianActivity.class);
                startActivity(intent);
            }
        });
        schedule_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        schedule_name.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        loadDataXunJian(date);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proDialog = new Dialog(ScheduleXunJianActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        page = 1;
        loadDataXunJian(date);
        initLinister();
    }
}
