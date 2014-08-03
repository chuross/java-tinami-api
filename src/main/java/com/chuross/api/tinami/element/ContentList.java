package com.chuross.api.tinami.element;

import org.simpleframework.xml.*;

@Root(name = "rsp")
public class ContentList implements RootElement {

    @Attribute(name = "stat")
    private String status;

    @Element(required = false)
    private Contents contents;

    @Element(name = "err", required = false)
    private Error error;

    @Override
    public String getStatus() {
        return status;
    }


    public Contents getContents() {
        return contents;
    }

    @Override
    public Error getError() {
        return error;
    }

}
