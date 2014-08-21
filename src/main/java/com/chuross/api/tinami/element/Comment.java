package com.chuross.api.tinami.element;

import com.chuross.common.library.util.MethodCallUtils;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

import java.util.Date;

public class Comment {

    @Attribute
    private long id;

    @Attribute(name = "authorname")
    private String userName;

    @Attribute(name = "datecreate")
    private Date createdAt;

    @Text
    private String body;

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }
}
