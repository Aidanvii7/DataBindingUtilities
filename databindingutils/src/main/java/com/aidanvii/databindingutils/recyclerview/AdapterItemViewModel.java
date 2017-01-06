package com.aidanvii.databindingutils.recyclerview;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 */
interface AdapterItemViewModel {

    void notifyViewModelChanged();

    int getAdapterPosition();

    /**
     * Called when the adapter is creating a new BindingViewHolder which requires a layout resource ID.
     *
     * @return A layout resource corresponding to the viewType.
     */
    int getLayoutId();

    /**
     * Called when the adapter is creating a new BindingViewHolder which requires a viewDataBinding resource ID.
     *
     * @return A layout resource corresponding to the viewType.
     */
    int getBindingId();

    void pauseNotify();

    void resumeNotify();
}