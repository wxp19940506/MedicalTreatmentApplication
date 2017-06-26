package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.OfficeScheduleActivity;
import iok.la.com.medicaltreatmentapplication.bean.OfficeData;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class OfficelistAdapter extends BaseAdapter {
    List<OfficeData.ResultsBean.DepartmentsBean> departments;
    Context context;
    public OfficelistAdapter( Context context, List<OfficeData.ResultsBean.DepartmentsBean> departments) {
        this.departments = departments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return departments.size();
    }

    @Override
    public Object getItem(int position) {
        return departments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.office_item,parent,false);
        TextView office_item = (TextView) view.findViewById(R.id.office_item);
        office_item.setText(departments.get(position).getName());
        return view;
    }
}
