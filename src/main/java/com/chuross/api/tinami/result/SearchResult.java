package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class SearchResult extends AbstractAuthenticatedResult<ContentList> {

    public SearchResult(int status, ContentList result) {
        super(status, result);
    }

}
