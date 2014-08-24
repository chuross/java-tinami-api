package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentInfo;
import com.chuross.api.tinami.element.transformer.DateformatTransformer;
import com.chuross.api.tinami.result.ContentInfoResult;
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

class ContentInfoApi extends GetApi<ContentInfoResult> {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPAN);

    private Long contentId;

    public ContentInfoApi(Context context, String authKey, Long contentId) {
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
        addParameter(nameValuePairs, "cont_id", contentId);
        addParameter(nameValuePairs, "dates", "1");
        addParameter(nameValuePairs, "models", "0");
    }

    @Override
    protected ContentInfoResult convert(HttpResponse response) throws Exception {
        RegistryMatcher matcher = new RegistryMatcher();
        matcher.bind(Date.class, new DateformatTransformer(FORMAT));
        ContentInfo info = XmlUtils.read(new Persister(matcher), ContentInfo.class, response.getContentsAsString(), false);
        return new ContentInfoResult(response.getStatus(), response.getHeaders(), info);
    }

}
