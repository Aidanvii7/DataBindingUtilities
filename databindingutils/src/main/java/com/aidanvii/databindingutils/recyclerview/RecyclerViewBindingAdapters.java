package com.aidanvii.databindingutils.recyclerview;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.aidanvii.databindingutils.utils.Preconditions.cast;
import static com.aidanvii.databindingutils.utils.Preconditions.checkArgumentIsNotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 18/10/16.
 *
 */
public final class RecyclerViewBindingAdapters {

    private RecyclerViewBindingAdapters() {
    }

    @BindingAdapter("bind_viewModel")
    public static <
            AdapterItemViewModelImpl extends AdapterItemViewModel,
            Helper extends BindingAdapterHelper<AdapterItemViewModel>>
    void bindViewModel(RecyclerView recyclerView, @NotNull SimpleListRecyclerViewModel<AdapterItemViewModel, Helper> viewModel) {
        checkArgumentIsNotNull(viewModel);
        bindAdapter(recyclerView, viewModel.getAdapter());
        bindList(recyclerView, viewModel.getList());
        bindLayoutManager(recyclerView, viewModel.getLayoutManager());
    }

    @BindingAdapter({"bind_adapter"})
    public static <AdapterItemViewModelImpl extends AdapterItemViewModel>
    void bindAdapter(
            RecyclerView recyclerView,
            @NotNull SimpleListBindingAdapter<AdapterItemViewModel> bindingAdapter) {

        checkArgumentIsNotNull(bindingAdapter);
        recyclerView.setAdapter(bindingAdapter);
    }

    @BindingAdapter({"bind_list"})
    public static <AdapterItemViewModelImpl extends AdapterItemViewModel>
    void bindList(
            RecyclerView recyclerView,
            @NotNull List<AdapterItemViewModel> list) {
        checkArgumentIsNotNull(list);
        setList(recyclerView, list);
    }

    private static <AdapterItemViewModelImpl extends AdapterItemViewModel>
    void setList(
            RecyclerView recyclerView,
            @NotNull List<AdapterItemViewModel> list) {

        final RecyclerView.Adapter curAdapter = recyclerView.getAdapter();
        if (curAdapter != null) {
            final SimpleListBindingAdapter<AdapterItemViewModel> curBindingAdapterCast = cast(curAdapter);
            curBindingAdapterCast.setItems(list);
        }
    }

    @BindingAdapter("bind_viewModel")
    public static <
            AdapterItemViewModelImpl extends AdapterItemViewModel,
            Helper extends BindingAdapterHelper<AdapterItemViewModel>>
    void bindViewModel(RecyclerView recyclerView, @NotNull ObservableListRecyclerViewModel<AdapterItemViewModel, Helper> viewModel) {
        checkArgumentIsNotNull(viewModel);
        bindAdapter(recyclerView, viewModel.getAdapter());
        bindObservableList(recyclerView, viewModel.getObservableList(), viewModel.getObservableListCallback());
        bindLayoutManager(recyclerView, viewModel.getLayoutManager());
    }

    @BindingAdapter({"bind_adapter"})
    public static <AdapterItemViewModelImpl extends AdapterItemViewModel>
    void bindAdapter(
            RecyclerView recyclerView,
            @NotNull ObservableListBindingAdapter<AdapterItemViewModel> bindingAdapter) {

        checkArgumentIsNotNull(bindingAdapter);
        recyclerView.setAdapter(bindingAdapter);
    }

    @BindingAdapter({"bind_observableList", "bind_observableListCallback"})
    public static <AdapterItemViewModelImpl extends AdapterItemViewModel>
    void bindObservableList(
            RecyclerView recyclerView,
            @NotNull ObservableList<AdapterItemViewModel> observableList,
            @NotNull ObservableList.OnListChangedCallback<ObservableList<AdapterItemViewModel>> observableListCallback) {
        checkArgumentIsNotNull(observableList);
        checkArgumentIsNotNull(observableListCallback);
        observableList.addOnListChangedCallback(observableListCallback);
        setObservableList(recyclerView, observableList);
    }

    private static <AdapterItemViewModelImpl extends AdapterItemViewModel>
    void setObservableList(
            RecyclerView recyclerView,
            @NotNull ObservableList<AdapterItemViewModel> observableList) {

        final RecyclerView.Adapter curAdapter = recyclerView.getAdapter();
        if (curAdapter != null) {
            final ObservableListBindingAdapter<AdapterItemViewModel> curBindingAdapterCast = cast(curAdapter);
            curBindingAdapterCast.setItems(observableList);
        }
    }

    @BindingAdapter("bind_viewModel")
    public static <
            ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel,
            Helper extends BindingAdapterHelper<ComparableAdapterItemViewModel>>
    void bindViewModel(RecyclerView recyclerView, @NotNull SortedListRecyclerViewModel<ComparableAdapterItemViewModel, Helper> viewModel) {
        checkArgumentIsNotNull(viewModel);
        bindAdapter(recyclerView, viewModel.getAdapter());
        bindSortedList(recyclerView, viewModel.getSortedList());
        bindLayoutManager(recyclerView, viewModel.getLayoutManager());
    }

    @BindingAdapter({"bind_adapter"})
    public static <ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel> void bindAdapter(
            RecyclerView recyclerView,
            @NotNull SortedListBindingAdapter<ComparableAdapterItemViewModel> bindingAdapter) {

        checkArgumentIsNotNull(bindingAdapter);
        recyclerView.setAdapter(bindingAdapter);
    }

    @BindingAdapter({"bind_sortedList"})
    public static <ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel> void bindSortedList(
            RecyclerView recyclerView,
            @NotNull SortedList<ComparableAdapterItemViewModel> sortedList) {
        checkArgumentIsNotNull(sortedList);
        setSortedList(recyclerView, sortedList);
    }

    private static <ComparableAdapterItemViewModel extends Comparable<ComparableAdapterItemViewModel> & AdapterItemViewModel> void setSortedList(
            RecyclerView recyclerView,
            @NotNull SortedList<ComparableAdapterItemViewModel> sortedList) {

        final RecyclerView.Adapter curAdapter = recyclerView.getAdapter();
        if (curAdapter != null) {
            final SortedListBindingAdapter<ComparableAdapterItemViewModel> curBindingAdapterCast = cast(curAdapter);
            curBindingAdapterCast.setItems(sortedList);
        }
    }

    @BindingAdapter("bind_layoutManager")
    public static void bindLayoutManager(RecyclerView recyclerView, @NotNull RecyclerView.LayoutManager layoutManager) {
        checkArgumentIsNotNull(layoutManager);
        recyclerView.setLayoutManager(layoutManager);
    }
}