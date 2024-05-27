package com.example.lostfoundupdate;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Post implements Serializable {
    private long Id;
    private String Name;
    private String Phone;
    private String Description;
    private String Date;
    private String Location;
    private String Type;
    public long getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getDescription() {
        return Description;
    }

    public String getDate() {
        return Date;
    }

    public String getLocation() {
        return Location;
    }

    public String getType() {
        return Type;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setType(String type) {
        Type = type;
    }

    public Post(long id, String name, String phone, String description, String date, String location, String type) {
        this.Id = id;
        this.Name = name;
        this.Phone = phone;
        this.Description = description;
        this.Date = date;
        this.Location = location;
        this.Type = type;
    }
}
