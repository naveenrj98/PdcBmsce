package com.developer.rjtech.pdcbmsce.CodingClub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Profile.AccountSettingsActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.Utils.SectionsPagerAdapter;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;


public class CodingClubFragment extends Fragment {

    private ScrollerViewPager viewPager;
    private SpringIndicator tabLayout;

    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_coding_club, container, false);
        viewPager =  view.findViewById(R.id.container);
        tabLayout =  view.findViewById(R.id.tabs);
        setupViewPager();

        //------------------------------------Navigation related code------------------------------------------------------------
        toolbar = view.findViewById(R.id.profileToolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);





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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AccountSettingsActivity.class) ;
                startActivity(intent);

                return true;
            case R.id.action_shreapp:
                Toast.makeText(getActivity(), "Thanks for sharing", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }



}
