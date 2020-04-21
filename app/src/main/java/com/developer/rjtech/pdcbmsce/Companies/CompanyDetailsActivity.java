package com.developer.rjtech.pdcbmsce.Companies;

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
import android.widget.QuickContactBadge;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Model.CompanyCategory;
import com.developer.rjtech.pdcbmsce.PDFViewer.PdfActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.Rating;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;


import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;


public class CompanyDetailsActivity extends AppCompatActivity  {
    private static final String TAG = "ComapnyDetails";

    //widgets
    TextView company_name, tv_pdfView, tv_jobRole,tv_jobLocation,tv_DOV,tv_cgpa,tv_eligibleDep,tv_offers;
    TextView tv_pdfname;
    TextView tv_jdname;
    TextView tv_stipend, tv_duration, tv_worktime, tv_jobtype;
    CircleImageView civ_linkedin, civ_glassdoor, civ_website;
    ImageView company_image;
    CardView cv_interview,cv_internship,cv_jd,cv_company, cv_about;
    LinearLayout ll_interview, ll_internship, ll_jd,ll_company, ll_company_dropdown,
            ll_internship_dropdown,ll_interview_dropdown,ll_jd_dropdown,ll_about_company, ll_about_review;
    Button btn_dropdown, btn_interview_dropdown, btn_jd, btn_company,btn_about_dropdown;

    CollapsingToolbarLayout collapsingToolbarLayout;
    //Firebase
    String companyId = "";
    FirebaseDatabase database;
    DatabaseReference companies;

    //Links
   String linkedin="";
   String glassdoor="";
   String website="";

