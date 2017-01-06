package com.aidanvii.databindingutils.recyclerview;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.repacked.google.common.util.concurrent.AtomicDouble;
import android.support.v7.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static com.aidanvii.databindingutils.utils.Preconditions.checkArgumentIsNotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 * TODO write tests!!
 */
public abstract class ObservableAdapterItem<AdapterItemViewModelImpl extends AdapterItemViewModel> extends BaseObservable implements AdapterItemViewModel {

    public interface Factory<Model, ObservableAdapterItemViewModelImpl extends ObservableAdapterItem> {
        @NotNull
        ObservableAdapterItemViewModelImpl create(@NotNull Model model);
    }

    @NotNull
    private final AdapterNotifier<AdapterItemViewModelImpl> adapterNotifier;
    private int adapterPosition = RecyclerView.NO_POSITION;

    /**
     * test constructor
     *
     * @param adapterNotifier
     */
    ObservableAdapterItem(AdapterNotifier<AdapterItemViewModelImpl> adapterNotifier) {
        this.adapterNotifier = checkArgumentIsNotNull(adapterNotifier);
        this.adapterNotifier.initAdapterItem(this);
    }

    public ObservableAdapterItem() {
        this.adapterNotifier = new AdapterNotifier.AdapterNotifierImpl<>();
        this.adapterNotifier.initAdapterItem(this);
    }

    protected <T> void trySetProperty(AtomicReference<T> curValue, T newValue, int property) {
        if (curValue.get() != newValue) {
            curValue.set(newValue);
            notifyPropertyChanged(property);
        }
    }

    protected void trySetProperty(AtomicBoolean curValue, boolean newValue, int property) {
        if (curValue.get() != newValue) {
            curValue.set(newValue);
            notifyPropertyChanged(property);
        }
    }

    protected void trySetProperty(ObservableBoolean curValue, boolean newValue, int property) {
        if (curValue.get() != newValue) {
            curValue.set(newValue);
            notifyPropertyChanged(property);
        }
    }

    protected void trySetProperty(AtomicInteger curValue, int newValue, int property) {
        if (curValue.get() != newValue) {
            curValue.set(newValue);
            notifyPropertyChanged(property);
        }
    }

    protected void trySetProperty(AtomicLong curValue, long newValue, int property) {
        if (curValue.get() != newValue) {
            curValue.set(newValue);
            notifyPropertyChanged(property);
        }
    }

    protected void trySetProperty(AtomicDouble curValue, double newValue, int property) {
        if (curValue.get() != newValue) {
            curValue.set(newValue);
            notifyPropertyChanged(property);
        }
    }

    protected <T> void trySetProperty(Runnable assignProperty, T curValue, T newValue, int property) {
        if (curValue != newValue) {
            assignProperty.run();
            notifyPropertyChanged(property);
        }
    }

    void adapterBindStart(@NotNull BindingAdapter<AdapterItemViewModelImpl> adapter) {
        adapterNotifier.adapterBindStart(adapter);
    }

    void adapterBindEnd() {
        adapterNotifier.adapterBindEnd();
    }

    @Override
    public synchronized void notifyChange() {
        notifyViewModelChanged();
    }

    @Override
    public final synchronized void notifyViewModelChanged() {
        if (notifyAdapterOnChange()) {
            adapterNotifier.notifyAdapterItemChanged();
        } else {
            super.notifyChange();
        }
    }

    @Override
    public int getAdapterPosition() {
        return adapterPosition;
    }

    void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    @Override
    public final void notifyPropertyChanged(int property) {
        if (notifyAdapterOnPropertyChange(property)) {
            adapterNotifier.notifyAdapterItemPropertiesChanged(property);
        } else {
            super.notifyPropertyChanged(property);
        }
    }

    @Override
    public final void pauseNotify() {
        adapterNotifier.pauseNotify();
    }

    @Override
    public final void resumeNotify() {
        adapterNotifier.resumeNotify();
    }

    public abstract boolean notifyAdapterOnChange();

    protected boolean notifyAdapterOnPropertyChange(int fieldId) {
        return false;
    }
}
