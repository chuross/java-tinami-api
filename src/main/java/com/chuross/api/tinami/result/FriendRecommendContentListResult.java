package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class FriendRecommendContentListResult extends AbstractAuthenticatedResult<ContentList> {

    public FriendRecommendContentListResult(int status, ContentList result) {
        super(status, result);
    }

}
