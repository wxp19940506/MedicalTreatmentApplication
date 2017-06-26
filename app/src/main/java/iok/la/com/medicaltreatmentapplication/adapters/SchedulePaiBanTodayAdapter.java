package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.SchedulePaibanTodayActivity;
import iok.la.com.medicaltreatmentapplication.bean.SchedulePaiBanTodayBean;
import iok.la.com.medicaltreatmentapplication.util.DensityUtil;

/**
 * Created by Administrator on 2017/2/5 0005.
 */

public class SchedulePaiBanTodayAdapter extends BaseAdapter {
    private Context context;
    private List<SchedulePaiBanTodayBean.ResultsBean.DtBean.RowsBean> data;

    public SchedulePaiBanTodayAdapter(Context context, List<SchedulePaiBanTodayBean.ResultsBean.DtBean.RowsBean> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.schedule_paiban_today_item,parent,false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.today_contains = (LinearLayout) convertView.findViewById(R.id.today_contains);
            holder.root_layout = (LinearLayout) convertView.findViewById(R.id.root_layout);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(data.get(position).getTitle());
        for (int i = 0; i < data.get(position).getCells().get(0).getItems().size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = DensityUtil.px2dip(context,10.0f);
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(position).getCells().get(0).getItems().get(i).getName());
            holder.today_contains.addView(textView,params);
        }
        if (position % 2 == 0){
            holder.root_layout.setBackgroundResource(R.color.blue);
        }else {
            holder.root_layout.setBackgroundResource(R.color.white);
        }
        return convertView;
    }
    class ViewHolder{
        private TextView name;
        private LinearLayout today_contains,root_layout;
    }
}
