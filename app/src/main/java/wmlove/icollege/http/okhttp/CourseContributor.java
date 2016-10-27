package wmlove.bistu.http.okhttp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import wmlove.bistu.model.CourseModel;
import wmlove.bistu.factory.DBFactory;
import wmlove.bistu.utils.Constant;
import wmlove.bistu.factory.URLFactory;

/**
 * Created by wmlove on 2016/10/17.
 */
public class CourseContributor {

    /**
     *
     */
    private static SQLiteDatabase db;

    /**
     *
     * @param id
     * @param token
     * @param context
     * @throws IOException
     */
    public void getAndSaveCourse(String id, String token, final Context context) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String actionURL = URLFactory.appendAction(Constant.BASE_URL,Constant.ACTION_COURSE_URL);
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        map.put("token",token);
        String url = URLFactory.appendParams(actionURL,map);
        Log.i("getCourse","url="+url);

        Request request = new Request.Builder().get().url(url).build();


//        Response response = client.newCall(request).execute();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Callback","连接失败...");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful())
                {
                    ResponseBody body = response.body();
                    BufferedSource source = body.source();
                    String resultStr = source.readUtf8();
                    Log.i("resultStr",resultStr);
                    List<CourseModel> courseList = new Gson().fromJson(resultStr, new TypeToken<List<CourseModel>>(){}.getType());
                    saveCourse(courseList, context);
                }
            }
        });
    }

    /**
     *
     * @param courseList
     * @param context
     */
    public void saveCourse(List<CourseModel> courseList,Context context)
    {
        DBFactory helper = new DBFactory(context);
        ContentValues modelCV = null;

        Pattern r;

        for (CourseModel model : courseList)
        {
            List<String> times = model.getCourseTime();
            List<Map<String,String>> timesMapList = new ArrayList<>();
            for (String str : times)
            {
                str = str.trim();
                Log.i("times","str="+str);
                String timePattern = null;
                if (str.contains("|"))
                {
                    timePattern = "周(.*)第(.*)节.第(.*)-(.*)周\\|(.*).";
                }
                else
                {
                    timePattern = "周(.*)第(.*)节.第(.*)-(.*)周(.*)\\}";
                }
                r = Pattern.compile(timePattern);
                Matcher matcher = r.matcher(str);

                Map<String,String> map = new HashMap<>();
                if (matcher.find())
                {
                    String day = getDayNumStr(matcher.group(1));
                    String hour = matcher.group(2);
                    String[] _hour = hour.split(",");
                    String start1 = _hour[0];
                    String end1 = _hour[_hour.length - 1];
                    String start2 = matcher.group(3);
                    String end2 = matcher.group(4);
                    String isSingle = matcher.group(5);
                    Log.i("times","day="+ day + "start1="+start1 + "end1=" + end1 + "start2="+start2 + "end2=" + end2+ "isSigle=" + isSingle);


                    map.put("day",day);
                    map.put("startLine", start1);
                    map.put("deadLine", end1);
                    map.put("openWeek", start2);
                    map.put("closeWeek", end2);
                    map.put("isSingle", isSingle);

                }
                timesMapList.add(map);
            }

            List<String> places = model.getPlace();
            //placesList 和 timesMapList size 相同
            for (int i = 0; i < places.size(); i++)
            {
                String place = places.get(i);
                if (place.endsWith("&nbsp"))
                {
                    place = "";
                }
                Log.i("places",place);

                modelCV= new ContentValues();

                modelCV.put("_id", model.getId() + "-" + i);
                modelCV.put("courseCode", model.getCourseCode());
                modelCV.put("courseName", model.getCourseName());
                modelCV.put("courseType", model.getCourseType());
                modelCV.put("teacher", model.getTeacher());
                modelCV.put("credit", model.getCredit());

                modelCV.put("day", timesMapList.get(i).get("day"));
                modelCV.put("startLine", timesMapList.get(i).get("startLine"));
                modelCV.put("deadLine", timesMapList.get(i).get("deadLine"));
                modelCV.put("openWeek", timesMapList.get(i).get("openWeek"));
                modelCV.put("closeWeek", timesMapList.get(i).get("closeWeek"));
                modelCV.put("isSingle", timesMapList.get(i).get("isSingle"));

                modelCV.put("place", place);

                try {
                    helper.insert(modelCV);
                } catch (Exception e) {
                    Log.i("insert","插入失败...");
                }
            }
            helper.close();
        }
    }

    /**
     *
     * @param day
     * @return
     */
    private String getDayNumStr(String day)
    {
        if (day.equals("一"))
        {
            day = "1";
        }
        else if (day.equals("二"))
        {
            day = "2";
        }
        else if (day.equals("三"))
        {
            day = "3";
        }
        else if (day.equals("四"))
        {
            day = "4";
        }
        else if (day.equals("五"))
        {
            day = "5";
        }
        else if (day.equals("六"))
        {
            day = "6";
        }
        else if (day.equals("七"))
        {
            day = "7";
        }
        return day;
    }

}
