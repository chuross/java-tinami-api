package com.chuross.api.tinami.element;

import com.chuross.api.tinami.ViewLevel;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Content {

    @Attribute(name = "id")
    private long id;

    @Attribute
    private String type;

    @Element
    private String title;

    @Element(name = "view_level")
    private String viewLevel;

    @Element(name = "age_level")
    private int ageLevel;

    @ElementList
    private List<Thumbnail> thumbnails;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public ViewLevel getViewLevel() {
        return ViewLevel.resolve(viewLevel);
    }

    public int getAgeLevel() {
        return ageLevel;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

}
