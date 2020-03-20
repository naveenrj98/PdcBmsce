package com.developer.rjtech.pdcbmsce.Model;

public class CompanyCategory {



    private String Name;
    private String Image;


    public CompanyCategory() {

    }

    public CompanyCategory(String name, String image) {
        Name = name;
        Image = image;
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

    @Override
    public String toString() {
        return "Year{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}
