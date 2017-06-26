package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.StudentPersonInfoBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import okhttp3.Call;

public class StudentDetailInfoActivity extends BaseActivity {
    @BindView(R.id.student_name)
    TextView studentName;
    @BindView(R.id.person_num)
    TextView personNum;
    @BindView(R.id.person_idcard)
    TextView personIdcard;
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
    TextView zigeNumber;
    @BindView(R.id.iszhuce_host)
    CheckBox iszhuceHost;
    @BindView(R.id.zhiye_number)
    TextView zhiyeNumber;
    @BindView(R.id.peixun_major)
    TextView peixun_major;
    private int employeeid;
    private boolean isXieTong,isGetZhiYe,isZhuCeHost;
    Dialog proDialog;

    @Override
    public int getLayoutStyle() {
        return R.layout.activity_student_detail_info;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_student_detail_info;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(StudentDetailInfoActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        employeeid = getIntent().getIntExtra("employeeid", 1209);
    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL + "AppPersonelCenter/MyStudentDetails?employeeid=" + employeeid + "&Token=" + PrefUtils.getString(getBaseContext(), "token", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        StudentPersonInfoBean personInfoBean = gson.fromJson(response, StudentPersonInfoBean.class);
                        if (personInfoBean.isStatus()) {
                            proDialog.dismiss();
                            studentName.setText(personInfoBean.getResults().getEmp().getName());
                            personNum.setText(personInfoBean.getResults().getEmp().getWorkNo());
                            personIdcard.setText(personInfoBean.getResults().getEmp().getIdCardNo());
                            peixun_major.setText(personInfoBean.getResults().getEmp().getBaseName());
                            nianji.setText(personInfoBean.getResults().getEmp().getGrade());
                            peixunTime.setText(personInfoBean.getResults().getEmp().getEmployeeType());
                            personType.setText(personInfoBean.getResults().getEmp().getWorkNo());
                            email.setText(personInfoBean.getResults().getEmp().getEmail());
                            qqNumber.setText(personInfoBean.getResults().getEmp().getQQ());
                            danweiName.setText(personInfoBean.getResults().getEmp().getUnit());
                            peixunStarttime.setText(personInfoBean.getResults().getEmp().getTrainingStart());
                            peixunEndtime.setText(personInfoBean.getResults().getEmp().getTrainingEnd());
                            xietongName.setText(personInfoBean.getResults().getEmp().getXieTongUnitName());
                            xietongType.setText(personInfoBean.getResults().getEmp().getXieTongUnitLevel());
                            zigeNumber.setText(personInfoBean.getResults().getEmp().getZiGeZhengShuNo());
                            zhiyeNumber.setText(personInfoBean.getResults().getEmp().getZhiYeZhuCeNo());
                            isXieTong = personInfoBean.getResults().getEmp().isIsXieTong();
                            ispeixunHost.setChecked(isXieTong);
                            isGetZhiYe = personInfoBean.getResults().getEmp().isIsGetZhiYeZhengShu();
                            isgetZhiye.setChecked(isGetZhiYe);
                            isZhuCeHost = personInfoBean.getResults().getEmp().isIsFirstOrAlterRegisterBase();
                            iszhuceHost.setChecked(isZhuCeHost);
                            ispeixunHost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    ispeixunHost.setChecked(isXieTong);
                                }
                            });
                            isgetZhiye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    isgetZhiye.setChecked(isGetZhiYe);
                                }
                            });
                            iszhuceHost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    iszhuceHost.setChecked(isZhuCeHost);
                                }
                            });
                        }
                    }
                });
    }

    @OnClick(R.id.turn_last)
    public void onClick() {
        finish();
    }
}
