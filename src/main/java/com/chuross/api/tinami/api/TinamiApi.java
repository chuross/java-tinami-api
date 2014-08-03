package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Account;
import com.chuross.api.tinami.ApiContext;
import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.parameter.ContentType;
import com.chuross.api.tinami.parameter.SearchParameter;
import com.chuross.api.tinami.result.*;
import com.chuross.api.tinami.result.AuthenticationResult;
import com.chuross.common.library.api.*;
import org.apache.http.client.config.RequestConfig;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TinamiApi extends Service<String> {

    private static final int TIME_OUT = (int) TimeUnit.SECONDS.toMillis(10);
    private static final int RETRY_COUNT = 3;
    private Account account;
    private RequestConfig config;
    private Context context;

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

    @Override
    protected void onLoginSessionChanged(String newSession) {
        account = new Account(account.getEmail(), account.getPassword(), newSession);
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

    public Future<BookmarkContentsResult> bookmarkContents(Executor executor, final int page, final int perpage, final boolean safe) {
        return executeWithAuthentication(executor, new Callable<Api<BookmarkContentsResult>>() {
            @Override
            public Api<BookmarkContentsResult> call() throws Exception {
                return new BookmarkContentsApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<BookmarkCreatorsResult> bookmarkCreators(Executor executor, final int page, final int perpage) {
        return executeWithAuthentication(executor, new Callable<Api<BookmarkCreatorsResult>>() {
            @Override
            public Api<BookmarkCreatorsResult> call() throws Exception {
                return new BookmarkCreatorsApi(context, account.getAuthKey(), page, perpage);
            }
        });
    }

    public Future<AppendBookmarkCreatorsResult> appendBookmarkCreators(Executor executor, final long userId) {
        return executeWithAuthentication(executor, new Callable<Api<AppendBookmarkCreatorsResult>>() {
            @Override
            public Api<AppendBookmarkCreatorsResult> call() throws Exception {
                return new AppendBookmarkCreatorsApi(context, account.getAuthKey(), userId);
            }
        });
    }

    public Future<FriendRecommendResult> friendRecommend(Executor executor, final int page, final int perpage, final boolean safe) {
        return executeWithAuthentication(executor, new Callable<Api<FriendRecommendResult>>() {
            @Override
            public Api<FriendRecommendResult> call() throws Exception {
                return new FriendRecommendApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<WatchKeywordResult> watchKeyword(Executor executor, final int page, final int perpage, final boolean safe) {
        return executeWithAuthentication(executor, new Callable<Api<WatchKeywordResult>>() {
            @Override
            public Api<WatchKeywordResult> call() throws Exception {
                return new WatchKeywordApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<CollectionResult> collections(Executor executor, final int page, final int perpage, final boolean safe) {
        return executeWithAuthentication(executor, new Callable<Api<CollectionResult>>() {
            @Override
            public Api<CollectionResult> call() throws Exception {
                return new CollectionApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<AppendCollectionResult> appendCollection(Executor executor, final long contentId) {
        return executeWithAuthentication(executor, new Callable<Api<AppendCollectionResult>>() {
            @Override
            public Api<AppendCollectionResult> call() throws Exception {
                return new AppendCollectionApi(context, account.getAuthKey(), contentId);
            }
        });
    }

    public Future<RankingResult> ranking(Executor executor, ContentType contentType) {
        return new RankingApi(context, contentType).execute(executor, config, RETRY_COUNT);
    }

    public Future<ContentInfoResult> contentInfo(Executor executor, final long contentId) {
        return executeWithAuthentication(executor, new Callable<Api<ContentInfoResult>>() {
            @Override
            public Api<ContentInfoResult> call() throws Exception {
                return new ContentInfoApi(context, account.getAuthKey(), contentId);
            }
        });
    }

    public Future<CreatorInfoResult> creatorInfo(Executor executor, final long creatorId) {
        return executeWithAuthentication(executor, new Callable<Api<CreatorInfoResult>>() {
            @Override
            public Api<CreatorInfoResult> call() throws Exception {
                return new CreatorInfoApi(context, account.getAuthKey(), creatorId);
            }
        });
    }

    public Future<CommentListResult> comments(Executor executor, long contentId) {
        return new CommentListApi(context, contentId).execute(executor, config, RETRY_COUNT);
    }

    public Future<AppendCommentResult> appendComment(Executor executor, final long contentId, final String comment) {
        return executeWithAuthentication(executor, new Callable<Api<AppendCommentResult>>() {
            @Override
            public Api<AppendCommentResult> call() throws Exception {
                return new AppendCommentApi(context, account.getAuthKey(), contentId, comment);
            }
        });
    }

    public Future<RemoveCommentResult> removeComment(Executor executor, final long commentId) {
        return executeWithAuthentication(executor, new Callable<Api<RemoveCommentResult>>() {
            @Override
            public Api<RemoveCommentResult> call() throws Exception {
                return new RemoveCommentApi(context, account.getAuthKey(), commentId);
            }
        });
    }

    private <R extends AbstractAuthenticatedResult<?>> Future<R> executeWithAuthentication(Executor executor, final Callable<Api<R>> apiCallable) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, apiCallable, new Callable<Api<AuthenticationResult>>() {
            @Override
            public Api<AuthenticationResult> call() throws Exception {
                return new AuthenticationApi(context, account.getEmail(), account.getPassword());
            }
        });
    }

}
