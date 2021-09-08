package com.VirtualNoticeBoardBackend.Payload.Responses;

import com.VirtualNoticeBoardBackend.Model.Announcement;

public class AnnouncementResponse {
    private Long id;
    private String email, name, surname, phoneNumber, street, country, province, city, title, description;

    public AnnouncementResponse(Announcement announcement) {
        this.id = announcement.getId();
        this.email = announcement.getUser().getEmail();
        this.name = announcement.getUser().getPerson().getName();
        this.surname = announcement.getUser().getPerson().getSurname();
        this.phoneNumber = announcement.getUser().getPerson().getPhoneNumber();
        this.phoneNumber = phoneNumber.replaceAll("(.{" + 3 + "})", "$1 ").trim();
        this.street = announcement.getAddress().getStreet();
        this.country = announcement.getAddress().getCountry();
        this.province = announcement.getAddress().getProvince();
        this.city = announcement.getAddress().getCity();
        this.title = announcement.getTitle();
        this.description = announcement.getDescription();
    }

    public AnnouncementResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}



/*
{
    "user": {
        "email": "jan_kowalski@op.pl",
        "password": "$2a$10$f1SqqpqLlgOODBRiKkuk9uJ1.z6PEX5UPxEZ.dItnioqx7h8BPKv2",
        "role": {
            "name": "ADMIN",
            "id": 104
        },
        "person": {
            "name": "Jan",
            "surname": "Kowalski",
            "phoneNumber": "364758354",
            "email": "jan_kowalski@op.pl",
            "id": 106
        },
        "id": 108
    },
    "address": {
        "street": "Wschodnia 7",
        "user": {
            "email": "jan_kowalski@op.pl",
            "password": "$2a$10$f1SqqpqLlgOODBRiKkuk9uJ1.z6PEX5UPxEZ.dItnioqx7h8BPKv2",
            "role": {
                "name": "ADMIN",
                "id": 104
            },
            "person": {
                "name": "Jan",
                "surname": "Kowalski",
                "phoneNumber": "364758354",
                "email": "jan_kowalski@op.pl",
                "id": 106
            },
            "id": 108
        },
        "id": 117,
        "country": "Poland",
        "city": "Staszów",
        "province": "Świętokrzyskie"
    },
    "type": {
        "name": "Sale",
        "id": 111
    },
    "title": "Sprzedam opla",
    "description": "Super opel na sprzedaż tylko 6000zł.",
    "id": 118
}
 */