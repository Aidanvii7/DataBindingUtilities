package com.aidanvii.databindingutils.recyclerview;

import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 */
public abstract class SimpleAdapterItem<AdapterItemViewModelImpl extends AdapterItemViewModel> implements AdapterItemViewModel {

    public interface Factory<Model, SimpleAdapterItemViewModelImpl extends SimpleAdapterItem> {
        @NotNull
        SimpleAdapterItemViewModelImpl create(@NotNull Model model);
    }

    private final AdapterNotifier<AdapterItemViewModelImpl> adapterNotifier;
    private int adapterPosition = RecyclerView.NO_POSITION;

    @VisibleForTesting
    SimpleAdapterItem(AdapterNotifier<AdapterItemViewModelImpl> adapterNotifier) {
        this.adapterNotifier = adapterNotifier;
        this.adapterNotifier.initAdapterItem(this);
    }

    public SimpleAdapterItem() {
        this.adapterNotifier = new AdapterNotifier.AdapterNotifierImpl<>();
        this.adapterNotifier.initAdapterItem(this);
    }

    void adapterBindStart(@NotNull BindingAdapter<AdapterItemViewModelImpl> adapter) {
        adapterNotifier.adapterBindStart(adapter);
    }

    void adapterBindEnd() {
        adapterNotifier.adapterBindEnd();
    }

    @Override
    public void pauseNotify() {
        adapterNotifier.pauseNotify();
    }

    @Override
    public final void resumeNotify() {
        adapterNotifier.resumeNotify();
    }

    @Override
    public final void notifyViewModelChanged() {
        adapterNotifier.notifyAdapterItemChanged();
    }

    @Override
    public int getAdapterPosition() {
        return adapterPosition;
    }

    void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }
}
