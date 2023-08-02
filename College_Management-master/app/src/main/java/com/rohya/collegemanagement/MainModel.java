package com.rohya.collegemanagement;

public class MainModel {

    MainModel() {

    }

    public MainModel(String name, String courses, String email, String turl) {
        this.name = name;
        this.courses = courses;
        this.email = email;
        this.turl = turl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }

    String name, courses, email, turl;

}
