package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.Response;

public class AppendCommentResult extends AbstractAuthenticatedResult<Response> {

    public AppendCommentResult(int status, Response result) {
        super(status, result);
    }

}
