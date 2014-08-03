package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.Response;

public class AppendBookmarkCreatorsResult extends AbstractAuthenticatedResult<Response> {

    public AppendBookmarkCreatorsResult(int status, Response result) {
        super(status, result);
    }

}
