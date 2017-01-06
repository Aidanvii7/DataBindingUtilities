package com.aidanvii.databindingutils.recyclerview;

import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.aidanvii.databindingutils.utils.Preconditions.checkArgumentIsNotNull;

/**
 * Created by Aidan on 30/12/2016.
 */
public abstract class AdapterHelperPlugin {

    Hook emptyHook = new Hook();

    @NotNull
    abstract Hook getHook(ViewDataBinding viewDataBinding);

    abstract boolean hasMultiple();

    @SuppressWarnings("unchecked")
    void onViewHolderCreated(ViewDataBinding viewDataBinding) {
        getHook(viewDataBinding).onViewHolderCreated(viewDataBinding);
    }

    @SuppressWarnings("unchecked")
    void onViewHolderBound(ViewDataBinding viewDataBinding, AdapterItemViewModel adapterItemViewModel, int position) {
        getHook(viewDataBinding).onViewHolderBound(viewDataBinding, adapterItemViewModel, position);
    }

    @SuppressWarnings("unchecked")
    void onViewHolderPartialBound(ViewDataBinding viewDataBinding, AdapterItemViewModel adapterItemViewModel, int position, int[] payloads) {
        getHook(viewDataBinding).onViewHolderPartialBound(viewDataBinding, adapterItemViewModel, position, payloads);
    }

    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(ViewDataBinding viewDataBinding) {
        getHook(viewDataBinding).onViewAttachedToWindow(viewDataBinding);
    }

    @SuppressWarnings("unchecked")
    public void onViewDetachedFromWindow(ViewDataBinding viewDataBinding) {
        getHook(viewDataBinding).onViewDetachedFromWindow(viewDataBinding);
    }

    @SuppressWarnings("unchecked")
    public void onViewRecycled(ViewDataBinding viewDataBinding) {
        getHook(viewDataBinding).onViewRecycled(viewDataBinding);
    }

    public boolean onFailedToRecycleView(ViewDataBinding viewDataBinding) {
        return getHook(viewDataBinding).onFailedToRecycleView(viewDataBinding);
    }

    public static Builder builder() {
        return new Builder();
    }


    final static class EmptyItemAdapterHelperPlugin extends AdapterHelperPlugin {

        @NotNull
        @Override
        protected Hook getHook(ViewDataBinding viewDataBinding) {
            return this.emptyHook;
        }

        @Override
        boolean hasMultiple() {
            return false;
        }
    }

    final static class NonEmptyItemAdapterHelperPlugin extends AdapterHelperPlugin {
        private final Map<Class, Hook> map;

        NonEmptyItemAdapterHelperPlugin(HashMap<Class, Hook> map) {
            this.map = map;
        }

        @NotNull
        @Override
        protected Hook getHook(ViewDataBinding viewDataBinding) {
            final Hook hook = map.get(viewDataBinding.getClass());
            return hook != null ? hook : emptyHook;
        }

        @Override
        boolean hasMultiple() {
            return true;
        }
    }

    /**
     * Allows the implementor to hook into various {@link android.support.v7.widget.RecyclerView.Adapter} callbacks that are
     * specific to the {@link ViewDataBinding} subclass and {@link AdapterItemViewModel} subclass.
     * <p>A {@link Hook} can be attached to an {@link AdapterHelperPlugin}</p>
     * <p>See {@link Builder#addMapping(Class, Hook)} for details.</p>
     *
     * @param <VDB>
     * @param <VM>
     */
    public static class Hook<VDB extends ViewDataBinding, VM extends AdapterItemViewModel> {

        /**
         * Called when a {@link BindingViewHolder} is created
         *
         * @param viewDataBinding
         */
        @SuppressWarnings("unchecked")
        public void onViewHolderCreated(VDB viewDataBinding) {
        }

        /**
         * Called when a {@link AdapterItemViewModel} is bound to a {@link BindingViewHolder}
         *
         * @param viewDataBinding
         * @param adapterItemViewModel
         * @param position
         */
        public void onViewHolderBound(VDB viewDataBinding, VM adapterItemViewModel, int position) {
        }

        /**
         * Called when a {@link AdapterItemViewModel} is partially re-bound to a {@link BindingViewHolder}
         * <p>This happens when the {@link BindingViewHolder} is re-bound to the same item, with partial changes.</p>
         *
         * @param viewDataBinding
         * @param adapterItemViewModel
         * @param position
         * @param changedProperties
         */
        public void onViewHolderPartialBound(VDB viewDataBinding, VM adapterItemViewModel, int position, int[] changedProperties) {
        }

