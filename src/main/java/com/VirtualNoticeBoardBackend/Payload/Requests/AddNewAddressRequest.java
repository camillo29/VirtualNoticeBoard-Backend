package com.VirtualNoticeBoardBackend.Payload.Requests;

public class AddNewAddressRequest {
    private String country, province, city, street;

    public AddNewAddressRequest(String country, String province, String city, String street) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
    }

    public AddNewAddressRequest() {}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
