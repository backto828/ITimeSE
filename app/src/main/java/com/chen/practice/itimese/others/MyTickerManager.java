package com.chen.practice.itimese.others;

import android.content.Context;

import com.chen.practice.itimese.model.MyTicker;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MyTickerManager {
    public static final String fileName = "myTickerSerializable";

    static public void save(Context context, ArrayList<MyTicker> myTickers) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(myTickers);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public ArrayList<MyTicker> load(Context context) {
        ArrayList<MyTicker> myTickers = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput(fileName));
            myTickers = (ArrayList<MyTicker>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return myTickers;
    }
}

