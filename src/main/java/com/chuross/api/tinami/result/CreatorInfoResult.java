package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.CreatorInfo;

public class CreatorInfoResult extends AbstractAuthenticatedResult<CreatorInfo> {

    public CreatorInfoResult(int status, CreatorInfo result) {
        super(status, result);
    }

}
