package com.tobiashehrlein.tobiswizardblock.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 10.12.2017.
 */


public class Round extends RealmObject {

    private String roundCount;
    private RealmList<Integer> announcedTipps;
    private RealmList<Integer> madeStitches;
    private RealmList<Integer> pointsAdded;
    private RealmList<Integer> pointsTotal;

    public Round() {
        announcedTipps = new RealmList<>();
        madeStitches = new RealmList<>();
        pointsAdded= new RealmList<>();
        pointsTotal = new RealmList<>();
    }

    public String getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(String roundCount) {
        this.roundCount = roundCount;
    }

    public RealmList<Integer> getAnnouncedTipps() {
        return announcedTipps;
    }

    public void setAnnouncedTipps(RealmList<Integer> announcedTipps) {
        this.announcedTipps = announcedTipps;
    }

    public RealmList<Integer> getMadeStitches() {
        return madeStitches;
    }

    public void setMadeStitches(RealmList<Integer> madeStitches) {
        this.madeStitches = madeStitches;
    }

    public RealmList<Integer> getPointsAdded() {
        return pointsAdded;
    }

    public void setPointsAdded(RealmList<Integer> pointsAdded) {
        this.pointsAdded = pointsAdded;
    }

    public RealmList<Integer> getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(RealmList<Integer> pointsTotal) {
        this.pointsTotal = pointsTotal;
    }
}
