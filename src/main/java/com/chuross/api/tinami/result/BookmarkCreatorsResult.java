package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.CreatorList;
import org.apache.http.Header;

import java.util.List;

public class BookmarkCreatorsResult extends AbstractAuthenticatedResult<CreatorList> {

    public BookmarkCreatorsResult(int status, List<Header> headers, CreatorList result) {
        super(status, headers, result);
    }

}
