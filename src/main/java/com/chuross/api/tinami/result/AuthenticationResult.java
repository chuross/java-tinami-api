package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.*;
import com.chuross.api.tinami.element.Error;
import org.apache.http.Header;

import java.util.List;

public class AuthenticationResult extends AbstractResult<Authentication> implements com.chuross.common.library.api.result.AuthenticationResult<String, Authentication> {

    public AuthenticationResult(int status, List<Header> headers, Authentication result) {
        super(status, headers, result);
    }

    @Override
    public boolean isInvalidAccountInfo() {
        Error error = getResult().getError();
        return error != null && error.getMessage().startsWith("Login failed");
    }

    @Override
    public String getSession() {
        return getResult() != null ? getResult().getAuthKey() : null ;
    }
}
