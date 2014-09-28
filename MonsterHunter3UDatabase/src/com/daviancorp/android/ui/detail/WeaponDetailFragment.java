package com.daviancorp.android.ui.detail;

import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.daviancorp.android.data.object.Weapon;
import com.daviancorp.android.loader.WeaponLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.dialog.WishlistDataAddDialogFragment;

public class WeaponDetailFragment extends Fragment {
	protected static final String ARG_WEAPON_ID = "WEAPON_ID";
	
	protected Weapon mWeapon;

	protected TextView mWeaponLabelTextView, mWeaponTypeTextView,
			mWeaponAttackTextView, mWeaponElementTextView,
			mWeaponRarityTextView, mWeaponSlotTextView,
			mWeaponAffinityTextView, mWeaponDefenseTextView,
			mWeaponCreationTextView, mWeaponUpgradeTextView;

	public static WeaponDetailFragment newInstance(long weaponId) {
		Bundle args = new Bundle();
		args.putLong(ARG_WEAPON_ID, weaponId);
		WeaponDetailFragment f = new WeaponDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);

		// Check for a Weapon ID as an argument, and find the weapon
		Bundle args = getArguments();
		if (args != null) {
			long weaponId = args.getLong(ARG_WEAPON_ID, -1);
			if (weaponId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.weapon_detail_fragment, args, new WeaponLoaderCallbacks());
			}
		}
	}
	
	protected void updateUI() throws IOException {
		mWeaponLabelTextView.setText(mWeapon.getName());
		mWeaponTypeTextView.setText(mWeapon.getWtype());
		mWeaponAttackTextView.setText("" + mWeapon.getAttack());
		mWeaponRarityTextView.setText("" + mWeapon.getRarity());
		mWeaponSlotTextView.setText("" + mWeapon.getNumSlots());
		mWeaponAffinityTextView.setText("" + mWeapon.getAffinity() + "%");
		mWeaponDefenseTextView.setText("" + mWeapon.getDefense());
		
		String createCost = "" + mWeapon.getCreationCost() + "z";
		String upgradeCost = "" + mWeapon.getUpgradeCost() + "z";
		
		if (createCost.equals("0z")) {
			createCost = "-";
		}
		if (upgradeCost.equals("0z")) {
			upgradeCost = "-";
		}
		
		mWeaponCreationTextView.setText(createCost);
		mWeaponUpgradeTextView.setText(upgradeCost);
		
		/* Element */
		String element = "";
		if (mWeapon.getElementalAttack() != null) {
			element = mWeapon.getElementalAttack();
		} else if (mWeapon.getAwakenedElementalAttack() != null) {
			element = mWeapon.getAwakenedElementalAttack();
		}

		if (element.contains(",")) {
			String[] twoElements = element.split(", ");
			String elementOne = twoElements[0];
			String elementTwo = twoElements[1];
			element = getElementData(elementOne) + ", " + getElementData(elementTwo);
		}
		else {
			element = getElementData(element);
		}

		if (mWeapon.getAwakenedElementalAttack() != null) {
			element = "(" + element + ")";
		}
		
		mWeaponElementTextView.setText(element);

		}

	private String getElementData(String element) {
		if (element.startsWith("FI")) {
			return "Fire " + element.substring(2);
		} else if (element.startsWith("WA")) {
			return "Water " + element.substring(2);
		} else if (element.startsWith("IC")) {
			return "Ice " + element.substring(2);
		} else if (element.startsWith("TH")) {
			return "Thunder " + element.substring(2);
		} else if (element.startsWith("DR")) {
			return "Dragon " + element.substring(2);
		} else if (element.startsWith("PA")) {
			return "Paralysis " + element.substring(2);
		} else if (element.startsWith("PO")) {
			return "Poison " + element.substring(2);
		} else if (element.startsWith("SLP")) {
			return "Sleep " + element.substring(3);
		} else if (element.startsWith("SLM")) {
			return "Slime " + element.substring(3);
		} else {
			return "";
		}
	}

	private class WeaponLoaderCallbacks implements LoaderCallbacks<Weapon> {

		@Override
		public Loader<Weapon> onCreateLoader(int id, Bundle args) {
			return new WeaponLoader(getActivity(), args.getLong(ARG_WEAPON_ID));
		}

		@Override
		public void onLoadFinished(Loader<Weapon> loader, Weapon run) {
			mWeapon = run;

			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onLoaderReset(Loader<Weapon> loader) {
			// Do nothing
		}
	}
}
