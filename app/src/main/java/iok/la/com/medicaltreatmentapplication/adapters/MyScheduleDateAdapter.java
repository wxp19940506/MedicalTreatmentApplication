package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.MyPaiBanDetailActivity;
import iok.la.com.medicaltreatmentapplication.bean.MyScheduleDate;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class MyScheduleDateAdapter extends BaseAdapter {
    private Context context;
    List<List<MyScheduleDate.ResultsBean.ItemsBean>> items;

    public MyScheduleDateAdapter(Context context, List<List<MyScheduleDate.ResultsBean.ItemsBean>> items) {
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
            holder =  new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.my_scheduledate_item,parent,false);
            holder.sunday = (TextView) convertView.findViewById(R.id.sunday);
            holder.monday = (TextView) convertView.findViewById(R.id.monday);
            holder.tuesday = (TextView) convertView.findViewById(R.id.tuesday);
            holder.wednesday = (TextView) convertView.findViewById(R.id.wednesday);
            holder.thursday = (TextView) convertView.findViewById(R.id.thursday);
            holder.friday = (TextView) convertView.findViewById(R.id.friday);
            holder.saturday = (TextView) convertView.findViewById(R.id.saturday);
            holder.lastline =  convertView.findViewById(R.id.lastline);
            holder.sundayss = (TextView) convertView.findViewById(R.id.sundayss);
            holder.mondayss = (TextView) convertView.findViewById(R.id.mondayss);
            holder.tuesdayss = (TextView) convertView.findViewById(R.id.tuesdayss);
            holder.wednesdayss = (TextView) convertView.findViewById(R.id.wednesdayss);
            holder.thursdayss = (TextView) convertView.findViewById(R.id.thursdayss);
            holder.fridayss = (TextView) convertView.findViewById(R.id.fridayss);
            holder.saturdayss = (TextView) convertView.findViewById(R.id.saturdayss);
            holder.saturday0 = (LinearLayout) convertView.findViewById(R.id.saturday0);
            holder.monday0 = (LinearLayout) convertView.findViewById(R.id.monday0);
            holder.tuesday0 = (LinearLayout) convertView.findViewById(R.id.tuesday0);
            holder.wednesday0 = (LinearLayout) convertView.findViewById(R.id.wednesday0);
            holder.thursday0 = (LinearLayout) convertView.findViewById(R.id.thursday0);
            holder.friday0 = (LinearLayout) convertView.findViewById(R.id.friday0);
            holder.sunday0 = (LinearLayout) convertView.findViewById(R.id.sunday0);
            holder.root_background = (LinearLayout) convertView.findViewById(R.id.root_background);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (items.get(position).get(0) == null){
            holder.sunday.setText("");
            holder.sundayss.setText("");
        }else {
            holder.sunday.setText(items.get(position).get(0).getDay()+"");
            holder.sunday0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MyPaiBanDetailActivity.class);
                    intent.putExtra("date",items.get(position).get(0).getDate());
                    context.startActivity(intent);
                }
            });
        }
        if (items.get(position).get(1) == null){
            holder.monday.setText("");
            holder.mondayss.setText("");
        }else {
            holder.monday.setText(items.get(position).get(1).getDay()+"");
            holder.monday0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MyPaiBanDetailActivity.class);
                    intent.putExtra("date",items.get(position).get(1).getDate());
                    context.startActivity(intent);
                }
            });
        }
        if (items.get(position).get(2) == null){
            holder.tuesday.setText("");
            holder.tuesdayss.setText("");
        }else {
            holder.tuesday.setText(items.get(position).get(2).getDay()+"");
            holder.tuesday0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MyPaiBanDetailActivity.class);
                    intent.putExtra("date",items.get(position).get(2).getDate());
                    context.startActivity(intent);
                }
            });
        }
        if (items.get(position).get(3) == null){
            holder.wednesdayss.setText("");
            holder.wednesday.setText("");
        }else {
            holder.wednesday.setText(items.get(position).get(3).getDay()+"");
            holder.wednesday0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MyPaiBanDetailActivity.class);
                    intent.putExtra("date",items.get(position).get(3).getDate());
                    context.startActivity(intent);
                }
            });
        }
        if (items.get(position).get(4) == null){
            holder.thursdayss.setText("");
            holder.thursday.setText("");
        }else {
            holder.thursday.setText(items.get(position).get(4).getDay()+"");
            holder.thursday0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MyPaiBanDetailActivity.class);
                    intent.putExtra("date",items.get(position).get(4).getDate());
                    context.startActivity(intent);
                }
            });

        }
        if (items.get(position).get(5) == null){
            holder.friday.setText("");
            holder.fridayss.setText("");
        }else {
            holder.friday.setText(items.get(position).get(5).getDay()+"");
            holder.friday0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MyPaiBanDetailActivity.class);
                    intent.putExtra("date",items.get(position).get(5).getDate());
                    context.startActivity(intent);
                }
            });
        }
        if (items.get(position).get(6) == null){
            holder.saturday.setText("");
            holder.saturdayss.setText("");
        }else {
            holder.saturday.setText(items.get(position).get(6).getDay()+"");
            holder.saturday0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,MyPaiBanDetailActivity.class);
                    intent.putExtra("date",items.get(position).get(6).getDate());
                    context.startActivity(intent);
                }
            });
        }
        if (position %2 == 0){
            holder.root_background.setBackgroundResource(R.color.white);
        }else {
            holder.root_background.setBackgroundResource(R.color.login_background);
        }
        if (position == items.size() -1){
            holder.lastline.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        private TextView sunday,monday,tuesday,wednesday,thursday,friday,saturday;
        private TextView sundayss,mondayss,tuesdayss,wednesdayss,thursdayss,fridayss,saturdayss;
        private LinearLayout sunday0,monday0,tuesday0,wednesday0,thursday0,friday0,saturday0,root_background;
        private View lastline;
    }

}
