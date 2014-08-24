package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Response;
import com.chuross.api.tinami.result.CommentAddResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.List;

class CommentAddApi extends GetApi<CommentAddResult> {

    private Long contentId;
    private String comment;

    public CommentAddApi(Context context, String authKey, Long contentId, String comment) {
        super(context, authKey);
        this.contentId = contentId;
        this.comment = comment;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/content/comment/add");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        addParameter(nameValuePairs, "cont_id", contentId);
        addParameter(nameValuePairs, "comment", comment);
    }

    @Override
    protected CommentAddResult convert(HttpResponse response) throws Exception {
        Response responseElement = XmlUtils.read(Response.class, response.getContentsAsString(), false);
        return new CommentAddResult(response.getStatus(), response.getHeaders(), responseElement);
    }
}
