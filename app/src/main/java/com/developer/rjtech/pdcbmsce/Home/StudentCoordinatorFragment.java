package com.developer.rjtech.pdcbmsce.Home;

import android.content.Intent;
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

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.DeveloperList;
import com.developer.rjtech.pdcbmsce.Model.StudentCoordinatorList;
import com.developer.rjtech.pdcbmsce.Profile.DevelopersDetailsActivity;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.DeveloperListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.StudentCoordinatorListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StudentCoordinatorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference clist;
    RecyclerView recycler_list;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<StudentCoordinatorList, StudentCoordinatorListViewHolder> adptor;


    List<String> suggestList = new ArrayList<>();

    String categoryId="";
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_coordinator, container, false);

        //Auth
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("Coordinators/Student");


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

        return view;
    }

    private void loadListCompany() {


        FirebaseRecyclerOptions<StudentCoordinatorList> options = new FirebaseRecyclerOptions.Builder<StudentCoordinatorList>()
                .setQuery(clist,StudentCoordinatorList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<StudentCoordinatorList, StudentCoordinatorListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StudentCoordinatorListViewHolder scListViewHolder, int i, @NonNull StudentCoordinatorList scList) {


                scListViewHolder.c_name.setText(scList.getName());
                scListViewHolder.c_email.setText(scList.getEmail());
                scListViewHolder.c_phone.setText(scList.getPhone());
                scListViewHolder.c_dept.setText(scList.getDept());
                scListViewHolder.c_year.setText(scList.getYear());

                Picasso.with(getActivity()).load(scList.getImage())
                        .into(scListViewHolder.c_image); //image ...........
                final StudentCoordinatorList clickItem = scList;

                scListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);

            }

            @NonNull
            @Override
            public StudentCoordinatorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_coordinator_list_item, parent, false);
                return new StudentCoordinatorListViewHolder(itemView);
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
