package com.developer.rjtech.pdcbmsce.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Companies.CompanyCategoryFragment;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.Year;
import com.developer.rjtech.pdcbmsce.Profile.AccountSettingsActivity;
import com.developer.rjtech.pdcbmsce.Profile.DeveloperActivty;
import com.developer.rjtech.pdcbmsce.Profile.DeveloperListFragment;
import com.developer.rjtech.pdcbmsce.Profile.DevelopersDetailsActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.Utils.UniversalImageLoader;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.YearViewHolder;
import com.developer.rjtech.pdcbmsce.helper.MainResumeActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
   // ImageView about,rules,alumni;
    private CardView about,rules,alumni,cv_resume_home;
    private Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference year;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<Year, YearViewHolder> adptor;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.content_home, container, false);
        toolbar = view.findViewById(R.id.profileToolBar);
        about = view.findViewById(R.id.about_image);
       rules = view.findViewById(R.id.rules_image);
       alumni = view.findViewById(R.id.alumni_image);
        cv_resume_home = view.findViewById(R.id.cv_resume_home);
        //------------------------------------Navigation related code------------------------------------------------------------

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        cv_resume_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainResumeActivity.class);
                startActivity(intent);

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);

            }
        });

        alumni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content_home, new AlumniFragment()).addToBackStack(null).commit();
            }
        });



        return view;
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





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
