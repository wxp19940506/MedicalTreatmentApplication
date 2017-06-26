package iok.la.com.medicaltreatmentapplication.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class ToastUtils {
    public static  void showToastSafe(final Context context, final String text){
        ThreadUtils.runInUThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,text, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
