package com.developer.rjtech.pdcbmsce.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developer.rjtech.pdcbmsce.R;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.FacultyCoordinatorList;
import com.developer.rjtech.pdcbmsce.ViewHolder.FacultyCoordinatorListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FacultyCoordinatorFragment extends Fragment {
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
    FirebaseRecyclerAdapter<FacultyCoordinatorList, FacultyCoordinatorListViewHolder> adptor;


    List<String> suggestList = new ArrayList<>();

    String categoryId="";
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_faculty_coordinator_list, container, false);
        //Auth
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("Coordinators/Faculty");


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


        FirebaseRecyclerOptions<FacultyCoordinatorList> options = new FirebaseRecyclerOptions.Builder<FacultyCoordinatorList>()
                .setQuery(clist,FacultyCoordinatorList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<FacultyCoordinatorList, FacultyCoordinatorListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FacultyCoordinatorListViewHolder ccListViewHolder, int i, @NonNull FacultyCoordinatorList ccList) {


                ccListViewHolder.c_name.setText(ccList.getName());
                ccListViewHolder.c_designation.setText(ccList.getDesignation());
                ccListViewHolder.c_email.setText(ccList.getEmail());
                ccListViewHolder.c_phone.setText(ccList.getPhone());
                ccListViewHolder.c_dept.setText(ccList.getDept());


                Picasso.with(getActivity()).load(ccList.getImage())
                        .into(ccListViewHolder.c_image); //image ...........
                final FacultyCoordinatorList clickItem = ccList;

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
            public FacultyCoordinatorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.faculty_coordinator_list_item, parent, false);
                return new FacultyCoordinatorListViewHolder(itemView);
            }
        };




        adptor.startListening();

        recycler_list.setAdapter(adptor);

        swipeRefreshLayout.setRefreshing(false);

    }
    @Override
    public void onResume() {
        super.onResume();
        loadListCoordinators();
    }

    @Override
    public void onStop() {
        super.onStop();
        adptor.stopListening();
    }
}
