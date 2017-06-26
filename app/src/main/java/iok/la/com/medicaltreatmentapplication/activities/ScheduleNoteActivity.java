package iok.la.com.medicaltreatmentapplication.activities;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleJinJIAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.ScheduleZhuangTaiAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OtherClassData;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteAddData;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteGridView;
import okhttp3.Call;

public class ScheduleNoteActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {
    private ImageButton turn_last,commonDesc;
    private TextView open_time,request_time,schedule_name,total_office;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private static final String TIME_PATTERN = "HH:mm";
    private PopupWindow office_popupWindow,departMent_popupWindow;
    private TagFlowLayout class_select_data,otherclass_select_data;
    private RadioButton jinji,zhongda,yiban;
    private RadioButton xinjian,chuli,wancheng;
    RadioButton[] radios;
    RadioButton[] radios0;
    private RadioGroup jijinlist;
    private RadioGroup zhuangtailist;
    private int[] jinjiIcons,zhuangtaiIcons;
    private Button more,less,submit_totle;
    private List<String> spIndex = new ArrayList<>();
    private LinearLayout other_class;
    private EditText desc_info;
    private Map<Integer, Object> selectMap = new HashMap<>();
    private String DepartmentID;
    private int RotaImportanceID;
    private int RotaStatusID;
    private int GeneralDepartmentID ;
    StringBuffer others = new StringBuffer();
    Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_note;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_note;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(ScheduleNoteActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        class_select_data = (TagFlowLayout) findViewById(R.id.class_select_data);
        otherclass_select_data = (TagFlowLayout) findViewById(R.id.otherclass_select_data);
        more = (Button) findViewById(R.id.more);
        less = (Button) findViewById(R.id.less);
        submit_totle = (Button) findViewById(R.id.submit_totle);
        desc_info = (EditText) findViewById(R.id.desc_info);
        jinjiIcons = new int[]{R.mipmap.urgent,R.mipmap.major,R.mipmap.commony};
        zhuangtaiIcons = new int[]{R.mipmap.perso,R.mipmap.treatment,R.mipmap.compelete};
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        commonDesc = (ImageButton) findViewById(R.id.commonDesc);
        open_time = (TextView) findViewById(R.id.open_time);
        request_time = (TextView) findViewById(R.id.request_time);
        schedule_name = (TextView) findViewById(R.id.schedule_name);
        total_office = (TextView) findViewById(R.id.total_office);
        jijinlist = (RadioGroup) findViewById(R.id.jijinlist);
        jinji = (RadioButton) findViewById(R.id.jinji);
        zhongda = (RadioButton) findViewById(R.id.zhongda);
        yiban = (RadioButton) findViewById(R.id.yiban);
        zhuangtailist = (RadioGroup) findViewById(R.id.zhuangtailist);
        xinjian = (RadioButton) findViewById(R.id.xinjian);
        chuli = (RadioButton) findViewById(R.id.chuli);
        wancheng = (RadioButton) findViewById(R.id.wancheng);
        radios = new RadioButton[]{jinji,zhongda,yiban};
        radios0 = new RadioButton[]{xinjian,chuli,wancheng};
        other_class = (LinearLayout) findViewById(R.id.other_class);
        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());
    }

    @Override
    public void initData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppRotas/RotasCreate?Token="+PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, final int id) {
                        //
                        final List<ScheduleNoteAddData.ResultsBean.OfsBean> rotaCategorieSelf = new ArrayList<>();
                        Gson gson = new Gson();
                        final ScheduleNoteAddData noteData= gson.fromJson(response,ScheduleNoteAddData.class);

                        //分类选择的checkBox
                        if(noteData.isStatus()){
                            proDialog.dismiss();
                            //总值班科室数据
                            initTotalSchedulePopupWindow(noteData);
                            //科室数据
                            initPopupWindow(noteData);
//                            final List<ScheduleNoteData.ResultsBean.OfsBean> rotaCategories =  noteData.getResults().getOfs();
                            final List<ScheduleNoteAddData.ResultsBean.OfsBean> rotaCategories = noteData.getResults().getOfs();
                            for (int i = 0; i <rotaCategories.size() ; i++) {
                            }
                            if (rotaCategories.size() >= 4){
                                for (int i = 0; i <4 ; i++) {
                                    rotaCategorieSelf.add(rotaCategories.get(i));
                                }
                            }else {
                                showAllDataList(rotaCategories);

                            }
                            showAllDataList(rotaCategorieSelf);
                            more.setVisibility(View.VISIBLE);
                            //加载更多的按钮
                            more.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    more.setVisibility(View.GONE);
                                    less.setVisibility(View.VISIBLE);
                                    showAllDataList(rotaCategories);
                                }
                            });

                            //收回按钮
                            less.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final List list = mapObjectToList(selectMap);
                                    selectMap.clear();
                                    for (int i = 0; i < list.size(); i++) {
                                        ScheduleNoteAddData.ResultsBean.OfsBean ofsBean = new ScheduleNoteAddData.ResultsBean.OfsBean();
                                        ScheduleNoteAddData.ResultsBean.OfsBean hasData = (ScheduleNoteAddData.ResultsBean.OfsBean) list.get(i);
                                        ofsBean.setID(hasData.getID());
                                        ofsBean.setName(hasData.getName());
                                        selectMap.put(i,ofsBean);
                                    }
                                    final List listnow = mapObjectToList(selectMap);
                                    deleteClassShowList(listnow);
                                    less.setVisibility(View.GONE);
                                    more.setVisibility(View.VISIBLE);

                                }
                            });
                            //紧急程度的checkBox
