package com.aidanvii.databindingutils.utils.suppliers;

/**
 * Created by aidan.mcwilliams@vimn.com on 22/12/16.
 */

public interface FunctionalSupplier<T> extends Supplier<T> {
    <R> R executeWithResult(Function<T, R> function);

    void execute(VoidFunction<T> function);
}
