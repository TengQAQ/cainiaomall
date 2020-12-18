package com.teng.cainiaomall.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

public class RegisterActivity extends AppCompatActivity {
    EditText mregist_phone,mregist_userid,mregist_password,mregist_passwordagain;
    TextView mregist_commit,mregist_back;

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mregist_phone=findViewById(R.id.regist_phone);
        mregist_userid=findViewById(R.id.regist_userid);
        mregist_password=findViewById(R.id.regist_password);
        mregist_passwordagain=findViewById(R.id.regist_passwordagain);
        mregist_commit=findViewById(R.id.regist_commit);
        mregist_back=findViewById(R.id.regist_back);

        mregist_back.setOnClickListener(v->{
            Intent intent =new Intent();
            intent.setClass(RegisterActivity.this,LoginActivity.class);
            finish();
        });
        mregist_commit.setOnClickListener(v->{
            User_Dao userDao = new User_Dao(RegisterActivity.this);
            if (userDao.findUser(String.valueOf(mregist_userid.getText()))==null){
                if (mregist_password.getText().toString().equals(mregist_passwordagain.getText().toString())){
                    if (mregist_phone.getText().toString().length()<12&&mregist_phone.getText().toString().length()>10){
                        User user = new User();
                        Log.i("chang", String.valueOf(mregist_phone.getText().toString().length()));
                        user.setUser_id(mregist_userid.getText().toString());
                        user.setUser_password(mregist_password.getText().toString());
                        user.setUser_tel(Long.parseLong(mregist_phone.getText().toString()));
                        if (userDao.register_user(user)>0) {
                            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                            Intent intent1=new Intent();
                            intent1.setClass(RegisterActivity.this, LoginActivity.class);
                            intent1.putExtra("user_id",user.getUser_id());
                            Log.i("user_id",user.getUser_id());
                            startActivity(intent1);
                            finish();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this,"电话号码不合法",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this,"密码不一致",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(RegisterActivity.this,"账号已经存在",Toast.LENGTH_LONG).show();
            }
        });
    }

}
