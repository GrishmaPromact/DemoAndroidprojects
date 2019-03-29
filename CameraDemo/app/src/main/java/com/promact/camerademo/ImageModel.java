package com.promact.camerademo;

import java.io.Serializable;

/**
 * Created by grishma on 24-01-2018.
 */

public class ImageModel implements Serializable {
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
