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
        return getStatus() == 200 && getResult() != null && getResult().getStatus().equals("ok");
    }

    protected boolean errorMessageExists(String target) {
        T result = getResult();
        if(result == null) {
            return false;
        }
        Error error = result.getError();
        if(error == null) {
            return false;
        }
        return Arrays.asList(error.getMessages()).contains(target);
    }

}
