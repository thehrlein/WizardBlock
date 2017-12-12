package com.tobiashehrlein.tobiswizardblock.model;

import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Tobias Hehrlein on 10.12.2017.
 */

@AutoValue
public abstract class Round implements Serializable {

    public abstract Map<Integer, Integer> getRound();

//   @AutoValue.Builder
//    abstract static class Builder {
//       abstract Builder setStitchAnnounced(int stitchAnnounced);
//       abstract Builder setStitchMade(int stitchMade);
//       abstract Builder setPointsToAdd(int pointsToAdd);
//       abstract Builder setPointsTotal(int pointsTotal);
//       abstract Round create();
//   }
}
