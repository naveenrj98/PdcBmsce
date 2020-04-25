package com.developer.rjtech.pdcbmsce.Companies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Common.Common;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.CompanyList;
import com.developer.rjtech.pdcbmsce.Profile.AccountSettingsActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.CompanyListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CompaniesFragment extends Fragment {

    Chip chipy,chipd,chipc;
    private ChipGroup cg_year, cg_department, cg_category;

       //---------Menu ViewHolder--------
    private FirebaseDatabase database;
    private DatabaseReference clist;
    private RecyclerView recycler_list;
    private FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder> adptor;

    //--------Search Functionality---------

    String categoryId="";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;


    //--------Search Functionality---------
    RecyclerView recycler_menu;
    FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;


    private TextView pleasewait, tv_year, tv_category, tv_department;
    private LinearLayout ll_filter;
    private String str, str_category, str_department ;

    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_company_year, container, false);

        //Auth
        str_department=null;
        str_category=null;
        str = "2020";
        Common.yearSelected = str;
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("CompanyYear").child(str)
                .child("details").child("Companies");

        //------------------------------------Navigation related code------------------------------------------------------------
        toolbar = view.findViewById(R.id.profileToolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        materialSearchBar = view.findViewById(R.id.search_new_bar);

        materialSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

//        materialSearchBar = view.findViewById(R.id.search_new_bar);
//        recycler_menu = view.findViewById(R.id.recycler_search);
//        recycler_menu.setHasFixedSize(true);
//        recycler_menu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//
//
//        loadSuggest();
//        materialSearchBar.setLastSuggestions(suggestList);
//        materialSearchBar.setCardViewElevation(10);
//        materialSearchBar.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                List<String> suggest = new ArrayList<>();
//                for (String search : suggestList) {
//
//                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
//
//                        suggest.add(search);
//                    }
//                    materialSearchBar.setLastSuggestions(suggest);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//            @Override
//            public void onSearchStateChanged(boolean enabled) {
//                if (!enabled) {
//                    recycler_menu.setAdapter(searchadptor);
//                }
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//
//                starSearch(text);
//
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//
//            }
//        });
//



        recycler_list = view.findViewById(R.id.recycler_menu);
        recycler_list.setHasFixedSize(true);

        ll_filter = view.findViewById(R.id.ll_filter);
        ll_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_category=null;
                str_department=null;
                showdialog();

            }
        });
        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.year_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        tv_year = view.findViewById(R.id.tv_result);
        tv_year.setText(str);
        tv_category = view.findViewById(R.id.tv_category);
        tv_category.setText(str_category);
        tv_department = view.findViewById(R.id.tv_department);
        tv_department.setText(str_department);

        recycler_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorgreen,
                R.color.color_option_menu,
                R.color.darkRed);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                str_category=null;
                str_department=null;
                tv_category.setText(null);
                tv_department.setText(str_department);
                loadListCompany();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadListCompany();
            }
        });

        return view;
    }


    private void loadSuggest() {

        clist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Category item = postSnapshot.getValue(Category.class);
                    suggestList.add(item.getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    private void starSearch(CharSequence text) {

        Query search = clist.orderByChild("name").equalTo(text.toString());

        FirebaseRecyclerOptions<CompanyList> options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                .setQuery(search,CompanyList.class)
                .build();


        searchadptor = new FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CompanyListViewHolder companyListViewHolder, int i, @NonNull CompanyList companyList) {
                Picasso.with(getActivity()).load(companyList.getImage())
                        .into(companyListViewHolder.imageView);
                final CompanyList clickItem = companyList;
                companyListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        //   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        Intent intent = new Intent(getActivity(), CompanyDetailsActivity.class);
                        intent.putExtra("CategoryId", searchadptor.getRef(position).getKey());
                        startActivity(intent);

                    }
                });



            }

            @NonNull
            @Override
            public CompanyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.company_list_item, parent, false);
                return new CompanyListViewHolder(itemView);


            }
        };
        searchadptor.startListening();

        recycler_menu.setAdapter(searchadptor);




    }






    private void loadListCompany() {

        FirebaseRecyclerOptions<CompanyList> options;



        Common.yearSelected = str;
        clist = database.getReference("CompanyYear").child(str)
                .child("details").child("Companies");

        if(str_category!=null )
        {
            tv_category.setText(str_category);
            if (str_department == null) {
                tv_department.setText("");
            }
            Common.companyCategorySelected = str_category;
            Query query = clist.orderByChild("ccID").equalTo(str_category);
            options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                    .setQuery(query,CompanyList.class)
                    .build();


        } else if (str_department != null ) {
            tv_department.setText(str_department);
            if (str_category == null) {
                tv_category.setText("");
            }

            Query query = clist.orderByChild("depID").equalTo(str_department);
            options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                    .setQuery(query, CompanyList.class)
                    .build();

        } else {
            tv_year.setText(str);
            tv_category.setText("");
            tv_department.setText("");
            options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                    .setQuery(clist.orderByChild("name"), CompanyList.class)
                    .build();
        }



        adptor = new FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CompanyListViewHolder companyListViewHolder, int i, @NonNull CompanyList companyList) {

                Picasso.with(getActivity()).load(companyList.getImage())
                        .into(companyListViewHolder.imageView);
                final CompanyList clickItem = companyList;
                companyListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), CompanyDetailsActivity.class);
                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
                      Common.companySelected = adptor.getRef(position).getKey();
                        startActivity(intent);

                    }
                });
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);

            }

            @NonNull
            @Override
            public CompanyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.company_list_item, parent, false);
                return new CompanyListViewHolder(itemView);
            }
        };


        adptor.startListening();

        recycler_list.setAdapter(adptor);

        swipeRefreshLayout.setRefreshing(false);

    }
    private void showdialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Filter By");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.filter_dialogue, null);

        Chip cp_cse = add_menu_layout.findViewById(R.id.cp_cse);
        Chip cp_ece = add_menu_layout.findViewById(R.id.cp_ece);
        Chip cp_eee = add_menu_layout.findViewById(R.id.cp_EEE);
        Chip cp_ise = add_menu_layout.findViewById(R.id.cp_ise);
        Chip cp_mech = add_menu_layout.findViewById(R.id.cp_mech);
        Chip cp_internship = add_menu_layout.findViewById(R.id.cp_internship);
        Chip cp_tier1 = add_menu_layout.findViewById(R.id.cp_tier1);
        Chip cp_tier2 = add_menu_layout.findViewById(R.id.cp_tier2);
        Chip cp_2020 = add_menu_layout.findViewById(R.id.cp_2020);
        Chip cp_2021 = add_menu_layout.findViewById(R.id.cp_2021);


        cg_category = add_menu_layout.findViewById(R.id.cg_category);
        cg_year = add_menu_layout.findViewById(R.id.cg_year);
        cg_department = add_menu_layout.findViewById(R.id.cg_sort);

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_developerss);

        alertDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                for(int i=0; i<cg_category.getChildCount();i++) {
                    chipc = (Chip) cg_category.getChildAt(i);
                    if (chipc.isChecked()) {
                        Log.i("inside if ", i + " chip = " + chipc.getText().toString());

                        Toast.makeText(getActivity(), chipc.getText().toString(), Toast.LENGTH_SHORT).show();

                        str_category = chipc.getText().toString();


                        loadListCompany();

                        break;
                    }

                }
                for(int j=0; j<cg_department.getChildCount();j++) {
                    chipd = (Chip) cg_department.getChildAt(j);
                    if (chipd.isChecked()){
                        Log.i("inside if ", j+ " chip = " + chipd.getText().toString());

                        Toast.makeText(getActivity(), chipd.getText().toString(), Toast.LENGTH_SHORT).show();
                        str_department = chipd.getText().toString();
                        loadListCompany();
                        break;

                    }
                }

                for(int j=0; j<cg_year.getChildCount();j++) {
                    chipy= (Chip) cg_year.getChildAt(j);
                    if (chipy.isChecked()){
                        Log.i("inside if ", j+ " chip = " + chipy.getText().toString());

                        Toast.makeText(getActivity(), chipy.getText().toString(), Toast.LENGTH_SHORT).show();
                        str = chipy.getText().toString();

                        loadListCompany();

                        break;
                    }
                }

                dialog.dismiss();

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




    @Override
    public void onResume() {
        super.onResume();
        if (searchadptor != null) {
            searchadptor.startListening();
        }
        loadListCompany();
    }


    @Override
    public void onStop() {
        super.onStop();
        if(searchadptor!=null)
        {
            searchadptor.stopListening();
        }
        adptor.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AccountSettingsActivity.class);
                startActivity(intent);

                return true;
            case R.id.action_shreapp:
                Toast.makeText(getActivity(), "Thanks for sharing", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }
