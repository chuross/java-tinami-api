package com.chuross.api.tinami;

public class MockContext extends AbstractContext {

    private String url;
    private String apiKey;

    public MockContext(String baseUrl) {
        this(baseUrl, null);
    }

    public MockContext(String baseUrl, String apiKey) {
        this.url = baseUrl;
        this.apiKey = apiKey;
    }

    @Override
    public String getBaseUrl() {
        return url;
    }

    @Override
    public String getSecureBaseUrl() {
        return url;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

}
