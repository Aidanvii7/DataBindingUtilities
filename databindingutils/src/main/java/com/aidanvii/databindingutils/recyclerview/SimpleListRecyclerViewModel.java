package com.aidanvii.databindingutils.recyclerview;

import android.support.v7.widget.RecyclerView;

import com.aidanvii.databindingutils.utils.suppliers.Lazy;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.aidanvii.databindingutils.utils.Preconditions.checkArgumentIsNotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 24/10/16.
 */
@AutoValue
public abstract class SimpleListRecyclerViewModel<
        AdapterItemViewModelImpl extends AdapterItemViewModel,
        Helper extends BindingAdapterHelper<AdapterItemViewModelImpl>> {

    SimpleListRecyclerViewModel() {
    }

    private Lazy<List<AdapterItemViewModelImpl>> lazyList = Lazy.create(ArrayList::new);

    @NotNull
    public abstract SimpleListBindingAdapter<AdapterItemViewModelImpl> getAdapter();

    @NotNull
    public abstract Helper getAdapterHelper();

    @NotNull
    public abstract RecyclerView.LayoutManager getLayoutManager();

    @NotNull
    public List<AdapterItemViewModelImpl> getList() {
        return lazyList.get();
    }

    public static <AdapterItemViewModelImpl extends AdapterItemViewModel, Helper extends BindingAdapterHelper<AdapterItemViewModelImpl>>
    SetAdapterHelper<AdapterItemViewModelImpl, Helper> builder() {
        return new StagedBuilder<>();
    }

    public interface SetAdapterHelper<AdapterItemViewModelImpl extends AdapterItemViewModel, Helper extends BindingAdapterHelper<AdapterItemViewModelImpl>> {
        SetLayoutManager<AdapterItemViewModelImpl, Helper> setAdapterHelper(@NotNull Helper adapterHelper);
    }

    public interface SetLayoutManager<AdapterItemViewModelImpl extends AdapterItemViewModel, Helper extends BindingAdapterHelper<AdapterItemViewModelImpl>> {
        Build<AdapterItemViewModelImpl, Helper> setLayoutManager(@NotNull RecyclerView.LayoutManager layoutManager);
    }

    public interface Build<AdapterItemViewModelImpl extends AdapterItemViewModel, Helper extends BindingAdapterHelper<AdapterItemViewModelImpl>> {
        @NotNull
        SimpleListRecyclerViewModel<AdapterItemViewModelImpl, Helper> build();
    }

    static final class StagedBuilder<AdapterItemViewModelImpl extends AdapterItemViewModel, Helper extends BindingAdapterHelper<AdapterItemViewModelImpl>>
            implements
            SetAdapterHelper<AdapterItemViewModelImpl, Helper>,
            SetLayoutManager<AdapterItemViewModelImpl, Helper>,
            Build<AdapterItemViewModelImpl, Helper> {

        private Helper adapterHelper;
        private RecyclerView.LayoutManager layoutManager;

        @Override
        public SetLayoutManager<AdapterItemViewModelImpl, Helper> setAdapterHelper(@NotNull Helper adapterHelper) {
            this.adapterHelper = checkArgumentIsNotNull(adapterHelper);
            return this;
        }

        @Override
        public Build<AdapterItemViewModelImpl, Helper> setLayoutManager(@NotNull RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = checkArgumentIsNotNull(layoutManager);
            return this;
        }

        @NotNull
        @Override
        public SimpleListRecyclerViewModel<AdapterItemViewModelImpl, Helper> build() {
            return new AutoValue_SimpleListRecyclerViewModel<>(new SimpleListBindingAdapter<AdapterItemViewModelImpl>(adapterHelper),
                    adapterHelper, layoutManager);
        }
    }
}
