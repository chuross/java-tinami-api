package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Response;
import com.chuross.api.tinami.result.CommentRemoveResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.List;

class RemoveCommentApi extends GetApi<CommentRemoveResult> {

    private long commentId;

    public RemoveCommentApi(Context context, String authKey, long commentId) {
        super(context, authKey);
        this.commentId = commentId;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/content/comment/remove");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        addParameterIfNotNull(nameValuePairs, "comment_id", commentId);
    }

    @Override
    protected CommentRemoveResult convert(HttpResponse response) throws Exception {
        Response responseElement = XmlUtils.read(Response.class, response.getContentsAsString(), false);
        return new CommentRemoveResult(response.getStatus(), response.getHeaders(), responseElement);
    }

}
