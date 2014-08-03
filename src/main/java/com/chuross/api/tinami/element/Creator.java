package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Creator {

    @Attribute
    private long id;

    @Attribute(name = "isbookmark", required = false)
    private boolean bookmarkAppended;

    @Element(required = false)
    private String name;

    @Element(name = "thumbnail", required = false)
    private String thumbnailUrl;

    public long getId() {
        return id;
    }

    public boolean isBookmarkAppended() {
        return bookmarkAppended;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl.trim().replaceAll("\n", "");
    }

}