        /**
         * hook for {@link android.support.v7.widget.RecyclerView.Adapter#onViewAttachedToWindow(RecyclerView.ViewHolder)}
         * @param viewDataBinding
         */
        public void onViewAttachedToWindow(VDB viewDataBinding) {
        }

        /**
         * hook for {@link android.support.v7.widget.RecyclerView.Adapter#onViewDetachedFromWindow(RecyclerView.ViewHolder)}
         * @param viewDataBinding
         */
        public void onViewDetachedFromWindow(VDB viewDataBinding) {
        }

        /**
         * hook for {@link android.support.v7.widget.RecyclerView.Adapter#onViewRecycled(RecyclerView.ViewHolder)}
         * @param viewDataBinding
         */
        public void onViewRecycled(VDB viewDataBinding) {
        }

        /**
         * hook for {@link android.support.v7.widget.RecyclerView.Adapter#onFailedToRecycleView(RecyclerView.ViewHolder)}
         * @param viewDataBinding
         */
        public boolean onFailedToRecycleView(ViewDataBinding viewDataBinding) {
            return false;
        }
    }

    public static abstract class HookGroup<VDB extends ViewDataBinding, VM extends AdapterItemViewModel> extends Hook<VDB, VM> {

        private final Hook[] hooks;

        public HookGroup(Hook... hooks) {
            this.hooks = hooks != null ? hooks : new Hook[0];
        }

        @Nullable
        public abstract ViewDataBinding getBindingForHook(VDB viewDataBinding, Hook hook);

        /**
         * Called when a {@link BindingViewHolder} is created
         *
         * @param viewDataBinding
         */
        @CallSuper
        @SuppressWarnings("unchecked")
        public void onViewHolderCreated(VDB viewDataBinding) {
            for (Hook hook : hooks) {
                hook.onViewHolderCreated(getBindingForHook(viewDataBinding, hook));
            }
        }


        /**
         * Called when a {@link AdapterItemViewModel} is bound to a {@link BindingViewHolder}
         *
         * @param viewDataBinding
         * @param adapterItemViewModel
         * @param position
         */
        @CallSuper
        @SuppressWarnings("unchecked")
        public void onViewHolderBound(VDB viewDataBinding, VM adapterItemViewModel, int position) {
            for (Hook hook : hooks) {
                hook.onViewHolderBound(getBindingForHook(viewDataBinding, hook), adapterItemViewModel, position);
            }
        }


        /**
         * Called when a {@link AdapterItemViewModel} is partially re-bound to a {@link BindingViewHolder}
         * <p>This happens when the {@link BindingViewHolder} is re-bound to the same item, with partial changes.</p>
         *
         * @param viewDataBinding
         * @param adapterItemViewModel
         * @param position
         * @param changedProperties
         */
        @CallSuper
        @SuppressWarnings("unchecked")
        public void onViewHolderPartialBound(VDB viewDataBinding, VM adapterItemViewModel, int position, int[] changedProperties) {
            for (Hook hook : hooks) {
                hook.onViewHolderPartialBound(getBindingForHook(viewDataBinding, hook), adapterItemViewModel, position, changedProperties);
            }
        }
    }

    /**
     * Used to create a new instance of {@link AdapterHelperPlugin}.
     */
    public static class Builder {
        private final Map<Class, Hook> map;

        private Builder() {
            map = new HashMap<>();
        }

        /**
         * Creates a mapping between a class of type {@link ViewDataBinding} and a compatible {@link Hook} type.
         *
         * @param viewDataBindingClass
         * @param hook
         * @param <VDB>
         * @return
         */
        public <VDB extends ViewDataBinding> Builder addMapping(@NotNull Class<VDB> viewDataBindingClass, @NotNull Hook<VDB, ? extends AdapterItemViewModel> hook) {
            map.put(checkArgumentIsNotNull(viewDataBindingClass), checkArgumentIsNotNull(hook));
            return this;
        }

        public AdapterHelperPlugin build() {
            switch (map.size()) {
                case 0:
                    return createEmpty();
                default:
                    return createNonEmpty();
            }
        }

        private AdapterHelperPlugin createEmpty() {
            return new EmptyItemAdapterHelperPlugin();
        }

        private AdapterHelperPlugin createNonEmpty() {
            return new NonEmptyItemAdapterHelperPlugin(new HashMap<>(map));
        }
    }
}
