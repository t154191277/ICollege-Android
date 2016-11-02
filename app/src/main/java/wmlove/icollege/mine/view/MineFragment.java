package wmlove.icollege.mine.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Interpolator;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import wmlove.icollege.R;
import wmlove.icollege.http.okhttp.StudentContributor;
import wmlove.icollege.login.view.LoginActivity;
import wmlove.icollege.mine.util.BitmapFactory;

/**
 * Created by wmlove on 2016/10/18.
 */
public class MineFragment extends Fragment{

    private Context mContext;

    private ImageView iv_icon;

    private Uri tempUri;

    public MineFragment(Context context) {
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mine,container,false);

        TextView tv_username = (TextView) root.findViewById(R.id.userName);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("bistu",Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        String token = sharedPreferences.getString("token","");
        String name = sharedPreferences.getString("name","");
        if (TextUtils.isEmpty(name))
        {
            StudentAsyncTask task = new StudentAsyncTask();
            task.execute(id, token, getContext());
            name = sharedPreferences.getString("name","");
        }

        tv_username.setText(name);

        TextView tv_query = (TextView) root.findViewById(R.id.query);
        tv_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QueryActivity.class);
                startActivity(intent);
            }
        });


        iv_icon = (ImageView) root.findViewById(R.id.icon);
        Resources resources = getContext().getResources();
        Bitmap bitmap = android.graphics.BitmapFactory.decodeResource(resources, R.drawable.icon);
        Bitmap rBitMap = BitmapFactory.toRoundBitmap(bitmap);
        iv_icon.setImageBitmap(rBitMap);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(R.string.mine_set_icon)
                        .setItems(new String[]{"从相册选择","摄像头获取"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which)
                                {
                                    case 0: // 选择本地照片
                                        Intent openAlbumIntent = new Intent(
                                                Intent.ACTION_GET_CONTENT);
                                        openAlbumIntent.setType("image/*");
                                        startActivityForResult(openAlbumIntent, 0);
                                        break;
                                    case 1: // 拍照
                                        Intent openCameraIntent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        tempUri = Uri.fromFile(new File(Environment
                                                .getExternalStorageDirectory(), "image.jpg"));
                                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                        startActivityForResult(openCameraIntent, 1);
                                        break;
                                }
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        TextView btn_logout = (TextView) root.findViewById(R.id.logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("bistu",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login",false);
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode) {
                case 0:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case 1:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case 2:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = BitmapFactory.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            iv_icon.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = BitmapFactory.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
        }
    }

    class StudentAsyncTask extends AsyncTask<Object,Object,Object>
    {
        private String name = null;

        @Override
        protected void onCancelled() {
        }

        @Override
        protected void onPreExecute() {
//            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object list) {
//            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object... params) {
            String id = (String) params[0];
            String token = (String) params[1];
            Context context = (Context) params[2];

            StudentContributor contributor = new StudentContributor();
            try {
                contributor.getAndSaveStudentInfo(id, token, context);
            } catch (IOException e) {
                Log.i("StudentAsyncTask","Student数据获取失败...");
            }
            return null;
        }
    }
}
