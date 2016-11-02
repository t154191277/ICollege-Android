package wmlove.icollege.timetable.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import wmlove.icollege.R;
import wmlove.icollege.model.CourseDBModel;

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
