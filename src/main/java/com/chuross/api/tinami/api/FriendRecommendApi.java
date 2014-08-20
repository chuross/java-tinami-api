package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentList;
import com.chuross.api.tinami.result.FriendRecommendResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class FriendRecommendApi extends AbstractPagingApi<FriendRecommendResult> {

    private boolean safe;

    public FriendRecommendApi(Context context, String authKey, int page, int perpage, boolean safe) {
        super(context, authKey, page, perpage);
        this.safe = safe;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/friend/recommend/content/list");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("safe", safe ? "1" : "0"));
    }

    @Override
    protected FriendRecommendResult convert(HttpResponse response) throws Exception {
        ContentList list = XmlUtils.read(ContentList.class, response.getContentsAsString(), false);
        return new FriendRecommendResult(response.getStatus(), response.getHeaders(), list);
    }

}
