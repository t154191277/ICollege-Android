package wmlove.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2015/6/17.
 */
public class CalendarView extends RelativeLayout {

    /**
     * 支持滑动的日历容器
     */
    private ViewPager mViewPager;

    /**
     * 日历容器的适配器
     */
    private CalendarViewAdapter mCalendarViewAdapter;

    /**
     * 把除了选中的Day所在的TimeView之外的day全部设置为非选中状态的回调
     */
    private class DaySelectChangeListener implements OnDaySelectChangeListener {

        @Override
        public void SelectChangeCallBack() {
            TimeView current = timeViewList.get(mViewPager.getCurrentItem());
            for (TimeView other : timeViewList) {
                if (other!=current){other.clearSelected();}
            }
        }
    }

    /**
     * DaySelectChangeListener 的实例
     */
    private DaySelectChangeListener mDaySelectChanegListener = new DaySelectChangeListener();

    /**
     * 判断是以周视图还是月视图显示日历的标志，默认是以周视图显示
     */
    private boolean inweek = false;

    private int TimeFlag ;

    /**
     * 每一个viewpager呈现的内容的集合
     */
    private List<TimeView> timeViewList = new ArrayList<>();

    /**
     *
     */
    private List<DaySelectChangeListener> OnTimeChangeListenerList = new ArrayList<>();

    /**
     * 日历最大显示的日期
     */
    private Calendar mCalendarMax = null;

    /**
     * 日历最小显示的日期
     */
    private Calendar mCalendarMin = null;

    /**
     * 日历当前的日期
     */
    private Calendar mCalendarCurrent = null;

    /**
     *  OnTimeChangeListener的实例
     */
    private OnTimeChangeListener mOnTimeChangeListener = null;


