package com.teng.cainiaomall.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.teng.cainiaomall.Activity.ChangePhoto;
import com.teng.cainiaomall.Activity.Setinformation;
import com.teng.cainiaomall.Activity.User_collection;
import com.teng.cainiaomall.Activity.User_personal_data;
import com.teng.cainiaomall.Activity.User_wallet;
import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class MineFragment extends Fragment {
    private ImageView myavatar;
    public static final int TAKE_CAMERA = 1;
    private static final int CHOOSE_PHOTO = 2;
    private static final int commit = 3;
    private View mhomepage_mine;
    private Uri imageUri;//原图保存地址
    private SharedPreferences sp= null;
    String imagdate;
    String imagepath = "";
    TextView myname;
    LinearLayout user_wallet,user_collection,user_personal_data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        myavatar = view.findViewById(R.id.myavatar);
        mhomepage_mine = view.findViewById(R.id.homepage_mine);
        myname = view.findViewById(R.id.myname);
        user_wallet = view.findViewById(R.id.user_wallet);
        user_collection = view.findViewById(R.id.user_collection);
        user_personal_data = view.findViewById(R.id.user_personal_data);

        sp = getActivity().getSharedPreferences("user_rememberpass", Context.MODE_PRIVATE);
        String userid = sp.getString("username", "");
        User_Dao userDao1 = new User_Dao(getActivity());
        User user2 = userDao1.findUser(userid);
        myname.setText(user2.getUser_name());
        String savepath = getActivity().getFilesDir() + "/" + user2.getUser_avatar();
        Log.i(TAG, "路径输出: " + savepath);
        if (user2.getUser_avatar() != null) {
            Bitmap bitmap =BitmapFactory.decodeFile(savepath);
            Matrix matrix =new Matrix();
            matrix.setRotate(90);
            bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            myavatar.setImageBitmap(bitmap);
        }

        user_wallet.setOnClickListener(v -> {
            Intent intent8 = new Intent();
            intent8.setClass(getActivity(), User_wallet.class);
            startActivity(intent8);
        });

        user_collection.setOnClickListener(v -> {
            Intent intent7 = new Intent();
            intent7.setClass(getActivity(), User_collection.class);
            startActivity(intent7);
        });

        mhomepage_mine.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), Setinformation.class);
            startActivity(intent);
        });

        user_personal_data.setOnClickListener(v -> {
            Intent intent2 = new Intent();
            intent2.setClass(getActivity(), User_personal_data.class);
            startActivity(intent2);
        });

        myavatar.setOnClickListener(v -> {
            Intent intent5 = new Intent();
            intent5.setClass(getActivity(), ChangePhoto.class);
            startActivity(intent5);
        });
        return view;
    }
    
}