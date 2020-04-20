package com.developer.rjtech.pdcbmsce.Model;

public class AlumniList {
    private String Name;
    private String Image;
    private String dept;
    private String designation;

    public AlumniList() {
    }

    @Override
    public String toString() {
        return "DeveloperList{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", dept='" + dept + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }



    public AlumniList(String name, String image, String phone, String email, String designation,String dept) {
        Name = name;
        Image = image;
        this.dept = dept;
        this.designation = designation;
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

    public void setDept(String name) {
        this.dept=dept;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }


    public String getDesignation() {
        return designation;
    }
}
