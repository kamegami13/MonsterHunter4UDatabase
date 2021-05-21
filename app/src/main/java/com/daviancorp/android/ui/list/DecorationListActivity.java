package com.daviancorp.android.ui.list;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.general.GenericActivity;
import com.daviancorp.android.ui.list.adapter.MenuSection;

public class DecorationListActivity extends GenericActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.decorations);

        // Enable back button if we're coming from the set builder
        if (getIntent().getBooleanExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, false)) {
            super.disableDrawerIndicator();
        } else {
            // Enable drawer button instead of back button
            super.enableDrawerIndicator();

            // Tag as top level activity
            super.setAsTopLevel();
        }
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

        if (requestCode == ASBActivity.REQUEST_CODE_ADD_DECORATION && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
