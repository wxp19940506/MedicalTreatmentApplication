package iok.la.com.medicaltreatmentapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import iok.la.com.medicaltreatmentapplication.activities.OfficeScheduleDetailActivity;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleNoteActivity;
import iok.la.com.medicaltreatmentapplication.jpush.MyJiGuangReceiver;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.TranslucentStatusUtil;
import okhttp3.Call;

public abstract class BaseActivity extends AppCompatActivity {
    public Activity mActivity;
    public View rootView;
    public BaseApplication application ;
    public String token;
    private MyJiGuangReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "iok.la.com.medicaltreatmentapplication.MESSAGE_RECEIVED_ACTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //isloadNewApk();
        registerMessageReceiver();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutStyle());
        application = BaseApplication.getInstance();
        application.addActivity(mActivity);
        init();
        token = PrefUtils.getString(getBaseContext(),"token","");
        ButterKnife.bind(mActivity);
        //设置沉浸式状态栏
        TranslucentStatusUtil.setTranslucentStatus(mActivity, getLayoutId(), R.color.titlebar_background);
        rootView = LayoutInflater.from(mActivity).inflate(getLayoutStyle(),null);
        initView();
        initData();
    }
    public abstract int getLayoutStyle();
    public abstract int getLayoutId();
    public abstract void init();
    public abstract void initView();
    public abstract void initData();
    public void registerMessageReceiver() {
        mMessageReceiver = new MyJiGuangReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);

    }

}
