package wmlove.icollege.http.okhttp;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import wmlove.icollege.factory.DESFactory;
import wmlove.icollege.factory.URLFactory;
import wmlove.icollege.model.UserModel;
import wmlove.icollege.utils.Constant;

/**
 * Created by wmlove on 2016/10/12.
 */
public class LoginContributor
{
    /**
     *
     * @param user
     * @return
     * @throws Exception
     */
    public String login(final UserModel user) throws Exception
    {
        //使用okhttp作为网络传输框架
        OkHttpClient client = new OkHttpClient();
        //拼接URL,Action
        String actionURL = URLFactory.appendAction(Constant.BASE_URL,Constant.ACTION_LOGIN_URL);

        Log.i("login","actionURL="+actionURL);

        //DES对id passwd进行加密传输
        String DESId = DESFactory.encrypt(user.getId());
        String DESPasswd = DESFactory.encrypt(user.getPasswd());

        Map<String,String> map = new HashMap<String, String>();
        map.put("id", DESId);
        map.put("passwd", DESPasswd);

        //拼接url,params
        String url = URLFactory.appendParams(actionURL,map);

        Log.i("login","url="+url);

        Request request = new Request.Builder().get().url(url).build();
        Response response = client.newCall(request).execute();

        String resultStr = null;

        if (response.isSuccessful())
        {
            ResponseBody body = response.body();
            BufferedSource bs = body.source();
            resultStr = bs.readUtf8();
            Log.i("login","str="+resultStr);
        }

        return resultStr;
    }
}
