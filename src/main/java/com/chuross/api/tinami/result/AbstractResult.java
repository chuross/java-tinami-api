package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.*;
import com.chuross.api.tinami.element.Error;

import java.util.Arrays;

public abstract class AbstractResult<T extends RootElement> extends com.chuross.common.library.api.AbstractResult<T> {

    public AbstractResult(int status, T result) {
        super(status, result);
    }

    @Override
    public boolean isSuccess() {
        return getStatus() == 200 && getResult().getStatus().equals("ok");
    }

    protected boolean errorMessageExists(String target) {
        Error error = getResult().getError();
        if(error == null) {
            return false;
        }
        return Arrays.asList(error.getMessages()).contains(target);
    }

}
