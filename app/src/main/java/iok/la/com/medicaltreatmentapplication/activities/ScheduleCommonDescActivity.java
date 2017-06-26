package iok.la.com.medicaltreatmentapplication.activities;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.OtherClassAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OtherClassData;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;

public class ScheduleCommonDescActivity extends BaseActivity {
    private ListView commondesc_list;
    private Intent intent;
    private Map<Integer,Map> selectData = new HashMap<>();
    private Button enable;
    private ImageButton turn_last;
    private EditText search_keyword;
    String searchWord;
    List<Map> data;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_common_desc;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_common_desc;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        commondesc_list = (ListView) findViewById(R.id.commondesc_list);
        enable = (Button) findViewById(R.id.enable);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        search_keyword = (EditText) findViewById(R.id.search_keyword);
        intent = getIntent();
    }

    @Override
    public void initData() {
        OtherClassData classData = (OtherClassData) intent.getSerializableExtra("data");
        data = classData.getData();
        CommonDescAdapter adapter = new CommonDescAdapter(data);
        commondesc_list.setAdapter(adapter);
        View headView = LayoutInflater.from(ScheduleCommonDescActivity.this).inflate(R.layout.common_desc_headview,null);
        commondesc_list.addHeaderView(headView);
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectData.toString() != "{}"){
                    OtherClassData classData = new OtherClassData();
                    classData.setMap(selectData);
                    intent.putExtra("info",classData);
                    intent.setClass(ScheduleCommonDescActivity.this,ScheduleNoteActivity.class);
                    setResult(Content.RESULT_CODE4,intent);
                    finish();
                }
            }
        });
        search_keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Map> datasearch  = new ArrayList<Map>();
                searchWord = search_keyword.getText().toString().trim();
                if (searchWord ==null && searchWord == ""){
                    BindOtherClassAdpterData(data);
                }
                for (int i = 0; i < data.size(); i++) {
                    Map pinyin = data.get(i);
                    String datapinyin = (String) pinyin.get("pinyin");
                    if (searchWord.equals(datapinyin)){
                        datasearch.add(data.get(i));
                        if (i == datasearch.size()-1){
                            BindOtherClassAdpterData(datasearch);
                            return;
                        }
                    }else {
                        ToastUtils.showToastSafe(ScheduleCommonDescActivity.this,"没有要查询的数据");
                        BindOtherClassAdpterData(datasearch);
                        if (searchWord .equals("") )
                            BindOtherClassAdpterData(data);
                    }
                }
            }
        });
    }
    class CommonDescAdapter extends BaseAdapter{
        List<Map> data;
        public CommonDescAdapter(List<Map> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (holder == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(ScheduleCommonDescActivity.this).inflate(R.layout.common_desc_itemview,parent,false);
                holder.box = (CheckBox) convertView.findViewById(R.id.box);
                holder.summary = (TextView) convertView.findViewById(R.id.summary);
                holder.pinyin = (TextView) convertView.findViewById(R.id.pinyin);
                holder.content = (TextView) convertView.findViewById(R.id.content);
                holder.type = (TextView) convertView.findViewById(R.id.type);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.summary.setText((CharSequence) data.get(position).get("summary"));
            holder.pinyin.setText((CharSequence) data.get(position).get("pinyin"));
            holder.content.setText((CharSequence) data.get(position).get("content"));
            holder.type.setText((CharSequence) data.get(position).get("type"));
            holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        selectData.put(position,data.get(position));
                    }else {
                        selectData.remove(position);
                    }
                }
            });
            return convertView;
        }
    }
    class ViewHolder {
        private CheckBox box;
        private TextView  summary,pinyin,content,type;
    }
    public void BindOtherClassAdpterData(List<Map> dataselect){
        CommonDescAdapter adapter = new CommonDescAdapter(dataselect);
        commondesc_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectData.toString() != "{}"){
                    OtherClassData classData = new OtherClassData();
                    classData.setMap(selectData);
                    intent.putExtra("info",classData);
                    intent.setClass(ScheduleCommonDescActivity.this,ScheduleNoteActivity.class);
                    setResult(Content.RESULT_CODE4,intent);
                    finish();
                }
            }
        });
    }
}
