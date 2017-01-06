package com.aidanvii.databindingutils.recyclerview;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by aidan.mcwilliams@vimn.com on 21/10/16.
 */
final class SimpleListBindingAdapter<AdapterItemViewModelImpl extends AdapterItemViewModel> extends BindingAdapter<AdapterItemViewModelImpl> {

    private List<AdapterItemViewModelImpl> items;

    SimpleListBindingAdapter(@NotNull BindingAdapterHelper<AdapterItemViewModelImpl> adapterHelper) {
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

    public void setItems(@NotNull List<AdapterItemViewModelImpl> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        return getItems().size();
    }
}
