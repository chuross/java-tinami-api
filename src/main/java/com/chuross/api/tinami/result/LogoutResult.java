package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.Response;

public class LogoutResult extends AbstractResult<Response> {

    public LogoutResult(int status, Response result) {
        super(status, result);
    }

}
