package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.*;
import com.chuross.api.tinami.element.Error;

public class AuthenticationResult extends AbstractResult<Authentication> {

    public AuthenticationResult(int status, Authentication result) {
        super(status, result);
    }

    public boolean isLoginFailed() {
        Error error = getResult().getError();
        return error != null && error.getMessage().startsWith("Login failed");
    }

}
