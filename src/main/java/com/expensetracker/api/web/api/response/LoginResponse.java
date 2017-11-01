package com.expensetracker.api.web.api.response;

import io.dropwizard.jackson.JsonSnakeCase;

@JsonSnakeCase
public class LoginResponse {

    private long id;

    private String name;

    private String role;

    private long expires;

    private String token;

    public LoginResponse() {
    }

    public LoginResponse(long id, String name, String role, long expires, String token) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.expires = expires;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public long getExpires() {
        return expires;
    }

    public String getToken() {
        return token;
    }
}
