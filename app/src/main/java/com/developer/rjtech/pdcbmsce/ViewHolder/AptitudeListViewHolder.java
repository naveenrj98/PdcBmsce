package com.developer.rjtech.pdcbmsce.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;

public class AptitudeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  public TextView a_name;
 public ImageView a_image;

private ItemClickListener itemClickListener;
    public AptitudeListViewHolder(View itemView) {
        super(itemView);


        a_image = itemView.findViewById(R.id.aptitude_image);
        a_name = itemView.findViewById(R.id.aptitude_text);

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
