package com.developer.rjtech.pdcbmsce.BackupFiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompanyListActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);

        //Auth
        database = FirebaseDatabase.getInstance();
        clist = database.getReference("CompanyYear").child(Common.yearSelected)
        .child("details").child("Companies");


        recycler_list = findViewById(R.id.recycler_company_list);
        recycler_list.setHasFixedSize(true);


        recycler_list.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));




                //Search
//        materialSearchBar = findViewById(R.id.searchBar);
//        materialSearchBar.setHint("Enter Your Company");
//        loadSuggest();
//        materialSearchBar.setLastSuggestions(suggestList);
//        materialSearchBar.setCardViewElevation(10);
//        materialSearchBar.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                List<String> suggest = new ArrayList<>();
//                for (String search : suggestList) {
//
//                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
//
//                        suggest.add(search);
//                    }
//                    materialSearchBar.setLastSuggestions(suggest);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//            @Override
//            public void onSearchStateChanged(boolean enabled) {
//                if (!enabled) {
//                    recycler_list.setAdapter(adptor);
//                }
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//
//                //starSearch(text);
//
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//
//            }
//        });
//

        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorgreen,
                R.color.color_option_menu,
                R.color.darkRed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(getIntent() != null){
                    categoryId = getIntent().getStringExtra("CategoryId");

                }
                if (!categoryId.isEmpty() && categoryId != null) {
                    loadListCompany(categoryId);
                }

            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if(getIntent() != null){
                    categoryId = getIntent().getStringExtra("CategoryId");

                }
                if (!categoryId.isEmpty() && categoryId != null) {
                    loadListCompany(categoryId);
                }

            }
        });


    }

    private void loadListCompany(String categoryId) {


        FirebaseRecyclerOptions<CompanyList> options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                .setQuery(clist.orderByChild("ccID").equalTo(categoryId),CompanyList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CompanyListViewHolder companyListViewHolder, int i, @NonNull CompanyList companyList) {

                Picasso.with(getApplicationContext()).load(companyList.getImage())
                        .into(companyListViewHolder.imageView);
                final CompanyList clickItem = companyList;
                companyListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getApplicationContext(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CompanyDetailsActivity.class);
//                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
                        Common.companyCategorySelected = adptor.getRef(position).getKey();
                        startActivity(intent);

                    }
                });

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
    protected void onResume() {
        super.onResume();
        loadListCompany(categoryId);
    }

    @Override
    public void onStop() {
        super.onStop();
        adptor.stopListening();
    }

//    private void starSearch(CharSequence text) {
//
//        searchadptor = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,
//                R.layout.menu_item,
//                MenuViewHolder.class,
//                category.orderByChild("Name").equalTo(text.toString())) {
//            @Override
//            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
//
//                menuViewHolder.textMenuName.setText(category.getName());
//                Picasso.with(getApplicationContext()).load(category.getImage())
//                        .into(menuViewHolder.imageView);
//                final Category clickItem = category;
//                menuViewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//
//                        Toast.makeText(getApplicationContext(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), CompanyDetailsActivity.class);
//                        intent.putExtra("CategoryId", searchadptor.getRef(position).getKey());
//                        startActivity(intent);
//                    }
//                });
//
//            }
//        };
//        recycler_menu.setAdapter(searchadptor);
//
//    }

    private void loadSuggest() {

        clist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Category item = postSnapshot.getValue(Category.class);
                    suggestList.add(item.getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void loadListCompany() {

        FirebaseRecyclerOptions<CompanyList> options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                .setQuery(clist, CompanyList.class)
                .build();

        adptor = new FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull CompanyListViewHolder holder, int position, @NonNull CompanyList model) {

                Picasso.with(getApplicationContext()).load(model.getImage())
                        .into(holder.imageView);
                final CompanyList clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getApplicationContext(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CompanyDetailsActivity.class);
//                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
                        Common.companyCategorySelected = adptor.getRef(position).getKey();
                        startActivity(intent);

                    }
                });


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

    }
}
