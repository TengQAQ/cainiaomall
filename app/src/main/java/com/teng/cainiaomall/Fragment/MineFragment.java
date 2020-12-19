package com.teng.cainiaomall.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.teng.cainiaomall.Activity.ChangePhoto;
import com.teng.cainiaomall.Activity.LoginActivity;
import com.teng.cainiaomall.Activity.MainActivity;
import com.teng.cainiaomall.Activity.RetrievePasswordActivity;
import com.teng.cainiaomall.Activity.Setinformation;
import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        myavatar=view.findViewById(R.id.myavatar);
        mhomepage_mine=view.findViewById(R.id.homepage_mine);
        myname=view.findViewById(R.id.myname);

        sp = getActivity().getSharedPreferences("user_rememberpass", Context.MODE_PRIVATE);
        String userid = sp.getString("username", "");
        User_Dao userDao1 =new User_Dao(getActivity());
        User user2= userDao1.findUser(userid);
        myname.setText(user2.getUser_name());
        if (user2.getUser_avatar() != null){
            myavatar.setImageBitmap(BitmapFactory.decodeFile(user2.getUser_avatar()));
        }




        mhomepage_mine.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setClass(getActivity(), Setinformation.class);
            startActivity(intent);
        });

        myavatar.setOnClickListener(v->{
            Intent intent2=new Intent();
            intent2.setClass(getActivity(), ChangePhoto.class);
            startActivity(intent2);
        });

        return view;
    }

    
}