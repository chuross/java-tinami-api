package com.chuross.api.tinami.api;

import com.chuross.api.tinami.ApiContext;
import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.result.AuthenticationResult;
import com.chuross.api.tinami.result.UserInfoResult;
import org.apache.http.client.config.RequestConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TinamiApi {

    private static final int TIME_OUT = (int) TimeUnit.SECONDS.toMillis(10);
    private static final int RETRY_COUNT = 3;
    private RequestConfig config;
    private Context context;

    public TinamiApi(String apiKey) {
        this(apiKey, RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build());
    }

    public TinamiApi(String apiKey, RequestConfig config) {
        context = new ApiContext(apiKey);
        this.config = config;
    }

    TinamiApi(Context context) {
        this.context = context;
    }

    public Future<AuthenticationResult> authenticate(Executor executor, String email, String password) {
        return new AuthenticationApi(context, email, password).execute(executor, config, RETRY_COUNT);
    }

    public Future<UserInfoResult> userInfo(Executor executor, String authKey) {
        return new UserInfoApi(context, authKey).execute(executor, config, RETRY_COUNT);
    }

}
