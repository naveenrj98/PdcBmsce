package com.developer.rjtech.pdcbmsce.Model;

public class CollegeCoordinatorList {
    private String Name;
    private String Image;
    private String phone;
    private String email;
    private String designation;

    public CollegeCoordinatorList() {
    }

    @Override
    public String toString() {
        return "DeveloperList{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }



    public CollegeCoordinatorList(String name, String image,  String phone, String email, String designation) {
        Name = name;
        Image = image;
        this.phone = phone;
        this.email = email;
        this.designation = designation;
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


    public String getDesignation() {
        return designation;
    }
}
