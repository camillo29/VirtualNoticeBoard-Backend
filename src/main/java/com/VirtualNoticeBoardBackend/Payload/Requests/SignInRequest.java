package com.VirtualNoticeBoardBackend.Payload.Requests;

public class SignInRequest {
    private String email, password;

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public SignInRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
