package com.daviancorp.android.ui.general;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.daviancorp.android.mh4udatabase.R;

/*
 * Any subclass needs to:
 *  - override onCreate() to set title
 *  - override createFragment() for detail fragments
 */

public abstract class GenericActivity extends GenericActionBarActivity {

	protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment)
                    .commit();
        }

        // Integrate Toolbar so sliding drawer can go over toolbar
        androidx.appcompat.widget.Toolbar mtoolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mtoolbar);

        setTitle(R.string.app_name);
        super.setupDrawer(); // Needs to be called after setContentView
        // Disabled by request. Turns into BACK button
        //super.enableDrawerIndicator(); // Enable drawer toggle button
    }
}
