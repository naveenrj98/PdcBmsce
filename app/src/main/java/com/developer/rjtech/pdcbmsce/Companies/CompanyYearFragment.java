package com.developer.rjtech.pdcbmsce.Companies;

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

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.Year;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.developer.rjtech.pdcbmsce.ViewHolder.YearViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompanyYearFragment extends Fragment {

    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference year;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<Year, YearViewHolder> adptor;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_company_year, container, false);

        //Auth
        database = FirebaseDatabase.getInstance();
        year = database.getReference("CompanyYear");


        recycler_menu = view.findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);

        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.year_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recycler_menu.setLayoutManager(new GridLayoutManager(getActivity(), 1));


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

        FirebaseRecyclerOptions<Year> options = new FirebaseRecyclerOptions.Builder<Year>()
                .setQuery(year, Year.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<Year, YearViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull YearViewHolder holder, int position, @NonNull Year model) {

                Picasso.with(getActivity()).load(model.getImage())
                        .into(holder.imageView);

                final Year clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getActivity(), CompanyCategoryActivity.class);
////                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompanyCategoryFragment()).addToBackStack(null).commit();

                        Common.yearSelected = adptor.getRef(position).getKey();
//                        startActivity(intent);

                    }
                });
                progressBar.setVisibility(View.GONE);
                pleasewait.setVisibility(View.GONE);


            }

            @NonNull
            @Override
            public YearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.year_item, parent, false);

                return new YearViewHolder(itemView);

            }

        };

        adptor.startListening();

        recycler_menu.setAdapter(adptor);

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
