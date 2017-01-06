package com.aidanvii.databindingutils.recyclerview;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;

import com.aidanvii.databindingutils.utils.suppliers.Lazy;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;

import static com.aidanvii.databindingutils.utils.Preconditions.checkArgumentIsNotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 24/10/16.
 */
@AutoValue
public abstract class ObservableListRecyclerViewModel<
        AdapterItemViewModelImpl extends AdapterItemViewModel,
        Helper extends BindingAdapterHelper<AdapterItemViewModelImpl>> {

    ObservableListRecyclerViewModel() {
    }

    private Lazy<ObservableList<AdapterItemViewModelImpl>> observableListSupplier = Lazy.create(ObservableArrayList::new);

    private Lazy<ObservableList.OnListChangedCallback<ObservableList<AdapterItemViewModelImpl>>> observableListCallbackSupplier
            = Lazy.create(this::createObservableListCallback);

    @NotNull
    public abstract ObservableListBindingAdapter<AdapterItemViewModelImpl> getAdapter();

    @NotNull
    public abstract Helper getAdapterHelper();

    @NotNull
    public abstract RecyclerView.LayoutManager getLayoutManager();

    @NotNull
    public ObservableList<AdapterItemViewModelImpl> getObservableList() {
        return observableListSupplier.get();
    }

    @NotNull
    public ObservableList.OnListChangedCallback<ObservableList<AdapterItemViewModelImpl>> getObservableListCallback() {
        return observableListCallbackSupplier.get();
    }

    private ObservableList.OnListChangedCallback<ObservableList<AdapterItemViewModelImpl>> createObservableListCallback() {
        return new ObservableList.OnListChangedCallback<ObservableList<AdapterItemViewModelImpl>>() {
            @Override
            public void onChanged(ObservableList<AdapterItemViewModelImpl> sender) {
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<AdapterItemViewModelImpl> sender, int positionStart, int itemCount) {
                getAdapter().notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<AdapterItemViewModelImpl> sender, int positionStart, int itemCount) {
                getAdapter().notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<AdapterItemViewModelImpl> sender, int fromPosition, int toPosition, int
                    itemCount) {
                // TODO addMapping support for when itemCount > 1
                if (itemCount > 1) {
                    throw new UnsupportedOperationException("cannot move batches of items at once.");
                }
                getAdapter().notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onItemRangeRemoved(ObservableList<AdapterItemViewModelImpl> sender, int positionStart, int itemCount) {
                getAdapter().notifyItemRangeRemoved(positionStart, itemCount);
            }
        };
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
        ObservableListRecyclerViewModel<AdapterItemViewModelImpl, Helper> build();
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
        public ObservableListRecyclerViewModel<AdapterItemViewModelImpl, Helper> build() {
            return new AutoValue_ObservableListRecyclerViewModel<>(new ObservableListBindingAdapter<>(adapterHelper), adapterHelper, layoutManager);
        }
    }
}
