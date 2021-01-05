package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.teng.cainiaomall.R;

public class Main_Admin extends AppCompatActivity {
    private LinearLayout useraudit,commodityaudit,mchargemonry,managegoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        useraudit =findViewById(R.id.useraudit);
        commodityaudit = findViewById(R.id.commodityaudit);
        mchargemonry = findViewById(R.id.chargemonry);

        useraudit.setOnClickListener(v -> {
            Intent intent =new Intent();
            intent.setClass(Main_Admin.this,UserAudit.class);
            startActivity(intent);
        });
        commodityaudit.setOnClickListener(v -> {
            Intent intent2 =new Intent();
            intent2.setClass(Main_Admin.this,CommodityAudit.class);
            startActivity(intent2);
        });
        mchargemonry.setOnClickListener(v -> {
            Intent intent3 =new Intent();
            intent3.setClass(Main_Admin.this,Charge.class);
            startActivity(intent3);
        });
    }
}