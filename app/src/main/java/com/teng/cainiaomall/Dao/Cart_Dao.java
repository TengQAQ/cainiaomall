package com.teng.cainiaomall.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.teng.cainiaomall.DB.DBOpenHelp;
import com.teng.cainiaomall.Model.Cart;
import com.teng.cainiaomall.Model.Good;

public class Cart_Dao {
    private DBOpenHelp DBOpenHelper;
    private SQLiteDatabase db;
    public Cart_Dao(Context context){
        DBOpenHelper=new DBOpenHelp(context);
    }

    /*
    *
    * 加入购物车
    * 参数：Cart
    * */
    public void addCart(Good good,String user_id){
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        ContentValues contentValues = new ContentValues();
        contentValues.put("cart_user_id", user_id);
        contentValues.put("cart_good_id", good.getGood_id());
        contentValues.put("cart_price", good.getGood_price());
        contentValues.put("cart_good_user_id",good.getGood_user_id());
        db.insert("cm_cart", null, contentValues);
    }
}
