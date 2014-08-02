package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Logout;
import com.chuross.api.tinami.result.LogoutResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;

import java.util.List;

class LogoutApi extends GetRequestApi<LogoutResult> {

    public LogoutApi(Context context, String authKey) {
        super(context, authKey);
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/logout");
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