//                            ScheduleJinJIAdapter adapter1 = new ScheduleJinJIAdapter(ScheduleNoteActivity.this,noteData.getResults().getRotaImportances(),jinjiIcons);
//                            jijinlist.setAdapter(adapter1);
//                            adapter1.setDataLinister(new ScheduleJinJIAdapter.OnCheckDataLinister() {
//                                @Override
//                                public void checkDataLinister(List data) {
//                                    RotaImportanceID = (int) data.get(0);
//                                }
//                            });
                            for (int i = 0; i < noteData.getResults().getRotaImportances().size(); i++) {
                                radios[i].setText(noteData.getResults().getRotaImportances().get(i).getName());
                                Drawable icon0 = ScheduleNoteActivity.this.getResources().getDrawable(jinjiIcons[i]);
                                Drawable icon1 = ScheduleNoteActivity.this.getResources().getDrawable(R.mipmap.selected);
                                icon1.setBounds(0,0,icon1.getMinimumWidth(),icon1.getMinimumHeight());
                                icon0.setBounds(0,0,icon0.getMinimumWidth(),icon0.getMinimumHeight());
                                radios[i].setCompoundDrawables(icon0,null,icon1,null);
                            }
                            jijinlist.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    switch (checkedId){
                                        case R.id.jinji:
                                            RotaImportanceID = noteData.getResults().getRotaImportances().get(0).getID();
                                            break;
                                        case R.id.zhongda:
                                            RotaImportanceID = noteData.getResults().getRotaImportances().get(1).getID();
                                            break;
                                        case R.id.yiban:
                                            RotaImportanceID = noteData.getResults().getRotaImportances().get(2).getID();
                                            break;
                                    }
                                }
                            });
                            //状态属性的checkBox
//                            ScheduleZhuangTaiAdapter adapter2 = new ScheduleZhuangTaiAdapter(ScheduleNoteActivity.this,noteData.getResults().getRotaStatus(),zhuangtaiIcons);
//                            zhuangtailist.setAdapter(adapter2);
//                            adapter2.setDataLinister(new ScheduleZhuangTaiAdapter.OnCheckDataLinister() {
//                                @Override
//                                public void checkDataLinister(List data) {
//                                    RotaStatusID = (int) data.get(0);
//                                }
//                            });
                            for (int i = 0; i < noteData.getResults().getRotaStatus().size(); i++) {
                                radios0[i].setText(noteData.getResults().getRotaStatus().get(i).getName());
                                Drawable icon0 = ScheduleNoteActivity.this.getResources().getDrawable(zhuangtaiIcons[i]);
                                Drawable icon1 = ScheduleNoteActivity.this.getResources().getDrawable(R.mipmap.selected);
                                icon1.setBounds(0,0,icon1.getMinimumWidth(),icon1.getMinimumHeight());
                                icon0.setBounds(0,0,icon0.getMinimumWidth(),icon0.getMinimumHeight());
                                radios0[i].setCompoundDrawables(icon0,null,icon1,null);
                            }
                            zhuangtailist.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    switch (checkedId){
                                        case R.id.xinjian:
                                            RotaStatusID = noteData.getResults().getRotaStatus().get(0).getID();
                                            break;
                                        case R.id.chuli:
                                            RotaStatusID = noteData.getResults().getRotaStatus().get(1).getID();
                                            break;
                                        case R.id.wancheng:
                                            RotaStatusID = noteData.getResults().getRotaStatus().get(2).getID();
                                            break;
                                    }
                                }
                            });
                            //跳转其他分类
                            other_class.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List<Map> data = new ArrayList<Map>();
