package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.MyNewsActivity;
import iok.la.com.medicaltreatmentapplication.activities.MyNewsDetailActivity;
import iok.la.com.medicaltreatmentapplication.bean.MyNewsData;

/**
 * Created by thinkpad on 2017/2/7.
 */

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private List<MyNewsData.ResultsBean.MsgsBean> msgs;
    public NewsListAdapter(Context context, List<MyNewsData.ResultsBean.MsgsBean> msgs) {
        this.context = context;
        this.msgs = msgs;
    }

    @Override
    public int getCount() {
        return msgs.size();
    }

    @Override
    public Object getItem(int position) {
        return msgs.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item,parent,false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.detail = (TextView) convertView.findViewById(R.id.detail);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.message_icon = (ImageView) convertView.findViewById(R.id.message_icon);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(msgs.get(position).getTitle());
        holder.detail.setText(msgs.get(position).getDescription());
        holder.date.setText(msgs.get(position).getSendDate());
        if (msgs.get(position).isIsRead()){
            holder.message_icon.setImageResource(R.mipmap.message_yi);
        }else {
            holder.message_icon.setImageResource(R.mipmap.message_wei);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MyNewsDetailActivity.class);
                intent.putExtra("msgid",msgs.get(position).getID());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        private TextView title,detail,date;
        private ImageView message_icon;
    }
}
