package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentList;
import com.chuross.api.tinami.parameter.ContentType;
import com.chuross.api.tinami.result.RankingResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class RankingApi extends GetRequestApi<RankingResult> {

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
        nameValuePairs.add(new BasicNameValuePair("category", String.valueOf(contentType.getCode())));
    }

    @Override
    protected RankingResult convert(HttpResponse response) throws Exception {
        ContentList list = XmlUtils.read(ContentList.class, response.getContentsAsString());
        return new RankingResult(response.getStatus(), list);
    }
}
