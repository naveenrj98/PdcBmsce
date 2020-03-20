package com.developer.rjtech.pdcbmsce.Companies;

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
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.CodingClub.CodingClubFragment;
import com.developer.rjtech.pdcbmsce.Home.HomeFragment;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.developer.rjtech.pdcbmsce.Model.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CompaniesFragment extends Fragment {


    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adptor;

    //--------Search Functionality---------
    FirebaseRecyclerAdapter<Category, MenuViewHolder> searchadptor;
    List<String> suggestList = new ArrayList<>();
//    MaterialSearchBar materialSearchBar;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_companies, container, false);




        //Auth
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        recycler_menu = view.findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);


        recycler_menu.setLayoutManager(new GridLayoutManager(getContext(), 3));



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

//        //Search
//        materialSearchBar = view.findViewById(R.id.searchBar);
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
//                for (String search : suggest) {
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
//                    recycler_menu.setAdapter(adptor);
//                }
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//
//                starSearch(text);
//
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//
//            }
//        });
        return view;
    }

//    private void starSearch(CharSequence text) {
//
//            searchadptor = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,
//                    R.layout.menu_item,
//                    MenuViewHolder.class, category) {
//                @Override
//                protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
//
//                    menuViewHolder.textMenuName.setText(category.getName());
//
//                    Picasso.with(getContext()).load(category.getImage())
//                            .into(menuViewHolder.imageView);
//                    final Category clickItem = category;
//                    menuViewHolder.setItemClickListener(new ItemClickListener() {
//                        @Override
//                        public void onClick(View view, int position, boolean isLongClick) {
//
//                            Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getActivity(), CompanyDetailsActivity.class);
//                            intent.putExtra("CategoryId", adptor.getRef(position).getKey());
//                            startActivity(intent);
//                        }
//                });
//
//                }
//            };
//            recycler_menu.setAdapter(searchadptor);
//
//    }

    private void loadSuggest() {

            category.addValueEventListener(new ValueEventListener() {
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

    private void loadMenu() {

            FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                    .setQuery(category,Category.class)
                    .build();

           adptor = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {

               @Override
               protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {

                   Picasso.with(getContext()).load(model.getImage())
                        .into(holder.imageView);
                final Category clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(), "" + clickItem.getName(), Toast.LENGTH_SHORT).show();
                     //   getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        Intent intent = new Intent(getActivity(), CompanyDetailsActivity.class);
                        intent.putExtra("CategoryId", adptor.getRef(position).getKey());
                        startActivity(intent);

                    }
                });


               }

               @NonNull
               @Override
               public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                   View itemView = LayoutInflater.from(parent.getContext())
                           .inflate(R.layout.menu_item, parent, false);
                   return new MenuViewHolder(itemView);
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
