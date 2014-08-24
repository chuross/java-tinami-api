package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.CreatorInfo;
import com.chuross.api.tinami.result.CreatorInfoResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.List;

class CreatorInfoApi extends GetApi<CreatorInfoResult> {

    private Long creatorId;

    public CreatorInfoApi(Context context, String authKey, Long creatorId) {
        super(context, authKey);
        this.creatorId = creatorId;
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/creator/info");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected void setParameters(List<NameValuePair> nameValuePairs) {
        super.setParameters(nameValuePairs);
        addParameter(nameValuePairs, "prof_id", creatorId);
    }

    @Override
    protected CreatorInfoResult convert(HttpResponse response) throws Exception {
        CreatorInfo info = XmlUtils.read(CreatorInfo.class, response.getContentsAsString(), false);
        return new CreatorInfoResult(response.getStatus(), response.getHeaders(), info);
    }

}
