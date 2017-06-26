package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.StudentDetailInfoActivity;
import iok.la.com.medicaltreatmentapplication.activities.StudentScheduleActivity;
import iok.la.com.medicaltreatmentapplication.bean.MyStudentListBean;

/**
 * Created by Administrator on 2017/2/7 0007.
 */

public class MyStudentListAdapter extends BaseAdapter {
    private Context context;
    private List<MyStudentListBean.ResultsBean.EmpsBean> empsBeanList;

    public MyStudentListAdapter(Context context, List<MyStudentListBean.ResultsBean.EmpsBean> empsBeanList) {
        this.context = context;
        this.empsBeanList = empsBeanList;
    }

    @Override
    public int getCount() {
        return empsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return empsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;
        if (holder == null){
            holder = new viewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.my_student_list_item,parent,false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.depatment_name = (TextView) convertView.findViewById(R.id.depatment_name);
            holder.work_num = (TextView) convertView.findViewById(R.id.work_num);
            holder.telephone_num = (TextView) convertView.findViewById(R.id.telephone_num);
            holder.student_schedule = (TextView) convertView.findViewById(R.id.student_schedule);
            holder.detail_info = (TextView) convertView.findViewById(R.id.detail_info);
            holder.root_layout = (LinearLayout) convertView.findViewById(R.id.root_layout);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.name.setText(empsBeanList.get(position).getName());
        holder.depatment_name.setText(empsBeanList.get(position).getDepartName());
        holder.work_num.setText(empsBeanList.get(position).getWorkNo());
        holder.telephone_num.setText(empsBeanList.get(position).getPhone());
        holder.student_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StudentScheduleActivity.class);
                intent.putExtra("employeeid",empsBeanList.get(position).getGuiPeiEmployeeId());
                intent.putExtra("name",empsBeanList.get(position).getName());
                context.startActivity(intent);
            }
        });
        holder.detail_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StudentDetailInfoActivity.class);
                intent.putExtra("employeeid",empsBeanList.get(position).getGuiPeiEmployeeId());
                context.startActivity(intent);
            }
        });
        if (position % 2 == 0){
            holder.root_layout.setBackgroundResource(R.color.login_background);
        }else {
            holder.root_layout.setBackgroundResource(R.color.white);
        }
        return convertView;
    }
    class viewHolder {
        private TextView name,depatment_name,work_num,telephone_num,student_schedule,detail_info;
        private LinearLayout root_layout;
    }
}
