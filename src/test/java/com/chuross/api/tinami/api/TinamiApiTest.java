package com.chuross.api.tinami.api;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.chuross.api.tinami.Account;
import com.chuross.api.tinami.MockContext;
import com.chuross.api.tinami.ViewLevel;
import com.chuross.api.tinami.element.*;
import com.chuross.api.tinami.parameter.SearchParameterBuilder;
import com.chuross.api.tinami.result.*;
import com.chuross.testcase.http.HttpRequestTestCase;
import com.chuross.testcase.http.RequestPattern;
import com.chuross.testcase.http.Response;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class TinamiApiTest extends HttpRequestTestCase {

    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = ContentType.APPLICATION_XML.getMimeType();

    private TinamiApi api;
    private Account account = new Account("hoge", "fuga", "piyo");

    @Before
    public void before() {
        api = new TinamiApi(new MockContext(URL, "mock"), account, null);
    }

    @Test
    public void 認証ができる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("email", "hoge"));
        parameters.add(new BasicNameValuePair("password", "fuga"));
        RequestPattern pattern = new RequestPattern("/auth", parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/authentication/success.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        AuthenticationResult result = api.login(MoreExecutors.sameThreadExecutor()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        Authentication authentication = result.getResult();
        assertThat(authentication.getStatus(), is("ok"));
        assertThat(authentication.getError(), nullValue());
        assertThat(authentication.getAuthKey(), is("06f7d3cff81ae8e70543b9fd433f9958"));
    }

    @Test
    public void 認証エラーを取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("email", "hoge"));
        parameters.add(new BasicNameValuePair("password", "fuga"));
        RequestPattern pattern = new RequestPattern("/auth", parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/authentication/fail.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        AuthenticationResult result = api.login(MoreExecutors.sameThreadExecutor()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(false));
        assertThat(result.isLoginFailed(), is(true));

        Authentication authentication = result.getResult();
        assertThat(authentication.getStatus(), is("fail"));
        assertThat(authentication.getError().getMessage(), is("Login failed "));
    }

    @Test
    public void ログアウトできる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        RequestPattern pattern = new RequestPattern("/logout", parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/response/success.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        LogoutResult result = api.logout(MoreExecutors.sameThreadExecutor()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        com.chuross.api.tinami.element.Response logout = result.getResult();
        assertThat(logout.getStatus(), is("ok"));
    }

    @Test
    public void ユーザー情報が取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        RequestPattern pattern = new RequestPattern("/login/info", parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/user_info/success_user.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        UserInfoResult result = api.userInfo(MoreExecutors.sameThreadExecutor()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        UserInfo info = result.getResult();
        assertThat(info.getStatus(), is("ok"));
        assertThat(info.getError(), nullValue());
        assertThat(info.getUser().getId(), is("12345"));
        assertThat(info.getCreator(), nullValue());
    }

    @Test
    public void クリエイター情報が取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        RequestPattern pattern = new RequestPattern("/login/info", parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/user_info/success_creator.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        UserInfoResult result = api.userInfo(MoreExecutors.sameThreadExecutor()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        UserInfo info = result.getResult();
        assertThat(info.getStatus(), is("ok"));
        assertThat(info.getError(), nullValue());
        assertThat(info.getUser().getId(), is("12345"));
        assertThat(info.getCreator().getId(), is("6789"));
    }

    @Test
    public void 検索ができる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("text", "keyword"));
        parameters.add(new BasicNameValuePair("safe", "0"));
        parameters.add(new BasicNameValuePair("page", "1"));
        parameters.add(new BasicNameValuePair("perpage", "1"));
        RequestPattern pattern = new RequestPattern("/content/search", parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/contentlist/success_multi_contents.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        SearchResult result = api.search(MoreExecutors.sameThreadExecutor(), new SearchParameterBuilder().setText("keyword").build()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        ContentList list = result.getResult();
        assertThat(list.getStatus(), is("ok"));
        assertThat(list.getError(), nullValue());

        List<Content> contents = list.getContents();
        assertThat(contents.size(), is(3));

        assertThat(contents.get(0).getId(), is(123456L));
        assertThat(contents.get(0).getType(), is("illust"));
        assertThat(contents.get(0).getTitle(), is("作品タイトル"));
        assertThat(contents.get(0).getViewLevel(), is(ViewLevel.PUBLIC));
        assertThat(contents.get(0).getAgeLevel(), is(1));
        assertThat(contents.get(0).getThumbnails().size(), is(1));
        assertThat(contents.get(0).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/1.gif"));
        assertThat(contents.get(0).getThumbnails().get(0).getWidth(), is(112));
        assertThat(contents.get(0).getThumbnails().get(0).getHeight(), is(120));

        assertThat(contents.get(1).getId(), is(456789L));
        assertThat(contents.get(1).getType(), is("illust"));
        assertThat(contents.get(1).getTitle(), is("作品タイトル2"));
        assertThat(contents.get(1).getViewLevel(), is(ViewLevel.USER));
        assertThat(contents.get(1).getAgeLevel(), is(2));
        assertThat(contents.get(1).getThumbnails().size(), is(1));
        assertThat(contents.get(1).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/2.gif"));
        assertThat(contents.get(1).getThumbnails().get(0).getWidth(), is(112));
        assertThat(contents.get(1).getThumbnails().get(0).getHeight(), is(120));

        assertThat(contents.get(2).getId(), is(101112L));
        assertThat(contents.get(2).getType(), is("illust"));
        assertThat(contents.get(2).getTitle(), is("作品タイトル3"));
        assertThat(contents.get(2).getViewLevel(), is(ViewLevel.SUPPORTER));
        assertThat(contents.get(2).getAgeLevel(), is(3));
        assertThat(contents.get(2).getThumbnails().size(), is(1));
        assertThat(contents.get(2).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/3.gif"));
        assertThat(contents.get(2).getThumbnails().get(0).getWidth(), is(112));
        assertThat(contents.get(2).getThumbnails().get(0).getHeight(), is(120));
    }

    @Test
    public void お気に入りクリエイターの作品が取得できる() throws Exception {
        コンテンツリストを取得できる("/bookmark/content/list", new Callable<Future<BookmarkContentResult>>() {
            @Override
            public Future<BookmarkContentResult> call() throws Exception {
                return api.bookmarkContents(MoreExecutors.sameThreadExecutor(), 1, 20, false);
            }
        });
    }

    @Test
    public void 友達が支援した作品を取得できる() throws Exception {
        コンテンツリストを取得できる("/friend/recommend/content/list", new Callable<Future<FriendRecommendResult>>() {
            @Override
            public Future<FriendRecommendResult> call() throws Exception {
                return api.friendRecommend(MoreExecutors.sameThreadExecutor(), 1, 20, false);
            }
        });
    }

    @Test
    public void ウォッチキーワードの作品を取得できる() throws Exception {
        コンテンツリストを取得できる("/watchkeyword/content/list", new Callable<Future<WatchKeywordResult>>() {
            @Override
            public Future<WatchKeywordResult> call() throws Exception {
                return api.watchKeyword(MoreExecutors.sameThreadExecutor(), 1, 20, false);
            }
        });
    }

    @Test
    public void コレクションを取得できる() throws Exception {
        コンテンツリストを取得できる("/collection/list", new Callable<Future<CollectionResult>>() {
            @Override
            public Future<CollectionResult> call() throws Exception {
                return api.collections(MoreExecutors.sameThreadExecutor(), 1, 20, false);
            }
        });
    }

    @Test
    public void コレクションに追加できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("cont_id", "1234567890"));
        RequestPattern pattern = new RequestPattern("/collection/add", parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/response/success.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        AppendCollectionResult result = api.appendCollection(MoreExecutors.sameThreadExecutor(), 1234567890L).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        com.chuross.api.tinami.element.Response logout = result.getResult();
        assertThat(logout.getStatus(), is("ok"));
    }

    private <T extends AbstractResult<ContentList>> void コンテンツリストを取得できる(String path, Callable<Future<T>> apiCallable) throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("page", "1"));
        parameters.add(new BasicNameValuePair("perpage", "20"));
        parameters.add(new BasicNameValuePair("safe", "0"));
        RequestPattern pattern = new RequestPattern(path, parameters, null);

        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/contentlist/success_single_contents.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        addResponse(pattern, response);

        T result = apiCallable.call().get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        ContentList list = result.getResult();
        assertThat(list.getStatus(), is("ok"));
        assertThat(list.getError(), nullValue());

        List<Content> contents = list.getContents();
        assertThat(contents.size(), is(1));

        assertThat(contents.get(0).getId(), is(123456L));
        assertThat(contents.get(0).getType(), is("illust"));
        assertThat(contents.get(0).getTitle(), is("作品タイトル"));
        assertThat(contents.get(0).getViewLevel(), is(ViewLevel.PUBLIC));
        assertThat(contents.get(0).getAgeLevel(), is(1));
        assertThat(contents.get(0).getThumbnails().size(), is(1));
        assertThat(contents.get(0).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/1.gif"));
        assertThat(contents.get(0).getThumbnails().get(0).getWidth(), is(112));
        assertThat(contents.get(0).getThumbnails().get(0).getHeight(), is(120));
    }

}
