package com.developer.rjtech.pdcbmsce.Common;


import com.developer.rjtech.pdcbmsce.Model.User;

public class Common {


public static User currentUser;

    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static final String USER_KEY = "User";
    public static final String PWD_KEY = "Password";
    public static final String BASE_URL = "https://fcm.googleapis.com/";
    public static  String yearSelected="";
    public static  String companyCategorySelected="";
    public static  String companySelected="";


//    public static APIService getFCMClient()
//    {
//        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
//
//    }
//
//    public static final String converCodeToStatus(String status) {
//
//        if(status.equals("0"))
//        {
//            return  "Placed";
//        }else if(status.equals("1")){
//            return "On my Way";
//
//
//        }else {
//
//            return "Shipped";
//        }
//
//
//    }
}
