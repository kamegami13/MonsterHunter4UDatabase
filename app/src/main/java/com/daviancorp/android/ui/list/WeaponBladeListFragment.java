package com.daviancorp.android.ui.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

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
		LoaderCallbacks<ArrayList<Weapon>> {

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
        getLoaderManager().initLoader(R.id.weapon_list_fragment, getArguments(), this).forceLoad();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_generic_list, null);
		return v;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<Weapon>> loader, ArrayList<Weapon> weapons) {
		// Create an adapter to point at this cursor
		WeaponListBladeAdapter adapter = new WeaponListBladeAdapter(
				getActivity(), weapons);
		setListAdapter(adapter);

	}

	private static class WeaponListBladeAdapter extends WeaponListElementAdapter {

        private static class ViewHolder extends ElementViewHolder {
            // Blade

            TextView specialtv;
            DrawSharpness sharpnessDrawable;
            ImageView note1v;
            ImageView note2v;
            ImageView note3v;
        }


        public WeaponListBladeAdapter(Context context, ArrayList<Weapon> weapons) {
            super(context, weapons);
        }

		@Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.fragment_weapon_tree_item_blademaster,
                        parent, false);

                //
                // GENERAL VIEWS
                //

                // Set the layout id
                holder.weaponLayout = (RelativeLayout) convertView.findViewById(R.id.main_layout);

                // Find all views
                holder.nametv = (TextView) convertView.findViewById(R.id.name_text);
                holder.attacktv = (TextView) convertView.findViewById(R.id.attack_text);
                holder.slottv = (TextView) convertView.findViewById(R.id.slots_text);
                holder.affinitytv = (TextView) convertView.findViewById(R.id.affinity_text);
                holder.defensetv = (TextView) convertView.findViewById(R.id.defense_text);
                holder.weaponIcon = (ImageView) convertView.findViewById(R.id.weapon_icon);
                holder.lineLayout = (View) convertView.findViewById(R.id.tree_lines);

                //
                // ELEMENT VIEWS
                //

                holder.elementtv = (TextView) convertView.findViewById(R.id.element_text);
                holder.elementtv2 = (TextView) convertView.findViewById(R.id.element_text2);
                holder.awakentv = (TextView) convertView.findViewById(R.id.awaken_text);
                holder.elementIcon = (ImageView) convertView.findViewById(R.id.element_image);
                holder.element2Icon = (ImageView) convertView.findViewById(R.id.element_image2);

                //
                // BLADE VIEWS

                holder.specialtv = (TextView) convertView.findViewById(R.id.special_text);
                holder.sharpnessDrawable = (DrawSharpness) convertView.findViewById(R.id.sharpness);


                holder.note1v = (ImageView) convertView.findViewById(R.id.note_image_1);
                holder.note2v = (ImageView) convertView.findViewById(R.id.note_image_2);
                holder.note3v = (ImageView) convertView.findViewById(R.id.note_image_3);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }



            super.getView(position, convertView, parent);

            // Get the monster for the current row
            Weapon weapon = getItem(position);

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
            holder.sharpnessDrawable.init(weapon.getSharpness1(), weapon.getSharpness2());
            holder.sharpnessDrawable.invalidate();

            return convertView;
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
