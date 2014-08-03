package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class CreatorInfo implements RootElement {

    @Attribute(name = "stat")
    private String status;

    @Element(required = false)
    private Creator creator;

    @Element(name = "err", required = false)
    private Error error;

    @Override
    public String getStatus() {
        return status;
    }

    public Creator getCreator() {
        return creator;
    }

    @Override
    public Error getError() {
        return error;
    }

}
