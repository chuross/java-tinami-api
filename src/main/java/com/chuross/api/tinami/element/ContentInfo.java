package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rsp")
public class ContentInfo implements RootElement {

    @Attribute(name = "stat")
    private String status;

    @Element(required = false)
    private Content content;

    @Element(name = "err", required = false)
    private Error error;

    @Override
    public String getStatus() {
        return status;
    }

    public Content getContent() {
        return content;
    }

    @Override
    public Error getError() {
        return error;
    }
}
