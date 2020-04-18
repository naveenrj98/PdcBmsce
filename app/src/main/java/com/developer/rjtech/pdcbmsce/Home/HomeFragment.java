package com.developer.rjtech.pdcbmsce.Home;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.developer.rjtech.pdcbmsce.R;



public class HomeFragment extends Fragment {
    ImageView about,rules,alumni;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        about = view.findViewById(R.id.about_image);
        rules = view.findViewById(R.id.rules_image);
        alumni = view.findViewById(R.id.alumni_image);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AboutActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }
}
