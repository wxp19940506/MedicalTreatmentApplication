package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.R;

/**
 * Created by Administrator on 2017/1/7 0007.
 */

public class XunJianOfficeAdapter extends BaseAdapter {
    Context context;
    List<Map> data;
    HashMap selecteddata = new HashMap();


    public OnXunJianOfficeLinister getClassDataLinister() {
        return xunJianOfficeLinister;
    }

    public void setOnXunJianOfficeLinister(OnXunJianOfficeLinister classDataLinister) {
        this.xunJianOfficeLinister = classDataLinister;
    }

    OnXunJianOfficeLinister xunJianOfficeLinister;
    public XunJianOfficeAdapter(Context context, List<Map> data) {
        this.context =context;
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
                    xunJianOfficeLinister.OnXunJianOfficeData(selecteddata);
                }else {
                    selecteddata.remove(position);
                    xunJianOfficeLinister.OnXunJianOfficeData(selecteddata);
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        CheckBox box;
    }
    public interface OnXunJianOfficeLinister{
        void OnXunJianOfficeData(Map map);
    }
}
