package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class FriendRecommendResult extends AbstractAuthenticatedResult<ContentList> {

    public FriendRecommendResult(int status, ContentList result) {
        super(status, result);
    }

}
