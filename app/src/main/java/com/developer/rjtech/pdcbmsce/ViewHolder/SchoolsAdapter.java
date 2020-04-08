package com.developer.rjtech.pdcbmsce.ViewHolder;


import androidx.annotation.NonNull;

import com.developer.rjtech.pdcbmsce.ResumeModel.School;

import java.util.List;


/**
 * Created by ibrahim on 1/19/18.
 */

public class SchoolsAdapter extends ResumeEventAdapter<School> {

    public SchoolsAdapter(@NonNull List<School> list,
                          ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}