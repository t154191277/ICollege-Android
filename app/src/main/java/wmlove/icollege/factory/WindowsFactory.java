package wmlove.bistu.factory;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by wmlove on 2016/10/12.
 */
public class WindowsFactory {

    public static Point getWindowsSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point;
    }
}
