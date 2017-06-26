package iok.la.com.medicaltreatmentapplication.util;


import android.os.Handler;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class ThreadUtils {
    //子线程执行task
    public static void runInThread(Runnable task){
        new Thread(task).start();
    }
    //主线程执行
    public static Handler handler = new Handler() ;
    public static void runInUThread(Runnable task){
        handler.post(task);
    }
}
