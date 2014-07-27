package com.chuross.api.tinami;

public class ApiContext extends AbstractContext {

    private String apiKey;

    public ApiContext(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getBaseUrl() {
        return "http://api.tinami.com";
    }

    @Override
    public String getSecureBaseUrl() {
        return "https://www.tinami.com/api";
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

}
