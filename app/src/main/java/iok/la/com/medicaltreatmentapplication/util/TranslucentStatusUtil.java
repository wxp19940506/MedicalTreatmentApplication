package iok.la.com.medicaltreatmentapplication.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author WangXiaoPeng
 * @date 2016-12-14
 */

public class  TranslucentStatusUtil {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setTranslucentStatus(Activity mActivity,int layoutCode,int colorCode)
    {
        //判断版本是4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window win = mActivity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemStatusManager tintManager = new SystemStatusManager(mActivity);
            //打开系统状态栏控制
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorCode);//设置背景
            View layoutAll = mActivity.findViewById(layoutCode);
            //设置系统栏需要的内偏移
            layoutAll.setPadding(0, ScreenUtils.getStatusHeight(mActivity), 0, 0);
        }
    }
}
