package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentList;
import com.chuross.api.tinami.result.WatchKeywordResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;

import java.util.List;

class WatchKeywordApi extends AbstractPagingContentListApi<WatchKeywordResult> {

    public WatchKeywordApi(Context context, String authKey, int page, int perpage, boolean safe) {
        super(context, authKey, page, perpage, safe);
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/watchkeyword/content/list");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected WatchKeywordResult convert(HttpResponse response) throws Exception {
        ContentList list = XmlUtils.read(ContentList.class, response.getContentsAsString(), false);
        return new WatchKeywordResult(response.getStatus(), response.getHeaders(), list);
    }
}
