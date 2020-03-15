package com.developer.rjtech.pdcbmsce.Companies;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.rjtech.pdcbmsce.Common.Common;
import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;
import com.developer.rjtech.pdcbmsce.ViewHolder.MenuViewHolder;
import com.developer.rjtech.pdcbmsce.models.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CompaniesFragment extends Fragment {


    //---------Menu ViewHolder--------
    FirebaseDatabase database;
    DatabaseReference category;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    TextView textFullName;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adptor;


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


        recycler_menu.setLayoutManager(new GridLayoutManager(getContext(),3));

        loadMenu();
        return view;
    }

    private void loadMenu(){

        adptor = new FirebaseRecyclerAdapter<Category,
                MenuViewHolder>(Category.class,
                R.layout.menu_item,
                MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Toast.makeText(getActivity(),""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), FoodDetail.class);
                        intent.putExtra("CategoryId",adptor.getRef(position).getKey());
                        startActivity(intent);

                    }
                });
            }
        };
        recycler_menu.setAdapter(adptor);
    }
}
