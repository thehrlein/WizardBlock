package com.tobiashehrlein.tobiswizardblock.old;

/**
 * Created by Tobias on 19.08.2016.
 */
public class ToSort implements Comparable<ToSort> {

    private Float val;
    private String id;

    public ToSort(Float val, String id){
        this.val = val;
        this.id = id;
    }

    @Override
    public int compareTo(ToSort f) {

        if (val.floatValue() > f.val.floatValue()) {
            return 1;
        }
        else if (val.floatValue() <  f.val.floatValue()) {
            return -1;
        }
        else {
            return 0;
        }

    }

    @Override
    public String toString(){
        return this.id;
    }

    public float getWinnerScore()
    {
        return this.val;
    }
}