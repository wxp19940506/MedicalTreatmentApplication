package iok.la.com.medicaltreatmentapplication.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.OtherClassData;
import iok.la.com.medicaltreatmentapplication.bean.XunjianBean;
import iok.la.com.medicaltreatmentapplication.bean.XunjianEditBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteGridView;
import okhttp3.Call;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

import static android.widget.AdapterView.OnClickListener;
import static android.widget.AdapterView.OnItemClickListener;
import static android.widget.AdapterView.OnLongClickListener;

public class ScheduleEditXunJianActivity extends BaseActivity {
    private RelativeLayout xunjian_time, yuanqu, xunjian_cyperson, xunjian_office, xunjian_content, problem_schedule;
    private TextView selectdate, yuanquname, xunjianperson, xunjian_officename, xunjian_contenttext,office_id,office_name;
    private Calendar calendar;
    private PopupWindow office_popupWindow;
    private int DepartmentID;
    private String ymday = "";
    private ImageButton addCommonDesc;
    private List<Map> problems;
    private List<Map> problemData;
    private List<String> descritions, canyupersons, deparments, schedules;
    private Button submit;
    private LinearLayout image_show, add_problems, problem_contain, image_contain;
    private RelativeLayout image_post;
    private List<Map> uris;
    private List  imagesDesc, aImageIDs;
    EditText problem_desc,desc_info, imagedesc;
    private TextView urlText;
    private ScheduleNoteGridView image_grid;
    XunjianEditBean bean;
    private List departMentIds, descs;
    PopupWindow imagePopupWindow;
    View addImagesItemView,itemView;
    ImageView selectimage;
    private String date = "";
    private String yuanquId = "";
    private String totlaDesc = "";
    private Map<String, File> imageFiles;
    private List<Map<String, String>> selectDepartments;
    RelativeLayout show_moreschedule;
    private ImageView addimagedesc,commondesc;
    private boolean isEmipy = true;
    private boolean isImageEmipy = true;
    int noteId;
    Observable observable,observableProblemDesx,observableImageDesx,observableImageUrl,observableImageId;
    Observer<ImageView> observer;
    Observer<TextView> observerImageUrl,observerImageId;
    Observer<EditText>  observerProblemDesx,observerImageDesx;
    private Dialog proDialog;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_add_xun_jian;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_add_xun_jian;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(ScheduleEditXunJianActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        noteId = getIntent().getIntExtra("id",56);
        selectDepartments = new ArrayList<>();
        aImageIDs = new ArrayList();
        imageFiles = new HashMap<>();
        departMentIds = new ArrayList<>();
        imagesDesc = new ArrayList<>();
        canyupersons = new ArrayList<>();
        deparments = new ArrayList<>();
        schedules = new ArrayList<>();
        descs = new ArrayList<>();
        image_grid = (ScheduleNoteGridView) findViewById(R.id.image_grid);
        uris = new ArrayList<>();
        descritions = new ArrayList<>();
        problems = new ArrayList<>();
        add_problems = (LinearLayout) findViewById(R.id.add_problems);
        problem_contain = (LinearLayout) findViewById(R.id.problem_contain);
        image_contain = (LinearLayout) findViewById(R.id.image_contain);
        image_show = (LinearLayout) findViewById(R.id.image_show);
        submit = (Button) findViewById(R.id.submit);
        desc_info = (EditText) findViewById(R.id.desc_info);
        addCommonDesc = (ImageButton) findViewById(R.id.addCommonDesc);
        xunjian_time = (RelativeLayout) findViewById(R.id.xunjian_time);
        yuanqu = (RelativeLayout) findViewById(R.id.yuanqu);
        xunjian_office = (RelativeLayout) findViewById(R.id.xunjian_office);
        xunjian_cyperson = (RelativeLayout) findViewById(R.id.xunjian_cyperson);
        xunjian_content = (RelativeLayout) findViewById(R.id.xunjian_content);
        selectdate = (TextView) findViewById(R.id.selectdate);
        yuanquname = (TextView) findViewById(R.id.yuanquname);
        xunjianperson = (TextView) findViewById(R.id.xunjianperson);
        xunjian_officename = (TextView) findViewById(R.id.xunjian_officename);
        xunjian_contenttext = (TextView) findViewById(R.id.xunjian_contenttext);
        calendar = Calendar.getInstance();
        ymday = getIntent().getStringExtra("date");
        date = getIntent().getStringExtra("date");
        selectdate.setText(ymday);
    }

