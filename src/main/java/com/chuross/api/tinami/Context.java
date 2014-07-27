package com.chuross.api.tinami;

public interface Context {

    public String getBaseUrl();

    public String getSecureBaseUrl();

    public String getUrl(String path);

    public String getSecureUrl(String path);

    public String getApiKey();

}
