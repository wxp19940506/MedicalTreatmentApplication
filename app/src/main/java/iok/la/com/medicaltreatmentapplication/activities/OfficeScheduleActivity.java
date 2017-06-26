package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.OfficeScheduleAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.OfficelistAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OfficeData;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfo;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.util.Content;
import okhttp3.Call;

public class OfficeScheduleActivity extends BaseActivity {
    @BindView(R.id.office_schedule_list)
    ListView officeScheduleList;
    @BindView(R.id.week)
    LinearLayout week;
    TextView dateRange, this_week;
    TextView left;
    TextView right;
    int departmentID,shiftTypeID,currentDepartmentID ,currentShiftTypeID ;
    private ProgressBar mProBar;
    private TextView copyLastweek, officeName,shiftText,datetext,week1,dates1,week2,dates2,week3,dates3,week4,dates4,week5,dates5,week6,dates6,week7,dates7;
    @BindView(R.id.activity_office_schedule)
    LinearLayout activityOfficeSchedule;
    @BindView(R.id.turn_last)
    ImageButton turn_last;
    private ImageView dayicon1,dayicon2,dayicon3,dayicon4,dayicon5,dayicon6,dayicon7;
    private List<Map<String, String>> data, officedata, clsstypedata;
    private View head_view;
    private LinearLayout offices, classtype, dateselect;
    private PopupWindow office_popupWindow,shift_popupWindow,date_popupWindow;
    String date_info;
    private Button querydata;
    private TextView[] weeks,dates;
    private ImageView[] holidayicons;
    long time;
    long currentTime;
    private Calendar calendar;
    private DateFormat dateFormat;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_office_schedule;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_office_schedule;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
//        proDialog = android.app.ProgressDialog.show(OfficeScheduleActivity.this, "等待", "加载中。。");
        proDialog = new Dialog(OfficeScheduleActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        //加载头部视图的布局
        head_view = View.inflate(this, R.layout.office_schedule_headview, null);
        offices = (LinearLayout) head_view.findViewById(R.id.offices);
        classtype = (LinearLayout) head_view.findViewById(R.id.classtype);
        dateselect = (LinearLayout) head_view.findViewById(R.id.dateselect);
        copyLastweek = (TextView) head_view.findViewById(R.id.copy_lastweek);
        dateRange = (TextView) head_view.findViewById(R.id.date_range);
        this_week = (TextView) head_view.findViewById(R.id.this_week);
        officeName = (TextView) head_view.findViewById(R.id.officename);
        datetext = (TextView) head_view.findViewById(R.id.datetext);
        shiftText = (TextView) head_view.findViewById(R.id.shift);
        left = (TextView) head_view.findViewById(R.id.left);
        right = (TextView) head_view.findViewById(R.id.right);
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
        dayicon3= (ImageView) head_view.findViewById(R.id.dayicon3);
        dayicon4 = (ImageView) head_view.findViewById(R.id.dayicon4);
        dayicon5 = (ImageView) head_view.findViewById(R.id.dayicon5);
        dayicon6 = (ImageView) head_view.findViewById(R.id.dayicon6);
        dayicon7 = (ImageView) head_view.findViewById(R.id.dayicon7);
        weeks = new TextView[]{week1,week2,week3,week4,week5,week6,week7};
        dates = new TextView[]{dates1,dates2,dates3,dates4,dates5,dates6,dates7};
        holidayicons = new ImageView[]{dayicon1,dayicon2,dayicon3,dayicon4,dayicon5,dayicon6,dayicon7};
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date_info = sDateFormat.format(new java.util.Date());
        datetext.setText(date_info);
        data = new ArrayList();
        try {
            //将datetext格式转换成毫秒数
            String datamil = datetext.getText().toString().trim();
            currentTime = TimeOrLongUtil.TimeToLong(datamil);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        download();
        viewLinister();
//        queryOfficeSchedule(currentDepartmentID,currentShiftTypeID,date_info);
        officeScheduleList.addHeaderView(head_view);//头部视图
    }
    //科室和班次类型的数据
    private void download() {

       String data_url = Content.BASE_URL+"AppShifts/Index";
        String name = PrefUtils.getString(getBaseContext(),"username", "");
        OkHttpUtils
                .post()
                .url(data_url)
                .addParams("Username",name+"")
                .addParams("Token", PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final OfficeData data = gson.fromJson(response, OfficeData.class);
                        if (data.isStatus()){
                            if (data.getResults().getShiftTypes().size() > 0 )
                                currentShiftTypeID = data.getResults().getShiftTypes().get(0).getId();
                            if (data.getResults().getDepartments().size() > 0)
                                currentDepartmentID = data.getResults().getDepartments().get(0).getId();
                            proDialog.show();
                            queryOfficeSchedule(currentDepartmentID, currentShiftTypeID, date_info);
                            if (data.getResults().getDepartments().size() >0)
                                officeName.setText(data.getResults().getDepartments().get(0).getName());//默认显示第一个科室
                            if (data.getResults().getShiftTypes().size() >0)
                                shiftText.setText(data.getResults().getShiftTypes().get(0).getName() );//默认显示第一个班次
                            //科室点击事件
                            offices.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //officeCount++;
                                    initPopupWindow(data);
                                }
                            });
                            //班次类型点击事件
                            classtype.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List<Map<String, String>> shift = new ArrayList<Map<String, String>>();
                                    if (officeName.getText().toString() != "科室") {
                                        for (int i = 0; i < data.getResults().getDepartments().size(); i++) {
                                            if (data.getResults().getDepartments().get(i).getName().equals(officeName.getText().toString())) {
                                                int officeid = data.getResults().getDepartments().get(i).getId();
                                                for (int j = 0; j < data.getResults().getShiftTypes().size(); j++) {
                                                    if (officeid == data.getResults().getShiftTypes().get(j).getDepartmentId()) {
                                                        Map<String, String> map = new HashMap<String, String>();
                                                        map.put("id", data.getResults().getShiftTypes().get(j).getId() + "");
                                                        map.put("name", data.getResults().getShiftTypes().get(j).getName() + "");
                                                        shift.add(map);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    initShiftPopupWindow(shift);
                                    if (shift.toString().equals("[]") || shift == null){
                                        shift_popupWindow.dismiss();
                                        ToastUtils.showToastSafe(OfficeScheduleActivity.this,"对不起,该科室暂无班次类型");
                                    }
                                }
                            });
                        }else {
                            ToastUtils.showToastSafe(OfficeScheduleActivity.this,"参数错误");
                        }

                    }
                });

    }

    private void initShiftPopupWindow(final List<Map<String, String>> shift) {
        View view = LayoutInflater.from(OfficeScheduleActivity.this).inflate(R.layout.office_list, null);
        ListView shiftlist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(OfficeScheduleActivity.this,shift,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        shiftlist.setAdapter(adapter);
        shift_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        shift_popupWindow.setFocusable(true);
        shift_popupWindow.setOutsideTouchable(true);
        shift_popupWindow.setTouchable(true);
        shift_popupWindow.setBackgroundDrawable(new BitmapDrawable());
        shift_popupWindow.showAsDropDown(classtype);
        shiftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shiftTypeID = Integer.parseInt(shift.get(position).get("id"));
                currentShiftTypeID = Integer.parseInt(shift.get(position).get("id"));
                shiftText.setText(shift.get(position).get("name"));
                shift_popupWindow.dismiss();
            }
        });
    }

    private void initPopupWindow(final OfficeData data) {
        View view = LayoutInflater.from(OfficeScheduleActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        OfficelistAdapter adapter = new OfficelistAdapter(OfficeScheduleActivity.this, data.getResults().getDepartments());
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        office_popupWindow.setFocusable(true);
        office_popupWindow.setOutsideTouchable(true);
        office_popupWindow.setTouchable(true);
        office_popupWindow .setBackgroundDrawable(new BitmapDrawable());
        office_popupWindow.showAsDropDown(offices);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                departmentID = data.getResults().getDepartments().get(position).getId();
                currentDepartmentID = data.getResults().getDepartments().get(position).getId();
                officeName.setText(data.getResults().getDepartments().get(position).getName());
                if (office_popupWindow != null && office_popupWindow.isShowing()) {
                    office_popupWindow.dismiss();
                    shiftText.setText("班次类型");
                }

            }
        });
    }


    private void viewLinister() {
        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                String somedate = datetext.getText().toString();
                if (departmentID == 0 || shiftTypeID== 0){
                    datetext.setText(somedate);
                    queryOfficeSchedule(currentDepartmentID, currentShiftTypeID, somedate);
                }else {
                    datetext.setText(somedate);
                    queryOfficeSchedule(departmentID, shiftTypeID, somedate);
                }

            }
        });
        this_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proDialog.show();
                Date dt= new Date();
                currentTime= dt.getTime();
                try {
                    date_info = TimeOrLongUtil.LongToTime(currentTime);
                    datetext.setText(date_info);
                    queryOfficeSchedule(currentDepartmentID,currentShiftTypeID,date_info);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        left.setOnClickListener(new View.OnClickListener() {//跳到上一周
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime= TimeOrLongUtil.LastWeekll(currentTime);
                    String toTime = TimeOrLongUtil.LongToTime(currentTime);
                    datetext.setText(toTime);
                    queryOfficeSchedule(currentDepartmentID,currentShiftTypeID,toTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        right.setOnClickListener(new View.OnClickListener() {//跳到下一周
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime= TimeOrLongUtil.NextWeekll(currentTime);
                    String time = TimeOrLongUtil.LongToTime(currentTime);
                    datetext.setText(time);
                    queryOfficeSchedule(currentDepartmentID,currentShiftTypeID,time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                        String thisdate = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        datetext.setText(thisdate);
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

    private void queryOfficeSchedule(int departmentID, int shiftTypeID, String somedate) {
        String office_data_url = Content.BASE_URL+"AppShifts/ShiftPartial";
        OkHttpUtils
                .post()
                .url(office_data_url)
                .addParams("DepartmentID",departmentID+"")
                .addParams("ShiftTypeID",shiftTypeID+"")
                .addParams("Date",somedate)
                .addParams("Token", PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        OfficeScheduleInfo scheduleInfo = gson.fromJson(response, OfficeScheduleInfo.class);
                        if (scheduleInfo.isStatus()){
                            proDialog.dismiss();
                            dateRange.setText(scheduleInfo.getResults().getTitle());
                            for (int i = 0; i <  scheduleInfo.getResults().getColumns().size(); i++) {
                                String title =scheduleInfo.getResults().getColumns().get(i).getTitle();
                                boolean isHoliday =scheduleInfo.getResults().getColumns().get(i).isHoliday();
                                boolean isWorkday =scheduleInfo.getResults().getColumns().get(i).isWorkDay();
                                weeks[i].setText(title.substring(0,title.indexOf(" ")));
                                dates[i].setText(title.substring(title.indexOf(" ")));
                                holidayicons[i].setImageResource(R.mipmap.weekday);
                                holidayicons[i].setVisibility(View.INVISIBLE);
                                if (isHoliday){
                                    holidayicons[i].setImageResource(R.mipmap.holiday);
                                    holidayicons[i].setVisibility(View.VISIBLE);
                                }else if (isWorkday){
                                    holidayicons[i].setImageResource(R.mipmap.weekday);
                                    holidayicons[i].setVisibility(View.VISIBLE);
                                }
                            }
                            OfficeScheduleAdapter adapter = new OfficeScheduleAdapter(OfficeScheduleActivity.this,scheduleInfo.getResults().getRows(),
                                    officeName.getText().toString().trim(),shiftText.getText().toString().trim(),currentDepartmentID,currentShiftTypeID,datetext.getText().toString().trim());
                            officeScheduleList.setAdapter(adapter);
                        }else {
                            ToastUtils.showToastSafe(OfficeScheduleActivity.this,"参数错误");

                        }

                    }
                });
    }


    @Override
    public void initData() {
        queryOfficeSchedule(currentDepartmentID,currentShiftTypeID,date_info);
    }


    /**
     * progressbar
     */
    private void showProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        // 解决java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
        ViewGroup viewGroup = (ViewGroup) mProBar.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mProBar);
        }
        layout.addView(mProBar);
    }

    private void dissmissProgressBar() {
        mProBar.setVisibility(View.GONE);
    }


    //view.isEnabled() && view.isShown() && view.isClickable()
    public void showWeek() {
        if (head_view.getVisibility() == View.VISIBLE) {
            week.setVisibility(View.GONE);
        } else {
            week.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        proDialog = new Dialog(OfficeScheduleActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        Date dt= new Date();
        time= dt.getTime();
        datetext.setText(new SimpleDateFormat("yyyy-MM-dd").format(time));
        queryOfficeSchedule(currentDepartmentID,currentShiftTypeID,date_info);

    }
}
