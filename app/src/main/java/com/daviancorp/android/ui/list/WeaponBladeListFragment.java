package com.daviancorp.android.ui.list;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;
import com.daviancorp.android.ui.general.DrawSharpness;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.weapon_list_fragment, null, this);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_generic_list, null);
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
			return inflater.inflate(R.layout.fragment_blademaster_list_item, parent,
					false);
		}

		@Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the monster for the current row
            Weapon weapon = mWeaponCursor.getWeapon();

            // Set the layout id
            RelativeLayout weaponLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
            weaponLayout.setTag(weapon.getId());
            weaponLayout.setOnClickListener(new WeaponClickListener(context, weapon.getId()));

            // Find all views
            TextView nametv = (TextView) view.findViewById(R.id.name_text);
            TextView attacktv = (TextView) view.findViewById(R.id.attack_text);
            TextView elementtv = (TextView) view.findViewById(R.id.element_text);
            TextView elementtv2 = (TextView) view.findViewById(R.id.element_text2);
            TextView awakentv = (TextView) view.findViewById(R.id.awaken_text);
            TextView slottv = (TextView) view.findViewById(R.id.slots_text);
            TextView affinitytv = (TextView) view.findViewById(R.id.affinity_text);
            TextView defensetv = (TextView) view.findViewById(R.id.defense_text);
            TextView specialtv = (TextView) view.findViewById(R.id.special_text);

            ImageView weaponIcon = (ImageView) view.findViewById(R.id.weapon_icon);
            ImageView elementIcon = (ImageView) view.findViewById(R.id.element_image);
            ImageView element2Icon = (ImageView) view.findViewById(R.id.element_image2);

            DrawSharpness sharpnessDrawable = (DrawSharpness) view.findViewById(R.id.sharpness);

            //
            // Set the image for the weapon
            //
            String cellImage = "";
            switch(weapon.getWtype()) {
                case "Great Sword":
                    cellImage = "icons_weapons/icons_great_sword/great_sword" + weapon.getRarity() + ".png";
                    break;
                case "Long Sword":
                    cellImage = "icons_weapons/icons_long_sword/long_sword" + weapon.getRarity() + ".png";
                    break;
                case "Sword and Shield":
                    cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + weapon.getRarity() + ".png";
                    break;
                case "Dual Blades":
                    cellImage = "icons_weapons/icons_dual_blades/dual_blades" + weapon.getRarity() + ".png";
                    break;
                case "Hammer":
                    cellImage = "icons_weapons/icons_hammer/hammer" + weapon.getRarity() + ".png";
                    break;
                case "Hunting Horn":
                    cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + weapon.getRarity() + ".png";
                    break;
                case "Lance":
                    cellImage = "icons_weapons/icons_hammer/hammer" + weapon.getRarity() + ".png";
                    break;
                case "Gunlance":
                    cellImage = "icons_weapons/icons_gunlance/gunlance" + weapon.getRarity() + ".png";
                    break;
                case "Switch Axe":
                    cellImage = "icons_weapons/icons_switch_axe/switch_axe" + weapon.getRarity() + ".png";
                    break;
                case "Charge Blade":
                    cellImage = "icons_weapons/icons_charge_blade/charge_blade" + weapon.getRarity() + ".png";
                    break;
                case "Insect Glaive":
                    cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + weapon.getRarity() + ".png";
                    break;
                case "Light Bowgun":
                    cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + weapon.getRarity() + ".png";
                    break;
                case "Heavy Bowgun":
                    cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + weapon.getRarity() + ".png";
                    break;
                case "Bow":
                    cellImage = "icons_weapons/icons_bow/bow" + weapon.getRarity() + ".png";
                    break;
            }
            weaponIcon.setImageDrawable(getDrawable(context, cellImage));


            //
            // Get the weapons name
            //
            String name = "";
            int wFinal = weapon.getWFinal();
            for(int i = 0; i < weapon.getTree_Depth(); i++)
            {
                name = name + "-";
            }
            name = name + weapon.getName();

            // Get the weapons attack
            String attack = Long.toString(weapon.getAttack());

            // Set the element to view
            String element = weapon.getElementalAttack();
            String awakenedElement = weapon.getAwakenedElementalAttack();
            String dualElement = null;
            String elementText = "";
            String awakenText = "";
            Drawable dEle = null;
            Drawable dDualEle = null;

            // Check to see if we should grab awaken
            if (!"".equals(awakenedElement)) {
                element = awakenedElement;
                awakenText = "(";
            }
            else
            {
                awakenText = "";
            }


            //
            // Set element text and images
            //
            if (!"".equals(element)) {
                String[] elementData = getElementData(element);
                elementText = elementData[0];
                dEle = getDrawable(context, elementData[1]);
                elementIcon.setImageDrawable(dEle);
                elementIcon.setVisibility(view.VISIBLE);

                if (element.contains(",")) {
                    String[] twoElements = elementText.split(",");
                    elementText = twoElements[0];
                    dualElement = twoElements[1];

                    String[] dualElementData = getElementData(dualElement);
                    dDualEle = getDrawable(context, dualElementData[1]);

                    elementtv2.setText(dualElementData[0]);
                    element2Icon.setImageDrawable(dDualEle);
                    element2Icon.setVisibility(view.VISIBLE);
                }
                else
                {
                    elementtv2.setText("");
                    element2Icon.setImageDrawable(null);
                    element2Icon.setVisibility(view.GONE);
                }

                if (!"".equals(awakenedElement)) {
                    elementText = elementText + ")";
                }

                elementtv.setText(elementText);
            }
            else {
                elementtv.setText("");
                elementIcon.setImageDrawable(null);
                elementtv2.setText("");
                element2Icon.setImageDrawable(null);
                elementIcon.setVisibility(view.GONE);
                element2Icon.setVisibility(view.GONE);
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

            //
            // Get affinity and defense
            //
            String affinity = "";
            if (weapon.getAffinity().length() > 0) {
                affinity = weapon.getAffinity() + "%";
            }

            String defense = "";
            if (weapon.getDefense() != 0) {
                defense = "" + weapon.getDefense();
            }

            //
            // Set remaining items
            //
            nametv.setText(name);
            attacktv.setText(attack);
            awakentv.setText(awakenText);
            slottv.setText(slot);
            affinitytv.setText(affinity);
            defensetv.setText(defense);

            //
            // Set special text fields
            //
            specialtv.setText("");
            String type = weapon.getWtype();
            if (type.equals("Hunting Horn")) {
                String special = weapon.getHornNotes();

                ImageView note1v = (ImageView) view.findViewById(R.id.note_image_1);
                ImageView note2v = (ImageView) view.findViewById(R.id.note_image_2);
                ImageView note3v = (ImageView) view.findViewById(R.id.note_image_3);

                note1v.setImageDrawable(getNoteDrawable(context, special.charAt(0)));
                note2v.setImageDrawable(getNoteDrawable(context, special.charAt(1)));
                note3v.setImageDrawable(getNoteDrawable(context, special.charAt(2)));

            }
            else if (type.equals("Gunlance")) {
                String special = weapon.getShellingType();
                specialtv.setText(special);
                specialtv.setGravity(Gravity.CENTER);
            }
            else if (type.equals("Switch Axe") || type.equals("Charge Blade")) {
                String special = weapon.getPhial();
                specialtv.setText(special);
                specialtv.setGravity(Gravity.CENTER);
            }

            String sharpString = weapon.getSharpness();
            sharpnessDrawable.init(sharpString);
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
			} else if (element.startsWith("SL")) {
				return new String[] {element.substring(2), "icons_monster_info/Sleep.png"};
			} else if (element.startsWith("BL")) {
				return new String[] {element.substring(2), "icons_monster_info/Blastblight.png"};
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
	}

}
