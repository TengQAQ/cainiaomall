package com.teng.cainiaomall.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.teng.cainiaomall.Activity.LoginActivity;
import com.teng.cainiaomall.Activity.RetrievePasswordActivity;
import com.teng.cainiaomall.Activity.Setinformation;
import com.teng.cainiaomall.R;

public class MineFragment extends Fragment {
    private ImageView myavatar;
    public static final int TAKE_CAMERA = 1;
    private static final int CHOOSE_PHOTO = 2;
    private View mhomepage_mine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        myavatar=view.findViewById(R.id.myavatar);
        mhomepage_mine=view.findViewById(R.id.homepage_mine);

        mhomepage_mine.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setClass(getActivity(), Setinformation.class);
            startActivity(intent);
        });

        myavatar.setOnClickListener(v->{
            
        });



        return view;
    }
}