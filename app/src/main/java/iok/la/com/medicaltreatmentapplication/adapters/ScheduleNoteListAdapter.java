package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.EditScheduleActivity;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleNoteDisPlayActivity;
import iok.la.com.medicaltreatmentapplication.activities.ScheduleNoteEditActivity;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfoTime;
import iok.la.com.medicaltreatmentapplication.bean.ScheduleNoteData;
import iok.la.com.medicaltreatmentapplication.util.DensityUtil;
import iok.la.com.medicaltreatmentapplication.util.ThreadUtils;

import static iok.la.com.medicaltreatmentapplication.R.id.updown;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class ScheduleNoteListAdapter extends BaseExpandableListAdapter {
    Context context;
    List<ScheduleNoteData.ResultsBean.RotasBean> rotas;
    private int[] icons,images;

    public ScheduleNoteListAdapter(Context context, List<ScheduleNoteData.ResultsBean.RotasBean> rotas) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.schenote_group_item,null);
        RelativeLayout rootlayout = (RelativeLayout) view.findViewById(R.id.rootlayout);
        TextView schedule_name = (TextView) view.findViewById(R.id.schedule_name);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView jinji = (TextView) view.findViewById(R.id.jinji);
        schedule_name.setText(rotas.get(groupPosition).getDepartment().getName());
        String occurrenceTime = rotas.get(groupPosition).getOccurrenceTime();
        long times = Long.parseLong(occurrenceTime.substring(occurrenceTime.indexOf("(")+1,occurrenceTime.indexOf(")")));
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(new Date(times));
        time.setText(s);
        jinji.setText(rotas.get(groupPosition).getRotaImportance().getName());
        ImageView image = (ImageView) view.findViewById(R.id.imageButton);
        Drawable icon1 = null;
        if (rotas.get(groupPosition).getRotaImportance().getName().equals("紧急")){
            icon1 = context.getResources().getDrawable(icons[0]);

        }else if (rotas.get(groupPosition).getRotaImportance().getName().equals("重大")){
            icon1 = context.getResources().getDrawable(icons[1]);

        }else if (rotas.get(groupPosition).getRotaImportance().getName().equals("一般")){
            icon1 = context.getResources().getDrawable(icons[2]);

        }
        icon1.setBounds(0,0,icon1.getMinimumWidth(),icon1.getMinimumHeight());
        jinji.setCompoundDrawables(icon1,null,null,null);
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
        View view = LayoutInflater.from(context).inflate(R.layout.schenote_child_item, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView createtime = (TextView) view.findViewById(R.id.createtime);
        TextView requesttime = (TextView) view.findViewById(R.id.requesttime);
        TextView zhuangtai = (TextView) view.findViewById(R.id.zhuangtai);
        TextView descrition = (TextView) view.findViewById(R.id.descrition);
        TextView sortclass = (TextView) view.findViewById(R.id.sortclass);
        String createTime = rotas.get(groupPosition).getCreatedDate();
        String requestTime = rotas.get(groupPosition).getRequestTime();
        long ctimes = Long.parseLong(createTime.substring(createTime.indexOf("(")+1,createTime.indexOf(")")));
        long rtimes = Long.parseLong(requestTime.substring(requestTime.indexOf("(")+1,requestTime.indexOf(")")));
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ctime = sdf.format(new Date(ctimes));
        String rtime = sdf.format(new Date(rtimes));
        createtime.setText(ctime);
        requesttime.setText(rtime);
        title.setText(rotas.get(groupPosition).getGeneralDepartment().getName());
        zhuangtai.setText(rotas.get(groupPosition).getRotaStatus().getName());
        descrition.setText(rotas.get(groupPosition).getDescription());
        sortclass.setText(rotas.get(groupPosition).getCategories());
        Drawable icon1 = null;
        if (rotas.get(groupPosition).getRotaStatus().getName().equals("新建")){
            icon1 = context.getResources().getDrawable(images[0]);

        }else if (rotas.get(groupPosition).getRotaStatus().getName().equals("处理中")){
            icon1 = context.getResources().getDrawable(images[1]);

        }else if (rotas.get(groupPosition).getRotaStatus().getName().equals("完成")){
            icon1 = context.getResources().getDrawable(images[2]);

        }
        icon1.setBounds(0,0,icon1.getMinimumWidth(),icon1.getMinimumHeight());
        zhuangtai.setCompoundDrawables(icon1,null,null,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ScheduleNoteEditActivity.class);
                intent.putExtra("id",rotas.get(groupPosition).getID());
                intent.putExtra("generateName",rotas.get(groupPosition).getGeneralDepartment().getName());
                intent.putExtra("departmentName",rotas.get(groupPosition).getDepartment().getName());
                context.startActivity(intent);
                ((ScheduleNoteDisPlayActivity)context).finish();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
