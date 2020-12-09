package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import com.teng.cainiaomall.R;

public class RetrievePasswordActivity extends AppCompatActivity {
    private ImageButton mbutton_back;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

        mbutton_back=findViewById(R.id.button_back);

        mbutton_back.setOnClickListener( v -> {
//            Intent intent= new Intent();
//            intent.setClass(RetrievePasswordActivity.this,LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//不刷新目标Activity
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关闭其他Activity
//            startActivity(intent);
            finish();
        });

    }
}