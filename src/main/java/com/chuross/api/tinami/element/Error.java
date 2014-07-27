package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;

public class Error {

    @Attribute(name = "msg")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
