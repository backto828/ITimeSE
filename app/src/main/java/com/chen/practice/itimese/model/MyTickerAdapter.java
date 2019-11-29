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
import com.chen.practice.itimese.others.Tools;

import java.util.ArrayList;
import java.util.Calendar;

public class MyTickerAdapter extends ArrayAdapter<MyTicker> {

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
        if (deltaDay > 0) {
            deltaString = (deltaDay * -1) + "天";
        } else {
            deltaString = Math.abs(deltaDay) + "天";
        }

        String myTickerDate;
        myTickerDate = myTicker.date.year + "年" + myTicker.date.month + "月" + myTicker.date.day + "日";

        // 倒计时标题
        ((TextView) view.findViewById(R.id.tv_ticker_item_title)).setText(myTicker.title);
        // 倒计时备注
        if (myTicker.remark.isEmpty()) {
            ((TextView) view.findViewById(R.id.tv_ticker_item_remark)).setHeight(0);
        } else {
            ((TextView) view.findViewById(R.id.tv_ticker_item_remark)).setText(myTicker.remark);
        }
        // 倒计时图片上显示剩余天数
        ((TextView) view.findViewById(R.id.tv_ticker_image_days)).setText(deltaString);
        // 倒计时日期
        ((TextView) view.findViewById(R.id.tv_ticker_item_date))
                .setText(myTickerDate);
        // 倒计时图片设置
        if (!myTicker.imageUriPath.isEmpty()) {
            Bitmap bitmap = Tools.getBitmapFromUriString(view.getContext().getContentResolver(), myTicker.imageUriPath);
            if (bitmap != null) {
                ((ImageView) view.findViewById(R.id.iv_ticker_item_image)).setImageBitmap(bitmap);
            } else {
                ((ImageView) view.findViewById(R.id.iv_ticker_item_image)).setColorFilter(0xFF000000);
            }
        } else {
            ((ImageView) view.findViewById(R.id.iv_ticker_item_image)).setColorFilter(0xFF000000);
        }

        return view;
    }
}
