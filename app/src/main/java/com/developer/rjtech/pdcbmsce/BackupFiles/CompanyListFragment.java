package com.developer.rjtech.pdcbmsce.BackupFiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.developer.rjtech.pdcbmsce.BackupFiles.CompanyDetailsActivity;
import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.CompanyList;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.CompanyListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CompanyListFragment extends Fragment {


    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference clist;
    RecyclerView recycler_list;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder> adptor;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    String categoryId="";
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_company_list, container, false);

        //Auth
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("CompanyYear").child(Common.yearSelected)
                .child("details").child("Companies");


        recycler_list = view.findViewById(R.id.recycler_company_list);
        recycler_list.setHasFixedSize(true);

        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.list_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recycler_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorgreen,
                R.color.color_option_menu,
                R.color.darkRed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                categoryId = getArguments().getString("CategoryId");
                loadListCompany(categoryId);
//                if(getArguments() != null){
//
//
//                    categoryId = getArguments().getString("CategoryId");
//
//                }
//                if (!categoryId.isEmpty() && categoryId != null) {
//                    loadListCompany(categoryId);
//                }

            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                categoryId = getArguments().getString("CategoryId");


                loadListCompany(categoryId);


//                if(getArguments() != null){
//                    categoryId = getArguments().getString("CategoryId");
//
//                }
//                if (!categoryId.isEmpty() && categoryId != null) {
//                    loadListCompany(categoryId);
//                }

            }
        });

        return view;
    }

    private void loadListCompany(String categoryId) {


        FirebaseRecyclerOptions<CompanyList> options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                .setQuery(clist.orderByChild("ccID").equalTo(categoryId),CompanyList.class)
                .build();

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

    @Override
    public void onResume() {
        super.onResume();
        loadListCompany(categoryId);
    }

    @Override
    public void onStop() {
        super.onStop();
        adptor.stopListening();
    }
}
