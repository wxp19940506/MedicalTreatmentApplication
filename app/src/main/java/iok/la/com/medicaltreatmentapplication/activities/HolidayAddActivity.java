package iok.la.com.medicaltreatmentapplication.activities;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.HolidayDataBean;
import iok.la.com.medicaltreatmentapplication.bean.HolidayDepartment;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import okhttp3.Call;

public class HolidayAddActivity extends BaseActivity {

    @BindView(R.id.turn_last)
    ImageButton turnLast;
    @BindView(R.id.holiday_type)
    TextView holidayType;
    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_date)
    TextView endDate;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.post_image)
    TextView postImage;
    @BindView(R.id.additem)
    Button additem;
    @BindView(R.id.activity_holiday_add)
    LinearLayout activityHolidayAdd;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.department_type)
    TextView departmentType;
    @BindView(R.id.holiday_reason)
    EditText holiday_reason;
    private File imageFile;
    private Intent intent;
    private PopupWindow office_popupWindow,imagePopupWindow;
    int departmentId,holidayId,EmployeeId;
    private Calendar calendar;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_holiday_add;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_holiday_add;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        intent = getIntent();
        calendar = Calendar.getInstance();
    }

    @Override
    public void initData() {
        Bundle extras = intent.getExtras();
        HolidayDataBean dataBean = (HolidayDataBean) extras.getSerializable("data");
        final List<HolidayDepartment.ResultsBean.DepartsAddBean> departsAddBeanList = dataBean.getDepartsAdd();
        final List<HolidayDepartment.ResultsBean.EetBean> eetBeanList = dataBean.getEetBeanList();
        EmployeeId = dataBean.getId();
        Log.e("types",departsAddBeanList.toString()+" "+eetBeanList.toString());

        departmentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDepartmentPopupWindow(departmentType,departsAddBeanList);
            }
        });
        holidayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHolidayTypePopupWindow(holidayType,eetBeanList);
            }
        });
    }

    private void initHolidayTypePopupWindow(final TextView holidayType, List<HolidayDepartment.ResultsBean.EetBean> eetBeanList) {
        final List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < eetBeanList.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",eetBeanList.get(i).getName());
            map.put("id", eetBeanList.get(i).getID());
            list.add(map);
        }
        View view = LayoutInflater.from(HolidayAddActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(HolidayAddActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        office_popupWindow.setFocusable(true);
        office_popupWindow.setOutsideTouchable(true);
        office_popupWindow.setTouchable(true);
        office_popupWindow .setBackgroundDrawable(new BitmapDrawable());
        office_popupWindow.showAsDropDown(holidayType);
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
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                holidayType.setText((CharSequence) list.get(position).get("name"));
                holidayId = (int) list.get(position).get("id");
                office_popupWindow.dismiss();

            }
        });
    }


    private void initDepartmentPopupWindow(final TextView text, final List<HolidayDepartment.ResultsBean.DepartsAddBean> departsAddBeanList) {
        final List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < departsAddBeanList.size(); i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",departsAddBeanList.get(i).getName());
            map.put("id", departsAddBeanList.get(i).getId());
            list.add(map);
        }
        View view = LayoutInflater.from(HolidayAddActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(HolidayAddActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        office_popupWindow.setFocusable(true);
        office_popupWindow.setOutsideTouchable(true);
        office_popupWindow.setTouchable(true);
        office_popupWindow .setBackgroundDrawable(new BitmapDrawable());
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
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText((CharSequence) list.get(position).get("name"));
                departmentId = (int) list.get(position).get("id");
                office_popupWindow.dismiss();
            }
        });
    }
    @OnClick({R.id.turn_last, R.id.holiday_type, R.id.start_date, R.id.start_time, R.id.end_date, R.id.end_time, R.id.post_image, R.id.additem})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.turn_last:
                finish();
                break;
            case R.id.start_date:
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        String startTime = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        startDate.setText(startTime);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            case R.id.end_date:
                DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog dialog, final int year, final int monthOfYear, final int dayOfMonth) {
                        String startTime = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        endDate.setText(startTime);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            case R.id.start_time:
                TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                        startTime.setText(hourOfDay+":"+minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                break;
            case R.id.end_time:
                TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                        endTime.setText(hourOfDay+":"+minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                break;
            case R.id.post_image:
                showPopWindowImage();
                break;
            case R.id.additem:
                Log.e("File", imageFile + "");
                if (departmentType.getText().toString().trim().equals("请选择")){
                    ToastUtils.showToastSafe(HolidayAddActivity.this,"请选择科室");
                }else if (holidayType.getText().toString().trim().equals("请选择")){
                    ToastUtils.showToastSafe(HolidayAddActivity.this,"请选择请假类型");
                 }else if (holiday_reason.getText().toString().trim().equals("")){
                    ToastUtils.showToastSafe(HolidayAddActivity.this,"请填写请假原因");
                }else if (startDate.getText().toString().trim().equals("请选择")){
                    ToastUtils.showToastSafe(HolidayAddActivity.this,"请选择开始日期");
                }else if (startTime.getText().toString().trim().equals("请选择")){
                    ToastUtils.showToastSafe(HolidayAddActivity.this,"请选择开始时间");
                }else if (endDate.getText().toString().trim().equals("请选择")){
                    ToastUtils.showToastSafe(HolidayAddActivity.this,"请选择结束日期");
                }else if (endTime.getText().toString().trim().equals("请选择")){
                    ToastUtils.showToastSafe(HolidayAddActivity.this,"请选择结束时间");
                }else {
                    if (imageFile != null){
                        postDataWithImage();
                    }else {
                        postDataNoImage();
                    }
                }
                break;
        }
    }
    //传数据
    public void postDataWithImage(){
        OkHttpUtils
                .post()
                .url(Content.BASE_URL+"AppPersonelCenter/VocationAdd")
                .addParams("DepartmentId",departmentId+"")
                .addParams("EmployeeId",EmployeeId+"")
                .addParams("EmployeeExcludeTypeID",holidayId+"")
                .addParams("Reason",holiday_reason.getText().toString().trim())
                .addParams("BeginDate",startDate.getText().toString().trim())
                .addParams("EndDate",endDate.getText().toString().trim())
                .addParams("BeginTime",startTime.getText().toString().trim())
                .addParams("EndTime",endTime.getText().toString().trim())
                .addParams("IsHoliday",true+"")
                .addFile("VocationImage",PrefUtils.getString(getBaseContext(),"username","")+imageFile.getName(),imageFile)
                .addParams("Token",PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("PostResponse",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean isStatus = jsonObject.optBoolean("status");
                            if (isStatus){
                                ToastUtils.showToastSafe(HolidayAddActivity.this,"添加成功");
                                finish();
                            }else {
                                ToastUtils.showToastSafe(HolidayAddActivity.this,"添加失败");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    public void postDataNoImage(){
        OkHttpUtils
                .post()
                .url(Content.BASE_URL+"AppPersonelCenter/VocationAdd")
                .addParams("DepartmentId",departmentId+"")
                .addParams("EmployeeId",EmployeeId+"")
                .addParams("EmployeeExcludeTypeID",holidayId+"")
                .addParams("Reason",holiday_reason.getText().toString().trim())
                .addParams("BeginDate",startDate.getText().toString().trim())
                .addParams("EndDate",endDate.getText().toString().trim())
                .addParams("BeginTime",startTime.getText().toString().trim())
                .addParams("EndTime",endTime.getText().toString().trim())
                .addParams("IsHoliday",true+"")
                .addFile("VocationImage",PrefUtils.getString(getBaseContext(),"username","")+imageFile.getName(),imageFile)
                .addParams("Token",PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("PostResponse",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean isStatus = jsonObject.optBoolean("");
                            if (isStatus){
                                ToastUtils.showToastSafe(HolidayAddActivity.this,"添加成功");
                                finish();
                            }else {
                                ToastUtils.showToastSafe(HolidayAddActivity.this,"添加失败");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //照片或图片回传
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {  //回传值接受成功

            if (requestCode == Content.TAKE_PHOTO) {    //拍照取图
                Bundle bundle = data.getExtras();   //获取data数据集合
                Bitmap bitmap = (Bitmap) bundle.get("data");        //获得data数据
                image.setImageBitmap(bitmap);
                saveImagePost(bitmap);
                imagePopupWindow.dismiss();

            } else if (requestCode == Content.GET_PHOTO) {     //相册取图
                Uri iconUri = data.getData();
                Picasso.with(HolidayAddActivity.this).load(data.getData()).into(
                        new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                image.setImageBitmap(bitmap);
                                imagePopupWindow.dismiss();
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        }
                );
                String imagePath = getPathByUri4kitkat(HolidayAddActivity.this, iconUri);
                File file = new File(imagePath);
                postPersonIcon(file);
            }

        }

    }

    private void saveImagePost(Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "CameraPhoto");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
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
        postPersonIcon(file);
    }

    private void postPersonIcon(File file) {
        imageFile = file;
//        OkHttpUtils
//                .post()
//                .url(Content.BASE_URL+"AppPersonelCenter/ModifyHeadImage")
//                .addFile("HeadImage",image.getName(),image)
//                .addParams("Token", PrefUtils.getString(getBaseContext(),"token",""))
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e("Imageresponse",response);
//
//                    }
//                });
    }

    //选择上传图片的方式
    private void showPopWindowImage() {
        View fileView = LayoutInflater.from(HolidayAddActivity.this).inflate(R.layout.filepop, null);
        TextView camera = (TextView) fileView.findViewById(R.id.camera);
        TextView localfile = (TextView) fileView.findViewById(R.id.localfile);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置拍照意图
                Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(mIntent, Content.TAKE_PHOTO);
            }
        });
        localfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用本地图库
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Content.GET_PHOTO);
            }
        });
        View rootView = LayoutInflater.from(HolidayAddActivity.this).inflate(R.layout.activity_schedule_add_xun_jian, null);
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

}
