package iok.la.com.medicaltreatmentapplication.bean;

/**
 * 类功能介绍。
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 *
 */


public class CalendarData {

    public int year;
    public int month;
    public int day;
    public int week = -1;
    public boolean isNextMonthDay = false;//是否是下个月中的日期
    public boolean isLastMonthDay = false;//是否是上个月中的日期
    public CalendarData() { }

    public CalendarData(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean isSameDay(CalendarData data) {
        return (data.day == day && data.month == month &&data.year == year);
    }

    @Override
    public String toString() {
        return "CalendarData{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", week=" + week +
                ", isNextMonthDay=" + isNextMonthDay +
                ", isLastMonthDay=" + isLastMonthDay +
                '}';
    }
}
