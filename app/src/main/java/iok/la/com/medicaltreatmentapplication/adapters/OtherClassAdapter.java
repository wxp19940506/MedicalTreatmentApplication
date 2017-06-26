package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleOtherClassActivity;

/**
 * Created by Administrator on 2017/1/7 0007.
 */

public class OtherClassAdapter extends BaseAdapter {
    Context context;
    List<Map> data;
    HashMap selecteddata = new HashMap();
    List<Integer> codes;
    public OnOtherClassDataLinister getClassDataLinister() {
        return classDataLinister;
    }

    public void setClassDataLinister(OnOtherClassDataLinister classDataLinister) {
        this.classDataLinister = classDataLinister;
    }

    OnOtherClassDataLinister classDataLinister;
    public OtherClassAdapter(Context context, List<Map> data, List<Integer> codes) {
        this.context =context;
        this.data = data;
        this.codes = codes;
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
        ViewHolder holder =null;
        if (holder == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.otherclass_item,parent,false);
            holder.box = (CheckBox) convertView.findViewById(R.id.other_checkclass);
            convertView.setTag(convertView);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.box.setText((CharSequence) data.get(position).get("name"));
        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (data.get(position) != null)
                    selecteddata.put(position,data.get(position));
                    classDataLinister.OnOtherClassData(selecteddata);
                }else {
                    selecteddata.remove(position);
                    classDataLinister.OnOtherClassData(selecteddata);
                }
            }
        });
        if (codes != null){
            for (int i = 0; i < codes.size(); i++) {
                if (codes.get(i) == data.get(position).get("id")){
                    holder.box.setChecked(true);
                }
            }
        }

        return convertView;
    }
    class ViewHolder{
        CheckBox box;
    }
    public interface OnOtherClassDataLinister{
        void OnOtherClassData(Map map);
    }
}
