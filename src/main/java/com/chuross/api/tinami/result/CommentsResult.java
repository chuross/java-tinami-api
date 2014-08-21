package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.CommentList;
import org.apache.http.Header;

import java.util.List;

public class CommentsResult extends AbstractResult<CommentList> {

    public CommentsResult(int status, List<Header> headers, CommentList result) {
        super(status, headers, result);
    }

}
