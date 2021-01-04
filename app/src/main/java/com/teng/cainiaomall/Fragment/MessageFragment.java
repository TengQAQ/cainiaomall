package com.teng.cainiaomall.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.teng.cainiaomall.Activity.Good_details;
import com.teng.cainiaomall.Dao.Cart_Dao;
import com.teng.cainiaomall.Dao.Good_Dao;
import com.teng.cainiaomall.Model.Cart;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MessageFragment extends Fragment {
    private ListView listView;
    ArrayList<Cart> cartArrayList;
    private SharedPreferences sp= null;//保存登录后的用户名
    private SharedPreferences.Editor editor=null;
    private String user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        sp=getActivity().getSharedPreferences("user_rememberpass", Context.MODE_PRIVATE);
        user_id=sp.getString("username","");

        listView = view.findViewById(R.id.cartlist);
        Cart_Dao cart_dao= new Cart_Dao(getActivity());
        cartArrayList= cart_dao.findCart(user_id);
        MyListAdapter myListAdapter=new MyListAdapter(getActivity(),cartArrayList);//实例化适配器
        listView.setAdapter(myListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long good_id = cartArrayList.get(position).getCart_good_id();
                Log.i(TAG, ":"+good_id);
                Intent intent = new Intent();
                intent.putExtra("good_id",good_id);
                intent.setClass(getActivity(), Good_details.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private class MyListAdapter extends BaseAdapter {
        LayoutInflater layoutInflater;
        ArrayList<Cart> list;
        public MyListAdapter(Context context, ArrayList<Cart>temlist){
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

                convertView=layoutInflater.inflate(R.layout.list_item3,parent,false);
                viewHolder.tupian=(ImageView)convertView.findViewById(R.id.c_picture);
                viewHolder.name=(TextView) convertView.findViewById(R.id.c_name);
                viewHolder.money= convertView.findViewById(R.id.c_money);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            Cart cart=list.get(position);
            viewHolder.money.setText(cart.getCart_money()+"");
            Bitmap bitmap= BitmapFactory.decodeFile(getActivity().getFilesDir()+"/"+cart.getCart_good_picpath());
            viewHolder.tupian.setImageBitmap(bitmap);
            viewHolder.name.setText(cart.getCart_good_name());
            return convertView;
        }
    }

    private class ViewHolder{
        public ImageView tupian;
        public TextView money;
        public TextView name;
    }

}