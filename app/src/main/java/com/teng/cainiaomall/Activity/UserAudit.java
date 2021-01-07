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

import com.teng.cainiaomall.Dao.User_Dao;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;

import java.util.ArrayList;

public class UserAudit extends AppCompatActivity {

    private ListView listView;
    ArrayList<User> list;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_audit);
        listView = findViewById(R.id.userauditlist);
        User_Dao user_dao = new User_Dao(UserAudit.this);
        list= user_dao.Useraudit();
        MyListAdapter myListAdapter=new MyListAdapter(UserAudit.this,list);//实例化适配器
        listView.setAdapter(myListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user_id= list.get(position).getUser_id();
                String type[]=new String[2];
                type[0]="审核通过";
                type[1]="审核不通过";
                AlertDialog.Builder builder=new AlertDialog.Builder(UserAudit.this);
                builder.setTitle("审核情况");
                builder.setItems(type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
//                            Log.i("用户id：", "onClick: "+user_id);
                            user_dao.user_auditsuccess(user_id);
                            Intent intent =new Intent();
                            intent.setClass(UserAudit.this,Main_Admin.class);
                            startActivity(intent);
                            finish();

                        }
                        else if(which==1){
                            user_dao.user_auditfailure(user_id);
                            Intent intent =new Intent();
                            intent.setClass(UserAudit.this,Main_Admin.class);
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
        ArrayList<User> list;
        public MyListAdapter(Context context, ArrayList<User>temlist){
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
                convertView=layoutInflater.inflate(R.layout.list_item2,parent,false);
                viewHolder.tupian=convertView.findViewById(R.id.u_picture);
                viewHolder.name= convertView.findViewById(R.id.u_name);
                viewHolder.tel= convertView.findViewById(R.id.u_tel);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            User user=list.get(position);
            viewHolder.tel.setText(user.getUser_id()+"");
            Bitmap bitmap= BitmapFactory.decodeFile(getFilesDir()+"/"+user.getUser_avatar());
            viewHolder.tupian.setImageBitmap(bitmap);
            viewHolder.name.setText(user.getUser_name());
            return convertView;
        }
    }

    class ViewHolder{
        public ImageView tupian;
        public TextView tel;
        public TextView name;
    }

}