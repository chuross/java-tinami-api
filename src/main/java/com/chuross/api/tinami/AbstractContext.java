package com.chuross.api.tinami;

public abstract class AbstractContext implements Context {

    @Override
    public String getUrl(String path) {
        return String.format("%s/%s", getBaseUrl(), path.startsWith("/") ? path.substring(1) : path);
    }

    @Override
    public String getSecureUrl(String path) {
        return String.format("%s/%s", getSecureBaseUrl(), path.startsWith("/") ? path.substring(1) : path);
    }
}
