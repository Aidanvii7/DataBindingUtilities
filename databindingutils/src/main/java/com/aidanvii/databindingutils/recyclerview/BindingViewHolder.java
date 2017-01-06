package com.aidanvii.databindingutils.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 */
final class BindingViewHolder<CustomViewDataBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {

    final int bindingResourceId;
    final CustomViewDataBinding viewDataBinding;

    BindingViewHolder(int bindingResourceId, CustomViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.bindingResourceId = bindingResourceId;
        this.viewDataBinding = viewDataBinding;
    }
}