//                                    List<ScheduleNoteData.ResultsBean.OtsBean> otsClass = noteData.getResults().getOts();
                                    List<ScheduleNoteAddData.ResultsBean.OtsBean> otsClass = noteData.getResults().getOts();
                                    for (int i = 0; i < otsClass.size(); i++) {
                                        Map map = new HashMap();
                                        map.put("name",otsClass.get(i).getName());
                                        map.put("id",otsClass.get(i).getID());
                                        map.put("pinyin",otsClass.get(i).getPinyin());
                                        data.add(map);
                                    }
                                    OtherClassData classData = new OtherClassData();
                                    classData.setData(data);
                                    Intent intent = new Intent(ScheduleNoteActivity.this,ScheduleOtherClassActivity.class);
                                    intent.putExtra("data",classData);
                                    startActivityForResult(intent,Content.REQUEST_CODE3);
                                }
                            });
                            //跳转常用语
                            commonDesc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List<Map> data = new ArrayList<Map>();
//                                    List<ScheduleNoteData.ResultsBean.SentencesBean> sentences = noteData.getResults().getSentences();
                                    List<ScheduleNoteAddData.ResultsBean.SentencesBean> sentences = noteData.getResults().getSentences();
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
                                    Intent intent = new Intent(ScheduleNoteActivity.this,ScheduleCommonDescActivity.class);
                                    intent.putExtra("data",classData);
                                    startActivityForResult(intent,Content.REQUEST_CODE4);
                                }
                            });

                            //提交所有数据
                            submit_totle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List selectList = mapObjectToList(selectMap);
                                    StringBuffer categories = new StringBuffer();
                                    for (int i = 0; i < selectList.size(); i++) {
                                        ScheduleNoteAddData.ResultsBean.OfsBean hasData = (ScheduleNoteAddData.ResultsBean.OfsBean)selectList.get(i);
                                        int classid  = hasData.getID();
                                        if (i == selectList.size()-1){
                                            categories.append(classid);
                                        }else{
                                            categories.append(classid+",");
                                        }
                                    }
                                    if (selectList.size() != 0 && !others.toString().trim().equals("")){
                                        categories = categories.append(","+others.toString());
                                    }else {
                                        categories = categories.append(others.toString());
                                    }
                                    String opentime = open_time.getText().toString().trim();
                                    String requesttime = request_time.getText().toString().trim();
                                    if (opentime.equals("发生时间")){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请选择发生时间");
                                        return;
                                    }else if (requesttime.equals("请求时间")){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请选择请求时间");
                                        return;
                                    } else if (total_office.getText().toString().trim().equals("总值班科室")){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请选择总值班科室名称");
                                        return;
                                    }
                                    else if (schedule_name.getText().toString().trim().equals("科室")){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请选择科室名称");
                                        return;
                                    }else if (categories.toString().trim().equals("") && others.toString().trim().equals("")){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请选择分类");
                                        return;
                                    } else if (RotaImportanceID == 0){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请选择紧急程度");
                                        return;
                                    }
                                    else if (RotaStatusID == 0){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请选择状态属性");
                                        return;
                                    }else if (desc_info.getText().toString().trim().equals("") ){
                                        ToastUtils.showToastSafe(ScheduleNoteActivity.this,"请添加描述");
                                        return;
                                    }else {
                                        OkHttpUtils
                                                .post()
                                                .url(Content.BASE_URL+"AppRotas/RotasCreate")
                                                .addParams("OccurrenceTime",opentime)
                                                .addParams("RequestTime",requesttime)
                                                .addParams("GeneralDepartmentID",GeneralDepartmentID+"")
                                                .addParams("DepartmentID",DepartmentID+"")
                                                .addParams("RotaImportanceID",RotaImportanceID+"")
                                                .addParams("RotaStatusID",RotaStatusID+"")
                                                .addParams("Description",desc_info.getText().toString().trim())
                                                .addParams("SelectedCategories",categories.toString())
                                                .addParams("Token",PrefUtils.getString(getBaseContext(),"token",""))
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {

                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        Log.e("response",response.toString());
                                                        try {
                                                            JSONObject resultObject = new JSONObject(response);
                                                            boolean status = resultObject.optBoolean("status");
                                                            if (status){
                                                                ToastUtils.showToastSafe(ScheduleNoteActivity.this,"提交成功！");
                                                                Intent intent = new Intent(ScheduleNoteActivity.this,ScheduleNoteDisPlayActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }else {
                                                                ToastUtils.showToastSafe(ScheduleNoteActivity.this,"提交失败，请重试！");
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                });
                                    }

                                }
                            });
                        }else {
                            ToastUtils.showToastSafe(ScheduleNoteActivity.this,"参数错误");
                        }
                    }
                });
        initLinister();
    }

    private void showAllDataList(final List<ScheduleNoteAddData.ResultsBean.OfsBean> rotaCategories) {
        final List selectList = mapObjectToList(selectMap);
        selectMap.clear();
        TagAdapter adapter = new TagAdapter(rotaCategories) {
            @Override
            public View getView(FlowLayout parent, final int position, Object o) {
                View view = LayoutInflater.from(ScheduleNoteActivity.this).inflate(R.layout.checkbox_view,parent,false);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                checkBox.setText(rotaCategories.get(position).getName());
                    if (selectList != null && selectList.toString() != "[]"){
                        for (int i = 0; i <selectList.size() ; i++) {
                            ScheduleNoteAddData.ResultsBean.OfsBean selectbena = (ScheduleNoteAddData.ResultsBean.OfsBean) selectList.get(i);
                            if (rotaCategories.get(position).getID() == selectbena.getID() ){
                                checkBox.setChecked(true);
                                selectMap.put(position,rotaCategories.get(position));
                            }
                        }

                    }

                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            selectMap.put(position,rotaCategories.get(position));
                        }else {
                            selectMap.remove(position);
                        }
                    }
                });
                return view;
            }
        };
        class_select_data.setAdapter(adapter);
    }
    private List mapToList(Map<Integer, Map> data) {
        List<Map> listValue = new ArrayList<>(data.values());
        return listValue;
    }
    private List mapObjectToList(Map<Integer, Object> data) {
        List<Object> listValue = new ArrayList<>(data.values());
        return listValue;
    }
    private void initLinister() {
        //科室
        schedule_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                departMent_popupWindow.setFocusable(true);
                departMent_popupWindow.setOutsideTouchable(true);
                departMent_popupWindow.setTouchable(true);
                departMent_popupWindow .setBackgroundDrawable(new BitmapDrawable());
                departMent_popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            departMent_popupWindow.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                departMent_popupWindow.showAsDropDown(schedule_name);
            }
        });
        //总值班科室
        total_office.setOnClickListener(new View.OnClickListener() {
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
                office_popupWindow.showAsDropDown(total_office);
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleNoteActivity.this,ScheduleNoteDisPlayActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //发生时间
        open_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(ScheduleNoteActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
        request_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                //request_time.setText(dateFormat.format(calendar.getTime())+" "+timeFormat.format(calendar.getTime()));
                                request_time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                //open_time.setText(dateFormat.format(calendar.getTime())+" "+timeFormat.format(calendar.getTime()));
                open_time.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth+" "+hourOfDay+":"+minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");

    }
    //选择科室的pop数据
    private void initPopupWindow(final ScheduleNoteAddData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getDepartments().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getDepartments().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getDepartments().get(i).getID()));
            list.add(map);
        }
        View view = LayoutInflater.from(ScheduleNoteActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(ScheduleNoteActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        departMent_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                schedule_name.setText(list.get(position).get("name"));
                DepartmentID = list.get(position).get("id");
                departMent_popupWindow.dismiss();

            }
        });
    }
    //选择总值班的pop数据
    private void initTotalSchedulePopupWindow(final ScheduleNoteAddData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getGeneralDepartments().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getGeneralDepartments().get(i).getText());
            map.put("id",data.getResults().getGeneralDepartments().get(i).getValue());
            list.add(map);
        }
        View view = LayoutInflater.from(ScheduleNoteActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(ScheduleNoteActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                total_office.setText(list.get(position).get("name"));
                GeneralDepartmentID = Integer.parseInt(list.get(position).get("id"));
                if (office_popupWindow != null && office_popupWindow.isShowing()) {
                    office_popupWindow.dismiss();
                    office_popupWindow.setTouchable(false);
                    office_popupWindow.setFocusable(false);
                }

            }
        });
    }
