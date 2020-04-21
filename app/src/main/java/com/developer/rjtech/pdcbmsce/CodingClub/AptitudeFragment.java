package com.developer.rjtech.pdcbmsce.CodingClub;

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
import com.developer.rjtech.pdcbmsce.Model.AptitudeList;
import com.developer.rjtech.pdcbmsce.Model.TechnicalList;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.AptitudeListViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.TechnicalListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class AptitudeFragment extends Fragment {

    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference clist;
    RecyclerView recycler_list;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<AptitudeList, AptitudeListViewHolder> adptor;


    List<String> suggestList = new ArrayList<>();

    String categoryId="";
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_aptitude, container, false);

        //Auth
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("Aptitude");


        recycler_list = view.findViewById(R.id.recycler_aptitude_list);
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


        FirebaseRecyclerOptions<AptitudeList> options = new FirebaseRecyclerOptions.Builder<AptitudeList>()
                .setQuery(clist,AptitudeList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<AptitudeList, AptitudeListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AptitudeListViewHolder developerListViewHolder, int i, @NonNull AptitudeList developerList) {


                developerListViewHolder.a_name.setText(developerList.getName());


                Picasso.with(getActivity()).load(developerList.getImage())
                        .into(developerListViewHolder.a_image); //image ...........
                final AptitudeList clickItem = developerList;

                developerListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), DevelopersDetailsActivity.class);
//
//                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
//                        startActivity(intent);


                    }
                });
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);

            }

            @NonNull
            @Override
            public AptitudeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.aptitude_list_item, parent, false);
                return new AptitudeListViewHolder(itemView);
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
