package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.result.AbstractResult;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

abstract class AbstractPagingApi<T extends AbstractResult<?>> extends GetRequestApi<T> {

    private int page;
    private int perpage;

    public AbstractPagingApi(Context context, int page, int perpage) {
        this(context, null, page, perpage);
    }

    public AbstractPagingApi(Context context, String authKey, int page, int perpage) {
        super(context, authKey);
        this.page = page;
        this.perpage = perpage;
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("page", String.valueOf(page > 0 ? page : 1)));
        nameValuePairs.add(new BasicNameValuePair("perpage", String.valueOf(perpage > 0 ? perpage : 1)));
    }
}
