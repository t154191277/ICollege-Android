package wmlove.bistu.timetable.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import wmlove.bistu.R;
import wmlove.bistu.model.CourseDBModel;

public class CourseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        CourseDBModel model = (CourseDBModel) bundle.getSerializable("model");
        TextView info = (TextView) findViewById(R.id.info);
        info.setText(model.toString());
    }
}
