package wmlove.bistu.timetable.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import wmlove.bistu.R;
import wmlove.bistu.timetable.adapter.WeekWheelAdapter;
import wmlove.bistu.factory.TimeFactory;

/**
 * Created by wmlove on 2016/10/24.
 */
public class WeekSelectDialog extends Dialog{

    private View view1,view2;
    private WheelView wheelView;
    private TextView title,submit;
    private int week = 0;

    private onSubmitClickListener onSubmitClickListener;

    public WeekSelectDialog(Context context, String weekStr) {
        super(context);
        week = Integer.valueOf(weekStr) - 1;
    }

    public void setOnSubmitClickListener(onSubmitClickListener onSubmitClickListener) {
        this.onSubmitClickListener = onSubmitClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_week_select);

        view1 = findViewById(R.id.title);
        view2 = findViewById(R.id.title);

        wheelView = (WheelView) findViewById(R.id.wheelview);

        title = (TextView) findViewById(R.id.title);
        submit = (TextView) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClickListener.onSubmitClick(wheelView.getCurrentPosition());
            }
        });

        WeekWheelAdapter adapter = new WeekWheelAdapter(getContext());

        wheelView.setSelection(week);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 30;
        wheelView.setStyle(style);
        wheelView.setExtraText("周", Color.parseColor("#0288ce"), 50, 80); //文字附加
        wheelView.setWheelSize(5); //显示多少个Item
        wheelView.setSkin(WheelView.Skin.Holo); // common皮肤
        wheelView.setWheelAdapter(adapter); // 文本数据源
        wheelView.setOnWheelItemClickListener(new WheelView.OnWheelItemClickListener() {
            @Override
            public void onItemClick(int position, Object o) {
                Log.i("onItemClick",position+"");
            }
        });
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                Log.i("onItemSelected",position+"");
            }
        });

        List<Integer> data = new ArrayList<>();
        for (int i = 1; i < TimeFactory.weekLength; i++)
        {
            data.add(i);
        }

        wheelView.setWheelData(data);  // 数据集合

    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
//        removeView(wheelView);
//        removeView(title);
//        removeView(submit);
//        removeView(view1);
//        removeView(view2);
        super.setOnDismissListener(listener);
    }

    public interface onSubmitClickListener
    {
        public void onSubmitClick(int position);
    }
}
