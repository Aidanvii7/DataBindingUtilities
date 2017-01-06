package com.aidanvii.databindingutils.recyclerview;

import android.support.v7.util.SortedList;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 21/10/16.
 */
final class SortedListBindingAdapter<AdapterItemViewModelImpl extends AdapterItemViewModel> extends BindingAdapter<AdapterItemViewModelImpl> {

    private SortedList<AdapterItemViewModelImpl> items;

    SortedListBindingAdapter(@NotNull BindingAdapterHelper<AdapterItemViewModelImpl> adapterHelper) {
        super(adapterHelper);
    }

    @NotNull
    @Override
    protected final AdapterItemViewModelImpl getViewModel(int position) {
        return items.get(position);
    }

    @NotNull
    private SortedList<AdapterItemViewModelImpl> getItems() {
        return items;
    }

    void setItems(@NotNull SortedList<AdapterItemViewModelImpl> items) {
        this.items = items;
    }

    @Override
    public final int getItemCount() {
        return getItems().size();
    }
}
