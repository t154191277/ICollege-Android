package wmlove.icollege.http.okhttp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import wmlove.icollege.factory.URLFactory;
import wmlove.icollege.model.StudentModel;
import wmlove.icollege.utils.Constant;

/**
 * Created by wmlove on 2016/10/26.
 */
public class StudentContributor {

    public void getAndSaveStudentInfo(String id, String token, final Context context) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String actionURL = URLFactory.appendAction(Constant.BASE_URL,Constant.ACTION_STUDENT_URL);
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        map.put("token",token);
        String url = URLFactory.appendParams(actionURL,map);

        Request request = new Request.Builder().get().url(url).build();
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
                    List<StudentModel> studentList = new Gson().fromJson(resultStr, new TypeToken<List<StudentModel>>(){}.getType());

//                    DBFactory helper = new DBFactory(context);
                    ContentValues modelCV = null;

                    String userName = null;
                    for (StudentModel model : studentList)
                    {
//                        modelCV= new ContentValues();
//
//                        modelCV.put("_class", model.get_Class());
//                        modelCV.put("id", model.getId());
//                        modelCV.put("department", model.getDepartment());
//                        modelCV.put("idcart", model.getIdcart());
//                        modelCV.put("marjoy", model.getMarjoy());
                        userName = model.getStuName();
                        Log.i("getAndSaveStudentInfo",model.toString());
//                    helper.close();
                    }
                    SharedPreferences.Editor editor = context.getSharedPreferences("bistu",Context.MODE_PRIVATE).edit();
                    editor.putString("name",userName);
                    editor.commit();
                }
            }
        });
    }

}
