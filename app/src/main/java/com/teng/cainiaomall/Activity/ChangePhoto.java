package com.teng.cainiaomall.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.teng.cainiaomall.R;

import java.io.File;
import java.io.FileNotFoundException;

public class ChangePhoto extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSIONS_STORAGE = 100;
    private static final int REQUEST_CODE_PERMISSIONS_CAMERA = 101;

    static final int REQUEST_CODE_PICK_IMAGE = 200;
    static final int REQUEST_CODE_CAPTURE_IMAGE = 201;
    static final int REQUEST_CODE_CROP_IMAGE = 202;

    // 对应 Manifest.xml 中 provider 标签下的 authorities
    private static final String FILE_PROVIDER_AUTHORITY = "com.teng.cainiaomall.fileprovider";

    // 保存拍照存放照片的绝对路径
    private static Uri mContentUri = null;
    // 保存图片裁剪后的绝对路径
    private static Uri mCropImageUri = null;

    private ImageView mShowImageView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_photo);
        mShowImageView = findViewById(R.id.iv_show);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.READ_PHONE_STATE}, 0);
        }
        /*Log.d("wlfTest", "getFilesDir().toString()"+getFilesDir().toString());
        Log.d("wlfTest", "getCacheDir().toString()"+getCacheDir().toString());
        Log.d("wlfTest", "Environment.getExternalStorageDirectory().toString()"+Environment.getExternalStorageDirectory().toString());
        Log.d("wlfTest", "getExternalFilesDir(null).toString()"+getExternalFilesDir(null).toString());
        Log.d("wlfTest", "getExternalCacheDir().toString()"+getExternalCacheDir().toString());
        Log.d("wlfTest", "getExternalMediaDirs().toString()"+getExternalMediaDirs().toString());*/
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_picture :
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSIONS_STORAGE);
                } else {
                    chooseImage(this);
                }
                break;
            case R.id.btn_camera:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CODE_PERMISSIONS_CAMERA);
                } else {
                    captureImage(this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS_STORAGE &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            chooseImage(this);
        }
        if (requestCode == REQUEST_CODE_PERMISSIONS_CAMERA &&
                grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            captureImage(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        startPhoneZoom(this, data.getData(), false);
                    }
                }
                break;
            case REQUEST_CODE_CAPTURE_IMAGE:
                if (resultCode == RESULT_OK) {
                    startPhoneZoom(this, getContentUri(), true);
                }
                break;
            case REQUEST_CODE_CROP_IMAGE:
                if (resultCode == RESULT_OK) {
                    Bitmap bp = null;
                    try {
                        // 将 uri 转成位图形式
                        bp = BitmapFactory.decodeStream(getContentResolver().openInputStream(getCropImageUri()
                        ));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    mShowImageView.setImageBitmap(bp);
                }
                break;
            default:
                break;
        }
    }
    static void chooseImage(Activity activity) {
      /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);*/
        /*//打开相册
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO); // 打开相册*/
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    static void captureImage(Activity activity) {
        // 注意这个路径下的目录需要定义在 Manifest.xml-provider-meta-data 标签中来获得共享权限
        // 否则会抛出 SecurityException 异常
        File imagePath = new File(activity.getFilesDir(), "myimages");//gn file_paths.xml文件中的files-path标签中的path值一致
        File newFile = new File(imagePath, System.currentTimeMillis() + ".jpg");
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }
        // 注意这里需要使用 content:// 形式暴露，否则在 7.0 以上系统会抛出 FileUriExposedException 异常
        mContentUri = FileProvider.getUriForFile(activity, FILE_PROVIDER_AUTHORITY, newFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mContentUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
    }
    static void startPhoneZoom(Activity activity, Uri uri, boolean isCaptureImage) {
        File file = new File(activity.getExternalFilesDir(null), "myimages");
       /* String saveDir=sdPicturesPath + "/MyWriteProduct";
        File filedir=new File(saveDir);
        if(!filedir.exists()){
            filedir.mkdir();
        }*/
        /* File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString(), "/myimages");*/
        if (!file.exists()) {
            file.mkdir();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (isCaptureImage) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(uri, "image/*");
            mCropImageUri = Uri.parse("file://" + file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
            // 这个 outputUri 是要使用 Uri.fromFile(file) 生成的，而不是使用FileProvider.getUriForFile
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
            activity.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
        }else{
            /*String IMAGE_FILE_LOCATION = "file:///" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() +
                    "/myimages/"+System.currentTimeMillis()+".jpg";*/
            String IMAGE_FILE_LOCATION = "file:///" +  file.getAbsolutePath() + "/"+System.currentTimeMillis()+".jpg";
            intent.setDataAndType(uri, "image/*");
            mCropImageUri= Uri.parse(IMAGE_FILE_LOCATION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImageUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            activity.startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
        }
    }
    static Uri getContentUri() {
        return mContentUri;
    }

    public static Uri getCropImageUri() {
        return mCropImageUri;
    }
}