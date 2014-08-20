package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.UserInfo;
import org.apache.http.Header;

import java.util.List;

public class UserInfoResult extends AbstractAuthenticatedResult<UserInfo> {

    public UserInfoResult(int status, List<Header> headers, UserInfo result) {
        super(status, headers, result);
    }

}
