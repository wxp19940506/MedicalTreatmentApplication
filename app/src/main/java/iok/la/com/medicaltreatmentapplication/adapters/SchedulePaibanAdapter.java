package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.OfficeScheduleDetailActivity;
import iok.la.com.medicaltreatmentapplication.activities.SchedulePaibanActivity;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfo;
import iok.la.com.medicaltreatmentapplication.bean.SchedulePaiBanBean;

/**
 * Created by Administrator on 2016/12/12 0012.
 */

public class SchedulePaibanAdapter extends BaseAdapter implements View.OnClickListener {
    private List<SchedulePaiBanBean.ResultsBean.DtBean.RowsBean> data;
    private Context context;
    public SchedulePaibanAdapter(Context context, List<SchedulePaiBanBean.ResultsBean.DtBean.RowsBean> data) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (holder == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.office_schedule_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.holiday_icon.setVisibility(View.INVISIBLE);
        if (data.get(position).getTooltip() != null){
            holder.holiday_icon.setVisibility(View.VISIBLE);
        }
        holder.name.setText(data.get(position).getTitle());
        if ( position == 0){
            holder.first_line.setVisibility(View.GONE);
        }
        if (position == data.size()-1){
            holder.border.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i <data.get(position).getCells().get(0).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(position).getCells().get(0).getItems().get(i).getName());
            holder.day1.addView(textView,params);
        }
        for (int i = 0; i <data.get(position).getCells().get(1).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(position).getCells().get(1).getItems().get(i).getName());
            holder.day2.addView(textView,params);
        }
        for (int i = 0; i <data.get(position).getCells().get(2).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(position).getCells().get(2).getItems().get(i).getName());
            holder.day3.addView(textView,params);
        }
        for (int i = 0; i <data.get(position).getCells().get(3).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(position).getCells().get(3).getItems().get(i).getName());
            textView.setGravity(Gravity.CENTER);
            holder.day4.addView(textView,params);
        }
        for (int i = 0; i <data.get(position).getCells().get(4).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(position).getCells().get(4).getItems().get(i).getName());
            holder.day5.addView(textView,params);
        }
        for (int i = 0; i <data.get(position).getCells().get(5).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setText(data.get(position).getCells().get(5).getItems().get(i).getName());
            textView.setGravity(Gravity.CENTER);
            holder.day6.addView(textView,params);
        }
        for (int i = 0; i <data.get(position).getCells().get(6).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            params.gravity = Gravity.CENTER;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(position).getCells().get(6).getItems().get(i).getName());
            holder.day7.addView(textView,params);
        }

        if (position % 2 == 0){
            convertView.setBackgroundResource(R.mipmap.white);
        }else{
            convertView.setBackgroundResource(R.mipmap.green);
        }
        holder.day1.setOnClickListener(this);
        holder.day2.setOnClickListener(this);
        holder.day3.setOnClickListener(this);
        holder.day4.setOnClickListener(this);
        holder.day5.setOnClickListener(this);
        holder.day6.setOnClickListener(this);
        holder.day7.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
    }

    class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.day1)
        LinearLayout day1;
        @BindView(R.id.day2)
        LinearLayout day2;
        @BindView(R.id.day3)
        LinearLayout day3;
        @BindView(R.id.day4)
        LinearLayout day4;
        @BindView(R.id.day5)
        LinearLayout day5;
        @BindView(R.id.day6)
        LinearLayout day6;
        @BindView(R.id.day7)
        LinearLayout day7;
        @BindView(R.id.holiday_icon)
        ImageView holiday_icon;
        View border,first_line;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            border = view.findViewById(R.id.border);
            first_line = view.findViewById(R.id.first_line);
        }
    }
}
