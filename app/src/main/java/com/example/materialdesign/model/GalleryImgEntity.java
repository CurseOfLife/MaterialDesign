package com.example.materialdesign.model;

public class GalleryImgEntity {

    public String title;
    public int image;
    public boolean section = false;

    public GalleryImgEntity() {
    }

    public GalleryImgEntity(String title, int image, boolean section) {
        this.title = title;
        this.image = image;
        this.section = section;
    }
}
