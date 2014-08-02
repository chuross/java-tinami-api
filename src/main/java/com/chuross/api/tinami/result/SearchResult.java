package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.Search;

public class SearchResult extends AbstractResult<Search> {

    public SearchResult(int status, Search result) {
        super(status, result);
    }

}
