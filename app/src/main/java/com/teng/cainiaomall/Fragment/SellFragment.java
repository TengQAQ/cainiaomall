package com.teng.cainiaomall.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teng.cainiaomall.R;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SellFragment extends Fragment {
    private NiceSpinner niceSpinner;
    List<String> spinnerData = new LinkedList<>(Arrays.asList("类型一", "类型二", "类型三", "类型四",
            "五", "六", "七", "八", "九", "十", "十一", "十二"));


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sell, container, false);
        view.findViewById(R.id.nice_spinner);

        niceSpinner.attachDataSource(spinnerData);
        niceSpinner.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner.setTextColor(Color.WHITE);
        niceSpinner.setTextSize(13);

        return view;
    }
}