package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.UserInfo;
import com.chuross.api.tinami.result.UserInfoResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;

import java.util.List;

class UserInfoApi extends GetApi<UserInfoResult> {

    public UserInfoApi(Context context, String authKey) {
        super(context, authKey);
    }

    @Override
    protected String getUrl() {
        return getContext().getSecureUrl("/login/info");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected UserInfoResult convert(HttpResponse response) throws Exception {
        UserInfo info = XmlUtils.read(UserInfo.class, response.getContentsAsString(), false);
        return new UserInfoResult(response.getStatus(), response.getHeaders(), info);
    }

}
