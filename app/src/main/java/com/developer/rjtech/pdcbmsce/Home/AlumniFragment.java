package com.developer.rjtech.pdcbmsce.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.AlumniList;

import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.AlumniListViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class AlumniFragment extends Fragment {


    FirebaseDatabase database;
    DatabaseReference alist;
    RecyclerView recycler_alumni_list;
   FirebaseRecyclerAdapter<AlumniList, AlumniListViewHolder> adptor;


    List<String> suggestList = new ArrayList<>();

    String categoryId="";
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_alumni_list, container, false);
        //Auth
        database = FirebaseDatabase.getInstance();
        alist = database.getReference("Alumni");


        recycler_alumni_list = view.findViewById(R.id.recycler_developer_list);
        recycler_alumni_list.setHasFixedSize(true);

        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.list_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recycler_alumni_list.setLayoutManager(new GridLayoutManager(getActivity(), 1));

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


        FirebaseRecyclerOptions<AlumniList> options = new FirebaseRecyclerOptions.Builder<AlumniList>()
                .setQuery(alist,AlumniList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<AlumniList, AlumniListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AlumniListViewHolder ccListViewHolder, int i, @NonNull AlumniList ccList) {


                ccListViewHolder.a_name.setText(ccList.getName());
                ccListViewHolder.a_dept.setText(ccList.getDepartment());
                ccListViewHolder.a_designation.setText(ccList.getDesignation());


                Picasso.with(getActivity()).load(ccList.getImage())
                        .into(ccListViewHolder.a_image); //image ...........
                final AlumniList clickItem = ccList;

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
            public AlumniListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.alumni_list_item, parent, false);
                return new AlumniListViewHolder(itemView);
            }
        };




        adptor.startListening();

        recycler_alumni_list.setAdapter(adptor);

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
