package iok.la.com.medicaltreatmentapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    public View mRootView;
    //fragment的创建
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();//获取宿主Activity对象
    }
    //初始化fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView();
        return mRootView;
    }
    // 宿主Activity的OnCreate执行结束，初始化数据
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        initData();
    }

    //初始化布局，抽象方法必须子类实现
    public abstract View initView();
    //初始化数据，抽象方法必须子类实现
    public abstract void initData();
}
