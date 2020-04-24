package com.developer.rjtech.pdcbmsce.BackupFiles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.CompanyCategory;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.CompanyCategoryViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CompanyCategoryFragment extends Fragment {
    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference ccategory;
    RecyclerView recycler_category;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<CompanyCategory, CompanyCategoryViewHolder> adptor;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_company_category, container, false);

        //Auth
        database = FirebaseDatabase.getInstance();
        ccategory = database.getReference("CompanyYear").child(Common.yearSelected)
                .child("details").child("CompanyCategory");


        recycler_category = view.findViewById(R.id.recycler_company_category);
        recycler_category.setHasFixedSize(true);

        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.ccategory_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recycler_category.setLayoutManager(new GridLayoutManager(getActivity(), 1));

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
    private void loadMenu() {

        FirebaseRecyclerOptions<CompanyCategory> options = new FirebaseRecyclerOptions.Builder<CompanyCategory>()
                .setQuery(ccategory, CompanyCategory.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<CompanyCategory, CompanyCategoryViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CompanyCategoryViewHolder holder, int position, @NonNull CompanyCategory model) {

                Picasso.with(getActivity()).load(model.getImage())
                        .into(holder.imageView);
                final CompanyCategory clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();

                        //Put the value
                        CompanyListFragment ldf = new CompanyListFragment ();
                        Bundle args = new Bundle();
                        args.putString("CategoryId", adptor.getRef(position).getKey());
                        ldf.setArguments(args);

//Inflate the fragment'
     getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).addToBackStack(null).commit();


//                        Intent intent = new Intent(getActivity(), CompanyListActivity.class);
//                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompanyListFragment()).commit();

                        Common.companyCategorySelected = adptor.getRef(position).getKey();
//                        startActivity(intent);

                    }
                });
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);


            }

            @NonNull
            @Override
            public CompanyCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.company_category_item, parent, false);
                return new CompanyCategoryViewHolder(itemView);
            }
        };
        adptor.startListening();

        recycler_category.setAdapter(adptor);

        swipeRefreshLayout.setRefreshing(false);
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
