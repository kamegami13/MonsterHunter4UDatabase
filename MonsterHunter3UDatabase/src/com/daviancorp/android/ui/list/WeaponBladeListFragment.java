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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.data.object.Weapon;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class WeaponBladeListFragment extends WeaponListFragment implements
		LoaderCallbacks<Cursor> {

	public static WeaponBladeListFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString(WeaponListFragment.ARG_TYPE, type);
		WeaponBladeListFragment f = new WeaponBladeListFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weapon_blade_list, null);
//		super.setContextMenu(v);
		return v;
	}
	
	@Override
	protected CursorAdapter getDetailAdapter() {
		return (CursorAdapter) getListAdapter();
	}
	
	@Override
	protected Weapon getDetailWeapon(int position) {
		WeaponBladeListCursorAdapter adapter = (WeaponBladeListCursorAdapter) getListAdapter();
		return((WeaponCursor) adapter.getItem(position)).getWeapon();
	}
	
	@Override
	protected Fragment getThisFragment() {
		return this;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// Create an adapter to point at this cursor
		WeaponBladeListCursorAdapter adapter = new WeaponBladeListCursorAdapter(
				getActivity(), (WeaponCursor) cursor);
		setListAdapter(adapter);

	}

	private static class WeaponBladeListCursorAdapter extends CursorAdapter {

		private WeaponCursor mWeaponCursor;

		public WeaponBladeListCursorAdapter(Context context, WeaponCursor cursor) {
			super(context, cursor, 0);
			mWeaponCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_weapon_blade_listitem, parent,
					false);
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
			TextView specialtv = (TextView) view.findViewById(R.id.special);
			
			// Need to reset drawables
			elementtv.setCompoundDrawables(null, null, null, null);
			specialtv.setCompoundDrawables(null, null, null, null);
			specialtv.setText(null);
			
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
			String dualElement = null;
			String elementText = "";
			String awakenText = "";
			Drawable dEle = null;
			Drawable dDualEle = null;
			
			if (awakenedElement != null) {
				element = awakenedElement;
				awakenText = "(";
			}
			
			if (element != null) {
				String[] elementData = getElementData(element);
				elementText = elementData[0];
				dEle = getDrawable(context, elementData[1]);
				
				if (element.contains(",")) {
					String[] twoElements = elementText.split(", ");
					elementText = twoElements[0];
					dualElement = twoElements[1];
					
					String[] dualElementData = getElementData(dualElement);
					
					specialtv.setText(dualElementData[0]);
					dDualEle = getDrawable(context, dualElementData[1]);

					dDualEle = scaleDrawable(dDualEle, 35, 35);
					specialtv.setCompoundDrawables(dDualEle, null, null, null);
					
					// reposition dual element
					android.view.Display display = ((android.view.WindowManager)context
							.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
					// x position: 185
					specialtv.setPadding((int) (display.getWidth()*0.16), 0, 0, 0);
				}
			
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

			String type = weapon.getWtype();
			if (type.equals("Hunting Horn")) {
				String special = weapon.getHornNotes();
				specialtv.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, 
						LayoutParams.WRAP_CONTENT, 0.3f));
				
				ImageView note1v = (ImageView) view.findViewById(R.id.note1);
				ImageView note2v = (ImageView) view.findViewById(R.id.note2);
				ImageView note3v = (ImageView) view.findViewById(R.id.note3);
				TextView fillerv = (TextView) view.findViewById(R.id.filler);
				
				note1v.setImageDrawable(getNoteDrawable(context, special.charAt(0)));
				note2v.setImageDrawable(getNoteDrawable(context, special.charAt(1)));
				note3v.setImageDrawable(getNoteDrawable(context, special.charAt(2)));
				
				fillerv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 
						LayoutParams.WRAP_CONTENT, 0.4f));
				note1v.setLayoutParams(new LinearLayout.LayoutParams(0, 50, 0.1f));
				note2v.setLayoutParams(new LinearLayout.LayoutParams(0, 50, 0.1f));
				note3v.setLayoutParams(new LinearLayout.LayoutParams(0, 50, 0.1f));
				
			}
			if (type.equals("Gunlance")) {
				String special = weapon.getShellingType();
				specialtv.setText(special);
				specialtv.setGravity(Gravity.CENTER);
			}
			if (type.equals("Switch Axe")) {
				String special = weapon.getPhial();
				specialtv.setText(special);
				specialtv.setGravity(Gravity.CENTER);
			}
			if (!type.equals("Light Bowgun") && !type.equals("Heavy Bowgun")
					&& !type.equals("Bow")) {
				ImageView sharpnesstv = (ImageView) view
						.findViewById(R.id.sharpness);
				String sharpFile = "icons_sharpness/"
						+ weapon.getSharpnessFile();
				sharpnesstv.setImageDrawable(getDrawable(context, sharpFile));
			}
		}
		
		private String[] getElementData(String element) {
			if (element.startsWith("FI")) {
				return new String[] {element.substring(2), "icons_monster_info/Fire.png"};
			} else if (element.startsWith("WA")) {
				return new String[] {element.substring(2), "icons_monster_info/Water.png"};
			} else if (element.startsWith("IC")) {
				return new String[] {element.substring(2), "icons_monster_info/Ice.png"};
			} else if (element.startsWith("TH")) {
				return new String[] {element.substring(2), "icons_monster_info/Thunder.png"};
			} else if (element.startsWith("DR")) {
				return new String[] {element.substring(2), "icons_monster_info/Dragon.png"};
			} else if (element.startsWith("PA")) {
				return new String[] {element.substring(2), "icons_monster_info/Paralysis.png"};
			} else if (element.startsWith("PO")) {
				return new String[] {element.substring(2), "icons_monster_info/Poison.png"};
			} else if (element.startsWith("SLP")) {
				return new String[] {element.substring(3), "icons_monster_info/Sleep.png"};
			} else if (element.startsWith("SLM")) {
				return new String[] {element.substring(3), "icons_monster_info/Slime.png"};
			} else {
				return null;
			}
		}
		
		private Drawable getNoteDrawable(Context c, char note) {
			String file = "icons_monster_info/";
			
			switch (note) {
				case 'B':
					file = file + "Note.blue.png";
					break;
				case 'C':
					file = file + "Note.aqua.png";
					break;
				case 'G':
					file = file + "Note.green.png";
					break;
				case 'O':
					file = file + "Note.orange.png";
					break;
				case 'P':
					file = file + "Note.purple.png";
					break;
				case 'R':
					file = file + "Note.red.png";
					break;
				case 'W':
					file = file + "Note.white.png";
					break;
				case 'Y':
					file = file + "Note.yellow.png";
					break;
			}
			
			return getDrawable(c, file);
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
