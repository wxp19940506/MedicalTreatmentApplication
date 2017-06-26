package iok.la.com.medicaltreatmentapplication.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import okhttp3.Call;

public class UpdatePasswordActivity extends BaseActivity {


    @BindView(R.id.turn_last)
    ImageButton turnLast;
    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.enable)
    Button enable;
    @BindView(R.id.isshow)
    CheckBox isshow;
    private boolean isShow = false;

    @Override
    public int getLayoutStyle() {
        return R.layout.activity_update_password;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_update_password;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        isshow.setChecked(isShow);
    }

    @Override
    public void initData() {
        initLinister();
    }

    private void initLinister() {
        isshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShow = isChecked;
                isshow.setChecked(isShow);
                if (isShow){
                    oldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    newPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    oldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    newPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }


    @OnClick({R.id.turn_last, R.id.enable})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turn_last:
                finish();
                break;
            case R.id.enable:
                if (oldPassword.getText().toString().trim().equals("")){
                    ToastUtils.showToastSafe(UpdatePasswordActivity.this,"请输入旧密码");
                }else if (newPassword.getText().toString().trim().equals("")){
                    ToastUtils.showToastSafe(UpdatePasswordActivity.this,"请输入新密码");
                }else {
                    OkHttpUtils
                            .post()
                            .url(Content.BASE_URL+"AppPersonelCenter/ModifyPassword")
                            .addParams("oldPass",oldPassword.getText().toString().trim())
                            .addParams("newPass",newPassword.getText().toString().trim())
                            .addParams("Token", PrefUtils.getString(getBaseContext(),"token",""))
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    Log.e("mima",response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean isStatus = jsonObject.optBoolean("status");
                                        String msg = (String) jsonObject.opt("ErrorMsg");
                                        if (isStatus){
                                            finish();
                                            ToastUtils.showToastSafe(UpdatePasswordActivity.this,"修改成功");
                                        }else {
                                            alertDialogShow(msg);
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
    private void alertDialogShow(String text) {
        //Dialog启示
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePasswordActivity.this);
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
}
