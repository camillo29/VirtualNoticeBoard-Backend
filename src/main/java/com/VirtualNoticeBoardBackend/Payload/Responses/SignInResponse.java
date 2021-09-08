package com.VirtualNoticeBoardBackend.Payload.Responses;

public class SignInResponse {
    private String token;
    private String email;

    public SignInResponse(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public SignInResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
