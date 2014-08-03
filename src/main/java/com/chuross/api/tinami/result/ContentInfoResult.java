package com.chuross.api.tinami.result;

import com.chuross.api.tinami.element.ContentInfo;

public class ContentInfoResult extends AbstractAuthenticatedResult<ContentInfo> {

    public ContentInfoResult(int status, ContentInfo result) {
        super(status, result);
    }

    public boolean isAuthenticatedUserOnly() {
        return errorMessageExists("この作品は登録ユーザー限定の作品です。");
    }

    public boolean isBookmarkAppendedUserOnly() {
        return errorMessageExists("この作品はお気に入りユーザー限定の作品です。");
    }

}
