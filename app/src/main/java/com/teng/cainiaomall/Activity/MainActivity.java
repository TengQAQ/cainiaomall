package com.teng.cainiaomall.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teng.cainiaomall.Fragment.BuyFragment;
import com.teng.cainiaomall.Fragment.HomeFragment;
import com.teng.cainiaomall.Fragment.MineFragment;
import com.teng.cainiaomall.Fragment.SellFragment;
import com.teng.cainiaomall.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView mtv_home,mtv_buy,mtv_sell,mtv_message,mtv_mine;
    Fragment mFragment_Home,mFragment_Buy,mFragment_Sell,mFragment_Messagge,mFragment_Mine;
    //初始第一个Fragment
    private int mFragmentId=0;
    //标记Fragment
    public static final int Fragment_Home =0;
    public static final int Fragment_Buy =1;
    public static final int Fragment_Sell =2;
    public static final int Fragment_Message =3;
    public static final int Fragment_Mine =4;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtv_home=findViewById(R.id.tv_home);
        mtv_buy=findViewById(R.id.tv_buy);
        mtv_sell=findViewById(R.id.tv_sell);
        mtv_message=findViewById(R.id.tv_message);
        mtv_mine=findViewById(R.id.tv_mine);

        //根据传入的Bundle对象判断Activity是正常启动还是销毁重建
        if(savedInstanceState == null){
            //设置第一个Fragment默认选中
            setFragment(mFragmentId);
        }

        mtv_home.setOnClickListener(this);
        mtv_buy.setOnClickListener(this);
        mtv_sell.setOnClickListener(this);
        mtv_message.setOnClickListener(this);
        mtv_mine.setOnClickListener(this);
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //通过FragmentManager获取保存在FragmentTransaction中的Fragment实例
        mFragment_Home = mFragmentManager.findFragmentByTag("HomeFragment");
        mFragment_Buy = mFragmentManager.findFragmentByTag("BuyFragment");
        mFragment_Sell = mFragmentManager.findFragmentByTag("SellFragment");
        mFragment_Messagge = mFragmentManager.findFragmentByTag("MessageFragment");
        mFragment_Mine = mFragmentManager.findFragmentByTag("MineFragment");
        //恢复销毁前显示的Fragment
        setFragment(savedInstanceState.getInt("Fragment_id"));
    }

    private void setFragment(int Fragment_Id) {
        //获取Fragment管理器
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        //隐藏所有Fragment
        hideFragments(mTransaction);
        switch (Fragment_Id){
            default:
                break;
            case Fragment_Home:
                mFragmentId = Fragment_Home;
                //设置菜单栏为选中状态（修改文字和图片颜色）
                mtv_home.setTextColor(getResources().getColor(R.color.colorTextPressed));
                mtv_home.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.homepage,0,0);
                //显示对应Fragment
                if(mFragment_Home == null){
                    mFragment_Home = new HomeFragment();
                    mTransaction.add(R.id.container, mFragment_Home, "HomeFragment");
                }else {
                    mTransaction.show(mFragment_Home);
                }
                break;
            case Fragment_Buy:
                mFragmentId = Fragment_Buy;
                mtv_buy.setTextColor(getResources().getColor(R.color.colorTextPressed));
                mtv_buy.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.buy_1,0,0);
                if(mFragment_Buy == null){
                    mFragment_Buy = new BuyFragment();
                    mTransaction.add(R.id.container, mFragment_Buy, "BuyFragment");
                }else {
                    mTransaction.show(mFragment_Buy);
                }
                break;
            case Fragment_Sell:
                mFragmentId = Fragment_Sell;
                mtv_sell.setTextColor(getResources().getColor(R.color.colorTextPressed));
                mtv_sell.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.sell_1,0,0);
                if(mFragment_Sell == null){
                    mFragment_Sell = new SellFragment();
                    mTransaction.add(R.id.container, mFragment_Sell, "SellFragment");
                }else {
                    mTransaction.show(mFragment_Sell);
                }
                break;
            case Fragment_Message:
                mFragmentId = Fragment_Message;
                mtv_message.setTextColor(getResources().getColor(R.color.colorTextPressed));
                mtv_message.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.message_1,0,0);
                if(mFragment_Messagge == null){
                    mFragment_Messagge = new SellFragment();
                    mTransaction.add(R.id.container, mFragment_Messagge, "MessageFragment");
                }else {
                    mTransaction.show(mFragment_Messagge);
                }
                break;
            case Fragment_Mine:
                mFragmentId = Fragment_Mine;
                mtv_mine.setTextColor(getResources().getColor(R.color.colorTextPressed));
                mtv_mine.setCompoundDrawablesWithIntrinsicBounds(0,
                        R.mipmap.usersetting_1,0,0);
                if(mFragment_Mine == null){
                    mFragment_Mine = new MineFragment();
                    mTransaction.add(R.id.container, mFragment_Mine, "MineFragment");
                }else {
                    mTransaction.show(mFragment_Mine);
                }
                break;

        }
        //提交事务
        mTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if(mFragment_Home != null){
            //隐藏Fragment
            transaction.hide(mFragment_Home);
            //将对应菜单栏设置为默认状态
            mtv_home.setTextColor(getResources().getColor(R.color.colorTextNormal));
            mtv_home.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.homepage_1,0,0);
        }
        if(mFragment_Buy != null){
            //隐藏Fragment
            transaction.hide(mFragment_Buy);
            //将对应菜单栏设置为默认状态
            mtv_buy.setTextColor(getResources().getColor(R.color.colorTextNormal));
            mtv_buy.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.buy,0,0);
        }
        if(mFragment_Sell != null){
            //隐藏Fragment
            transaction.hide(mFragment_Sell);
            //将对应菜单栏设置为默认状态
            mtv_sell.setTextColor(getResources().getColor(R.color.colorTextNormal));
            mtv_sell.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.sell,0,0);
        }
        if(mFragment_Messagge != null){
            //隐藏Fragment
            transaction.hide(mFragment_Messagge);
            //将对应菜单栏设置为默认状态
            mtv_message.setTextColor(getResources().getColor(R.color.colorTextNormal));
            mtv_message.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.sell,0,0);
        }
        if(mFragment_Mine != null){
            //隐藏Fragment
            transaction.hide(mFragment_Mine);
            //将对应菜单栏设置为默认状态
            mtv_mine.setTextColor(getResources().getColor(R.color.colorTextNormal));
            mtv_mine.setCompoundDrawablesWithIntrinsicBounds(0,
                    R.mipmap.sell,0,0);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_home:
                setFragment(Fragment_Home);
                break;
            case R.id.tv_buy:
                setFragment(Fragment_Buy);
                break;
            case R.id.tv_sell:
                setFragment(Fragment_Sell);
                break;
            case R.id.tv_message:
                setFragment(Fragment_Message);
                break;
            case R.id.tv_mine:
                setFragment(Fragment_Mine);
                break;
        }
    }
}