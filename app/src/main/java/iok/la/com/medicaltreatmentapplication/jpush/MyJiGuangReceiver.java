package iok.la.com.medicaltreatmentapplication.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;
import iok.la.com.medicaltreatmentapplication.activities.MyScheduleActivity;
import iok.la.com.medicaltreatmentapplication.activities.TotalScheduleActivity;

public class MyJiGuangReceiver extends BroadcastReceiver {
	private static final String TAG = "MyJiGuangReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		// Log.e("TAG", "onReceive - " + intent.getAction());
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			// 逻辑代码
			// String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			// String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
			// Log.e(TAG, "收到了自定义消息@@消息内容是:" + content);
			// Log.e(TAG, "收到了自定义消息@@消息extra是:" + extra);
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.e(TAG,
					"收到了自定义消息。消息内容是："
							+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			Log.e(TAG, "收到了通知");
			// String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			// String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
			// Log.e(TAG, "收到了自定义消息@@消息内容是:" + content);
			// Log.e(TAG, "收到了自定义消息@@消息extra是:" + extra);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			Log.e(TAG, "用户点击打开了通知");
			String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
			JPushResultBean resultBean = getJPushReshult(extra);
			// 在这里可以自己写代码去定义用户点击后的行为
			Intent intentToResult = new Intent(context, TotalScheduleActivity.class); // 自定义打开的界面
			//intentToResult.putExtra("resultBean", resultBean);
			intentToResult.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intentToResult);
		} else {
			Log.e("TAG", "Unhandled intent - " + intent.getAction());
		}

	}

	private JPushResultBean getJPushReshult(String extra) {
		Gson gson = new Gson();
		JPushResultBean jPushResultBean = gson.fromJson(extra,
				JPushResultBean.class);
		return jPushResultBean;
	}
}
