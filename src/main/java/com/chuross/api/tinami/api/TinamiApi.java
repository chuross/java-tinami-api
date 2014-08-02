package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Account;
import com.chuross.api.tinami.ApiContext;
import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.OnLoginSessionExpiredListener;
import com.chuross.api.tinami.parameter.SearchParameter;
import com.chuross.api.tinami.result.*;
import com.chuross.common.library.api.Api;
import com.chuross.common.library.util.FutureUtils;
import com.chuross.common.library.util.MethodCallUtils;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.http.client.config.RequestConfig;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TinamiApi {

    private static final int TIME_OUT = (int) TimeUnit.SECONDS.toMillis(10);
    private static final int RETRY_COUNT = 3;
    private Account account;
    private RequestConfig config;
    private Context context;
    private OnLoginSessionExpiredListener listener;

    public TinamiApi(String apiKey, Account account) {
        this(apiKey, account, RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build());
    }

    public TinamiApi(String apiKey, Account account, RequestConfig config) {
        this(new ApiContext(apiKey), account, config);
    }

    TinamiApi(Context context, Account account, RequestConfig config) {
        this.context = context;
        this.account = account;
        this.config = config;
    }

    public Future<AuthenticationResult> login(Executor executor) {
        return new AuthenticationApi(context, account.getEmail(), account.getPassword()).execute(executor, config, RETRY_COUNT);
    }

    public Future<LogoutResult> logout(Executor executor) {
        return new LogoutApi(context, account.getAuthKey()).execute(executor, config, RETRY_COUNT);
    }

    public Future<UserInfoResult> userInfo(Executor executor) {
        return executeWithAuthentication(executor, new Callable<Api<UserInfoResult>>() {
            @Override
            public Api<UserInfoResult> call() throws Exception {
                return new UserInfoApi(context, account.getAuthKey());
            }
        });
    }

    public Future<SearchResult> search(final Executor executor, final SearchParameter searchParameter) {
        return executeWithAuthentication(executor, new Callable<Api<SearchResult>>() {
            @Override
            public Api<SearchResult> call() throws Exception {
                return new SearchApi(context, account.getAuthKey(), searchParameter);
            }
        });
    }

    public Future<BookmarkContentListResult> bookmarkContents(Executor executor, final int page, final int perpage, final boolean safe) {
        return executeWithAuthentication(executor, new Callable<Api<BookmarkContentListResult>>() {
            @Override
            public Api<BookmarkContentListResult> call() throws Exception {
                return new BookmarkContentListApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<FriendRecommendContentListResult> friendRecommendContents(Executor executor, final int page, final int perpage, final boolean safe) {
        return executeWithAuthentication(executor, new Callable<Api<FriendRecommendContentListResult>>() {
            @Override
            public Api<FriendRecommendContentListResult> call() throws Exception {
                return new FriendRecommendContentListApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    private <R extends AbstractAuthenticatedResult<?>> Future<R> executeWithAuthentication(Executor executor, final Callable<Api<R>> apiCallable) {
        return FutureUtils.executeOrNull(executor, new Callable<R>() {
            @Override
            public R call() throws Exception {
                return executeWithAuthentication(apiCallable);
            }
        });
    }

    private <R extends AbstractAuthenticatedResult<?>> R executeWithAuthentication(final Callable<Api<R>> apiCallable) {
        Api<R> api = MethodCallUtils.callOrNull(apiCallable);
        if(api == null) {
            return null;
        }
        R result = FutureUtils.getOrNull(api.execute(MoreExecutors.sameThreadExecutor(), config, RETRY_COUNT));
        if(result == null) {
            return null;
        }
        if(!result.isExpiredAuthKey() && !result.isInvalidAuthKey()) {
            return result;
        }
        AuthenticationResult authenticationResult = FutureUtils.getOrNull(login(MoreExecutors.sameThreadExecutor()));
        if(authenticationResult.isLoginFailed() && listener != null) {
            listener.onChangedAccountInfo();
        }
        if(!authenticationResult.isSuccess()) {
            return null;
        }
        String newAuthKey = authenticationResult.getResult().getAuthKey();
        if(listener != null) {
            listener.onSessionChanged(newAuthKey);
        }
        account = new Account(account.getEmail(), account.getPassword(), newAuthKey);
        return FutureUtils.getOrNull(MethodCallUtils.callOrNull(apiCallable).execute(MoreExecutors.sameThreadExecutor(), config, RETRY_COUNT));
    }

    public void setOnLoginSessionChangedListener(OnLoginSessionExpiredListener listener) {
        this.listener = listener;
    }

}
