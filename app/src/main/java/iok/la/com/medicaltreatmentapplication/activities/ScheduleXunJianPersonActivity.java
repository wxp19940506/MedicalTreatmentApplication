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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.BaseActivity;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.adapters.OtherClassAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.OtherSchedulePersonAdapter;
import iok.la.com.medicaltreatmentapplication.adapters.TodaySchedulePersonAdapter;
import iok.la.com.medicaltreatmentapplication.bean.OtherClassData;
import iok.la.com.medicaltreatmentapplication.bean.XunJianPersonTotalPersons;
import iok.la.com.medicaltreatmentapplication.util.Content;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteGridView;
import iok.la.com.medicaltreatmentapplication.widget.ScheduleNoteListView;
import okhttp3.Call;

import static android.R.attr.data;

public class ScheduleXunJianPersonActivity extends BaseActivity {
    Intent intent ;
    String date;
    ScheduleNoteGridView scheperson_now;
    ScheduleNoteListView scheperson_total;
    List<Map>  gess,nowgess;
    Button submit;
    EditText search_keyword;
    private ImageButton turn_last;
    private OtherClassData classDatas;
    @Override
    public int getLayoutStyle() {
        return R.layout.activity_schedule_xun_jian_person;
    }

    @Override
    public int getLayoutId() {
        return R.id.activity_schedule_xun_jian_person;
    }

    @Override
    public void init() {
        mActivity = this;
    }

    @Override
    public void initView() {
        submit = (Button) findViewById(R.id.submit);
        search_keyword = (EditText) findViewById(R.id.search_keyword);
        scheperson_now = (ScheduleNoteGridView) findViewById(R.id.scheperson_now);
        scheperson_total = (ScheduleNoteListView) findViewById(R.id.scheperson_other);
        turn_last = (ImageButton) findViewById(R.id.turn_last);
        intent = getIntent();
        date = intent.getStringExtra("date");
        OtherClassData classData = (OtherClassData) intent.getSerializableExtra("persons");
        gess  = classData.getData();
        nowgess = new ArrayList<>();
        classDatas = new OtherClassData();
    }

