package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Authentication;
import com.chuross.api.tinami.result.AuthenticationResult;
import com.chuross.common.library.api.PostRequestApi;
import com.chuross.common.library.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.simpleframework.xml.core.Persister;

import java.util.List;

class AuthenticationApi extends PostRequestApi<AuthenticationResult> {

    private Context context;
    private String email;
    private String password;

    public AuthenticationApi(Context context, String email, String password) {
        this.context = context;
        this.email = email;
        this.password = password;
    }

    @Override
    protected String getUrl() {
        return context.getSecureUrl("/auth");
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        nameValuePairs.add(new BasicNameValuePair("api_key", context.getApiKey()));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected AuthenticationResult convert(HttpResponse response) throws Exception {
        Authentication authentication = new Persister().read(Authentication.class, response.getContentsAsString("UTF-8"));
        return new AuthenticationResult(response.getStatus(), authentication);
    }
}
