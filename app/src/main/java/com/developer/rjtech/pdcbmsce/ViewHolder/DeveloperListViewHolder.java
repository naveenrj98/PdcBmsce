package com.developer.rjtech.pdcbmsce.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;

public class DeveloperListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  public TextView d_name, d_designation, d_college;
 public ImageView d_image,d_github;

private ItemClickListener itemClickListener;
    public DeveloperListViewHolder(View itemView) {
        super(itemView);

       // textMenuName = itemView.findViewById(R.id.menu_name);
        d_image = itemView.findViewById(R.id.developer_image);
        d_name = itemView.findViewById(R.id.developer_name);
        d_college = itemView.findViewById(R.id.developer_college);
        d_designation = itemView.findViewById(R.id.developer_designation);
        d_github = itemView.findViewById(R.id.developer_github);
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
