package com.ixsans.ixrecyclerviewlib;

import java.util.List;


/**
 * @author ixsan
 */
public abstract class IxDoubleLoadingAdapter<T> extends IxRecyclerViewAdapter<T> {

    private boolean mIsLoadingMore;
    private boolean mIsLoadingPrevious;
    private RecyclerListener mListener;

    public IxDoubleLoadingAdapter(List<T> data, RecyclerListener listener) {
        super(data, true, true); // With Header & With Footer
        this.mListener = listener;
    }


    public void setIsOnLoadingPrevious(boolean onLoading){
        mIsLoadingPrevious = onLoading;
    }

    public void setIsOnLoadingMore(boolean onLoading){
        mIsLoadingMore = onLoading;
    }

    /**
     * Load more listener functionality
     */
    public interface RecyclerListener {

        /**
         * Load more on top
         */
        void onLoadPrevious();

        /**
         * Load more on bottom
         */
        void onLoadMore();

    }
}
