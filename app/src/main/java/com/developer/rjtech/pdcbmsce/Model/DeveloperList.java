package com.developer.rjtech.pdcbmsce.Model;

public class DeveloperList {
    private String Name;
    private String Image;
    private String college;
    private String phone;
    private String email;
    private String year;
    private String github;
    private String designation;
    private String description;

    public DeveloperList() {
    }

    @Override
    public String toString() {
        return "DeveloperList{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", college='" + college + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", year='" + year + '\'' +
                ", github='" + github + '\'' +
                ", designation='" + designation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DeveloperList(String name, String image, String college, String phone, String email, String year, String github, String designation, String description) {
        Name = name;
        Image = image;
        this.college = college;
        this.phone = phone;
        this.email = email;
        this.year = year;
        this.github = github;
        this.designation = designation;
        this.description = description;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public DeveloperList(String name, String image, String college, String phone, String email, String year, String github, String designation) {
        Name = name;
        Image = image;
        this.college = college;
        this.phone = phone;
        this.email = email;
        this.year = year;
        this.github = github;
        this.designation = designation;
    }

    public DeveloperList(String name, String image, String college, String phone, String email, String year, String github) {
        Name = name;
        Image = image;
        this.college = college;
        this.phone = phone;
        this.email = email;
        this.year = year;
        this.github = github;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

}
