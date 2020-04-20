package com.developer.rjtech.pdcbmsce.Model;

public class AlumniList {
    private String Name;
    private String Image;
    private String department;
    private String designation;

    public AlumniList() {
    }

    public AlumniList(String name, String image, String deparment, String designation) {
        Name = name;
        Image = image;
        this.department = deparment;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String deparment) {
        this.department = deparment;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "AlumniList{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", deparment='" + department + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
