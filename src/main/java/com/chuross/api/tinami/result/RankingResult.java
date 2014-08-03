package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentList;

public class RankingResult extends AbstractResult<ContentList> {

    public RankingResult(int status, ContentList result) {
        super(status, result);
    }

}
