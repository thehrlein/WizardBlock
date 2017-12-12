package com.tobiashehrlein.tobiswizardblock.model;

import java.io.Serializable;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 10.12.2017.
 */


public class Round extends RealmObject {

    private String roundCount;
    private RealmList<Integer> announcedTipps;
    private RealmList<Integer> madeTipps;
    private RealmList<Integer> pointsAdded;
    private RealmList<Integer> pointsTotal;

    public Round() {

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

    public RealmList<Integer> getMadeTipps() {
        return madeTipps;
    }

    public void setMadeTipps(RealmList<Integer> madeTipps) {
        this.madeTipps = madeTipps;
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
