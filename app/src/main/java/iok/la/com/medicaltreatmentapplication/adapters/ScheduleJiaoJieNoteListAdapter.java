package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleEditJiaoJieActivity;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleJiaoJieActivity;
import iok.la.com.medicaltreatmentapplication.bean.PostScheduleData;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class ScheduleJiaoJieNoteListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<PostScheduleData.ResultsBean.TransfersBean> rotas;

    public ScheduleJiaoJieNoteListAdapter(Context context, List<PostScheduleData.ResultsBean.TransfersBean> rotas) {
        this.context = context;
        this.rotas = rotas;
    }

    @Override
    public int getGroupCount() {
        return rotas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (rotas.size() != 0){
            return 1;
        }else {
            return 0;
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        return rotas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return rotas.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.schejiaoban_group_item,null);
        RelativeLayout rootlayout = (RelativeLayout) view.findViewById(R.id.rootlayout);
        TextView schedule_name = (TextView) view.findViewById(R.id.schedule_name);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView jiaoban = (TextView) view.findViewById(R.id.jiaoban);
        schedule_name.setText(rotas.get(groupPosition).getDapartmentName());
        String occurrenceTime = rotas.get(groupPosition).getReceiveDate();
//        long times = Long.parseLong(occurrenceTime.substring(occurrenceTime.indexOf("(")+1,occurrenceTime.indexOf(")")));
//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String s = sdf.format(new Date(times));
        time.setText(occurrenceTime);
        jiaoban.setText(rotas.get(groupPosition).getTransferStatusName());
        ImageView image = (ImageView) view.findViewById(R.id.imageButton);
        if (isExpanded) {
            image.setImageResource(R.mipmap.down);
            rootlayout.setBackgroundResource(R.drawable.schedule_display_style_shape);
        }else {
            image.setImageResource(R.mipmap.upper3);
            rootlayout.setBackgroundResource(R.drawable.commondesc_style_shape);
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.schejioabannote_child_item, null);
        TextView jiaoname = (TextView) view.findViewById(R.id.jiaoname);
        TextView jiename = (TextView) view.findViewById(R.id.jiename);
        TextView jietime = (TextView) view.findViewById(R.id.jietime);
        TextView jiaosum = (TextView) view.findViewById(R.id.jiaosum);
        TextView other = (TextView) view.findViewById(R.id.other);
        jiaoname.setText(rotas.get(groupPosition).getFromEmployeeName());
        jiename.setText(rotas.get(groupPosition).getToEmployeeName());
        jiaosum.setText((String)rotas.get(groupPosition).getSummary());
        other.setText((String)rotas.get(groupPosition).getDescription());
        if (rotas.get(groupPosition).getTransferDate() != null && rotas.get(groupPosition).getTransferDate().contains("(")&& rotas.get(groupPosition).getTransferDate().contains(")") ){
            String jieTime = rotas.get(groupPosition).getTransferDate();
            long jieTimes = Long.parseLong(jieTime.substring(jieTime.indexOf("(")+1,jieTime.indexOf(")")));
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String jieTimestring = sdf.format(new Date(jieTimes));
            jietime.setText(jieTimestring);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScheduleEditJiaoJieActivity.class);
                intent.putExtra("id",rotas.get(groupPosition).getID());
                context.startActivity(intent);
//                ((ScheduleJiaoJieActivity)context).finish();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
