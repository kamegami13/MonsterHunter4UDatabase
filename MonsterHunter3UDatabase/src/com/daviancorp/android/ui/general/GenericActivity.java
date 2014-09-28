package com.daviancorp.android.ui.general;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.dialog.AboutDialogFragment;

/*
 * Any subclass needs to:
 *  - override onCreate() to set title
 *  - override createFragmentOne() for detail fragments
 */

public abstract class GenericActivity extends SherlockFragmentActivity {

	protected static final String DIALOG_ABOUT = "about";

	protected Fragment detail;

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
		
		setTitle(R.string.app_name);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(GenericActivity.this, HomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			return true;
		case R.id.about:
			FragmentManager fm = getSupportFragmentManager();
			AboutDialogFragment dialog = new AboutDialogFragment();
			dialog.show(fm, DIALOG_ABOUT);

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		for (int i = 0; i < menu.size(); i++) {
			MenuItem mi = menu.getItem(i);
			String title = mi.getTitle().toString();
			Spannable newTitle = new SpannableString(title);
			newTitle.setSpan(new ForegroundColorSpan(Color.WHITE), 0,
					newTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			mi.setTitle(newTitle);
		}
		return true;
	}

	public Fragment getDetail() {
		return detail;
	}
}
