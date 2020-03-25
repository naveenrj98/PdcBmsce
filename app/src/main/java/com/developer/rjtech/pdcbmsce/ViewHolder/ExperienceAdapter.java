package com.developer.rjtech.pdcbmsce.ViewHolder;


import androidx.annotation.NonNull;

import com.developer.rjtech.pdcbmsce.ResumeModel.Experience;

import java.util.List;

/**
 * Created by ibrahim on 1/19/18.
 */

public class ExperienceAdapter extends ResumeEventAdapter<Experience> {

    public ExperienceAdapter(@NonNull List<Experience> list,
                             ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }
}
