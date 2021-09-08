package com.VirtualNoticeBoardBackend.Model;

import javax.persistence.*;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Type type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    public Announcement(User user, Address address, Type type, String title, String description) {
        this.user = user;
        this.address = address;
        this.type = type;
        this.title = title;
        this.description = description;
    }

    public Announcement() {}

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
