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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
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

        addResponse(pattern, getResponse(200, "/testdata/authentication/success.xml"));

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

        addResponse(pattern, getResponse(200, "/testdata/authentication/fail.xml"));

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

        addResponse(pattern, getResponse(200, "/testdata/response/success.xml"));

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

        addResponse(pattern, getResponse(200, "/testdata/user_info/success_user.xml"));

        UserInfoResult result = api.userInfo(MoreExecutors.sameThreadExecutor()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        UserInfo info = result.getResult();
        assertThat(info.getStatus(), is("ok"));
        assertThat(info.getError(), nullValue());
        assertThat(info.getUser().getId(), is(12345L));
        assertThat(info.getCreator(), nullValue());
    }

    @Test
    public void クリエイター情報付のユーザー情報が取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        RequestPattern pattern = new RequestPattern("/login/info", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/user_info/success_creator.xml"));

        UserInfoResult result = api.userInfo(MoreExecutors.sameThreadExecutor()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        UserInfo info = result.getResult();
        assertThat(info.getStatus(), is("ok"));
        assertThat(info.getError(), nullValue());
        assertThat(info.getUser().getId(), is(12345L));
        assertThat(info.getCreator().getId(), is(6789L));
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

        addResponse(pattern, getResponse(200, "/testdata/contentlist/success_multi.xml"));

        SearchResult result = api.search(MoreExecutors.sameThreadExecutor(), new SearchParameterBuilder().setText("keyword").build()).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        ContentList contentList = result.getResult();
        assertThat(contentList.getStatus(), is("ok"));
        assertThat(contentList.getError(), nullValue());

        Contents contents = contentList.getContents();
        assertThat(contents.getTotal(), is(486402L));
        assertThat(contents.getPage(), is(1));
        assertThat(contents.getPages(), is(162134));
        assertThat(contents.getPerpage(), is(3));

        List<Content> list = contents.getList();
        assertThat(list.size(), is(3));
        assertThat(list.get(0).getId(), is(123456L));
        assertThat(list.get(0).getType(), is("illust"));
        assertThat(list.get(0).getTitle(), is("作品タイトル"));
        assertThat(list.get(0).getViewLevel(), is(ViewLevel.PUBLIC));
        assertThat(list.get(0).getAgeLevel(), is(1));
        assertThat(list.get(0).getThumbnails().size(), is(1));
        assertThat(list.get(0).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/1.gif"));
        assertThat(list.get(0).getThumbnails().get(0).getWidth(), is(112));
        assertThat(list.get(0).getThumbnails().get(0).getHeight(), is(120));

        assertThat(list.get(1).getId(), is(456789L));
        assertThat(list.get(1).getType(), is("illust"));
        assertThat(list.get(1).getTitle(), is("作品タイトル2"));
        assertThat(list.get(1).getViewLevel(), is(ViewLevel.USER));
        assertThat(list.get(1).getAgeLevel(), is(2));
        assertThat(list.get(1).getThumbnails().size(), is(1));
        assertThat(list.get(1).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/2.gif"));
        assertThat(list.get(1).getThumbnails().get(0).getWidth(), is(112));
        assertThat(list.get(1).getThumbnails().get(0).getHeight(), is(120));

        assertThat(list.get(2).getId(), is(101112L));
        assertThat(list.get(2).getType(), is("illust"));
        assertThat(list.get(2).getTitle(), is("作品タイトル3"));
        assertThat(list.get(2).getViewLevel(), is(ViewLevel.SUPPORTER));
        assertThat(list.get(2).getAgeLevel(), is(3));
        assertThat(list.get(2).getThumbnails().size(), is(1));
        assertThat(list.get(2).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/3.gif"));
        assertThat(list.get(2).getThumbnails().get(0).getWidth(), is(112));
        assertThat(list.get(2).getThumbnails().get(0).getHeight(), is(120));
    }

    @Test
    public void お気に入りクリエイターの作品が取得できる() throws Exception {
        ページングコンテンツリストを取得できる("/bookmark/content/list", new Callable<Future<BookmarkContentsResult>>() {
            @Override
            public Future<BookmarkContentsResult> call() throws Exception {
                return api.bookmarkContents(MoreExecutors.sameThreadExecutor(), 1, 20, false);
            }
        });
    }

    @Test
    public void お気に入りクリエイターが取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("page", "1"));
        parameters.add(new BasicNameValuePair("perpage", "20"));
        RequestPattern pattern = new RequestPattern("/bookmark/list", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/creatorlist/success_multi.xml"));

        BookmarkCreatorsResult result = api.bookmarkCreators(MoreExecutors.sameThreadExecutor(), 1, 20).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        CreatorList creatorList = result.getResult();
        assertThat(creatorList.getStatus(), is("ok"));
        assertThat(creatorList.getError(), nullValue());

        Creators creators = creatorList.getCreators();
        assertThat(creators.getTotal(), is(2L));
        assertThat(creators.getPage(), is(1));
        assertThat(creators.getPages(), is(1));
        assertThat(creators.getPerpage(), is(20));

        List<Creator> list = creators.getList();
        assertThat(list.size(), is(2));

        assertThat(list.get(0).getId(), is(51946L));
        assertThat(list.get(0).getName(), is("ラムネ"));
        assertThat(list.get(0).getThumbnailUrl(), is("http://img.tinami.com/supporter/profile/68/sp00272568_45b606019e24ab289a8d269140f0186a.png"));

        assertThat(list.get(1).getId(), is(10L));
        assertThat(list.get(1).getName(), is("TINAMIの中の人"));
        assertThat(list.get(1).getThumbnailUrl(), is("http://www.tinami.com/supporter/images/profile/sp00000010_dc8dfb2063d236fc1832223c5de23760.gif"));
    }

    @Test
    public void お気に入りクリエイターを追加できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("prof_id", "1234567890"));
        RequestPattern pattern = new RequestPattern("/bookmark/add", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/response/success.xml"));

        AppendBookmarkCreatorsResult result = api.appendBookmarkCreators(MoreExecutors.sameThreadExecutor(), 1234567890L).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        com.chuross.api.tinami.element.Response responseElement = result.getResult();
        assertThat(responseElement.getStatus(), is("ok"));
    }

    @Test
    public void 友達が支援した作品を取得できる() throws Exception {
        ページングコンテンツリストを取得できる("/friend/recommend/content/list", new Callable<Future<FriendRecommendResult>>() {
            @Override
            public Future<FriendRecommendResult> call() throws Exception {
                return api.friendRecommend(MoreExecutors.sameThreadExecutor(), 1, 20, false);
            }
        });
    }

    @Test
    public void ウォッチキーワードの作品を取得できる() throws Exception {
        ページングコンテンツリストを取得できる("/watchkeyword/content/list", new Callable<Future<WatchKeywordResult>>() {
            @Override
            public Future<WatchKeywordResult> call() throws Exception {
                return api.watchKeyword(MoreExecutors.sameThreadExecutor(), 1, 20, false);
            }
        });
    }

    @Test
    public void コレクションを取得できる() throws Exception {
        ページングコンテンツリストを取得できる("/collection/list", new Callable<Future<CollectionResult>>() {
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

        addResponse(pattern, getResponse(200, "/testdata/response/success.xml"));

        AppendCollectionResult result = api.appendCollection(MoreExecutors.sameThreadExecutor(), 1234567890L).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        com.chuross.api.tinami.element.Response responseElement = result.getResult();
        assertThat(responseElement.getStatus(), is("ok"));
    }

    @Test
    public void ランキングを取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("category", "1"));
        コンテンツリストを取得できる("/ranking", parameters, new Callable<Future<RankingResult>>() {
            @Override
            public Future<RankingResult> call() throws Exception {
                return api.ranking(MoreExecutors.sameThreadExecutor(), com.chuross.api.tinami.parameter.ContentType.ILLUST);
            }
        });
    }

    @Test
    public void 作品情報を取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("cont_id", "123456789"));
        parameters.add(new BasicNameValuePair("dates", "1"));
        parameters.add(new BasicNameValuePair("models", "0"));
        RequestPattern pattern = new RequestPattern("/content/info", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/content_info/success.xml"));

        ContentInfoResult result = api.contentInfo(MoreExecutors.sameThreadExecutor(), 123456789L).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        ContentInfo info = result.getResult();
        assertThat(info.getStatus(), is("ok"));
        assertThat(info.getError(), nullValue());

        Content content = info.getContent();
        assertThat(content.getType(), is("illust"));
        assertThat(content.isSupported(), is(true));
        assertThat(content.isCollectionAppended(), is(false));
        assertThat(content.getTitle(), is("ある王国の物語"));
        assertThat(content.getCreator().getId(), is(17527L));
        assertThat(content.getCreator().getName(), is("文月椎野"));
        assertThat(content.getCreator().getThumbnailUrl(), is("http://img.tinami.com/supporter/profile/33/sp00105233_924cc0b5abb9ab91e709707151291f1a.jpg"));
        assertThat(content.getAgeLevel(), is(1));
        assertThat(content.getDescription(), is("遠い遠い、誰も知らない王国のお姫様と、反乱を目論む騎士団に属する一人の青年のお話。"));
        assertThat(content.getThumbnails().size(), is(1));
        assertThat(content.getThumbnails().get(0).getUrl(), is("http://img.tinami.com/illust2/A/635/53dbc167d2f51.gif"));
        assertThat(content.getThumbnails().get(0).getWidth(), is(106));
        assertThat(content.getThumbnails().get(0).getHeight(), is(150));
        assertThat(content.getImage().getUrl(), is("http://api.tinami.com/image?api_key=hoge&cont_id=705561"));
        assertThat(content.getImage().getWidth(), is(707));
        assertThat(content.getImage().getHeight(), is(1000));
        assertThat(content.getTotalViewCount(), is(61));
        assertThat(content.getUserViewCount(), is(60));
        assertThat(content.getValuation(), is(0));
        assertThat(content.getTags().size(), is(4));
        assertThat(content.getTags().get(0), is("オリキャラ"));
        assertThat(content.getTags().get(1), is("王国"));
        assertThat(content.getTags().get(2), is("姫"));
        assertThat(content.getTags().get(3), is("騎士"));
        assertThat(content.getCreatedAt(), is(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPAN).parse("2014-08-02 01:33:42")));
    }

    @Test
    public void クリエイター情報を取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("prof_id", "123456789"));
        RequestPattern pattern = new RequestPattern("/creator/info", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/creator_info/success.xml"));

        CreatorInfoResult result = api.creatorInfo(MoreExecutors.sameThreadExecutor(), 123456789L).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        CreatorInfo info = result.getResult();
        assertThat(info.getStatus(), is("ok"));
        assertThat(info.getError(), nullValue());

        Creator creator = info.getCreator();
        assertThat(creator.getName(), is("hoge"));
        assertThat(creator.getThumbnailUrl(), is("http://hogefuga.com"));
        assertThat(creator.isBookmarkAppended(), is(true));
    }

    @Test
    public void コメントを取得できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("cont_id", "123456789"));
        RequestPattern pattern = new RequestPattern("/content/comment/list", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/comment_list/success.xml"));

        CommentListResult result = api.comments(MoreExecutors.sameThreadExecutor(), 123456789L).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        CommentList list = result.getResult();
        assertThat(list.getStatus(), is("ok"));
        assertThat(list.getError(), nullValue());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN);

        List<Comment> comments = list.getComments();
        assertThat(comments.size(), is(3));
        assertThat(comments.get(0).getId(), is(123L));
        assertThat(comments.get(0).getUserName(), is("ユーザー１"));
        assertThat(comments.get(0).getCreatedAt(), is(format.parse("2010-03-15 16:55")));
        assertThat(comments.get(0).getBody(), is("コメント１"));
        assertThat(comments.get(1).getId(), is(456L));
        assertThat(comments.get(1).getUserName(), is("ユーザー２"));
        assertThat(comments.get(1).getCreatedAt(), is(format.parse("2010-03-14 12:03")));
        assertThat(comments.get(1).getBody(), is("コメント２"));
        assertThat(comments.get(2).getId(), is(789L));
        assertThat(comments.get(2).getUserName(), is("ユーザー３"));
        assertThat(comments.get(2).getCreatedAt(), is(format.parse("2010-02-24 16:25")));
        assertThat(comments.get(2).getBody(), is("コメント３"));
    }

    @Test
    public void コメントを追加できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("cont_id", "123456789"));
        parameters.add(new BasicNameValuePair("comment", "これコメント"));
        RequestPattern pattern = new RequestPattern("/content/comment/add", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/response/success.xml"));

        AppendCommentResult result = api.appendComment(MoreExecutors.sameThreadExecutor(), 123456789L, "これコメント").get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        assertThat(result.getResult().getStatus(), is("ok"));
        assertThat(result.getResult().getError(), nullValue());
    }

    @Test
    public void コメントを削除できる() throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("comment_id", "123456789"));
        RequestPattern pattern = new RequestPattern("/content/comment/remove", parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/response/success.xml"));

        RemoveCommentResult result = api.removeComment(MoreExecutors.sameThreadExecutor(), 123456789L).get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        assertThat(result.getResult().getStatus(), is("ok"));
        assertThat(result.getResult().getError(), nullValue());
    }

    private <T extends AbstractResult<ContentList>> void ページングコンテンツリストを取得できる(String path, Callable<Future<T>> apiCallable) throws Exception {
        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("api_key", "mock"));
        parameters.add(new BasicNameValuePair("auth_key", "piyo"));
        parameters.add(new BasicNameValuePair("page", "1"));
        parameters.add(new BasicNameValuePair("perpage", "20"));
        parameters.add(new BasicNameValuePair("safe", "0"));
        コンテンツリストを取得できる(path, parameters, apiCallable);
    }

    private <T extends AbstractResult<ContentList>> void コンテンツリストを取得できる(String path, List<NameValuePair> parameters, Callable<Future<T>> apiCallable) throws Exception {
        RequestPattern pattern = new RequestPattern(path, parameters, null);

        addResponse(pattern, getResponse(200, "/testdata/contentlist/success_single.xml"));

        T result = apiCallable.call().get();
        assertThat(result.getStatus(), is(200));
        assertThat(result.isSuccess(), is(true));

        ContentList contentList = result.getResult();
        assertThat(contentList.getStatus(), is("ok"));
        assertThat(contentList.getError(), nullValue());

        Contents contents = contentList.getContents();
        assertThat(contents.getTotal(), is(1L));
        assertThat(contents.getPage(), is(1));
        assertThat(contents.getPages(), is(1));
        assertThat(contents.getPerpage(), is(20));

        List<Content> list = contents.getList();
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getId(), is(123456L));
        assertThat(list.get(0).getType(), is("illust"));
        assertThat(list.get(0).getTitle(), is("作品タイトル"));
        assertThat(list.get(0).getViewLevel(), is(ViewLevel.PUBLIC));
        assertThat(list.get(0).getAgeLevel(), is(1));
        assertThat(list.get(0).getThumbnails().size(), is(1));
        assertThat(list.get(0).getThumbnails().get(0).getUrl(), is("http://img.tinami.com/1.gif"));
        assertThat(list.get(0).getThumbnails().get(0).getWidth(), is(112));
        assertThat(list.get(0).getThumbnails().get(0).getHeight(), is(120));
    }

    private Response getResponse(int status, String filePath) throws Exception {
        String body = IOUtils.toString(getClass().getResourceAsStream(filePath));
        return new Response(status, body, ENCODING, CONTENT_TYPE, null);
    }

}
