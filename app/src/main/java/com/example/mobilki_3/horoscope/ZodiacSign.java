package com.example.mobilki_3.horoscope;

import android.graphics.Bitmap;

import java.util.Date;

public class ZodiacSign {
    private String name;
    private Date dateFrom;
    private Date dateTo;
    private String ImgUrl;
    private String horoscope;
    private Bitmap image;

    public ZodiacSign(String name, Date dateFrom, Date dateTo, String imgUrl, String horoscope) {
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.ImgUrl = imgUrl;
        this.horoscope = horoscope;
        this.image = null;
    }

    public String getName() {
        return name;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
