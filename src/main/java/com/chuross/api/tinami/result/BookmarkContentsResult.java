package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class BookmarkContentsResult extends AbstractAuthenticatedResult<ContentList> {

    public BookmarkContentsResult(int status, ContentList result) {
        super(status, result);
    }

}
