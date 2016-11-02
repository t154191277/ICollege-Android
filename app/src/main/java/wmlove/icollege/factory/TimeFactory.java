package wmlove.icollege.factory;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wmlove on 2016/10/20.
 */
public class TimeFactory {

    private static Date date = Calendar.getInstance().getTime();

    /**
     * 开学第一周第一天
     */
    private static final int startDay = 29;

    /**
     * 开学第一周的月份
     */
    private static final int startMonth = 8 - 1;

    /**
     * 这个学期的教学周数
     */
    public static final int weekLength = 20;

    /**
     * 一周的天数
     */
    private static final int dayInWeek = 7;

    /**
     * 调用这个方法，获取当前星期几
     * @return 星期几
     */
    public static String getCurrentDay()
    {
        //Day of week in month
        return getDay(date);
    }

    /**
     * 传入一个currentDate ，调用这个方法，获取当前星期几
     * @param currentDate 要查询的Date
     * @return 星期几
     */
    public static String getCurrentDay(Date currentDate)
    {
        return getDay(currentDate);
    }

    private static String getDay(Date date)
    {
        if (date.getDay() == 0)
        {
            return String.valueOf(7);
        }
        return String.valueOf(date.getDay());
    }

    /**
     * 这个方法会返回当前是第几周
     * @return 第几周
     */
    public static String getCurrentWeek()
    {
        return String.valueOf(getWhichWeek(date));
    }

    /**
     * 查询当前日期在教学的第几周
     * @param currentDate 要查询的Date
     * @return 返回第几周，返回 -1 则不在教学周中
     */
    public static String getCurrentWeek(Date currentDate)
    {
        return String.valueOf(getWhichWeek(currentDate));
    }

    /**
     * 装配这个学期的每一个教学周的Calender, 这些Calender都将被设定为这一教学周的周一
     * @return  本学期教学周Calendar数组
     */
    private static Calendar[] getWeekInSemester()
    {
        Calendar temp = Calendar.getInstance();
        temp.set(temp.get(Calendar.YEAR), startMonth, startDay);
        Calendar [] weekInSemester = new Calendar[weekLength];
        for (int i = 0; i < weekLength - 1; i++)
        {
            Calendar calendar = (Calendar) temp.clone();
            weekInSemester[i] = calendar;
            temp.add(Calendar.DAY_OF_WEEK, dayInWeek);
        }
        return weekInSemester;
    }

    /**
     * 查询当前日期在教学的第几周
     * @param currentDate
     * @return 返回第几周，返回 -1 则不在教学周中
     */
    public static int getWhichWeek(Date currentDate)
    {
        Calendar[] weekInSemester = getWeekInSemester();
        for (int i = 0; i < weekInSemester.length - 1; i++)
        {
            Calendar calendar = weekInSemester[i];
            if (isEqual(calendar, currentDate))
            {
                return i + 1 ;
            }
        }
        return -1;
    }

    /**
     * 返回日期是否在某一教学周里
     * @param calendar 某一周的周一
     * @param currentDate 查询的日期
     * @return 在某一周中则返回 True,否则False
     */
    private static Boolean isEqual(Calendar calendar, Date currentDate)
    {
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),23,59,59);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 8);
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,1);
        Date endDate = calendar.getTime();
        return startDate.before(currentDate) && endDate.after(currentDate);
    }

    public static Calendar getCalenderFromWeek(int week)
    {
        Calendar[] weekInSemester = getWeekInSemester();
        return weekInSemester[week - 1];
    }
}
