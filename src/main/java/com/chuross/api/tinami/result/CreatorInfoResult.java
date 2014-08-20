package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.CreatorInfo;
import org.apache.http.Header;

import java.util.List;

public class CreatorInfoResult extends AbstractAuthenticatedResult<CreatorInfo> {

    public CreatorInfoResult(int status, List<Header> headers, CreatorInfo result) {
        super(status, headers, result);
    }

}
