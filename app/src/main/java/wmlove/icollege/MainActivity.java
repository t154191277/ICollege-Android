package wmlove.bistu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import java.util.ArrayList;

import wmlove.bistu.chat.view.ChatFragment;
import wmlove.bistu.find.view.FindFragment;
import wmlove.bistu.mine.view.MineFragment;
import wmlove.bistu.timetable.view.TimeTableFragment;
import wmlove.bistu.utils.NoScrollViewPager;

public class MainActivity extends AppCompatActivity {


    private NoScrollViewPager mViewPager;
    private AdvancedPagerSlidingTabStrip mTabs;
    ArrayList<View> viewContainter = new ArrayList<View>();
    ArrayList<String> titleContainer = new ArrayList<String>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onStart() {
        Log.i("MainActivity","onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i("MainActivity","onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("MainActivity","onPause");
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //页签项
        titleContainer.add("Bistu");
        titleContainer.add("组织");
        titleContainer.add("发现");
        titleContainer.add("我的");

        ChatFragment chatFragment = new ChatFragment();
        TimeTableFragment timeTableFragment = new TimeTableFragment();
        FindFragment findFragment = new FindFragment();
        MineFragment mineFragment = new MineFragment(getApplicationContext());
        fragmentList.add(chatFragment);
        fragmentList.add(timeTableFragment);
        fragmentList.add(findFragment);
        fragmentList.add(mineFragment);

        Log.i("time1",System.currentTimeMillis()+"");
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);

        mViewPager.setOffscreenPageLimit(4);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public CharSequence getPageTitle(int position) {
                return titleContainer.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mTabs = (AdvancedPagerSlidingTabStrip) findViewById(R.id.tabs);
        mTabs.setViewPager(mViewPager);

    }






}
