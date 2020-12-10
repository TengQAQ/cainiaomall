package com.teng.cainiaomall.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelp extends SQLiteOpenHelper {
    final static String DBNAME="cainiaomall.db";
    final static int version=3;
    public DBOpenHelp(@Nullable Context context) {
        super(context, DBNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户数据
        db.execSQL("CREATE TABLE 'cm_user'(user_id varchar(10) PRIMARY KEY," +
                "user_password varchar(20) not null," +
                "user_name varchar(20) not null," +
                "user_status int not null," +
                "user_avatar varchar(200)," +
                "user_money double(20,0)," +
                "user_tel int(11));");

        db.execSQL("insert into 'cm_user' values ('001','123456','秦腾辉',0,null,1.0,17623876031)");
        db.execSQL("insert into 'cm_user' values ('002','123456','刘立杰',1,null,1.0,17623876031)");
        db.execSQL("insert into 'cm_user' values ('003','123456','李文平',2,null,1.0,17623876031)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cm_user");
        onCreate(db);
    }
}
