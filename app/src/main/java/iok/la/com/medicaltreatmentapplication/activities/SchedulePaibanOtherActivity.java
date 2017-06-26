package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.SchedulePaibanOtherAdapter;
import iok.la.com.medicaltreatmentapplication.bean.SchedulePaiBanOtherBean;
import iok.la.com.medicaltreatmentapplication.bean.SchedulePaiBanTodayBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import okhttp3.Call;

public class SchedulePaibanOtherActivity extends BaseActivity {
    @BindView(R.id.other_schedule)
    ListView otherSchedule;
    private View head_view;
    private TextView copyLastweek, officeName, shiftText, datetext, week1, dates1, week2, dates2, week3, dates3, week4, dates4, week5, dates5, week6, dates6, week7, dates7;
    private LinearLayout offices, classtype, dateselect;
    TextView dateRange;
    LinearLayout left,right,this_week;
    private ImageView dayicon1, dayicon2, dayicon3, dayicon4, dayicon5, dayicon6, dayicon7;
    private TextView[] weeks, dates;
    private ImageView[] holidayicons;
    private Button querydata;
    String date_info;
    String DepartmentId = 1054 + "";
    String selectDate = "2017-2-5";
    String ShiftTypeId = "18";
    private PopupWindow schedulePopupWindow,office_popupWindow;
    private Calendar calendar;
    private long currentTime;
    private Dialog proDialog;

    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_paiban_other;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_paiban_other;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(SchedulePaibanOtherActivity.this,R.style.progress_dialog);
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
        calendar = Calendar.getInstance();
        head_view = LayoutInflater.from(SchedulePaibanOtherActivity.this).inflate(R.layout.schedule_paiban_other_headview, null);
        offices = (LinearLayout) head_view.findViewById(R.id.offices);
        classtype = (LinearLayout) head_view.findViewById(R.id.classtype);
        dateselect = (LinearLayout) head_view.findViewById(R.id.dateselect);
        copyLastweek = (TextView) head_view.findViewById(R.id.copy_lastweek);
        dateRange = (TextView) head_view.findViewById(R.id.date_range);
        this_week = (LinearLayout) head_view.findViewById(R.id.this_week);
        officeName = (TextView) head_view.findViewById(R.id.officename);
        datetext = (TextView) head_view.findViewById(R.id.datetext);
        shiftText = (TextView) head_view.findViewById(R.id.shift);
        left = (LinearLayout) head_view.findViewById(R.id.left);
        right = (LinearLayout) head_view.findViewById(R.id.right);
        querydata = (Button) head_view.findViewById(R.id.querydata);
        week1 = (TextView) head_view.findViewById(R.id.week1);
        dates1 = (TextView) head_view.findViewById(R.id.dates1);
        week2 = (TextView) head_view.findViewById(R.id.week2);
        dates2 = (TextView) head_view.findViewById(R.id.dates2);
        week3 = (TextView) head_view.findViewById(R.id.week3);
        dates3 = (TextView) head_view.findViewById(R.id.dates3);
        week4 = (TextView) head_view.findViewById(R.id.week4);
        dates4 = (TextView) head_view.findViewById(R.id.dates4);
        week5 = (TextView) head_view.findViewById(R.id.week5);
        dates5 = (TextView) head_view.findViewById(R.id.dates5);
        week6 = (TextView) head_view.findViewById(R.id.week6);
        dates6 = (TextView) head_view.findViewById(R.id.dates6);
        week7 = (TextView) head_view.findViewById(R.id.week7);
        dates7 = (TextView) head_view.findViewById(R.id.dates7);
        dayicon1 = (ImageView) head_view.findViewById(R.id.dayicon1);
        dayicon2 = (ImageView) head_view.findViewById(R.id.dayicon2);
        dayicon3 = (ImageView) head_view.findViewById(R.id.dayicon3);
        dayicon4 = (ImageView) head_view.findViewById(R.id.dayicon4);
        dayicon5 = (ImageView) head_view.findViewById(R.id.dayicon5);
        dayicon6 = (ImageView) head_view.findViewById(R.id.dayicon6);
        dayicon7 = (ImageView) head_view.findViewById(R.id.dayicon7);
        weeks = new TextView[]{week1, week2, week3, week4, week5, week6, week7};
        dates = new TextView[]{dates1, dates2, dates3, dates4, dates5, dates6, dates7};
        holidayicons = new ImageView[]{dayicon1, dayicon2, dayicon3, dayicon4, dayicon5, dayicon6, dayicon7};
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date_info = sDateFormat.format(new Date());
        datetext.setText(date_info);
        otherSchedule.addHeaderView(head_view);
    }

    @Override
    public void initData() {
        loadDataUse();
        initLinister();
    }

    private void initLinister() {
        offices.setOnClickListener(new View.OnClickListener() {
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
                office_popupWindow.showAsDropDown(offices);
            }
        });
        classtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedulePopupWindow.setFocusable(true);
                schedulePopupWindow.setOutsideTouchable(true);
                schedulePopupWindow.setTouchable(true);
                schedulePopupWindow .setBackgroundDrawable(new BitmapDrawable());
                office_popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            schedulePopupWindow.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                schedulePopupWindow.showAsDropDown(classtype);
            }
        });
        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        selectDate =year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        datetext.setText(selectDate);
                        try {
                            currentTime = TimeOrLongUtil.TimeToLong(selectDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                loadDataUse();
            }
        });
        this_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy-MM-dd");
                currentTime = System.currentTimeMillis();
                Date curDate = new  Date();//获取当前时间
                selectDate  =   formatter.format(curDate);
                loadDataUse();
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.LastWeekll(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.NextWeekll(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadDataUse() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL + "AppPersonelCenter/OtherDepartmentShifts?DepartmentId=" + DepartmentId + "&Date=" + selectDate + "&ShiftTypeId=" + ShiftTypeId + "&Token=" + PrefUtils.getString(getBaseContext(), "token", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        SchedulePaiBanOtherBean paiBanOtherBean = gson.fromJson(response, SchedulePaiBanOtherBean.class);
                        if (paiBanOtherBean.isStatus()) {
                            proDialog.dismiss();
                            dateRange.setText(paiBanOtherBean.getResults().getDt().getTitle());
                            List<SchedulePaiBanOtherBean.ResultsBean.DtBean.ColumnsBean> columns= paiBanOtherBean.getResults().getDt().getColumns();
                            for (int i = 0; i < columns.size(); i++) {
                                dates[i].setText(columns.get(i).getTitle());
                            }
                            initSchedulePopupWindow(shiftText,paiBanOtherBean);
                            initDepartmentPopupWindow(officeName,paiBanOtherBean);
                            SchedulePaibanOtherAdapter adapter = new SchedulePaibanOtherAdapter(SchedulePaibanOtherActivity.this,paiBanOtherBean.getResults().getDt().getRows());
                            otherSchedule.setAdapter(adapter);
                        }
                    }
                });
    }

    @OnClick(R.id.turn_last)
    public void onClick() {
        finish();
    }
    private void initSchedulePopupWindow(final TextView text, final SchedulePaiBanOtherBean data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getTps().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getTps().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getTps().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(SchedulePaibanOtherActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(SchedulePaibanOtherActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        schedulePopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                ShiftTypeId = list.get(position).get("id");
                schedulePopupWindow.dismiss();

            }
        });
    }
    private void initDepartmentPopupWindow(final TextView text, final SchedulePaiBanOtherBean data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getDps().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getDps().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getDps().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(SchedulePaibanOtherActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(SchedulePaibanOtherActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
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
}
