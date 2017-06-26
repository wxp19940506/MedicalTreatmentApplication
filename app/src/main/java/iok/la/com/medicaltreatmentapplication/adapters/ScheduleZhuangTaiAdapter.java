package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleNoteActivity;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteAddData;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class ScheduleZhuangTaiAdapter extends BaseAdapter {
    List<ScheduleNoteAddData.ResultsBean.RotaStatusBean> rotaCategories;
    Context context;
    List data = new ArrayList();
    int[] zhuangtaiIcons;
    List<CheckBox> boxs = new ArrayList<>();
    public ScheduleZhuangTaiAdapter(Context context, List<ScheduleNoteAddData.ResultsBean.RotaStatusBean> rotaCategories, int[] zhuangtaiIcons) {
        this.rotaCategories =rotaCategories;
        this.context = context;
        this.zhuangtaiIcons = zhuangtaiIcons;
    }


    public OnCheckDataLinister getDataLinister() {
        return dataLinister;
    }

    public void setDataLinister(OnCheckDataLinister dataLinister) {
        this.dataLinister = dataLinister;
    }

    OnCheckDataLinister dataLinister;

    @Override
    public int getCount() {
        return rotaCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return rotaCategories.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.checkbox_view,null);
            holder.check = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        boxs.add(holder.check);
        holder.check.setText(rotaCategories.get(position).getName());
        //holder.check.setCompoundDrawables(context.getResources().getDrawable(zhuangtaiIcons[position]),null,null,null);
        Drawable icon0 = context.getResources().getDrawable(zhuangtaiIcons[position]);
        Drawable icon1 = context.getResources().getDrawable(R.mipmap.selected);
        icon0.setBounds(0,0,icon0.getMinimumWidth(),icon0.getMinimumHeight());
        icon1.setBounds(0,0,icon1.getMinimumWidth(),icon1.getMinimumHeight());
        holder.check.setCompoundDrawables(icon0,null,icon1,null);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                List data = new ArrayList();
                for (int i = 0; i < boxs.size() ; i++) {
                    boxs.get(i).setChecked(false);
                }
                if (isChecked){
                    buttonView.setChecked(true);
                    int id = rotaCategories.get(position).getID();
                    data.add(id);
                    dataLinister.checkDataLinister(data);
                }else {
                    buttonView.setChecked(false);
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        private CheckBox check;
    }
    public interface OnCheckDataLinister{
        void checkDataLinister(List data);
    }
}
