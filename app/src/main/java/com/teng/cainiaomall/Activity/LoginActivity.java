package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.Admin_Dao;
import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.Admin;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private TextView mEditTextUserName,mEditTextPasswd,mLoginregister,mLoginpassword,mLoginhelp;
    private Button mButtonLogin;
    private String userName,userPasswd;
    private SharedPreferences sp= null;//保存登录后的用户名
    private SharedPreferences.Editor editor=null;
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


        Intent intent=getIntent();
        String user9277548 =intent.getStringExtra("user_id");
        mEditTextUserName.setText(user9277548);

        if (isremenberpassword){
            mEditTextUserName.setText(sp.getString("username",userName));
            mEditTextPasswd.setText(sp.getString("password",userPasswd));
            mremenberpassword.setChecked(true);
        }

        mLoginhelp.setOnClickListener(v -> {
            Intent intent5= new Intent();
            intent5.setAction(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse("https://kf.qq.com/product/QQ.html");
            intent5.setData(content_url);
            startActivity(intent5);
        });

        mLoginpassword.setOnClickListener(v -> {
            Intent intent1=new Intent();
            intent1.setClass(LoginActivity.this,RetrievePasswordActivity.class);
            startActivity(intent1);
        });

        mLoginregister.setOnClickListener(v -> {
            Intent intent2=new Intent();
            intent2.putExtra("user_id",userName);
            intent2.setClass(LoginActivity.this,RegisterActivity.class);
            startActivity(intent2);
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
                Admin_Dao admin_dao = new Admin_Dao(LoginActivity.this);
                Admin admin=admin_dao.adminLogin(userName);
                if (admin == null) {

                }else {
                    if (admin.getAdmin_id().equals(userName)&&admin.getAdmin_password().equals(userPasswd)){
                        Intent intent4 =new Intent();
                        intent4.setClass(LoginActivity.this, Main_Admin.class);
                        startActivity(intent4);
                        finish();
                    }
                    return;
                }

                User user=user_dao.findUser(userName);
                if (user==null){}else {
                    if (user.getUser_id().equals(userName)&&user.getUser_password().equals(userPasswd)){
                        if (user.getUser_statue() == 1){//1正常登录
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
                            Intent intent3 =new Intent();
                            intent3.setClass(LoginActivity.this,MainActivity.class);
                            startActivity(intent3);
                            finish();
                        }
                        else if (user.getUser_statue() == 2){
                            Toast.makeText(LoginActivity.this, "请耐心等待管理员审核", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this,"账号异常,无法登录",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this,"账号或者密码错误",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

}