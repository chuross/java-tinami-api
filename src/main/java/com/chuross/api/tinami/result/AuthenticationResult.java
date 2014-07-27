package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.*;
import com.chuross.api.tinami.element.Error;
import com.chuross.common.library.api.AbstractResult;

public class AuthenticationResult extends AbstractResult<Authentication> {

    public AuthenticationResult(int status, Authentication result) {
        super(status, result);
    }

    @Override
    public boolean isSuccess() {
        return getStatus() == 200 && getResult().getStatus().equals("ok");
    }

    public boolean isLoginFailed() {
        Error error = getResult().getError();
        return error != null && error.getMessage().startsWith("Login failed");
    }

}
