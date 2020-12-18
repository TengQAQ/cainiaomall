package com.teng.cainiaomall.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.teng.cainiaomall.Activity.LoginActivity;
import com.teng.cainiaomall.Activity.MainActivity;
import com.teng.cainiaomall.Activity.RetrievePasswordActivity;
import com.teng.cainiaomall.Activity.Setinformation;
import com.teng.cainiaomall.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends Fragment {
    private ImageView myavatar;
    public static final int TAKE_CAMERA = 1;
    private static final int CHOOSE_PHOTO = 2;
    private View mhomepage_mine;
    private Uri imageUri;//原图保存地址
    ImageView imageView;
    String imagdate;
    String imagepath = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        myavatar=view.findViewById(R.id.myavatar);
        mhomepage_mine=view.findViewById(R.id.homepage_mine);

        mhomepage_mine.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setClass(getActivity(), Setinformation.class);
            startActivity(intent);
        });

        myavatar.setOnClickListener(v->{
            String type[]=new String[2];
            type[0]="拍照";
            type[1]="从相册中选择";
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("选择注册类型");
            builder.setItems(type, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which==0){
                        File imagePath = new File(getActivity().getFilesDir(), "myimages");//gn file_paths.xml文件中的files-path标签中的path值一致
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
                    }else if(which==1){
                        Intent intent=new Intent();
//                        intent.setClass(MineFragment.this,Distributor_Register.class);
                        startActivity(intent);
                    }
                }
            });
            builder.show();
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:break;
        }
    }

}