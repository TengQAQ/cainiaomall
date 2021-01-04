package com.teng.cainiaomall.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teng.cainiaomall.DB.DBOpenHelp;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.Model.User;

import java.util.ArrayList;

public class Good_Dao {
    private DBOpenHelp DBOpenHelper;
    private SQLiteDatabase db;
    public Good_Dao(Context context){
        DBOpenHelper=new DBOpenHelp(context);
    }
    /*
    * //添加商品
    *
    * */
    public long addgood(Good good){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        long insertfalg;
        insertfalg = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put("good_name", good.getGood_name());
        contentValues.put("good_describe", good.getGood_describe());
        contentValues.put("good_price", good.getGood_price());
        contentValues.put("good_time", good.getGood_time());
        contentValues.put("good_ztype", good.getGood_ztype());
        contentValues.put("good_user_id",good.getGood_user_id());
        contentValues.put("good_status", 0);
        contentValues.put("good_picpath",good.getGood_picpath());
        contentValues.put("good_sx", good.getGood_sx());
        insertfalg = db.insert("cm_good", null, contentValues);
        return insertfalg;
    }
    /*
    * 未审核商品
    *
    * */
    public ArrayList<Good> goodaudit(){
        ArrayList<Good> resultlist=new ArrayList<>();
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        final Cursor cursor = db.rawQuery("select * from cm_good where good_status='" + 0 + "'", null);
        while (cursor.moveToNext()){
            Good good=new Good();
            good.setGood_id(cursor.getInt(cursor.getColumnIndex("good_id")));
            good.setGood_user_id(cursor.getString(cursor.getColumnIndex("good_user_id")));
            good.setGood_ztype(cursor.getString(cursor.getColumnIndex("good_ztype")));
            good.setGood_describe(cursor.getString(cursor.getColumnIndex("good_describe")));
            good.setGood_name(cursor.getString(cursor.getColumnIndex("good_name")));
            good.setGood_price(cursor.getDouble(cursor.getColumnIndex("good_price")));
            good.setGood_status(cursor.getString(cursor.getColumnIndex("good_status")));
            good.setGood_picpath(cursor.getString(cursor.getColumnIndex("good_picpath")));
            good.setGood_time(cursor.getString(cursor.getColumnIndex("good_time")));
            good.setGood_sx(cursor.getString(cursor.getColumnIndex("good_sx")));
            resultlist.add(good);
        }
        cursor.close();
        db.close();
        return resultlist;
    }

    /*
     * 未审核商品
     *
     * */
    public ArrayList<Good> sellgoodlist(){
        ArrayList<Good> resultlist=new ArrayList<>();
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        final Cursor cursor = db.rawQuery("select * from cm_good where good_status='" + 1 + "'", null);
        while (cursor.moveToNext()){
            Good good=new Good();
            good.setGood_id(cursor.getInt(cursor.getColumnIndex("good_id")));
            good.setGood_user_id(cursor.getString(cursor.getColumnIndex("good_user_id")));
            good.setGood_ztype(cursor.getString(cursor.getColumnIndex("good_ztype")));
            good.setGood_describe(cursor.getString(cursor.getColumnIndex("good_describe")));
            good.setGood_name(cursor.getString(cursor.getColumnIndex("good_name")));
            good.setGood_price(cursor.getDouble(cursor.getColumnIndex("good_price")));
            good.setGood_status(cursor.getString(cursor.getColumnIndex("good_status")));
            good.setGood_picpath(cursor.getString(cursor.getColumnIndex("good_picpath")));
            good.setGood_time(cursor.getString(cursor.getColumnIndex("good_time")));
            good.setGood_sx(cursor.getString(cursor.getColumnIndex("good_sx")));
            resultlist.add(good);
        }
        cursor.close();
        db.close();
        return resultlist;
    }

    /*
     * 审核商品
     *
     * */
    public void through_goods(String good_id){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor = db.rawQuery("update cm_good set good_status = 1 where good_id='"+ good_id+"'",null);
    }

    /*
     * 审核通过
     *
     * */
    public boolean good_auditsuccess(String good_id){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_good set good_status = 1 where good_id='"+good_id+"'",null);
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
    public int good_auditfailure(String good_id){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        Cursor cursor=db.rawQuery("update cm_good set good_status= 2 where good_id='"+good_id+"'",null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
        }
        cursor.close();
        db.close();
        return 1;
    }

    /**
     *
     * 搜索某一商品详情
     * 传值：good_id
     */
    public Good findgood(int good_id) {
        db = DBOpenHelper.getReadableDatabase();
        final Cursor cursor = db.rawQuery("select * from cm_good where good_id ='" + good_id + "'", null);
        if (cursor.moveToNext()){
            Good good=new Good();
            good.setGood_id(cursor.getInt(cursor.getColumnIndex("good_id")));
            good.setGood_user_id(cursor.getString(cursor.getColumnIndex("good_user_id")));
            good.setGood_ztype(cursor.getString(cursor.getColumnIndex("good_ztype")));
            good.setGood_describe(cursor.getString(cursor.getColumnIndex("good_describe")));
            good.setGood_name(cursor.getString(cursor.getColumnIndex("good_name")));
            good.setGood_price(cursor.getDouble(cursor.getColumnIndex("good_price")));
            good.setGood_status(cursor.getString(cursor.getColumnIndex("good_status")));
            good.setGood_picpath(cursor.getString(cursor.getColumnIndex("good_picpath")));
            good.setGood_time(cursor.getString(cursor.getColumnIndex("good_time")));
            good.setGood_sx(cursor.getString(cursor.getColumnIndex("good_sx")));
            return good;
        }
        cursor.close();
        db.close();
        return null;
    }

}
