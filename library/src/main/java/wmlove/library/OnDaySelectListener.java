package wmlove.library;

import java.util.Date;

/**
 * Created by wmlove on 2015/6/19.
 */
public interface OnDaySelectListener {
    /**
     * 当day被选中的时候，获取当前day的日期
     * @param date 当前日期
     */
    public void OnDaySelect(Date date);
}
