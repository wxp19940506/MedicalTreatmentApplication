package iok.la.com.medicaltreatmentapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class ScheduleNoteGridView extends GridView {
    public ScheduleNoteGridView(Context context) {
        super(context);
    }

    public ScheduleNoteGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScheduleNoteGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
