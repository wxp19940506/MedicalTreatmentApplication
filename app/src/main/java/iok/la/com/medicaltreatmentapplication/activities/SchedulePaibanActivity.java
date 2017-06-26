package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
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
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.OfficeScheduleAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.SchedulePaibanAdapter;
import iok.la.com.medicaltreatmentapplication.bean.HolidayDepartment;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfo;
import iok.la.com.medicaltreatmentapplication.bean.SchedulePaiBanBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import okhttp3.Call;

public class SchedulePaibanActivity extends BaseActivity {
    @BindView(R.id.schedule_paiban_list)
    ListView schedulePaibanList;
    @BindView(R.id.turn_last)
    ImageButton turn_last;
    private View head_View;
    private TextView date_range,shift,left,right,datetext,week1,dates1,week2,dates2,week3,dates3,week4,dates4,week5,dates5,week6,dates6,week7,dates7;
    private Button querydata;
    private ImageView dayicon1,dayicon2,dayicon3,dayicon4,dayicon5,dayicon6,dayicon7;
    private String selectDate = "2017-02-5";
    private long currentTime;
    private String ShiftTypeId = 18+"";
    private TextView[] weeks,dates;
    private ImageView[] holidayicons;
    private LinearLayout classtype,dateselect,this_week;
    private Calendar calendar;
    private PopupWindow office_popupWindow;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_paiban;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_paiban;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(SchedulePaibanActivity.this,R.style.progress_dialog);
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
        //加载头部视图的布局
        head_View = View.inflate(this, R.layout.schedule_paiban_headview, null);
        classtype = (LinearLayout) head_View.findViewById(R.id.classtype);
        dateselect = (LinearLayout) head_View.findViewById(R.id.dateselect);
        left = (TextView) head_View.findViewById(R.id.left);
        right = (TextView) head_View.findViewById(R.id.right);
        querydata = (Button) head_View.findViewById(R.id.querydata);
        week1 = (TextView) head_View.findViewById(R.id.week1);
        dates1 = (TextView) head_View.findViewById(R.id.dates1);
        week2 = (TextView) head_View.findViewById(R.id.week2);
        dates2 = (TextView) head_View.findViewById(R.id.dates2);
        week3 = (TextView) head_View.findViewById(R.id.week3);
        dates3 = (TextView) head_View.findViewById(R.id.dates3);
        week4 = (TextView) head_View.findViewById(R.id.week4);
        dates4 = (TextView) head_View.findViewById(R.id.dates4);
        week5 = (TextView) head_View.findViewById(R.id.week5);
        dates5 = (TextView) head_View.findViewById(R.id.dates5);
        week6 = (TextView) head_View.findViewById(R.id.week6);
        dates6 = (TextView) head_View.findViewById(R.id.dates6);
        week7 = (TextView) head_View.findViewById(R.id.week7);
        dates7 = (TextView) head_View.findViewById(R.id.dates7);
        dayicon1 = (ImageView) head_View.findViewById(R.id.dayicon1);
        dayicon2 = (ImageView) head_View.findViewById(R.id.dayicon2);
        dayicon3= (ImageView) head_View.findViewById(R.id.dayicon3);
        dayicon4 = (ImageView) head_View.findViewById(R.id.dayicon4);
        dayicon5 = (ImageView) head_View.findViewById(R.id.dayicon5);
        dayicon6 = (ImageView) head_View.findViewById(R.id.dayicon6);
        dayicon7 = (ImageView) head_View.findViewById(R.id.dayicon7);
        date_range = (TextView) head_View.findViewById(R.id.date_range);
        shift = (TextView) head_View.findViewById(R.id.shift);
        datetext = (TextView) head_View.findViewById(R.id.datetext);
        this_week = (LinearLayout) head_View.findViewById(R.id.this_week);
        schedulePaibanList.addHeaderView(head_View);
        weeks = new TextView[]{week1,week2,week3,week4,week5,week6,week7};
        dates = new TextView[]{dates1,dates2,dates3,dates4,dates5,dates6,dates7};
        holidayicons = new ImageView[]{dayicon1,dayicon2,dayicon3,dayicon4,dayicon5,dayicon6,dayicon7};
    }

    @Override
    public void initData() {
        loadDataUseDateSchedule();
        initLinister();
    }

    private void initLinister() {
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        selectDate = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        try {
                            currentTime = TimeOrLongUtil.TimeToLong(selectDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        datetext.setText(selectDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        //班次类型
        classtype.setOnClickListener(new View.OnClickListener() {
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
                office_popupWindow.showAsDropDown(shift);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.LastWeekll(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUseDateSchedule();
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
                    loadDataUseDateSchedule();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
                loadDataUseDateSchedule();
            }
        });
        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                loadDataUseDateSchedule();
            }
        });
    }

    private void loadDataUseDateSchedule() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/CurrentDepartmentShifts?Date="+selectDate+"&ShiftTypeId="+ShiftTypeId+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        SchedulePaiBanBean scheduleInfo = gson.fromJson(response, SchedulePaiBanBean.class);
                        if (scheduleInfo.isStatus()){
                            proDialog.dismiss();
                            initPopupWindow(shift,scheduleInfo);
                            date_range.setText(scheduleInfo.getResults().getDt().getTitle());
                            for (int i = 0; i <  scheduleInfo.getResults().getDt().getColumns().size(); i++) {
                                String title =scheduleInfo.getResults().getDt().getColumns().get(i).getTitle();
                                boolean isHoliday =scheduleInfo.getResults().getDt().getColumns().get(i).isHoliday();
                                boolean isWorkday =scheduleInfo.getResults().getDt().getColumns().get(i).isWorkDay();
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
                            SchedulePaibanAdapter adapter = new SchedulePaibanAdapter(SchedulePaibanActivity.this,scheduleInfo.getResults().getDt().getRows());
                            schedulePaibanList.setAdapter(adapter);
                        }else {
                            ToastUtils.showToastSafe(SchedulePaibanActivity.this,"参数错误");

                        }
                    }
                });
    }
    private void initPopupWindow(final TextView text, final SchedulePaiBanBean data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getSt().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getSt().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getSt().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(SchedulePaibanActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(SchedulePaibanActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                ShiftTypeId = list.get(position).get("id");
                office_popupWindow.dismiss();

            }
        });
    }
}
