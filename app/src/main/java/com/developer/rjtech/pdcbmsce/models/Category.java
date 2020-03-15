package com.developer.rjtech.pdcbmsce.models;

public class Category {



    private String Name;
    private String Image,Description,Price,Discount,MenuId;


    public Category() {

    }

    public Category(String name, String image, String description, String price, String discount, String menuId) {
        Name = name;
        Image = image;
        Description = description;
        Price = price;
        Discount = discount;
        MenuId = menuId;
    }
    public Category(String name, String image) {
        Name = name;
        Image = image;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
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
        return "Category{" +
                "Name='" + Name + '\'' +
                ", Image='" + Image + '\'' +
                ", Description='" + Description + '\'' +
                ", Price='" + Price + '\'' +
                ", Discount='" + Discount + '\'' +
                ", MenuId='" + MenuId + '\'' +
                '}';
    }
}
