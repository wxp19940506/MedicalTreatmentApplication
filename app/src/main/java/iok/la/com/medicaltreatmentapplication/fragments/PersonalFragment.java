package iok.la.com.medicaltreatmentapplication.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.imageview.BGAImageView;
import iok.la.com.medicaltreatmentapplication.BaseFragment;
import iok.la.com.medicaltreatmentapplication.LoginActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.HolidayAddActivity;
import iok.la.com.medicaltreatmentapplication.activities.MyHolidayActivity;
import iok.la.com.medicaltreatmentapplication.activities.MyNewsActivity;
import iok.la.com.medicaltreatmentapplication.activities.MyScheduleActivity;
import iok.la.com.medicaltreatmentapplication.activities.MyStudentsActivity;
import iok.la.com.medicaltreatmentapplication.activities.PersonalCommonEditActivity;
import iok.la.com.medicaltreatmentapplication.activities.PersonalGuiPeiEditActivity;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleAddXunJianActivity;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleEditJiaoJieActivity;
import iok.la.com.medicaltreatmentapplication.activities.SettingPersonIconActivity;
import iok.la.com.medicaltreatmentapplication.activities.UpdatePasswordActivity;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/10 0010.
 */

public class PersonalFragment extends BaseFragment {
    @BindView(R.id.update_icon)
    LinearLayout updateIcon;
    @BindView(R.id.user_info)
    RelativeLayout userInfo;
    @BindView(R.id.user_schedule)
    RelativeLayout userSchedule;
    @BindView(R.id.user_student)
    RelativeLayout userStudent;
    @BindView(R.id.user_leave)
    RelativeLayout userLeave;
    @BindView(R.id.user_news)
    RelativeLayout userNews;
    @BindView(R.id.user_collect)
    RelativeLayout userCollect;
    @BindView(R.id.user_wages)
    RelativeLayout userWages;
    @BindView(R.id.user_setting)
    RelativeLayout userSetting;
    @BindView(R.id.icon)
    BGAImageView icon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.exit)
    Button exit;
    private boolean isIsGuiPei;
    String iconUrl = "";
    private Dialog proDialog;

    @Override
    public View initView() {
        isIsGuiPei = PrefUtils.getBoolean(getContext(), "isIsGuiPei", false);
        View view = View.inflate(mActivity, R.layout.main_activity_personal, null);
        return view;
    }

    @Override
    public void initData() {
        updatePersonIcon();
        initLinister();
    }

    private void initLinister() {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定退出当前帐号？");
                builder.setTitle("退出帐号");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        proDialog = new Dialog(getActivity(),R.style.progress_dialog);
                        proDialog.setContentView(R.layout.dialog);
                        proDialog.setCancelable(true);
                        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
                        msg.setText("加载中");
                        proDialog.show();

                        OkHttpUtils
                                .get()
                                .url(Content.BASE_URL+"AppPersonelCenter/Logout?Token="+PrefUtils.getString(getContext(),"token",""))
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
                                                proDialog.dismiss();
                                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                                ToastUtils.showToastSafe(getContext(),"退出帐号成功");
                                                PrefUtils.setBoolean(getContext(),"isLogin",false);
                                            }else {
                                                ToastUtils.showToastSafe(getContext(),"退出帐号失败");
                                                PrefUtils.setBoolean(getContext(),"isLogin",true);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void updatePersonIcon() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL + "AppPersonelCenter/GetHeadImage?Token=" + PrefUtils.getString(getContext(), "token", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optBoolean("status")){
                                String names = jsonObject.optString("Name");
                                name.setText(names);
                                iconUrl = jsonObject.optString("Path");
                                if (iconUrl != null&& !iconUrl.equals(""))
                                    Picasso.with(getContext()).load(iconUrl).error(R.mipmap.wei).into(icon);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.update_icon, R.id.user_info, R.id.user_schedule, R.id.user_student, R.id.user_leave, R.id.user_news, R.id.user_collect, R.id.user_wages, R.id.user_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_icon:
                Intent intent = new Intent(getActivity(), SettingPersonIconActivity.class);
                intent.putExtra("imageUrl",iconUrl);
                startActivityForResult(intent, Content.REQUEST_CODE0);
                break;
            case R.id.user_info:
                if (isIsGuiPei) {
                    Intent intent1 = new Intent(getActivity(), PersonalCommonEditActivity.class);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(getActivity(), PersonalGuiPeiEditActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.user_schedule:
                Intent my_intent = new Intent(getActivity(), MyScheduleActivity.class);
                startActivity(my_intent);
                break;
            case R.id.user_student:
                Intent myStudent_intent = new Intent(getActivity(), MyStudentsActivity.class);
                startActivity(myStudent_intent);
                break;
            case R.id.user_leave:
//                Intent holidayIntent = new Intent(getContext(),HolidayAddActivity.class);
                Intent holidayIntent = new Intent(getContext(),MyHolidayActivity.class);
                startActivity(holidayIntent);
                break;
            case R.id.user_news:
                Intent my_news = new Intent(getActivity(), MyNewsActivity.class);
                startActivity(my_news);
                break;
            case R.id.user_collect:
                ToastUtils.showToastSafe(getContext(),"该模块功能尚未开放，敬请期待");
                break;
            case R.id.user_wages:
                ToastUtils.showToastSafe(getContext(),"该模块功能尚未开放，敬请期待");
                break;
            case R.id.user_setting:
                Intent intent1 = new Intent(getContext(),UpdatePasswordActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePersonIcon();
    }
}
