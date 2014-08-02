package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;

public class User {

    @Attribute(name = "no")
    private long id;

    public long getId() {
        return id;
    }

}
