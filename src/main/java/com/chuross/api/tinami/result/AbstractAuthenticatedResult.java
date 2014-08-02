package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.RootElement;

public abstract class AbstractAuthenticatedResult<T extends RootElement> extends AbstractResult<T> {

    public AbstractAuthenticatedResult(int status, T result) {
        super(status, result);
    }

    public boolean isExpiredAuthKey() {
        return errorMessageExists("認証キーの有効期限が切れました");
    }

    public boolean isInvalidAuthKey() {
        return errorMessageExists("認証キーが不正です");
    }

}
