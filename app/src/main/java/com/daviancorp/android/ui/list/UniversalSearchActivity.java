package com.daviancorp.android.ui.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

/**
 * Created by Carlos on 8/3/2015.
 */
public class UniversalSearchActivity extends GenericActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null); // todo: replace
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new UniversalSearchFragment();
        return super.detail;
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.UNLISTED; // todo: something else?
    }
}
