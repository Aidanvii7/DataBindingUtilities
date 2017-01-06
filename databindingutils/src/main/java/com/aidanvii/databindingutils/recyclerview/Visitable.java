package com.aidanvii.databindingutils.recyclerview;

/**
 * Created by aidan.mcwilliams@vimn.com on 08/12/16.
 */
public interface Visitable {

    int type(TypeFactory typeFactory);

    interface TypeFactory {
        int type(Visitable type);
    }
}
