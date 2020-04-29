package com.developer.rjtech.pdcbmsce.Onboarding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Firebase.FirebaseMethods;
import com.developer.rjtech.pdcbmsce.Main.MainActivity;
import com.developer.rjtech.pdcbmsce.Main.TargetViewActivity;
import com.developer.rjtech.pdcbmsce.Model.User;
import com.developer.rjtech.pdcbmsce.Model.UserAccountSettings;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.Users.RegisterActivity;
import com.developer.rjtech.pdcbmsce.Utils.StringManipulation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class OnboardingActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Context mContext;
    private OnboardingAdapter onboardingAdapter;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        makeStatusbarTransparent();
        mContext = OnboardingActivity.this;
        firebaseMethods = new FirebaseMethods(mContext);
        viewPager = findViewById(R.id.onboarding_view_pager);

        onboardingAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(onboardingAdapter);
        viewPager.setPageTransformer(false, new OnboardingPageTransformer());
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                  //  firebaseMethods.addNewUser(user.getEmail(), user.getDisplayName(), "", "", "","");
                    User user1 = new User( user.getUid(),  user.getPhoneNumber(),  user.getEmail(),  StringManipulation.condenseUsername(user.getDisplayName()) );
        Common.currentUser = user1;
        myRef.child(mContext.getString(R.string.dbname_users))
                .child(user.getUid())
                .setValue(user1);


        UserAccountSettings settings = new UserAccountSettings(
                "description",
                user.getDisplayName(),
                0,
                0,
                0,
                "",
                StringManipulation.condenseUsername(user.getDisplayName()),
                "website",
                user.getPhoneNumber()
        );

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(user.getUid())
                .setValue(settings);

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());



                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");

                }
                // ...
            }
        };


    }


    // Listener for next button press
    public void nextPage(View view) {
        if (view.getId() == R.id.button2) {
            if (viewPager.getCurrentItem() < onboardingAdapter.getCount() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }else {



                Intent intent = new Intent(getApplicationContext(), TargetViewActivity.class);
                startActivity(intent);
            }
        }
    }

    private void makeStatusbarTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
