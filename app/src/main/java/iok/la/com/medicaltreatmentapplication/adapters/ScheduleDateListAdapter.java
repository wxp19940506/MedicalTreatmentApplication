package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iok.la.com.medicaltreatmentapplication.R;
import iok.la.com.medicaltreatmentapplication.activities.EditScheduleActivity;
import iok.la.com.medicaltreatmentapplication.bean.EditBanBean;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfoTime;
import iok.la.com.medicaltreatmentapplication.util.DensityUtil;
import iok.la.com.medicaltreatmentapplication.util.ThreadUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class ScheduleDateListAdapter extends BaseExpandableListAdapter {
    List<OfficeScheduleInfoTime.ResultsBean.ModulesBean> modules;
    Context context;
    int currentDepartmentID,currentShiftTypeID;
    String officeName;
    public ScheduleDateListAdapter(Context context, List<OfficeScheduleInfoTime.ResultsBean.ModulesBean> modules, String officeName, int currentDepartmentID, int currentShiftTypeID) {
        this.context = context;
        this.modules = modules;
        this.currentDepartmentID = currentDepartmentID;
        this.currentShiftTypeID = currentShiftTypeID;
        this.officeName = officeName;
    }

    @Override
    public int getGroupCount() {
        return modules.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return modules.get(0).getRows().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return modules.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return modules.get(groupPosition).getRows().get(childPosition);
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
        View view = LayoutInflater.from(context).inflate(R.layout.date_group_item,null);
        ImageView updown = (ImageView) view.findViewById(R.id.updown);
        ImageView isholiday = (ImageView) view.findViewById(R.id.isholiday);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(modules.get(groupPosition).getDateColumn().getTitle().toString());
        isholiday.setVisibility(View.INVISIBLE);
        if (modules.get(groupPosition).getDateColumn().isHoliday()){
            isholiday.setImageResource(R.mipmap.holiday);
            isholiday.setVisibility(View.VISIBLE);
        }else if (modules.get(groupPosition).getDateColumn().isWorkDay()){
            isholiday.setImageResource(R.mipmap.weekday);
            isholiday.setVisibility(View.VISIBLE);
        }
        if (isExpanded) {
            updown.setImageResource(R.mipmap.xia);
        }else {
            updown.setImageResource(R.mipmap.upper);
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.date_child_item, null);
        final List data = new ArrayList();
        View last_line = view.findViewById(R.id.last_line);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(modules.get(groupPosition).getRows().get(childPosition).getTitle());
        LinearLayout schedule_text = (LinearLayout) view.findViewById(R.id.schedule_text);
        LinearLayout rootView = (LinearLayout) view.findViewById(R.id.rootView);
        if (childPosition % 2 == 0 ){
            rootView.setBackgroundResource(R.color.white);
        }else {
            rootView.setBackgroundResource(R.color.blue);
        }
        final LinearLayout descrotation = (LinearLayout) view.findViewById(R.id.descrotation);
        for (int i = 0; i <modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().size() ; i++) {
            data.add(modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().get(i).getID());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setTextColor(context.getResources().getColor(R.color.text_back));
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
            textView.setText(modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().get(i).getName());
            textView.setGravity(Gravity.CENTER);
            schedule_text.addView(textView);
        }
        if (childPosition ==  modules.get(0).getRows().size() - 1 ){
            last_line.setVisibility(View.VISIBLE);
        }
        final TextView other = (TextView) view.findViewById(R.id.other);
        other.setText(modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getDescription());
        ImageButton edit_schedule = (ImageButton) view.findViewById(R.id.edit_schedule);
        if (edit_schedule != null) {
            edit_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditScheduleActivity.class);
                    intent.putExtra("currentDepartmentID",currentDepartmentID);
                    intent.putExtra("currentShiftTypeID",currentShiftTypeID);
                    intent.putExtra("date",modules.get(groupPosition).getDateColumn().getTitle().toString());
                    intent.putExtra("officeName",officeName);
                    intent.putExtra("name",modules.get(groupPosition).getRows().get(childPosition).getTitle());
                    intent.putExtra("id",modules.get(groupPosition).getRows().get(childPosition).getID());
                    int fid = 0;
                    if (modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().size() > 0){
                        fid = modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().get(0).getFrequencyID();
                    }
                    intent.putExtra("FrequencyID",fid);
                    EditBanBean editBanBean = new EditBanBean();
                    editBanBean.setData(data);
                    intent.putExtra("data",editBanBean);
                    context.startActivity(intent);
                }
            });
        }
        descrotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View descrationView =LayoutInflater.from(context).inflate(R.layout.desctation_popview,null);
                TextView descrotion_pop = (TextView) descrationView.findViewById(R.id.descrotion_pop);
                if (modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getDescription() != null && modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getDescription().trim() != ""){
                    String pop_text = modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getDescription();
                    descrotion_pop.setText(pop_text);
                }else {
                    descrotion_pop.setText("对不起，暂无数据！");
                }
                final PopupWindow office_popupWindow = new PopupWindow(descrationView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                office_popupWindow.setOutsideTouchable(true);
                office_popupWindow.showAsDropDown(other,0, DensityUtil.dip2px(context,-15),Gravity.CENTER);
                ThreadUtils.runInThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            ThreadUtils.runInUThread(new Runnable() {
                                @Override
                                public void run() {
                                    office_popupWindow.dismiss();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
