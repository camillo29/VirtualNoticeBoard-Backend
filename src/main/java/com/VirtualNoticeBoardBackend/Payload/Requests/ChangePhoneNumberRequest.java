package com.VirtualNoticeBoardBackend.Payload.Requests;

public class ChangePhoneNumberRequest {
    private String newPhoneNumber, password;

    public ChangePhoneNumberRequest(String newPhoneNumber, String password) {
        this.newPhoneNumber = newPhoneNumber;
        this.password = password;
    }

    public ChangePhoneNumberRequest() {
    }

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
