package com.VirtualNoticeBoardBackend.Payload.Requests;

import com.VirtualNoticeBoardBackend.Model.Address;

public class RemoveAddressRequest {
    private Address address;

    public RemoveAddressRequest(Address address) {
        this.address = address;
    }

    public RemoveAddressRequest(){}

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
