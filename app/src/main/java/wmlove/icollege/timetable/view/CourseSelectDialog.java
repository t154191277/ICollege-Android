package wmlove.bistu.timetable.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import wmlove.bistu.R;
import wmlove.bistu.model.CourseDBModel;

/**
 * Created by wmlove on 2016/10/24.
 */
public class CourseSelectDialog extends Dialog{

    private CourseDBModel model;

    public CourseSelectDialog(Context context, CourseDBModel model) {
        super(context);
        this.model = model;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_course_select);
    }
}
