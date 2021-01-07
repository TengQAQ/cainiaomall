package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.Cart_Dao;
import com.teng.cainiaomall.Dao.Good_Dao;
import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.Cart;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

public class Good_details extends AppCompatActivity {
    private SharedPreferences sp= null;//保存登录后的用户名
    private SharedPreferences.Editor editor=null;
    private Good good;
    private ImageView shangtupian;
    private TextView addcard,regist_back,shangpinleixing,shangpinshuxing,shangpinjiage,shangpinmingch,shangpinmiaoshu;
    private boolean isremenberpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);

        shangpinmiaoshu = findViewById(R.id.shangpinmiaoshu);
        shangpinjiage = findViewById(R.id.shangpinjiage);
        shangpinmingch = findViewById(R.id.shangpinmingch);
        shangpinshuxing = findViewById(R.id.shangpinshuxing);
        shangpinleixing = findViewById(R.id.shangpinleixing);
        shangtupian = findViewById(R.id.shangpintupian);

        addcard = findViewById(R.id.button_addcart);
        regist_back = findViewById(R.id.regist_back);

        sp = getSharedPreferences("user_rememberpass", Context.MODE_PRIVATE);
        editor = sp.edit();
        isremenberpassword = sp.getBoolean("bt_isremember", false);
        String user_id1 =sp.getString("username","");


        Intent intent = getIntent();
        int good_id = intent.getIntExtra("good_id", 1);
        Log.i("good_id", ": " + good_id);
        Good_Dao good_dao = new Good_Dao(Good_details.this);
        good = good_dao.findgood(good_id);
        if (good == null) {
            Cart_Dao cart_dao =new Cart_Dao(getApplicationContext());
            cart_dao.clearoneCart(user_id1,good_id);
            Intent intent1 = new Intent();
            intent1.setClass(Good_details.this, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            Toast.makeText(Good_details.this, "抱歉商品已被购买了哦", Toast.LENGTH_LONG).show();
//            finish();
        } else {
            shangpinjiage.setText(good.getGood_price() + "");
            shangpinleixing.setText(good.getGood_ztype());
            shangpinmiaoshu.setText(good.getGood_describe());
            shangpinmingch.setText(good.getGood_name());
            shangpinshuxing.setText(good.getGood_sx());
            Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/" + good.getGood_picpath());

            shangtupian.setImageBitmap(bitmap);

            regist_back.setOnClickListener(v -> {
                String user_id = sp.getString("username", "");
                Cart_Dao cart_dao = new Cart_Dao(getApplicationContext());
                //            cart_dao.findCart(good_)
                User_Dao user_dao = new User_Dao(getApplicationContext());
                User user = user_dao.findUser(user_id);
                Double result = user.getUser_money() - good.getGood_price();
                Log.i("123", String.valueOf(result));
                if (result > 0) {
                    user_dao.chargeuser(user_id, result);
                    good_dao.cleangood(good_id);
                    Intent intent2 = new Intent();
                    intent2.setClass(Good_details.this, MainActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    finish();
                } else {
                    Toast.makeText(Good_details.this,"余额不足！",Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            addcard.setOnClickListener(v -> {
                String user_id = sp.getString("username", "");
                Cart_Dao cart_dao = new Cart_Dao(Good_details.this);
                cart_dao.addCart(good, user_id);
                Intent intent1 = new Intent();
                intent1.setClass(Good_details.this, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            });
        }
    }
}