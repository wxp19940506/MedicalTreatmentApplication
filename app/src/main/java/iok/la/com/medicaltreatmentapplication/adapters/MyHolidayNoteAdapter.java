package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.bean.HolidayDepartment;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;
import iok.la.com.medicaltreatmentapplication.util.Content;

import static iok.la.com.medicaltreatmentapplication.R.id.createtime;
import static iok.la.com.medicaltreatmentapplication.R.id.requesttime;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class MyHolidayNoteAdapter extends BaseExpandableListAdapter {
    Context context;
    List<HolidayDepartment.ResultsBean.ExcludesBean> rotas;
    private int[] icons,images;

    public MyHolidayNoteAdapter(Context context, List<HolidayDepartment.ResultsBean.ExcludesBean> rotas) {
        this.context = context;
        this.rotas = rotas;
        icons = new int[]{R.mipmap.urgent,R.mipmap.major,R.mipmap.commony};
        images = new int[]{R.mipmap.perso,R.mipmap.treatment,R.mipmap.compelete};
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
        View view = LayoutInflater.from(context).inflate(R.layout.myholiday_group_item,null);
        RelativeLayout rootlayout = (RelativeLayout) view.findViewById(R.id.rootlayout);
        TextView holiday_name = (TextView) view.findViewById(R.id.holiday_name);
        TextView totaltime = (TextView) view.findViewById(R.id.totaltime);
        //TextView jinji = (TextView) view.findViewById(R.id.jinji);
        holiday_name.setText(rotas.get(groupPosition).getDepartmentName());
        String occurrenceTime = rotas.get(groupPosition).getBeginDate()+" - "+rotas.get(groupPosition).getEndDate();
//        long times = Long.parseLong(occurrenceTime.substring(occurrenceTime.indexOf("(")+1,occurrenceTime.indexOf(")")));
//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String s = sdf.format(new Date(times));
        totaltime.setText(occurrenceTime);
        ImageView image = (ImageView) view.findViewById(R.id.imageButton);
        Drawable icon1 = null;
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
        View view = LayoutInflater.from(context).inflate(R.layout.myholiday_child_item, null);
        TextView office_name = (TextView) view.findViewById(R.id.office_name);
        TextView person_name = (TextView) view.findViewById(R.id.person_name);
        TextView reason = (TextView) view.findViewById(R.id.reason);
        TextView start_time = (TextView) view.findViewById(R.id.start_time);
        TextView endtime = (TextView) view.findViewById(R.id.endtime);
        ImageView image_qingjia = (ImageView) view.findViewById(R.id.image_qingjia);
        String officename = rotas.get(groupPosition).getDepartmentName();
        String personname = rotas.get(groupPosition).getEmployeeName();
//        long ctimes = Long.parseLong(createTime.substring(createTime.indexOf("(")+1,createTime.indexOf(")")));
//        long rtimes = Long.parseLong(requestTime.substring(requestTime.indexOf("(")+1,requestTime.indexOf(")")));
//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String ctime = sdf.format(new Date(ctimes));
//        String rtime = sdf.format(new Date(rtimes));
        start_time.setText(rotas.get(groupPosition).getBeginDate()+" "+rotas.get(groupPosition).getBeginTime());
        endtime.setText(rotas.get(groupPosition).getEndDate()+" "+rotas.get(groupPosition).getEndTime());
        office_name.setText(officename);
        person_name.setText(personname);
        reason.setText(rotas.get(groupPosition).getReason());
        Picasso.with(context).load(Content.BASE_URL0+rotas.get(groupPosition).getPath()).error(R.mipmap.wei).placeholder(R.mipmap.wei).into(image_qingjia);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
