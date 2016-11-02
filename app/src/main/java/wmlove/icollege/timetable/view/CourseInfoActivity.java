package wmlove.icollege.timetable.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import wmlove.icollege.R;
import wmlove.icollege.chat.view.ChatFragment;
import wmlove.icollege.model.CourseDBModel;

public class CourseInfoActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private ViewPager viewPager;

    private TextView file,advice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        CourseDBModel model = (CourseDBModel) bundle.getSerializable("model");
        TextView info = (TextView) findViewById(R.id.info);
        info.setText(model.toString());

        ChatFragment rtimeTableFragment = new ChatFragment();
        ChatFragment ltimeTableFragment = new ChatFragment();
        ChatFragment chatFragment = new ChatFragment();

        fragmentList.add(ltimeTableFragment);
        fragmentList.add(rtimeTableFragment);
        fragmentList.add(chatFragment);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        file = (TextView) findViewById(R.id.file);
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, false);
            }
        });
        advice = (TextView) findViewById(R.id.advice);
        advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }
}
