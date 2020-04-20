package com.developer.rjtech.pdcbmsce.Companies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.developer.rjtech.pdcbmsce.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private Chip cp_cse,cp_ise,cp_ece, cp_eee,cp_mech;
    private Chip cp_internship,cp_tier1,cp_tier2;
    private Chip cp_2020,cp_2021;
    private ChipGroup cg_year, cg_department, cg_category;

    private Button btn_apply;
    private List<String> selectSortedValues = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        cp_cse = findViewById(R.id.cp_cse);
        cp_ece = findViewById(R.id.cp_ece);
        cp_eee = findViewById(R.id.cp_EEE);
        cp_ise = findViewById(R.id.cp_ise);
        cp_mech = findViewById(R.id.cp_mech);
        cp_internship = findViewById(R.id.cp_internship);
        cp_tier1 = findViewById(R.id.cp_tier1);
        cp_tier2 = findViewById(R.id.cp_tier2);
        cp_2020 = findViewById(R.id.cp_2020);
        cp_2021 = findViewById(R.id.cp_2021);

        btn_apply = findViewById(R.id.btn_apply);
        btn_apply.setBackgroundColor(Color.GRAY);
        btn_apply.setEnabled(false);

        cg_category = findViewById(R.id.cg_category);
        cg_year = findViewById(R.id.cg_year);
        cg_department = findViewById(R.id.cg_sort);





        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked)
                {

                    selectSortedValues.add(buttonView.getText().toString());
                    btn_apply.setBackgroundColor(Color.parseColor("#1293D2"));
                    btn_apply.setEnabled(true);


                }else{

                    selectSortedValues.remove(buttonView.getText().toString());
                    btn_apply.setEnabled(false);
                    btn_apply.setBackgroundColor(Color.GRAY);
                }

            }
        };

        cp_cse.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_ece.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_eee.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_ise.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_mech.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_internship.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_tier1.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_tier2.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_2020.setOnCheckedChangeListener(onCheckedChangeListener);
        cp_2021.setOnCheckedChangeListener(onCheckedChangeListener);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", selectSortedValues.toString());

                setResult(101, intent);
                finish();
            }
        });
    }
}