    /**
     * 获取周视图或者月视图的TimeFlag
     * @param inweek
     * @return 返回TimeFlag 周视图则返回 Calendar.WEEK_OF_YEAR 月视图返回 Calendar.MONTH
     */
    public int getTimeFlag(boolean inweek) {
        return inweek ? Calendar.WEEK_OF_YEAR : Calendar.MONTH;
    }


    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.CalendarView);
        inweek = t.getBoolean(R.styleable.CalendarView_viewinview,false);
        t.recycle();

        TimeFlag = getTimeFlag(inweek);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);

        addTimeView();

        LinearLayout title = new LinearLayout(context);
        title.setOrientation(LinearLayout.HORIZONTAL);
        Calendar calendar = CalendarUtils.getCalenar();
        CalendarUtils.setToFirstDayInWeek(calendar);
        int size = SizeUtils.getSize(context);
        SimpleDateFormat format = new SimpleDateFormat("EEE", Locale.CHINA);
        for (int i = 0; i < 7; i++) {
            TextView textView = new TextView(context);
            textView.setWidth(size);
            textView.setHeight(size * 3 / 5);
            textView.setGravity(Gravity.CENTER);
            textView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setText(format.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
            title.addView(textView);
        }
        root.addView(title);

        mViewPager = new ViewPager(context);
        mCalendarViewAdapter = new CalendarViewAdapter();
        mViewPager.setAdapter(mCalendarViewAdapter);
        int index = getIndex(Calendar.getInstance());
        mViewPager.setCurrentItem(index);

        mCalendarCurrent = timeViewList.get(index).getTimeCalendar();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                TimeView timeView = timeViewList.get(position);
                Date date = timeView.getDate();
                mOnTimeChangeListener.OnTimeChange(date);

                for (TimeView other : timeViewList) {
                    other.clearSelected();
                }

                mCalendarCurrent = timeView.getTimeCalendar();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        root.addView(mViewPager, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(root);

    }

    /**
     * 当日历滑动的时候回调
     * @param listener OnTimeChangeListener
     */
    public void setOnTimeChangeListener(OnTimeChangeListener listener)
    {
        if(null != listener)
        {
            this.mOnTimeChangeListener = listener;
        }
    }

    /**
     * 当日历上的day被点击的时候的回调
     * @param listener OnDaySelectListener
     */
    public void setOnDaySelectListener(OnDaySelectListener listener)
    {
        if(null != listener)
        {
            for(TimeView timeView : timeViewList)
            {
                timeView.setOnDaySelectListener(listener);
            }
        }
    }

    /**
     * 设置viewpager最大显示的日期。
     * @param calendarMax 最大日期
     */
    public void setMaxDate(Calendar calendarMax){
        this.mCalendarMax = calendarMax;
        postInvalidate();
    }

    /**
     * 设置viewpager最小显示的日期。
     * @param calendarMin 最小日期
     */
    public void setMinDate(Calendar calendarMin)
    {
        this.mCalendarMin = calendarMin;
        postInvalidate();
    }

    /**
     * 设置day被选中的时候的颜色
     * @param color 选中的时候的颜色
     */
    public void setSelectColor(int color){
        for(TimeView timeView : timeViewList){
            timeView.setSelectColor(color);
        }
    }

    /**
     * 设置day选中时候的背景大小
     * @param size 背景大小， 100 ~ 150
     */
    public void setSelectSize(int size){
        for(TimeView timeView : timeViewList){
            timeView.setSelectSize(size);
        }
    }

    /**
     * 设置当前日期。
     * @param currentCalendar 当前日期
     */
    public void setCurrentTime(Calendar currentCalendar)
    {
        this.mCalendarCurrent = currentCalendar;
        int i = getIndex(currentCalendar);
        mViewPager.setCurrentItem(i);
        postInvalidate();
    }

    public Calendar getCurrentTime()
    {
        return this.mCalendarCurrent;
    }

    private Calendar getMinCalendarDay()
    {
        if(mCalendarMin==null)
        {
            int mindate = -2;
            Calendar min = CalendarUtils.getCalenar();
            min.add(Calendar.YEAR, mindate);
            return min;
        }
        return mCalendarMin;
    }

    private Calendar getMaxCalendarDay()
    {
        if(mCalendarMax==null){
            int maxdate = 2;
            Calendar max = CalendarUtils.getCalenar();
            max.add(Calendar.YEAR,maxdate);
            return max;
        }
        return mCalendarMax;
    }

    //把符合条件的TimeView添加到集合当中
    private void addTimeView()
    {
        Calendar minCalendar = getMinCalendarDay();
        Calendar maxCalendar = getMaxCalendarDay();
        if(mCalendarCurrent==null)
        {
            mCalendarCurrent = CalendarUtils.getCalenar();
        }
        CalendarUtils.copyTo(minCalendar,mCalendarCurrent);
        while (CalendarUtils.isBefore(mCalendarCurrent, maxCalendar))
        {
            TimeView timeView = new TimeView(getContext(), mCalendarCurrent, TimeFlag);
            timeView.setOnDaySelectChangeListener(mDaySelectChanegListener);
            timeViewList.add(timeView);
            mCalendarCurrent.add(TimeFlag, 1);
        }
    }

    //得到当前日期的Index
    private int getIndex(Calendar c)
    {
        //解决中文周日的bug
        if (c.get(Calendar.DAY_OF_WEEK) == 1)
        {
            c.add(Calendar.DAY_OF_WEEK, -1);
        }
        for(int i = 0 ;i < timeViewList.size(); i++)
        {
            TimeView timeView = timeViewList.get(i);
            Calendar other = timeView.getTimeCalendar();
            if(c.get(Calendar.YEAR) == other.get(Calendar.YEAR)
                    &&c.get(Calendar.MONTH) == other.get(Calendar.MONTH))
            {
                if(TimeFlag==Calendar.WEEK_OF_YEAR)
                {

                    if(c.get(Calendar.WEEK_OF_YEAR) == other.get(Calendar.WEEK_OF_YEAR))
                    {
                        return i;
                    }
                }
                else
                {
                    return i;
                }

            }
        }
        return 0;
    }

    //ViewPager的适配器
    private class CalendarViewAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return timeViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TimeView timeView = timeViewList.get(position);
            Log.i("TAG",timeViewList.size()+"size");
            container.addView(timeView);
            return timeViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(timeViewList.get(position));
        }
    }
}