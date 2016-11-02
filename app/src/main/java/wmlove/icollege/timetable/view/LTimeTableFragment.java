package wmlove.icollege.timetable.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import wmlove.icollege.R;
import wmlove.icollege.factory.DBFactory;
import wmlove.icollege.model.CourseDBModel;
import wmlove.icollege.timetable.adapter.LCourseListAdapter;

/**
 * Created by wmlove on 2016/10/24.
 */
public class LTimeTableFragment extends Fragment{

    private ListView mListView;

    private LCourseListAdapter mAdapter;

    private final int UPDATE_COURSE_LIST = 1;

    private Handler mHandlerCourseList = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_COURSE_LIST)
            {
                Cursor cursor = getCourseCursorFromDB();
                mAdapter.setCursor(cursor);
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_course, container, false);
        mListView = (ListView) root.findViewById(R.id.listview);
        mAdapter = new LCourseListAdapter(getContext());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("model", (CourseDBModel)mListView.getItemAtPosition(position));
                Intent intent = new Intent(getActivity(), CourseInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Message msg = new Message();
        msg.what = UPDATE_COURSE_LIST;
        mHandlerCourseList.sendMessage(msg);
        return root;
    }

    private Cursor getCourseCursorFromDB()
    {
        DBFactory dbHelper = new DBFactory(getContext());
        Cursor cursor = dbHelper.queryCourseOrderByWeek();
        dbHelper.close();
        return cursor;
    }
}
