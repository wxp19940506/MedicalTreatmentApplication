package iok.la.com.medicaltreatmentapplication.activities;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.NewsListAdapter;
import iok.la.com.medicaltreatmentapplication.bean.MyNewsData;
import iok.la.com.medicaltreatmentapplication.bean.MyStudentListBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.RefreshListView;
import okhttp3.Call;

public class MyNewsActivity extends BaseActivity {
    @BindView(R.id.message_count)
    TextView messageCount;
    @BindView(R.id.my_newslist)
    RefreshListView myNewslist;
    private Context context;
    private int page = 1;
    Dialog proDialog;
    List<MyNewsData.ResultsBean.MsgsBean> msgs;
    NewsListAdapter adapter;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_my_news;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_my_news;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(MyNewsActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        context = this;
    }

    @Override
    public void initData() {
        loadData();
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/MyMessage?page="+page+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyNewsData newsData = new Gson().fromJson(response,MyNewsData.class);
                        if (newsData.isStatus()){
                            proDialog.dismiss();
                            int notReadCount = newsData.getResults().getNotReadCount();
                            if (notReadCount == 0){
                                messageCount.setVisibility(View.GONE);
                            }else {
                                messageCount.setVisibility(View.VISIBLE);
                                messageCount.setText(newsData.getResults().getNotReadCount()+"");
                            }
                            msgs = newsData.getResults().getMsgs();
                            adapter = new NewsListAdapter(MyNewsActivity.this,msgs);
                            myNewslist.setAdapter(adapter);
                            myNewslist.setRefreshListener(new RefreshListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    //pager++;
                                }

                                @Override
                                public void onLoadMore() {
                                    page++;
                                    loadUpdateData();
                                }
                            });
                        }else {
                            page--;
                            myNewslist.onRefreshComplete();
                            ToastUtils.showToastSafe(MyNewsActivity.this,"没有其他数据");
                        }
                    }
                });
    }

    private void loadUpdateData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/MyMessage?page="+page+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyNewsData newsData = new Gson().fromJson(response,MyNewsData.class);
                        if (newsData.isStatus()){
                            List<MyNewsData.ResultsBean.MsgsBean> msgss = newsData.getResults().getMsgs();
                            if (msgss != null && msgss.toString() !="[]"&&page!= 1){
                                for (int i = 0; i <msgss.size() ; i++) {
                                    msgs.add(msgss.get(i));
                                }
                                //showSelectScheduleData(rotas);
                                adapter.notifyDataSetChanged();
                                myNewslist.onRefreshComplete();
                            }else {
                                page -- ;
                                myNewslist.onRefreshComplete();
                                ToastUtils.showToastSafe(MyNewsActivity.this,"没有其他数据");
                            }
                            myNewslist.setRefreshListener(new RefreshListView.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                }

                                @Override
                                public void onLoadMore() {
                                    page ++;
                                    loadUpdateData();
                                }
                            });
                        }else {
                            page -- ;
                            myNewslist.onRefreshComplete();
                        }
                    }
                });
    }

    @OnClick(R.id.turn_last)
    public void onClick() {
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        proDialog = new Dialog(MyNewsActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        loadData();

    }
}
