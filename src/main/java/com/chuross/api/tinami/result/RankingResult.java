package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;
import org.apache.http.Header;

import java.util.List;

public class RankingResult extends AbstractResult<ContentList> {

    public RankingResult(int status, List<Header> headers, ContentList result) {
        super(status, headers, result);
    }

}
