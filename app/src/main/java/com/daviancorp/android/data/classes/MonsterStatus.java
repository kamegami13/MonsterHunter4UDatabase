package com.daviancorp.android.data.classes;

/**
 * Created by Mark on 2/22/2015.
 * A container for monster status info
 */
public class MonsterStatus {
    private Monster mMonster;
    private String mStatus;
    private long mInitial;
    private long mIncrease;
    private long mMax;
    private long mDuration;
    private long mDamage;

    /**
     * Default constructor for status object
     */
    public MonsterStatus()
    {
        mMonster = null;
        mStatus = "";
        mInitial = -1;
        mIncrease = -1;
        mMax = -1;
        mDamage = -1;
        mDuration = -1;
    }

    public Monster getMonster() {
        return mMonster;
    }

    public void setMonster(Monster mMonster) {
        this.mMonster = mMonster;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public long getInitial() {
        return mInitial;
    }

    public void setInitial(long mInitial) {
        this.mInitial = mInitial;
    }

    public long getIncrease() {
        return mIncrease;
    }

    public void setIncrease(long mIncrease) {
        this.mIncrease = mIncrease;
    }

    public long getMax() {
        return mMax;
    }

    public void setMax(long mMax) {
        this.mMax = mMax;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public long getDamage() {
        return mDamage;
    }

    public void setDamage(long mDamage) {
        this.mDamage = mDamage;
    }
}
