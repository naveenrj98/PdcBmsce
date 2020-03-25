package com.developer.rjtech.pdcbmsce.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.CodingClub.CodingClubFragment;
import com.developer.rjtech.pdcbmsce.Companies.CompanyYearFragment;
import com.developer.rjtech.pdcbmsce.Companies.SearchActivity;
import com.developer.rjtech.pdcbmsce.Home.HomeFragment;
import com.developer.rjtech.pdcbmsce.NewUpdates.NewsFragment;
import com.developer.rjtech.pdcbmsce.Profile.AccountSettingsActivity;
import com.developer.rjtech.pdcbmsce.ResumeDeatailsSettings.ProfileFragment;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ResumeModel.Resume;
import com.developer.rjtech.pdcbmsce.Users.LoginActivity;
import com.developer.rjtech.pdcbmsce.Utils.UniversalImageLoader;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ImageView profileMenu;
    private static final String TAG = "HomeActivity";

    private final static int ID_HOME = 1;
    private final static int ID_NOTIFICATION = 2;
    private final static int ID_CODING_CLUB = 3;
    private final static int ID_COMPANIES = 4;
    private final static int ID_ACCOUNT = 5;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button materialSearchBar;
    private Resume resume;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.profileToolBar);
        setupFirebaseAuth();
        initImageLoader();
        materialSearchBar = findViewById(R.id.btn_search);
         materialSearchBar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                 startActivity(intent);
             }
         });

        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json = mPrefs.getString("SerializableObject", "");
        if (json.isEmpty())
            resume = Resume.createNewResume();
        else
            resume = gson.fromJson(json, Resume.class);

        //------------------------------------Navigation related code------------------------------------------------------------
        setSupportActionBar(toolbar);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

//------------------------------------Bottom Navigation Code Starts--------------------------------------------------
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATION, R.drawable.ic_notification));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_CODING_CLUB, R.drawable.ic_codingclub));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_COMPANIES, R.drawable.ic_business_black_24dp));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.ic_account));

        bottomNavigation.setCount(ID_NOTIFICATION, "115");

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
              //  Toast.makeText(MainActivity.this, "clicked item : " + item.getId(), Toast.LENGTH_SHORT).show();


            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
               // Toast.makeText(MainActivity.this, "showing item : " + item.getId(), Toast.LENGTH_SHORT).show();
                Fragment selectFragment = null;

                String name;
                switch (item.getId()) {
                    case ID_HOME:
                        name = "HOME";
                        selectFragment = new HomeFragment();
                        break;
                    case ID_NOTIFICATION:
                        name = "EXPLORE";
                        selectFragment = new NewsFragment();
                        break;
                    case ID_CODING_CLUB:
                        name = "MESSAGE";
                        selectFragment = new CodingClubFragment();
                        break;
                    case ID_COMPANIES:
                        name = "NOTIFICATION";
//                        Intent i = new Intent(getApplicationContext(), YearActivity.class);
//                        startActivity(i);
                       selectFragment = new CompanyYearFragment();
                        break;
                    case ID_ACCOUNT:
                        name = "ACCOUNT";
                        selectFragment = new ProfileFragment();
                        break;
                    default:
                        name = "";
                }
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();


            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(MainActivity.this, "reselected item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setCount(ID_NOTIFICATION, "115");

        bottomNavigation.show(ID_HOME,true);

        //------------------------------------Bottom Navigation Code Starts--------------------------------------------------


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AccountSettingsActivity.class) ;
            startActivity(intent);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getApplicationContext());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * checks to see if the @param 'user' is logged in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        checkCurrentUser(mAuth.getCurrentUser());
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(resume);
        prefsEditor.putString("SerializableObject", json);
        prefsEditor.apply();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
//    @Override
//    public void onBackPressed() {
//        new ExitDialogFragment().show(getSupportFragmentManager(), "Exit");
//    }
}
