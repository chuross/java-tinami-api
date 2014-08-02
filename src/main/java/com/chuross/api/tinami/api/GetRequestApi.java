package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.result.AbstractResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

abstract class GetRequestApi<T extends AbstractResult<?>> extends com.chuross.common.library.api.GetRequestApi<T> {

    private Context context;
    private String authKey;

    public GetRequestApi(Context context) {
        this(context, null);
    }

    public GetRequestApi(Context context, String authKey) {
        this.context = context;
        this.authKey = authKey;
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        nameValuePairs.add(new BasicNameValuePair("api_key", context.getApiKey()));
        if(StringUtils.isBlank(authKey)) {
            return;
        }
        nameValuePairs.add(new BasicNameValuePair("auth_key", authKey));
    }
}
