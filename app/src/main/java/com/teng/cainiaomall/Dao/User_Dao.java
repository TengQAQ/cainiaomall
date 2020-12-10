package com.teng.cainiaomall.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teng.cainiaomall.DB.DBOpenHelp;
import com.teng.cainiaomall.Model.User;

public class User_Dao {
    private DBOpenHelp DBOpenHelper;
    private SQLiteDatabase db;
    public User_Dao(Context context){
        DBOpenHelper=new DBOpenHelp(context);
    }
    /*
    * 账号匹配
    * */
    public User findUser(String user_id){
        db=DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        final Cursor cursor = db.rawQuery("select * from cm_user where user_id='" + user_id + "'", null);
        if (cursor.moveToNext()){
            User user=new User();
            user.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
            user.setUser_password(cursor.getString(cursor.getColumnIndex("user_password")));
            user.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
            user.setUser_tel(cursor.getLong(cursor.getColumnIndex("user_tel")));
            user.setUser_money(cursor.getDouble(cursor.getColumnIndex("user_money")));
            user.setUser_statue(cursor.getInt(cursor.getColumnIndex("user_status")));
            user.setUser_avatar(cursor.getString(cursor.getColumnIndex("user_avatar")));
            cursor.close();
            db.close();
            return user;
        }
        cursor.close();
        db.close();
        return null;
    }

    /*
    * 更改账号头像
    * */
    public void change_avatar(String id,String avatar){
        db=DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update tb_user set user_picPath='"+avatar+"'where user_id='"+id+"'",null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
    }
}
