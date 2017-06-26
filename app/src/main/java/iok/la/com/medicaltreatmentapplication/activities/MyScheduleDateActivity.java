package iok.la.com.medicaltreatmentapplication.activities;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.MyScheduleDateAdapter;
import iok.la.com.medicaltreatmentapplication.bean.MyScheduleDate;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TimeOrLongUtil;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import okhttp3.Call;

public class MyScheduleDateActivity extends BaseActivity {

    @BindView(R.id.turn_last)
    ImageButton turnLast;
    @BindView(R.id.schedule_date)
    ListView scheduleDate;
    TextView date,tomonth,last_month,next_month;
    View head_view;
    String dateString;
    long currentTime;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_my_schedule_date;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_my_schedule_date;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(MyScheduleDateActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        //加载头部视图的布局
        head_view = View.inflate(this, R.layout.my_schedule_headview, null);
        date = (TextView) head_view.findViewById(R.id.date);
        tomonth = (TextView) head_view.findViewById(R.id.tomonth);
        last_month = (TextView) head_view.findViewById(R.id.last_month);
        next_month = (TextView) head_view.findViewById(R.id.next_month);
        scheduleDate.addHeaderView(head_view);
        //获取当前月
        currentTime = System.currentTimeMillis();
        Date curDate = new  Date();//获取当前时间
        SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy-MM-dd");
        dateString  =   formatter.format(curDate);
    }

    @Override
    public void initData() {
        loadDataUseDate();
        initLinister();
    }

    private void initLinister() {
        next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.NextMonthl(currentTime);
                    dateString = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUseDate();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        last_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                try {
                    currentTime = TimeOrLongUtil.LastMonthl(currentTime);
                    dateString = TimeOrLongUtil.LongToTime(currentTime);
                    loadDataUseDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        tomonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                SimpleDateFormat formatter  =  new  SimpleDateFormat("yyyy-MM-dd");
                currentTime = System.currentTimeMillis();
                Date curDate = new  Date();//获取当前时间
                dateString  =   formatter.format(curDate);
                loadDataUseDate();
            }
        });
    }


    private void loadDataUseDate() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/MyShift?date="+dateString+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyScheduleDate dateBean = gson.fromJson(response,MyScheduleDate.class);
                        if (dateBean.isStatus()){
                            proDialog.dismiss();
                            date.setText(dateBean.getResults().getTitle());
                            List<MyScheduleDate.ResultsBean.ItemsBean> items = dateBean.getResults().getItems();
                            List datal = new ArrayList();
                            for (int i = 0; i <items.get(0).getDayOfWeek() ; i++) {
                                MyScheduleDate.ResultsBean.ItemsBean bean = null;
                                datal.add(bean);
                            }
                            for (int i = 0; i < items.size(); i++) {
                                datal.add(items.get(i));
                            }
                            for (int i = 0; i <6 - items.get(items.size()-1).getDayOfWeek() ; i++) {
                                MyScheduleDate.ResultsBean.ItemsBean bean = null;
                                datal.add(bean);
                            }
                            List<List<MyScheduleDate.ResultsBean.ItemsBean>> dataList = new ArrayList();
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
                            MyScheduleDateAdapter adapter = new MyScheduleDateAdapter(MyScheduleDateActivity.this,dataList);
                            scheduleDate.setAdapter(adapter);
                        }else {
                            ToastUtils.showToastSafe(MyScheduleDateActivity.this,"参数错误");
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
}
