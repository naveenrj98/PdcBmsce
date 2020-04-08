package com.developer.rjtech.pdcbmsce.adapters;


import android.view.View;

import androidx.annotation.NonNull;


import com.developer.rjtech.pdcbmsce.datamodel.Project;

import java.util.List;

/**
 * Created by ibrahim on 1/19/18.
 */

public class ProjectsAdapter extends ResumeEventAdapter<Project> {

    public ProjectsAdapter(@NonNull List<Project> list,
                           ResumeEventOnClickListener resumeEventOnClickListener) {
        super(list, resumeEventOnClickListener);
    }

    @Override
    protected void updateViewHolder(ResumeEventAdapterViewHolder viewHolder) {
        viewHolder.subtitle.setVisibility(View.GONE);
    }
}