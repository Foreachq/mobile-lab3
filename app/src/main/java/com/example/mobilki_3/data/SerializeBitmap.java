package com.example.mobilki_3.data;

import android.graphics.Bitmap;
import java.io.Serializable;

public class SerializeBitmap implements Serializable {
    private final int [] pixels;
    private final int width , height;

    public SerializeBitmap(Bitmap bitmap){
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int [width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
    }

    public Bitmap getBitmap(){
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }
}
