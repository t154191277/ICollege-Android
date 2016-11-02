package wmlove.icollege.timetable.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;


import wmlove.icollege.R;
import wmlove.icollege.chat.view.ChatFragment;
import wmlove.icollege.model.CourseModel;
import wmlove.icollege.utils.NoScrollViewPager;

/**
 * Created by wmlove on 2016/10/18.
 */
public class TimeTableFragment extends Fragment
{
    private List<CourseModel> courseList;
    private NoScrollViewPager mViewPager;
    private AdvancedPagerSlidingTabStrip tabs;
    private View root;

    ArrayList<String> titleContainer = new ArrayList<String>();
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        titleContainer.add("班级");
        titleContainer.add("课程");

        RTimeTableFragment rtimeTableFragment = new RTimeTableFragment();
        LTimeTableFragment ltimeTableFragment = new LTimeTableFragment();

        fragmentList.add(ltimeTableFragment);
        fragmentList.add(rtimeTableFragment);

        View root = inflater.inflate(R.layout.fragment_timetable_main,null);
        mViewPager = (NoScrollViewPager) root.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

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
        mViewPager.setOffscreenPageLimit(2);
        tabs = (AdvancedPagerSlidingTabStrip) root.findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);
        return root;

    }
}
