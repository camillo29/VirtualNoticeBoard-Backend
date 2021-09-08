package com.VirtualNoticeBoardBackend.Payload.Requests;

import com.VirtualNoticeBoardBackend.Model.*;

public class CreateAnnouncementRequest {
    private String title, description;
    private Type type;
    private Address address;

    public CreateAnnouncementRequest(String title, String description, Address address, Type type) {
        this.title = title;
        this.description = description;
    }

    public CreateAnnouncementRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
