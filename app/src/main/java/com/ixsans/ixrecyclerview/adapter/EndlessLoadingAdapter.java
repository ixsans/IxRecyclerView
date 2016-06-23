package com.ixsans.ixrecyclerview.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ixsans.ixrecyclerview.R;
import com.ixsans.ixrecyclerviewlib.IxRecyclerViewAdapter;

import java.util.List;


public class EndlessLoadingAdapter extends IxRecyclerViewAdapter<String> {


    private final int VISIBLE_THRESHOLD = 5;

    private boolean mIsLoadingMore;
    private boolean mIsLoadingPrevious;

    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean enableLoadMore = true;

    public EndlessLoadingAdapter(RecyclerView rv, List<String> data, final IxRecyclerViewAdapter.RecyclerListener listener){
        super(rv, data, false, true);

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if(!enableLoadMore) return;

                    totalItemCount = linearLayoutManager.getItemCount();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    if (mIsLoadingMore) {
                        if (totalItemCount > previousTotal) {
                            mIsLoadingMore = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!mIsLoadingMore && (totalItemCount - visibleItemCount)  <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                        //Reach last item

                        showFooter(true);
                        if (listener != null) {
                            listener.onLoadMore();
                        }
                        mIsLoadingMore = true;
                    }
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String data = getItem(position);
            itemViewHolder.text.setText(data);
        } else if (holder instanceof FooterViewHolder) {

        }
    }

    //region Override Get ViewHolder
    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_example, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(inflater.inflate(R.layout.endless_footer, parent, false));
    }
    //endregion


    //region ViewHolder Header and Footer
    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ItemViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.text);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    //endregion

    public void setEnableLoadMore(boolean enable){
        showFooter(false);
        this.enableLoadMore = enable;
    }

    
}
