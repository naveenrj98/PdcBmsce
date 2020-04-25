package com.developer.rjtech.pdcbmsce.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.developer.rjtech.pdcbmsce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;


public class PlacmentRulesFragment extends Fragment {

//    String rules= "<h1 >Placement Rules</h1>"
//            +"<ol>" +
//            "  <li>Coffee</li>" +
//            "  <li>Tea</li>" +
//            "  <li>Milk</li>" +
//            "</ol>";
    String rules = "";
    private FirebaseDatabase database;
    private DatabaseReference mref;
    HtmlTextView htmlTextView;
    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_placment_rules, container, false);

        database = FirebaseDatabase.getInstance();
        mref = database.getReference("Rules").child("1");
        htmlTextView = view.findViewById(R.id.html_text);
        progressBar = view.findViewById(R.id.rules_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rules = dataSnapshot.child("text").getValue().toString();
                htmlTextView.setHtml(rules);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return view;
    }
}
