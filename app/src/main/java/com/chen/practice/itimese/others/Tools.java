package com.chen.practice.itimese.others;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Tools {
    public static Bitmap getBitmapFromUriString(ContentResolver cr, String uriString) {
        try {
            InputStream input = cr.openInputStream(Uri.parse(uriString));
            return BitmapFactory.decodeStream(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
