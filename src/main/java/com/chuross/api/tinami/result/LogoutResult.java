package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.Response;
import org.apache.http.Header;

import java.util.List;

public class LogoutResult extends AbstractResult<Response> {

    public LogoutResult(int status, List<Header> headers, Response result) {
        super(status, headers, result);
    }

}
