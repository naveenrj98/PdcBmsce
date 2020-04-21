package com.developer.rjtech.pdcbmsce.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.developer.rjtech.pdcbmsce.Model.DeveloperList;
import com.developer.rjtech.pdcbmsce.R;
import com.github.clans.fab.FloatingActionMenu;


public class DeveloperActivty extends AppCompatActivity {

    private static final String TAG = "DevelopersActivity";

    //floating Action button
    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton fab_fb, fab_linkedin, fab_insta,fab_github, fab_web, fab_help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_activty);
        init();


    }


    private void init(){
        Log.d(TAG, "init: inflating " + getString(R.string.profile_fragment));

        DeveloperListFragment fragment = new DeveloperListFragment();
        FragmentTransaction transaction = DeveloperActivty.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container1, fragment);
        transaction.addToBackStack(getString(R.string.Developer_fragment));
        transaction.commit();
    }
    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            finish();
        }

    }



}
