package iok.la.com.medicaltreatmentapplication;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import iok.la.com.medicaltreatmentapplication.jpush.MyJiGuangReceiver;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class BaseApplication extends Application {
    private List<Activity> activitys;
    public Map<Integer,Map>  problemNotes;
    public BaseApplication() {
        activitys = new LinkedList();
    }
    private static BaseApplication instance;

    /**
     * 单例模式中获取唯一的Application实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
            instance.problemNotes = new LinkedHashMap<>();
        }
        return instance;

    }
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化极光
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if(!activitys.contains(activity)){
                activitys.add(activity);
            }
        }else{
            activitys.add(activity);
        }

    }

    // 遍历所有Activity并finish
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
