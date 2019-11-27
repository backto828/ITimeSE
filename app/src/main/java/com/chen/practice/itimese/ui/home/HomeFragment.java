package com.chen.practice.itimese.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chen.practice.itimese.R;
import com.chen.practice.itimese.model.MyTicker;
import com.chen.practice.itimese.model.MyTickerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<MyTicker> myTickers;
    private MyTickerAdapter myTickerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // 定义数据
        initData();
        // 新增按钮
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "新增页面待开发", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
    }

    private void initData() {
        // 加载myTime 等待重构
//        myTickers = MyTimeManager.load(this.getContext());
//        myTickerAdapter = new MyTickerAdapter(this.getContext(), R.layout.home_my_time_item_layout, myTickers);
    }
}