package com.project.taskhub2;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    String id, name, text;
    User author;
    Group group;
    Date createdOn;
    Boolean completed;
    Date deadline;

    public Task(String id, String name, String text, User author, Group group) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.author = author;
        this.group = group;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.createdOn = new Date(timestamp.getTime());
    }

    public Task(String id, String name, String text, User author, Group group, Date deadline) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.author = author;
        this.group = group;
        this.deadline = deadline;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.createdOn = new Date(timestamp.getTime());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public Group getGroup() {
        return group;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Boolean getCompleted() {
        return completed;
    }
}
