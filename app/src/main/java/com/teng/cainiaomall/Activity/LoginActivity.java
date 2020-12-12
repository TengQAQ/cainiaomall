package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private TextView mEditTextUserName,mEditTextPasswd,mLoginregister,mLoginpassword,mLoginhelp;
    private Button mButtonLogin;
    private String userName,userPasswd;
    SharedPreferences sp= null;//保存登录后的用户名
    SharedPreferences.Editor editor=null;
    private CheckBox mremenberpassword;
    private boolean isremenberpassword;

    @SuppressLint({"WrongViewCast", "NewApi"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEditTextUserName = findViewById(R.id.userName);
        mEditTextPasswd = findViewById(R.id.userPasswd);
        mButtonLogin = findViewById(R.id.login);
        mLoginregister = findViewById(R.id.Loginregister);
        mLoginpassword = findViewById(R.id.Loginpassword);
        mLoginhelp =findViewById(R.id.Loginhelp);
        mremenberpassword=findViewById(R.id.remenberpassword);

        sp=getSharedPreferences("user_rememberpass", Context.MODE_PRIVATE);
        editor=sp.edit();
        isremenberpassword=sp.getBoolean("bt_isremember",false);

        if (isremenberpassword){
            mEditTextUserName.setText(sp.getString("username",userName));
            mEditTextPasswd.setText(sp.getString("password",userPasswd));
            mremenberpassword.setChecked(true);
        }

        mLoginhelp.setOnClickListener(v -> {
            Intent intent= new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse("https://kf.qq.com/product/QQ.html");
            intent.setData(content_url);
            startActivity(intent);
        });

        mLoginpassword.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this,RetrievePasswordActivity.class);
            startActivity(intent);
        });

        mLoginregister.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setClass(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
        mButtonLogin.setOnClickListener( v -> {
            userName = mEditTextUserName.getText().toString();
            userPasswd = mEditTextPasswd.getText().toString();
            User_Dao user_dao=new User_Dao(LoginActivity.this);
            if (userName.isEmpty() || userPasswd.isEmpty()){
                Toast.makeText(LoginActivity.this,"请输入账号密码",Toast.LENGTH_LONG).show();
                Log.i("操作错误","输入为空");
            }else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时");//获取当前时间戳
                Date date = new Date(System.currentTimeMillis());

                User user=user_dao.findUser(userName);
                if (user.getUser_id().equals(userName)&&user.getUser_password().equals(userPasswd)){
                    if (user.getUser_statue() == 0){
                        if (mremenberpassword.isChecked()){
                            editor.putBoolean("bt_isremember",true);
                            editor.putString("logintime",simpleDateFormat.format(date));
                            editor.putString("password",userPasswd);
                            editor.putString("username",userName);
                        }else {
                            editor.clear();
                        }
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent();
                        intent.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if (user.getUser_statue() == 1){
                        Toast.makeText(LoginActivity.this, "请耐心等待管理员审核", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this,"账号已被封禁,无法登录",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"账号或者密码错误",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}