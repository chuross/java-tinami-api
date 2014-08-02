package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;

public class Thumbnail {

    @Attribute
    private String url;

    @Attribute
    private int width;

    @Attribute
    private int height;

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
