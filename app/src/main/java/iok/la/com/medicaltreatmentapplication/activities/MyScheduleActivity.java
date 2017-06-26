package iok.la.com.medicaltreatmentapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;

public class MyScheduleActivity extends BaseActivity {

    @Override
    public int getLayoutStyle() {
        return R.layout.activity_my_schedule;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_my_schedule;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.turn_last, R.id.my_paiban, R.id.schedule_paiban, R.id.schedule_paiban_today, R.id.other_schedule_paiban, R.id.my_manage_schedule_paiban})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turn_last:
                finish();
                break;
            case R.id.my_paiban:
                Intent myIntent = new Intent(MyScheduleActivity.this,MyScheduleDateActivity.class);
                startActivity(myIntent);
                break;
            case R.id.schedule_paiban:
                Intent scheduleIntent = new Intent(MyScheduleActivity.this,SchedulePaibanActivity.class);
                startActivity(scheduleIntent);
                break;
            case R.id.schedule_paiban_today:
                Intent scheduleTodayIntent = new Intent(MyScheduleActivity.this,SchedulePaibanTodayActivity.class);
                startActivity(scheduleTodayIntent);
                break;
            case R.id.other_schedule_paiban:
                Intent scheduleOtherIntent = new Intent(MyScheduleActivity.this,SchedulePaibanOtherActivity.class);
                startActivity(scheduleOtherIntent);
                break;
            case R.id.my_manage_schedule_paiban:
                Intent scheduleManageIntent = new Intent(MyScheduleActivity.this,SchedulePaibanManageActivity.class);
                startActivity(scheduleManageIntent);
                break;
        }
    }
}
