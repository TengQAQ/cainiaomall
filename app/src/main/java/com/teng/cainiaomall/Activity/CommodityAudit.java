package com.teng.cainiaomall.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Dao.Good_Dao;
import com.teng.cainiaomall.Dao.User_Dao;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String good_id= String.valueOf(list.get(position).getGood_id());
                String type[]=new String[2];
                type[0]="审核通过";
                type[1]="审核不通过";
                AlertDialog.Builder builder=new AlertDialog.Builder(CommodityAudit.this);
                builder.setTitle("审核情况");
                builder.setItems(type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
//                            Log.i("用户id：", "onClick: "+user_id);
                            good_dao.good_auditsuccess(good_id);
                            Intent intent =new Intent();
                            intent.setClass(CommodityAudit.this,Main_Admin.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(which==1){
                            good_dao.good_auditfailure(good_id);
                            Intent intent =new Intent();
                            intent.setClass(CommodityAudit.this,Main_Admin.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                });
                builder.show();
            }
        });
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
            Bitmap bitmap= BitmapFactory.decodeFile(getFilesDir()+"/"+good.getGood_picpath());
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