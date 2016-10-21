package com.tobiashehrlein.tobiswizardblock;

import io.realm.RealmObject;

/**
 * Created by Tobias on 15.06.2016.
 */
public class RealmString extends RealmObject
{
    private String val;

    public RealmString()
    {

    }

    public RealmString(String val)
    {
        this.val = val;
    }

    public String getVal()
    {
        return val;
    }

    public void setVal(String val)
    {
        this.val = val;
    }
}
