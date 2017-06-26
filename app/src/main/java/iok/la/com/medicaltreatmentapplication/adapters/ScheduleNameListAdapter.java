package iok.la.com.medicaltreatmentapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import iok.la.com.medicaltreatmentapplication.activities.OfficeScheduleDetailActivity;
import iok.la.com.medicaltreatmentapplication.bean.EditBanBean;
import iok.la.com.medicaltreatmentapplication.bean.OfficeScheduleInfoName;
import iok.la.com.medicaltreatmentapplication.util.DensityUtil;
import iok.la.com.medicaltreatmentapplication.util.ThreadUtils;
import iok.la.com.medicaltreatmentapplication.util.ToastUtils;

import static iok.la.com.medicaltreatmentapplication.R.id.offices;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class ScheduleNameListAdapter extends BaseExpandableListAdapter {
    List<OfficeScheduleInfoName.ResultsBean.ModulesBean> modules;
    Context context;
    int currentDepartmentID,currentShiftTypeID;
    String officeName;
    public ScheduleNameListAdapter(OfficeScheduleDetailActivity context, List<OfficeScheduleInfoName.ResultsBean.ModulesBean> modules, String officeName, int currentDepartmentID, int currentShiftTypeID) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.name_group_item,null);
        ImageView updown = (ImageView) view.findViewById(R.id.updown);
        ImageView isholiday = (ImageView) view.findViewById(R.id.updown);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(modules.get(groupPosition).getTitle().toString());
        isholiday.setVisibility(View.INVISIBLE);
        if (modules.get(groupPosition).getTooltip() != null){
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, final View convertView, ViewGroup parent) {
        final List data = new ArrayList();
        final View view = LayoutInflater.from(context).inflate(R.layout.name_child_item, null);
        View last_line = view.findViewById(R.id.last_line);
        TextView date = (TextView) view.findViewById(R.id.date);
        date.setText(modules.get(groupPosition).getRows().get(childPosition).getTitle());
        LinearLayout schedule_text = (LinearLayout) view.findViewById(R.id.schedule_text);
        final LinearLayout descrotation = (LinearLayout) view.findViewById(R.id.descrotation);
        ImageButton edit_schedule = (ImageButton) view.findViewById(R.id.edit_schedule);

        if (isLastChild){
            last_line.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i <modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().size() ; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);
            params.weight=1;
            TextView textView = new TextView(context);
            textView.setTextColor(context.getResources().getColor(R.color.text_back));
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
            textView.setText(modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().get(i).getName());
            textView.setGravity(Gravity.CENTER);
            schedule_text.addView(textView);
            data.add(modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getItems().get(i).getID());
        }
        final TextView other = (TextView) view.findViewById(R.id.other);
        LinearLayout rootView = (LinearLayout) view.findViewById(R.id.rootView);
        if (edit_schedule != null) {
            edit_schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditScheduleActivity.class);
                    intent.putExtra("currentDepartmentID",currentDepartmentID);
                    intent.putExtra("currentShiftTypeID",currentShiftTypeID);
                    intent.putExtra("date",modules.get(groupPosition).getRows().get(childPosition).getTitle());
                    intent.putExtra("officeName",officeName);
                    intent.putExtra("name",modules.get(groupPosition).getTitle().toString());
                    intent.putExtra("id",modules.get(groupPosition).getEmployeeId());
                    EditBanBean editBanBean = new EditBanBean();
                    editBanBean.setData(data);
                    intent.putExtra("data",editBanBean);
                    context.startActivity(intent);
                }
            });
        }
        if (childPosition % 2 == 0 ){
            rootView.setBackgroundResource(R.color.white);
        }else {
            rootView.setBackgroundResource(R.color.blue);
        }
        other.setText(modules.get(groupPosition).getRows().get(childPosition).getCells().get(0).getDescription());
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
