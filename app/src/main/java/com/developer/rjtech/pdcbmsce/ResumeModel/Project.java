package com.developer.rjtech.pdcbmsce.ResumeModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ibrahim on 1/18/18.
 */

public class Project extends ResumeEvent {

    public Project() {
    }

    public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(new ResumeEvent(in));
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public Project(ResumeEvent event) {
        super(event);
    }

    public String getName() {
        return getTitle();
    }

    public void setName(String name) {
        setTitle(name);
    }
}
