package wmlove.library;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by wmlove on 2015/6/17.
 */
public class CalendarUtils {
    public static Calendar getCalenar(){
        return Calendar.getInstance();
    }

    public static void setToFirstDayInMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.getTimeInMillis();
    }

    public static void setToFirstDayInWeek(Calendar calendar){
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.get(Calendar.MONTH);
        calendar.getTimeInMillis();
    }

    public static void copyTo(Calendar from,Calendar to){
        int year = from.get(Calendar.YEAR);
        int month = from.get(Calendar.MONTH);
        int day = from.get(Calendar.DAY_OF_MONTH);
        to.set(year,month,day);
        to.get(Calendar.MONTH);
        to.getTimeInMillis();
    }

    public static boolean Equals(Calendar currentCalendar, Calendar calendar){
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int calendarYear = calendar.get(Calendar.YEAR);
        int calendarMonth = calendar.get(Calendar.MONTH);
        int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        return (currentYear == calendarYear && currentMonth == calendarMonth && currentDay == calendarDay) ;
    }

    public static boolean isBefore(Calendar currentCalendar, Calendar max){
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int currentMonth = currentCalendar.get(Calendar.MONTH);
        int currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        int maxYear = max.get(Calendar.YEAR);
        int maxMonth = max.get(Calendar.MONTH);
        int maxDay = max.get(Calendar.DAY_OF_MONTH);
        return (currentYear == maxYear) ?
                ((currentMonth == currentMonth) ? (currentDay < maxDay) : (currentMonth < maxMonth)) :
                (currentYear < maxYear);
    }



}
