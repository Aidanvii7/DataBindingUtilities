package com.aidanvii.databindingutils.recyclerview;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by aidan.mcwilliams@vimn.com on 07/12/16.
 */

public interface ViewDataBindingFactory<T extends ViewDataBinding> {

    T createViewDataBinding(LayoutInflater inflater, int layoutId, ViewGroup parent);

}
