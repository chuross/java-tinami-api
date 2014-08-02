package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class WatchKeywordResult extends AbstractAuthenticatedResult<ContentList> {

    public WatchKeywordResult(int status, ContentList result) {
        super(status, result);
    }

}
