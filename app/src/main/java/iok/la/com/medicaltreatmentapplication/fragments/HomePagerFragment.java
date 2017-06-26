package iok.la.com.medicaltreatmentapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseFragment;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.MyScheduleActivity;
import iok.la.com.medicaltreatmentapplication.activities.OfficeScheduleActivity;
import iok.la.com.medicaltreatmentapplication.activities.TotalScheduleActivity;

/**
 * Created by Administrator on 2016/12/9 0009.
 * 首页的Fragment
 */

public class HomePagerFragment extends BaseFragment {
    @BindView(R.id.mySchedule)
    ImageButton mySchedule;//我的排班
    @BindView(R.id.officeSchedule)
    ImageButton officeSchedule;//科室排班
    @BindView(R.id.totalSchedule)
    ImageButton totalSchedule;//总值班

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.main_activity_homepager, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData(){};


    @OnClick({R.id.mySchedule, R.id.officeSchedule, R.id.totalSchedule})
    public void onClick(View view) {
        switch (view.getId()) {
            //我的排班
            case R.id.mySchedule:
                Intent intent_my = new Intent(mActivity, MyScheduleActivity.class);
                startActivity(intent_my);
                break;
            //科室排班
            case R.id.officeSchedule:
                Intent intent_office = new Intent(mActivity, OfficeScheduleActivity.class);
                startActivity(intent_office);
                break;
            //总值班
            case R.id.totalSchedule:
                Intent intent_total = new Intent(mActivity, TotalScheduleActivity.class);
                startActivity(intent_total);
                break;
        }
    }
}
