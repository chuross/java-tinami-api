package com.chuross.api.tinami.api;

import com.chuross.api.tinami.TinamiAccount;
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

public class TinamiApi extends AccountService<String, AuthenticationResult> {

    private static final int TIME_OUT = (int) TimeUnit.SECONDS.toMillis(10);
    private static final int RETRY_COUNT = 3;
    private TinamiAccount account;
    private RequestConfig config;
    private Context context;

    public TinamiApi(String apiKey, TinamiAccount account) {
        this(apiKey, account, RequestConfig.custom().setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build());
    }

    public TinamiApi(String apiKey, TinamiAccount account, RequestConfig config) {
        this(new Context(apiKey), account, config);
    }

    TinamiApi(Context context, TinamiAccount account, RequestConfig config) {
        this.context = context;
        this.account = account;
        this.config = config;
    }

    @Override
    protected void onLoginSessionChanged(String newSession) {
        account = new TinamiAccount(account.getEmail(), account.getPassword(), newSession);
    }

    @Override
    protected Callable<Api<AuthenticationResult>> getAuthenticationApiCallable() {
        return new Callable<Api<AuthenticationResult>>() {
            @Override
            public Api<AuthenticationResult> call() throws Exception {
                return new AuthenticationApi(context, account.getEmail(), account.getPassword());
            }
        };
    }

    public Future<AuthenticationResult> login(Executor executor) {
        return new AuthenticationApi(context, account.getEmail(), account.getPassword()).execute(executor, config, RETRY_COUNT);
    }

    public Future<LogoutResult> logout(Executor executor) {
        return new LogoutApi(context, account.getAuthKey()).execute(executor, config, RETRY_COUNT);
    }

    public Future<UserInfoResult> getUserInfo(Executor executor) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<UserInfoResult>>() {
            @Override
            public Api<UserInfoResult> call() throws Exception {
                return new UserInfoApi(context, account.getAuthKey());
            }
        });
    }

    public Future<SearchResult> search(final Executor executor, final SearchParameter searchParameter) {
        return new SearchApi(context, account.getAuthKey(), searchParameter).execute(executor, config, RETRY_COUNT);
    }

    public Future<BookmarkContentsResult> getBookmarkContents(Executor executor, final Integer page, final Integer perpage, final Boolean safe) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<BookmarkContentsResult>>() {
            @Override
            public Api<BookmarkContentsResult> call() throws Exception {
                return new BookmarkContentsApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<BookmarkCreatorsResult> getBookmarkCreators(Executor executor, final Integer page, final Integer perpage) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<BookmarkCreatorsResult>>() {
            @Override
            public Api<BookmarkCreatorsResult> call() throws Exception {
                return new BookmarkCreatorsApi(context, account.getAuthKey(), page, perpage);
            }
        });
    }

    public Future<BookmarkCreatorsAddResult> addBookmarkCreators(Executor executor, final Long userId) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<BookmarkCreatorsAddResult>>() {
            @Override
            public Api<BookmarkCreatorsAddResult> call() throws Exception {
                return new BookmarkCreatorsAddApi(context, account.getAuthKey(), userId);
            }
        });
    }

    public Future<FriendRecommendResult> getFriendRecommend(Executor executor, final Integer page, final Integer perpage, final Boolean safe) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<FriendRecommendResult>>() {
            @Override
            public Api<FriendRecommendResult> call() throws Exception {
                return new FriendRecommendApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<WatchKeywordResult> getWatchKeyword(Executor executor, final Integer page, final Integer perpage, final Boolean safe) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<WatchKeywordResult>>() {
            @Override
            public Api<WatchKeywordResult> call() throws Exception {
                return new WatchKeywordApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<CollectionResult> getCollections(Executor executor, final Integer page, final Integer perpage, final Boolean safe) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<CollectionResult>>() {
            @Override
            public Api<CollectionResult> call() throws Exception {
                return new CollectionApi(context, account.getAuthKey(), page, perpage, safe);
            }
        });
    }

    public Future<CollectionAddResult> addCollection(Executor executor, final Long contentId) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<CollectionAddResult>>() {
            @Override
            public Api<CollectionAddResult> call() throws Exception {
                return new CollectionAddApi(context, account.getAuthKey(), contentId);
            }
        });
    }

    public Future<RankingResult> getRanking(Executor executor, ContentType contentType) {
        return new RankingApi(context, contentType).execute(executor, config, RETRY_COUNT);
    }

    public Future<ContentInfoResult> getContentInfo(Executor executor, final Long contentId) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<ContentInfoResult>>() {
            @Override
            public Api<ContentInfoResult> call() throws Exception {
                return new ContentInfoApi(context, account.getAuthKey(), contentId);
            }
        });
    }

    public Future<CreatorInfoResult> getCreatorInfo(Executor executor, final long creatorId) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<CreatorInfoResult>>() {
            @Override
            public Api<CreatorInfoResult> call() throws Exception {
                return new CreatorInfoApi(context, account.getAuthKey(), creatorId);
            }
        });
    }

    public Future<CommentsResult> getComments(Executor executor, Long contentId) {
        return new CommentsApi(context, contentId).execute(executor, config, RETRY_COUNT);
    }

    public Future<CommentAddResult> addComment(Executor executor, final Long contentId, final String comment) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<CommentAddResult>>() {
            @Override
            public Api<CommentAddResult> call() throws Exception {
                return new CommentAddApi(context, account.getAuthKey(), contentId, comment);
            }
        });
    }

    public Future<CommentRemoveResult> removeComment(Executor executor, final Long commentId) {
        return executeWithAuthentication(executor, config, RETRY_COUNT, new Callable<Api<CommentRemoveResult>>() {
            @Override
            public Api<CommentRemoveResult> call() throws Exception {
                return new RemoveCommentApi(context, account.getAuthKey(), commentId);
            }
        });
    }

}
