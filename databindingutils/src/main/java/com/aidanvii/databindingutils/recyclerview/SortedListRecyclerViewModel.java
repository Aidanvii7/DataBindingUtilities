package com.aidanvii.databindingutils.recyclerview;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import com.aidanvii.databindingutils.utils.suppliers.Lazy;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.NotNull;

import static com.aidanvii.databindingutils.utils.Preconditions.checkArgumentIsNotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 24/10/16.
 */
@AutoValue
public abstract class SortedListRecyclerViewModel<
        ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel,
        Helper extends BindingAdapterHelper<ComparableAdapterItemViewModel>> {

    SortedListRecyclerViewModel() {
    }

    private Lazy<SortedList<ComparableAdapterItemViewModel>>
            lazySortedList = Lazy.create(this::createSortedList);

    private Lazy<SortedList.Callback<ComparableAdapterItemViewModel>>
            lazySortedListCallback = Lazy.create(this::createSortedListCallback);

    @NotNull
    public abstract SortedListBindingAdapter<ComparableAdapterItemViewModel> getAdapter();

    @NotNull
    public abstract Helper getAdapterHelper();

    @NotNull
    public abstract RecyclerView.LayoutManager getLayoutManager();

    @NotNull
    public abstract Class<ComparableAdapterItemViewModel> getRootViewModelClass();

    @NotNull
    public SortedList<ComparableAdapterItemViewModel> getSortedList() {
        return lazySortedList.get();
    }

    @NotNull
    private SortedList.Callback<ComparableAdapterItemViewModel> getSortedListCallback() {
        return lazySortedListCallback.get();
    }

    @NotNull
    private SortedList<ComparableAdapterItemViewModel> createSortedList() {
        return new SortedList<>(getRootViewModelClass(),
                getSortedListCallback());
    }

    private SortedList.Callback<ComparableAdapterItemViewModel> createSortedListCallback() {
        return new SortedList.Callback<ComparableAdapterItemViewModel>() {
            @Override
            public void onInserted(int position, int count) {
                getAdapter().notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                getAdapter().notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                getAdapter().notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public int compare(ComparableAdapterItemViewModel o1, ComparableAdapterItemViewModel o2) {
                return o1.compareTo(o2);
            }

            @Override
            public void onChanged(int position, int count) {
                getAdapter().notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(ComparableAdapterItemViewModel oldItem, ComparableAdapterItemViewModel newItem) {
                return oldItem.areContentsTheSame(newItem);
            }

            @Override
            public boolean areItemsTheSame(ComparableAdapterItemViewModel item1, ComparableAdapterItemViewModel item2) {
                return item1.areItemsTheSame(item2);
            }
        };
    }

    public static <ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel, Helper extends
            BindingAdapterHelper<ComparableAdapterItemViewModel>>
    SetAdapterHelper<ComparableAdapterItemViewModel, Helper> builder() {
        return new StagedBuilder<>();
    }

    public interface SetAdapterHelper<ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel, Helper extends

            BindingAdapterHelper<ComparableAdapterItemViewModel>> {
        SetLayoutManager<ComparableAdapterItemViewModel, Helper> setAdapterHelper(@NotNull Helper adapterHelper);
    }

    public interface SetLayoutManager<ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel, Helper extends
            BindingAdapterHelper<ComparableAdapterItemViewModel>> {

        SetRootViewModelClass<ComparableAdapterItemViewModel, Helper> setLayoutManager(@NotNull RecyclerView.LayoutManager layoutManager);
    }

    public interface SetRootViewModelClass<ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel, Helper extends
            BindingAdapterHelper<ComparableAdapterItemViewModel>> {
        Build<ComparableAdapterItemViewModel, Helper> setRootViewModelClass(@NotNull Class<ComparableAdapterItemViewModel> rootViewModelClass);
    }

    public interface Build<ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel, Helper extends
            BindingAdapterHelper<ComparableAdapterItemViewModel>> {
        @NotNull
        SortedListRecyclerViewModel<ComparableAdapterItemViewModel, Helper> build();
    }

    static final class StagedBuilder<ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel, Helper extends
            BindingAdapterHelper<ComparableAdapterItemViewModel>>
            implements
            SetAdapterHelper<ComparableAdapterItemViewModel, Helper>,
            SetLayoutManager<ComparableAdapterItemViewModel, Helper>,
            SetRootViewModelClass<ComparableAdapterItemViewModel, Helper>,
            Build<ComparableAdapterItemViewModel, Helper> {

        private Helper adapterHelper;
        private RecyclerView.LayoutManager layoutManager;
        private Class<ComparableAdapterItemViewModel> rootViewModelClass;

        @Override
        public SetLayoutManager<ComparableAdapterItemViewModel, Helper> setAdapterHelper(@NotNull Helper adapterHelper) {
            this.adapterHelper = checkArgumentIsNotNull(adapterHelper);
            return this;
        }

        @Override
        public SetRootViewModelClass<ComparableAdapterItemViewModel, Helper> setLayoutManager(@NotNull RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = checkArgumentIsNotNull(layoutManager);
            return null;
        }

        @Override
        public Build<ComparableAdapterItemViewModel, Helper> setRootViewModelClass(@NotNull Class<ComparableAdapterItemViewModel> vmClass) {
            this.rootViewModelClass = checkArgumentIsNotNull(vmClass);
            return this;
        }

        @NotNull
        @Override
        public SortedListRecyclerViewModel<ComparableAdapterItemViewModel, Helper> build() {
            return new AutoValue_SortedListRecyclerViewModel<>
                    (new SortedListBindingAdapter<>(adapterHelper), adapterHelper, layoutManager, rootViewModelClass);
        }
    }
}
