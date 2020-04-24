package com.developer.rjtech.pdcbmsce.Companies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.developer.rjtech.pdcbmsce.Model.CompanyList;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.CompanyListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder> adptor;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
     MaterialSearchBar materialSearchBar;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Auth
        database = FirebaseDatabase.getInstance();
        category =database.getReference("CompanyYear").child("2020")
                .child("details").child("Companies");

        recycler_menu = findViewById(R.id.recycler_search);
        recycler_menu.setHasFixedSize(true);


        recycler_menu.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));




        materialSearchBar = findViewById(R.id.search_new_bar);


       loadSuggest();
      materialSearchBar.setLastSuggestions(suggestList);
       materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {

                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {

                        suggest.add(search);
                    }
                    materialSearchBar.setLastSuggestions(suggest);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    recycler_menu.setAdapter(searchadptor);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                starSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }



    private void loadSuggest() {

        category.addListenerForSingleValueEvent(new ValueEventListener() {
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
    private void starSearch(CharSequence text) {

        Query search = category.orderByChild("name").equalTo(text.toString());

        FirebaseRecyclerOptions<CompanyList> options = new FirebaseRecyclerOptions.Builder<CompanyList>()
                .setQuery(search,CompanyList.class)
                .build();


        searchadptor = new FirebaseRecyclerAdapter<CompanyList, CompanyListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CompanyListViewHolder companyListViewHolder, int i, @NonNull CompanyList companyList) {
                Picasso.with(getBaseContext()).load(companyList.getImage())
                        .into(companyListViewHolder.imageView);
                final CompanyList clickItem = companyList;
                companyListViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getApplicationContext(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                        //   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        Intent intent = new Intent(getApplicationContext(), CompanyDetailsActivity.class);
                        intent.putExtra("CategoryId", searchadptor.getRef(position).getKey());
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
        searchadptor.startListening();

        recycler_menu.setAdapter(searchadptor);




    }


    @Override
    public void onResume() {
        super.onResume();
        if (searchadptor != null) {
            searchadptor.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(searchadptor!=null)
        {
            searchadptor.stopListening();
        }

    }

}
