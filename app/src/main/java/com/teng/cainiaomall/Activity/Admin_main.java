package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.teng.cainiaomall.R;

public class Admin_main extends AppCompatActivity {
    private LinearLayout useraudit,commodityaudit,manageuser,managegoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        useraudit =findViewById(R.id.useraudit);
        commodityaudit = findViewById(R.id.commodityaudit);

        useraudit.setOnClickListener(v -> {
            Intent intent =new Intent();
            intent.setClass(Admin_main.this,UserAudit.class);
            startActivity(intent);
        });
        commodityaudit.setOnClickListener(v -> {
            Intent intent2 =new Intent();
            intent2.setClass(Admin_main.this,CommodityAudit.class);
            startActivity(intent2);
        });
    }
}