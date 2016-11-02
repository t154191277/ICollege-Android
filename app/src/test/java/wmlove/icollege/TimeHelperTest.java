package wmlove.icollege;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import wmlove.icollege.factory.TimeFactory;

/**
 * Created by wmlove on 2016/10/22.
 */
public class TimeHelperTest {
    @Test
    public void testTime()
    {
        System.out.println("星期"+ TimeFactory.getCurrentDay());
        System.out.println("第"+ TimeFactory.getCurrentWeek()+"周");
        Date date = Calendar.getInstance().getTime();
        System.out.println("星期"+ TimeFactory.getCurrentDay(date));
        System.out.println("第"+ TimeFactory.getCurrentWeek(date)+"周");

    }
}
