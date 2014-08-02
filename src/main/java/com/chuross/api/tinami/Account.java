package com.chuross.api.tinami;

public class Account {

    private String email;
    private String password;
    private String authKey;

    public Account(String email, String password) {
        this(email, password, null);
    }

    public Account(String email, String password, String authKey) {
        this.email = email;
        this.password = password;
        this.authKey = authKey;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthKey() {
        return authKey;
    }

}
