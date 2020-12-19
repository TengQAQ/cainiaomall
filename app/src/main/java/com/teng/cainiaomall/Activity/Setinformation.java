package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

public class Setinformation extends AppCompatActivity {
    TextView modification_username,modification_phone,modification_password,modification_passwordagain,modification_commit,modification_back;
    private String password,passwordagain,phone,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setinformation);

        modification_username=findViewById(R.id.modification_username);
        modification_password=findViewById(R.id.modification_password);
        modification_passwordagain=findViewById(R.id.modification_passwordagain);
        modification_phone=findViewById(R.id.modification_phone);
        modification_commit=findViewById(R.id.modification_commit);
        modification_back=findViewById(R.id.modification_back);

        modification_back.setOnClickListener(v->{

        });


        modification_commit.setOnClickListener(v->{
            password=modification_password.getText().toString();
            passwordagain=modification_passwordagain.getText().toString();
            phone=modification_phone.getText().toString();
            username=modification_username.getText().toString();
            if (password.equals(passwordagain)){
                Log.i("2", String.valueOf(phone.length()));
                if (phone.length()==11){
                    SharedPreferences sp =getSharedPreferences("user_rememberpass",MODE_PRIVATE);
                    User user =new User();
                    user.setUser_name(username);
                    user.setUser_password(password);
                    user.setUser_tel(Long.valueOf(phone));
                    user.setUser_id(sp.getString("username",""));
                    User_Dao userDao =new User_Dao(Setinformation.this);
                    if (userDao.update_user(user)>0){
                        Toast.makeText(Setinformation.this,"数据已更新",Toast.LENGTH_LONG);
                        Intent intent =new Intent();
                        intent.setClass(Setinformation.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Setinformation.this,"保存失败",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(Setinformation.this,"格式不正确",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(Setinformation.this,"密码不一致",Toast.LENGTH_LONG).show();
            }
        });
    }
}