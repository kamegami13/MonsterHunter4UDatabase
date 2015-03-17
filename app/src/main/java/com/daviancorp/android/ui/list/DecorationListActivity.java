package com.daviancorp.android.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class DecorationListActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.decorations);

        // Enable drawer button instead of back button
        super.enableDrawerIndicator();
    }

    @Override
    protected MenuSection getSelectedSection() {
        return MenuSection.DECORATION;
    }

    @Override
    protected Fragment createFragment() {
        super.detail = new DecorationListFragment();
        return super.detail;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ArmorSetBuilderActivity.BUILDER_REQUEST_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
