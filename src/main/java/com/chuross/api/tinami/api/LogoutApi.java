package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Logout;
import com.chuross.api.tinami.result.LogoutResult;
import com.chuross.common.library.api.GetRequestApi;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class LogoutApi extends GetRequestApi<LogoutResult> {

    private Context context;
    private String authKey;

    public LogoutApi(Context context, String authKey) {
        this.context = context;
        this.authKey = authKey;
    }

    @Override
    protected String getUrl() {
        return context.getUrl("/logout");
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
    protected LogoutResult convert(HttpResponse response) throws Exception {
        Logout logout = XmlUtils.read(Logout.class, response.getContentsAsString());
        return new LogoutResult(response.getStatus(), logout);
    }
}
