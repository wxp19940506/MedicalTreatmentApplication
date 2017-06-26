package iok.la.com.medicaltreatmentapplication.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;

public class TotalScheduleActivity extends BaseActivity {
    private ImageButton turn_last;
    private RelativeLayout schedule_note,schedule_xunjian,schedule_jiaojie,first_notify,totle_info;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_total_schedule;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_total_schedule;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        schedule_note = (RelativeLayout) findViewById(R.id.schedule_note);
        schedule_xunjian = (RelativeLayout) findViewById(R.id.schedule_xunjian);
        schedule_jiaojie = (RelativeLayout) findViewById(R.id.schedule_jiaojie);
        first_notify = (RelativeLayout) findViewById(R.id.first_notify);
        totle_info = (RelativeLayout) findViewById(R.id.totle_info);
    }

    @Override
    public void initData() {
        initLinister();
    }

    private void initLinister() {
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        schedule_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(TotalScheduleActivity.this,ScheduleNoteActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(TotalScheduleActivity.this,ScheduleNoteDisPlayActivity.class);
                startActivity(intent);

            }
        });
        schedule_xunjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalScheduleActivity.this,ScheduleXunJianActivity.class);
                startActivity(intent);
            }
        });
        schedule_jiaojie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalScheduleActivity.this,ScheduleJiaoJieActivity.class);
                startActivity(intent);
            }
        });
        first_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToastSafe(TotalScheduleActivity.this,"该模块功能尚未开放，敬请期待");

            }
        });
        totle_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToastSafe(TotalScheduleActivity.this,"该模块功能尚未开放，敬请期待");

            }
        });
    }
}
