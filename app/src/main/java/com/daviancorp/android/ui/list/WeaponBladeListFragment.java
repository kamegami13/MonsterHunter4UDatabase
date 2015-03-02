package com.daviancorp.android.ui.list;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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

import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;
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

        ListView view = (ListView) v.findViewById(android.R.id.list);

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

        private static class ViewHolder extends ElementViewHolder {
            // Blade

            TextView specialtv;
            DrawSharpness sharpnessDrawable;
            ImageView note1v;
            ImageView note2v;
            ImageView note3v;
        }


		public WeaponBladeListCursorAdapter(Context context, WeaponCursor cursor) {
			super(context, cursor);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.fragment_weapon_tree_item_blademaster, parent,
					false);

            ViewHolder holder = new ViewHolder();

            //
            // GENERAL VIEWS
            //

            // Set the layout id
            holder.weaponLayout = (RelativeLayout) view.findViewById(R.id.main_layout);

            // Find all views
            holder.nametv = (TextView) view.findViewById(R.id.name_text);
            holder.attacktv = (TextView) view.findViewById(R.id.attack_text);
            holder.slottv = (TextView) view.findViewById(R.id.slots_text);
            holder.affinitytv = (TextView) view.findViewById(R.id.affinity_text);
            holder.defensetv = (TextView) view.findViewById(R.id.defense_text);
            holder.weaponIcon = (ImageView) view.findViewById(R.id.weapon_icon);
            holder.lineLayout = (View) view.findViewById(R.id.tree_lines);

            //
            // ELEMENT VIEWS
            //

            holder.elementtv = (TextView) view.findViewById(R.id.element_text);
            holder.elementtv2 = (TextView) view.findViewById(R.id.element_text2);
            holder.awakentv = (TextView) view.findViewById(R.id.awaken_text);
            holder.elementIcon = (ImageView) view.findViewById(R.id.element_image);
            holder.element2Icon = (ImageView) view.findViewById(R.id.element_image2);

            //
            // BLADE VIEWS

            holder.specialtv = (TextView) view.findViewById(R.id.special_text);
            holder.sharpnessDrawable = (DrawSharpness) view.findViewById(R.id.sharpness);


            holder.note1v = (ImageView) view.findViewById(R.id.note_image_1);
            holder.note2v = (ImageView) view.findViewById(R.id.note_image_2);
            holder.note3v = (ImageView) view.findViewById(R.id.note_image_3);

            view.setTag(holder);

            return view;
		}

		@Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);

            ViewHolder holder = (ViewHolder) view.getTag();

            // Get the monster for the current row
            Weapon weapon = mWeaponCursor.getWeapon();

            //
            // Set special text fields
            //
            holder.specialtv.setText("");
            String type = weapon.getWtype();
            if (type.equals("Hunting Horn")) {
                String special = weapon.getHornNotes();

                holder.note1v.setTag(weapon.getId());
                holder.note2v.setTag(weapon.getId());
                holder.note3v.setTag(weapon.getId());

                new LoadImage(holder.note1v, getNoteDrawable(special.charAt(0))).execute();
                new LoadImage(holder.note2v, getNoteDrawable(special.charAt(1))).execute();
                new LoadImage(holder.note3v, getNoteDrawable(special.charAt(2))).execute();

            }
            else if (type.equals("Gunlance")) {
                String special = weapon.getShellingType();
                holder.specialtv.setText(special);
                holder.specialtv.setGravity(Gravity.CENTER);
            }
            else if (type.equals("Switch Axe") || type.equals("Charge Blade")) {
                String special = weapon.getPhial();
                holder.specialtv.setText(special);
                holder.specialtv.setGravity(Gravity.CENTER);
            }

            // Set sharpness
            String sharpString = weapon.getSharpness();
            holder.sharpnessDrawable.init(sharpString);
            holder.sharpnessDrawable.invalidate();
        }


		private String getNoteDrawable(char note) {
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

			return file;
		}

	}

}
