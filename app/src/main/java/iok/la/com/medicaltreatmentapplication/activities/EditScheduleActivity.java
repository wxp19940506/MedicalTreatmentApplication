package iok.la.com.medicaltreatmentapplication.activities;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.BaseApplication;
import iok.la.com.medicaltreatmentapplication.MainActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleFrequenciesAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleTypeAdapter;
import iok.la.com.medicaltreatmentapplication.bean.EditBanBean;
import iok.la.com.medicaltreatmentapplication.bean.Schedule_circleData;
import iok.la.com.medicaltreatmentapplication.bean.Schedule_fixedData;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.util.Content;
import okhttp3.Call;

public class EditScheduleActivity extends BaseActivity {
    private ImageButton turn_last,go_home;
    private int currentDepartmentID,currentShiftTypeID,userId,frequencyID;
    private String date, officeName,name,enable_date;
    TextView office_title,date_title,name_title;
    RadioGroup schedule_radiogroup;
    private GridView schedule_type;
    private Button submit_info;
    private EditText edit;
    List data;
    Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_edit_schedule;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_edit_schedule;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(EditScheduleActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();        edit = (EditText) findViewById(R.id.edit);
        submit_info = (Button) findViewById(R.id.submit_info);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        go_home = (ImageButton) findViewById(R.id.go_home);
        office_title  = (TextView) findViewById(R.id.office_title);
        date_title  = (TextView) findViewById(R.id.date_title);
        name_title  = (TextView) findViewById(R.id.name_title);
        schedule_type = (GridView) findViewById(R.id.schedule_type);
        schedule_radiogroup = (RadioGroup) findViewById(R.id.schedule_radiogroup);
        Intent intent = getIntent();
        currentDepartmentID = intent.getIntExtra("currentDepartmentID",1054);
        currentShiftTypeID = intent.getIntExtra("currentShiftTypeID",18);
        frequencyID = intent.getIntExtra("FrequencyID",0);
        userId = intent.getIntExtra("id",18);
        date =intent.getStringExtra("date");
        EditBanBean editBanBean = (EditBanBean) intent.getSerializableExtra("data");
        data = editBanBean.getData();
        Log.e("datas",frequencyID+"");
        officeName =intent.getStringExtra("officeName");
        name =intent.getStringExtra("name");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy");
        String year = sDateFormat.format(new java.util.Date());
        String month = date.substring(3).substring(0,date.substring(3).indexOf("/"));
        String day = date.substring(3).substring(date.substring(3).indexOf("/")+1);
        enable_date = year+"-"+month+"-"+day;
        office_title.setText(officeName);
        date_title.setText(date);
        name_title.setText(name);
    }

    @Override
    public void initData() {
        loadFixedScheduleData();
        initLinister();
    }

