package com.teng.cainiaomall.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.Resource;
import com.makeramen.roundedimageview.RoundedImageView;
import com.teng.cainiaomall.Activity.MainActivity;
import com.teng.cainiaomall.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.MEDIA_MOUNTED;
import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class MineFragment extends Fragment {
    private ImageView myavatar;
    public static final int TAKE_CAMERA = 1;
    private static final int CHOOSE_PHOTO = 2;
    private String imagdate;
    private Uri imageUri;

    String imagepath="";//存储目录


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        myavatar=view.findViewById(R.id.myavatar);
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 0);
        }

        myavatar.setOnClickListener(v -> {
            String type[]=new String[2];
            type[0]="拍照";
            type[1]="从相册选择图片";

            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("更换头像");
            builder.setItems(type, (dialog, which) -> {
                if(which==0){
                    File imagePath = new File(getActivity().getFilesDir(), "myavatar");//gn file_paths.xml文件中的files-path标签中的path值一致
                    if (!imagePath.exists()) {
                        imagePath.mkdirs();
                    }
                    imagdate = new SimpleDateFormat("yyyy_MMdd_hhmmss").format(new Date());
                    File newFile = new File(imagePath, imagdate + ".jpg");
                    imagepath=newFile.getPath();
                    System.out.println("newFile.getPath()="+newFile.getPath());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //大于等于版本24（7.0）的场合
                        imageUri = FileProvider.getUriForFile(getActivity(), "com.teng.cainiaomall.fileprovider", newFile);
                        //此处的outputImage指定的路径要在file_paths.xml文件对应配置path指定路径的子路径
                    } else {
                        //小于android 版本7.0（24）的场合
                        imageUri = Uri.fromFile(newFile);
                    }
                    //启动相机程序
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //MediaStore.ACTION_IMAGE_CAPTURE = android.media.action.IMAGE_CAPTURE
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_CAMERA);
                }
                else if(which==1){
                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent1, CHOOSE_PHOTO);
                }
            });
            builder.show();
        });
        return view;
    }

    @SuppressLint("RestrictedApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
//                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        myavatar.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;

        }
    };

    //打开手机图库
    private void open(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }


}