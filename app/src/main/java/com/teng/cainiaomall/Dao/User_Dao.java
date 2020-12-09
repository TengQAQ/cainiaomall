package com.teng.cainiaomall.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teng.cainiaomall.DB.DBOpenHelp;

public class User_Dao {
    private DBOpenHelp DBOpenHelper;
    private SQLiteDatabase db;
    public User_Dao(Context context){
        DBOpenHelper=new DBOpenHelp(context);
    }

    public Boolean FindUserById(String username,String password){
        db=DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("select * from cm_user where user_id= '"+ username +"' and user_password= '"+ password +"';",null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }
    /*

        更改头像
     */
    public void Addtheavatar(String id,String avatar){
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
