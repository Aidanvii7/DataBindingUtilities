package com.aidanvii.databindingutils.recyclerview;

import com.aidanvii.databindingutils.utils.suppliers.Late;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 */

interface AdapterNotifier<AdapterItemViewModelImpl extends AdapterItemViewModel> {

    void initAdapterItem(AdapterItemViewModel adapterItem);

    void notifyAdapterItemChanged();

    void notifyAdapterItemPropertiesChanged(int... properties);

    void adapterBindStart(final @NotNull BindingAdapter<AdapterItemViewModelImpl> adapter);

    void adapterBindEnd();

    void pauseNotify();

    void resumeNotify();

    class AdapterNotifierImpl<AdapterItemViewModelImpl extends AdapterItemViewModel> implements AdapterNotifier<AdapterItemViewModelImpl> {

        private final Late<AdapterItemViewModel> lateAdapterItem = Late.create();
        //        private final List<Integer> pendingChangedProperties = new ArrayList<>();
        private int[] pendingChangedProperties = new int[0];
        // TODO try to make not weak and fix architecture!
        // TODO should be able to 'bind' to muptliple adapters at the same time!
        private WeakReference<BindingAdapter<AdapterItemViewModelImpl>> weakAdapter;
        // TODO replace notifyPaused with 2 handler objects
        private boolean notifyPaused;
        private boolean adapterBinding;


        AdapterNotifierImpl() {
            weakAdapter = new WeakReference<>(null);
            adapterBinding = false;
        }

        @Override
        public void initAdapterItem(AdapterItemViewModel adapterItem) {
            this.lateAdapterItem.set(adapterItem);
        }

        @Override
        public void notifyAdapterItemChanged() {
            final BindingAdapter<AdapterItemViewModelImpl> adapter = getAdapter();
            adapter.notifyItemChanged(lateAdapterItem.get().getAdapterPosition());
        }

        @Override
        public void notifyAdapterItemPropertiesChanged(int... properties) {
            if (!tryPostponePropertyChanges(properties)) {
                final BindingAdapter<AdapterItemViewModelImpl> adapter = getAdapter();
                adapter.notifyItemChanged(lateAdapterItem.get().getAdapterPosition(), properties);
            }
        }

        @Override
        public void adapterBindStart(@NotNull BindingAdapter<AdapterItemViewModelImpl> adapter) {
            tryUpdateAdapter(adapter);
            adapterBinding = true;
        }

        @Override
        public void adapterBindEnd() {
            adapterBinding = false;
        }

        @Override
        public final void pauseNotify() {
            notifyPaused = true;
        }

        @Override
        public final void resumeNotify() {
            notifyPaused = false;
            notifyAdapterItemPropertiesChanged(pendingChangedProperties);
            resetChangedPendingProperties(0);
        }

        private BindingAdapter<AdapterItemViewModelImpl> getAdapter() {
            checkAdapterState();
            return weakAdapter.get();
        }

        private boolean tryPostponePropertyChanges(int... changedProperties) {
            if (notifyPaused) {
                postponePropertyChanges(changedProperties);
            }
            return notifyPaused;
        }

        private void postponePropertyChanges(int... changedProperties) {
            resetChangedPendingProperties(changedProperties.length);
            System.arraycopy(changedProperties, 0, pendingChangedProperties, 0, changedProperties.length);
        }

        private void resetChangedPendingProperties(int size) {
            pendingChangedProperties = new int[size];
        }

        private void checkAdapterState() {
            if (adapterBinding || weakAdapter.get() == null) {
                throw new IllegalStateException("cannot notify adapter during a onBindViewHolder call");
            }
        }

        private void tryUpdateAdapter(BindingAdapter<AdapterItemViewModelImpl> adapter) {
            // TODO do we need this? trim the fat! save the cycles! #perfmatters
            weakAdapter = new WeakReference<>(adapter);
        }
    }
}
