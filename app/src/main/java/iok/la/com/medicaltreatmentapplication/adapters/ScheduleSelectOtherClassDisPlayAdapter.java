package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;
import java.util.Map;

import iok.la.com.medicaltreatmentapplication.R;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class ScheduleSelectOtherClassDisPlayAdapter extends BaseAdapter {
    Context context;
    List<Map> data;
    public ScheduleSelectOtherClassDisPlayAdapter(Context context, List<Map> data) {
        this.data =data;
        this.context = context;
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
        ViewHolder  holder = null;
        if (holder == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.checkbox_displayview,null);
            holder.check = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.check.setText((String) data.get(position).get("title"));
        holder.check.setChecked(true);
        return convertView;
    }
    class ViewHolder{
        private CheckBox check;
    }

}
