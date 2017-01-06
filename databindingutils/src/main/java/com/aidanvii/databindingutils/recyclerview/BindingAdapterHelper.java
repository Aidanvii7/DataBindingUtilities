package com.aidanvii.databindingutils.recyclerview;

import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aidanvii.databindingutils.utils.suppliers.Late;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 */
abstract class BindingAdapterHelper<AdapterItemViewModelImpl extends AdapterItemViewModel> {

    private final Late<BindingAdapter<AdapterItemViewModelImpl>> lateAdapter = Late.create();
    private final ViewTypeHandler viewTypeHandler;
    private final ViewDataBindingFactory viewDataBindingFactory;
    private final AdapterHelperPlugin adapterHelperPlugin;

    protected BindingAdapterHelper(@Nullable AdapterHelperPlugin adapterHelperPlugin, @Nullable ViewDataBindingFactory viewDataBindingFactory) {
        this.adapterHelperPlugin = adapterHelperPlugin != null ? adapterHelperPlugin : AdapterHelperPlugin.builder().build();
        viewTypeHandler = this.adapterHelperPlugin.hasMultiple() ? new MultiViewTypeHandler() : new SingleViewTypeHandler();
        this.viewDataBindingFactory = viewDataBindingFactory != null ? viewDataBindingFactory : new DefaultViewDataBindingFactory();
    }

    void lateInitAdapter(@NotNull BindingAdapter<AdapterItemViewModelImpl> adapter) {
        lateAdapter.set(adapter);
    }

    @NotNull
    BindingAdapter<AdapterItemViewModelImpl> getAdapter() {
        return lateAdapter.get();
    }

    final BindingViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final BindingViewHolder viewHolder = createBindingViewHolder(parent, viewType);

        adapterHelperPlugin.onViewHolderCreated(viewHolder.viewDataBinding);
        return viewHolder;
    }

    private BindingViewHolder createBindingViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        final int layoutId = viewTypeHandler.getLayoutId(viewType);
        final int bindingId = viewTypeHandler.getBindingId(layoutId);
        final ViewDataBinding viewDataBinding = viewDataBindingFactory.createViewDataBinding(inflater, layoutId, parent);

        return new BindingViewHolder<>(bindingId, viewDataBinding);
    }

    final void bindViewModel(final BindingViewHolder viewHolder, final AdapterItemViewModelImpl adapterItemViewModel, final int position) {
        viewHolder.viewDataBinding.setVariable(viewHolder.bindingResourceId, adapterItemViewModel);
        viewHolder.viewDataBinding.executePendingBindings();
        adapterHelperPlugin.onViewHolderBound(viewHolder.viewDataBinding, adapterItemViewModel, position);
    }

    final void bindViewModel(final BindingViewHolder viewHolder, final AdapterItemViewModelImpl adapterItemViewModel, final int position, final int...
            payloads) {
        for (int payload : payloads) {
            viewHolder.viewDataBinding.notifyPropertyChanged(payload);
        }
        viewHolder.viewDataBinding.executePendingBindings();
        adapterHelperPlugin.onViewHolderPartialBound(viewHolder.viewDataBinding, adapterItemViewModel, position, payloads);
    }

    final int getItemViewType(final int position) {
        return viewTypeHandler.getItemViewType(position);
    }

    abstract void onBindViewHolder(BindingViewHolder holder, AdapterItemViewModelImpl viewModel, int position);

    abstract void onBindViewHolder(BindingViewHolder holder, AdapterItemViewModelImpl viewModel, int position, int... changedProperties);

    void onViewAttachedToWindow(BindingViewHolder holder) {
        adapterHelperPlugin.onViewAttachedToWindow(holder.viewDataBinding);
    }

    void onViewDetachedFromWindow(BindingViewHolder holder) {
        adapterHelperPlugin.onViewDetachedFromWindow(holder.viewDataBinding);
    }

    void onViewRecycled(BindingViewHolder holder) {
        adapterHelperPlugin.onViewRecycled(holder.viewDataBinding);
    }

    boolean onFailedToRecycleView(BindingViewHolder holder) {
        return adapterHelperPlugin.onFailedToRecycleView(holder.viewDataBinding);
    }

    private interface ViewTypeHandler {

        int getItemViewType(final int position);

        int getLayoutId(int viewType);

        int getBindingId(int layoutId);
    }

    class SingleViewTypeHandler implements ViewTypeHandler {

        private static final int NOT_SET = -1;
        private int bindingId = NOT_SET;
        private int layoutId = NOT_SET;
        @NotNull
        private WeakReference<AdapterItemViewModelImpl> cachedWeakViewModel = new WeakReference<>(null);

        @Override
        public int getItemViewType(int position) {
            final AdapterItemViewModelImpl viewModel = getViewModel();
            lateInitResourceId(viewModel);
            lateInitBindingId(viewModel);
            return 0;
        }

        @NotNull
        private AdapterItemViewModelImpl getViewModel() {
            // TODO refactor?
            final AdapterItemViewModelImpl cachedViewModel = cachedWeakViewModel.get();
            final AdapterItemViewModelImpl viewModel;
            if (cachedViewModel != null) {
                viewModel = cachedViewModel;
            } else {
                viewModel = getAdapter().getViewModel(0);
                cachedWeakViewModel = new WeakReference<>(viewModel);
            }
            return viewModel;
        }

        @Override
        public int getLayoutId(int viewType) {
            return layoutId;
        }

        @Override
        public int getBindingId(int layoutId) {
            return bindingId;
        }

        private void lateInitResourceId(AdapterItemViewModelImpl viewModel) {
            if (layoutId == NOT_SET) {
                layoutId = viewModel.getLayoutId();
            }
        }

        private void lateInitBindingId(AdapterItemViewModelImpl viewModel) {
            if (bindingId == NOT_SET) {
                bindingId = viewModel.getBindingId();
            }
        }
    }

    class MultiViewTypeHandler implements ViewTypeHandler {

        private final SparseIntArray cachedBindingIds = new SparseIntArray();

        @Override
        public int getItemViewType(int position) {
            // RecyclerView will call through here a lot. We have to consider that first we are doing a lookup
            // inside a collection of unknown size followed by a binary search in the SparseIntArray
            final AdapterItemViewModelImpl viewModel = getAdapter().getViewModel(position);
            final int layoutId = viewModel.getLayoutId();
            cachedBindingIds.put(layoutId, viewModel.getBindingId());
            return layoutId;
        }

        @Override
        public int getLayoutId(int viewType) {
            return viewType;
        }

        @Override
        public int getBindingId(int layoutId) {
            return cachedBindingIds.get(layoutId);
        }
    }
}
