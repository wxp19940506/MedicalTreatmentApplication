package iok.la.com.medicaltreatmentapplication.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class TimeOrLongUtil {
    public static long TimeToLong(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(time).getTime();
    }
    public static String LongToTime(long mills) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(mills));
    }
    //上一周
    public static String LastWeeks(String time) throws ParseException {
            long mills = TimeToLong(time);
            return LongToTime(mills -(7*24*60*60*1000));

    }
    //下一周
    public static String NextWeeks(String time) throws ParseException {
        long mills = TimeToLong(time);
        return LongToTime(mills +(7*24*60*60*1000));

    }
    //上一天
    public static long LastWeekll(long time) throws ParseException {

        return time -(7*24*60*60*1000);

    }
    //下一天
    public static long NextWeekll(long time) throws ParseException {
        return time +(7*24*60*60*1000);
    }
    //上一天
    public static long LastWeekl(long time) throws ParseException {

        return time -(24*60*60*1000);

    }
    //下一天
    public static long NextWeekl(long time) throws ParseException {
        return time +(24*60*60*1000);
    }
//    //上一月
//    public static long LastMonthl(long time) throws ParseException {
//        int days = getThisMothDay(time);
//        return time -(days*24*60*60*1000);
//
//    }
//    //下一月
//    public static long NextMonthl(long time) throws ParseException {
//        int days = getThisMothDay(time);
//        return time +(days*24*60*60*1000);
//    }
//    //获取当天月份，根据月份获取天数
//    public static int getThisMothDay(long time){
//        int days = 30;
//        DateFormat format0 = new SimpleDateFormat("M");
//        DateFormat format1 = new SimpleDateFormat("yyyy");
//        final String months = format0.format(new Date(time));
//        final String years = format1.format(new Date(time));
//        int month = Integer.parseInt(months);
//        int year = Integer.parseInt(years);
//        if (month == 1|| month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month ==12){
//            days  = 31;
//        }else if (month == 4|| month == 6 || month == 9 || month == 10 || month == 11){
//            days  = 30;
//        }else{
//            if (year % 4 == 0 && year % 100!=0||year%400==0) {
//                days = 29;
//            }
//            else {
//                days = 28;
//            }
//        }
//
//
//
//        return days;
//    }
    //上一月
    public static long LastMonthl(long time) throws ParseException {
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        DateFormat monthFormat = new SimpleDateFormat("M");
        DateFormat dayFormat = new SimpleDateFormat("d");
        int year = Integer.parseInt(yearFormat.format(new Date(time)));
        int month = Integer.parseInt(monthFormat.format(new Date(time)));
        int day = Integer.parseInt(dayFormat.format(new Date(time)));
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(year, month-1, day);//月份是从0开始的，所以11表示12月
        Date now = ca.getTime();
        ca.add(Calendar.MONTH, -1); //月份减1
        Date lastMonth = ca.getTime(); //结果
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        String date = sf.format(lastMonth);
        return lastMonth.getTime();

    }
    //下一月
    public static long NextMonthl(long time) throws ParseException {
        DateFormat yearFormat = new SimpleDateFormat("yyyy");
        DateFormat monthFormat = new SimpleDateFormat("M");
        DateFormat dayFormat = new SimpleDateFormat("d");
        int year = Integer.parseInt(yearFormat.format(new Date(time)));
        int month = Integer.parseInt(monthFormat.format(new Date(time)));
        int day = Integer.parseInt(dayFormat.format(new Date(time)));
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(year, month-1, day);//月份是从0开始的，所以11表示12月
        Date now = ca.getTime();
        ca.add(Calendar.MONTH, +1); //月份加1
        Date nextMonth = ca.getTime(); //结果
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//        String date = sf.format(nextMonth);
        return nextMonth.getTime();
    }

}
