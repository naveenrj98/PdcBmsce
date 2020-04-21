package com.developer.rjtech.pdcbmsce.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
import com.developer.rjtech.pdcbmsce.Profile.DeveloperActivty;
import com.developer.rjtech.pdcbmsce.Profile.DeveloperListFragment;
import com.developer.rjtech.pdcbmsce.Profile.DevelopersDetailsActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.YearViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
   // ImageView about,rules,alumni;
    private CardView about,rules,alumni,developers;
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.content_home, container, false);
//        about = view.findViewById(R.id.about_image);
//        rules = view.findViewById(R.id.rules_image);
//        alumni = view.findViewById(R.id.alumni_image);
        about = view.findViewById(R.id.about_image);
       rules = view.findViewById(R.id.rules_image);
       alumni = view.findViewById(R.id.alumni_image);
        developers = view.findViewById(R.id.developers);


        developers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DeveloperActivty.class);
                startActivity(intent);

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AboutActivity.class);
                startActivity(intent);

            }
        });

        alumni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, new AlumniFragment()).addToBackStack(null).commit();
            }
        });



        return view;
    }
}
