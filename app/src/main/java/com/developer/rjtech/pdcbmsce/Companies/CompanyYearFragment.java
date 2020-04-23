package com.developer.rjtech.pdcbmsce.Companies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Companies.CompanyCategoryFragment;
import com.developer.rjtech.pdcbmsce.Companies.CompanyDetailsActivity;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.CompanyList;
import com.developer.rjtech.pdcbmsce.Model.Year;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.CompanyListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.YearViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CompanyYearFragment extends Fragment {

    Chip chipy,chipd,chipc;
    private ChipGroup cg_year, cg_department, cg_category;

    private Button btn_apply;
    private List<String> selectSortedMValues = new ArrayList<>();

    //---------Menu ViewHolder--------
    private FirebaseDatabase database;
    private DatabaseReference clist;
    private RecyclerView recycler_list;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    private FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder> adptor;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    String categoryId="";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;


    private TextView pleasewait, tv_year, tv_category, tv_department;
    private LinearLayout ll_filter;
    private String str, str_category, str_department ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_company_year, container, false);


        // chipy.setText("2020");
        //Auth
        str_department=null;
        str_category=null;
        str = "2020";
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("CompanyYear").child(str)
                .child("details").child("Companies");

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
    private void loadListCompany() {

        FirebaseRecyclerOptions<CompanyList> options;



        clist = database.getReference("CompanyYear").child(str)
                .child("details").child("Companies");

        if(str_category!=null )
        {

            Query query = clist.orderByChild("ccID").equalTo(str_category);
            options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                    .setQuery(query,CompanyList.class)
                    .build();


        } else if (str_department != null) {

            Query query = clist.orderByChild("depID").equalTo(str_department);
            options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                    .setQuery(query, CompanyList.class)
                    .build();

        } else {
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
                        Common.companyCategorySelected = adptor.getRef(position).getKey();
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
                        tv_category.setText(str_category);
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
                        tv_department.setText(str_department);
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
                        tv_year.setText(str);
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
        loadListCompany();
    }


    @Override
    public void onStop() {
        super.onStop();
        adptor.stopListening();
    }
}
