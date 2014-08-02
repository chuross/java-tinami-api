package com.chuross.api.tinami.parameter;

public enum ContentType {

    ILLUST(1), COMIC(2), MODEL(3), NOVEL(4), COSPLAY(5);

    private int code;
    private ContentType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
