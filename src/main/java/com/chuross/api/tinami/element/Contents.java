package com.chuross.api.tinami.element;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.List;

public class Contents {

    @Attribute
    public long total;

    @Attribute
    private int page;

    @Attribute
    private int pages;

    @Attribute
    private int perpage;

    @ElementList(inline = true, required = false, empty = false)
    private List<Content> list;

    public long getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public List<Content> getList() {
        return list;
    }

}
