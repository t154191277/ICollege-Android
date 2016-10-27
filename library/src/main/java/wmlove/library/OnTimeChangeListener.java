package wmlove.library;

import java.util.Date;

/**
 * Created by wmlove on 2015/6/30.
 */
public interface OnTimeChangeListener {
    /**
     * 当viewPager视图变换的时候，获取当前视图中的第一天的日期
     * @param currentDate 第一天的日期
     */
    void OnTimeChange(Date currentDate);
}
