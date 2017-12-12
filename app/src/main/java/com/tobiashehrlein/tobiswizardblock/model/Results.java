package com.tobiashehrlein.tobiswizardblock.model;

import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Tobias Hehrlein on 10.12.2017.
 */

@AutoValue
public abstract class Results implements Serializable {

    public abstract Map<String, Round> getResults();

    public static Results create(Map<String, Round> results) {
        return new AutoValue_Results(results);
    }
}