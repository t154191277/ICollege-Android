package wmlove.icollege.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import wmlove.icollege.MainActivity;
import wmlove.icollege.R;
import wmlove.icollege.factory.WindowsFactory;
import wmlove.icollege.http.okhttp.LoginContributor;
import wmlove.icollege.model.TokenModel;
import wmlove.icollege.model.UserModel;

public class LoginActivity extends Activity {

    private EditText idEditText;
    private EditText passwdEditText;
    private TextView txTextView;
    private LinearLayout mLinearLayout;
    private Button loginButton;
    private ProgressBar pb;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txTextView = (TextView) findViewById(R.id.tx);
        passwdEditText = (EditText) findViewById(R.id.passwd);
        idEditText = (EditText) findViewById(R.id.id);
        loginButton = (Button) findViewById(R.id.login);
        pb = (ProgressBar) findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

        mSharedPreferences = getSharedPreferences("bistu", Context.MODE_PRIVATE);
        final String login_id = mSharedPreferences.getString("id","");
        final String login_passwd = mSharedPreferences.getString("passwd","");
        Boolean isLogin = mSharedPreferences.getBoolean("login",false);

        if (!TextUtils.isEmpty(login_id) && !TextUtils.isEmpty(login_passwd))
        {

        }


        if (isLogin)
        {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        Point point = WindowsFactory.getWindowsSize(getApplicationContext());
        int x = point.x;
        int y = point.y;

        int height = y * 1 / 13;
        int width = x * 3 / 5;

        int margin = y * 1 / 60;

        int top = y * 3 / 5;
        int left = x * 1 / 5;
        int right = x * 1 / 5;
        int buttom = y  * ( 1 - 3 * height - 2 * margin );


        txTextView.setText(R.string.login_tx_info);
        txTextView.setGravity(Gravity.CENTER);
        txTextView.setTextSize(48l);

        mLinearLayout = (LinearLayout) findViewById(R.id.ll);
        //父控件Layout类型
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
//        params.setMargins(left,top,right,0);
//        mLinearLayout.setLayoutParams(params);


        idEditText.setHint(R.string.login_hint_id);
        idEditText.setWidth(width);
        idEditText.setHeight(height);


        passwdEditText.setHint(R.string.login_hint_passwd);
//        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
//        params1.setMargins(0,margin,0,margin);
//        passwdEditText.setLayoutParams(params1);
        passwdEditText.setWidth(width);
        passwdEditText.setHeight(height);


        loginButton.setText(R.string.login_btn_submit);
        loginButton.setWidth(width);
        loginButton.setHeight(height);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                String id = idEditText.getText().toString();
                String passwd = passwdEditText.getText().toString();
                if (TextUtils.isEmpty(id))
                {
                    Toast.makeText(LoginActivity.this,R.string.login_toast_id,Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(passwd))
                {
                    Toast.makeText(LoginActivity.this,R.string.login_toast_passwd,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //登录按钮设置为不可按
                    loginButton.setEnabled(false);

                    //验证账号密码
                    String body = null;
                    Boolean success = false;
                    UserModel user = new UserModel();
                    user.setId(id);
                    user.setPasswd(passwd);

                    Log.i("onClick","startAsyncTask...");

                    try
                    {
                        LoginAsyncTask login = new LoginAsyncTask();
                        login.execute(user);
                        body = login.get();
                        success = !TextUtils.isEmpty(body);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }

                    if (success)
                    {
                        TokenModel token = new Gson().fromJson(body,TokenModel.class);
                        String tokenStr = token.getToken();

                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString("token",tokenStr);
                        editor.putString("id",id);
                        editor.putString("passwd",passwd);
                        editor.putBoolean("login",true);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        passwdEditText.setText("");
                        loginButton.setEnabled(true);
                        Toast.makeText(LoginActivity.this,"账户或者密码错误",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

    }

    class LoginAsyncTask extends AsyncTask<Object, Object, String>
    {
        private String body = null;
        private UserModel mUser;

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            pb.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Object... params)
        {
            Log.i("LoginAsyncTask","doInBackground...");

            mUser = (UserModel) params[0];
            try
            {
                LoginContributor contributor = new LoginContributor();
                body = contributor.login(mUser);
                Log.i("LoginAsyncTask","body="+body);
            }
             catch (Exception e)
            {
                e.printStackTrace();
            }
            return body;
        }
    }


}
