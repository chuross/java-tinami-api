package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class BookmarkContentListResult extends AbstractAuthenticatedResult<ContentList> {

    public BookmarkContentListResult(int status, ContentList result) {
        super(status, result);
    }

}
