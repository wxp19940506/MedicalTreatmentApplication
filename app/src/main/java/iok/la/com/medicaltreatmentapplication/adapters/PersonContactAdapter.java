package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.PersonContactData;

/**
 * Created by Administrator on 2017/1/19 0019.
 */

public class PersonContactAdapter extends BaseAdapter {
    private Context context;
    private List<PersonContactData.ResultsBean> results;
    public PersonContactAdapter(Context context, List<PersonContactData.ResultsBean> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.otherName = (TextView) convertView.findViewById(R.id.othername);
            holder.telePhone = (TextView) convertView.findViewById(R.id.telephone);
            holder.contactPhone = (TextView) convertView.findViewById(R.id.contactphone);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(results.get(position).getName());
        holder.otherName.setText("科室名称："+results.get(position).getDepartmentName());
        holder.telePhone.setText("移动电话："+results.get(position).getCellPhone());
        holder.contactPhone.setText("办公电话："+results.get(position).getOfficePhone());
        holder.telePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneToSomeone(results.get(position).getCellPhone());
            }
        });
        holder.contactPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneToSomeone(results.get(position).getOfficePhone());
            }
        });
        return convertView;
    }

    private void phoneToSomeone(String cellPhone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + cellPhone));
        //开启系统拨号器
        context.startActivity(intent);
    }

    class ViewHolder{
        private TextView name,otherName,telePhone,contactPhone;
    }
}
