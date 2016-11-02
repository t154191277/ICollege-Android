package wmlove.icollege.factory;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wmlove on 2016/10/17.
 */
public class SharedPreferencesFactory {


    public static SharedPreferences getSharedPreferences(Context context,String contentStr)
    {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(contentStr, Context.MODE_PRIVATE);
        return mSharedPreferences;
    }
}
