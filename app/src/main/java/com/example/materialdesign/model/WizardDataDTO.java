package com.example.materialdesign.model;

public class WizardDataDTO {

    private String title;
    private String description;
    private int image;

    public WizardDataDTO(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getImage() {
        return image;
    }
}
