package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.PersonGuiPeiData;
import iok.la.com.medicaltreatmentapplication.bean.PostScheduleData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import okhttp3.Call;

public class PersonalGuiPeiEditActivity extends BaseActivity {
    private ImageButton turn_last;
    private TextView schedule_name;
    private PopupWindow office_popupWindow;
    String scheduleCode="";
    String person_id="";
    String zhiyeCode="";
    String xingZCode="";
    private EditText person_name,pinyin,person_num,telephone_num,office_phone,address,idcard_num;
    private TextView zhiyezc,xingzhengjob;
    private CheckBox isrotaBox;
    private Button complete;
    private boolean isRota;
    private int department;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_personal_gui_pei_edit;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_personal_gui_pei_edit;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(PersonalGuiPeiEditActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        isrotaBox = (CheckBox) findViewById(R.id.isrota);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        schedule_name = (TextView) findViewById(R.id.schedule_name);
        person_name = (EditText) findViewById(R.id.person_name);
        zhiyezc = (TextView) findViewById(R.id.zhiyezc);
        xingzhengjob = (TextView) findViewById(R.id.xingzhengjob);
        pinyin = (EditText) findViewById(R.id.pinyin);
        person_num = (EditText) findViewById(R.id.person_num);
        telephone_num = (EditText) findViewById(R.id.telephone_num);
        office_phone = (EditText) findViewById(R.id.office_phone);
        address = (EditText) findViewById(R.id.address);
        idcard_num = (EditText) findViewById(R.id.idcard_num);
        complete = (Button) findViewById(R.id.complete);
    }

    @Override
    public void initData() {
        loadData();
        initLinister();
    }

