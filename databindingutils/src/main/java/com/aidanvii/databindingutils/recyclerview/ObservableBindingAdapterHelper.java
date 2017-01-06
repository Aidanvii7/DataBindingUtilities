package com.aidanvii.databindingutils.recyclerview;

import org.jetbrains.annotations.Nullable;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 */
public final class ObservableBindingAdapterHelper
        <ObservableAdapterItemViewModel extends ObservableAdapterItem<ObservableAdapterItemViewModel>>
        extends BindingAdapterHelper<ObservableAdapterItemViewModel> {


    public ObservableBindingAdapterHelper(@Nullable AdapterHelperPlugin adapterHelperPlugin, @Nullable ViewDataBindingFactory viewDataBindingFactory) {
        super(adapterHelperPlugin, viewDataBindingFactory);
    }

    public ObservableBindingAdapterHelper() {
        super(null, null);
    }

    @Override
    final void onBindViewHolder(final BindingViewHolder holder, final ObservableAdapterItemViewModel viewModel, final int position) {
        viewModel.adapterBindStart(getAdapter());
        viewModel.setAdapterPosition(position);
        bindViewModel(holder, viewModel, position);
        viewModel.adapterBindEnd();
    }

    @Override
    final void onBindViewHolder(BindingViewHolder holder, ObservableAdapterItemViewModel viewModel, int position, int... changedProperties) {
        viewModel.adapterBindStart(getAdapter());
        viewModel.setAdapterPosition(position);
        bindViewModel(holder, viewModel, position, changedProperties);
        viewModel.adapterBindEnd();
    }
}
