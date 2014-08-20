package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Response;
import com.chuross.api.tinami.result.BookmarkCreatorsAddResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class BookmarkCreatorsAddApi extends GetApi<BookmarkCreatorsAddResult> {

    private long userId;

    public BookmarkCreatorsAddApi(Context context, String authKey, long userId) {
        super(context, authKey);
        this.userId = userId;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/bookmark/add");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("prof_id", String.valueOf(userId)));
    }

    @Override
    protected BookmarkCreatorsAddResult convert(HttpResponse response) throws Exception {
        Response responseElement = XmlUtils.read(Response.class, response.getContentsAsString(), false);
        return new BookmarkCreatorsAddResult(response.getStatus(), response.getHeaders(), responseElement);
    }

}
