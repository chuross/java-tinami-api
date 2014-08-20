package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentInfo;
import com.chuross.api.tinami.result.ContentInfoResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class ContentInfoApi extends GetApi<ContentInfoResult> {

    private long contentId;

    public ContentInfoApi(Context context, String authKey, long contentId) {
        super(context, authKey);
        this.contentId = contentId;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/content/info");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("cont_id", String.valueOf(contentId)));
        nameValuePairs.add(new BasicNameValuePair("dates", "1"));
        nameValuePairs.add(new BasicNameValuePair("models", "0"));
    }

    @Override
    protected ContentInfoResult convert(HttpResponse response) throws Exception {
        ContentInfo info = XmlUtils.read(ContentInfo.class, response.getContentsAsString(), false);
        return new ContentInfoResult(response.getStatus(), response.getHeaders(), info);
    }

}
