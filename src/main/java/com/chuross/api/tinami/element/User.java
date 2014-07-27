package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;

public class User {

    @Attribute(name = "no")
    private String id;

    public String getId() {
        return id;
    }

}
