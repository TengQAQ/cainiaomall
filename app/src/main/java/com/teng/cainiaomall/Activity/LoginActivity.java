package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.R;

public class LoginActivity extends AppCompatActivity {
    private TextView mEditTextUserName,mEditTextPasswd,mLoginregister,mLoginpassword,mLoginhelp;
    private Button mButtonLogin;
    private String userName,userPasswd;
    private Boolean info;
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
                if (user_dao.FindUserById(userName,userPasswd)){
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent();
                    intent.setClass(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}