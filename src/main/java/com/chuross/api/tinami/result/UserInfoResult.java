package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.UserInfo;

public class UserInfoResult extends AbstractAuthenticatedResult<UserInfo> {

    public UserInfoResult(int status, UserInfo result) {
        super(status, result);
    }

}
