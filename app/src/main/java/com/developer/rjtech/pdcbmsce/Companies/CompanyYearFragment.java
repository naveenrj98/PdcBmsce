package com.developer.rjtech.pdcbmsce.Companies;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompanyYearFragment extends Fragment {

    //---------Menu ViewHolder--------
    private FirebaseDatabase database;
    private DatabaseReference year;
   private RecyclerView recycler_menu;
   private RecyclerView.LayoutManager layoutManager;
   private TextView textFullName;
   private FirebaseRecyclerAdapter<Year, YearViewHolder> adptor;
    private List<String> selectSortedValues ;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

   private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView pleasewait, tv_result;
    private LinearLayout ll_filter;
    String[] str;


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
        ll_filter = view.findViewById(R.id.ll_filter);

        ll_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivityForResult(intent,101);
            }
        });

        pleasewait = view.findViewById(R.id.pleaseWait);
        progressBar = view.findViewById(R.id.year_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        tv_result = view.findViewById(R.id.tv_result);
        tv_result.setText(null);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {

            if (data.getStringExtra("data") != null) {
                 str = data.getStringExtra("data").split(",");
                selectSortedValues =  Arrays.asList(str);

               // selectSortedValues = new ArrayList<>(fixedLenghtList);
                tv_result.setText(data.getStringExtra("data"));
                System.out.println(Arrays.toString(str));
                System.out.println((selectSortedValues));
            }else {
                tv_result.setText("Non Selected");
            }

        }

    }

    private void loadMenu() {


        if(selectSortedValues == null)
        {
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
        } else if (selectSortedValues.get(0).equals("2020") || selectSortedValues.get(0).equals("2021")) {

            FirebaseRecyclerOptions<Year> options = new FirebaseRecyclerOptions.Builder<Year>()
                    .setQuery(year.child(selectSortedValues.get(0)), Year.class)
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
