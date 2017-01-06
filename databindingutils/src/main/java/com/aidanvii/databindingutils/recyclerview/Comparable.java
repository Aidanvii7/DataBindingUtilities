package com.aidanvii.databindingutils.recyclerview;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aidan.mcwilliams@vimn.com on 02/12/16.
 */
public interface Comparable<VM> extends java.lang.Comparable<Comparable> {

    /**
     * Should be called when deciding whether the {@link Comparable} and {@code other} represent the same item or not.
     * <p>
     * For example, if your items have unique ids, this method should check their equality.
     *
     * @param other The item to check.
     * @return True if the two items represent the same object or false if they are different.
     */
    boolean areItemsTheSame(@NotNull AdapterItemViewModel other);


    /**
     * Should be called when deciding whether two items have the same data or not.
     * <p>
     * Should be used to check equality instead of {@link Object#equals(Object)}
     * so that you can change its behavior depending on your UI.
     * <p>
     * Should determine whether the item's visual representations are the same or not.
     *
     * @param other The item to check.
     * @return True if the contents of the items are the same or false if they are different.
     */
    boolean areContentsTheSame(@NotNull VM other);

}
