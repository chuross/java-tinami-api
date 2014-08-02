package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.Search;
import com.chuross.api.tinami.parameter.SearchParameter;
import com.chuross.api.tinami.result.SearchResult;
import com.chuross.common.library.api.GetRequestApi;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

class SearchApi extends GetRequestApi<SearchResult> {

    private Context context;
    private String authKey;
    private SearchParameter searchParameters;

    public SearchApi(Context context, String authKey, SearchParameter searchParameters) {
        this.context = context;
        this.authKey = authKey;
        this.searchParameters = searchParameters;
    }

    @Override
    protected String getUrl() {
        return context.getUrl("/content/search");
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        nameValuePairs.add(new BasicNameValuePair("api_key", context.getApiKey()));
        nameValuePairs.addAll(searchParameters.getParameters());
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected SearchResult convert(HttpResponse response) throws Exception {
        Search search = XmlUtils.read(Search.class, response.getContentsAsString());
        return new SearchResult(response.getStatus(), search);
    }

}
