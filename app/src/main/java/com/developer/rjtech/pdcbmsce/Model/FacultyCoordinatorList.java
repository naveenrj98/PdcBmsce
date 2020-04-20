package com.developer.rjtech.pdcbmsce.Model;

public class FacultyCoordinatorList {
    private String Name;
    private String Image;
    private String phone;
    private String email;
    private String dept;
    private String designation;

    public FacultyCoordinatorList() {
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



    public FacultyCoordinatorList(String name, String image, String phone, String email, String designation,String dept) {
        Name = name;
        Image = image;
        this.phone = phone;
        this.email = email;
        this.designation = designation;
        this.dept = dept;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept=dept;
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