    @Override
    public void initData() {
        String xunjianUrl = Content.BASE_URL +"AppRotas/PatrolEdit?id="+noteId+"&Token=" + PrefUtils.getString(getBaseContext(), "token", "");
        OkHttpUtils
                .get()
                .url(xunjianUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(response, XunjianEditBean.class);
                        if (bean.isStatus()) {
                            proDialog.dismiss();
                            //显示默认信息
                            yuanquId = bean.getResults().getPatrol().getAreaID()+"";
                            yuanquname.setText(bean.getResults().getPatrol().getAreaName());
                            StringBuffer stringBuffer = new StringBuffer();
                            for (int i =0;i<bean.getResults().getSelectedEmployees().size();i++){
                                canyupersons.add(bean.getResults().getSelectedEmployees().get(i).getEmployeeID()+"");
                                if (i == bean.getResults().getSelectedEmployees().size()-1){
                                    stringBuffer.append(bean.getResults().getSelectedEmployees().get(i).getName());
                                }else {
                                    stringBuffer.append(bean.getResults().getSelectedEmployees().get(i).getName()+",");
                                }
                            }
                            xunjianperson.setText(stringBuffer.toString());
                            StringBuffer problemBuffer = new StringBuffer();
                            for (int i = 0; i <bean.getResults().getSelectedDepartments().size() ; i++) {
                                Map<String,String> map = new HashMap<String, String>();
                                map.put("name",bean.getResults().getSelectedDepartments().get(i).getName());
                                map.put("id",bean.getResults().getSelectedDepartments().get(i).getDepartmentID()+"");
                                selectDepartments.add(map);
                                deparments.add( bean.getResults().getSelectedDepartments().get(i).getDepartmentID()+"");
                                if (i == bean.getResults().getSelectedDepartments().size()-1){
                                    problemBuffer.append(bean.getResults().getSelectedDepartments().get(i).getName());
                                }else {
                                    problemBuffer.append(bean.getResults().getSelectedDepartments().get(i).getName()+",");
                                }
                            }
                            xunjian_officename.setText(problemBuffer.toString());
                            StringBuffer categoryBuffer = new StringBuffer();
                            for (int i = 0; i <bean.getResults().getSelectedCategories().size() ; i++) {
                                schedules.add(bean.getResults().getSelectedCategories().get(i).getPatrolCategoryID()+"");
                                if (i == bean.getResults().getSelectedCategories().size()-1){
                                    categoryBuffer.append(bean.getResults().getSelectedCategories().get(i).getName());
                                }else {
                                    categoryBuffer.append(bean.getResults().getSelectedCategories().get(i).getName()+",");
                                }
                            }
                            xunjian_contenttext.setText(categoryBuffer.toString());
                            totlaDesc = bean.getResults().getPatrol().getDescription();
                            desc_info.setText(totlaDesc);
                            //问题
                            for (int i = 0; i < bean.getResults().getSelectedPatrolRecords().size(); i++) {
                                final View itemView = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.problem_note_item, null);
                                commondesc = (ImageView) itemView.findViewById(R.id.commondesc);
                                show_moreschedule = (RelativeLayout) itemView.findViewById(R.id.show_moreschedule);
                                final EditText problem_desc = (EditText) itemView.findViewById(R.id.problem_desc);
                                office_id = (TextView) itemView.findViewById(R.id.office_id);
                                office_name = (TextView) itemView.findViewById(R.id.office_name);
                                office_name.setText(bean.getResults().getSelectedPatrolRecords().get(i).getName());
                                problem_desc.setText(bean.getResults().getSelectedPatrolRecords().get(i).getDescription());
                                office_id.setText(bean.getResults().getSelectedPatrolRecords().get(i).getDepartmentID()+"");
                                commondesc.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        observableProblemDesx = Observable.create(new Observable.OnSubscribe<EditText>() {
                                            @Override
                                            public void call(Subscriber<? super EditText> subscriber) {
                                                subscriber.onNext(problem_desc);
                                            }
                                        });
                                        List<Map> data = new ArrayList<Map>();
                                        List<XunjianEditBean.ResultsBean.SentencesBean> sentences = bean.getResults().getSentences();
                                        for (int i = 0; i < sentences.size(); i++) {
                                            Map map = new HashMap();
                                            map.put("content", sentences.get(i).getContent());
                                            map.put("id", sentences.get(i).getID());
                                            map.put("pinyin", sentences.get(i).getPinyin());
                                            map.put("summary", sentences.get(i).getSummary());
                                            map.put("type", sentences.get(i).getRotaSentenceCategoryName());
                                            data.add(map);
                                        }
                                        OtherClassData classData = new OtherClassData();
                                        classData.setData(data);
                                        Intent intent = new Intent(ScheduleEditXunJianActivity.this, ScheduleXunJianCommonDescActivity.class);
                                        intent.putExtra("id", 2);
                                        intent.putExtra("data", classData);
                                        startActivityForResult(intent, Content.REQUEST_CODE11);
                                    }
                                });
                                show_moreschedule.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        office_id = (TextView) itemView.findViewById(R.id.office_id);
                                        office_name = (TextView) itemView.findViewById(R.id.office_name);
                                        //problem_desc = (EditText) itemView.findViewById(R.id.problem_desc);
                                        if (selectDepartments.toString().trim().equals("[]")){
                                            ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请先选择巡检科室");
                                        }else {
                                            initSchedulePopupWindow(office_name,office_id, selectDepartments);
                                        }
                                    }
                                });
                                //问题记录描述
                                commondesc.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        observableProblemDesx = Observable.create(new Observable.OnSubscribe<EditText>() {
                                            @Override
                                            public void call(Subscriber<? super EditText> subscriber) {
                                                subscriber.onNext(problem_desc);
                                            }
                                        });
                                        List<Map> data = new ArrayList<Map>();
                                        List<XunjianEditBean.ResultsBean.SentencesBean> sentences = bean.getResults().getSentences();
                                        for (int i = 0; i < sentences.size(); i++) {
                                            Map map = new HashMap();
                                            map.put("content", sentences.get(i).getContent());
                                            map.put("id", sentences.get(i).getID());
                                            map.put("pinyin", sentences.get(i).getPinyin());
                                            map.put("summary", sentences.get(i).getSummary());
                                            map.put("type", sentences.get(i).getRotaSentenceCategoryName());
                                            data.add(map);
                                        }
                                        OtherClassData classData = new OtherClassData();
                                        classData.setData(data);
                                        Intent intent = new Intent(ScheduleEditXunJianActivity.this, ScheduleXunJianCommonDescActivity.class);
                                        intent.putExtra("id", 2);
                                        intent.putExtra("data", classData);
                                        startActivityForResult(intent, Content.REQUEST_CODE11);
                                    }
                                });
                                problem_contain.addView(itemView);
                                //问题记录长按删除
                                itemView.setOnLongClickListener(new OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleEditXunJianActivity.this);
                                        builder.setMessage("确定删除该数据？");
                                        builder.setTitle("提示");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                problem_contain.removeView(itemView);
                                            }
                                        });
                                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create().show();
                                        return true;
                                    }
                                });


                            }
                            for (int i = 0; i < bean.getResults().getSelectedPatrolImages().size(); i++) {
                                //图片
                                addImagesItemView = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.add_images_contain, null);
                                image_post = (RelativeLayout) addImagesItemView.findViewById(R.id.image_post);
                                final ImageView selectimage2 = (ImageView) addImagesItemView.findViewById(R.id.selectimage);
                                addimagedesc = (ImageView) addImagesItemView.findViewById(R.id.addimagedesc);
                                final TextView urlText = (TextView) addImagesItemView.findViewById(R.id.urlText);
                                final TextView imageid = (TextView) addImagesItemView.findViewById(R.id.imageid);
                                imageid.setText(bean.getResults().getSelectedPatrolImages().get(i).getID()+"");
                                final EditText imagedesc = (EditText) addImagesItemView.findViewById(R.id.imagedesc);
                                imagedesc.setText(bean.getResults().getSelectedPatrolImages().get(i).getDescription());
                                String url = bean.getResults().getSelectedPatrolImages().get(i).getPath();
                                if (url.substring(url.indexOf("~")) != null){
                                    final String newurl = url.substring(url.indexOf("~")+1).trim();
                                    Picasso.with(ScheduleEditXunJianActivity.this).load(Content.BASE_URL0+newurl).error(R.mipmap.wei).placeholder(R.mipmap.wei).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            selectimage2.setImageBitmap(bitmap);
//                                            saveLoadImagePost(bitmap,newurl,urlText);
                                            urlText.setText("0");

                                        }

                                        @Override
                                        public void onBitmapFailed(Drawable errorDrawable) {
                                            selectimage2.setImageResource(R.mipmap.wei);

                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                                        }
                                    });
                                }
                                addimagedesc.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        observableImageDesx = Observable.create(new Observable.OnSubscribe<EditText>() {
                                            @Override
                                            public void call(Subscriber<? super EditText> subscriber) {
                                                subscriber.onNext(imagedesc);
                                            }
                                        });
                                        List<Map> data = new ArrayList<Map>();
                                        //List<ScheduleNoteData.ResultsBean.SentencesBean> sentences = noteData.getResults().getSentences();
                                        List<XunjianEditBean.ResultsBean.SentencesBean> sentences = bean.getResults().getSentences();;
                                        for (int i = 0; i < sentences.size(); i++) {
                                            Map map = new HashMap();
                                            map.put("content", sentences.get(i).getContent());
                                            map.put("id", sentences.get(i).getID());
                                            map.put("pinyin", sentences.get(i).getPinyin());
                                            map.put("summary", sentences.get(i).getSummary());
                                            map.put("type", sentences.get(i).getRotaSentenceCategoryName());
                                            data.add(map);
                                        }
                                        OtherClassData classData = new OtherClassData();
                                        classData.setData(data);
                                        Intent intent = new Intent(ScheduleEditXunJianActivity.this, ScheduleXunJianCommonDescActivity.class);
                                        intent.putExtra("id", 3);
                                        intent.putExtra("data", classData);
                                        startActivityForResult(intent, Content.REQUEST_CODE12);
                                    }
                                });
                                //传图片调用相机或图库
                                image_post.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showPopWindowImage();
                                        observable = Observable.create(new Observable.OnSubscribe<ImageView>() {
                                            @Override
                                            public void call(Subscriber<? super ImageView> subscriber) {
                                                subscriber.onNext(selectimage2);
                                            }
                                        });
                                        observableImageUrl = Observable.create(new Observable.OnSubscribe<TextView>() {
                                            @Override
                                            public void call(Subscriber<? super TextView> subscriber) {
                                                subscriber.onNext(urlText);
                                            }
                                        });
                                        observableImageId = Observable.create(new Observable.OnSubscribe<TextView>() {
                                            @Override
                                            public void call(Subscriber<? super TextView> subscriber) {
                                                subscriber.onNext(imageid);
                                            }
                                        });
                                    }
                                });
                                image_contain.addView(addImagesItemView);
                                addImagesItemView.setOnLongClickListener(new OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleEditXunJianActivity.this);
                                        builder.setMessage("确定删除该数据？");
                                        builder.setTitle("提示");
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                image_contain.removeView(addImagesItemView);
                                            }
                                        });
                                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create().show();
                                        return true;
                                    }
                                });

                            }

                            final List<XunjianEditBean.ResultsBean.GeneralEmployeesBean> ges = bean.getResults().getGeneralEmployees();
                            initPopupWindow(bean);
                            //院区的点击事件
                            yuanqu.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    office_popupWindow.setFocusable(true);
                                    office_popupWindow.setOutsideTouchable(true);
                                    office_popupWindow.setTouchable(true);
                                    office_popupWindow.setBackgroundDrawable(new BitmapDrawable());
                                    office_popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                                office_popupWindow.dismiss();
                                                return true;
                                            }
                                            return false;
                                        }
                                    });
                                    office_popupWindow.showAsDropDown(yuanqu);

                                }
                            });
                            //巡检内容数据
                            final List<Map> content0 = new ArrayList<Map>();
                            final List<Map> content1 = new ArrayList<Map>();
                            for (int i = 0; i < bean.getResults().getPatrolCategoryOftens().size(); i++) {
                                Map map = new HashMap();
                                map.put("name", bean.getResults().getPatrolCategoryOftens().get(i).getName());
                                map.put("id", bean.getResults().getPatrolCategoryOftens().get(i).getID());
                                map.put("pinyin", bean.getResults().getPatrolCategoryOftens().get(i).getPinyin());
                                content0.add(map);
                            }
                            for (int i = 0; i < bean.getResults().getPatrolCategoryOthers().size(); i++) {
                                Map map = new HashMap();
                                map.put("name", bean.getResults().getPatrolCategoryOthers().get(i).getName());
                                map.put("id", bean.getResults().getPatrolCategoryOthers().get(i).getID());
                                map.put("pinyin", bean.getResults().getPatrolCategoryOthers().get(i).getPinyin());
                                content1.add(map);
                            }
                            //巡检科室数据
                            final List<Map> offices0 = new ArrayList<Map>();
                            final List<Map> offices1 = new ArrayList<Map>();
                            int count0 = 0;
                            if (bean.getResults().getDepartments().size() >= 10) {
                                count0 = 10;
                                for (int i = 10; i < bean.getResults().getDepartments().size(); i++) {
                                    Map map = new HashMap();
                                    map.put("name", bean.getResults().getDepartments().get(i).getName());
                                    map.put("id", bean.getResults().getDepartments().get(i).getId());
                                    map.put("pinyin", bean.getResults().getDepartments().get(i).getPinyin());
                                    offices1.add(map);
                                }
                            } else {
                                count0 = bean.getResults().getDepartments().size();
                            }
                            for (int i = 0; i < count0; i++) {
                                Map map = new HashMap();
                                map.put("name", bean.getResults().getDepartments().get(i).getName());
                                map.put("id", bean.getResults().getDepartments().get(i).getId());
                                map.put("pinyin", bean.getResults().getDepartments().get(i).getPinyin());
                                offices0.add(map);
                            }
                            //巡检参与人数据
                            final List<Map> data = new ArrayList<Map>();
                            for (int i = 0; i < ges.size(); i++) {
                                Map map = new HashMap();
                                map.put("name", ges.get(i).getName());
                                map.put("id", ges.get(i).getId());
                                map.put("pinyin", ges.get(i).getPinyin());
                                data.add(map);
                            }
                            //巡检参与人
                            xunjian_cyperson.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ymday.equals("")) {
                                        ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this, "请选择巡检时间");
                                    } else {
                                        Intent intent = new Intent(ScheduleEditXunJianActivity.this,ScheduleXunJianPersonActivity.class);
                                        intent.putExtra("date", ymday);
                                        OtherClassData classData = new OtherClassData();
                                        classData.setData(data);
                                        intent.putExtra("persons", classData);
                                        startActivityForResult(intent, Content.REQUEST_CODE5);
                                    }
                                }
                            });
                            //巡检科室
                            xunjian_office.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    selectDepartments.clear();
                                    //deparments.clear();
                                    Intent intent = new Intent(ScheduleEditXunJianActivity.this, XunJianOfficeActivity.class);
                                    OtherClassData classData = new OtherClassData();
                                    classData.setData(offices0);
                                    classData.setData0(offices1);
                                    intent.putExtra("office", classData);
                                    intent.putExtra("id", 0);
                                    startActivityForResult(intent, Content.REQUEST_CODE6);
                                }
                            });
                            //巡检内容
                            xunjian_content.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    schedules.clear();
                                    Intent intent = new Intent(ScheduleEditXunJianActivity.this, XunJianOfficeActivity.class);
                                    OtherClassData classData = new OtherClassData();
                                    classData.setData(content0);
                                    classData.setData0(content1);
                                    intent.putExtra("office", classData);
                                    intent.putExtra("id", 1);
                                    startActivityForResult(intent, Content.REQUEST_CODE7);
                                }
                            });
                            //描述
                            addCommonDesc.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    List<Map> data = new ArrayList<Map>();
                                    //List<ScheduleNoteData.ResultsBean.SentencesBean> sentences = noteData.getResults().getSentences();
                                    List<XunjianEditBean.ResultsBean.SentencesBean> sentences = bean.getResults().getSentences();
                                    for (int i = 0; i < sentences.size(); i++) {
                                        Map map = new HashMap();
                                        map.put("content", sentences.get(i).getContent());
                                        map.put("id", sentences.get(i).getID());
                                        map.put("pinyin", sentences.get(i).getPinyin());
                                        map.put("summary", sentences.get(i).getSummary());
                                        map.put("type", sentences.get(i).getRotaSentenceCategoryName());
                                        data.add(map);
                                    }
                                    OtherClassData classData = new OtherClassData();
                                    classData.setData(data);
                                    Intent intent = new Intent(ScheduleEditXunJianActivity.this, ScheduleXunJianCommonDescActivity.class);
                                    intent.putExtra("id", 0);
                                    intent.putExtra("data", classData);
                                    startActivityForResult(intent, Content.REQUEST_CODE8);
                                }
                            });

                            //问题记录+
                            add_problems.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final View itemView = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.problem_note_item, null);
                                    commondesc = (ImageView) itemView.findViewById(R.id.commondesc);
                                    show_moreschedule = (RelativeLayout) itemView.findViewById(R.id.show_moreschedule);
                                    final EditText problem_desc = (EditText) itemView.findViewById(R.id.problem_desc);
                                    commondesc.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            observableProblemDesx = Observable.create(new Observable.OnSubscribe<EditText>() {
                                                @Override
                                                public void call(Subscriber<? super EditText> subscriber) {
                                                    subscriber.onNext(problem_desc);
                                                }
                                            });
                                            List<Map> data = new ArrayList<Map>();
                                            //List<ScheduleNoteData.ResultsBean.SentencesBean> sentences = noteData.getResults().getSentences();
                                            List<XunjianEditBean.ResultsBean.SentencesBean> sentences = bean.getResults().getSentences();
                                            for (int i = 0; i < sentences.size(); i++) {
                                                Map map = new HashMap();
                                                map.put("content", sentences.get(i).getContent());
                                                map.put("id", sentences.get(i).getID());
                                                map.put("pinyin", sentences.get(i).getPinyin());
                                                map.put("summary", sentences.get(i).getSummary());
                                                map.put("type", sentences.get(i).getRotaSentenceCategoryName());
                                                data.add(map);
                                            }
                                            OtherClassData classData = new OtherClassData();
                                            classData.setData(data);
                                            Intent intent = new Intent(ScheduleEditXunJianActivity.this, ScheduleXunJianCommonDescActivity.class);
                                            intent.putExtra("id", 2);
                                            intent.putExtra("data", classData);
                                            startActivityForResult(intent, Content.REQUEST_CODE11);
                                        }
                                    });
                                    show_moreschedule.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            office_id = (TextView) itemView.findViewById(R.id.office_id);
                                            office_name = (TextView) itemView.findViewById(R.id.office_name);
                                            //problem_desc = (EditText) itemView.findViewById(R.id.problem_desc);
                                            if (selectDepartments.toString().trim().equals("[]")){
                                                ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请先选择巡检科室");
                                            }else {
                                                initSchedulePopupWindow(office_name,office_id, selectDepartments);
                                            }
                                        }
                                    });
                                    problem_contain.addView(itemView);
                                    //问题记录长按删除
                                    itemView.setOnLongClickListener(new OnLongClickListener() {
                                        @Override
                                        public boolean onLongClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleEditXunJianActivity.this);
                                            builder.setMessage("确定删除该数据？");
                                            builder.setTitle("提示");
                                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    problem_contain.removeView(itemView);
                                                }
                                            });
                                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            builder.create().show();
                                            return true;
                                        }
                                    });

                                }
                            });
                            //图片+
                            image_show.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final View addImagesItemView = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.add_images_contain, null);
                                    image_post = (RelativeLayout) addImagesItemView.findViewById(R.id.image_post);
                                    final ImageView selectimage0 = (ImageView) addImagesItemView.findViewById(R.id.selectimage);
                                    ImageView addimagedesc = (ImageView) addImagesItemView.findViewById(R.id.addimagedesc);
                                    final TextView urlText = (TextView) addImagesItemView.findViewById(R.id.urlText);
                                    final TextView imageid = (TextView) addImagesItemView.findViewById(R.id.imageid);
                                    imageid.setText("0");
                                    final EditText imagedesc = (EditText) addImagesItemView.findViewById(R.id.imagedesc);
                                    addimagedesc.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            observableImageDesx = Observable.create(new Observable.OnSubscribe<EditText>() {
                                                @Override
                                                public void call(Subscriber<? super EditText> subscriber) {
                                                    subscriber.onNext(imagedesc);
                                                }
                                            });
                                            List<Map> data = new ArrayList<Map>();
                                            //List<ScheduleNoteData.ResultsBean.SentencesBean> sentences = noteData.getResults().getSentences();
                                            List<XunjianEditBean.ResultsBean.SentencesBean> sentences = bean.getResults().getSentences();;
                                            for (int i = 0; i < sentences.size(); i++) {
                                                Map map = new HashMap();
                                                map.put("content", sentences.get(i).getContent());
                                                map.put("id", sentences.get(i).getID());
                                                map.put("pinyin", sentences.get(i).getPinyin());
                                                map.put("summary", sentences.get(i).getSummary());
                                                map.put("type", sentences.get(i).getRotaSentenceCategoryName());
                                                data.add(map);
                                            }
                                            OtherClassData classData = new OtherClassData();
                                            classData.setData(data);
                                            Intent intent = new Intent(ScheduleEditXunJianActivity.this, ScheduleXunJianCommonDescActivity.class);
                                            intent.putExtra("id", 3);
                                            intent.putExtra("data", classData);
                                            startActivityForResult(intent, Content.REQUEST_CODE12);
                                        }
                                    });
                                    //传图片调用相机或图库
                                    image_post.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            observable = Observable.create(new Observable.OnSubscribe<ImageView>() {
                                                @Override
                                                public void call(Subscriber<? super ImageView> subscriber) {
                                                    subscriber.onNext(selectimage0);
                                                }
                                            });
                                            observableImageUrl = Observable.create(new Observable.OnSubscribe<TextView>() {
                                                @Override
                                                public void call(Subscriber<? super TextView> subscriber) {
                                                    subscriber.onNext(urlText);
                                                }
                                            });
                                            showPopWindowImage();
                                        }
                                    });
                                    image_contain.addView(addImagesItemView);
                                    addImagesItemView.setOnLongClickListener(new OnLongClickListener() {
                                        @Override
                                        public boolean onLongClick(View v) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleEditXunJianActivity.this);
                                            builder.setMessage("确定删除该数据？");
                                            builder.setTitle("提示");
                                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    image_contain.removeView(addImagesItemView);
                                                }
                                            });
                                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            builder.create().show();
                                            return true;
                                        }
                                    });
                                }
                            });

                        } else {
                            ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this, "参数错误");
                        }

                    }
                });
        initLinister();
    }

    private void initLinister() {
        //提交数据
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                isImageEmipy = false;
                isEmipy =false;
                getProblemsImageData();
                getProblemsData();
                Log.e("canshu",imageFiles.toString()+"__"+aImageIDs.toString());

                totlaDesc = desc_info.getText().toString();
                if (totlaDesc == null){
                    totlaDesc = "";
                }
                if (date.equals("")){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请选择巡检时间");
                }else if (yuanquId.equals("")){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请选择院区");
                }else if (canyupersons.toString().equals("[]")){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请选择巡检参与人");
                }else if (deparments.toString().equals("[]")){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请选择巡检科室");
                }else if (schedules.toString().equals("[]")){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请选择巡检内容");
                }else if (totlaDesc.equals("")){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"请填写基本记录描述");
                }else if (!isEmipy){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this, "问题科室或问题内容有一项为空");
                }else if (!isImageEmipy){
                    ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this, "图片或描述有一项为空");
                }
                else {
                    Log.e("canshu",imageFiles.toString()+"__"+aImageIDs.toString()+"--"+imagesDesc.toString());

                    OkHttpUtils
                            .post()
                            .url(Content.BASE_URL+"AppRotas/PatrolEditSave")
                            .addParams("ID", noteId+"")
                            .addParams("PatrolDate",date)
                            .addParams("Description",totlaDesc)
                            .addParams("AreaID", yuanquId)
                            .addParams("aSelectedEmployees",canyupersons.toString())
                            .addParams("aSelectedDepartments",deparments.toString())
                            .addParams("aSelectedCategories",schedules.toString())
                            .addParams("aDepartmentIDs",departMentIds.toString())
                            .addParams("aDescriptions",descs.toString())
                            .addParams("aImageDescriptions",imagesDesc.toString())
                            .addParams("aImageIDs",aImageIDs.toString())
                            .addParams("Token",PrefUtils.getString(getBaseContext(), "token", ""))
                            .files("Images",imageFiles)
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
                                            proDialog.dismiss();
                                            ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"修改成功");
                                            finish();
                                        }else {
                                            ToastUtils.showToastSafe(ScheduleEditXunJianActivity.this,"修改失败");
                                            canyupersons.clear();
                                            deparments.clear();
                                            schedules.clear();
                                            departMentIds.clear();
                                            imagesDesc.clear();
                                            aImageIDs.clear();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        });
        //巡检时间
        xunjian_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                //request_time.setText(dateFormat.format(calendar.getTime())+" "+timeFormat.format(calendar.getTime()));
                                selectdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hourOfDay + ":" + minute);
                                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " " + hourOfDay + ":" + minute;
                                ymday = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });
    }

    //选择上传图片的方式
    private void showPopWindowImage() {
        View fileView = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.filepop, null);
        TextView camera = (TextView) fileView.findViewById(R.id.camera);
        TextView localfile = (TextView) fileView.findViewById(R.id.localfile);
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置拍照意图
                Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(mIntent, Content.TAKE_PHOTO);
            }
        });
        localfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用本地图库
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Content.GET_PHOTO);
            }
        });
        View rootView = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.activity_schedule_add_xun_jian, null);
        imagePopupWindow = new PopupWindow(fileView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置背景颜色
        backgroundAlpha(0.6f);
        ColorDrawable cd = new ColorDrawable(0x000000);
        imagePopupWindow.setBackgroundDrawable(cd);
        imagePopupWindow.setOutsideTouchable(true);
        imagePopupWindow.setFocusable(true);
        imagePopupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        imagePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    //选择院区的pop数据
    private void initPopupWindow(final XunjianEditBean bean) {
        final List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < bean.getResults().getAreas().size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", bean.getResults().getAreas().get(i).getName());
            map.put("id", String.valueOf(bean.getResults().getAreas().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(ScheduleEditXunJianActivity.this, list, R.layout.office_item, new String[]{"name"}, new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ;
                yuanquname.setText(list.get(position).get("name"));
                yuanquId = list.get(position).get("id");
                if (office_popupWindow != null && office_popupWindow.isShowing()) {
                    office_popupWindow.dismiss();
                    office_popupWindow.setTouchable(false);
                    office_popupWindow.setFocusable(false);
                }
            }
        });
    }

    //页面跳转返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StringBuffer bufferperson = new StringBuffer();
        if (requestCode == Content.REQUEST_CODE5 && resultCode == Content.RESULT_CODE5) {
            canyupersons.clear();
            OtherClassData classData = (OtherClassData) data.getSerializableExtra("person");
            List<Map> data1 = classData.getData();
            HashMap map = classData.getMap();
            List<Map> data0 = mapToList(map);
            if (data0 != null){
                for (int i = 0; i < data0.size(); i++) {
                    String name = "";
                    Map map1 = data0.get(i);
                    if (map1 != null){
                        name = (String) map1.get("name");
                    }
                    canyupersons.add(map1.get("id").toString().trim());
                    if (i == data0.size() - 1) {
                        if (data1 != null) {
                            bufferperson.append(name + ",");
                        } else {
                            bufferperson.append(name);
                        }
                    } else {
                        bufferperson.append(name + ",");
                    }
                }
            }
            if (data1 != null){
                for (int i = 0; i < data1.size(); i++) {
                    Map map2 = data1.get(i);
                    canyupersons.add(map2.get("id").toString().trim());
                    if (i == data1.size() - 1) {
                        String name = (String) map2.get("name");
                        bufferperson.append(name);
                    } else {
                        String name = (String) map2.get("name");
                        bufferperson.append(name);

                    }
                }
            }

            xunjianperson.setText(bufferperson.toString());
        } else if (requestCode == Content.REQUEST_CODE6 && resultCode == Content.RESULT_CODE6) {
            deparments.clear();
            selectContentUpdate(data, xunjian_officename, deparments, 0);
        } else if (requestCode == Content.REQUEST_CODE7 && resultCode == Content.RESULT_CODE7) {
            selectContentUpdate(data, xunjian_contenttext, schedules, 1);
        } else if (requestCode == Content.REQUEST_CODE8 && resultCode == Content.RESULT_CODE8) {
            showProblemContent(data, desc_info);
        } else if (requestCode == Content.REQUEST_CODE9 && resultCode == Content.RESULT_CODE9) {
//            showProblemContent(data, problem_content);
        } else if (resultCode == RESULT_OK) {  //回传值接受成功
            if (requestCode == Content.TAKE_PHOTO) {    //拍照取图
                Bundle bundle = data.getExtras();   //获取data数据集合
                final Bitmap bitmap = (Bitmap) bundle.get("data");        //获得data数据
                observer = new Observer<ImageView>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ImageView imageView) {
                        imageView.setImageBitmap(bitmap);

                    }
                };
                if (observable != null & observer != null)
                    observable.subscribe(observer);
                imagePopupWindow.dismiss();
                saveImagePost(bitmap);
                observerImageId = new Observer<TextView>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TextView textView) {
                        textView.setText("0");
                    }
                };
                if (observableImageId != null & observerImageId != null)
                    observableImageId.subscribe(observerImageId);
            } else if (requestCode == Content.GET_PHOTO) {     //相册取图
                getImageFronUri(data);
                observerImageId = new Observer<TextView>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TextView textView) {
                        textView.setText("0");
                    }
                };
                if (observableImageId != null & observerImageId != null)
                    observableImageId.subscribe(observerImageId);
                if (office_popupWindow != null)
                    office_popupWindow.dismiss();
            }
        } else if (requestCode == Content.REQUEST_CODE11 && resultCode == Content.RESULT_CODE11) {
            observerProblemDesx = new Observer<EditText>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(EditText editText) {
                    showProblemContent(data, editText);
                }
            };
            if(observableProblemDesx!= null && observerProblemDesx!= null)
                observableProblemDesx.subscribe(observerProblemDesx);
        }else if (requestCode == Content.REQUEST_CODE12 && resultCode == Content.RESULT_CODE12) {
            observerImageDesx = new Observer<EditText>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(EditText editText) {
                    showProblemContent(data, editText);
                }
            };
            if(observableImageDesx!= null && observerImageDesx!= null)
                observableImageDesx.subscribe(observerImageDesx);
        }
    }

    private void getImageFronUri(final Intent data) {
        imagePopupWindow.dismiss();
        observer = new Observer<ImageView>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ImageView imageView) {
                Picasso.with(ScheduleEditXunJianActivity.this).load(data.getData()).error(R.mipmap.wei).placeholder(R.mipmap.wei).into(imageView);
            }
        };
        if (observable != null & observer != null)
            observable.subscribe(observer);
        final String imagePath = getPathByUri4kitkat(ScheduleEditXunJianActivity.this, data.getData());
        observerImageUrl = new Observer<TextView>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextView textView) {
                textView.setText(imagePath);
            }
        };
        if (observableImageUrl != null & observerImageUrl != null)
            observableImageUrl.subscribe(observerImageUrl);
    }


    private void showProblemContent(Intent data, EditText desc_info) {
        OtherClassData otherClassData = (OtherClassData) data.getSerializableExtra("info");
        HashMap map = otherClassData.getMap();
        List list = mapToList(map);
        StringBuffer databuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Map seledata = (Map) list.get(i);
            databuffer.append(seledata.get("content") + " ");
        }
        desc_info.setText(databuffer.toString());
    }

    //巡检科室和巡检内容回传数据
    private void selectContentUpdate(Intent data, TextView textView, List list, int code) {
        list.clear();
        StringBuffer stringBuffer = new StringBuffer();
        OtherClassData classData = (OtherClassData) data.getSerializableExtra("data");
        Map<Integer, Map> data0 = classData.getMap();
        Map map0 = data0.get(0);
        Map map1 = data0.get(1);
        if (map0 != null) {
            List<Map> list0 = mapToList(map0);
            for (int i = 0; i < list0.size(); i++) {
                if (code == 0) {
                    String name = (String) list0.get(i).get("name");
                    String id = list0.get(i).get("id").toString().trim();
                    Map<String, String> map = new HashMap();
                    map.put("name", name);
                    map.put("id", id);
                    selectDepartments.add(map);
                }
                String officename = (String) list0.get(i).get("name");
                list.add(list0.get(i).get("id").toString().trim());
                if (i == list0.size() - 1) {
                    if (map1 == null || map1.toString().equals("[]")) {
                        stringBuffer.append(officename);
                    } else {
                        stringBuffer.append(officename + ",");
                    }
                } else {
                    stringBuffer.append(officename + ",");
                }
            }
        }
        if (map1 != null) {
            List<Map> list1 = mapToList(map1);
            for (int i = 0; i < list1.size(); i++) {
                if (code == 0) {
                    String name = (String) list1.get(i).get("name");
                    String id = list1.get(i).get("id").toString().trim();
                    Map<String, String> map = new HashMap();
                    map.put("name", name);
                    map.put("id", id);
                    selectDepartments.add(map);
                }
                String officename = (String) list1.get(i).get("name");
                list.add(list1.get(i).get("id").toString().trim());
                if (i == list1.size() - 1) {
                    stringBuffer.append(officename);
                } else {
                    stringBuffer.append(officename + ",");
                }
            }
        }
        textView.setText(stringBuffer.toString());
    }

    private List mapToList(Map<Integer, Map> data) {
        List<Map> listValue = null;
        if (data != null)
        listValue = new ArrayList<>(data.values());
        return listValue;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        this.getWindow().setAttributes(lp);
    }


    @OnClick(R.id.turn_last)
    public void onClick() {
        finish();
    }


    //图片和描述listview
    class ImagesDescPostAdapter extends BaseAdapter {
        private List<Map> uris;

        public ImagesDescPostAdapter(List<Map> uris) {
            this.uris = uris;
        }

        @Override
        public int getCount() {
            return uris.size();
        }

        @Override
        public Object getItem(int position) {
            return uris.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.imagelist, parent, false);
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        application.problemNotes.clear();
    }

    //问题记录里科室的pop
    private void initSchedulePopupWindow(final TextView text, final TextView idText, final List<Map<String, String>> aselectDepartments) {
        View view = LayoutInflater.from(ScheduleEditXunJianActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(ScheduleEditXunJianActivity.this, aselectDepartments, R.layout.office_item, new String[]{"name"}, new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        final PopupWindow office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        office_popupWindow.setFocusable(true);
        office_popupWindow.setOutsideTouchable(true);
        office_popupWindow.setTouchable(true);
        office_popupWindow.setBackgroundDrawable(new BitmapDrawable());
        office_popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    office_popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        office_popupWindow.showAsDropDown(text);
        officelist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                text.setText(aselectDepartments.get(position).get("name"));
                idText.setText(aselectDepartments.get(position).get("id"));
                office_popupWindow.dismiss();

            }
        });
    }

    //获取问题科室的所有数据
    public void getProblemsData() {
        descs.clear();
        departMentIds.clear();
        isEmipy = true;
        if (problem_contain != null) {
            descs.clear();
            departMentIds.clear();
            int childCount = problem_contain.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View problemView = problem_contain.getChildAt(i);
                final TextView office_id = (TextView) problemView.findViewById(R.id.office_id);
                final EditText office_desc = (EditText) problemView.findViewById(R.id.problem_desc);
                String id = (String) office_id.getText();
                String desc = office_desc.getText().toString().trim();
                if (!id.equals("") && !desc.equals("")) {
                    descs.add(desc);
                    departMentIds.add(id);
                } else if ((id.equals("") && !desc.equals("") )||(!id.equals("") && desc.equals(""))) {
                    isEmipy = false;
                }
            }
        }
    }

    //获取图片的所有数据
    public void getProblemsImageData() {
        imageFiles.clear();
        aImageIDs.clear();
        isImageEmipy = true;
        if (image_contain != null) {
            int childCount = image_contain.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View ImagesItemView = image_contain.getChildAt(i);
                final TextView  urlText = (TextView) ImagesItemView.findViewById(R.id.urlText);
                final TextView  imageid = (TextView) ImagesItemView.findViewById(R.id.imageid);
                final EditText imagedesc = (EditText) ImagesItemView.findViewById(R.id.imagedesc);
                String imagUrl = urlText.getText().toString().trim();
                String imageIds = imageid.getText().toString().trim();
                String desc = imagedesc.getText().toString().trim();
                aImageIDs.add(imageIds);
                imagesDesc.add(desc.trim());
//                File file = new File(imagUrl);
//                imageFiles.put(PrefUtils.getString(getBaseContext(),"username","")+file.getName().substring(0),file);
                if (!imagUrl.equals("") && !desc.equals("")&&!imagUrl.equals("0")) {
                    //imageUris.add(imagUrl);
                    File file = new File(imagUrl);
                    imageFiles.put(PrefUtils.getString(getBaseContext(),"username","")+i+file.getName().substring(0),file);
                } else if((imagUrl.equals("") && !desc.equals("")) ||(!imagUrl.equals("") && desc.equals("")) ) {
                    isImageEmipy = false;
                    return;
                }
            }
        }
    }

    // 专为Android4.4设计的从Uri获取文件绝对路径
    //根据Uri获取图片路径
    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private void saveImagePost(Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "CameraPhoto");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        final File file = new File(appDir, fileName);
        observerImageUrl = new Observer<TextView>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(TextView textView) {
                textView.setText(file.toString());
            }
        };
        if (observableImageUrl != null & observerImageUrl != null)
            observableImageUrl.subscribe(observerImageUrl);
//        imageFiles.put(PrefUtils.getString(getBaseContext(), "username", "") + fileName, file);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveLoadImagePost(Bitmap bitmap,String imageUrl,TextView textUrl) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "CameraPhoto");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String lastName = imageUrl.substring(imageUrl.indexOf(".")).trim();
        String fileName = System.currentTimeMillis() + lastName;
        final File file = new File(appDir,fileName);
//        textUrl.setText("0");
        textUrl.setText(file.toString());
//        imageFiles.put(PrefUtils.getString(getBaseContext(), "username", "") + fileName, file);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
