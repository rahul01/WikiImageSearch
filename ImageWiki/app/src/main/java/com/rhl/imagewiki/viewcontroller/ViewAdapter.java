package com.rhl.imagewiki.viewcontroller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.rhl.imagewiki.R;
import com.rhl.imagewiki.model.Page;
import com.rhl.imagewiki.model.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * view adapter for results
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Page> mPageList;
    private Context mContext;

    /**
     * constructor
     *
     * @param context  context
     * @param pageList page list
     */
    public ViewAdapter(Context context, List<Page> pageList) {
        mPageList = pageList;
        mContext = context;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, null);
        return new ViewHolder(layoutView);
    }

    @Override
    public long getItemId(int position) {
        return mPageList.get(position).hashCode();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mPageList.get(position).imageUrl.equals("")) {//show image if exist
            holder.title.setVisibility(View.GONE);
            Picasso.with(mContext).load(mPageList.get(position).imageUrl).placeholder(R.drawable.loading).error(R.drawable.error).into(holder.image);
        } else {//show placeholder
            holder.title.setText(mPageList.get(position).title);
            holder.title.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(R.drawable.placeholder).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return this.mPageList != null ? this.mPageList.size() : 0;
    }
}