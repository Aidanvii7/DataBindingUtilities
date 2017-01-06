package com.aidanvii.databindingutils.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.aidanvii.databindingutils.utils.Preconditions.checkArgumentIsNotNull;


/**
 * Created by aidan.mcwilliams@vimn.com on 21/11/16.
 */
abstract class BindingAdapter<AdapterItemViewModelImpl extends AdapterItemViewModel> extends RecyclerView.Adapter<BindingViewHolder> {

    private final BindingAdapterHelper<AdapterItemViewModelImpl> adapterHelper;

    protected BindingAdapter(@NotNull BindingAdapterHelper<AdapterItemViewModelImpl> adapterHelper) {
        this.adapterHelper = checkArgumentIsNotNull(adapterHelper);
        this.adapterHelper.lateInitAdapter(this);
    }

    @Override
    public final BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapterHelper.onCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(BindingViewHolder holder, int position) {
        adapterHelper.onBindViewHolder(holder, getViewModel(position), position);
    }

    @Override
    public final void onBindViewHolder(BindingViewHolder holder, int position, List<Object> payloads) {
        int[] changedProperties = getChangedProperties(payloads);
        if (changedProperties != null) {
            adapterHelper.onBindViewHolder(holder, getViewModel(position), position, changedProperties);
        } else {
            onBindViewHolder(holder, position);
        }
    }

    @Nullable
    private int[] getChangedProperties(final List<Object> payloads) {
        if (payloads.size() == 1) {
            Object payload = payloads.get(0);
            if (payload instanceof int[]) {
                return (int[]) payload;
            } else {
                throw new IllegalArgumentException("change payload is not an int[]");
            }
        }
        return null;
    }

    @Override
    public final int getItemViewType(int position) {
        return adapterHelper.getItemViewType(position);
    }

    @Override
    public final void onViewAttachedToWindow(BindingViewHolder holder) {
        // TODO allow the implementor to do something with this
        adapterHelper.onViewAttachedToWindow(holder);
    }

    @Override
    public final void onViewDetachedFromWindow(BindingViewHolder holder) {
        // TODO allow the implementor to do something with this
        adapterHelper.onViewDetachedFromWindow(holder);
    }

    @Override
    public final void onViewRecycled(BindingViewHolder holder) {
        // TODO allow the implementor to do something with this, e.g. release resources!
        adapterHelper.onViewRecycled(holder);
    }

    @Override
    public final boolean onFailedToRecycleView(BindingViewHolder holder) {
        // TODO do anything with this?
        return adapterHelper.onFailedToRecycleView(holder);
    }

    /**
     * Called when the adapter is viewDataBinding an item to the {@link BindingViewHolder} which requires a viewDataBinding resource ID.
     * <p>The {@link AdapterItemViewModel} should be relative to the position.</p>
     *
     * @param position the position in the data set to determine the {@link AdapterItemViewModel} and viewDataBinding resource ID.
     * @return
     */
    @NotNull
    protected abstract AdapterItemViewModelImpl getViewModel(int position);
}
