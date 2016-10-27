package wmlove.bistu.timetable.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import wmlove.bistu.R;
import wmlove.bistu.http.okhttp.CourseContributor;
import wmlove.bistu.timetable.adapter.RCourseListAdapter;
import wmlove.bistu.model.CourseDBModel;
import wmlove.bistu.factory.DBFactory;
import wmlove.bistu.factory.TimeFactory;
import wmlove.library.CalendarView;
import wmlove.library.OnDaySelectListener;
import wmlove.library.OnTimeChangeListener;

/**
 * Created by wmlove on 2016/10/18.
 */
public class RTimeTableFragment extends Fragment{

    private SharedPreferences mSharedPreferences;
    private CalendarView mCalendarView;
    private TextView dateTextView;
    private Date mDate;
    private View root;
    private Context mContext;
    private ProgressBar mProgressBar;
    private TextView tx;
    private ListView mListView;
    private String id;
    private String token;
    private RCourseListAdapter adapter;

    private final int UPDATE_LISTVIEW = 0;
    private final int SHOW_TIP = 1;

    private final int NOT_IN_SEMESTER = 2;
    private final int NO_SELECT = 3;

    private GestureDetector detector = null;

    private Handler mListHanler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            //更新课程表List
            if (msg.what == UPDATE_LISTVIEW)
            {
                Bundle bundle = msg.getData();
                if (bundle != null)
                {
                    String selection = bundle.getString("selection");
                    String[] selectionArgs = bundle.getStringArray("selectionArgs");
                    DBFactory helper = new DBFactory(getContext());
                    Cursor cursor = helper.query(selection,selectionArgs);
                    helper.close();
                    if (cursor.getCount() == 0)
                    {
                        mListView.setVisibility(View.GONE);
                        tx.setVisibility(View.VISIBLE);
                        tx.setText(R.string.timetable_not_course);
                    }
                    else
                    {
                        adapter.setCursor(cursor);
                        adapter.notifyDataSetChanged();
                        mListView.setAdapter(adapter);
                        dateTextView.setVisibility(View.VISIBLE);
                        mListView.setVisibility(View.VISIBLE);
                        tx.setVisibility(View.GONE);
                    }
                    mProgressBar.setVisibility(View.GONE);
                }

            }
            //提示选择具体日期
            else if (msg.what == SHOW_TIP)
            {
                if (msg.arg1 == NO_SELECT)
                {
                    tx.setText(R.string.timetable_select_tip);
                }
                else if (msg.arg1 == NOT_IN_SEMESTER)
                {
                    tx.setText(R.string.timetable_not_in_semester_tip);
                }
                mListView.setVisibility(View.GONE);
                tx.setVisibility(View.VISIBLE);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mSharedPreferences = container.getContext().getSharedPreferences("bistu", Context.MODE_PRIVATE);
        id = mSharedPreferences.getString("id","");
        token = mSharedPreferences.getString("token","");
        Log.i("ID",id + "," + token);

        root = inflater.inflate(R.layout.fragment_timetable,container,false);

        tx = (TextView) root.findViewById(R.id.tx);
        tx.setText(R.string.timetable_progressbar_tx);

        mListView = (ListView) root.findViewById(R.id.listview);


        adapter = new RCourseListAdapter(container.getContext());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourseDBModel model = (CourseDBModel) mListView.getItemAtPosition(position);
                String title = model.getCourseName();
                String msg = "任课老师：" + model.getTeacher();
                Dialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(title)
                        .setMessage(msg)
                        .setPositiveButton("知道啦！", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        mProgressBar = (ProgressBar) root.findViewById(R.id.pb);

        mCalendarView = (CalendarView) root.findViewById(R.id.calendarview);

        dateTextView = (TextView)root.findViewById(R.id.dateTextView);
        dateTextView.setTextColor(Color.BLUE);

        detector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener()
        {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i("onTouch","onSingleTapUp");
                Calendar calendar = mCalendarView.getCurrentTime();
                String weekStr = TimeFactory.getCurrentWeek(calendar.getTime());
                final WeekSelectDialog dialog = new WeekSelectDialog(getContext(), weekStr);
                dialog.show();
                dialog.setOnSubmitClickListener(new WeekSelectDialog.onSubmitClickListener() {
                    @Override
                    public void onSubmitClick(int position) {
                        Log.i("onSubmitClick","position="+position);
                        dialog.dismiss();
                        mCalendarView.setCurrentTime(TimeFactory.getCalenderFromWeek(position + 1));
                    }
                });
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.i("onTouch","onDoubleTap");
                mCalendarView.setCurrentTime(Calendar.getInstance());
                return false;
            }
        });

