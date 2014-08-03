package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.CreatorInfo;
import com.chuross.api.tinami.result.CreatorInfoResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class CreatorInfoApi extends GetRequestApi<CreatorInfoResult> {

    private long creatorId;

    public CreatorInfoApi(Context context, String authKey, long creatorId) {
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
        nameValuePairs.add(new BasicNameValuePair("prof_id", String.valueOf(creatorId)));
    }

    @Override
    protected CreatorInfoResult convert(HttpResponse response) throws Exception {
        CreatorInfo info = XmlUtils.read(CreatorInfo.class, response.getContentsAsString());
        return new CreatorInfoResult(response.getStatus(), info);
    }

}