    private void alertDialogShow(String text) {
        //Dialog启示
    AlertDialog.Builder builder = new AlertDialog.Builder(PersonalGuiPeiEditActivity.this);
    builder.setMessage(text);
    builder.setTitle("提示");
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
        builder.create().show();
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/PersonelInfo?Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final PersonGuiPeiData peiData = gson.fromJson(response,PersonGuiPeiData.class);
                        if (peiData.isStatus()){
                            proDialog.dismiss();
                            //科室
                            department = peiData.getResults().getObj().getDepartmentId();
                            List<PersonGuiPeiData.ResultsBean.DeptsBean> depts = peiData.getResults().getDepts();
                            for (int i = 0; i < depts.size(); i++) {
                                if (depts.get(i).getId() == department ){
                                    schedule_name.setText(depts.get(i).getName());
                                }
                            }
                            schedule_name.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initPopupWindow(schedule_name,peiData);
                                }
                            });
                            //员工姓名
                            person_name.setText(peiData.getResults().getObj().getName());
                            person_id = peiData.getResults().getObj().getId()+"";
                            //执业职称
                            zhiyeCode = peiData.getResults().getObj().getTitleID()+"";
                            if (Integer.parseInt(zhiyeCode.trim()) == 0){
                                zhiyezc.setText("");
                                zhiyeCode = 0+"";
                            }else {
                                for (int i = 0; i < peiData.getResults().getTs().size(); i++) {
                                    if (Integer.parseInt(zhiyeCode.trim()) ==peiData.getResults().getTs().get(i).getID() ){
                                        zhiyezc.setText(peiData.getResults().getTs().get(i).getName());
                                    }
                                }
                            }
                            zhiyezc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initZhiYePopupWindow(zhiyezc,peiData);
                                }
                            });
                            //行政职务
                            xingZCode = peiData.getResults().getObj().getDutyID()+"";
                            if (Integer.parseInt(xingZCode.trim()) == 0){
                                xingzhengjob.setText("");
                                xingZCode = 0+"";
                            }else {
                                for (int i = 0; i < peiData.getResults().getDs().size(); i++) {
                                    if (Integer.parseInt(xingZCode.trim()) ==peiData.getResults().getDs().get(i).getID() ){
                                        xingzhengjob.setText(peiData.getResults().getDs().get(i).getName());
                                    }
                                }
                            }
                            xingzhengjob.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    initXingZhengPopupWindow(xingzhengjob,peiData);
                                }
                            });
                            //拼音
                            if (peiData.getResults().getObj().getPinyin() != null){
                                pinyin.setText(peiData.getResults().getObj().getPinyin().toString());
                            }else {
                                pinyin.setText("");
                            }
                            //是否总值班人员
                            isrotaBox.setChecked(peiData.getResults().getObj().isIsRota());
                            isRota = peiData.getResults().getObj().isIsRota();
                            isrotaBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    isRota = isChecked;
                                }
                            });
                            //工号
                            if (peiData.getResults().getObj().getWorkNo()!= null)
                                person_num.setText(peiData.getResults().getObj().getWorkNo());
                            //手机号码
                            if (peiData.getResults().getObj().getCellPhone() != null)
                                telephone_num.setText(peiData.getResults().getObj().getCellPhone()+"");
                            //办公电话
                            if (peiData.getResults().getObj().getOfficePhone() != null)
                                office_phone.setText(peiData.getResults().getObj().getOfficePhone()+"");
                            //地址
                            if (peiData.getResults().getObj().getAddress() != null)
                                address.setText(peiData.getResults().getObj().getAddress()+"");
                            //身份证号
                            if (peiData.getResults().getObj().getIdentityNo() != null)
                                idcard_num.setText(peiData.getResults().getObj().getIdentityNo()+"");
                        }
                    }
                });
    }

    private void initLinister() {
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (person_name.getText().toString().trim().equals("")){
                    ToastUtils.showToastSafe(PersonalGuiPeiEditActivity.this,"请输入员工姓名");
                }else if (person_num.getText().toString().trim().equals("")){
                    ToastUtils.showToastSafe(PersonalGuiPeiEditActivity.this,"请输入工号");
                }else if (telephone_num.getText().toString().trim().equals("")){
                    ToastUtils.showToastSafe(PersonalGuiPeiEditActivity.this,"请输入手机号码");
                }else {
                    proDialog.show();
                    OkHttpUtils
                            .post()
                            .url(Content.BASE_URL+"AppPersonelCenter/EmployeePersonelInfoSave")
                            .addParams("id",person_id)
                            .addParams("Name",person_name.getText().toString().trim())
                            .addParams("WorkNo",person_num.getText().toString().trim())
                            .addParams("CellPhone",telephone_num.getText().toString().trim())
                            .addParams("officePhone",office_phone.getText().toString().trim())
                            .addParams("Address",address.getText().toString().trim())
                            .addParams("IdentityNo",idcard_num.getText().toString().trim())
                            .addParams("Pinyin",pinyin.getText().toString().trim())
                            .addParams("DepartmentId",department+"")
                            .addParams("TitleID",zhiyeCode)
                            .addParams("DutyID",xingZCode)
                            .addParams("IsRota",isRota+"")
                            .addParams("Token",PrefUtils.getString(getBaseContext(),"token",""))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean isStatus = jsonObject.optBoolean("status");
                                        if (isStatus){
                                            proDialog.dismiss();
                                            ToastUtils.showToastSafe(PersonalGuiPeiEditActivity.this,"保存成功");
                                            finish();
                                        }else {
                                            alertDialogShow(jsonObject.optString("ErrorMsg"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }

            }
        });
    }
    //执业职称pop
    private void initZhiYePopupWindow(final TextView text, final PersonGuiPeiData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getTs().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getTs().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getTs().get(i).getID()));
            list.add(map);
        }
        View view = LayoutInflater.from(PersonalGuiPeiEditActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(PersonalGuiPeiEditActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, text.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        office_popupWindow.setFocusable(true);
        office_popupWindow.setOutsideTouchable(true);
        office_popupWindow.setTouchable(true);
        office_popupWindow.showAsDropDown(text);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                zhiyeCode = list.get(position).get("id");
                if (office_popupWindow != null && office_popupWindow.isShowing()) {
                    office_popupWindow.dismiss();
                    office_popupWindow.setTouchable(false);
                    office_popupWindow.setFocusable(false);
                }

            }
        });
    }
    //科室pop
    private void initPopupWindow(final TextView text, final PersonGuiPeiData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getDepts().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getDepts().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getDepts().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(PersonalGuiPeiEditActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(PersonalGuiPeiEditActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, text.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        office_popupWindow.setFocusable(true);
        office_popupWindow.setOutsideTouchable(true);
        office_popupWindow.setTouchable(true);
        office_popupWindow.showAsDropDown(text);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                department = Integer.parseInt(list.get(position).get("id"));
                if (office_popupWindow != null && office_popupWindow.isShowing()) {
                    office_popupWindow.dismiss();
                    office_popupWindow.setTouchable(false);
                    office_popupWindow.setFocusable(false);
                }

            }
        });
    }
    //行政职务pop
    private void initXingZhengPopupWindow(final TextView text, final PersonGuiPeiData data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getDs().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getDs().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getDs().get(i).getID()));
            list.add(map);
        }
        View view = LayoutInflater.from(PersonalGuiPeiEditActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(PersonalGuiPeiEditActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, text.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        office_popupWindow.setFocusable(true);
        office_popupWindow.setOutsideTouchable(true);
        office_popupWindow.setTouchable(true);
        office_popupWindow.showAsDropDown(text);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                xingZCode = list.get(position).get("id");
                if (office_popupWindow != null && office_popupWindow.isShowing()) {
                    office_popupWindow.dismiss();
                    office_popupWindow.setTouchable(false);
                    office_popupWindow.setFocusable(false);
                }

            }
        });
    }
    //Dialog确认是否删除
//    AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleNoteActivity.this);
//    builder.setMessage("确定删除该数据？");
//    builder.setTitle("提示");
//    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            selectMap.remove(position);
//            List listnow = mapObjectToList(selectMap);
//            StringBuffer stringBuffer = new StringBuffer();
//            for (int i = 0; i <listnow.size() ; i++) {
//                stringBuffer.append(listnow.get(i).toString());
//            }
//            Log.e("listnow2",stringBuffer.toString());
//            deleteClassShowList(listnow);
//        }
//    });
//    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            dialog.dismiss();
//        }
//    });
//    builder.create().show();
//    return true;
//}
}
