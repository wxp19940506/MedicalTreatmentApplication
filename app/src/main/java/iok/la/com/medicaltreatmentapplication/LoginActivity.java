package iok.la.com.medicaltreatmentapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleNoteEditActivity;
import iok.la.com.medicaltreatmentapplication.bean.LoginStatueData;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.util.TranslucentStatusUtil;
import iok.la.com.medicaltreatmentapplication.util.Content;
import okhttp3.Call;

import static iok.la.com.medicaltreatmentapplication.R.id.info;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    private String usernames,passwords;
    private boolean isLogin = false;
    private Dialog proDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        init();//初始化
        //isloadNewApk();
        isLogin = PrefUtils.getBoolean(getBaseContext(),"isLogin",false);
        if (isLogin){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void isloadNewApk() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppLogin/VersionUpdate")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject resultObject = (JSONObject) jsonObject.opt("results");
                            String path = resultObject.optString("Path");
                            if (!getVersionName().equals(resultObject.optString("VersionCode"))&& !resultObject.optString("VersionCode").equals("")){
                                showUpdataDialog(path);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void init() {
        //设置沉浸式状态栏
        TranslucentStatusUtil.setTranslucentStatus(LoginActivity.this, R.id.activity, R.color.titlebar_background);
        String saveusername = PrefUtils.getString(getBaseContext(), "username", "");
        String saveuserpassword = PrefUtils.getString(getBaseContext(), "password", "");
        username.setText(saveusername);
        password.setText(saveuserpassword);
    }

    @OnClick({R.id.title, R.id.username, R.id.password, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title:
                break;
            case R.id.username:
                break;
            case R.id.password:
                break;
            case R.id.login:
                if (username.getText().toString().trim().equals("") ){
                    ToastUtils.showToastSafe(LoginActivity.this,"用户名不能为空");
                    return;
                }else if (password.getText().toString().trim() .equals("") ){
                    ToastUtils.showToastSafe(LoginActivity.this,"密码不能为空");
                    return;
                }else {
                    proDialog = new Dialog(LoginActivity.this,R.style.progress_dialog);
                    proDialog.setContentView(R.layout.dialog);
                    proDialog.setCancelable(true);
                    proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
                    msg.setText("加载中");
                    proDialog.show();
                    usernames = username.getText().toString().trim();
                    passwords = password.getText().toString().trim();
                    String url =Content.BASE_URL+"AppLogin/Login";
                    OkHttpUtils
                            .post()
                            .url(url)
                            .addParams("Username",usernames)
                            .addParams("Password", passwords)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    proDialog.dismiss();
                                    ToastUtils.showToastSafe(LoginActivity.this,"连接超时，请先连接网络！");
                                }
                                @Override
                                public void onResponse(String response, int id) {
                                    proDialog.dismiss();
                                    Gson gson = new Gson();
                                    LoginStatueData statueData = gson.fromJson(response, LoginStatueData.class);
                                    if (statueData.isStatus()){
                                        ToastUtils.showToastSafe(LoginActivity.this,"密码验证成功！");
                                        Intent intent = new Intent(LoginActivity.this,BindPhoneNumActivity.class);
                                        startActivity(intent);
                                        finish();
                                        PrefUtils.setString(getBaseContext(),"username",usernames);
                                        PrefUtils.setString(getBaseContext(),"password",passwords);
                                        PrefUtils.setBoolean(getBaseContext(),"isIsGuiPei",statueData.isIsGuiPei());
                                        PrefUtils.setBoolean(getBaseContext(),"isLogin",true);
                                        PrefUtils.setString(getBaseContext(),"token",statueData.getToken());
                                    }else {
                                        ToastUtils.showToastSafe(LoginActivity.this,"密码输入错误！");
                                    }
                                }
                            });
                }
                break;
        }
    }
    /*
 * 获取当前程序的版本号
 */
            private String getVersionName() throws Exception{
           //获取packagemanager的实例
                PackageManager packageManager = getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
                PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
             return packInfo.versionName;
        }
    /*
 *
 * 弹出对话框通知用户更新程序
 *
 * 弹出对话框的步骤：
 *  1.创建alertDialog的builder.
 *  2.要给builder设置属性, 对话框的内容,样式,按钮
 *  3.通过builder 创建一个对话框
 *  4.对话框show()出来
 */
    protected void showUpdataDialog(final String pathApk) {
        AlertDialog.Builder builer = new AlertDialog.Builder(this) ;
        builer.setTitle("版本升级");
        builer.setMessage("有新版本需要升级，是否升级？");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk(pathApk);
            }
        });
        //当点取消按钮时进行登录
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
//                LoginMain();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }
    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk(String pathApkUrl) {
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        try {

            OkHttpUtils
                    .get()
                    .url(pathApkUrl)
                    .build()
                    .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),getPackageName()+getVersionName()+new Date().getTime()+".apk") {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(File response, int id) {
                            pd.dismiss();
                            installApk(response);
                        }

                        @Override
                        public void inProgress(float progress, long total, int id) {
                            pd.setProgress((int) (progress * 100));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //安装apk
            protected void installApk(File file) {
                Intent intent = new Intent();
            //执行动作
                 intent.setAction(Intent.ACTION_VIEW);
            //执行的数据类型
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }

}
