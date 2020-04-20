package com.developer.rjtech.pdcbmsce.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.developer.rjtech.pdcbmsce.CodingClub.AptitudeFragment;
import com.developer.rjtech.pdcbmsce.CodingClub.ResumeFragment;
import com.developer.rjtech.pdcbmsce.CodingClub.TechnicalFragment;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.Utils.AboutSectionPageAdapter;
import com.developer.rjtech.pdcbmsce.Utils.SectionsPagerAdapter;

import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AboutActivity extends AppCompatActivity {
    ScrollerViewPager viewPager;
    SpringIndicator tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        viewPager =  findViewById(R.id.container);
        tabLayout =  findViewById(R.id.tabs);
        setupViewPager();

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                finish();
            }
        });

    }
    private void setupViewPager(){
        AboutSectionPageAdapter adapter = new AboutSectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CollegeCoordinatorFragment()); //index 0
        adapter.addFragment(new FacultyCoordinatorFragment()); //index 1
        adapter.addFragment(new StudentCoordinatorFragment()); //index 2


        viewPager.setAdapter(adapter);


        tabLayout.setViewPager(viewPager);



    }
}