        dateTextView.setText("第"+ TimeFactory.getCurrentWeek()+"周");
        dateTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("onTouch","ontouch");
                detector.onTouchEvent(event);
                return true;
            }
        });


        mCalendarView = (CalendarView) root.findViewById(R.id.calendarview);
        mCalendarView.setOnDaySelectListener(new OnDaySelectListener()
        {
            @Override
            public void OnDaySelect(Date date)
            {
                int week = Integer.valueOf(TimeFactory.getCurrentWeek(date));
                Log.i("OnDaySelect",week +"");
                if (week != -1)
                {
                    dateTextView.setText("第"+ week +"周");
                    dateTextView.setEnabled(true);
                    sendMeg(date);
                }
                else
                {
                    dateTextView.setText(new SimpleDateFormat("MMMMdd",Locale.CHINA).format(date));
                    dateTextView.setEnabled(false);
                    Message msg = new Message();
                    msg.what = SHOW_TIP;
                    msg.arg1 = NOT_IN_SEMESTER;
                    mListHanler.sendMessage(msg);
                }


            }
        });
        mCalendarView.setOnTimeChangeListener(new OnTimeChangeListener()
        {
            @Override
            public void OnTimeChange(Date currentDate)
            {

                int week = Integer.valueOf(TimeFactory.getCurrentWeek(currentDate));
                Log.i("OnTimeChange",week +"," + currentDate.toString());
                if (week != -1)
                {
                    dateTextView.setText("第"+ week +"周");
                    dateTextView.setEnabled(true);
                }
                else
                {
                    dateTextView.setText(new SimpleDateFormat("MMMMdd",Locale.CHINA).format(currentDate));
                    dateTextView.setEnabled(false);
                }

                //显示TextView要求选择日期
                Message msg = new Message();
                msg.what = SHOW_TIP;
                msg.arg1 = NO_SELECT;
                mListHanler.sendMessage(msg);
            }
        });

        mCalendarView.setSelectColor(Color.GRAY);
        mCalendarView.setSelectSize(120);


        Boolean isSaved = mSharedPreferences.getBoolean("isSaved",false);
        //数据库没有存储就去请求服务器加载数据，然后储存到数据库、
        Log.i("isSaved",isSaved + "");
        if (!isSaved)
        {
            Log.i("loadDatas","异步加载...");
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            try {
                CourseDownLoadAsyncTask downLoadAsyncTask = new CourseDownLoadAsyncTask();
                downLoadAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, id, token, getContext());
                Log.i("loadDatas","异步加载成功...");
                editor.putBoolean("isSaved",true);
            } catch (Exception e) {
                Log.i("loadDatas","异步加载失败...");
                editor.putBoolean("isSaved",false);
            }
            editor.commit();
        }
        Log.i("loadDatas","数据库加载...");
        if (isSaved)
        {
            sendMeg();
        }
        return root;
    }

    /**
     * 异步向服务器获取Course数据
     */
    class CourseDownLoadAsyncTask extends AsyncTask<Object,Object,Object>
    {

        @Override
        protected void onPreExecute() {
            mCalendarView.setVisibility(View.GONE);
            dateTextView.setVisibility(View.GONE);
            mListView.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Object object) {
            mProgressBar.setVisibility(View.GONE);
            tx.setVisibility(View.GONE);
            mCalendarView.setVisibility(View.VISIBLE);
            dateTextView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object... params) {
            String id = (String) params[0];
            String token = (String) params[1];
            Context context = (Context) params[2];

            try {
                CourseContributor contributor = new CourseContributor();
                contributor.getAndSaveCourse(id,token,context);
                Thread.sleep(1000);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    /**
     * 发送消息更新List
     * @param params {week,day}
     */
    private void sendMeg(Date... params)
    {
        Date date = null;
        String week = null;
        String day = null;
        if (params.length == 1)
        {
            date = params[0];
            week = TimeFactory.getCurrentWeek(date);
            day = TimeFactory.getCurrentDay(date);
        }
        else if (params.length == 0)
        {
            week = TimeFactory.getCurrentWeek();
            day = TimeFactory.getCurrentDay();
        }
        else
        {
            Log.e("sendMeg","params缺少参数...");
            return;
        }

        Message msg = new Message();
        msg.what = UPDATE_LISTVIEW;
        Bundle bundle = new Bundle();

        String selection = "openWeek <= ? and closeWeek >= ? and day = ?";
        String[] selectionArgs = new String[]{week, week, day};
        Log.i("selectionArgs",selectionArgs[0] + "," + selectionArgs[1]);
        bundle.putString("selection",selection);
        bundle.putStringArray("selectionArgs",selectionArgs);
        msg.setData(bundle);
        mListHanler.sendMessage(msg);
    }
}
