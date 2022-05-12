package com.example.mobilki_3.horoscope;

import java.util.ArrayList;
import java.util.Date;

public class Horoscope {
    private static Horoscope instance;
    private ArrayList<ZodiacSign> horoscopeList = new ArrayList<>();
    private String horoscopeUrl = "https://retrofm.ru/index.php?go=goroskop";
    public static synchronized Horoscope getInstance(){
        if (instance == null) {
            instance = new Horoscope();
        }
        return instance;
    }

    private Horoscope(){
        horoscopeList.add(new ZodiacSign("Овен", new Date(0, 3, 21), new Date(0,4,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_1.png", ""));
        horoscopeList.add(new ZodiacSign("Телец", new Date(0, 4, 21), new Date(0,5,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_2.png", ""));
        horoscopeList.add(new ZodiacSign("Близнецы", new Date(0, 5, 21), new Date(0,6,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_3.png", ""));
        horoscopeList.add(new ZodiacSign("Рак", new Date(0, 6, 21), new Date(0,7,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_4.png", ""));
        horoscopeList.add(new ZodiacSign("Лев", new Date(0, 7, 21), new Date(0,8,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_5.png", ""));
        horoscopeList.add(new ZodiacSign("Дева", new Date(0, 8, 21), new Date(0,9,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_6.png", ""));
        horoscopeList.add(new ZodiacSign("Весы", new Date(0, 9, 21), new Date(0,10,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_7.png", ""));
        horoscopeList.add(new ZodiacSign("Скорпион", new Date(0, 10, 21), new Date(0,11,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_8.png", ""));
        horoscopeList.add(new ZodiacSign("Стрелец", new Date(0, 11, 21), new Date(0,12,21), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_9.png", ""));
        horoscopeList.add(new ZodiacSign("Козерог", new Date(0, 12, 22), new Date(1,1,19), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_10.png", ""));
        horoscopeList.add(new ZodiacSign("Водолей", new Date(1, 1, 20), new Date(1,2,19), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_11.png", ""));
        horoscopeList.add(new ZodiacSign("Рыбы", new Date(1, 2, 20), new Date(1,3,20), "https://retrofm.ru/template/Retro-2012/img/other/zodiac_12.png", ""));
    }

    public ArrayList<ZodiacSign> getHoroscopeList() {
        return horoscopeList;
    }

    public String getHoroscopeUrl() {
        return horoscopeUrl;
    }

    public void setText(ZodiacSign zodiacSign, String text){
        if (horoscopeList.contains(zodiacSign)){
            horoscopeList.get(horoscopeList.indexOf(zodiacSign)).setHoroscope(text);
        }
    }
}
