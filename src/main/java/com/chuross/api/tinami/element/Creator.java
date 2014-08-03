package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Creator {

    @Attribute(required = false)
    private long id;

    @Attribute(name = "isbookmark", required = false)
    private int bookmarkAppended;

    @Element(required = false)
    private String name;

    @Element(name = "thumbnail", required = false)
    private String thumbnailUrl;

    public long getId() {
        return id;
    }

    public boolean isBookmarkAppended() {
        return bookmarkAppended == 1;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl.trim().replaceAll("\n", "");
    }

}
