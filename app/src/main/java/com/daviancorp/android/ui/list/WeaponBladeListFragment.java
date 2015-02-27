package com.daviancorp.android.ui.list;

import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;
import com.daviancorp.android.ui.adapter.WeaponListElementAdapter;
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

	private static class WeaponBladeListCursorAdapter extends WeaponListElementAdapter {


		public WeaponBladeListCursorAdapter(Context context, WeaponCursor cursor) {
			super(context, cursor);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_weapon_tree_item_blademaster, parent,
					false);
		}

		@Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            // Get the monster for the current row
            Weapon weapon = mWeaponCursor.getWeapon();

            TextView specialtv = (TextView) view.findViewById(R.id.special_text);


            DrawSharpness sharpnessDrawable = (DrawSharpness) view.findViewById(R.id.sharpness);
            // Need to reset sharpness so it gets redrawn, not recycled
            sharpnessDrawable.invalidate();
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

            // Set sharpness
            String sharpString = weapon.getSharpness();
            sharpnessDrawable.init(sharpString);
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
	}

}
