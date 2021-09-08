package com.VirtualNoticeBoardBackend.Model;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(nullable = true)
    private String street;

    @Column(nullable = false)
    private String City;

    @Column(nullable = false)
    private String Province;

    @Column(nullable = false)
    private String Country;

    @ManyToOne
    private User user;

    public Address(String street, String city, String province, String country, User user) {
        this.street = street;
        City = city;
        Province = province;
        Country = country;
        this.user = user;
    }


    public Address() {}

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
