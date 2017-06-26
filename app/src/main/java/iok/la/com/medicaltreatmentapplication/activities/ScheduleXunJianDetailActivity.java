package iok.la.com.medicaltreatmentapplication.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleXunJianDetail;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.DensityUtil;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteGridView;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteListView;
import okhttp3.Call;

public class ScheduleXunJianDetailActivity extends BaseActivity {
    ImageButton turnLast;
    TextView xunjianTime,yuanqu,xunjianPerson,xunjianSchedule,xunjianContent,xunjianDesc,scheduleName,problemDesc;
    ScheduleNoteListView images;
    private int id;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_xun_jian_add;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_xun_jian_add;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(ScheduleXunJianDetailActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 2);
        turnLast = (ImageButton) findViewById(R.id.turn_last);
        xunjianTime = (TextView) findViewById(R.id.xunjian_time);
        yuanqu = (TextView) findViewById(R.id.yuanqu);
        xunjianPerson = (TextView) findViewById(R.id.xunjian_person);
        xunjianSchedule = (TextView) findViewById(R.id.xunjian_schedule);
        xunjianContent = (TextView) findViewById(R.id.xunjian_content);
        xunjianDesc = (TextView) findViewById(R.id.xunjian_desc);
        scheduleName = (TextView) findViewById(R.id.schedule_name);
        problemDesc = (TextView) findViewById(R.id.problem_desc);
        images = (ScheduleNoteListView) findViewById(R.id.images);
    }



    @Override
    public void initData() {
        loadXunJianData();
        initLinister();
    }

    private void initLinister() {
        turnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class XunJianImageAdapter extends BaseAdapter{
        List<ScheduleXunJianDetail.ResultsBean.ImagesBean>  imageUrls ;
        public XunJianImageAdapter(List imageUrls) {
            this.imageUrls = imageUrls;
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public Object getItem(int position) {
            return imageUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(ScheduleXunJianDetailActivity.this).inflate(R.layout.image_item,parent,false);
            final ImageView imageview = (ImageView) view.findViewById(R.id.imageview);
            TextView image_desc = (TextView) view.findViewById(R.id.image_desc);
            String imageUrl = Content.BASE_URL0 + imageUrls.get(position).getPath();
            Picasso.with(ScheduleXunJianDetailActivity.this).load(imageUrl).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageview.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    imageview.setImageResource(R.mipmap.wei);
                    //imageview.setImageDrawable(errorDrawable);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    imageview.setImageResource(R.mipmap.wei);
                }
            });
            image_desc.setText("描述："+imageUrls.get(position).getDescription());
            return view;
        }
    }
    private void loadXunJianData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL + "AppRotas/PatrolsDetails?id="+id+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        ScheduleXunJianDetail jianDetail = gson.fromJson(response, ScheduleXunJianDetail.class);
                        if (jianDetail.isStatus()){
                            proDialog.dismiss();
                            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //String date = dates.substring(dates.indexOf("(")+1,dates.indexOf(")"));
                            //String xunJianTimeText = sdf.format(new Date(date));
                            List<ScheduleXunJianDetail.ResultsBean.EmployeesBean> employees = jianDetail.getResults().getEmployees();
                            List<ScheduleXunJianDetail.ResultsBean.DepartmentsBean> departments = jianDetail.getResults().getDepartments();
                            List<ScheduleXunJianDetail.ResultsBean.CategorysBean> categorys = jianDetail.getResults().getCategorys();
                            List<ScheduleXunJianDetail.ResultsBean.RecordsBean> records = jianDetail.getResults().getRecords();
                            StringBuffer employeString = new StringBuffer();
                            StringBuffer departmentsString = new StringBuffer();
                            StringBuffer xunJianContentString = new StringBuffer();
                            StringBuffer xunJianScheduleString = new StringBuffer();
                            StringBuffer xunJianScheduleDescString = new StringBuffer();
                            for (int i = 0; i < employees.size(); i++) {
                                if (i == employees.size()-1){
                                    employeString.append(employees.get(i).getName());
                                }else {
                                    employeString.append(employees.get(i).getName()+",");
                                }

                            }

                            for (int i = 0; i <departments.size() ; i++) {
                                if (i == departments.size()-1){
                                    departmentsString.append(departments.get(i).getName());
                                }else {
                                    departmentsString.append(departments.get(i).getName()+",");
                                }
                            }
                            for (int i = 0; i <categorys.size() ; i++) {
                                if (i == categorys.size()-1){
                                    xunJianContentString.append(categorys.get(i).getName());
                                }else {
                                    xunJianContentString.append(categorys.get(i).getName()+",");
                                }
                            }
                            for (int i = 0; i < records.size(); i++) {
                                if (i == records.size()-1){
                                    xunJianScheduleString.append(records.get(i).getName());
                                    xunJianScheduleDescString.append(records.get(i).getDescription());
                                }else {
                                    xunJianScheduleString.append(records.get(i).getName()+",");
                                    xunJianScheduleDescString.append(records.get(i).getDescription()+",");

                                }
                            }
                            String description ="";
                            if (jianDetail.getResults().getPatrol().getDescription() == null){
                                description = "暂无描述";
                            }else {
                                 description = jianDetail.getResults().getPatrol().getDescription();
                            }
                            if (jianDetail.getResults().getPatrol().getPatrolDate() != null){
                                String dates = jianDetail.getResults().getPatrol().getPatrolDate();
                                String sdate = dates.substring(dates.indexOf("(") + 1, dates.indexOf(")"));
                                String s = sdf.format(new Date(Long.parseLong(sdate)));
                                xunjianTime.setText("巡检时间："+s);
                            }
                            yuanqu.setText("院区："+jianDetail.getResults().getPatrol().getName());
                            xunjianPerson.setText("巡检参与人："+employeString.toString());
                            xunjianSchedule.setText("巡检科室："+departmentsString.toString());
                            xunjianContent.setText("巡检内容："+xunJianContentString.toString());
                            xunjianDesc.setText("描述："+description);
                            scheduleName.setText("问题科室："+xunJianScheduleString.toString());
                            problemDesc.setText("问题内容："+xunJianScheduleDescString.toString());
                            List<ScheduleXunJianDetail.ResultsBean.ImagesBean> imagesUrls = jianDetail.getResults().getImages();
                            XunJianImageAdapter adapter = new XunJianImageAdapter(imagesUrls);
                            images.setAdapter(adapter);
                        }else {
                            ToastUtils.showToastSafe(ScheduleXunJianDetailActivity.this,"参数错误");
                        }

                    }
                });
    }
}
