package com.VirtualNoticeBoardBackend.Payload.Requests;

public class TestRequest {
    private String email;
    private String password;

    public TestRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public TestRequest() {
    }

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
