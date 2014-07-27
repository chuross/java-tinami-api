package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.UserInfo;
import com.chuross.api.tinami.result.UserInfoResult;
import com.chuross.common.library.api.GetRequestApi;
import com.chuross.common.library.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.simpleframework.xml.core.Persister;

import java.util.List;

class UserInfoApi extends GetRequestApi<UserInfoResult> {

    private Context context;
    private String  authKey;

    public UserInfoApi(Context context, String authKey) {
        this.context = context;
        this.authKey = authKey;
    }

    @Override
    protected String getUrl() {
        return context.getSecureUrl("/login/info");
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        nameValuePairs.add(new BasicNameValuePair("api_key", context.getApiKey()));
        nameValuePairs.add(new BasicNameValuePair("auth_key", authKey));
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected UserInfoResult convert(HttpResponse response) throws Exception {
        UserInfo info = new Persister().read(UserInfo.class, response.getContentsAsString("UTF-8"));
        return new UserInfoResult(response.getStatus(), info);
    }

}
