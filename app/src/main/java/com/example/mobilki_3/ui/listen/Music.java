package com.example.mobilki_3.ui.listen;

public class Music {
    private String author;
    private String composition;
    private int source;

    public Music(String author, String composition, int source) {
        this.author = author;
        this.composition = composition;
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public String getComposition() {
        return composition;
    }

    public int getSource() {
        return source;
    }
}
