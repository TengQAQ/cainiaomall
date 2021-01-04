package com.teng.cainiaomall.Model;

import android.content.Intent;

public class Cart {
    private String cart_user_id;
    private Intent cart_good_id;
    private String cart_good_user_id;
    private Double cart_money;

    public String getCart_user_id() {
        return cart_user_id;
    }

    public void setCart_user_id(String cart_user_id) {
        this.cart_user_id = cart_user_id;
    }

    public Intent getCart_good_id() {
        return cart_good_id;
    }

    public void setCart_good_id(Intent cart_good_id) {
        this.cart_good_id = cart_good_id;
    }

    public String getCart_good_user_id() {
        return cart_good_user_id;
    }

    public void setCart_good_user_id(String cart_good_user_id) {
        this.cart_good_user_id = cart_good_user_id;
    }

    public Double getCart_money() {
        return cart_money;
    }

    public void setCart_money(Double cart_money) {
        this.cart_money = cart_money;
    }
}
