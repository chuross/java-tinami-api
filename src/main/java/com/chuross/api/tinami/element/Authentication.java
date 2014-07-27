package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rsp")
public class Authentication {

    @Attribute(name = "stat")
    private String status;

    @Element(name = "err", required = false)
    private Error error;

    @Element(name = "auth_key", required = false)
    private String authKey;

    public String getStatus() {
        return status;
    }

    public Error getError() {
        return error;
    }

    public String getAuthKey() {
        return authKey;
    }

}
