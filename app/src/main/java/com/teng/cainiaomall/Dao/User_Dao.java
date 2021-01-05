package com.teng.cainiaomall.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.teng.cainiaomall.DB.DBOpenHelp;
import com.teng.cainiaomall.Model.User;

import java.util.ArrayList;

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
    public void change_avatar(String id, String avatar){
        db=DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_user set user_avatar='"+avatar+"'where user_id='"+id+"'",null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
    }
    /*
    * 注册账号
    * user_id
    * */
    public long register_user(User user) {
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        long insertfalg;
        insertfalg = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user.getUser_id());
        contentValues.put("user_password", user.getUser_password());
        contentValues.put("user_tel", user.getUser_tel());
        contentValues.put("user_name", "小菜鸟");
        contentValues.put("user_money", 100);
        contentValues.put("user_status", 0);
//        contentValues.put("user_avatar", "null");
        insertfalg = db.insert("cm_user", null, contentValues);
        return insertfalg;
    }
    /*
    * 更新个人资料
    *
    * */
    public int update_user(User user){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_user set user_tel='"+user.getUser_tel()+"',user_password='"+user.getUser_password()+"',user_name='"+user.getUser_name()+"'where user_id='"+user.getUser_id()+"'",null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return 0;
        }

        cursor.close();
        db.close();
        return 1;
    }

    public ArrayList<User> Useraudit(){
        ArrayList<User> resultlist = new ArrayList<>();
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        final Cursor cursor = db.rawQuery("select * from cm_user where user_status='" + 0 + "'", null);
        while (cursor.moveToNext()){
            User user=new User();
            user.setUser_id(cursor.getString(cursor.getColumnIndex("user_id")));
            user.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
            user.setUser_avatar(cursor.getString(cursor.getColumnIndex("user_avatar")));
            user.setUser_tel(cursor.getLong(cursor.getColumnIndex("user_tel")));
            resultlist.add(user);
        }
        cursor.close();
        db.close();
        return resultlist;
    }

    /*
     * 审核通过
     *
     * */
    public boolean user_auditsuccess(String user_id){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_user set user_status = 1 where user_id='"+user_id+"'",null);
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
     * 审核不通过
     *
     * */
    public int user_auditfailure(String user_id){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_user set user_status= 2 where user_id='"+user_id+"'",null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
        return 1;
    }

    /*
    *用户充值
    *参数：user_id user_money
    *
    * */
    public void chargeuser(String user_id,Double user_money){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_user set user_money= '"+user_money+"' where user_id='"+user_id+"'",null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
    }

    /*
    * 购买支付
    *
    * */
    public void payfor(String user_id,Double user_money){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_user set user_money= '"+user_money+"' where user_id='"+user_id+"'",null);
    }

}
