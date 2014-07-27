package com.chuross.api.tinami.api;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.chuross.api.tinami.MockContext;
import com.chuross.api.tinami.element.Authentication;
import com.chuross.api.tinami.result.AuthenticationResult;
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

public class TinamiApiTest extends HttpRequestTestCase {

    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = ContentType.APPLICATION_XML.getMimeType();

    private TinamiApi api;

    @Before
    public void before() {
        api = new TinamiApi(new MockContext(URL));
    }

    @Test
    public void 認証ができる() throws Exception {
        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/authentication/success.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("email", "hoge"));
        parameters.add(new BasicNameValuePair("password", "fuga"));
        RequestPattern pattern = new RequestPattern("/auth", parameters, null);

        addResponse(pattern, response);

        AuthenticationResult result = api.authenticate(MoreExecutors.sameThreadExecutor(), "hoge", "fuga").get();
        assertThat(result, notNullValue());
        assertThat(result.isSuccess(), is(true));
        assertThat(result.getStatus(), is(200));

        Authentication authentication = result.getResult();
        assertThat(authentication.getStatus(), is("ok"));
        assertThat(authentication.getError(), nullValue());
        assertThat(authentication.getAuthKey(), is("06f7d3cff81ae8e70543b9fd433f9958"));
    }

    @Test
    public void 認証エラーを取得できる() throws Exception {
        String body = IOUtils.toString(getClass().getResourceAsStream("/testdata/authentication/fail.xml"));
        Response response = new Response(200, body, ENCODING, CONTENT_TYPE, null);

        List<NameValuePair> parameters = Lists.newArrayList();
        parameters.add(new BasicNameValuePair("email", "hoge"));
        parameters.add(new BasicNameValuePair("password", "fuga"));
        RequestPattern pattern = new RequestPattern("/auth", parameters, null);

        addResponse(pattern, response);

        AuthenticationResult result = api.authenticate(MoreExecutors.sameThreadExecutor(), "hoge", "fuga").get();
        assertThat(result, notNullValue());
        assertThat(result.isSuccess(), is(false));
        assertThat(result.getStatus(), is(200));
        assertThat(result.isLoginFailed(), is(true));

        Authentication authentication = result.getResult();
        assertThat(authentication.getStatus(), is("fail"));
        assertThat(authentication.getError().getMessage(), is("Login failed "));
    }

}
