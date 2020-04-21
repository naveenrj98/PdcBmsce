package com.developer.rjtech.pdcbmsce.CodingClub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.Utils.SectionsPagerAdapter;

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
        adapter.addFragment(new TechnicalFragment()); //index 0
        adapter.addFragment(new AptitudeFragment()); //index 1
        adapter.addFragment(new InterviewFragment()); //index 2


        viewPager.setAdapter(adapter);


        tabLayout.setViewPager(viewPager);



    }



}
