package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.result.AbstractResult;
import org.apache.http.NameValuePair;

import java.util.List;

abstract class PostApi<T extends AbstractResult<?>> extends com.chuross.common.library.api.PostApi<T> {

    private Context context;
    private String authKey;

    public PostApi(Context context) {
        this(context, null);
    }

    public PostApi(Context context, String authKey) {
        this.context = context;
        this.authKey = authKey;
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        addParameter(nameValuePairs, "api_key", context.getApiKey());
        addParameterIfNotNull(nameValuePairs, "auth_key", authKey);
    }
}
