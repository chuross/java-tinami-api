package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.CommentList;
import org.apache.http.Header;

import java.util.List;

public class CommentListResult extends AbstractResult<CommentList> {

    public CommentListResult(int status, List<Header> headers, CommentList result) {
        super(status, headers, result);
    }

}
