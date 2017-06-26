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
import iok.la.com.medicaltreatmentapplication.bean.Schedule_circleData;
import iok.la.com.medicaltreatmentapplication.bean.Schedule_fixedData;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class ScheduleFrequenciesAdapter extends BaseAdapter {
    List<Schedule_circleData.ResultsBean.FrequenciesBean> shiftPlans;
    Context context;
    Map<Integer,Integer> data = new HashMap<>();
    int frequencyID;
    public OnCheckDataLinister getDataLinister() {
        return dataLinister;
    }

    public void setDataLinister(OnCheckDataLinister dataLinister) {
        this.dataLinister = dataLinister;
    }

    OnCheckDataLinister dataLinister;
    public ScheduleFrequenciesAdapter(Context context, List<Schedule_circleData.ResultsBean.FrequenciesBean> shiftPlans, int frequencyID) {
        this.shiftPlans =shiftPlans;
        this.context = context;
        this.frequencyID = frequencyID;
    }

    @Override
    public int getCount() {
        return shiftPlans.size();
    }

    @Override
    public Object getItem(int position) {
        return shiftPlans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder  holder = null;
        if (holder == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.button_view,null);
            holder.check = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.check.setText(shiftPlans.get(position).getName());
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    data.put(position,shiftPlans.get(position).getId());
                    dataLinister.CheckDataLinister(data);
                }else{
                    data.remove(position);
                    dataLinister.CheckDataLinister(data);
                }
            }
        });
        if (frequencyID != 0){
            holder.check.setChecked(true);
        }
        return convertView;
    }
    class ViewHolder{
        private CheckBox check;
    }
    public interface OnCheckDataLinister{
        void CheckDataLinister(Map<Integer,Integer> data);
    }
}
