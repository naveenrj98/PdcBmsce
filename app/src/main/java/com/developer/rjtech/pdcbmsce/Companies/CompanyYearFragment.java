package com.developer.rjtech.pdcbmsce.Companies;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.Year;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.YearViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CompanyYearFragment extends Fragment {

    private static final String TAG = "CompanyYearFragment";

    private Chip cp_cse,cp_ise,cp_ece, cp_eee,cp_mech;
    private Chip cp_internship,cp_tier1,cp_tier2;
    private Chip cp_2020,cp_2021;
    Chip chipy,chipd,chipc;
    private ChipGroup cg_year, cg_department, cg_category;

    private Button btn_apply;
    private List<String> selectSortedMValues = new ArrayList<>();

    //---------Menu ViewHolder--------
    private FirebaseDatabase database;
    private DatabaseReference year;
   private RecyclerView recycler_menu;
   private RecyclerView.LayoutManager layoutManager;
   private TextView textFullName;
   private FirebaseRecyclerAdapter<Year, YearViewHolder> adptor;
    private List<String> selectSortedValues ;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

   private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait, tv_result;
    private LinearLayout ll_filter;
    String[] str;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_company_year, container, false);

        //Auth
        database = FirebaseDatabase.getInstance();
        year = database.getReference("CompanyYear");


        recycler_menu = view.findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        ll_filter = view.findViewById(R.id.ll_filter);

        ll_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showdialog();
//                Intent intent = new Intent(getActivity(), FilterActivity.class);
//                startActivityForResult(intent,101);
            }
        });

        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.year_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        tv_result = view.findViewById(R.id.tv_result);
        tv_result.setText(null);

        recycler_menu.setLayoutManager(new GridLayoutManager(getActivity(), 1));


        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorgreen,
                R.color.color_option_menu,
                R.color.darkRed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMenu();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                loadMenu();

            }
        });


        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101) {
//
//            if (data.getStringExtra("data") != null) {
//                 str = data.getStringExtra("data").split(",");
//                selectSortedValues =  Arrays.asList(str);
//
//               // selectSortedValues = new ArrayList<>(fixedLenghtList);
//                tv_result.setText(data.getStringExtra("data"));
//                System.out.println(Arrays.toString(str));
//                System.out.println((selectSortedValues));
//            }else {
//                tv_result.setText("Non Selected");
//            }
//
//        }
//
//    }

    private void loadMenu() {

//        Query chip_year = year.child(chipy.getText().toString());
//        Query chip_category = year.child(Common.yearSelected)
//                .child("details").child("CompanyCategory");

            FirebaseRecyclerOptions<Year> options = new FirebaseRecyclerOptions.Builder<Year>()
                    .setQuery(year, Year.class)
                    .build();
            adptor = new FirebaseRecyclerAdapter<Year, YearViewHolder>(options) {

                @Override
                protected void onBindViewHolder(@NonNull YearViewHolder holder, int position, @NonNull Year model) {

                    Picasso.with(getActivity()).load(model.getImage())
                            .into(holder.imageView);

                    final Year clickItem = model;
                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {

                            Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), CompanyCategoryActivity.class);
////                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompanyCategoryFragment()).addToBackStack(null).commit();

                            Common.yearSelected = adptor.getRef(position).getKey();
//                        startActivity(intent);

                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    pleasewait.setVisibility(View.GONE);


                }

                @NonNull
                @Override
                public YearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.year_item, parent, false);

                    return new YearViewHolder(itemView);

                }

            };

            adptor.startListening();

            recycler_menu.setAdapter(adptor);

            swipeRefreshLayout.setRefreshing(false);




    }


    private void showdialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Filter By");


        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.filter_dialogue, null);


        cp_cse = add_menu_layout.findViewById(R.id.cp_cse);
        cp_ece = add_menu_layout.findViewById(R.id.cp_ece);
        cp_eee = add_menu_layout.findViewById(R.id.cp_EEE);
        cp_ise = add_menu_layout.findViewById(R.id.cp_ise);
        cp_mech = add_menu_layout.findViewById(R.id.cp_mech);
        cp_internship = add_menu_layout.findViewById(R.id.cp_internship);
        cp_tier1 = add_menu_layout.findViewById(R.id.cp_tier1);
        cp_tier2 = add_menu_layout.findViewById(R.id.cp_tier2);
        cp_2020 = add_menu_layout.findViewById(R.id.cp_2020);
        cp_2021 = add_menu_layout.findViewById(R.id.cp_2021);

        btn_apply = add_menu_layout.findViewById(R.id.btn_apply);


        cg_category = add_menu_layout.findViewById(R.id.cg_category);
        cg_year = add_menu_layout.findViewById(R.id.cg_year);
        cg_department = add_menu_layout.findViewById(R.id.cg_sort);




