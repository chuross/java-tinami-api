package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Response;
import com.chuross.api.tinami.result.CollectionAddResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.List;

class CollectionAddApi extends GetApi<CollectionAddResult> {

    private Long contentId;

    public CollectionAddApi(Context context, String authKey, Long contentId) {
        super(context, authKey);
        this.contentId = contentId;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/collection/add");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        addParameter(nameValuePairs, "cont_id", contentId);
    }

    @Override
    protected CollectionAddResult convert(HttpResponse response) throws Exception {
        Response responseElement = XmlUtils.read(Response.class, response.getContentsAsString(), false);
        return new CollectionAddResult(response.getStatus(), response.getHeaders(), responseElement);
    }
}
