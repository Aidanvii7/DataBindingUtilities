package com.aidanvii.databindingutils.recyclerview;

import android.databinding.ObservableList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by aidan.mcwilliams@vimn.com on 21/10/16.
 */
final class ObservableListBindingAdapter<AdapterItemViewModelImpl extends AdapterItemViewModel> extends BindingAdapter<AdapterItemViewModelImpl> {

    private ObservableList<AdapterItemViewModelImpl> items;

    ObservableListBindingAdapter(@NotNull BindingAdapterHelper<AdapterItemViewModelImpl> adapterHelper) {
        super(adapterHelper);
    }

    @NotNull
    @Override
    protected final AdapterItemViewModelImpl getViewModel(int position) {
        return items.get(position);
    }

    @NotNull
    private List<AdapterItemViewModelImpl> getItems() {
        return items;
    }

    public void setItems(@NotNull ObservableList<AdapterItemViewModelImpl> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        return getItems().size();
    }
}
