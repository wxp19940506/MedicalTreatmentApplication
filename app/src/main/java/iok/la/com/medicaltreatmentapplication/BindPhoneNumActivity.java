package iok.la.com.medicaltreatmentapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.bean.PhoneStatus;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TranslucentStatusUtil;
import iok.la.com.medicaltreatmentapplication.util.Content;
import okhttp3.Call;

public class BindPhoneNumActivity extends AppCompatActivity {
    @BindView(R.id.phonenum)
    EditText phonenum;
    @BindView(R.id.yzcode)
    EditText yzcode;
    @BindView(R.id.getyzcode)
    TextView getyzcode;
    @BindView(R.id.submit)
    Button submit;
    int i = 30;
    private Dialog proDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bind_phone_num);
        ButterKnife.bind(this);
        TranslucentStatusUtil.setTranslucentStatus(BindPhoneNumActivity.this,R.id.activity_bind_phone_num,R.color.titlebar_background);
        String getPhoneUrl = Content.BASE_URL+"AppLogin/AppValidationCode";
        OkHttpUtils
                .post()
                .url(getPhoneUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });
    }


    @OnClick({R.id.phonenum, R.id.yzcode, R.id.getyzcode, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phonenum:
                break;
            case R.id.yzcode:
                break;
            case R.id.getyzcode:
                break;
            case R.id.submit:
                proDialog = new Dialog(BindPhoneNumActivity.this,R.style.progress_dialog);
                proDialog.setContentView(R.layout.dialog);
                proDialog.setCancelable(true);
                proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
                msg.setText("加载中");
                proDialog.show();
                String postCode = Content.BASE_URL+"AppLogin/AppValidationCodeSubmit";
                OkHttpUtils
                        .post()
                        .url(postCode)
                        .addParams("ValidationCode",yzcode.getText().toString()+"")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                proDialog.dismiss();
                                Gson gson = new Gson();
                                PhoneStatus  phoneStatus= gson.fromJson(response,PhoneStatus.class);
                                if (phoneStatus.isStatus()){
                                    Intent intent = new Intent(BindPhoneNumActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                break;
        }
    }

}