    private void initLinister() {

        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditScheduleActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        schedule_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.fixed_schedule:
                        proDialog.show();
                        loadFixedScheduleData();
                        break;
                    case R.id.circle_schadule:
                        proDialog.show();
                        loadCircleScheduleData();
                        break;
                }
            }
        });
    }
    private void loadFixedScheduleData() {
        String fixed_url = Content.BASE_URL+"AppShifts/ShiftAddInFix";
        //固定排班
        OkHttpUtils
                .post()
                .url(fixed_url)
                .addParams("DepartmentID",currentDepartmentID+"")
                .addParams("ShiftTypeID", currentShiftTypeID+"")
                .addParams("EmployeeId", userId+"")
                .addParams("Date", enable_date+"")
                .addParams("Token", token+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final Schedule_fixedData fixedData = gson.fromJson(response, Schedule_fixedData.class);
                        if (fixedData.isStatus()){
                            proDialog.dismiss();
                            ScheduleTypeAdapter adapter = new ScheduleTypeAdapter(EditScheduleActivity.this,fixedData.getResults().getShiftPlans(),data);
                            schedule_type.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            adapter.setDataLinister(new ScheduleTypeAdapter.OnCheckDataLinister() {
                                @Override
                                public void checkDataLinister(final Map map) {
                                    submit_info.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            List data = mapToList(map);
                                            StringBuffer plans = new StringBuffer();
                                            for (int i = 0; i <data.size() ; i++) {
                                                if (i == data.size() -1){
                                                    plans.append(data.get(i).toString().trim());
                                                }else {
                                                    plans.append(data.get(i).toString().trim()+",");
                                                }
                                            }
                                            String fixed_url = Content.BASE_URL+"AppShifts/ShiftAddInFixSave";
                                            String description = "";
                                            if (edit.getText().toString().trim() !=""){
                                                description = edit.getText().toString().trim();
                                            }
                                            if (currentShiftTypeID == 0){
                                                ToastUtils.showToastSafe(EditScheduleActivity.this,"");
                                                return;
                                            }else {
                                                OkHttpUtils
                                                        .post()
                                                        .url(fixed_url)
                                                        .addParams("ItemID", userId+"")
                                                        .addParams("Date", enable_date)
                                                        .addParams("DepartmentID", currentDepartmentID+"")
                                                        .addParams("Description", description)
                                                        .addParams("ShiftTypeId", currentShiftTypeID+"")
                                                        .addParams("Plans",plans.toString().trim())
                                                        .addParams("Token", token+"")
                                                        .build()
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onError(Call call, Exception e, int id) {
                                                                ToastUtils.showToastSafe(EditScheduleActivity.this,"连接超时，请先连接网络！");
                                                            }
                                                            @Override
                                                            public void onResponse(String response, int id) {
                                                                if (response != null){
                                                                    try {
                                                                        JSONObject jsonObject = new JSONObject(response);
                                                                        if (jsonObject.optBoolean("status")){
                                                                            ToastUtils.showToastSafe(EditScheduleActivity.this,"排班成功");
                                                                            finish();
                                                                        }else {
                                                                            ToastUtils.showToastSafe(EditScheduleActivity.this,"排班失败");
                                                                        }
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }

                                                                }
                                                            }
                                                        });
                                            }
                                        }
                                    });

                                }
                            });
                        }

                    }
                });
    }

    private void loadCircleScheduleData() {
        //周期排班
        String circle_url = Content.BASE_URL+"AppShifts/ShiftAddInSequence";
        OkHttpUtils
                .post()
                .url(circle_url)
                .addParams("DepartmentID",currentDepartmentID+"")
                .addParams("ShiftTypeID", currentShiftTypeID+"")
                .addParams("EmployeeId", userId+"")
                .addParams("Date", enable_date+"")
                .addParams("Token", token+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Schedule_circleData circleData = gson.fromJson(response, Schedule_circleData.class);
                        if (circleData.isStatus()){
                            proDialog.dismiss();
                            ScheduleFrequenciesAdapter adapter = new ScheduleFrequenciesAdapter(EditScheduleActivity.this,circleData.getResults().getFrequencies(),frequencyID);
                            schedule_type.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            adapter.setDataLinister(new ScheduleFrequenciesAdapter.OnCheckDataLinister() {
                                @Override
                                public void CheckDataLinister(final Map<Integer, Integer> data) {
                                    submit_info.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String circlr_url = Content.BASE_URL+"AppShifts/ShiftAddInSequenceSave";
                                            //List list = mapToListCircle(data);
                                            //.e("list",list.get(0).toString());
                                            String description = "";
                                            if (edit.getText().toString().trim() !="填写备注信息"){
                                                description = edit.getText().toString().trim();
                                            }
                                            OkHttpUtils
                                                    .post()
                                                    .url(circlr_url)
                                                    .addParams("ItemID", userId+"")
                                                    .addParams("Date", enable_date)
                                                    .addParams("DepartmentID", currentDepartmentID+"")
                                                    .addParams("Description", description)
                                                    .addParams("ShiftTypeId", currentShiftTypeID+"")
                                                    .addParams("FrequencyID",data.get(0).toString())
                                                    .addParams("Token", token+"")
                                                    .build()
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onError(Call call, Exception e, int id) {
                                                            ToastUtils.showToastSafe(EditScheduleActivity.this,"连接超时，请先连接网络！");
                                                        }
                                                        @Override
                                                        public void onResponse(String response, int id) {
                                                            if (response != null){
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(response);
                                                                    if (jsonObject.optBoolean("status")){
                                                                        ToastUtils.showToastSafe(EditScheduleActivity.this,"排班成功");
                                                                        finish();
                                                                    }else {
                                                                        ToastUtils.showToastSafe(EditScheduleActivity.this,"排班失败");
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }
                                                        }
                                                    });
                                        }
                                    });
                                }
                            });
                        }
                    }
                });

                }
    private List mapToList(Map data) {
        List listValue = new ArrayList<>(data.values());
        return listValue;
    }

}
