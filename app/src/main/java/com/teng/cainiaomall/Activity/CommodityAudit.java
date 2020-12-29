package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.teng.cainiaomall.Dao.Good_Dao;
import com.teng.cainiaomall.Model.Admin;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.R;

import java.util.ArrayList;

public class CommodityAudit extends AppCompatActivity {

    private ListView listView;
    ArrayList<Good> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_audit);
        listView=findViewById(R.id.commodityauditlist);
        Good_Dao good_dao = new Good_Dao(CommodityAudit.this);
        list=good_dao.goodaudit();
        MyListAdapter myListAdapter=new MyListAdapter(CommodityAudit.this,list);//实例化适配器
        listView.setAdapter(myListAdapter);
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        ArrayList<Good> list;
        public MyListAdapter(Context context, ArrayList<Good>temlist){
            layoutInflater=LayoutInflater.from(context);
            list=temlist;
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @SuppressLint("WrongViewCast")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView==null) {

                convertView=layoutInflater.inflate(R.layout.list_item1,parent,false);
                viewHolder.tupian=(ImageView)convertView.findViewById(R.id.g_picture);
                viewHolder.name=(TextView) convertView.findViewById(R.id.g_name);
                viewHolder.describe=(TextView) convertView.findViewById(R.id.g_describe);
                viewHolder.money= convertView.findViewById(R.id.g_money);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            Good good=list.get(position);
            viewHolder.money.setText(good.getGood_price()+"");
            Bitmap bitmap= BitmapFactory.decodeFile( good.getGood_picpath());
            viewHolder.tupian.setImageBitmap(bitmap);
            viewHolder.name.setText(good.getGood_name());
            viewHolder.describe.setText(good.getGood_describe()+"");
            return convertView;
        }
    }

    class ViewHolder{
        public ImageView tupian;
        public TextView money;
        public TextView name;
        public TextView describe;
    }
}