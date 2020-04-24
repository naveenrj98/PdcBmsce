package com.developer.rjtech.pdcbmsce.PDFViewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Model.CompanyCategory;
import com.developer.rjtech.pdcbmsce.Model.CompanyList;
import com.developer.rjtech.pdcbmsce.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class PdfActivity extends AppCompatActivity {

    PDFView pdfView;
    TextView tv_pdfView,pleasewait;

    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference mref;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdfView);
        tv_pdfView = findViewById(R.id.tv_pdfview);
        progressBar = findViewById(R.id.pdf_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        pleasewait = findViewById(R.id.pleaseWait);
        database = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        String companyID = getIntent().getStringExtra("companyID");
        mref = database.getReference("CompanyYear").child(Common.yearSelected)
                .child("details").child("Companies").child(companyID).child("InterviewProcess");

        mref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.VISIBLE);
                CompanyList value = dataSnapshot.getValue(CompanyList.class);
                tv_pdfView.setText(value.getPdfurl());
                String url = tv_pdfView.getText().toString();
                new RetrivePdfStream().execute(url);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }
        class RetrivePdfStream extends AsyncTask<String,Void, InputStream> {
            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream=null;
                try {
                    URL uri = new URL (strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
                    if (urlConnection.getResponseCode() == 200) {

                        inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    }
                } catch (IOException e) {

                    return null;
                }
                return inputStream;
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {

                pdfView.fromStream(inputStream).load();
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);
            }
        }

    }

