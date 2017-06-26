package iok.la.com.medicaltreatmentapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import iok.la.com.medicaltreatmentapplication.fragments.ContactFragment;
import iok.la.com.medicaltreatmentapplication.fragments.HomePagerFragment;
import iok.la.com.medicaltreatmentapplication.fragments.PersonalFragment;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TranslucentStatusUtil;
import iok.la.com.medicaltreatmentapplication.widget.NoScrollViewPager;
import okhttp3.Call;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_viewpager)
    NoScrollViewPager mainViewpager;
    @BindView(R.id.bottom_radiogroup)
    RadioGroup bottomRadiogroup;
    @BindView(R.id.flag)
    TextView flag;
    private TextView title;
    private List<BaseFragment> fragments;
    private String[] titles;


    @Override
    public int getLayoutStyle() {
        return R.layout.activity_main;//布局
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_main;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        PrefUtils.setBoolean(getBaseContext(),"isload",true);
        if (PrefUtils.getBoolean(getBaseContext(),"isload",true))
        isloadNewApk();
        title = (TextView) findViewById(R.id.title);
        fragments = new ArrayList<>();
        titles = new String[]{"首页","通讯录","个人中心"};
    }

    @Override
    public void initData() {
        initFragments();//初始化ViewPager适配器数据
        initPagerAdapter();//绑定ViewPager适配器
        initLinister();//设置监听
        loadData();

    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/IsHasUnReadMessage?Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optBoolean("status")){
                                flag.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initLinister() {
        bottomRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
               switch (checkid){
                   case R.id.home_btn:
                       mainViewpager.setCurrentItem(0);
                       title.setText(titles[0]);
                       break;
                   case R.id.contact_btn:
                       mainViewpager.setCurrentItem(1);
                       title.setText(titles[1]);
                       break;
                   case R.id.personal_btn:
                       mainViewpager.setCurrentItem(2);
                       title.setText(titles[2]);
                       break;
               }

            }
        });
    }

    private void initPagerAdapter() {
        PagerAdapter adapter = new PagersAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);
    }

    private void initFragments() {
        //初始化ViewPager适配器数据
        fragments.add(new HomePagerFragment());
        fragments.add(new ContactFragment());
        fragments.add(new PersonalFragment());
    }
    private class PagersAdapter extends FragmentPagerAdapter {

        public PagersAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
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
                            if (!getVersionName().equals(resultObject.optString("VersionCode")) && !resultObject.optString("VersionCode").equals("")){
                                showUpdataDialog(path);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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
//                LoginMain();
                PrefUtils.setBoolean(getBaseContext(),"isload",false);
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
