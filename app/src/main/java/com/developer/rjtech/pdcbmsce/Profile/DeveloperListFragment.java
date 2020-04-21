package com.developer.rjtech.pdcbmsce.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Companies.CompanyDetailsActivity;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.CompanyList;
import com.developer.rjtech.pdcbmsce.Model.DeveloperList;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.CompanyListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.DeveloperListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DeveloperListFragment extends Fragment {


    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference clist;
    RecyclerView recycler_list;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<DeveloperList, DeveloperListViewHolder> adptor;


    List<String> suggestList = new ArrayList<>();

    String categoryId="";
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_developer_list, container, false);

        //Auth
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("Developers");


        recycler_list = view.findViewById(R.id.recycler_developer_list);
        recycler_list.setHasFixedSize(true);

        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.list_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recycler_list.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorgreen,
                R.color.color_option_menu,
                R.color.darkRed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadListCompany();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadListCompany();
            }
        });

        ImageView backArrow = view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        return view;
    }

    private void loadListCompany() {


        FirebaseRecyclerOptions<DeveloperList> options = new FirebaseRecyclerOptions.Builder<DeveloperList>()
                .setQuery(clist,DeveloperList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<DeveloperList, DeveloperListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DeveloperListViewHolder developerListViewHolder, int i, @NonNull DeveloperList developerList) {


                developerListViewHolder.d_name.setText(developerList.getName());
                developerListViewHolder.d_designation.setText(developerList.getDesignation());
                developerListViewHolder.d_college.setText(developerList.getCollege());

                Picasso.with(getActivity()).load(developerList.getImage())
                        .into(developerListViewHolder.d_image); //image ...........
                final DeveloperList clickItem = developerList;

                developerListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DevelopersDetailsActivity.class);

                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
                        startActivity(intent);


                    }
                });
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);

            }

            @NonNull
            @Override
            public DeveloperListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.developer_list_item, parent, false);
                return new DeveloperListViewHolder(itemView);
            }
        };




        adptor.startListening();

        recycler_list.setAdapter(adptor);

        swipeRefreshLayout.setRefreshing(false);

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
