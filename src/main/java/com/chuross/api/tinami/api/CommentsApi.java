package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.CommentList;
import com.chuross.api.tinami.element.transformer.DateformatTransformer;
import com.chuross.api.tinami.result.CommentsResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class CommentsApi extends GetApi<CommentsResult> {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPAN);
    private Long contentId;

    public CommentsApi(Context context, Long contentId) {
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
        addParameter(nameValuePairs, "cont_id", contentId);
    }

    @Override
    protected CommentsResult convert(HttpResponse response) throws Exception {
        RegistryMatcher matcher = new RegistryMatcher();
        matcher.bind(Date.class, new DateformatTransformer(FORMAT));
        CommentList list = XmlUtils.read(new Persister(matcher), CommentList.class, response.getContentsAsString(), false);
        return new CommentsResult(response.getStatus(), response.getHeaders(), list);
    }

}
