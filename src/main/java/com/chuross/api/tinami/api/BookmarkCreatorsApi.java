package com.chuross.api.tinami.api;

import com.chuross.api.tinami.Context;
import com.chuross.api.tinami.element.CreatorList;
import com.chuross.api.tinami.result.BookmarkCreatorsResult;
import com.chuross.common.library.http.HttpResponse;
import com.chuross.common.library.util.XmlUtils;
import org.apache.http.Header;

import java.util.List;

public class BookmarkCreatorsApi extends AbstractPagingApi<BookmarkCreatorsResult> {


    public BookmarkCreatorsApi(Context context, String authKey, int page, int perpage) {
        super(context, authKey, page, perpage);
    }

    @Override
    protected String getUrl() {
        return getContext().getUrl("/bookmark/list");
    }

    @Override
    protected void setRequestHeaders(List<Header> headers) {
    }

    @Override
    protected BookmarkCreatorsResult convert(HttpResponse response) throws Exception {
        CreatorList creatorList = XmlUtils.read(CreatorList.class, response.getContentsAsString());
        return new BookmarkCreatorsResult(response.getStatus(), creatorList);
    }

}
