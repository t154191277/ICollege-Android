package wmlove.bistu.timetable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.wheelview.adapter.BaseWheelAdapter;

import wmlove.bistu.R;

/**
 * Created by wmlove on 2016/10/24.
 */
public class WeekWheelAdapter extends BaseWheelAdapter<String>
{

    private LayoutInflater mLayoutInflater;

    public WeekWheelAdapter(Context context)
    {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_course_dialog_week, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(String.valueOf(mList.get(position)));
        return convertView;
    }

    private class ViewHolder {
        TextView textView;
    }
}
