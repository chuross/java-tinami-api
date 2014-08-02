package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.CreatorList;

public class BookmarkCreatorsResult extends AbstractAuthenticatedResult<CreatorList> {

    public BookmarkCreatorsResult(int status, CreatorList result) {
        super(status, result);
    }

}
