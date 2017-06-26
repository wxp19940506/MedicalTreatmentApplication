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
import iok.la.com.medicaltreatmentapplication.adapters.SchedulePaiBanTodayAdapter;
import iok.la.com.medicaltreatmentapplication.bean.HolidayDepartment;
import iok.la.com.medicaltreatmentapplication.bean.SchedulePaiBanTodayBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import okhttp3.Call;

public class SchedulePaibanTodayActivity extends BaseActivity {

    @BindView(R.id.turn_last)
    ImageButton turnLast;
    @BindView(R.id.today_schedule)
    ListView todaySchedule;
    View head_View;
    LinearLayout classtype,dateselect,today,left,right;
    Button querydata;
    TextView date_range,today_date,shift,datetext;
    String selectDate = "2017-2-5";
    String ShiftTypeId = 18+"";
    private PopupWindow office_popupWindow;
    private Calendar calendar;
    private long currentTime;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_paiban_today;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_paiban_today;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(SchedulePaibanTodayActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        calendar = Calendar.getInstance();
        head_View = LayoutInflater.from(SchedulePaibanTodayActivity.this).inflate(R.layout.schedule_paiban_today_headview,null);
        classtype = (LinearLayout) head_View.findViewById(R.id.classtype);
        dateselect = (LinearLayout) head_View.findViewById(R.id.dateselect);
        today = (LinearLayout) head_View.findViewById(R.id.today);
        left = (LinearLayout) head_View.findViewById(R.id.left);
        right = (LinearLayout) head_View.findViewById(R.id.right);
        querydata = (Button) head_View.findViewById(R.id.querydata);
        date_range = (TextView) head_View.findViewById(R.id.date_range);
        today_date = (TextView) head_View.findViewById(R.id.today_date);
        shift = (TextView) head_View.findViewById(R.id.shift);
        datetext = (TextView) head_View.findViewById(R.id.datetext);
        todaySchedule.addHeaderView(head_View);
        SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy-MM-dd");
        currentTime = System.currentTimeMillis();
        Date curDate = new  Date();//获取当前时间
        selectDate  = formatter.format(curDate);
    }

    @Override
    public void initData() {
        loadDataUse();
        initLinister();
    }

    private void initLinister() {
        today.setOnClickListener(new View.OnClickListener() {
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
                    currentTime = TimeOrLongUtil.LastWeekl(currentTime);
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
                    currentTime = TimeOrLongUtil.NextWeekl(currentTime);
                    selectDate = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
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
        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
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
    }

    private void loadDataUse() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/CurrentDepartmentDayShifts?Date="+selectDate+"&ShiftTypeId="+ShiftTypeId+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        SchedulePaiBanTodayBean paiBanTodayBean = gson.fromJson(response,SchedulePaiBanTodayBean.class);
                        if (paiBanTodayBean.isStatus()){
                            proDialog.dismiss();
                            date_range.setText(paiBanTodayBean.getResults().getDt().getTitle().toString());
                            //String time = paiBanTodayBean.getResults().getDt().getDate();
                            today_date.setText(paiBanTodayBean.getResults().getDt().getColumns().get(0).getTitle());
                            initSchedulePopupWindow(shift,paiBanTodayBean);
                            List<SchedulePaiBanTodayBean.ResultsBean.DtBean.RowsBean> data= paiBanTodayBean.getResults().getDt().getRows();
                            SchedulePaiBanTodayAdapter adapter = new SchedulePaiBanTodayAdapter(SchedulePaibanTodayActivity.this,data);
                            todaySchedule.setAdapter(adapter);
                        }
                    }
                });
    }

    @OnClick(R.id.turn_last)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turn_last:
                finish();
                break;
        }
    }
    private void initSchedulePopupWindow(final TextView text, final SchedulePaiBanTodayBean data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getSt().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getSt().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getSt().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(SchedulePaibanTodayActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(SchedulePaibanTodayActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
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
