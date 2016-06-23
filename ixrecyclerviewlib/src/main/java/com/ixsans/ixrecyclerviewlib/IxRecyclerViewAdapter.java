package com.ixsans.ixrecyclerviewlib;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;


public abstract class IxRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    protected List<T> data;

    protected RecyclerView recyclerView;
    private boolean mWithHeader;
    private boolean mWithFooter;


    public IxRecyclerViewAdapter(List<T> data, boolean withHeader, boolean withFooter) {
        this.data = data;
        mWithHeader = withHeader;
        mWithFooter = withFooter;
    }

    public IxRecyclerViewAdapter(RecyclerView recyclerView, List<T> data, boolean withHeader, boolean withFooter){
        this.data = data;
        this.recyclerView = recyclerView;
        mWithHeader = withHeader;
        mWithFooter = withFooter;
    }

    //region Get View
    protected abstract RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent);
    //endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            return getItemView(inflater, parent);
        } else if (viewType == TYPE_HEADER) {
            return getHeaderView(inflater, parent);
        } else if (viewType == TYPE_FOOTER) {
            return getFooterView(inflater, parent);
        }else {
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = data.size();

        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    protected T getItem(int position) {
        return mWithHeader ? data.get(position - 1) : data.get(position);
    }

    public void showFooter(boolean show){
        mWithFooter = show;
        notifyItemRemoved(getItemCount());
    }

    public void showHeader(boolean show){
        mWithFooter = show;
        notifyItemRemoved(getItemCount());
    }

    public interface RecyclerListener {
        void onLoadPrevious();
        void onLoadMore();
    }



}
