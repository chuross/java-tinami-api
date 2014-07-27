package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;

public class Creator {

    @Attribute(name = "id")
    private String id;

    public String getId() {
        return id;
    }

}
