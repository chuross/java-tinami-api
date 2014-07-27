package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rsp")
public class UserInfo implements RootElement {

    @Attribute(name = "stat")
    private String status;

    @Element(name = "err", required = false)
    private Error error;

    @Element
    private User user;

    @Element(required = false)
    private Creator creator;

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Error getError() {
        return error;
    }

    public User getUser() {
        return user;
    }

    public Creator getCreator() {
        return creator;
    }

}
