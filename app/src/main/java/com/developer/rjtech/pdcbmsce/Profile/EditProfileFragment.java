package com.developer.rjtech.pdcbmsce.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.developer.rjtech.pdcbmsce.Firebase.FirebaseMethods;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.Utils.UniversalImageLoader;
import com.developer.rjtech.pdcbmsce.dialogs.ConfirmPasswordDialog;
import com.developer.rjtech.pdcbmsce.Model.User;
import com.developer.rjtech.pdcbmsce.Model.UserAccountSettings;
import com.developer.rjtech.pdcbmsce.Model.UserSettings;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment implements
        ConfirmPasswordDialog.OnConfirmPasswordListener{

    private Uri saveUrl;
    private final int PICK_IMAGE_REQUEST = 71;


    @Override
    public void onConfirmPassword(String password) {
        Log.d(TAG, "onConfirmPassword: got the password: " + password);

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        ///////////////////// Prompt the user to re-provide their sign-in credentials
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "User re-authenticated.");

                            ///////////////////////check to see if the email is not already present in the database
                            mAuth.fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                    if(task.isSuccessful()){
                                        try{
                                            if(task.getResult().getProviders().size() == 1){
                                                Log.d(TAG, "onComplete: that email is already in use.");
                                                Toast.makeText(getActivity(), "That email is already in use", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Log.d(TAG, "onComplete: That email is available.");

                                                //////////////////////the email is available so update it
                                                mAuth.getCurrentUser().updateEmail(mEmail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d(TAG, "User email address updated.");
                                                                    Toast.makeText(getActivity(), "email updated", Toast.LENGTH_SHORT).show();
                                                                    mFirebaseMethods.updateEmail(mEmail.getText().toString());
                                                                }
                                                            }
                                                        });
                                            }
                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException: "  +e.getMessage() );
                                        }
                                    }
                                }
                            });





                        }else{
                            Log.d(TAG, "onComplete: re-authentication failed.");
                        }

                    }
                });
    }

    private static final String TAG = "EditProfileFragment";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef, mpic;
    private FirebaseMethods mFirebaseMethods;
    private String userID;
    private StorageReference storageReference;
    private FirebaseStorage storage;





    //EditProfile Fragment widgets
    private EditText mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;


    //vars
    private UserSettings mUserSettings;
    private UserAccountSettings userAccountSettings;
    private Button btnselect,btnuplaod;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mDisplayName = view.findViewById(R.id.display_name);
        mUsername = view.findViewById(R.id.username);
        mWebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mEmail = view.findViewById(R.id.email);
        mPhoneNumber = view.findViewById(R.id.phoneNumber);
        mChangeProfilePhoto = view.findViewById(R.id.changeProfilePhoto);
        mFirebaseMethods = new FirebaseMethods(getActivity());

        setupFirebaseAuth();

        //back arrow for navigating back to "ProfileFragment"
        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        ImageView checkmark = view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save changes.");
                saveProfileSettings();
            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(userAccountSettings);
            }
        });





        return view;
    }


    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before donig so it chekcs to make sure the username chosen is unqiue
     */
    private void saveProfileSettings(){
        final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();
        final String website = mWebsite.getText().toString();
        final String description = mDescription.getText().toString();
        final String email = mEmail.getText().toString();
        final String phoneNumber = mPhoneNumber.getText().toString();


        //case1: if the user made a change to their username
        if(!mUserSettings.getUser().getUsername().equals(username)){

            checkIfUsernameExists(username);
        }
        //case2: if the user made a change to their email
        if(!mUserSettings.getUser().getEmail().equals(email)){

            // step1) Reauthenticate
            //          -Confirm the password and email
            ConfirmPasswordDialog dialog = new ConfirmPasswordDialog();
            dialog.show(getFragmentManager(), getString(R.string.confirm_password_dialog));
            dialog.setTargetFragment(EditProfileFragment.this, 1);


            // step2) check if the email already is registered
            //          -'fetchProvidersForEmail(String email)'
            // step3) change the email
            //          -submit the new email to the database and authentication
        }

        /**
         * change the rest of the settings that do not require uniqueness
         */
        if(!mUserSettings.getSettings().getDisplay_name().equals(displayName)){
            //update displayname
            mFirebaseMethods.updateUserAccountSettings(displayName, null, null, null);
        }
        if(!mUserSettings.getSettings().getWebsite().equals(website)){
            //update website
            mFirebaseMethods.updateUserAccountSettings(null, website, null, null);
        }
        if(!mUserSettings.getSettings().getDescription().equals(description)){
            //update description
            mFirebaseMethods.updateUserAccountSettings(null, null, description, null);
        }
        if(!mUserSettings.getSettings().getPhone_number().equals(phoneNumber)){
            //update phoneNumber
            mFirebaseMethods.updateUserAccountSettings(null, null, null, phoneNumber);
        }
    }



    /**
     * Check is @param username already exists in teh database
     * @param username
     */
    private void checkIfUsernameExists(final String username) {
        Log.d(TAG, "checkIfUsernameExists: Checking if  " + username + " already exists.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    //add the username
                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(), "saved username.", Toast.LENGTH_SHORT).show();

                }
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){
                        Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "That username already exists.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setProfileWidgets(UserSettings userSettings){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEmail());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getPhone_number());
        userAccountSettings = userSettings.getSettings();
        mUserSettings = userSettings;
        //User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();
        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(userSettings.getUser().getEmail());
        mPhoneNumber.setText(userSettings.getSettings().getPhone_number());


    }
       /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mpic = mFirebaseDatabase.getReference("user_account_settings");
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


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


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database
                setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * This section is for uploading the profile picture of user
     */
    private void showUpdateDialog(final UserAccountSettings item) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Set Profile Pic");
        alertDialog.setMessage("Please Choose From Gallery");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_new_menu_layout, null);

       btnselect = add_menu_layout.findViewById(R.id.btnselect);
        btnuplaod = add_menu_layout.findViewById(R.id.btnupload);

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                chooseImage();
            }
        });

        btnuplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeImage(item);

            }
        });


        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_profile);

        alertDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            //    mContext.getString(R.string.dbname_user_account_settings;

                dialog.dismiss();
                //item.setName(editName.getText().toString());
                mpic.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(item);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        alertDialog.show();



    }

    /**
     * Updating the existing Profile Picture
     */
    private void ChangeImage(final UserAccountSettings item) {


        if (saveUrl != null) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Uploading........");
            progressDialog.show();


            String imageName = UUID.randomUUID().toString();
            final StorageReference imageFolder = storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            progressDialog.dismiss();
                            Toast.makeText(getContext(),"Uplaod Succesfull", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    item.setProfile_photo(uri.toString());

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uplaoded...."+progress+"%");

                        }
                    });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null && data != null) {

            saveUrl = data.getData();
            btnselect.setText("Image Selected");
            btnuplaod.setBackgroundColor(Color.GREEN);
            btnuplaod.setText("Click on Upload");
        }
    }

    /**
     * Choose Picture from Gallery
     */
    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select user Profile Picture"),PICK_IMAGE_REQUEST);


    }


}