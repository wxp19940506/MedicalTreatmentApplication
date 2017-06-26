package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.MyPaiBanDetailActivity;
import iok.la.com.medicaltreatmentapplication.bean.MyScheduleDate;
import iok.la.com.medicaltreatmentapplication.bean.MyStudentScheduleListBean;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class MyStudentScheduleListAdapter extends BaseAdapter {
    private Context context;
    private List<List<MyStudentScheduleListBean.ResultsBean.DatetableBean.RowsBean.CellsBean>> items;

    public MyStudentScheduleListAdapter(Context context, List<List<MyStudentScheduleListBean.ResultsBean.DatetableBean.RowsBean.CellsBean>> items) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.my_student_schedule_item,parent,false);
            holder.sunday = (TextView) convertView.findViewById(R.id.sunday);
            holder.monday = (TextView) convertView.findViewById(R.id.monday);
            holder.tuesday = (TextView) convertView.findViewById(R.id.tuesday);
            holder.wednesday = (TextView) convertView.findViewById(R.id.wednesday);
            holder.thursday = (TextView) convertView.findViewById(R.id.thursday);
            holder.friday = (TextView) convertView.findViewById(R.id.friday);
            holder.saturday = (TextView) convertView.findViewById(R.id.saturday);
            holder.lastline =  convertView.findViewById(R.id.lastline);

            holder.sundayss = (LinearLayout) convertView.findViewById(R.id.sundayss);
            holder.mondayss = (LinearLayout) convertView.findViewById(R.id.mondayss);
            holder.tuesdayss = (LinearLayout) convertView.findViewById(R.id.tuesdayss);
            holder.wednesdayss = (LinearLayout) convertView.findViewById(R.id.wednesdayss);
            holder.thursdayss = (LinearLayout) convertView.findViewById(R.id.thursdayss);
            holder.fridayss = (LinearLayout) convertView.findViewById(R.id.fridayss);
            holder.saturdayss = (LinearLayout) convertView.findViewById(R.id.saturdayss);
            holder.root_background = (LinearLayout) convertView.findViewById(R.id.root_background);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (items.get(position).get(0) == null){
            holder.sunday.setText("");
        }else {
            holder.sunday.setText(items.get(position).get(0).getDateNo()+"");
            for (int i = 0; i <items.get(position).get(0).getItems().size() ; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
                params.weight=1;
                params.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText(items.get(position).get(0).getItems().get(i).getName());
                holder.sundayss.addView(textView,params);
            }
        }
        if (items.get(position).get(1) == null){
            holder.monday.setText("");
        }else {
            holder.monday.setText(items.get(position).get(1).getDateNo()+"");
            for (int i = 0; i <items.get(position).get(1).getItems().size() ; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            params.gravity = Gravity.CENTER;
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(items.get(position).get(1).getItems().get(i).getName());
            holder.mondayss.addView(textView,params);
            }
        }
        if (items.get(position).get(2) == null){
            holder.tuesday.setText("");
        }else {
            holder.tuesday.setText(items.get(position).get(2).getDateNo()+"");
            for (int i = 0; i <items.get(position).get(2).getItems().size() ; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
                params.weight=1;
                params.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText(items.get(position).get(2).getItems().get(i).getName());
                holder.tuesdayss.addView(textView,params);
            }
        }
        if (items.get(position).get(3) == null){
            holder.wednesday.setText("");
        }else {
            holder.wednesday.setText(items.get(position).get(3).getDateNo()+"");
            for (int i = 0; i <items.get(position).get(3).getItems().size() ; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
                params.weight=1;
                params.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText(items.get(position).get(3).getItems().get(i).getName());
                holder.wednesdayss.addView(textView,params);
            }
        }
        if (items.get(position).get(4) == null){
            holder.thursday.setText("");
        }else {
            holder.thursday.setText(items.get(position).get(4).getDateNo()+"");
            for (int i = 0; i <items.get(position).get(4).getItems().size() ; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
                params.weight=1;
                params.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText(items.get(position).get(4).getItems().get(i).getName());
                holder.thursdayss.addView(textView,params);
            }
        }
        if (items.get(position).get(5) == null){
            holder.friday.setText("");
        }else {
            holder.friday.setText(items.get(position).get(5).getDateNo()+"");
            for (int i = 0; i <items.get(position).get(5).getItems().size() ; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
                params.weight=1;
                params.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText(items.get(position).get(5).getItems().get(i).getName());
                holder.fridayss.addView(textView,params);
            }
        }
        if (items.get(position).get(6) == null){
            holder.saturday.setText("");
        }else {
            holder.saturday.setText(items.get(position).get(6).getDateNo()+"");
            for (int i = 0; i <items.get(position).get(6).getItems().size() ; i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
                params.weight=1;
                params.gravity = Gravity.CENTER;
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText(items.get(position).get(6).getItems().get(i).getName());
                holder.saturdayss.addView(textView,params);
            }
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
        private LinearLayout sundayss,mondayss,tuesdayss,wednesdayss,thursdayss,fridayss,saturdayss;
        private LinearLayout root_background;
        private View lastline;
    }

}
