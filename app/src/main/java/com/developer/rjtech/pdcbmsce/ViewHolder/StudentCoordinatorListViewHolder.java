package com.developer.rjtech.pdcbmsce.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;

public class StudentCoordinatorListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  public TextView c_name,c_email,c_phone, c_dept, c_year;
 public ImageView c_image;

private ItemClickListener itemClickListener;
    public StudentCoordinatorListViewHolder(View itemView) {
        super(itemView);

       // textMenuName = itemView.findViewById(R.id.menu_name);
        c_image = itemView.findViewById(R.id.coordinator_image);
        c_name = itemView.findViewById(R.id.coordinator_name);
        c_email = itemView.findViewById(R.id.coordinator_email);
        c_phone = itemView.findViewById(R.id.coordinator_phone);
        c_dept = itemView.findViewById(R.id.coordinator_dept);
        c_year = itemView.findViewById(R.id.coordinator_year);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
