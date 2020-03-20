package com.developer.rjtech.pdcbmsce.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;

public class YearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  public TextView textMenuName;
 public ImageView imageView;

private ItemClickListener itemClickListener;
    public YearViewHolder(View itemView) {
        super(itemView);

       // textMenuName = itemView.findViewById(R.id.menu_name);
        imageView = itemView.findViewById(R.id.year_image);

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