    @Override
    public void initData() {
        initLinister();
        //其他值班人员
        BindOtherClassAdpterData(gess);

//        final OtherSchedulePersonAdapter adapter0 = new OtherSchedulePersonAdapter(ScheduleXunJianPersonActivity.this,gess);
//        scheperson_total.setAdapter(adapter0);
        //当前值班人员
        OkHttpUtils
                .post()
                .url(Content.BASE_URL+"AppRotas/GetCurrentGeneralEmployees"+"?Token="+token)
                .addParams("date",date)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        XunJianPersonTotalPersons nowTotalPersons = gson.fromJson(response, XunJianPersonTotalPersons.class);
                        if (nowTotalPersons.isStatus()){
                            searchKeyWord();
                            for (int i = 0; i <nowTotalPersons.getResults().size() ; i++) {
                                Map map = new HashMap();
                                map.put("name",nowTotalPersons.getResults().get(i).getName());
                                map.put("id",nowTotalPersons.getResults().get(i).getId());
                                map.put("pinyin",nowTotalPersons.getResults().get(i).getPinyin());
                                nowgess.add(map);
                            }
                            //当前值班人员
                            TodaySchedulePersonAdapter adapter1 = new TodaySchedulePersonAdapter(ScheduleXunJianPersonActivity.this,nowgess);
                            scheperson_now.setAdapter(adapter1);
                            adapter1.setClassDataLinister(new TodaySchedulePersonAdapter.OnTodaySchedulePersonIdLinister() {
                                @Override
                                public void OnTodayPersonIdData(Map map) {
                                    intent.setClass(ScheduleXunJianPersonActivity.this,ScheduleXunJianActivity.class);
                                    List<Map> list = mapToList(map);
                                    classDatas.setData(list);
                                }
                            });
                        }

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
                //其他值班人员筛选后

                List<Map> datasearch  = new ArrayList<Map>();
                for (int i = 0; i <gess.size() ; i++) {
                    Map myname = gess.get(i);
                    String datapinyin = (String) myname.get("pinyin");
                    if (datapinyin != null && search_keyword.getText().toString().trim() != "" && search_keyword.getText().toString().trim().equals(datapinyin)  ){
                        datasearch.add(gess.get(i));
                        if (i == gess.size()-1){
                            BindOtherClassAdpterData(datasearch);
                        }
                    }else {
                        ToastUtils.showToastSafe(ScheduleXunJianPersonActivity.this,"其他值班人员没有要查询的数据");
                        BindOtherClassAdpterData(datasearch);
                        if (search_keyword.getText().toString().trim().equals("") )
                            BindOtherClassAdpterData(gess);
                    }
                }

                //当前值班人员筛选后
                List<Map> todaysearch  = new ArrayList<Map>();
                for (int i = 0; i <nowgess.size() ; i++) {
                    Map myname = nowgess.get(i);
                    String datapinyin = (String) myname.get("pinyin");
                    if (datapinyin != null && search_keyword.getText().toString().trim() != "" && search_keyword.getText().toString().trim().equals(datapinyin)  ){
                        todaysearch.add(nowgess.get(i));
                        if (i == nowgess.size()-1){
                            BindTodayClassAdpterData(todaysearch);
                        }
                    }else {
                        ToastUtils.showToastSafe(ScheduleXunJianPersonActivity.this,"当前值班人员没有要查询的数据");
                        BindTodayClassAdpterData(todaysearch);
                        if (search_keyword.getText().toString().trim().equals("") )
                            BindTodayClassAdpterData(nowgess);
                    }
                }
            }
        });
    }

    private  List<Map> mapToList(Map<Integer, Map> data) {
        List<Map> listValue = new ArrayList<>(data.values());
        return listValue;
    }
    private void initLinister() {
        turn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((classDatas.getMap() !=null &&!classDatas.getMap().toString().equals("[]") )|| (classDatas.getData() != null && !classDatas.getData().equals("[]"))){
                    intent.putExtra("person",classDatas);
                    setResult(Content.RESULT_CODE5,intent);
                    finish();
                }else {
                    ToastUtils.showToastSafe(ScheduleXunJianPersonActivity.this,"请选择值班人员");
                }

            }
        });
    }

    private void BindOtherClassAdpterData(List<Map> datasearch) {
        final OtherSchedulePersonAdapter adapter0 = new OtherSchedulePersonAdapter(ScheduleXunJianPersonActivity.this,datasearch);
        scheperson_total.setAdapter(adapter0);
        adapter0.notifyDataSetChanged();
        adapter0.setClassDataLinister(new OtherSchedulePersonAdapter.OnOtherSchedulePersonIdLinister() {
            @Override
            public void OnOtherPersonIdData(final Map map) {
                intent.setClass(ScheduleXunJianPersonActivity.this,ScheduleXunJianActivity.class);
                //classData.setData(nowgess);
                classDatas.setMap(map);

            }
        });
    }
    private void BindTodayClassAdpterData(List<Map> datasearch) {
        TodaySchedulePersonAdapter adapter1 = new TodaySchedulePersonAdapter(ScheduleXunJianPersonActivity.this,datasearch);
        scheperson_now.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        adapter1.setClassDataLinister(new TodaySchedulePersonAdapter.OnTodaySchedulePersonIdLinister() {
            @Override
            public void OnTodayPersonIdData(Map map) {
                intent.setClass(ScheduleXunJianPersonActivity.this,ScheduleXunJianActivity.class);
                List<Map> list = mapToList(map);
                classDatas.setData(list);
            }
        });
    }
//
//    class XunJianPersonsAdapter extends BaseAdapter{
//        List<Map> results;
//        public XunJianPersonsAdapter(List<Map> results) {
//            this.results = results;
//        }
//
//        @Override
//        public int getCount() {
//            return results.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return results.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder =null;
//            if (holder == null){
//                holder = new ViewHolder();
//                convertView = LayoutInflater.from(ScheduleXunJianPersonActivity.this).inflate(R.layout.name_item,parent,false);
//                holder.name = (TextView) convertView.findViewById(R.id.name);
//                convertView.setTag(holder);
//            }else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//            holder.name.setText((String)results.get(position).get("name"));
//            return convertView;
//        }
//    }
//    class ViewHolder {
//        private TextView name;
//    }
}
