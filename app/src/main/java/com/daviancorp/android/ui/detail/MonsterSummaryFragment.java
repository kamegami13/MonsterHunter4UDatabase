package com.daviancorp.android.ui.detail;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Monster;
import com.daviancorp.android.data.classes.MonsterAilment;
import com.daviancorp.android.data.classes.MonsterWeakness;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.MonsterAilmentCursor;
import com.daviancorp.android.loader.MonsterAilmentCursorLoader;
import com.daviancorp.android.loader.MonsterLoader;
import com.daviancorp.android.mh4udatabase.R;

import org.apmem.tools.layouts.FlowLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MonsterSummaryFragment extends Fragment {
	private static final String ARG_MONSTER_ID = "MONSTER_ID";

	private Bundle mBundle;

	private Monster mMonster;
	private MonsterWeakness mWeakness;

	private TextView mMonsterLabelTextView;
	private ImageView mMonsterIconImageView;

	// Sections to hold icons and text
	private FlowLayout mWeaknessData, mTrapData, mBombData;
	private FlowLayout mWeaknessModData, mTrapModData, mBombModData;
	private LinearLayout mWeaknessMod, mTrapMod, mBombMod;
	private View mWeaknessModDiv, mTrapModDiv, mBombModDiv;
	private TextView mWeaknessModText, mTrapModText, mBombModText, mMovesData;
	private LinearLayout mAilments;

	// Need to add dividers
    //private View mDividerView;

	public static MonsterSummaryFragment newInstance(long monsterId) {
		Bundle args = new Bundle();
		args.putLong(ARG_MONSTER_ID, monsterId);
		MonsterSummaryFragment f = new MonsterSummaryFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		// Check for a Monster ID as an argument, and find the monster
		Bundle args = getArguments();
		if (args != null) {
			long monsterId = args.getLong(ARG_MONSTER_ID, -1);
			if (monsterId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.monster_detail_fragment, args, new MonsterLoaderCallbacks());
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_monster_summary, container, false);

		mMonsterLabelTextView = (TextView) view.findViewById(R.id.detail_monster_label);
		mMonsterIconImageView = (ImageView) view.findViewById(R.id.detail_monster_image);

		mWeaknessData = (FlowLayout) view.findViewById(R.id.weakness_data);
		mTrapData = (FlowLayout) view.findViewById(R.id.trap_data);
		mBombData = (FlowLayout) view.findViewById(R.id.bomb_data);
		mAilments = (LinearLayout) view.findViewById(R.id.ailments_data);
		mMovesData = (TextView) view.findViewById(R.id.moves_data);

		// Mods if monster has a secondary state
		// Sections
		mWeaknessMod = (LinearLayout) view.findViewById(R.id.weaknesses_mod);
		mTrapMod = (LinearLayout) view.findViewById(R.id.trap_mod);
		mBombMod = (LinearLayout) view.findViewById(R.id.bombs_mod);

		// Text titles
		mWeaknessModText = (TextView) view.findViewById(R.id.weakness_mod_text);
		mTrapModText = (TextView) view.findViewById(R.id.trap_mod_text);
		mBombModText = (TextView) view.findViewById(R.id.bomb_mod_text);

		// FlowLayouts
		mWeaknessModData = (FlowLayout) view.findViewById(R.id.weakness_mod_data);
		mTrapModData = (FlowLayout) view.findViewById(R.id.trap_mod_data);
		mBombModData = (FlowLayout) view.findViewById(R.id.bombs_mod_data);

		// Dividers
		mWeaknessModDiv = view.findViewById(R.id.weakness_mod_div);
		mTrapModDiv = view.findViewById(R.id.trap_mod_div);
		mBombModDiv = view.findViewById(R.id.bombs_mod_div);

		return view;
	}

	private void updateUI() {

		LayoutInflater inflater = getLayoutInflater(mBundle);

		// Header
		String cellText = mMonster.getName();
		String cellImage = "icons_monster/" + mMonster.getFileLocation();

		mMonsterLabelTextView.setText(cellText);
        AssetManager manager = getActivity().getAssets();
        try {
            InputStream open = manager.open(cellImage);
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            // Assign the bitmap to an ImageView in this layout
            mMonsterIconImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

		updateWeaknessUI();

	}

	// Update weakness display after loader callback
	private void updateWeaknessUI(){
		// Weakness Section
		int fire, water, thunder, ice, dragon, poison, paralysis, sleep, pitfalltrap,
				shocktrap, flashbomb, sonicbomb, dungbomb, meat;


		// Load ArrayList of weaknesses without using a loader
		ArrayList<MonsterWeakness> weaknesses = DataManager.get(getActivity()).queryMonsterWeaknessArray(mMonster.getId());

		// Get "Normal" weaknesses
		mWeakness = weaknesses.get(0);

		// Fire
		evalWeakness(mWeakness.getFire(), mWeaknessData, getResources().getString(R.string.image_location_fire));
		// Water
		evalWeakness(mWeakness.getWater(), mWeaknessData, getResources().getString(R.string.image_location_water));
		// Thunder
		evalWeakness(mWeakness.getThunder(), mWeaknessData, getResources().getString(R.string.image_location_thunder));
		// Ice
		evalWeakness(mWeakness.getIce(), mWeaknessData, getResources().getString(R.string.image_location_ice));
		// Dragon
		evalWeakness(mWeakness.getDragon(), mWeaknessData, getResources().getString(R.string.image_location_dragon));
		// Poison
		evalWeakness(mWeakness.getPoison(), mWeaknessData, getResources().getString(R.string.image_location_poison));
		// Paralysis
		evalWeakness(mWeakness.getParalysis(), mWeaknessData, getResources().getString(R.string.image_location_paralysis));
		// Sleep
		evalWeakness(mWeakness.getSleep(), mWeaknessData, getResources().getString(R.string.image_location_sleep));

		// Pitfall Trap
		if(mWeakness.getPitfalltrap() != 0)
			addIcon(mTrapData, getResources().getString(R.string.image_location_pitfall_trap), null);
		// Shock Trap
		if(mWeakness.getShocktrap() != 0)
			addIcon(mTrapData, getResources().getString(R.string.image_location_shock_trap), null);
		// Meat
		if(mWeakness.getMeat() != 0)
			addIcon(mTrapData, getResources().getString(R.string.image_location_meat), null);

		// Flash Bomb
		if(mWeakness.getFlashbomb() != 0)
			addIcon(mBombData, getResources().getString(R.string.image_location_flash_bomb), null);
		// Sonic Bomb
		if(mWeakness.getSonicbomb() != 0)
			addIcon(mBombData, getResources().getString(R.string.image_location_sonic_bomb), null);
		// Dung Bomb
		if(mWeakness.getDungbomb() != 0)
			addIcon(mBombData, getResources().getString(R.string.image_location_dung_bomb), null);


		// Apply CHARGED or ENRAGED weaknesses if applicable. Only supports one more state right now.
		if (weaknesses.size() > 1){
			mWeakness = weaknesses.get(1);

			String mState = mWeakness.getState();

			// Make all mod layouts and dividers visible
			mWeaknessMod.setVisibility(View.VISIBLE);
			mTrapMod.setVisibility(View.VISIBLE);
			mBombMod.setVisibility(View.VISIBLE);
			mWeaknessModDiv.setVisibility(View.VISIBLE);
			mTrapModDiv.setVisibility(View.VISIBLE);
			mBombModDiv.setVisibility(View.VISIBLE);

			// Set new section names
			mWeaknessModText.setText("(" + mState + ")");
			mTrapModText.setText("(" + mState + ")");
			mBombModText.setText("(" + mState + ")");

			// Set Data
			// Fire
			evalWeakness(mWeakness.getFire(), mWeaknessModData, getResources().getString(R.string.image_location_fire));
			// Water
			evalWeakness(mWeakness.getWater(), mWeaknessModData, getResources().getString(R.string.image_location_water));
			// Thunder
			evalWeakness(mWeakness.getThunder(), mWeaknessModData, getResources().getString(R.string.image_location_thunder));
			// Ice
			evalWeakness(mWeakness.getIce(), mWeaknessModData, getResources().getString(R.string.image_location_ice));
			// Dragon
			evalWeakness(mWeakness.getDragon(), mWeaknessModData, getResources().getString(R.string.image_location_dragon));
			// Poison
			evalWeakness(mWeakness.getPoison(), mWeaknessModData, getResources().getString(R.string.image_location_poison));
			// Paralysis
			evalWeakness(mWeakness.getParalysis(), mWeaknessModData, getResources().getString(R.string.image_location_paralysis));
			// Sleep
			evalWeakness(mWeakness.getSleep(), mWeaknessModData, getResources().getString(R.string.image_location_sleep));

			// Pitfall Trap
			if(mWeakness.getPitfalltrap() != 0)
				addIcon(mTrapModData, getResources().getString(R.string.image_location_pitfall_trap), null);
			// Shock Trap
			if(mWeakness.getShocktrap() != 0)
				addIcon(mTrapModData, getResources().getString(R.string.image_location_shock_trap), null);
			// Meat
			if(mWeakness.getMeat() != 0)
				addIcon(mTrapModData, getResources().getString(R.string.image_location_meat), null);

			// Flash Bomb
			if(mWeakness.getFlashbomb() != 0)
				addIcon(mBombModData, getResources().getString(R.string.image_location_flash_bomb), null);
			// Sonic Bomb
			if(mWeakness.getSonicbomb() != 0)
				addIcon(mBombModData, getResources().getString(R.string.image_location_sonic_bomb), null);
			// Dung Bomb
			if(mWeakness.getDungbomb() != 0)
				addIcon(mBombModData, getResources().getString(R.string.image_location_dung_bomb), null);
		}


		// Signature Moves section
		mMovesData.setText(mMonster.getSignatureMove());

	}

	private void evalWeakness(int weaknessvalue, FlowLayout parentview, String imagelocation){
		// Add icon and modifier to show effectiveness
		switch(weaknessvalue){
			case 1:
				addIcon(parentview, imagelocation, null);
				break;
			case 2:
				addIcon(parentview, imagelocation, getResources().getString(R.string.image_location_effectiveness_2));
				break;
			case 3:
				addIcon(parentview, imagelocation, getResources().getString(R.string.image_location_effectiveness_3));
				break;
			case 0:
				// Do nothing
				break;
		}
	}

	// Add small_icon to a particular LinearLayout
	private void addIcon(FlowLayout parentview, String imagelocation, String imagemodlocation){
		LayoutInflater inflater = getLayoutInflater(mBundle);
		ImageView mImage; // Generic image holder
		ImageView mImageMod; // Modifier image holder
		View view; // Generic icon view holder

		// Create new small_icon layout
		view = inflater.inflate(R.layout.small_icon, parentview, false);

		// Get reference to image in small_icon layout
		mImage = (ImageView) view.findViewById(R.id.image);
		mImageMod = (ImageView) view.findViewById(R.id.image_mod);

		// Open Image
		String cellImage = imagelocation;
		AssetManager manager = getActivity().getAssets();
		try {
			InputStream open = manager.open(cellImage);
			Bitmap bitmap = BitmapFactory.decodeStream(open);
			// Assign the bitmap to an ImageView in this layout
			mImage.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Open Image Mod if applicable
		if(imagemodlocation != null){
			cellImage = imagemodlocation;
			manager = getActivity().getAssets();
			try {
				InputStream open = manager.open(cellImage);
				Bitmap bitmap = BitmapFactory.decodeStream(open);
				// Assign the bitmap to an ImageView in this layout
				mImageMod.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			mImageMod.setVisibility(View.VISIBLE);
		}

		// Add small_icon to appropriate layout
		parentview.addView(view);
	}

    // Adapter to populate the Ailments Listview
    private class MonsterAilmentsCursorAdapter extends CursorAdapter{

        private MonsterAilmentCursor mMonsterAilmentsCursor;

        public MonsterAilmentsCursorAdapter(Context context,
                                         MonsterAilmentCursor cursor) {
            super(context, cursor, 0);
            mMonsterAilmentsCursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.fragment_ailment_listitem,
                    parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the Ailment for the current row
            MonsterAilment mMonsterAilment = mMonsterAilmentsCursor.getAilment();

            // Locate textview
            TextView mAilment = (TextView) view.findViewById(R.id.ailment_text);

            // Set ailment text
            mAilment.setText(mMonsterAilment.getAilment());
        }
    }

    // Loader to load data for this monster
	private class MonsterLoaderCallbacks implements LoaderCallbacks<Monster> {

		@Override
		public Loader<Monster> onCreateLoader(int id, Bundle args) {
			return new MonsterLoader(getActivity(), args.getLong(ARG_MONSTER_ID));
		}

		@Override
		public void onLoadFinished(Loader<Monster> loader, Monster run) {
			mMonster = run;
            LoaderManager lm = getLoaderManager();
			Bundle args = new Bundle();
            args.putLong(ARG_MONSTER_ID, run.getId());

            // Load ailments data after monster is found
            lm.initLoader(R.id.monster_ailments, args, new MonsterAilmentsLoaderCallbacks());
		}

		@Override
		public void onLoaderReset(Loader<Monster> loader) {
			// Do nothing
		}
	}

    // Loader to load the ailment data for this monster
    private class MonsterAilmentsLoaderCallbacks implements LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // Get cursor based on Monster ID
            return new MonsterAilmentCursorLoader(getActivity(), args.getLong(ARG_MONSTER_ID));
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

            // Get the cursor adapter
            MonsterSummaryFragment.MonsterAilmentsCursorAdapter adapter = new MonsterSummaryFragment.MonsterAilmentsCursorAdapter(
                    getActivity(), (MonsterAilmentCursor) cursor);

            // mAilmentsListView.setAdapter(adapter);
            // Assign list items to LinearLayout instead of ListView

            // mAilmentsLinearLayout should be the vertical LinearLayout that you substituted the listview with
            for(int i=0;i<adapter.getCount();i++) {
                LinearLayout v = (LinearLayout) adapter.getView(i, null, null);
                mAilments.addView(v);
            }

            // Update the UI after loaders are finished
            updateUI();

			getLoaderManager().destroyLoader(R.id.monster_ailments);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // Stop using the cursor (via the adapter)
            //mAilmentsListView.setAdapter(null);
        }
    }
	
	private String checkDamageValue(String damage) {
		String ret = damage;
		if (ret.equals("-1")) {
			ret = "--";
		}
		else if (ret.equals("-2")) {
			ret = "?";
		}
		return ret;
	}
}
