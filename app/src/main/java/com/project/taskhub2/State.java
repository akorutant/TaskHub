package com.project.taskhub2;

import java.util.Date;

public class State {

    private String title;
    private String description;
    private Date dateStart;
    private Date dateEnd;

    public State(String title, String description) {

        this.title = title;
        this.description = description;
//        this.dateStart = dateStart;
//        this.dateEnd = dateEnd;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
