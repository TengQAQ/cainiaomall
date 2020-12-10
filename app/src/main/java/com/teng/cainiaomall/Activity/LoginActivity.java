package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

public class LoginActivity extends AppCompatActivity {
    private TextView mEditTextUserName,mEditTextPasswd,mLoginregister,mLoginpassword,mLoginhelp;
    private Button mButtonLogin;
    private String userName,userPasswd;
    SharedPreferences sp= null;//保存登录后的用户名
    SharedPreferences.Editor editor=null;
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
                User user=user_dao.findUser(userName);
                if (user.getUser_id().equals(userName)&&user.getUser_password().equals(userPasswd)){
                    if (user.getUser_statue() == 0){
                        sp=getSharedPreferences("disdata", Context.MODE_PRIVATE);
                        editor=sp.edit();
                        editor.putString("username",userName);
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
                    Toast.makeText(LoginActivity.this,"账号不存在",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}