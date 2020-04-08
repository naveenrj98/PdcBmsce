package com.developer.rjtech.pdcbmsce.Profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Model.CompanyCategory;
import com.developer.rjtech.pdcbmsce.Model.DeveloperList;
import com.developer.rjtech.pdcbmsce.PDFViewer.PdfActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class DevelopersDetailsActivity extends AppCompatActivity  {
    private static final String TAG = "DeveloperDetails";

    //widgets
    TextView company_name, tv_company_description, tv_jobRole,tv_jobLocation,tv_DOV,tv_cgpa,tv_eligibleDep,tv_offers;
    TextView tv_pdfname;
    TextView tv_stipend, tv_duration, tv_worktime, tv_jobtype;
    CircleImageView civ_linkedin, civ_glassdoor, civ_website;
    ImageView company_image;


    CollapsingToolbarLayout collapsingToolbarLayout;
    //Firebase
    String companyId = "";
    FirebaseDatabase database;
    DatabaseReference companies;

    //Links
   String linkedin="";
   String glassdoor="";
   String github="";

    //Model
    DeveloperList currentCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_detail);

        tv_company_description = findViewById(R.id.tv_company_description);



        //Auth
        database = FirebaseDatabase.getInstance();
        companies = database.getReference("Developers");


        company_image = findViewById(R.id.img_company);
        company_name = findViewById(R.id.developer_name);
        if (getIntent() != null) {
            companyId = getIntent().getStringExtra
                    ("CategoryId");

        }
        if (!companyId.isEmpty() && companyId != null) {

            getDetailCompany(companyId);



        }
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);



    }



    //================Getting Company Details ---------------------------------------------------//
    private void getDetailCompany(final String companyId){

        companies.child(companyId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currentCompany = dataSnapshot.getValue(DeveloperList.class);

                    Picasso.with(getBaseContext()).load(currentCompany.getImage())
                            .into(company_image);
                    collapsingToolbarLayout.setTitle(currentCompany.getName());

                    company_name.setText(currentCompany.getName());
                    tv_company_description.setText(currentCompany.getDescription());


                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    //-----------------LInks for Scial Media-----------------------------------------------------//
    public static Intent getOpenLinkdinIntent(Context context,String linkedin) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.linkedin.android", 0); //Checks if Linkdin is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(linkedin)); //Trys to make intent with Linkdin's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(linkedin)); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenWebIntent(Context context, String website) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.google.android.youtube", 0); //Checks if YT is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(website)); //Trys to make intent with YT's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(website)); //catches and opens a url to the desired page
        }
    }

    public static Intent getOpenGlassDoorIntent(Context context, String glassdoor) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.google.android", 0); //Checks if YT is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(glassdoor)); //Trys to make intent with YT's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(glassdoor)); //catches and opens a url to the desired page
        }
    }
}

