package com.developer.rjtech.pdcbmsce.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.developer.rjtech.pdcbmsce.Interface.ItemClickListener;
import com.developer.rjtech.pdcbmsce.R;

public class InterviewListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

  public TextView i_name;
 public ImageView i_image;

private ItemClickListener itemClickListener;
    public InterviewListViewHolder(View itemView) {
        super(itemView);


        i_image = itemView.findViewById(R.id.interview_image);
        i_name = itemView.findViewById(R.id.interview_text);

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
