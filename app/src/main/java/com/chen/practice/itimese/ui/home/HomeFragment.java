package com.chen.practice.itimese.ui.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.chen.practice.itimese.R;
import com.chen.practice.itimese.model.Date;
import com.chen.practice.itimese.model.MyTicker;
import com.chen.practice.itimese.model.MyTickerAdapter;
import com.chen.practice.itimese.others.MyTickerManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    // Waiting For Refactoring
    private int color = 0;
    private boolean colorChanged = false;
    private boolean setAble = false;

    // 定义一些整型常量
    private static final int REQUEST_CODE = 2013;
    private static final int ADD_MODE = 1;
    private static final int MODIFY_MODE = 2;
    private static final int DELETE_MODE = 3;

    // 数组及适配器
    private ArrayList<MyTicker> myTickers;
    private MyTickerAdapter myTickerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // 定义数据
        initData();
        // ListView
        ListView listViewMyTimer = root.findViewById(R.id.list_view_my_timer);
        listViewMyTimer.setAdapter(myTickerAdapter);

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

    //接收并添加传过来的值
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeFragment.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                int mode = bundle.getInt("mode");
                switch (mode) {
                    case ADD_MODE: {
                        MyTicker myTicker = (MyTicker) bundle.getSerializable("time");
                        myTickers.add(myTicker);
                        myTickerAdapter.notifyDataSetChanged();
                        break;
                    }
                    case MODIFY_MODE: {
                        MyTicker myTicker = (MyTicker) bundle.getSerializable("time");
                        int position = bundle.getInt("position");
                        if (position >= 0 && position < myTickers.size()) {
                            myTickers.set(position, myTicker);
                            myTickerAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                    case DELETE_MODE: {
                        int position = bundle.getInt("position");
                        if (position >= 0 && position < myTickers.size()) {
                            myTickers.remove(position);
                            myTickerAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                }
            }
        }
    }

    private void initData() {
        myTickers = MyTickerManager.load(this.getContext());
        myTickerAdapter = new MyTickerAdapter(this.getContext(), R.layout.list_view_ticker_item, myTickers);

    }

    // Waiting For Refactoring
    @Override
    public void onResume() {
        super.onResume();
        setAble = true;
        if (colorChanged) {
            this.colorChanged = false;
            setFabColor();
        }
    }

    // Waiting For Refactoring
    public void setColor(int color) {
        if (this.color != color) {
            colorChanged = true;
            this.color = color;

            if (this.setAble) {
                this.colorChanged = false;
                setFabColor();
            }
        }
    }

    private void setFabColor() {
        // Waiting For Refactoring
//        fab.setBackgroundTintList(ColorStateList.valueOf(color));
    }
}