package com.developer.rjtech.pdcbmsce.ResumeModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ibrahim on 1/18/18.
 */

public class Experience extends ResumeEvent {
    public Experience() {
    }

    public static final Parcelable.Creator<Experience> CREATOR = new Parcelable.Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(new ResumeEvent(in));
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    public Experience(ResumeEvent event) {
        super(event);
    }

    public String getCompany() {
        return getTitle();
    }

    public void setCompany(String company) {
        setTitle(company);
    }

    public String getLocation() {
        return getDetail();
    }

    public void setLocation(String location) {
        setDetail(location);
    }

    public String getJobTitle() {
        return getSubtitle();
    }

    public void setJobTitle(String jobTitle) {
        setSubtitle(jobTitle);
    }
}
