package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.Response;

public class RemoveCommentResult extends AbstractAuthenticatedResult<Response> {

    public RemoveCommentResult(int status, Response result) {
        super(status, result);
    }

}
