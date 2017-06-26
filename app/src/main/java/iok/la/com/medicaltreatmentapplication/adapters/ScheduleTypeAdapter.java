package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.util.Log;
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
import iok.la.com.medicaltreatmentapplication.bean.Schedule_fixedData;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class ScheduleTypeAdapter extends BaseAdapter {
    List<Schedule_fixedData.ResultsBean.ShiftPlansBean> shiftPlans;
    Context context;
    Map data = new HashMap();
    List datas;

    public OnCheckDataLinister getDataLinister() {
        return dataLinister;
    }

    public void setDataLinister(OnCheckDataLinister dataLinister) {
        this.dataLinister = dataLinister;
    }

    OnCheckDataLinister dataLinister;
    public ScheduleTypeAdapter(Context context, List<Schedule_fixedData.ResultsBean.ShiftPlansBean> shiftPlans, List datas) {
        this.shiftPlans =shiftPlans;
        this.context = context;
        this.datas = datas;
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
                    int id = shiftPlans.get(position).getId();
                    data.put(position,id);
                    dataLinister.checkDataLinister(data);
                }else {
                    data.remove(position);
                }
            }
        });
        for (int i = 0; i <datas.size() ; i++) {
            String ids = String.valueOf(shiftPlans.get(position).getId());
            int id = Integer.parseInt(ids);
            int  aid  = (int) datas.get(i);
            if ( id == aid){
               holder.check.setChecked(true);
            }
        }
        return convertView;
    }
    class ViewHolder{
        private CheckBox check;
    }
    public interface OnCheckDataLinister{
        void checkDataLinister(Map data);
    }
}
