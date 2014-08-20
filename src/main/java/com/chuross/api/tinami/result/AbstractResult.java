package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.*;
import com.chuross.api.tinami.element.Error;
import org.apache.http.Header;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractResult<T extends RootElement> extends com.chuross.common.library.api.result.AbstractResult<T> {

    public AbstractResult(int status, List<Header> headers, T result) {
        super(status, headers, result);
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
