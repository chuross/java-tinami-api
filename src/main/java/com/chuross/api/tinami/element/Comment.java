package com.chuross.api.tinami.element;

import com.chuross.common.library.util.MethodCallUtils;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;

public class Comment {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN);

    @Attribute
    private long id;

    @Attribute(name = "authorname")
    private String userName;

    @Attribute(name = "datecreate")
    private String createdAt;

    @Text
    private String body;

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Date getCreatedAt() {
        return MethodCallUtils.callOrNull(new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return FORMAT.parse(createdAt);
            }
        });
    }

    public String getBody() {
        return body;
    }
}
