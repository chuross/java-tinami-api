package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class CollectionResult extends AbstractAuthenticatedResult<ContentList> {

    public CollectionResult(int status, ContentList result) {
        super(status, result);
    }

}
