package com.daviancorp.android.ui.general;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.dialog.AboutDialogFragment;

/*
 * Any subclass needs to:
 *  - override onCreate() to set title
 */

public abstract class GenericTabActivity extends GenericActionBarActivity{

	protected Fragment detail;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.app_name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		setContentView(R.layout.activity_tab);
        setupDrawer(); // Needs to be called after setContentView
	}
}
