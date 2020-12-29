package com.teng.cainiaomall.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teng.cainiaomall.R;
import com.teng.cainiaomall.tool.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private String mParam1;
    private String mParam2;
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
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        view.findViewById(R.id.sendExpress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), SentExpActivity.class));
            }
        });
        view.findViewById(R.id.pickExpress);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), PickExpActivity.class));
            }
        });
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
        return view;
    }
}