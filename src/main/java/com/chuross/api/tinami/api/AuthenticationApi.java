package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Authentication;
import com.chuross.api.tinami.result.AuthenticationResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class AuthenticationApi extends PostApi<AuthenticationResult> {

    private String email;
    private String password;

    public AuthenticationApi(Context context, String email, String password) {
        super(context);
        this.email = email;
        this.password = password;
    }

    @Override
    protected String getUrl() {
        return getContext().getSecureUrl("/auth");
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("password", password));
    }

    @Override
    protected AuthenticationResult convert(HttpResponse response) throws Exception {
        Authentication authentication = XmlUtils.read(Authentication.class, response.getContentsAsString(), false);
        return new AuthenticationResult(response.getStatus(), response.getHeaders(), authentication);
    }
}
