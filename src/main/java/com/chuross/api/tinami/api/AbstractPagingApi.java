package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.result.AbstractResult;
import org.apache.http.NameValuePair;

import java.util.List;

abstract class AbstractPagingApi<T extends AbstractResult<?>> extends GetApi<T> {

    private Integer page;
    private Integer perpage;

    public AbstractPagingApi(Context context, Integer page, Integer perpage) {
        this(context, null, page, perpage);
    }

    public AbstractPagingApi(Context context, String authKey, Integer page, Integer perpage) {
        super(context, authKey);
        this.page = page;
        this.perpage = perpage;
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        addParameterIfNotNull(nameValuePairs, "page", page);
        addParameterIfNotNull(nameValuePairs, "perpage", perpage);
    }
}
