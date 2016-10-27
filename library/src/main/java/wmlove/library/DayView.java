package wmlove.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015/6/17.
 */
public class DayView extends RelativeLayout{

    private int backgroupColor = Color.YELLOW;
    private Date date;
    private int day;
    private int month;
    private TextView tx;

    public DayView(Context context) {
        super(context);
        tx = new TextView(context);
        tx.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setBackgroupColor();
        tx.setTextSize(20);
        int size = SizeUtils.getSize(context);
        tx.setTextColor(Color.BLACK);
        tx.setGravity(Gravity.CENTER);
        addView(tx);
    }

    private void setParams(int size)
    {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size,size);
        params.addRule(CENTER_IN_PARENT);
        tx.setLayoutParams(params);
    }

    public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSelectColor(int color){
        this.backgroupColor = color;
        setBackgroupColor();

    }

    public void setSelectSize(int size) {
        setParams(size);
        postInvalidate();
    }

    private void setBackgroupColor(){
        StateListDrawable drawable = new StateListDrawable();
        ShapeDrawable shape = new ShapeDrawable(new OvalShape());
        shape.setShaderFactory(new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int i, int i1) {
                return new LinearGradient(0,0,0,0,backgroupColor,backgroupColor, Shader.TileMode.REPEAT);
            }
        });
        drawable.addState(new int[]{android.R.attr.state_selected}, shape);
        tx.setBackground(drawable);
    }

    public void setDay(boolean showotherdays,boolean inrange){
        if(!showotherdays)
        {
            String text = inrange ? day + "" : "";
            boolean enable = inrange ? true : false;
            setEnabled(enable);
            tx.setText(text);
            return;
        }
        tx.setText(String.valueOf(day));

    }

    @Override
    public boolean isSelected() {
        return tx.isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        tx.setSelected(selected);
    }

    public void setTimeRange(Calendar calendar) {
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH);
        this.date = calendar.getTime();
    }

    public Date getDate(){
        return this.date;
    }

    public void setSelected()
    {
        tx.setSelected(true);
    }
}
