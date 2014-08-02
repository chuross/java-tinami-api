package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class BookmarkContentResult extends AbstractAuthenticatedResult<ContentList> {

    public BookmarkContentResult(int status, ContentList result) {
        super(status, result);
    }

}
