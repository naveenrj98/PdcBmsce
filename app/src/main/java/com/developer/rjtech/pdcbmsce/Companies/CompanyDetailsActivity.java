package com.developer.rjtech.pdcbmsce.Companies;

import android.content.Intent;
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


public class CompanyDetailsActivity extends AppCompatActivity implements RatingDialogListener {
    private static final String TAG = "FoodDetails";

    TextView food_name, food_desription, tv_pdfView;
    ImageView food_image;


    CardView cv_interview,cv_internship,cv_jd,cv_company;
    LinearLayout ll_interview, ll_internship, ll_jd,ll_company;
    Button btn_dropdown, btn_interview_dropdown, btn_jd, btn_company;

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton  btnraingBar;


    RatingBar ratingBar;

    String foodId = "";
    FirebaseDatabase database;
    DatabaseReference foods;
    DatabaseReference ratingTBl;




    Category currentfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_detail);



        cv_company = findViewById(R.id.cv_company_details);
        ll_company = findViewById(R.id.ll_company);
        btn_company = findViewById(R.id.btn_company_dropdown);

        btn_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll_company.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_company, new AutoTransition());
                    ll_company.setVisibility(View.VISIBLE);
                    btn_company.setBackgroundResource(R.drawable.ic_dropdown);
                } else {
                    TransitionManager.endTransitions(cv_company);
                    ll_company.setVisibility(View.GONE);
                    btn_company.setBackgroundResource(R.drawable.ic_dropdown);

                }

            }
        });

        cv_jd = findViewById(R.id.cv_jd);
        ll_jd = findViewById(R.id.ll_job_description);
        btn_jd = findViewById(R.id.btn_jd_dropdown);

        btn_jd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll_jd.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_jd, new AutoTransition());
                    ll_jd.setVisibility(View.VISIBLE);
                    btn_jd.setBackgroundResource(R.drawable.ic_dropdown);
                } else {
                    TransitionManager.endTransitions(cv_jd);
                    ll_jd.setVisibility(View.GONE);
                    btn_jd.setBackgroundResource(R.drawable.ic_dropdown);

                }

            }
        });

        cv_internship = findViewById(R.id.cv_internship);
        ll_internship = findViewById(R.id.ll_internship);
        btn_dropdown = findViewById(R.id.btn_dropdown);

        btn_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ll_internship.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_internship, new AutoTransition());
                    ll_internship.setVisibility(View.VISIBLE);
                    btn_dropdown.setBackgroundResource(R.drawable.ic_dropdown);
                } else {
                    TransitionManager.endTransitions(cv_internship);
                    ll_internship.setVisibility(View.GONE);
                    btn_dropdown.setBackgroundResource(R.drawable.ic_dropdown);

                }

            }
        });

        cv_interview = findViewById(R.id.cv_interview);
        ll_interview = findViewById(R.id.ll_interview);
        btn_interview_dropdown = findViewById(R.id.btn_interview_dropdown);

        btn_interview_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_interview.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cv_interview, new AutoTransition());
                    ll_interview.setVisibility(View.VISIBLE);
                    btn_interview_dropdown.setBackgroundResource(R.drawable.ic_dropdown);
                } else {
                    TransitionManager.endTransitions(cv_interview);
                    ll_interview.setVisibility(View.GONE);
                    btn_interview_dropdown.setBackgroundResource(R.drawable.ic_dropdown);

                }
            }
        });

        tv_pdfView = findViewById(R.id.tv_pdfname);
        tv_pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PdfActivity.class);
                startActivity(intent);

            }
        });


        //Auth

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Category");
        ratingTBl = database.getReference("Rating");



        btnraingBar = findViewById(R.id.btnrateing);


        food_image = findViewById(R.id.img_food);
        food_name = findViewById(R.id.food_name);

        food_desription = findViewById(R.id.food_description);
        ratingBar = findViewById(R.id.ratingbar);


        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


        if (getIntent() != null) {
            foodId = getIntent().getStringExtra("CategoryId");

        }
        if (!foodId.isEmpty() && foodId != null) {

            getDetailFood(foodId);
        getRatingFood(foodId);


        }

        btnraingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showRatingDialog();
            }
        });


    }

    private void getRatingFood(String foodId) {

        Query foodRating = ratingTBl.orderByChild("foodId").equalTo(foodId);

        foodRating.addValueEventListener(new ValueEventListener() {
            int count =0, sum=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postDataSnapshot1 : dataSnapshot.getChildren()) {

                    Rating item = postDataSnapshot1.getValue(Rating.class);

                    sum += Integer.parseInt(item.getRatevalue());
                    count++;

                }
                if (count !=0 ) {

                    float average = sum/count;
                    ratingBar.setRating(average);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void showRatingDialog() {

        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Vry Bad","Not Good","Quite Ok","Very Good","Excellent"))
                .setDefaultRating(1)
                .setDescription("Please give your feedback by giving some stars to RJF")
                .setTitleTextColor(R.color.colorPrimary)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please comment here.....")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.ratingDialogFadeAnim)
                .create(CompanyDetailsActivity.this)
                .show();
    }

    

        private void getDetailFood(final String foodId){

            foods.child(foodId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currentfood = dataSnapshot.getValue(Category.class);

                    Picasso.with(getBaseContext()).load(currentfood.getImage())
                            .into(food_image);
                    collapsingToolbarLayout.setTitle(currentfood.getName());

                    food_name.setText(currentfood.getName());
                    food_desription.setText(currentfood.getDescription());

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }


    @Override
    public void onPositiveButtonClicked(int value, String comments) {

        final Rating rating = new Rating(String.valueOf(Common.currentUser.getPhone_number()),
                foodId,
                String.valueOf(value),
                comments
                );

        ratingTBl.child(String.valueOf(Common.currentUser.getPhone_number())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(String.valueOf(Common.currentUser.getPhone_number())).exists()) {


                    ratingTBl.child(String.valueOf(Common.currentUser.getPhone_number())).removeValue();
                    ratingTBl.child(String.valueOf(Common.currentUser.getPhone_number())).setValue(rating);


                }else {

                    ratingTBl.child(String.valueOf(Common.currentUser.getPhone_number())).setValue(rating);
                }
                Toast.makeText(CompanyDetailsActivity.this,"THanks for your great feedback boss",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }
}

