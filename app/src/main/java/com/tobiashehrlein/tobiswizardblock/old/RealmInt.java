package com.tobiashehrlein.tobiswizardblock.old;

import io.realm.RealmObject;

/**
 * Created by Tobias on 15.06.2016.
 */
public class RealmInt extends RealmObject
{
    private int val;

    public RealmInt()
    {

    }

    public RealmInt(int val)
    {
        this.val = val;
    }

    public int getVal()
    {
        return val;
    }

    public void setVal(int val)
    {
        this.val = val;
    }
}
