package com.chuross.api.tinami;

public class Context {

    private String apiKey;

    public Context(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return "http://api.tinami.com";
    }

    public String getSecureBaseUrl() {
        return "https://www.tinami.com/api";
    }

    public String getUrl(String path) {
        return String.format("%s/%s", getBaseUrl(), path.startsWith("/") ? path.substring(1) : path);
    }

    public String getSecureUrl(String path) {
        return String.format("%s/%s", getSecureBaseUrl(), path.startsWith("/") ? path.substring(1) : path);
    }

    public String getApiKey() {
        return apiKey;
    }

}
