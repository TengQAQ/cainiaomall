package com.teng.cainiaomall.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teng.cainiaomall.DB.DBOpenHelp;
import com.teng.cainiaomall.Model.Admin;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.Model.User;

import java.util.ArrayList;

public class Admin_Dao {
    private DBOpenHelp DBOpenHelper;
    private SQLiteDatabase db;
    public Admin_Dao(Context context){
        DBOpenHelper=new DBOpenHelp(context);
    }
    /*
     * //添加商品
     *
     * */
    public Admin adminLogin(String username){
        db=DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        final Cursor cursor = db.rawQuery("select * from cm_admin where admin_id='" + username + "'", null);
        if (cursor.moveToNext()){
            Admin admin=new Admin();
            admin.setAdmin_id(cursor.getString(cursor.getColumnIndex("admin_id")));
            admin.setAdmin_password(cursor.getString(cursor.getColumnIndex("admin_password")));
            admin.setAdmin_tel(cursor.getString(cursor.getColumnIndex("admin_tel")));
            cursor.close();
            db.close();
            return admin;
        }
        cursor.close();
        db.close();
        return null;
    }

}