//        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//                if(isChecked)
//                {
//
//                    selectSortedMValues.add(buttonView.getText().toString());
//                    btn_apply.setBackgroundColor(Color.parseColor("#1293D2"));
//
//
//
//                }else{
//
//                    selectSortedMValues.remove(buttonView.getText().toString());
//
//
//                }
//
//            }
//        };

//        cp_cse.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_ece.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_eee.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_ise.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_mech.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_internship.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_tier1.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_tier2.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_2020.setOnCheckedChangeListener(onCheckedChangeListener);
//        cp_2021.setOnCheckedChangeListener(onCheckedChangeListener);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int i=0; i<cg_category.getChildCount();i++) {
                    chipc = (Chip) cg_category.getChildAt(i);
                    if (chipc.isChecked()) {
                        Log.i("inside if ", i + " chip = " + chipc.getText().toString());

                        Toast.makeText(getActivity(), chipc.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                    for(int j=0; j<cg_department.getChildCount();j++) {
                        chipd = (Chip) cg_department.getChildAt(j);
                        if (chipd.isChecked()){
                            Log.i("inside if ", j+ " chip = " + chipd.getText().toString());

                            Toast.makeText(getActivity(), chipd.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                for(int j=0; j<cg_year.getChildCount();j++) {
                    chipd = (Chip) cg_year.getChildAt(j);
                    if (chipd.isChecked()){
                        Log.i("inside if ", j+ " chip = " + chipd.getText().toString());

                        Toast.makeText(getActivity(), chipd.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });



//////        mItemSelected = (TextView) add_menu_layout.findViewById(R.id.tvItemSelected);
//////
//////        listItems = getResources().getStringArray(R.array.days_of_volunteer_itmes);
//////        checkedItems = new boolean[listItems.length];
//////
//////        mItemSelected.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View view) {
//////                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//////                mBuilder.setTitle("Days of Volunteer");
//////                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
//////                    @Override
//////                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
////////                        if (isChecked) {
////////                            if (!mUserItems.contains(position)) {
////////                                mUserItems.add(position);
////////                            }
////////                        } else if (mUserItems.contains(position)) {
////////                            mUserItems.remove(position);
////////                        }
//////                        if(isChecked){
//////                            mUserItems.add(position);
//////                        }else{
//////                            mUserItems.remove((Integer.valueOf(position)));
//////                            mItemSelected.setText("Select Days of Volunteer");
//////                        }
//////                    }
//////                });
////
////                mBuilder.setCancelable(true);
////                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int which) {
////                        String item = "";
////                        for (int i = 0; i < mUserItems.size(); i++) {
////                            item = item + listItems[mUserItems.get(i)];
////                            if (i != mUserItems.size() - 1) {
////                                item = item + ", ";
////                            }
////                        }
////                        mItemSelected.setText(item);
////                    }
////                });
////
////                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        dialogInterface.dismiss();
////                        mItemSelected.setText("Select Days of Volunteer");
////                    }
////                });
//
//                AlertDialog mDialog = mBuilder.create();
//                mDialog.show();
//            }
//        });




//        editName = add_menu_layout.findViewById(R.id.editName);
//        edtDescription = add_menu_layout.findViewById(R.id.editDescription);
//        edtPrice = add_menu_layout.findViewById(R.id.editPrice);
//        edtDiscount = add_menu_layout.findViewById(R.id.editDiscount);
//





        alertDialog.setView(add_menu_layout);
        //  alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        alertDialog.show();



    }

    @Override
    public void onResume() {
        super.onResume();
        loadMenu();
    }

    @Override
    public void onStop() {
        super.onStop();
        adptor.stopListening();
    }

}
