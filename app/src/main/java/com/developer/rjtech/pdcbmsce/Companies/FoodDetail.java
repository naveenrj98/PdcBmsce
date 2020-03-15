package com.developer.rjtech.pdcbmsce.Companies;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.models.Category;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.Arrays;



public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_desription;
    ImageView food_image;

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart, btnraingBar;
    ElegantNumberButton elegantNumberButton;

    RatingBar ratingBar;

    String foodId = "";
    FirebaseDatabase database;
    DatabaseReference foods;
    DatabaseReference ratingTBl;

    Category currentfood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);


        //Auth
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Category");


        elegantNumberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);


        food_image = findViewById(R.id.img_food);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_desription = findViewById(R.id.food_description);


        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);


        if (getIntent() != null) {
            foodId = getIntent().getStringExtra("CategoryId");

        }
        if (!foodId.isEmpty() && foodId != null) {

            getDetailFood(foodId);
//            getRatingFood(foodId);


        }
    }

        private void getDetailFood(final String foodId){

            foods.child(foodId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    currentfood = dataSnapshot.getValue(Category.class);

                    Picasso.with(getBaseContext()).load(currentfood.getImage())
                            .into(food_image);
                    collapsingToolbarLayout.setTitle(currentfood.getName());
                    food_price.setText(currentfood.getPrice());
                    food_name.setText(currentfood.getName());
                    food_desription.setText(currentfood.getDescription());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }

