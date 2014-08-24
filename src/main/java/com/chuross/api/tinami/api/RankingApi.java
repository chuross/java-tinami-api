package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentList;
import com.chuross.api.tinami.parameter.ContentType;
import com.chuross.api.tinami.result.RankingResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.List;

class RankingApi extends GetApi<RankingResult> {

    private ContentType contentType;

    public RankingApi(Context context, ContentType contentType) {
        super(context);
        this.contentType = contentType;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/ranking");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        addParameterIfNotNull(nameValuePairs, "category", contentType != null ? contentType.getCode() : null);
    }

    @Override
    protected RankingResult convert(HttpResponse response) throws Exception {
        ContentList list = XmlUtils.read(ContentList.class, response.getContentsAsString(), false);
        return new RankingResult(response.getStatus(), response.getHeaders(), list);
    }
}
