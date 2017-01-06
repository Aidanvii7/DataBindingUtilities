package com.aidanvii.databindingutils.recyclerview;

import android.support.v7.util.SortedList;

/**
 * Created by aidan.mcwilliams@vimn.com on 23/11/16.
 */
public final class SmartSortedList<T> extends SortedList<T> {

    public SmartSortedList(Class<T> klass, Callback<T> callback) {
        super(klass, callback);
    }

    public SmartSortedList(Class<T> klass, Callback<T> callback, int initialCapacity) {
        super(klass, callback, initialCapacity);
    }

    @Override
    public int add(T item) {
        int existingIndex = indexOf(item);
        if (existingIndex == INVALID_POSITION) {
            return super.add(item);
        } else {
            updateItemAt(existingIndex, item);
            return existingIndex;
        }
    }
}