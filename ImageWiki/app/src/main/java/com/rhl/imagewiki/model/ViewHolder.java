package com.rhl.imagewiki.model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rhl.imagewiki.R;

/**
 * view holder
 */
public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView title;
    public ImageView image;
    public FrameLayout container;

    /**
     * constructor
     *
     * @param itemView view representing single item in recycler view
     */
    public ViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        title = (TextView) itemView.findViewById(R.id.page_title);
        image = (ImageView) itemView.findViewById(R.id.page_image);
        container = (FrameLayout) itemView.findViewById(R.id.item_container);
    }

    @Override
    public void onClick(View view) {
        Log.d("ViewHolder", "Item clicked :: " + getAdapterPosition());
        // do nothing
    }
}
