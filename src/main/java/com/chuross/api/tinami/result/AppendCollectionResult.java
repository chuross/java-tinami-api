package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.Response;

public class AppendCollectionResult extends AbstractAuthenticatedResult<Response> {

    public AppendCollectionResult(int status, Response result) {
        super(status, result);
    }

}
