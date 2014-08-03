package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.CommentList;
import com.chuross.api.tinami.result.CommentListResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class CommentsApi extends GetRequestApi<CommentListResult> {

    private long contentId;

    public CommentsApi(Context context, long contentId) {
        super(context);
        this.contentId = contentId;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/content/comment/list");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("cont_id", String.valueOf(contentId)));
    }

    @Override
    protected CommentListResult convert(HttpResponse response) throws Exception {
        CommentList list = XmlUtils.read(CommentList.class, response.getContentsAsString(), false);
        return new CommentListResult(response.getStatus(), list);
    }

}
