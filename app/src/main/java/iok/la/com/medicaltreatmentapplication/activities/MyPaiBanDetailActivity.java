package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.MyScheduleDetailAdapter;
import iok.la.com.medicaltreatmentapplication.bean.MyScheduleDetailBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.DensityUtil;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import okhttp3.Call;

public class MyPaiBanDetailActivity extends BaseActivity {
    private Intent intent;
    private String selectDate;
    private View head_View;
    @BindView(R.id.schedule_list)
    ListView schedule_list;
    @BindView(R.id.turn_last)
    ImageButton turn_last;
    TextView dateweek,show_day,current_date,today,last_day,next_day;
    LinearLayout select_date;
    private Calendar calendar;
    private Button querydata;
    private long currentTime;
    String date;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_my_pai_ban_detail;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_my_pai_ban_detail;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(MyPaiBanDetailActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        calendar = Calendar.getInstance();
        intent = getIntent();
        selectDate = intent.getStringExtra("date");
        try {
            currentTime = TimeOrLongUtil.TimeToLong(selectDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        head_View = LayoutInflater.from(MyPaiBanDetailActivity.this).inflate(R.layout.my_scheduledate_headview,null);
        select_date = (LinearLayout) head_View.findViewById(R.id.select_date);
        querydata = (Button) head_View.findViewById(R.id.querydata);
        dateweek = (TextView) head_View.findViewById(R.id.dateweek);
        show_day = (TextView) head_View.findViewById(R.id.show_day);
        current_date = (TextView) head_View.findViewById(R.id.current_date);
        today = (TextView) head_View.findViewById(R.id.today);
        last_day = (TextView) head_View.findViewById(R.id.last_day);
        next_day = (TextView) head_View.findViewById(R.id.next_day);
        schedule_list.addHeaderView(head_View);
    }

    @Override
    public void initData() {
        loadDataUseDate();
        initLinister();
    }

    private void initLinister() {
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        selectDate = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        current_date.setText(selectDate);
                        try {
                            currentTime = TimeOrLongUtil.TimeToLong(selectDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        //上一天
        last_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.LastWeekl(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUseDate();
                    current_date.setText(selectDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        next_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.NextWeekl(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUseDate();
                    current_date.setText(selectDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date curDate =  new Date(System.currentTimeMillis());
                selectDate = formatter.format(curDate);
                try {
                    currentTime = TimeOrLongUtil.TimeToLong(selectDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                loadDataUseDate();
                current_date.setText(selectDate);
            }
        });
        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                loadDataUseDate();
            }
        });
    }

    private void loadDataUseDate() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/MyShiftDetails?Date="+selectDate+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyScheduleDetailBean detailBean = gson.fromJson(response, MyScheduleDetailBean.class);
                        if (detailBean.isStatus()){
                            proDialog.dismiss();
                            date = detailBean.getResults().getDate();
                            //将String时间类型变成Long类型
                            try {
                                currentTime = TimeOrLongUtil.TimeToLong(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dateweek.setText(date);
                            show_day.setText(date);
                            MyScheduleDetailAdapter adapter = new MyScheduleDetailAdapter(MyPaiBanDetailActivity.this,detailBean.getResults().getSfts());
                            schedule_list.setAdapter(adapter);
                        }
                    }
                });
    }
}
