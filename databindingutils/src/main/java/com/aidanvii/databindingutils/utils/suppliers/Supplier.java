package com.aidanvii.databindingutils.utils.suppliers;

/**
 * Created by aidan.mcwilliams@vimn.com on 06/12/16.
 */

public interface Supplier<T> {
    /**
     * Gets a result.
     *
     * @return a result
     */
    T get();
}
