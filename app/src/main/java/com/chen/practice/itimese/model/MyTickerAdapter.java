package com.chen.practice.itimese.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.practice.itimese.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MyTickerAdapter extends ArrayAdapter<MyTicker> {
    //定义一个startActivityForResult（）方法用到的整型值
    private final int requestCode = 1600;

    private int resourceId;

    public MyTickerAdapter(Context context, int resource, ArrayList<MyTicker> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(this.getContext());
        View view = mInflater.inflate(this.resourceId, parent, false);

        MyTicker myTicker = this.getItem(position);

        // 计算时间差（天）
        Calendar now = Calendar.getInstance();
        Calendar timeDate = Calendar.getInstance();
        timeDate.set(myTicker.date.year, myTicker.date.month - 1, myTicker.date.day, 0, 0, 0);
        long deltaDay = (now.getTime().getTime() - timeDate.getTime().getTime()) / (1000 * 3600 * 24);

        String deltaString;
        if (deltaDay == 0) {
            deltaString = "今天";
        } else if (deltaDay > 0) {
            deltaString = "已经" + deltaDay + "天";
        } else {
            deltaString = "还有" + Math.abs(deltaDay) + "天";
        }

        // 等待重构

//        ((TextView) view.findViewById(R.id.home_my_time_title_text_iew)).setText(myTicker.title);
//        if (myTicker.remark.isEmpty()) {
//            ((TextView) view.findViewById(R.id.home_my_time_remark_text_view)).setHeight(0);
//        } else {
//            ((TextView) view.findViewById(R.id.home_my_time_remark_text_view)).setText(myTicker.remark);
//        }
//        ((TextView) view.findViewById(R.id.home_my_time_countdown_text_view)).setText(deltaString);
//
//        if (!myTicker.imageUriPath.isEmpty()) {
//            Bitmap bitmap = Tool.getBitmapFromUriString(view.getContext().getContentResolver(), myTicker.imageUriPath);
//            if (bitmap != null) {
//                ((ImageView) view.findViewById(R.id.home_my_time_image_view)).setImageBitmap(bitmap);
//            } else {
//                ((ImageView) view.findViewById(R.id.home_my_time_image_view)).setColorFilter(0xFF000000);
//            }
//        } else {
//            ((ImageView) view.findViewById(R.id.home_my_time_image_view)).setColorFilter(0xFF000000);
//        }

        return view;
    }
}