    //Model
    CompanyCategory currentCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_detail);

        tv_jobRole = findViewById(R.id.tv_company_role);
        tv_jobLocation= findViewById(R.id.tv_jobLocation);
        tv_DOV = findViewById(R.id.tv_dateOfVisit);
        tv_cgpa=findViewById(R.id.tv_cgpa);
        tv_eligibleDep=findViewById(R.id.tv_eligibleDep);
        tv_offers = findViewById(R.id.tv_offers);
        tv_pdfname = findViewById(R.id.tv_pdfname);
        tv_jdname = findViewById(R.id.tv_job_description);
        tv_stipend = findViewById(R.id.tv_stipend);
        tv_duration = findViewById(R.id.tv_duration);
        tv_jobtype = findViewById(R.id.tv_conversion);
        tv_worktime = findViewById(R.id.tv_worktime);

        //method for calling on Click listener for dropdown in CardView
        dropDown();

        //Auth
        database = FirebaseDatabase.getInstance();
        companies = database.getReference("CompanyYear").child(Common.yearSelected)
                .child("details").child("Companies");


        company_image = findViewById(R.id.img_company);
        company_name = findViewById(R.id.company_name);
        if (getIntent() != null) {
            companyId = getIntent().getStringExtra("CategoryId");

        }
        if (!companyId.isEmpty() && companyId != null) {

            getDetailCompany(companyId);



        }
        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);



    }

    private void dropDown() {

        cv_about = findViewById(R.id.cv_company_details);
        ll_about_company = findViewById(R.id.ll_about_company);
        ll_about_review = findViewById(R.id.ll_about_review);
        btn_about_dropdown = findViewById(R.id.btn_about_dropdown);


        cv_company = findViewById(R.id.cv_company_details);
        ll_company_dropdown = findViewById(R.id.ll_dropdown_company);
        ll_company = findViewById(R.id.ll_company);
        btn_company = findViewById(R.id.btn_company_dropdown);

        civ_linkedin = findViewById(R.id.civ_linkedin);
        civ_glassdoor = findViewById(R.id.civ_glassdoor);
        civ_website = findViewById(R.id.civ_website);

        cv_jd = findViewById(R.id.cv_jd);
        ll_jd = findViewById(R.id.ll_job_description);
        ll_jd_dropdown = findViewById(R.id.ll_dropdown_jd);
        btn_jd = findViewById(R.id.btn_jd_dropdown);

        cv_internship = findViewById(R.id.cv_internship);
        ll_internship = findViewById(R.id.ll_internship);
        ll_internship_dropdown = findViewById(R.id.ll_dropdown_internship);
        btn_dropdown = findViewById(R.id.btn_dropdown);

        cv_interview = findViewById(R.id.cv_interview);
        ll_interview = findViewById(R.id.ll_interview);
        ll_interview_dropdown = findViewById(R.id.ll_dropdown_interview);
        btn_interview_dropdown = findViewById(R.id.btn_interview_dropdown);

        ll_about_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll_about_company.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_about, new AutoTransition());
                    ll_about_company.setVisibility(View.VISIBLE);
                    btn_about_dropdown.setBackgroundResource(R.drawable.ic_updown);
                } else {
                    TransitionManager.endTransitions(cv_about);
                    ll_about_company.setVisibility(View.GONE);
                    btn_about_dropdown.setBackgroundResource(R.drawable.ic_dropdown);

                }

            }
        });

        ll_company_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll_company.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_company, new AutoTransition());
                    ll_company.setVisibility(View.VISIBLE);
                    btn_company.setBackgroundResource(R.drawable.ic_updown);
                } else {
                    TransitionManager.endTransitions(cv_company);
                    ll_company.setVisibility(View.GONE);
                    btn_company.setBackgroundResource(R.drawable.ic_dropdown);

                }

            }
        });
        ll_interview_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_interview.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_interview, new AutoTransition());
                    ll_interview.setVisibility(View.VISIBLE);
                    btn_interview_dropdown.setBackgroundResource(R.drawable.ic_updown);
                } else {
                    TransitionManager.endTransitions(cv_interview);
                    ll_interview.setVisibility(View.GONE);
                    btn_interview_dropdown.setBackgroundResource(R.drawable.ic_dropdown);

                }
            }
        });
        ll_internship_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll_internship.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_internship, new AutoTransition());
                    ll_internship.setVisibility(View.VISIBLE);
                    btn_dropdown.setBackgroundResource(R.drawable.ic_updown);
                } else {
                    TransitionManager.endTransitions(cv_internship);
                    ll_internship.setVisibility(View.GONE);
                    btn_dropdown.setBackgroundResource(R.drawable.ic_dropdown);

                }

            }
        });
        ll_jd_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll_jd.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_jd, new AutoTransition());
                    ll_jd.setVisibility(View.VISIBLE);
                    btn_jd.setBackgroundResource(R.drawable.ic_updown);
                } else {
                    TransitionManager.endTransitions(cv_jd);
                    ll_jd.setVisibility(View.GONE);
                    btn_jd.setBackgroundResource(R.drawable.ic_dropdown);

                }

            }
        });
        civ_linkedin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent linkedinIntent = getOpenLinkdinIntent(getApplicationContext(),linkedin);
                startActivity(linkedinIntent);


            }
        });
        civ_glassdoor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent linkedinIntent = getOpenGlassDoorIntent(getApplicationContext(),glassdoor);
                startActivity(linkedinIntent);


            }
        });
        civ_website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent linkedinIntent = getOpenWebIntent(getApplicationContext(),website);
                startActivity(linkedinIntent);


            }
        });









    }


    //================Getting Company Details ---------------------------------------------------//
    private void getDetailCompany(final String companyId){

        companies.child(companyId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currentCompany = dataSnapshot.getValue(CompanyCategory.class);

                    Picasso.with(getBaseContext()).load(currentCompany.getImage())
                            .into(company_image);
                    collapsingToolbarLayout.setTitle(currentCompany.getName());

                    company_name.setText(currentCompany.getName()+" Review");

                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        companies.child(companyId).child("companyDetails").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    currentCompany = dataSnapshot.getValue(CompanyCategory.class);

                    tv_jobRole.setText(currentCompany.getRole());
                    tv_jobLocation.setText(currentCompany.getJoblocation());
                    tv_DOV.setText(currentCompany.getVisitdate());
                    tv_cgpa.setText(currentCompany.getCgpa());
                    tv_eligibleDep.setText(currentCompany.getEligibledepartment());
                    tv_offers.setText(currentCompany.getOffers());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        companies.child(companyId).child("InternshipDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentCompany = dataSnapshot.getValue(CompanyCategory.class);

                tv_stipend.setText(currentCompany.getStipend());
                tv_duration.setText(currentCompany.getDuration());
                tv_worktime.setText(currentCompany.getWorktime());
                tv_jobtype.setText(currentCompany.getJobtype());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        companies.child(companyId).child("InterviewProcess").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    currentCompany = dataSnapshot.getValue(CompanyCategory.class);

                    tv_pdfname.setText(currentCompany.getPdfname());

                    tv_pdfname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), PdfActivity.class);
                            intent.putExtra("companyID",companyId);
                            startActivity(intent);

                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        companies.child(companyId).child("jobDescription").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentCompany = dataSnapshot.getValue(CompanyCategory.class);

                tv_jdname.setText(currentCompany.getPdfname());

                tv_jdname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), PdfActivity.class);
                        intent.putExtra("companyID",companyId);
                        startActivity(intent);

                    }
                });


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

