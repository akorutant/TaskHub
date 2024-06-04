package com.project.taskhub2;

import android.media.Image;

public class Group {
    String id;
    String name;
    User owner;
    User[] users;
    Image image;

    public Group(String id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Group(String id, String name, User owner, User[] users) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
