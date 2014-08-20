package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Response;
import com.chuross.api.tinami.result.LogoutResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;

import java.util.List;

class LogoutApi extends GetApi<LogoutResult> {

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
        Response responseElement = XmlUtils.read(Response.class, response.getContentsAsString(), false);
        return new LogoutResult(response.getStatus(), response.getHeaders(), responseElement);
    }
}
