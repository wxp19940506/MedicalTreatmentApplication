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
import iok.la.com.medicaltreatmentapplication.activities.ScheduleNoteEditActivity;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteAddData;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteEditData;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class ScheduleJinJIEditAdapter extends BaseAdapter {
    List<ScheduleNoteEditData.ResultsBean.RotaImportancesBean> rotaCategories;
    Context context;
    List<CheckBox> boxs = new ArrayList<>();
    int[] jinjiIcons;
    int rotaStatusID;
    public ScheduleJinJIEditAdapter(Context context, List<ScheduleNoteEditData.ResultsBean.RotaImportancesBean> rotaCategories, int[] jinjiIcons,int rotaStatusID) {
        this.rotaCategories =rotaCategories;
        this.context = context;
        this.jinjiIcons = jinjiIcons;
        this.rotaStatusID = rotaStatusID;
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
        Drawable icon0 = context.getResources().getDrawable(jinjiIcons[position]);
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
                if (rotaStatusID == rotaCategories.get(position).getID()){
                    buttonView.setChecked(true);
                }
                if (isChecked){
                    buttonView.setChecked(true);
                    int id = rotaCategories.get(position).getID();
                    data.add(id);
                    dataLinister.checkDataLinister(data);
                }else {
                    buttonView.setChecked(false);
                }
// else {
//                    data.remove(position);
//                }
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
