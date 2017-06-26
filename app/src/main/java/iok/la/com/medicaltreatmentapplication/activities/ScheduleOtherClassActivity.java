package iok.la.com.medicaltreatmentapplication.activities;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.OtherClassAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OtherClassData;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.util.Content;

public class ScheduleOtherClassActivity extends BaseActivity {
    private ListView other_class_data;
    List<Map> data;
    EditText search_class;
    Intent intent;
    Button enable;
    private ImageButton turn_last;
    List<Integer> codes;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_other_class;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_other_class;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        codes = new ArrayList<>();
        other_class_data = (ListView) findViewById(R.id.other_class_data);
        search_class = (EditText) findViewById(R.id.search_class);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        enable = (Button) findViewById(R.id.enable);
        intent = getIntent();
    }

    @Override
    public void initData() {
        OtherClassData classData = (OtherClassData) intent.getSerializableExtra("data");
        data = classData.getData();
        codes = classData.getSelectid();
        BindOtherClassAdpterData(data);
        initLinister();
    }

    private void initLinister() {
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtherClassData otherClassData = new OtherClassData();
                otherClassData.setMap(null);
                intent.setClass(ScheduleOtherClassActivity.this,ScheduleNoteActivity.class);
                intent.putExtra("info",otherClassData);
                setResult(Content.RESULT_CODE3,intent);
                finish();
            }
        });
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_class.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Map> datasearch  = new ArrayList<Map>();
                String searchWord = search_class.getText().toString().trim();
                if (searchWord ==null && searchWord == ""){
                    BindOtherClassAdpterData(data);
                }
                for (int i = 0; i < data.size(); i++) {
                    String datapinyin = (String) data.get(i).get("pinyin");
                    if (datapinyin != null && searchWord != "" && searchWord.equals(datapinyin)  ){
                        datasearch.add(data.get(i));
                        if (i == datasearch.size()-1){
                            BindOtherClassAdpterData(datasearch);
                            return;
                        }
                    }else {
                        ToastUtils.showToastSafe(ScheduleOtherClassActivity.this,"没有要查询的数据");
                        BindOtherClassAdpterData(datasearch);
                        if (searchWord .equals("") )
                            BindOtherClassAdpterData(data);
                        //return;
                    }
                }
            }
        });
    }
    public void BindOtherClassAdpterData(List<Map> dataselect){
        OtherClassAdapter adapter = new OtherClassAdapter(ScheduleOtherClassActivity.this,dataselect,codes);
        other_class_data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setClassDataLinister(new OtherClassAdapter.OnOtherClassDataLinister() {
            @Override
            public void OnOtherClassData(final Map map) {
                Log.e("map",map.toString());
                enable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map.toString() == "{}"){
                            OtherClassData otherClassData = new OtherClassData();
                            otherClassData.setMap(map);
                            intent.setClass(ScheduleOtherClassActivity.this,ScheduleNoteActivity.class);
                            intent.putExtra("info",otherClassData);
                            setResult(Content.RESULT_CODE3,intent);
                        }else {
                            OtherClassData otherClassData = new OtherClassData();
                            otherClassData.setMap(map);
                            intent.setClass(ScheduleOtherClassActivity.this,ScheduleNoteActivity.class);
                            intent.putExtra("info",otherClassData);
                            setResult(Content.RESULT_CODE3,intent);
                        }
                        finish();
                    }
                });
            }
        });
    }
}
