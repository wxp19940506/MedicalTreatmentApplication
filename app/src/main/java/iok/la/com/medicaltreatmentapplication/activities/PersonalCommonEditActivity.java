package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.PersonCommonData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import okhttp3.Call;

public class PersonalCommonEditActivity extends BaseActivity {
    @BindView(R.id.turn_last)
    ImageButton turnLast;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.shenhe_status)
    TextView shenheStatus;
    @BindView(R.id.peixun_major)
    TextView peixunMajor;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.person_num)
    TextView personNum;
    @BindView(R.id.person_idcard)
    TextView personIdcard;
    @BindView(R.id.telephone_num)
    TextView telephoneNum;
    @BindView(R.id.nianji)
    TextView nianji;
    @BindView(R.id.peixun_time)
    TextView peixunTime;
    @BindView(R.id.person_type)
    TextView personType;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.qq_number)
    TextView qqNumber;
    @BindView(R.id.danwei_name)
    TextView danweiName;
    @BindView(R.id.peixun_starttime)
    TextView peixunStarttime;
    @BindView(R.id.peixun_endtime)
    TextView peixunEndtime;
    @BindView(R.id.ispeixun_host)
    CheckBox ispeixunHost;
    @BindView(R.id.xietong_name)
    TextView xietongName;
    @BindView(R.id.xietong_type)
    TextView xietongType;
    @BindView(R.id.isget_zhiye)
    CheckBox isgetZhiye;
    @BindView(R.id.zige_number)
    EditText zigeNumber;
    @BindView(R.id.iszhuce_host)
    CheckBox iszhuceHost;
    @BindView(R.id.zhiye_number)
    EditText zhiyeNumber;
    @BindView(R.id.complete)
    Button complete;
    private ImageButton turn_last;
    private String[] status;
    private int eBId,nameId;
    private boolean isGetZhiYe,isZhuCeLocal;
    private String ziGeNum = "";
    private String zhiYeNum = "";
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_personal_common_edit;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_personal_common_edit;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(PersonalCommonEditActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        status = new String[]{"待审核","已审核","新建","审核未通过"};
        turn_last = (ImageButton) findViewById(R.id.turn_last);
    }

    @Override
    public void initData() {
        loadData();
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL + "AppPersonelCenter/PersonelInfo?Token=" + PrefUtils.getString(getBaseContext(), "token", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        final PersonCommonData peiData = gson.fromJson(response, PersonCommonData.class);
                        if (peiData.isStatus()) {
                            proDialog.dismiss();
                            shenheStatus.setText(status[peiData.getResults().getStatus()]);
                            peixunMajor.setText(peiData.getResults().getEBName());
                            eBId = peiData.getResults().getEBId();
                            name.setText(peiData.getResults().getName());
                            nameId = peiData.getResults().getId();
                            personNum.setText(peiData.getResults().getWorkNo());
                            personIdcard.setText(peiData.getResults().getIdCardNo());
                            telephoneNum.setText(peiData.getResults().getPhone());
                            nianji.setText(peiData.getResults().getGrade());
                            peixunTime.setText(peiData.getResults().getTrainingLimit());
                            personType.setText(peiData.getResults().getEmployeeType());
                            email.setText(peiData.getResults().getEmail());
                            if (peiData.getResults().getQQ() != null)
                            qqNumber.setText(peiData.getResults().getQQ()+"");
                            danweiName.setText(peiData.getResults().getUnit());
                            if (peiData.getResults().getTrainingStart() != null && peiData.getResults().getTrainingStart().contains("(") &&peiData.getResults().getTrainingStart().contains(")")){
                                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String startDate = peiData.getResults().getTrainingStart();
                                String date = startDate.substring(startDate.indexOf("(")+1,startDate.indexOf(")"));
                                String xunJianTimeText = sdf.format(new Date(Long.parseLong(date)));
                                peixunStarttime.setText(xunJianTimeText);
                            }
                            if (peiData.getResults().getTrainingEnd() != null && peiData.getResults().getTrainingStart().contains("(") &&peiData.getResults().getTrainingStart().contains(")")){
                                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String endDate = peiData.getResults().getTrainingEnd();
                                String date = endDate.substring(endDate.indexOf("(")+1,endDate.indexOf(")"));
                                String xunJianTimeText = sdf.format(new Date(Long.parseLong(date)));
                                peixunEndtime.setText(xunJianTimeText);
                            }


                            if (peiData.getResults().getXieTongUnitName() != null)
                                xietongName.setText(peiData.getResults().getXieTongUnitName()+"");
                            if (peiData.getResults().getXieTongUnitLevel() != null)
                                xietongType.setText(peiData.getResults().getXieTongUnitLevel()+"");
                            if (peiData.getResults().getZiGeZhengShuNo() != null)
                                zigeNumber.setText(peiData.getResults().getZiGeZhengShuNo()+"");
                            if (peiData.getResults().getZhiYeZhuCeNo() != null)
                                zhiyeNumber.setText(peiData.getResults().getZhiYeZhuCeNo()+"");
                            Drawable ispxHost = getResources().getDrawable(R.mipmap.check);
                            ispxHost.setBounds(0, 0, ispxHost.getMinimumWidth(), ispxHost.getMinimumHeight());
                            Drawable nopxHost = getResources().getDrawable(R.mipmap.checkbox);
                            nopxHost.setBounds(0, 0, nopxHost.getMinimumWidth(), nopxHost.getMinimumHeight());
                            if (peiData.getResults().isIsXieTong()){
                                ispeixunHost.setCompoundDrawables(null,null,ispxHost ,null);
                            }else {
                                ispeixunHost.setCompoundDrawables(null,null,nopxHost ,null);
                            }
                            if (peiData.getResults().isIsGetZhiYeZhengShu()){
                                isgetZhiye.setChecked(true);
                                isGetZhiYe = true;
                            }else {
                                isgetZhiye.setChecked(false);
                                isGetZhiYe = false;
                            }
                            if (peiData.getResults().isIsFirstOrAlterRegisterBase()){
                                iszhuceHost.setChecked(true);
                                isZhuCeLocal = true;
                            }else {
                                iszhuceHost.setChecked(false);
                                isZhuCeLocal = false;
                            }
                            //checkBox监听事件
                            iszhuceHost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    isZhuCeLocal = isChecked;
                                }
                            });
                            isgetZhiye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    isGetZhiYe = isChecked;
                                }
                            });
                        } else {

                            ToastUtils.showToastSafe(PersonalCommonEditActivity.this, "参数错误");
                        }
                    }
                });
    }

    @OnClick(R.id.complete)
    public void onClick() {
        proDialog.show();
        ziGeNum = zigeNumber.getText().toString().trim();
        zhiYeNum = zhiyeNumber.getText().toString().trim();
        OkHttpUtils
                .post()
                .url(Content.BASE_URL+"AppPersonelCenter/GuiPeiEmployeePersonelInfoSave")
                .addParams("Id",nameId+"")
                .addParams("IsGetZhiYeZhengShu",isGetZhiYe+"")
                .addParams("ZiGeZhengShuNo",ziGeNum)
                .addParams("IsFirstOrAlterRegisterBase",isZhuCeLocal+"")
                .addParams("ZhiYeZhuCeNo",zhiYeNum)
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
                                ToastUtils.showToastSafe(PersonalCommonEditActivity.this,"保存成功");
                                finish();
                            }else {
                                ToastUtils.showToastSafe(PersonalCommonEditActivity.this,"保存失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
