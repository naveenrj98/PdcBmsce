package com.developer.rjtech.pdcbmsce.ResumeModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ibrahim on 1/18/18.
 */

public class School extends ResumeEvent {
    public School() {
    }

    public static final Parcelable.Creator<School> CREATOR = new Parcelable.Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(new ResumeEvent(in));
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    public School(ResumeEvent event) {
        super(event);
    }

    public String getSchoolName() {
        return getTitle();
    }

    public void setSchoolName(String school) {
        setTitle(school);
    }

    public String getLocation() {
        return getDetail();
    }

    public void setLocation(String location) {
        setDetail(location);
    }

    public String getDegree() {
        return getSubtitle();
    }

    public void setDegree(String degree) {
        setSubtitle(degree);
    }
}
