package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.RootElement;
import com.chuross.common.library.api.result.AuthenticatedResult;
import org.apache.http.Header;

import java.util.List;

public abstract class AbstractAuthenticatedResult<T extends RootElement> extends AbstractResult<T> implements AuthenticatedResult<T> {

    public AbstractAuthenticatedResult(int status, List<Header> headers, T result) {
        super(status, headers, result);
    }

    @Override
    public boolean isExpiredLoginSession() {
        return isExpiredAuthKey() || isInvalidAuthKey();
    }

    public boolean isExpiredAuthKey() {
        return errorMessageExists("認証キーの有効期限が切れました");
    }

    public boolean isInvalidAuthKey() {
        return errorMessageExists("認証キーが不正です");
    }

}
