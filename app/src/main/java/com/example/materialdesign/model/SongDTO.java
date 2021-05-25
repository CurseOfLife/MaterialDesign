package com.example.materialdesign.model;


public class SongDTO {

    public String songtitle;
    public String artist;
    public Integer image = null;
    public int color = -1;

    public boolean swiped = false;

    public SongDTO() { }

    public SongDTO(String songtitle, String artist, int image) {
        this.songtitle = songtitle;
        this.artist = artist;
        this.image = image;
    }
}
