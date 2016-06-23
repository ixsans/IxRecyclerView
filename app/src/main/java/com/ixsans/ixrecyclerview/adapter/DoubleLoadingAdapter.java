package com.ixsans.ixrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ixsans.ixrecyclerview.R;
import com.ixsans.ixrecyclerviewlib.IxRecyclerViewAdapter;

import java.util.List;

import static com.ixsans.ixrecyclerviewlib.IxDoubleLoadingAdapter.RecyclerListener;


/**
 * @author ixsan
 */
public class DoubleLoadingAdapter extends IxRecyclerViewAdapter<String> {

    private boolean mIsLoadingMore;
    private boolean mIsLoadingPrevious;
    private RecyclerListener mListener;

    public DoubleLoadingAdapter(List<String> data, RecyclerListener listener) {
        super(data, true, true);  // with header and footer
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String data = getItem(position);
            itemViewHolder.txtTitle.setText(data);
        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder)holder).pbLoadPrevious.setVisibility(mIsLoadingPrevious ? View.VISIBLE : View.GONE);
            ((HeaderViewHolder)holder).btnLoadPrevious.setVisibility(mIsLoadingPrevious ? View.GONE : View.VISIBLE);
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder)holder).pbLoadMore.setVisibility(mIsLoadingMore ? View.VISIBLE : View.GONE);
            ((FooterViewHolder)holder).btnLoadMore.setVisibility(mIsLoadingMore ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.row_item, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.load_more_header, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(inflater.inflate(R.layout.load_more_footer, parent, false));
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.title);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        ProgressBar pbLoadPrevious;
        Button btnLoadPrevious;
        
        public HeaderViewHolder(View itemView) {
            super(itemView);
            btnLoadPrevious = (Button) itemView.findViewById(R.id.btn_loadprevious);
            btnLoadPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setIsOnLoadingPrevious(true);
                    notifyDataSetChanged();
                    //let notifyDataSetChanged work done, then reset loading status to false
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            setIsOnLoadingPrevious(false);
                        }
                    });

                    mListener.onLoadPrevious();
                }
            });
            pbLoadPrevious = (ProgressBar) itemView.findViewById(R.id.pb_loadprevious);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        ProgressBar pbLoadMore;
        Button btnLoadMore;

        public FooterViewHolder(View itemView) {
            super(itemView);
            btnLoadMore = (Button) itemView.findViewById(R.id.btn_loadnext);
            btnLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setIsOnLoadingMore(true);
                    notifyDataSetChanged();

                    //let notifyDataSetChanged work done, then reset loading status to false
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            setIsOnLoadingMore(false);
                        }
                    });
                    mListener.onLoadMore();
                }
            });
            pbLoadMore = (ProgressBar) itemView.findViewById(R.id.pb_loadnext);

        }
    }

    public void setIsOnLoadingPrevious(boolean onLoading){
        mIsLoadingPrevious = onLoading;
    }

    public void setIsOnLoadingMore(boolean onLoading){
        mIsLoadingMore = onLoading;
    }
}
