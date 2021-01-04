package com.teng.cainiaomall.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelp extends SQLiteOpenHelper {
    final static String DBNAME="cainiaomall.db";
    final static int version=6;
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
        db.execSQL("CREATE TABLE 'cm_good'(good_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "good_name varchar(30)," +
                "good_picpath varchar(200)," +
                "good_describe varchar(400)," +
                "good_price double(10)," +
                "good_ztype varchar(10)," +
                "good_time varchar(30)," +
                "good_user_id varchar(10)," +//商家id
                "good_note varchar(500)," +//备注
                "good_status varchar(2)," +//审核状态
                "good_sx varchar(2) not null);");//属性0:实体 1;虚拟
        db.execSQL("CREATE TABLE 'cm_admin'(admin_id varchar(10) PRIMARY KEY," +
                "admin_password varchar(20) not null," +
                "admin_tel int(11));");
        db.execSQL("insert into 'cm_admin' values('123','123','17623876031')");

        db.execSQL("CREATE TABLE 'cm_cart'(cart_id varchar(10) PRIMARY KEY AUTOINCREMENT," +
                "cart_user_id varchar(10) not null," +
                "cart_good_id int(11)," +
                "cart_good_user_id varchar(10)," +
                "cart_money double(10));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cm_user");
        db.execSQL("drop table if exists cm_good");
        onCreate(db);
    }
}
