package com.teng.cainiaomall.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
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
import android.widget.Toast;

import com.teng.cainiaomall.Activity.CommodityAudit;
import com.teng.cainiaomall.Activity.Good_details;
import com.teng.cainiaomall.Dao.Good_Dao;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.Model.User;
import com.teng.cainiaomall.R;
import com.teng.cainiaomall.tool.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    private ListView listView;
    ArrayList<Good> goodlist;
    Banner banner;//banner组件
    List mlist;//图片资源
    List<String> mlist1;//轮播标题
    private Toolbar toolbar;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        toolbar=view.findViewById(R.id.toolbar);

        mlist = new ArrayList<>();
        mlist.add(R.mipmap.cainiaologo);
        mlist.add(R.mipmap.lunbo2);
        mlist.add(R.mipmap.lunbo3);
        mlist1 = new ArrayList<>();
        mlist1.add("特色快速业务");
        mlist1.add("送货到家服务");
        mlist1.add("一键查询服务");
        banner = view.findViewById(R.id.main_banner);
        banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
        banner.setImages(mlist);//设置图片源
        banner.setBannerTitles(mlist1);//设置标题源
        banner.setDelayTime(2000);//设置轮播事件，单位毫秒
        banner.setBannerAnimation(Transformer.Stack);
        banner.setOnBannerListener(new OnBannerListener() {
            public void OnBannerClick(int position) {
                Toast.makeText(getActivity(), "点击了轮播第"+position+"个图片" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
        banner.start();//开始轮播，一定要调用此方法。

        listView = view.findViewById(R.id.selllist);
        Good_Dao good_dao = new Good_Dao(getActivity());
        goodlist= good_dao.sellgoodlist();
        MyListAdapter myListAdapter=new MyListAdapter(getActivity(),goodlist);//实例化适配器
        listView.setAdapter(myListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int good_id = goodlist.get(position).getGood_id();
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
            Bitmap bitmap= BitmapFactory.decodeFile(getActivity().getFilesDir()+"/"+good.getGood_picpath());
            viewHolder.tupian.setImageBitmap(bitmap);
            viewHolder.name.setText(good.getGood_name());
            viewHolder.describe.setText(good.getGood_describe()+"");
            return convertView;
        }
    }

    private class ViewHolder{
        public ImageView tupian;
        public TextView money;
        public TextView name;
        public TextView describe;
    }

}