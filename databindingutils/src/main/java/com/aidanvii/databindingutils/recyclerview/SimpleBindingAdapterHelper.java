package com.aidanvii.databindingutils.recyclerview;

import org.jetbrains.annotations.Nullable;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 */
public final class SimpleBindingAdapterHelper
        <SimpleAdapterItemViewModel extends SimpleAdapterItem<SimpleAdapterItemViewModel>>
        extends BindingAdapterHelper<SimpleAdapterItemViewModel> {

    public SimpleBindingAdapterHelper(@Nullable AdapterHelperPlugin adapterHelperPlugin, @Nullable ViewDataBindingFactory viewDataBindingFactory) {
        super(adapterHelperPlugin, viewDataBindingFactory);
    }

    @Override
    final void onBindViewHolder(final BindingViewHolder holder, final SimpleAdapterItemViewModel viewModel, final int position) {
        viewModel.adapterBindStart(getAdapter());
        viewModel.setAdapterPosition(position);
        bindViewModel(holder, viewModel, position);
        viewModel.adapterBindEnd();
    }

    @Override
    final void onBindViewHolder(BindingViewHolder holder, final SimpleAdapterItemViewModel viewModel, int position, int... changedProperties) {
        viewModel.adapterBindStart(getAdapter());
        viewModel.setAdapterPosition(position);
        bindViewModel(holder, viewModel, position, changedProperties);
        viewModel.adapterBindEnd();
    }
}