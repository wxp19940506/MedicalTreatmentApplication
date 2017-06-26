package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleEditXunJianActivity;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleXunJianDetailActivity;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleXunJian;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class ScheduleXunJianAdapter extends BaseAdapter {
    Context context;
    List<ScheduleXunJian.ResultsBean.PatrolsBean> items;
    public ScheduleXunJianAdapter(Context context, List<ScheduleXunJian.ResultsBean.PatrolsBean> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.schedulexunjian_item,parent,false);
            holder.xunJianTime = (TextView) convertView.findViewById(R.id.xunjiantime_textview);
            holder.createTime = (TextView) convertView.findViewById(R.id.createtime_textview);
            holder.descrition = (TextView) convertView.findViewById(R.id.xunjian_descrition);
            holder.getmore = (TextView) convertView.findViewById(R.id.show_more);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (items.get(position).getPatrolDate() != null){
            String xunTimes = items.get(position).getPatrolDate();
            String xunTime = xunTimes.substring(xunTimes.indexOf("(")+1,xunTimes.indexOf(")"));
            long  xunjiantimeString= Long.parseLong(xunTime);
            String xunJianTimeText = sdf.format(new Date(xunjiantimeString));
            holder.xunJianTime.setText(xunJianTimeText);
        }
        if (items.get(position).getCreatedDate() != null) {
            String createTimes = items.get(position).getCreatedDate();
            String createTime = createTimes.substring(createTimes.indexOf("(")+1,createTimes.indexOf(")"));
            long  createtimeString = Long.parseLong(createTime);
            String createTimeText = sdf.format(new Date(createtimeString));
            holder.createTime.setText(createTimeText);
        }
        String descriptions = null;
        if (items.get(position).getDescription() == null){
            descriptions = "暂无描述";
        }else {
            descriptions = items.get(position).getDescription();
        }
        holder.descrition.setText("描述:"+descriptions);
        holder.getmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ScheduleXunJianDetailActivity.class);
                intent.putExtra("id",items.get(position).getID());
                context.startActivity(intent);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ScheduleEditXunJianActivity.class);
                intent.putExtra("id",items.get(position).getID());
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String xunTimes = items.get(position).getPatrolDate();
                String xunTime = xunTimes.substring(xunTimes.indexOf("(")+1,xunTimes.indexOf(")"));
                long  xunjiantimeString= Long.parseLong(xunTime);
                String xunJianTimeText = sdf.format(new Date(xunjiantimeString));
                intent.putExtra("date",xunJianTimeText);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        private TextView xunJianTime,createTime,descrition,getmore;
    }
}
