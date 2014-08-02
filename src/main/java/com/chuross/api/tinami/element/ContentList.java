package com.chuross.api.tinami.element;

import org.simpleframework.xml.*;

import java.util.List;

@Root(name = "rsp")
public class ContentList implements RootElement {

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

    @ElementList(name = "contents")
    private List<Content> contents;

    @Attribute(name = "err", required = false)
    private Error error;

    @Override
    public String getStatus() {
        return status;
    }

//    public long getTotal() {
//        return total;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public int getPages() {
//        return pages;
//    }
//
//    public int getPerpage() {
//        return perpage;
//    }

    public List<Content> getContents() {
        return contents;
    }

    @Override
    public Error getError() {
        return error;
    }

}
