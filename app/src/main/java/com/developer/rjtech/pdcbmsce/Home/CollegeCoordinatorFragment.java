package com.developer.rjtech.pdcbmsce.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.rjtech.pdcbmsce.Profile.DevelopersDetailsActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Companies.CompanyDetailsActivity;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.CompanyList;
import com.developer.rjtech.pdcbmsce.Model.CollegeCoordinatorList;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.CompanyListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.CollegeCoordinatorListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollegeCoordinatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollegeCoordinatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseDatabase database;
    DatabaseReference clist;
    RecyclerView recycler_list;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<CollegeCoordinatorList, CollegeCoordinatorListViewHolder> adptor;


    List<String> suggestList = new ArrayList<>();

    String categoryId="";
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_college_coordinator_list, container, false);
        //Auth
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("Coordinators/College");


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
                loadListCoordinators();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadListCoordinators();
            }
        });

        return view;


    }
    private void loadListCoordinators() {


        FirebaseRecyclerOptions<CollegeCoordinatorList> options = new FirebaseRecyclerOptions.Builder<CollegeCoordinatorList>()
                .setQuery(clist,CollegeCoordinatorList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<CollegeCoordinatorList, CollegeCoordinatorListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CollegeCoordinatorListViewHolder ccListViewHolder, int i, @NonNull CollegeCoordinatorList ccList) {


                ccListViewHolder.c_name.setText(ccList.getName());
                ccListViewHolder.c_designation.setText(ccList.getDesignation());
                ccListViewHolder.c_phone.setText(ccList.getPhone());
                ccListViewHolder.c_email.setText(ccList.getEmail());


                Picasso.with(getActivity()).load(ccList.getImage())
                        .into(ccListViewHolder.c_image); //image ...........
                final CollegeCoordinatorList clickItem = ccList;

                ccListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {




                    }
                });
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);

            }

            @NonNull
            @Override
            public CollegeCoordinatorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.college_coordinator_list_item, parent, false);
                return new CollegeCoordinatorListViewHolder(itemView);
            }
        };




        adptor.startListening();

        recycler_list.setAdapter(adptor);

        swipeRefreshLayout.setRefreshing(false);

    }
}
