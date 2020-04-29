package com.developer.rjtech.pdcbmsce.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.CodingClub.CodingClubFragment;
import com.developer.rjtech.pdcbmsce.Companies.CompaniesFragment;
import com.developer.rjtech.pdcbmsce.Home.HomeFragment;
import com.developer.rjtech.pdcbmsce.NewUpdates.NewsFragment;
import com.developer.rjtech.pdcbmsce.OnboardActivty;
import com.developer.rjtech.pdcbmsce.Profile.AccountSettingsActivity;
import com.developer.rjtech.pdcbmsce.Profile.ProfileFragment;
import com.developer.rjtech.pdcbmsce.R;

import com.developer.rjtech.pdcbmsce.Users.LoginActivity;
import com.developer.rjtech.pdcbmsce.Utils.UniversalImageLoader;
import com.developer.rjtech.pdcbmsce.datamodel.Resume;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private Toolbar toolbar;
    private ImageView profileMenu;
    private static final String TAG = "MainActivity";

    private final static int ID_HOME = 1;
    private final static int ID_NOTIFICATION = 2;
    private final static int ID_CODING_CLUB = 3;
    private final static int ID_COMPANIES = 4;
    private final static int ID_ACCOUNT = 5;


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    Button materialSearchBar;
    MeowBottomNavigation bottomNavigation;
    private Resume resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // toolbar = findViewById(R.id.profileToolBar);


        setupFirebaseAuth();
        initImageLoader();
        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json = mPrefs.getString("SerializableObject", "");
        if (json.isEmpty())
            resume = Resume.createNewResume();
        else
            resume = gson.fromJson(json, Resume.class);

        //------------------------------------Navigation related code------------------------------------------------------------
      //  setSupportActionBar(toolbar);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

//------------------------------------Bottom Navigation Code Starts--------------------------------------------------
     bottomNavigation = findViewById(R.id.bottomNavigation);

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
                        name = "Companies";

                       selectFragment = new CompaniesFragment();
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

        bottomNavigation.setCount(ID_NOTIFICATION, "1");

        bottomNavigation.show(ID_HOME,true);

        //------------------------------------Bottom Navigation Code Ends--------------------------------------------------


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
        } else if (id == R.id.action_shreapp) {

            Intent intent = new Intent(Intent.ACTION_SEND);

            String applink = "https://play.google.com/store/apps/details?id=com.developer.rjtech.pdcbmsce";
            intent.setType("text/link");
            String body = "Hey! Download App and Prepare for BMSCE PLACEMENT"+"\n"+" "+applink+"\n"+"Thanks and Regards"+"\n"+" "+"Naveen RJ";
            String sub = "Placement BMSCE by Naveen RJ";
            intent.putExtra(Intent.EXTRA_SUBJECT, sub);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent,"ShareVia"));
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
       // showHelp();
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
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
         //   showHelp();
        }
    }

    private void showHelp() {

        final Display display = getWindowManager().getDefaultDisplay();
        // Load our little droid guy
        final Drawable home = ContextCompat.getDrawable(this, R.drawable.ic_home);
        final Drawable news = ContextCompat.getDrawable(this, R.drawable.ic_notification);
        final Drawable coding = ContextCompat.getDrawable(this, R.drawable.ic_codingclub);
        final Drawable company = ContextCompat.getDrawable(this, R.drawable.ic_business_black_24dp);
        final Drawable account = ContextCompat.getDrawable(this, R.drawable.ic_account);

        // Tell our droid buddy where we want him to appear
        final Rect homedroidTarget = new Rect(-500, display.getWidth() * 2, home.getIntrinsicWidth()*2, home.getIntrinsicHeight() * 2);
        final Rect newsdroidTarget = new Rect(-100, display.getWidth() * 2, news.getIntrinsicWidth() * 2, news.getIntrinsicHeight() * 2);
        final Rect codingdroidTarget = new Rect(news.getIntrinsicWidth() * 3, display.getWidth() * 2, coding.getIntrinsicWidth() * 2, coding.getIntrinsicHeight() * 2);
        final Rect companydroidTarget = new Rect(coding.getIntrinsicWidth() * 8, display.getWidth() * 2, company.getIntrinsicWidth() * 2, company.getIntrinsicHeight() * 2);
        final Rect accountdroidTarget = new Rect(coding.getIntrinsicWidth() * 15, display.getWidth() * 2, account.getIntrinsicWidth() * 2, account.getIntrinsicHeight() * 2);

        // Using deprecated methods makes you look way cool
        homedroidTarget.offset(display.getWidth() / 3, display.getHeight() / 3);
        newsdroidTarget.offset(display.getWidth() / 3, display.getHeight() / 3);
        codingdroidTarget.offset(display.getWidth() / 3, display.getHeight() / 3);
        companydroidTarget.offset(display.getWidth() / 3, display.getHeight() / 3);
        accountdroidTarget.offset(display.getWidth() / 3, display.getHeight() / 3);

        final SpannableString sassyDesc = new SpannableString("It allows you to go back, sometimes");
        sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "sometimes".length(), sassyDesc.length(), 0);
        // We have a sequence of targets, so lets build it!
        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(


                        TapTarget.forBounds(homedroidTarget, "Home!", "Navigation for PlacementRules/ContactCoordinators/AlumniDetails/BuildResume")
                                .cancelable(false)
                                .icon(home)
                                .id(1),
                        TapTarget.forBounds(newsdroidTarget, "Notification", "User can get the regular updates of the placement department")
                                .cancelable(false)
                                .icon(news)
                                .id(2),
                        TapTarget.forBounds(codingdroidTarget, "Coding Club", "User can Practise the Coding/Aptitude and Prepare for the Interview")
                                .cancelable(false)
                                .icon(coding)
                                .id(3),
                        TapTarget.forBounds(companydroidTarget, "Company Details", "User can see the CompanyDetails/CTC/JobDescription ...etc")
                                .cancelable(false)
                                .icon(company)
                                .id(4),
                        TapTarget.forBounds(accountdroidTarget, "Profile Settings", "User can update the Personnel Information")
                                .cancelable(false)
                                .icon(account)
                                .id(5)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(getApplicationContext(), "You are ready to go!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
        sequence.start();

    }
}
