package com.project.taskhub2;

import java.util.ArrayList;
import java.util.List;

public class Group {
    String id;
    String name;
    String description;
    User owner;
    List<User> users;
    int membersCount;
    String slug;

    public Group(String id, String name, String description, User owner, int membersCount, String slug) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.membersCount = membersCount;
        this.slug = slug;
        this.users = new ArrayList<>();
        this.users.add(this.owner);
    }

    public Group(String id, String name, String description, User owner, int membersCount, String slug, List<User> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.users = users;
        this.membersCount = membersCount;
        this.slug = slug;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
