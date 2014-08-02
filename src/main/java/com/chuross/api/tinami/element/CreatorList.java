package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rsp")
public class CreatorList implements RootElement {

    @Attribute(name = "stat")
    private String status;

//    @Attribute
//    @Path("/rsp/contents/@total")
//    public long total;
//
//    @Path("/rsp/contents/@page")
//    private int page;
//
//    @Path("/rsp/contents/@pages")
//    private int pages;
//
//    @Path("/rsp/contents/@perpage")
//    private int perpage;

    @ElementList(name = "creators")
    List<Creator> creators;

    @Element(name = "err", required = false)
    private Error error;

    @Override
    public String getStatus() {
        return status;
    }

    public List<Creator> getCreators() {
        return creators;
    }

    @Override
    public Error getError() {
        return error;
    }

}