//    public interface SelectedDataLinister{
//        public void onSelectedDataLinister(List<Map> shiftPlans);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OtherClassData otherClassData = null;
        if (data != null){
            otherClassData = (OtherClassData)data.getSerializableExtra("info");
        }

        if (otherClassData != null && requestCode == Content.REQUEST_CODE3 && resultCode == Content.RESULT_CODE3){
            if (otherClassData.getMap()!= null && otherClassData != null && !otherClassData.getMap().toString().equals("{}")){
                final List<Map> toList = mapToList(otherClassData.getMap());
                Log.e("toList",toList.toString());
                others.setLength(0);
                for (int i = 0; i < toList.size(); i++) {
                    if (i == toList.size() -1){
                        others.append(toList.get(i).get("id"));
                    }else {
                        others.append(toList.get(i).get("id")+",");
                    }
                }
                otherclass_select_data.setAdapter(new TagAdapter(toList) {
                    @Override
                    public View getView(FlowLayout parent, int position, Object o) {
                        View view = LayoutInflater.from(ScheduleNoteActivity.this).inflate(R.layout.checkbox_displayview,parent,false);
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                        checkBox.setText((CharSequence) toList.get(position).get("name"));
                        return view;
                    }
                });
            }
        }else if (otherClassData != null && requestCode == Content.REQUEST_CODE4 && resultCode == Content.RESULT_CODE4){
            HashMap map = otherClassData.getMap();
            List list = mapToList(map);
            StringBuffer databuffer = new StringBuffer();
            for (int i = 0; i <list.size() ; i++) {
                Map seledata = (Map) list.get(i);
                databuffer.append(seledata.get("content")+" ");
            }
            desc_info.setText(databuffer.toString());
        }
    }
    public void deleteClassShowList(final List map){
        selectMap.clear();
        for (int i = 0; i < map.size(); i++) {
            ScheduleNoteAddData.ResultsBean.OfsBean ofsBean = new ScheduleNoteAddData.ResultsBean.OfsBean();
            ScheduleNoteAddData.ResultsBean.OfsBean hasData = (ScheduleNoteAddData.ResultsBean.OfsBean) map.get(i);
            ofsBean.setID(hasData.getID());
            ofsBean.setName(hasData.getName());
            selectMap.put(i,ofsBean);
        }
        TagAdapter adapter = new TagAdapter(map) {
            @Override
            public View getView(FlowLayout parent, final int position, Object o) {
                View view = LayoutInflater.from(ScheduleNoteActivity.this).inflate(R.layout.checkbox_displayview,parent,false);
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                ScheduleNoteAddData.ResultsBean.OfsBean bean= (ScheduleNoteAddData.ResultsBean.OfsBean) map.get(position);
                checkBox.setText(bean.getName());
                checkBox.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //Dialog确认是否删除
                        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleNoteActivity.this);
                        builder.setMessage("确定删除该数据？");
                        builder.setTitle("提示");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectMap.remove(position);
                                List listnow = mapObjectToList(selectMap);
                                StringBuffer stringBuffer = new StringBuffer();
                                for (int i = 0; i <listnow.size() ; i++) {
                                    stringBuffer.append(listnow.get(i).toString());
                                }
                                deleteClassShowList(listnow);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                        return true;
                    }
                });
                return view;
            }
        };
        class_select_data.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(ScheduleNoteActivity.this,ScheduleNoteDisPlayActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
