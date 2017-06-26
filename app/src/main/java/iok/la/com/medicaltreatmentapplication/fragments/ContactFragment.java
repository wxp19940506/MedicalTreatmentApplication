package iok.la.com.medicaltreatmentapplication.fragments;

import android.app.Dialog;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import iok.la.com.medicaltreatmentapplication.BaseFragment;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleEditJiaoJieActivity;
import iok.la.com.medicaltreatmentapplication.adapters.DepartmentContactAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.PersonContactAdapter;
import iok.la.com.medicaltreatmentapplication.bean.DepartmentContactData;
import iok.la.com.medicaltreatmentapplication.bean.PersonContactData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.PrefUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.RefreshListView;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/10 0010.
 */

public class ContactFragment extends BaseFragment {
    @BindView(R.id.person_address)
    RadioButton personAddress;
    @BindView(R.id.office_address)
    RadioButton officeAddress;
    @BindView(R.id.buttons)
    RadioGroup buttons;
    @BindView(R.id.total_address)
    RefreshListView totalAddress;
    private List<Map> nameData;
    private List<Map> scheduleData;
    @BindView(R.id.listcontain)
    LinearLayout listcontain;
    @BindView(R.id.search_keyword)
    EditText search_Keyword;
    String contact_url;
    String keyword = "";
    int page = 1;
    int contactTag = 1;
    List<PersonContactData.ResultsBean> totalPersonContact;
    List<DepartmentContactData.ResultsBean> totalDepartmentContact;
    PersonContactAdapter personAdapter;
    DepartmentContactAdapter contactAdapter;
    private Dialog proDialog;
    @Override
    public View initView() {
        proDialog = new Dialog(getActivity(),R.style.progress_dialog);
        proDialog.setContentView(R.layout.dialog);
        proDialog.setCancelable(true);
        proDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) proDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("加载中");
        proDialog.show();
        totalPersonContact = new ArrayList<>();
        totalDepartmentContact = new ArrayList<>();
        contact_url = Content.BASE_URL+"AppContact/EmployeeContact?page="+page+"&search="+keyword;
        nameData = new ArrayList<>();
        scheduleData = new ArrayList<>();
        View view = View.inflate(mActivity, R.layout.main_activity_contact, null);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initLinister() {
        buttons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.person_address:
                        proDialog.show();
                        contactTag = 1;
                        page = 1;
                        bindListViewAndAddress();
                        break;
                    case R.id.office_address:
                        proDialog.show();
                        contactTag = 2;
                        page = 1;
                        bindListViewAndAddress();
                        break;
                }
            }
        });
        //分页监听
        totalAddress.setRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                bindListViewAndAddress();

            }
        });
        //搜索的监听
        search_Keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                proDialog.show();
                keyword = s.toString();
                bindListViewAndAddress();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (keyword.equals("")){
                    proDialog.show();
                    keyword = "";
                    bindListViewAndAddress();
                }
            }
        });

    }


    @Override
    public void initData() {
        bindListViewAndAddress();
        initLinister();
    }
    private void bindListViewAndAddress() {
        if (contactTag == 1){
            contact_url = Content.BASE_URL+"AppContact/EmployeeContact?page="+page+"&search="+keyword+"&Token="+ PrefUtils.getString(getContext(),"token","");
        }else if (contactTag == 2){
            contact_url = Content.BASE_URL+"AppContact/DepartmentContact?page="+page+"&search="+keyword+"&Token="+ PrefUtils.getString(getContext(),"token","");
        }
        OkHttpUtils
                .get()
                .url(contact_url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        switch (contactTag){
                            case 1:
                                //解析员工通讯录并解析
                                Gson personGson = new Gson();
                                PersonContactData personData = personGson.fromJson(response,PersonContactData.class);
                                if (personData.isStatus()){
                                    proDialog.dismiss();
                                    if (page == 1){
                                        totalPersonContact.clear();
                                        totalPersonContact = personData.getResults();
                                        personAdapter = new PersonContactAdapter(getContext(),totalPersonContact);
                                        totalAddress.setAdapter(personAdapter);
                                    }else {
                                        if (personData.getResults() != null && !personData.getResults().toString().equals("[]") ){
                                            List<PersonContactData.ResultsBean> beanList = personData.getResults();
                                            for (int i = 0; i < beanList.size(); i++) {
                                                totalPersonContact.add(beanList.get(i));
                                            }
                                            personAdapter.notifyDataSetChanged();
                                            totalAddress.onRefreshComplete();
                                        }else {
                                            page -- ;
                                            ToastUtils.showToastSafe(getContext(),"暂无更多数据");
                                            totalAddress.onRefreshComplete();
                                        }

                                    }

                                }else {
                                    ToastUtils.showToastSafe(getContext(),"参数错误");
                                }
                                break;
                            case 2:
                                //解析科室通讯录并解析
                                Gson deparmentGson = new Gson();
                                DepartmentContactData departmentData= deparmentGson.fromJson(response,DepartmentContactData.class);
                                if (departmentData.isStatus()){
                                    proDialog.dismiss();
                                    if (page == 1){
                                        totalDepartmentContact.clear();
                                        totalDepartmentContact = departmentData.getResults();
                                        contactAdapter = new DepartmentContactAdapter(getContext(),totalDepartmentContact);
                                        totalAddress.setAdapter(contactAdapter);
                                    }else {
                                        if (departmentData.getResults() != null && !departmentData.getResults().toString().equals("[]") ){
                                            List<DepartmentContactData.ResultsBean> beanList = departmentData.getResults();
                                            for (int i = 0; i < beanList.size(); i++) {
                                                totalDepartmentContact.add(beanList.get(i));
                                            }
                                            contactAdapter.notifyDataSetChanged();
                                            totalAddress.onRefreshComplete();
                                        }else {
                                            page -- ;
                                            ToastUtils.showToastSafe(getContext(),"暂无更多数据");
                                            totalAddress.onRefreshComplete();
                                        }

                                    }

                                }else {
                                    ToastUtils.showToastSafe(getContext(),"参数错误");
                                }
                                break;
                        }

                    }
                });

    }
}
