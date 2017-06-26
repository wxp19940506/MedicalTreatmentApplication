package iok.la.com.medicaltreatmentapplication.activities;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.OtherSchedulePersonAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.TodaySchedulePersonAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.XunJianOfficeAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.XunJianOtherOfficeAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OtherClassData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteListView;

public class XunJianOfficeActivity extends BaseActivity {
    private ScheduleNoteListView common_office,other_office;
    private Intent intent;
    private List<Map> data0,data1;
    XunJianOfficeAdapter adapter0;
    XunJianOtherOfficeAdapter adapter1;
    private Map<Integer,Map> toldata;
    private TextView title,common,other;
    int id;
    private ImageButton turn_last;
    Button submit;
    private EditText search_keyword;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_xun_jian_office;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_xun_jian_office;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        toldata = new HashMap<>();
        data0 = new ArrayList<>();
        data1 = new ArrayList<>();
        submit = (Button) findViewById(R.id.submit);
        search_keyword = (EditText) findViewById(R.id.search_keyword);
        common_office = (ScheduleNoteListView) findViewById(R.id.common_office);
        other_office = (ScheduleNoteListView) findViewById(R.id.other_office);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        title = (TextView) findViewById(R.id.title);
        common = (TextView) findViewById(R.id.common);
        other = (TextView) findViewById(R.id.other);
    }

    @Override
    public void initData() {
        intent = getIntent();
        id = intent.getIntExtra("id",0);
        if (id == 1){
            title.setText("巡检内容");
            common.setText("常用巡检内容");
            other.setText("其他巡检内容");
        }
        OtherClassData classData = (OtherClassData) intent.getSerializableExtra("office");
        data0 = classData.getData();
        data1 = classData.getData0();
        adapter0 = new XunJianOfficeAdapter(XunJianOfficeActivity.this,data0);
        common_office.setAdapter(adapter0);
        adapter1 = new XunJianOtherOfficeAdapter(XunJianOfficeActivity.this,data1);
        other_office.setAdapter(adapter1);
        searchKeyWord();
        initLinister();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("toldata",toldata.toString());
                if (id == 0){
                    turnToActivity(Content.RESULT_CODE6,id);
                }else if (id == 1){
                    turnToActivity(Content.RESULT_CODE7,id);
                }
            }
        });

    }

    private void turnToActivity(int code,int id) {
        if (toldata != null && !toldata.toString().equals("{}")){
            intent.setClass(XunJianOfficeActivity.this,ScheduleAddXunJianActivity.class);
            OtherClassData classData = new OtherClassData();
            classData.setMap(toldata);
            intent.putExtra("data",classData);
            setResult(code,intent);
            finish();
        }else {
            if (id == 0){
                ToastUtils.showToastSafe(XunJianOfficeActivity.this,"请选择巡检科室");
            } else if (id == 1) {
                ToastUtils.showToastSafe(XunJianOfficeActivity.this,"请选择巡检内容");

            }
        }

    }

    private void initLinister() {
        adapter0.setOnXunJianOfficeLinister(new XunJianOfficeAdapter.OnXunJianOfficeLinister() {
            @Override
            public void OnXunJianOfficeData(Map map) {
                toldata.put(0,map);
            }
        });
        adapter1.setOnXunJianOtherOfficeLinisterLinister(new XunJianOtherOfficeAdapter.OnXunJianOtherOfficeLinisterLinister() {
            @Override
            public void OnXunJianOtherOfficeData(Map map) {
                toldata.put(1,map);
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void searchKeyWord() {
        search_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //其他筛选后
                List<Map> datasearch  = new ArrayList<Map>();
                for (int i = 0; i <data0.size() ; i++) {
                    Map myname = data0.get(i);
                    String datapinyin = (String) myname.get("pinyin");
                    if (datapinyin != null && search_keyword.getText().toString().trim() != "" && search_keyword.getText().toString().trim().equals(datapinyin)  ){
                        datasearch.add(data0.get(i));
                        if (i == data0.size()-1){
                            BindOtherClassAdpterData(datasearch);
                        }
                    }else {
                        if (id == 1){
                            ToastUtils.showToastSafe(XunJianOfficeActivity.this,"常用巡检内容没有要查询的数据");
                        }else {
                            ToastUtils.showToastSafe(XunJianOfficeActivity.this,"常用巡检科室没有要查询的数据");
                        }
                        BindOtherClassAdpterData(datasearch);
                        if (search_keyword.getText().toString().trim().equals("") )
                            BindOtherClassAdpterData(data0);
                    }
                }

                //常用筛选后
                List<Map> todaysearch  = new ArrayList<Map>();
                for (int i = 0; i <data1.size() ; i++) {
                    Map myname = data1.get(i);
                    String datapinyin = (String) myname.get("pinyin");
                    if (datapinyin != null && search_keyword.getText().toString().trim() != "" && search_keyword.getText().toString().trim().equals(datapinyin)  ){
                        todaysearch.add(data1.get(i));
                        if (i == data1.size()-1){
                            BindTodayClassAdpterData(todaysearch);
                        }
                    }else {
                        if (id == 1){
                            ToastUtils.showToastSafe(XunJianOfficeActivity.this,"其他巡检内容室没有要查询的数据");
                        }else {
                            ToastUtils.showToastSafe(XunJianOfficeActivity.this,"其他巡检科室没有要查询的数据");
                        }
                        BindTodayClassAdpterData(todaysearch);
                        if (search_keyword.getText().toString().trim().equals("") )
                            BindTodayClassAdpterData(data1);
                    }
                }
            }
        });
    }
    private void BindOtherClassAdpterData(List<Map> datasearch) {
        adapter0 = new XunJianOfficeAdapter(XunJianOfficeActivity.this,datasearch);
        common_office.setAdapter(adapter0);
        adapter0.notifyDataSetChanged();
        initLinister();
    }
    private void BindTodayClassAdpterData(List<Map> datasearch) {
        adapter1 = new XunJianOtherOfficeAdapter(XunJianOfficeActivity.this,datasearch);
        other_office.setAdapter(adapter1);
        adapter0.notifyDataSetChanged();
        initLinister();
    }
}
