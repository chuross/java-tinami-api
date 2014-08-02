package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.result.AbstractResult;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public abstract class AbstractPagingContentListApi<T extends AbstractResult<?>> extends AbstractPagingApi<T> {

    private boolean safe;

    public AbstractPagingContentListApi(Context context, int page, int perpage, boolean safe) {
        this(context, null, page, perpage, safe);
    }

    public AbstractPagingContentListApi(Context context, String authKey, int page, int perpage, boolean safe) {
        super(context, authKey, page, perpage);
        this.safe = safe;
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair("safe", safe ? "1" : "0"));
    }
}
