package com.chuross.api.tinami;

public class MockContext extends Context {

    private String url;

    public MockContext(String baseUrl) {
        this(baseUrl, null);
    }

    public MockContext(String baseUrl, String apiKey) {
        super(apiKey);
        this.url = baseUrl;
    }

    @Override
    public String getBaseUrl() {
        return url;
    }

    @Override
    public String getSecureBaseUrl() {
        return url;
    }

}
