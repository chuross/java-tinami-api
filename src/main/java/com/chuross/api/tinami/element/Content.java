package com.chuross.api.tinami.element;

import com.chuross.api.tinami.ViewLevel;
import com.chuross.common.library.util.MethodCallUtils;
import org.simpleframework.xml.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

@Root
public class Content {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPAN);

    @Attribute(required = false)
    private long id;

    @Attribute
    private String type;

    @Attribute(name = "issupport", required = false)
    private int supported;

    @Attribute(name = "iscollection", required = false)
    private int collectionAppended;

    @Element
    private String title;

    @Element(required = false)
    private Creator creator;

    @Element(name = "view_level", required = false)
    private String viewLevel;

    @Element(name = "age_level", required = false)
    private int ageLevel;

    @Element(required = false)
    private String description;

    @ElementList
    private List<Thumbnail> thumbnails;

    @Element(required = false)
    private Image image;

    @Element(name = "total_view", required = false)
    private int totalViewCount;

    @Element(name = "user_view", required = false)
    private int userViewCount;

    @Element(required = false)
    private int valuation;

    @ElementList(required = false)
    List<String> tags;

    @Attribute(name = "posted", required = false)
    @Path("dates")
    private String createdAt;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isSupported() {
        return supported == 1;
    }

    public boolean isCollectionAppended() {
        return collectionAppended == 1;
    }

    public String getTitle() {
        return title;
    }

    public Creator getCreator() {
        return creator;
    }

    public ViewLevel getViewLevel() {
        return ViewLevel.resolve(viewLevel);
    }

    public int getAgeLevel() {
        return ageLevel;
    }

    public String getDescription() {
        return description;
    }

    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public Image getImage() {
        return image;
    }

    public int getTotalViewCount() {
        return totalViewCount;
    }

    public int getUserViewCount() {
        return userViewCount;
    }

    public int getValuation() {
        return valuation;
    }

    public List<String> getTags() {
        return tags;
    }

    public Date getCreatedAt() {
        return MethodCallUtils.callOrNull(new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return FORMAT.parse(createdAt);
            }
        });
    }

}
