package com.daviancorp.android.ui.list;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.adapter.WeaponListElementAdapter;
import com.daviancorp.android.ui.general.FixedImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class WeaponBowListFragment extends WeaponListFragment implements
		LoaderCallbacks<ArrayList<Weapon>> {

	public static WeaponBowListFragment newInstance(String type) {
		Bundle args = new Bundle();
		args.putString(WeaponListFragment.ARG_TYPE, type);
		WeaponBowListFragment f = new WeaponBowListFragment();
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
        //		super.setContextMenu(v);
		return inflater.inflate(R.layout.fragment_generic_list, null);
	}
	
	/*@Override
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
	}*/
	
	@Override
	public void onLoadFinished(Loader<ArrayList<Weapon>> loader, ArrayList<Weapon> weapons) {
		// Create an adapter to point at this cursor
		WeaponBowListAdapter adapter = new WeaponBowListAdapter(
				getActivity(), weapons);
		setListAdapter(adapter);

	}

	private static class WeaponBowListAdapter extends WeaponListElementAdapter {

        private static class ViewHolder extends ElementViewHolder {
            // Bow

            FixedImageView powerv;
            FixedImageView crangev;
            FixedImageView poisonv;
            FixedImageView parav;
            FixedImageView sleepv;
            FixedImageView exhaustv;
            FixedImageView slimev;
            FixedImageView paintv;

            TextView arctv;
            TextView chargetv;

            int position;

            Context context;
        }

        public WeaponBowListAdapter(Context context, ArrayList<Weapon> weapons) {
            super(context, weapons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.fragment_weapon_tree_item_bow,
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
                // BOW VIEWS
                holder.arctv = (TextView) convertView.findViewById(R.id.arc_shot_text);
                holder.chargetv = (TextView) convertView.findViewById(R.id.charge_text);

                // Coatings
                holder.powerv = (FixedImageView) convertView.findViewById(R.id.power);
                holder.crangev = (FixedImageView) convertView.findViewById(R.id.crange);
                holder.poisonv = (FixedImageView) convertView.findViewById(R.id.poison);
                holder.parav = (FixedImageView) convertView.findViewById(R.id.para);
                holder.sleepv = (FixedImageView) convertView.findViewById(R.id.sleep);
                holder.exhaustv = (FixedImageView) convertView.findViewById(R.id.exhaust);
                holder.slimev = (FixedImageView) convertView.findViewById(R.id.blast);
                holder.paintv = (FixedImageView) convertView.findViewById(R.id.paint);


                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.position = position;
            holder.context = convertView.getContext();

            super.getView(position, convertView, parent);

            // Get the weapon for the current row
            Weapon weapon = getItem(position);

            String arc = weapon.getRecoil();

            holder.arctv.setText(arc);
            holder.chargetv.setText(weapon.getChargeString());

            // Clear images
            holder.powerv.setImageDrawable(null);
            holder.crangev.setImageDrawable(null);
            holder.poisonv.setImageDrawable(null);
            holder.parav.setImageDrawable(null);
            holder.sleepv.setImageDrawable(null);
            holder.exhaustv.setImageDrawable(null);
            holder.slimev.setImageDrawable(null);
            holder.paintv.setImageDrawable(null);

            holder.powerv.setVisibility(View.GONE);
            holder.crangev.setVisibility(View.GONE);
            holder.poisonv.setVisibility(View.GONE);
            holder.parav.setVisibility(View.GONE);
            holder.sleepv.setVisibility(View.GONE);
            holder.exhaustv.setVisibility(View.GONE);
            holder.slimev.setVisibility(View.GONE);
            holder.paintv.setVisibility(View.GONE);


            String[] coatings = weapon.getCoatingsArray();

            if (!coatings[0].equals("-")) {
                holder.powerv.setTag(weapon.getId());
                new LoadImage(holder.powerv, "icons_items/Bottle-Red.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.powerv.setVisibility(View.VISIBLE);
            }
            if (!coatings[1].equals("-")) {
                holder.poisonv.setTag(weapon.getId());
                new LoadImage(holder.poisonv, "icons_items/Bottle-Purple.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.poisonv.setVisibility(View.VISIBLE);
            }
            if (!coatings[2].equals("-")) {
                holder.parav.setTag(weapon.getId());
                new LoadImage(holder.parav, "icons_items/Bottle-Yellow.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.parav.setVisibility(View.VISIBLE);
            }
            if (!coatings[3].equals("-")) {
                holder.sleepv.setTag(weapon.getId());
                new LoadImage(holder.sleepv, "icons_items/Bottle-Cyan.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.sleepv.setVisibility(View.VISIBLE);
            }
            if (!coatings[4].equals("-")) {
                holder.crangev.setTag(weapon.getId());
                new LoadImage(holder.crangev, "icons_items/Bottle-White.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.crangev.setVisibility(View.VISIBLE);
            }
            if (!coatings[5].equals("-")) {
                holder.paintv.setTag(weapon.getId());
                new LoadImage(holder.paintv, "icons_items/Bottle-Pink.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.paintv.setVisibility(View.VISIBLE);
            }
            if (!coatings[6].equals("-")) {
                holder.exhaustv.setTag(weapon.getId());
                new LoadImage(holder.exhaustv, "icons_items/Bottle-Blue.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.exhaustv.setVisibility(View.VISIBLE);
            }
            if (!coatings[7].equals("-")) {
                holder.slimev.setTag(weapon.getId());
                new LoadImage(holder.slimev, "icons_items/Bottle-Orange.png").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                holder.slimev.setVisibility(View.VISIBLE);
            }


            return convertView;
        }
	}

    private static class LoadPhials extends AsyncTask<Void,Void,HashMap<ImageView,Drawable>> {
        private WeaponBowListAdapter.ViewHolder holder;
        private int position;
        private String[] coatings;

        public LoadPhials(WeaponBowListAdapter.ViewHolder imv, String[] coatings) {
            this.holder = imv;
            this.position = holder.position;
            this.coatings = coatings;
        }

        @Override
        protected HashMap<ImageView, Drawable> doInBackground(Void... arg0) {
            HashMap<ImageView, Drawable> map = new HashMap<>();

            if (!coatings[0].equals("-")) {
                map.put(holder.powerv, loadDrawable("icons_items/Bottle-Red.png"));
            } else {

            }
            if (!coatings[1].equals("-")) {
                map.put(holder.poisonv, loadDrawable("icons_items/Bottle-Purple.png"));
            }
            if (!coatings[2].equals("-")) {
                map.put(holder.parav, loadDrawable("icons_items/Bottle-Yellow.png"));
            }
            if (!coatings[3].equals("-")) {
                map.put(holder.sleepv, loadDrawable("icons_items/Bottle-Cyan.png"));
            }
            if (!coatings[4].equals("-")) {
                map.put(holder.crangev, loadDrawable("icons_items/Bottle-White.png"));
            }
            if (!coatings[6].equals("-")) {
                map.put(holder.exhaustv, loadDrawable("icons_items/Bottle-Blue.png"));
            }
            if (!coatings[7].equals("-")) {
                map.put(holder.slimev, loadDrawable("icons_items/Bottle-Orange.png"));
            }

            return map;
        }

        @Override
        protected void onPostExecute(HashMap<ImageView,Drawable> result) {
            if(position == holder.position) {
                Iterator it = result.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<ImageView,Drawable> pair = (Map.Entry<ImageView,Drawable>) it.next();

                    pair.getKey().setImageDrawable(pair.getValue());
                    pair.getKey().setVisibility(View.VISIBLE);
                }
            }
        }

        protected Drawable loadDrawable(String path) {
            Drawable d = null;

            try {
                d = Drawable.createFromStream(holder.context.getAssets().open(path),
                        null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return d;
        }
    }

}
