package com.aidanvii.databindingutils.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by aidan.mcwilliams@vimn.com on 07/12/16.
 */

final class DefaultViewDataBindingFactory implements ViewDataBindingFactory {

    @Override
    public ViewDataBinding createViewDataBinding(LayoutInflater inflater, int layoutId, ViewGroup parent) {
        return DataBindingUtil.inflate(inflater, layoutId, parent, false);
    }
}
