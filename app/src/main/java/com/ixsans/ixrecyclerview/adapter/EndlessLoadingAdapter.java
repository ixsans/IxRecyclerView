package com.ixsans.ixrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ixsans.ixrecyclerview.R;
import com.ixsans.ixrecyclerviewlib.IxEndlessLoadingAdapter;

import java.util.List;


/**
 * @author ixsan
 */
public class EndlessLoadingAdapter extends IxEndlessLoadingAdapter<String> {

    public EndlessLoadingAdapter(RecyclerView rv, List<String> data, RecyclerListener listener) {
        super(rv, data, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            String data = getItem(position);
            itemViewHolder.txtTitle.setText(data);
        } else if (holder instanceof FooterViewHolder) {
            //handle your custom logic here
        }
    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.row_item, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(inflater.inflate(R.layout.endless_footer, parent, false));
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.title);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
