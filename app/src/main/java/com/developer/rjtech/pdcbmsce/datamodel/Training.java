package com.developer.rjtech.pdcbmsce.datamodel;

public class Training {
    public final String trainingTitle;
    public final String trainingDate;
    public final String pName;
    public final String pDetails;

    public Training(String trainingTitle, String trainingDate, String pName, String pDetails) {
        this.trainingTitle = trainingTitle;
        this.trainingDate = trainingDate;
        this.pName = pName;
        this.pDetails = pDetails;
    }
}
