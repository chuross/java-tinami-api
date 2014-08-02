package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentList;
import com.chuross.api.tinami.result.BookmarkContentListResult;
import com.chuross.common.library.api.GetRequestApi;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class BookmarkContentListApi extends GetRequestApi<BookmarkContentListResult> {

    private Context context;
    private String authKey;
    private int page;
    private int perpage;
    private boolean safe;

    public BookmarkContentListApi(Context context, String authKey, int page, int perpage, boolean safe) {
        this.context = context;
        this.authKey = authKey;
        this.page = page;
        this.perpage = perpage;
        this.safe = safe;
    }

    @Override
    protected String getUrl() {
        return context.getUrl("/bookmark/content/list");
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        nameValuePairs.add(new BasicNameValuePair("api_key", context.getApiKey()));
        nameValuePairs.add(new BasicNameValuePair("auth_key", authKey));
        nameValuePairs.add(new BasicNameValuePair("page", String.valueOf(page > 0 ? page : 1)));
        nameValuePairs.add(new BasicNameValuePair("perpage", String.valueOf(perpage > 0 ? perpage : 1)));
        nameValuePairs.add(new BasicNameValuePair("safe", safe ? "1" : "0"));
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected BookmarkContentListResult convert(HttpResponse response) throws Exception {
        ContentList list = XmlUtils.read(ContentList.class, response.getContentsAsString());
        return new BookmarkContentListResult(response.getStatus(), list);
    }
}
