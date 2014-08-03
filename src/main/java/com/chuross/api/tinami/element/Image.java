package com.chuross.api.tinami.element;

import org.simpleframework.xml.Element;

public class Image {

    @Element
    private String url;

    @Element
    private int width;

    @Element
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
