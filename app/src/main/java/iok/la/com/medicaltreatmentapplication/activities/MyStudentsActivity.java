package iok.la.com.medicaltreatmentapplication.activities;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.MyStudentListAdapter;
import iok.la.com.medicaltreatmentapplication.bean.MyStudentListBean;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;
import iok.la.com.medicaltreatmentapplication.bean.SchedulePaiBanBean;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.RefreshExpandableListView;
import iok.la.com.medicaltreatmentapplication.widget.RefreshListView;
import okhttp3.Call;

public class MyStudentsActivity extends BaseActivity {

    @BindView(R.id.students_list)
    RefreshListView studentsList;
    private View head_View;
    private Context context;
    String departmentId = "";
    String grade = "";
    int page = 1;
    private LinearLayout departmenttype,gradeselect;
    private TextView departmenttext,gradetext;
    private Button querydata;
    PopupWindow office_popupWindow,grade_popupWindow;
    private Dialog proDialog;
    List<MyStudentListBean.ResultsBean.EmpsBean> empsBeanList;
    MyStudentListAdapter adapter;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_my_students;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_my_students;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        proDialog = new Dialog(MyStudentsActivity.this,R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        context = this;
        head_View = LayoutInflater.from(context).inflate(R.layout.my_studentlist_headview,null);
        departmenttype = (LinearLayout) head_View.findViewById(R.id.departmenttype);
        gradeselect = (LinearLayout) head_View.findViewById(R.id.gradeselect);
        departmenttext = (TextView) head_View.findViewById(R.id.departmenttext);
        gradetext = (TextView) head_View.findViewById(R.id.gradetext);
        initHeadViewLinster();
        querydata = (Button) head_View.findViewById(R.id.querydata);
        studentsList.addHeaderView(head_View);
    }

    private void initHeadViewLinster() {
        departmenttype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                office_popupWindow.showAsDropDown(departmenttype);
            }
        });
        gradeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grade_popupWindow.setFocusable(true);
                grade_popupWindow.setOutsideTouchable(true);
                grade_popupWindow.setTouchable(true);
                grade_popupWindow .setBackgroundDrawable(new BitmapDrawable());
                grade_popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            grade_popupWindow.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                grade_popupWindow.showAsDropDown(gradeselect);

            }
        });
    }

    @Override
    public void initData() {
        loadDataUse();
        initLinister();
    }

    private void initLinister() {
        querydata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
                loadDataUse();
            }
        });
    }

    private void loadDataUse() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/MyStudents?DepartmentId="+departmentId+"&Grade="+grade+"&page="+page+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson  = new Gson();
                        MyStudentListBean studentBean= gson.fromJson(response,MyStudentListBean.class);
                        if (studentBean.isStatus()){
                            proDialog.dismiss();
                            initDepartmentsPopupWindow(departmenttext,studentBean);
                            initGradePopupWindow(gradetext,studentBean);
                            empsBeanList = studentBean.getResults().getEmps();
                            adapter = new MyStudentListAdapter(context,empsBeanList);
                            studentsList.setAdapter(adapter);
                            studentsList.setRefreshListener(new RefreshListView.OnRefreshListener() {
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
                            studentsList.onRefreshComplete();
                            ToastUtils.showToastSafe(MyStudentsActivity.this,"没有其他数据");
                        }

                    }
                });
    }

    private void loadUpdateData() {
        OkHttpUtils
                .get()
                .url(Content.BASE_URL+"AppPersonelCenter/MyStudents?DepartmentId="+departmentId+"&Grade="+grade+"&page="+page+"&Token="+ PrefUtils.getString(getBaseContext(),"token",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        MyStudentListBean studentBean= gson.fromJson(response,MyStudentListBean.class);
                        if (studentBean.isStatus()){
                            List<MyStudentListBean.ResultsBean.EmpsBean> empsBeanLists = studentBean.getResults().getEmps();
                            if (studentBean != null && studentBean.toString() !="[]"&&page!= 1){
                                for (int i = 0; i <empsBeanLists.size() ; i++) {
                                    empsBeanList.add(empsBeanLists.get(i));
                                }
                                //showSelectScheduleData(rotas);
                                adapter.notifyDataSetChanged();
                                studentsList.onRefreshComplete();
                            }else {
                                page -- ;
                                studentsList.onRefreshComplete();
                                ToastUtils.showToastSafe(MyStudentsActivity.this,"没有其他数据");
                            }
                            studentsList.setRefreshListener(new RefreshListView.OnRefreshListener() {
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
                            studentsList.onRefreshComplete();
                        }
                    }
                });
    }


    @OnClick(R.id.turn_last)
    public void onClick() {
        finish();
    }
    //科室
    private void initDepartmentsPopupWindow(final TextView text, final MyStudentListBean data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getDeparts().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getDeparts().get(i).getName());
            map.put("id", String.valueOf(data.getResults().getDeparts().get(i).getId()));
            list.add(map);
        }
        View view = LayoutInflater.from(MyStudentsActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(MyStudentsActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        office_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                departmentId = list.get(position).get("id");
                office_popupWindow.dismiss();

            }
        });
    }
    //科室
    private void initGradePopupWindow(final TextView text, final MyStudentListBean data) {
        final List<Map<String,String>> list = new ArrayList<>();
        for (int i = 0; i < data.getResults().getGrades().size(); i++) {
            Map<String,String> map = new HashMap<>();
            map.put("name",data.getResults().getGrades().get(i)+"");
            list.add(map);
        }
        View view = LayoutInflater.from(MyStudentsActivity.this).inflate(R.layout.office_list, null);
        ListView officelist = (ListView) view.findViewById(R.id.officelist);
        SimpleAdapter adapter = new SimpleAdapter(MyStudentsActivity.this, list,R.layout.office_item,new String[]{"name"},new int[]{R.id.office_item});
        officelist.setAdapter(adapter);
        grade_popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        officelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {;
                text.setText(list.get(position).get("name"));
                grade = list.get(position).get("name");
                grade_popupWindow.dismiss();
            }
        });
    }
}
