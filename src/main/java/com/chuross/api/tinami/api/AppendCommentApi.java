package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Response;
import com.chuross.api.tinami.result.AppendCommentResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class AppendCommentApi extends GetRequestApi<AppendCommentResult> {

    private long contentId;
    private String comment;

    public AppendCommentApi(Context context, String authKey, long contentId, String comment) {
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
        nameValuePairs.add(new BasicNameValuePair("cont_id", String.valueOf(contentId)));
        nameValuePairs.add(new BasicNameValuePair("comment", comment));
    }

    @Override
    protected AppendCommentResult convert(HttpResponse response) throws Exception {
        Response responseElement = XmlUtils.read(Response.class, response.getContentsAsString());
        return new AppendCommentResult(response.getStatus(), responseElement);
    }
}
