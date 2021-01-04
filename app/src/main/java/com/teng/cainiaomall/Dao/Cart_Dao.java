package com.teng.cainiaomall.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teng.cainiaomall.DB.DBOpenHelp;
import com.teng.cainiaomall.Model.Cart;
import com.teng.cainiaomall.Model.Good;

import java.util.ArrayList;

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
        contentValues.put("cart_money", good.getGood_price());
        contentValues.put("cart_good_user_id",good.getGood_user_id());
        db.insert("cm_cart", null, contentValues);
    }

    /*
    *
    * 顾客购物车查询
    * 参数：user_id
    * */
    public ArrayList<Cart> findCart(String user_id ){
        ArrayList<Cart> carts = new ArrayList<>();
        db = DBOpenHelper.getReadableDatabase();//初始化SQLiteDatabase
        final Cursor cursor = db.rawQuery("select * from cm_cart where cart_user_id='" + user_id + "'", null);
        while (cursor.moveToNext()){
            Cart cart=new Cart();
            cart.setCart_good_id(cursor.getLong(cursor.getColumnIndex("cart_good_id")));
            cart.setCart_good_user_id(cursor.getString(cursor.getColumnIndex("cart_good_user_id")));
            cart.setCart_money(cursor.getDouble(cursor.getColumnIndex("cart_price")));
            cart.setCart_user_id(cursor.getString(cursor.getColumnIndex("cart_status")));
            carts.add(cart);
        }

        return carts;
    }
}
