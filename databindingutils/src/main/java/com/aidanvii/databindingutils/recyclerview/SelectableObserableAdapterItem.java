package com.aidanvii.databindingutils.recyclerview;

import android.databinding.Bindable;

import com.aidanvii.databindingutils.BR;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by aidan.mcwilliams@vimn.com on 06/12/16.
 */

public abstract class SelectableObserableAdapterItem<AdapterItemViewModelImpl extends AdapterItemViewModel>
        extends ObservableAdapterItem<AdapterItemViewModelImpl> {

    private final AtomicBoolean selected = new AtomicBoolean(false);

    @Bindable
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        trySetProperty(this.selected, selected, BR.selected);
    }

    public void toggleSelected() {
        setSelected(!selected.get());
    }
}
