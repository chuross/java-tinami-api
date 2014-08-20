package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.ContentList;
import com.chuross.api.tinami.parameter.SearchParameter;
import com.chuross.api.tinami.result.SearchResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.List;

class SearchApi extends GetApi<SearchResult> {

    private SearchParameter searchParameters;

    public SearchApi(Context context, String authKey, SearchParameter searchParameters) {
        super(context, authKey);
        this.searchParameters = searchParameters;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/content/search");
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.addAll(searchParameters.getParameters());
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected SearchResult convert(HttpResponse response) throws Exception {
        ContentList list = XmlUtils.read(ContentList.class, response.getContentsAsString(), false);
        return new SearchResult(response.getStatus(), response.getHeaders(), list);
    }

}
