package com.developer.rjtech.pdcbmsce.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;

import androidx.recyclerview.widget.RecyclerView;

public class AlumniListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  public TextView a_name, a_designation, a_dept;
 public ImageView a_image;

private ItemClickListener itemClickListener;
    public AlumniListViewHolder(View itemView) {
        super(itemView);

       // textMenuName = itemView.findViewById(R.id.menu_name);
        a_image = itemView.findViewById(R.id.alumni_image);
        a_name = itemView.findViewById(R.id.alumni_name);
        a_dept = itemView.findViewById(R.id.alumni_dept);
        //c_phone = itemView.findViewById(R.id.coordinator_phone);
        a_designation = itemView.findViewById(R.id.alumni_designation);
        //c_email = itemView.findViewById(R.id.coordinator_email);
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
