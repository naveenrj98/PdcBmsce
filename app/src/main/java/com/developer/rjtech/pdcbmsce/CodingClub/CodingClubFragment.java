package com.developer.rjtech.pdcbmsce.CodingClub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.rjtech.pdcbmsce.Home.HomeFragment;
import com.developer.rjtech.pdcbmsce.NewUpdates.NewsFragment;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ResumeBuild.ResumeFragment;
import com.developer.rjtech.pdcbmsce.Utils.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;


public class CodingClubFragment extends Fragment {

    ScrollerViewPager viewPager;
    SpringIndicator tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_coding_club, container, false);
        viewPager =  view.findViewById(R.id.container);
        tabLayout =  view.findViewById(R.id.tabs);
        setupViewPager();

        return view;
    }

    /**
     * Responsible for adding the 3 tabs: Team, Friends, Gallery
     */
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ResumeFragment()); //index 0
        adapter.addFragment(new ResumeBuildFragment()); //index 1
        adapter.addFragment(new AptitudeFragment()); //index 2


        viewPager.setAdapter(adapter);


        tabLayout.setViewPager(viewPager);



    }



}
