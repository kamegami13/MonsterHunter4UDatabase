package com.daviancorp.android.ui.general;

import java.util.ArrayList;
import java.util.List;

import com.daviancorp.android.data.classes.Weapon;
import com.oissela.software.multilevelexpindlistview.MultiLevelExpIndListAdapter;

/**
 * Created by Mark on 3/3/2015.
 * Expandable data entry wrapper for a weapon object
 */
public class WeaponListEntry implements MultiLevelExpIndListAdapter.ExpIndData {
    public Weapon weapon;
    private int mIndentation;
    private List<WeaponListEntry> mChildren;
    private boolean mIsGroup;
    private int mGroupSize;

    public WeaponListEntry(Weapon weapon) {
        this.weapon = weapon;
        mChildren = new ArrayList<>();

        setIndentation(0);
    }

    @Override
    public List<? extends MultiLevelExpIndListAdapter.ExpIndData> getChildren() {
        return mChildren;
    }

    @Override
    public boolean isGroup() {
        return mIsGroup;
    }

    @Override
    public void setIsGroup(boolean value) {
        mIsGroup = value;
    }

    public int getGroupSize() {
        return mGroupSize;
    }

    @Override
    public void setGroupSize(int groupSize) {
        mGroupSize = groupSize;
    }

    public void addChild(WeaponListEntry child) {
        mChildren.add(child);
        child.setIndentation(getIndentation() + 1);
    }

    public int getIndentation() {
        return mIndentation;
    }

    private void setIndentation(int indentation) {
        mIndentation = indentation;
    }
}
