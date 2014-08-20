package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.result.AbstractResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

abstract class GetApi<T extends AbstractResult<?>> extends com.chuross.common.library.api.GetApi<T> {

    private Context context;
    private String authKey;

    public GetApi(Context context) {
        this(context, null);
    }

    public GetApi(Context context, String authKey) {
        this.context = context;
        this.authKey = authKey;
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        nameValuePairs.add(new BasicNameValuePair("api_key", context.getApiKey()));
        if(!StringUtils.isBlank(authKey)) {
            nameValuePairs.add(new BasicNameValuePair("auth_key", authKey));
        }
    }
}
