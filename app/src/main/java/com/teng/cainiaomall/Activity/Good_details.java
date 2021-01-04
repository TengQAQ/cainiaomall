package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.teng.cainiaomall.Dao.Cart_Dao;
import com.teng.cainiaomall.Dao.Good_Dao;
import com.teng.cainiaomall.Model.Cart;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.R;

public class Good_details extends AppCompatActivity {
    private SharedPreferences sp= null;//保存登录后的用户名
    private SharedPreferences.Editor editor=null;
    private Good good;
    private TextView addcard;
    private boolean isremenberpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);

        addcard = findViewById(R.id.button_addcart);

        sp=getSharedPreferences("user_rememberpass", Context.MODE_PRIVATE);
        editor=sp.edit();
        isremenberpassword=sp.getBoolean("bt_isremember",false);

        addcard.setOnClickListener(v -> {
            String user_id=sp.getString("username","");
            Cart_Dao cart_dao =new Cart_Dao(Good_details.this);
//            String type[]=new String[2];
//            type[0]="";
//            type[1]="审核不通过";
//            AlertDialog.Builder builder=new AlertDialog.Builder(Good_details.this);
//            builder.setTitle("审核情况");
//            builder.setItems(type, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if(which==0){
//                    }
//                    else if(which==1){
//
//                    }
//                }
//            });
//            builder.show();

            cart_dao.addCart(good,user_id);
            Intent intent1 = new Intent();
            intent1.setClass(Good_details.this,MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            finish();
        });

        Intent intent = getIntent();
        int good_id = intent.getIntExtra("good_id",1);
        Log.i("good_id", ": "+good_id);
        Good_Dao good_dao =new Good_Dao(Good_details.this);
        good =good_dao.findgood(good_id);


    }
}