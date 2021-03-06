package wmlove.icollege.timetable.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wmlove.icollege.R;
import wmlove.icollege.model.CourseDBModel;

/**
 * Created by wmlove on 2016/10/19.
 */
public class RCourseListAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<CourseDBModel> courseDBModelsList;

    public RCourseListAdapter(Context mContext) {
        mInflater = LayoutInflater.from(mContext);
    }

    private String[] info = {"交作业","点名","交作业 点名"};

    public List<CourseDBModel> getCourseList(Cursor mCursor) {
        courseDBModelsList = new ArrayList<>();

        while (mCursor.moveToNext())
        {
            CourseDBModel model = new CourseDBModel();

            model.setId(mCursor.getString(0));
            model.setCourseCode(mCursor.getString(1));
            model.setCourseName(mCursor.getString(2));
            model.setCourseType(mCursor.getString(3));
            model.setTeacher(mCursor.getString(4));
            model.setCredit(mCursor.getFloat(5));
            model.setDay(mCursor.getString(6));
            model.setStartLine(mCursor.getString(7));
            model.setDeadLine(mCursor.getString(8));
            model.setOpenWeek(mCursor.getString(9));
            model.setCloseWeek(mCursor.getString(10));
            model.setIsSingle(mCursor.getString(11));
            model.setPlace(mCursor.getString(12));
            courseDBModelsList.add(model);
        }
        mCursor.close();
        return courseDBModelsList;
    }

    public void setCursor(Cursor mCursor) {
        courseDBModelsList = getCourseList(mCursor);
    }

    @Override
    public int getCount() {
        return courseDBModelsList == null ? 0 : courseDBModelsList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseDBModelsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_course_right,null);
            viewHolder.courseName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.courseTime = (TextView) convertView.findViewById(R.id.time);
            viewHolder.coursePlace = (TextView) convertView.findViewById(R.id.place);
            viewHolder.courseInfo = (TextView) convertView.findViewById(R.id.info);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CourseDBModel model = courseDBModelsList.get(position);
        String name = model.getCourseName();
        viewHolder.courseName.setText(name);
        if (name.length() > 10)
        {
            String prefix = name.substring(0,10);
            String sufifx = "...";
            String rname = prefix + sufifx;
            viewHolder.courseName.setText(rname);
        }
        String time = model.getStartLine() + "-" + model.getDeadLine() + "节";
        viewHolder.courseTime.setText(time);
        viewHolder.coursePlace.setText(model.getPlace());
        int randomInt = new Random().nextInt(3);
        viewHolder.courseInfo.setText(info[randomInt]);

        return convertView;
    }

    class ViewHolder
    {
        TextView courseName;
        TextView courseTime;
        TextView coursePlace;
        TextView courseInfo;
    }
}
