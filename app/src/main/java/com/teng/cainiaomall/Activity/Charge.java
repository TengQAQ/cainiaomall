package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

public class Charge extends AppCompatActivity {
    private EditText muser_id,muser_money;
    private TextView mbutton_sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        muser_id=findViewById(R.id.charg_user_id);
        muser_money=findViewById(R.id.charg_user_money);
        mbutton_sure=findViewById(R.id.button_sure);

        mbutton_sure.setOnClickListener(v -> {
            String user_id=muser_id.getText().toString();
            String user_money=muser_money.getText().toString();
            Log.i("数据", ":"+user_id+user_money);
            User_Dao userDao = new User_Dao(Charge.this);
            User user =new User();
            user=userDao.findUser(user_id);
            Double user_money1= user.getUser_money() + Double.parseDouble(user_money);
            userDao.chargeuser(user_id,user_money1);
            Intent intent1 =new Intent();
            intent1.setClass(Charge.this,Main_Admin.class);
            startActivity(intent1);
            finish();
        });
    }
}