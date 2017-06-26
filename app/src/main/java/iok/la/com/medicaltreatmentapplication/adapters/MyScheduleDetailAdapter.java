package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.MyPaiBanDetailActivity;
import iok.la.com.medicaltreatmentapplication.bean.MyScheduleDetailBean;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class MyScheduleDetailAdapter extends BaseAdapter {
    private Context context;
    private List<MyScheduleDetailBean.ResultsBean.SftsBean> sfts;
    public MyScheduleDetailAdapter(Context context, List<MyScheduleDetailBean.ResultsBean.SftsBean> sfts) {
        this.context = context;
        this.sfts = sfts;
    }

    @Override
    public int getCount() {
        return sfts.size();
    }

    @Override
    public Object getItem(int position) {
        return sfts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.my_schedule_detail_item,parent,false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.department_name = (TextView) convertView.findViewById(R.id.department_name);
            holder.schedules = (TextView) convertView.findViewById(R.id.schedules);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(sfts.get(position).getName());
        holder.department_name.setText(sfts.get(position).getDepartmentName());
        holder.schedules.setText(sfts.get(position).getShiftPlanName());
        return convertView;
    }
    class ViewHolder {
        private TextView name,department_name,schedules;
        //private LinearLayout schedules;
    }
}
