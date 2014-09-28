package com.daviancorp.android.ui.list;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.data.object.Weapon;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class WeaponBowListFragment extends WeaponListFragment implements
		LoaderCallbacks<Cursor> {

	public static WeaponBowListFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString(WeaponListFragment.ARG_TYPE, type);
		WeaponBowListFragment f = new WeaponBowListFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weapon_bow_list, null);
//		super.setContextMenu(v);
		return v;
	}
	
	@Override
	protected CursorAdapter getDetailAdapter() {
		return (CursorAdapter) getListAdapter();
	}
	
	@Override
	protected Weapon getDetailWeapon(int position) {
		WeaponBowListCursorAdapter adapter = (WeaponBowListCursorAdapter) getListAdapter();
		return((WeaponCursor) adapter.getItem(position)).getWeapon();
	}	
	
	@Override
	protected Fragment getThisFragment() {
		return this;
	}
	
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		WeaponBowListCursorAdapter adapter = new WeaponBowListCursorAdapter(
				getActivity(), (WeaponCursor) cursor);
		setListAdapter(adapter);

	}

	private static class WeaponBowListCursorAdapter extends CursorAdapter {

		private WeaponCursor mWeaponCursor;

		public WeaponBowListCursorAdapter(Context context, WeaponCursor cursor) {
			super(context, cursor, 0);
			mWeaponCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_weapon_bow_listitem,
					parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the monster for the current row
			Weapon weapon = mWeaponCursor.getWeapon();

			// Set up the text view
			TextView nametv = (TextView) view.findViewById(R.id.name);
			TextView attacktv = (TextView) view.findViewById(R.id.attack);
			TextView elementtv = (TextView) view.findViewById(R.id.element);
			TextView awakentv = (TextView) view.findViewById(R.id.awaken);
			TextView slottv = (TextView) view.findViewById(R.id.slot);
			TextView affinitytv = (TextView) view.findViewById(R.id.affinity);
			TextView defensetv = (TextView) view.findViewById(R.id.defense);

			// Need to reset drawables
			elementtv.setCompoundDrawables(null, null, null, null);

			String name = "";
			int wFinal = weapon.getWFinal();
			
			if (wFinal != 0) {
				name = "*";
			}
			
			name = name + weapon.getName();
			String attack = "" + weapon.getAttack();

			// Set the element to view
			String element = weapon.getElementalAttack();
			String awakenedElement = weapon.getAwakenedElementalAttack();
			String elementText = "";
			String awakenText = "";
			Drawable dEle = null;

			if (awakenedElement != null) {
				element = awakenedElement;
				awakenText = "(";
			}

			if (element != null) {
				String[] elementData = getElementData(element);
				elementText = elementData[0];
				dEle = getDrawable(context, elementData[1]);
				dEle = scaleDrawable(dEle, 35, 35);
				elementtv.setCompoundDrawables(dEle, null, null, null);

				if (awakenedElement != null) {
					elementText = elementText + ")";
				}
			}

			// Set the slot to view
			String slot = "";
			switch (weapon.getNumSlots()) {
			case 0:
				slot = "---";
				break;
			case 1:
				slot = "O--";
				break;
			case 2:
				slot = "OO-";
				break;
			case 3:
				slot = "OOO";
				break;
			default:
				slot = "error!!";
				break;
			}

			String affinity = "";
			if (weapon.getAffinity() != 0) {
				affinity = "" + weapon.getAffinity() + "%";
			}

			String defense = "";
			if (weapon.getDefense() != 0) {
				defense = "" + weapon.getDefense();
			}

			nametv.setText(name);
			nametv.setTypeface(null, Typeface.BOLD);
			attacktv.setText(attack);
			elementtv.setText(elementText);
			awakentv.setText(awakenText);
			slottv.setText(slot);
			affinitytv.setText(affinity);
			defensetv.setText(defense);

			// Bow stuff
			TextView arctv = (TextView) view.findViewById(R.id.arc);
			TextView chargetv = (TextView) view.findViewById(R.id.charge);

			String arc = weapon.getRecoil();
			String charge = weapon.getCharges();
			String chargeText = "|";

			String[] charges = charge.split("\\|");
			for (String c : charges) {
				chargeText = chargeText + getChargeData(c);
			}

			arctv.setText(arc);
			chargetv.setText(chargeText);

			// Coatings
			ImageView powerv = (ImageView) view.findViewById(R.id.power);
			ImageView crangev = (ImageView) view.findViewById(R.id.crange);
			ImageView poisonv = (ImageView) view.findViewById(R.id.poison);
			ImageView parav = (ImageView) view.findViewById(R.id.para);
			ImageView sleepv = (ImageView) view.findViewById(R.id.sleep);
			ImageView exhaustv = (ImageView) view.findViewById(R.id.exhaust);
			ImageView slimev = (ImageView) view.findViewById(R.id.slime);
			ImageView paintv = (ImageView) view.findViewById(R.id.paint);
			
			// Clear images
			powerv.setImageDrawable(null);
			crangev.setImageDrawable(null);
			poisonv.setImageDrawable(null);
			parav.setImageDrawable(null);
			sleepv.setImageDrawable(null);
			exhaustv.setImageDrawable(null);
			slimev.setImageDrawable(null);
			paintv.setImageDrawable(null);

			String[] coatings = weapon.getCoatings().split(" ");

			if (!coatings[1].startsWith("0")) {
				powerv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Red.png"));
			}
			if (!coatings[2].startsWith("0")) {
				crangev.setImageDrawable(getDrawable(context, "icons_items/Bottle-White.png"));
			}
			if (!coatings[3].startsWith("0")) {
				poisonv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Purple.png"));
			}
			if (!coatings[4].startsWith("0")) {
				parav.setImageDrawable(getDrawable(context, "icons_items/Bottle-Yellow.png"));
			}
			if (!coatings[5].startsWith("0")) {
				sleepv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Cyan.png"));
			}
			if (!coatings[6].startsWith("0")) {
				exhaustv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Blue.png"));
			}
			if (!coatings[7].startsWith("0")) {
				slimev.setImageDrawable(getDrawable(context, "icons_items/Bottle-Slime.png"));
			}
			if (!coatings[8].startsWith("0")) {
				paintv.setImageDrawable(getDrawable(context, "icons_items/Bottle-Pink.png"));
			}

			// ImageView powerv = (ImageView) view.findViewById(R.id.power);
			// ImageView crangev = (ImageView) view.findViewById(R.id.crange);
			// ImageView poisonv = (ImageView) view.findViewById(R.id.poison);
			// ImageView parav = (ImageView) view.findViewById(R.id.para);
			// ImageView sleepv = (ImageView) view.findViewById(R.id.sleep);
			// ImageView exhaustv = (ImageView) view.findViewById(R.id.exhaust);
			// ImageView slimev = (ImageView) view.findViewById(R.id.slime);
			// ImageView paintv = (ImageView) view.findViewById(R.id.paint);
			//
			// powerv.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-Red.png"));
			// crangev.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-White.png"));
			// poisonv.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-Purple.png"));
			// parav.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-Yellow.png"));
			// sleepv.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-Cyan.png"));
			// exhaustv.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-Blue.png"));
			// slimev.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-Slime.png"));
			// paintv.setImageDrawable(getDrawable(context,
			// "icons_items/Bottle-Pink.png"));

		}

		private String[] getElementData(String element) {
			if (element.startsWith("FI")) {
				return new String[] { element.substring(2),
						"icons_monster_info/Fire.png" };
			} else if (element.startsWith("WA")) {
				return new String[] { element.substring(2),
						"icons_monster_info/Water.png" };
			} else if (element.startsWith("IC")) {
				return new String[] { element.substring(2),
						"icons_monster_info/Ice.png" };
			} else if (element.startsWith("TH")) {
				return new String[] { element.substring(2),
						"icons_monster_info/Thunder.png" };
			} else if (element.startsWith("DR")) {
				return new String[] { element.substring(2),
						"icons_monster_info/Dragon.png" };
			} else if (element.startsWith("PA")) {
				return new String[] { element.substring(2),
						"icons_monster_info/Paralysis.png" };
			} else if (element.startsWith("PO")) {
				return new String[] { element.substring(2),
						"icons_monster_info/Poison.png" };
			} else if (element.startsWith("SLP")) {
				return new String[] { element.substring(3),
						"icons_monster_info/Sleep.png" };
			} else if (element.startsWith("SLM")) {
				return new String[] { element.substring(3),
						"icons_monster_info/Slime.png" };
			} else {
				return null;
			}
		}

		private String getChargeData(String charge) {
			String s = "";

			if (charge.startsWith("Spread")) {
				s = "S";
			} else if (charge.startsWith("Rapid")) {
				s = "R";
			} else if (charge.startsWith("Pierce")) {
				s = "P";
			}

			s = s + charge.charAt(charge.length() - 1) + "|";
			return s;
		}

		private Drawable getDrawable(Context c, String location) {
			Drawable d = null;

			try {
				d = Drawable.createFromStream(c.getAssets().open(location),
						null);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return d;
		}

		// found on Google
		private Drawable scaleDrawable(Drawable drawable, int width, int height) {
			int wi = drawable.getIntrinsicWidth();
			int hi = drawable.getIntrinsicHeight();
			int dimDiff = Math.abs(wi - width) - Math.abs(hi - height);
			float scale = (dimDiff > 0) ? width / (float) wi : height
					/ (float) hi;
			Rect bounds = new Rect(0, 0, (int) (scale * wi), (int) (scale * hi));
			drawable.setBounds(bounds);
			return drawable;
		}
	}

}
