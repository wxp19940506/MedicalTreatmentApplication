package iok.la.com.medicaltreatmentapplication.activities;

import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.NewsDetailData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.widget.MyWebView;
import okhttp3.Call;

public class MyNewsDetailActivity extends BaseActivity {

    @BindView(R.id.new_details)
    WebView new_details;
    @BindView(R.id.turn_last)
    ImageButton turn_last;
    private int msgid;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_my_news_detail;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_my_news_detail;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        msgid = getIntent().getIntExtra("msgid",90);
    }

    @Override
    public void initData() {
        loadData();
        initLinister();
    }

    private void initLinister() {
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/MyMessageDetails?msgid="+msgid+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        NewsDetailData newsDetail = new Gson().fromJson(response,NewsDetailData.class);
                        if (newsDetail.isStatus()){
                            String url = newsDetail.getResults().getContent();
                            //启用支持javascript
                            WebSettings settings = new_details.getSettings();
                            settings.setJavaScriptEnabled(true);
                            new_details.loadUrl(url);
                            MyWebView myWebView = new MyWebView(MyNewsDetailActivity.this, new_details);
                            new_details.setWebViewClient(myWebView);
//                            new_details.setWebViewClient(new WebViewClient(){
//                                @Override
//                                public boolean shouldOverrideUrlLoading(WebView view, String request) {
//                                    view.loadUrl(request);
//                                    return true;
//                                }
//                            });
                        }
                    }
                });
    }
}
