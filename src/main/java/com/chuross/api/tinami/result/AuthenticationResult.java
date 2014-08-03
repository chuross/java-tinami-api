package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.*;
import com.chuross.api.tinami.element.Error;

public class AuthenticationResult extends AbstractResult<Authentication> implements com.chuross.common.library.api.AuthenticationResult<String, Authentication> {

    public AuthenticationResult(int status, Authentication result) {
        super(status, result);
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
