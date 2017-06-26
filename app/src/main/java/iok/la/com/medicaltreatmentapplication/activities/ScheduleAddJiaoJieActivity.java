package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleJieBanZhuangTaiAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleZhuangTaiAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OtherClassData;
import iok.la.com.medicaltreatmentapplication.bean.PostScheduleData;
import iok.la.com.medicaltreatmentapplication.bean.SceduleJiaoBanAddNoteData;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleJieBanPerson;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteGridView;
import okhttp3.Call;

public class ScheduleAddJiaoJieActivity extends BaseActivity {

    @BindView(R.id.turn_last)
    ImageButton turnLast;
    @BindView(R.id.schedule_name)
    TextView scheduleName;
    @BindView(R.id.jioaban_person)
    TextView jiaobanPerson;
    @BindView(R.id.jiaoban_time)
    TextView jiaobanTime;
    @BindView(R.id.jieban_person)
    TextView jiebanPerson;
    @BindView(R.id.jiaoban_total)
    EditText jiaobanTotal;
    @BindView(R.id.backup_info)
    EditText backupInfo;
    @BindView(R.id.submit_totle)
    Button submitTotle;
    @BindView(R.id.activity_schedule_add_jiao_jie)
    LinearLayout activityScheduleAddJiaoJie;
    @BindView(R.id.commonDesc)
    ImageButton commonDesc;
    private PopupWindow office_popupWindow;
    String atCode = "";
    String jieBanId,detailTime;
    int rotaStatusID;
    int jiaoBanId;
    int[] zhuangtaiIcons;
    private Calendar calendar;
    private RadioGroup zhuangtailist;
    private RadioButton bianzhi,jiaoban,jieban;
    RadioButton[] radios0;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_add_jiao_jie;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_add_jiao_jie;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(ScheduleAddJiaoJieActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        calendar = Calendar.getInstance();
        zhuangtaiIcons = new int[]{R.mipmap.perso,R.mipmap.treatment,R.mipmap.compelete};
        zhuangtailist = (RadioGroup) findViewById(R.id.zhuangtai);
        bianzhi = (RadioButton) findViewById(R.id.bianzhi);
        jiaoban = (RadioButton) findViewById(R.id.jiaoban);
        jieban = (RadioButton) findViewById(R.id.jieban);
        radios0 = new RadioButton[]{bianzhi,jiaoban,jieban};

    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/TransfersCreate?Token="+ PrefUtils.getString(ScheduleAddJiaoJieActivity.this,"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final SceduleJiaoBanAddNoteData addNoteData = gson.fromJson(response,SceduleJiaoBanAddNoteData.class);
                        if (addNoteData.isStatus()){
                            proDialog.dismiss();
                            //交班人
                            jiaobanPerson.setText("交班人："+addNoteData.getResults().getMd().getName());
                            jiaoBanId = addNoteData.getResults().getMd().getFromEmployeeID();
                            //状态属性的checkBox
//                        ScheduleJieBanZhuangTaiAdapter adapter = new ScheduleJieBanZhuangTaiAdapter(ScheduleAddJiaoJieActivity.this,addNoteData.getResults().getStas(),zhuangtaiIcons);
//                        zhuangtgrid.setAdapter(adapter);
//                        adapter.setDataLinister(new ScheduleJieBanZhuangTaiAdapter.OnCheckDataLinister() {
//                            @Override
//                            public void checkDataLinister(List data) {
//                                RotaStatusID = data.get(0).toString();
//                            }
//                        });
                            for (int i = 0; i < addNoteData.getResults().getStas().size(); i++) {
                                radios0[i].setText(addNoteData.getResults().getStas().get(i).getName());
                                Drawable icon0 = ScheduleAddJiaoJieActivity.this.getResources().getDrawable(zhuangtaiIcons[i]);
                                Drawable icon1 = ScheduleAddJiaoJieActivity.this.getResources().getDrawable(R.mipmap.selected);
                                icon1.setBounds(0,0,icon1.getMinimumWidth(),icon1.getMinimumHeight());
                                icon0.setBounds(0,0,icon0.getMinimumWidth(),icon0.getMinimumHeight());
                                radios0[i].setCompoundDrawables(icon0,null,icon1,null);
                            }
                            zhuangtailist.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    switch (checkedId){
                                        case R.id.bianzhi:
                                            rotaStatusID = addNoteData.getResults().getStas().get(0).getID();
                                            break;
                                        case R.id.jiaoban:
                                            rotaStatusID = addNoteData.getResults().getStas().get(1).getID();
                                            break;
                                        case R.id.jieban:
                                            rotaStatusID = addNoteData.getResults().getStas().get(2).getID();
                                            break;
                                    }
                                }
                            });
                            //科室
                            initPopupWindow(scheduleName,addNoteData);
                            scheduleName.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    office_popupWindow.setFocusable(true);
                                    office_popupWindow.setOutsideTouchable(true);
                                    office_popupWindow.setTouchable(true);
                                    office_popupWindow .setBackgroundDrawable(new BitmapDrawable());
                                    office_popupWindow.showAsDropDown(scheduleName);
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
                                }
                            });
                            //常用语
                            commonDesc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List<Map> data = new ArrayList<Map>();
                                    List<SceduleJiaoBanAddNoteData.ResultsBean.SensBean> sentences = addNoteData.getResults().getSens();
                                    for (int i = 0; i < sentences.size(); i++) {
                                        Map map = new HashMap();
                                        map.put("content",sentences.get(i).getContent());
                                        map.put("id",sentences.get(i).getID());
                                        map.put("pinyin",sentences.get(i).getPinyin());
                                        map.put("summary",sentences.get(i).getSummary());
                                        map.put("type",sentences.get(i).getRotaSentenceCategoryName());
                                        data.add(map);
                                    }
                                    OtherClassData classData = new OtherClassData();
                                    classData.setData(data);
                                    Intent intent = new Intent(ScheduleAddJiaoJieActivity.this,ScheduleCommonDescActivity.class);
                                    intent.putExtra("data",classData);
                                    startActivityForResult(intent,Content.REQUEST_CODE4);
                                }
                            });
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.turn_last, R.id.schedule_name, R.id.jioaban_person, R.id.jiaoban_time, R.id.jieban_person, R.id.submit_totle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turn_last:
                finish();
                break;
            case R.id.jiaoban_time:
                //时间选择
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                detailTime = year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute;
                                jiaobanTime.setText("交班时间："+detailTime);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            case R.id.jieban_person:
                ToastUtils.showToastSafe(ScheduleAddJiaoJieActivity.this,"请先选择科室");
                break;
            case R.id.submit_totle:
                proDialog.show();
                Log.e("rotaStatusID",rotaStatusID+"");
                if (atCode.equals("")){
                    ToastUtils.showToastSafe(ScheduleAddJiaoJieActivity.this,"请选择科室");
                }else if(rotaStatusID== 0){
                    ToastUtils.showToastSafe(ScheduleAddJiaoJieActivity.this,"请选择状态属性");
                }else if (jiaobanPerson.getText().toString().trim().equals("")){
                    ToastUtils.showToastSafe(ScheduleAddJiaoJieActivity.this,"交班人不能为空");
                }else if (detailTime == null || detailTime.equals("") ){
                    ToastUtils.showToastSafe(ScheduleAddJiaoJieActivity.this,"请选择交班时间");
                }else{
                    Log.e("canshu","DepartmentID:"+atCode+"FromEmployeeID:"+jiaoBanId+"ToEmployeeID:"+jieBanId+"TransferStatusID:"+rotaStatusID);
                    OkHttpUtils
                            .post()
                            .url(Content.BASE_URL+"AppRotas/TransfersCreate")
                            .addParams("DepartmentID",atCode)
                            .addParams("FromEmployeeID",jiaoBanId+"")
                            .addParams("ToEmployeeID",jieBanId)
                            .addParams("TransferStatusID",rotaStatusID+"")
                            .addParams("Summary",jiaobanTotal.getText().toString().trim())
                            .addParams("Description",backupInfo.getText().toString().trim())
                            .addParams("TransferDate",detailTime)
                            .addParams("Token",  PrefUtils.getString(ScheduleAddJiaoJieActivity.this,"token",""))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean isSave = jsonObject.optBoolean("status");
                                        if (isSave){
                                            proDialog.dismiss();
                                            ToastUtils.showToastSafe(ScheduleAddJiaoJieActivity.this,"提交成功");
                                            finish();
                                        }else {
                                            ToastUtils.showToastSafe(ScheduleAddJiaoJieActivity.this,"保存失败");

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }

                break;
        }
    }
    private void initPopupWindow(final TextView text, final SceduleJiaoBanAddNoteData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getDps().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getDps().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getDps().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(ScheduleAddJiaoJieActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(ScheduleAddJiaoJieActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                text.setText("科室："+list.get(position).get("name"));
                atCode = list.get(position).get("id");
                office_popupWindow.dismiss();
                //接班
                OkHttpUtils
                        .get()
                        .url(Content.BASE_URL+"AppRotas/GetToUser?DepartmentID="+list.get(position).get("id")+"&Token="+token)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }
                            @Override
                            public void onResponse(String response, int id) {
                                Gson gson = new Gson();
                                ScheduleJieBanPerson banPerson = gson.fromJson(response,ScheduleJieBanPerson.class);
                                if (banPerson.isStatus()){
                                    jiebanPerson.setText("接班人："+banPerson.getName());
                                    jieBanId = banPerson.getId()+"";
                                }else {
                                    jiebanPerson.setText(banPerson.getErrorMsg());
                                    jieBanId = "";
                                }

                            }
                        });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OtherClassData otherClassData = null;
        if (data != null){
            otherClassData = (OtherClassData)data.getSerializableExtra("info");
            if (requestCode == Content.REQUEST_CODE4 && resultCode == Content.RESULT_CODE4){
                HashMap map = otherClassData.getMap();
                List list = mapToList(map);
                StringBuffer databuffer = new StringBuffer();
                for (int i = 0; i <list.size() ; i++) {
                    Map seledata = (Map) list.get(i);
                    databuffer.append(seledata.get("content")+" ");
                }
                backupInfo.setText(databuffer.toString());
            }
        }
    }
    private List mapToList(Map<Integer, Map> data) {
        List<Map> listValue = new ArrayList<>(data.values());
        return listValue;
    }
}
