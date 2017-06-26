package iok.la.com.medicaltreatmentapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class ScheduleNoteListView extends ListView {
    public ScheduleNoteListView(Context context) {
        super(context);
    }

    public ScheduleNoteListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScheduleNoteListView(Context context, AttributeSet attrs) {
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
