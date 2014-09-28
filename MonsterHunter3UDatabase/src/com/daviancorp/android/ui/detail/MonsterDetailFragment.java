package com.daviancorp.android.ui.detail;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Monster;
import com.daviancorp.android.data.object.MonsterDamage;
import com.daviancorp.android.loader.MonsterLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class MonsterDetailFragment extends Fragment {
	private static final String ARG_MONSTER_ID = "MONSTER_ID";

	private Bundle mBundle;
	
	private Monster mMonster;
	
	private TextView mMonsterLabelTextView;
	private ImageView mMonsterIconImageView;
	
	private TableLayout mWeaponDamageTL, mElementalDamageTL;
	
	private ImageView mCutImageView, mImpactImageView, mShotImageView, mKOImageView;
	private ImageView mFireImageView, mWaterImageView, mIceImageView, 
		mThunderImageView, mDragonImageView;
	
	public static MonsterDetailFragment newInstance(long monsterId) {
		Bundle args = new Bundle();
		args.putLong(ARG_MONSTER_ID, monsterId);
		MonsterDetailFragment f = new MonsterDetailFragment();
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_monster_detail, container, false);
		
		mMonsterLabelTextView = (TextView) view.findViewById(R.id.detail_monster_label);
		mMonsterIconImageView = (ImageView) view.findViewById(R.id.detail_monster_image);

		mCutImageView = (ImageView) view.findViewById(R.id.cut);
		mImpactImageView = (ImageView) view.findViewById(R.id.impact);
		mShotImageView = (ImageView) view.findViewById(R.id.shot);
		mKOImageView = (ImageView) view.findViewById(R.id.ko);
		mFireImageView = (ImageView) view.findViewById(R.id.fire);
		mWaterImageView = (ImageView) view.findViewById(R.id.water);
		mIceImageView = (ImageView) view.findViewById(R.id.ice);
		mThunderImageView = (ImageView) view.findViewById(R.id.thunder);
		mDragonImageView = (ImageView) view.findViewById(R.id.dragon);
	
		mWeaponDamageTL = (TableLayout) view.findViewById(R.id.weapon_damage);
		mElementalDamageTL = (TableLayout) view.findViewById(R.id.elemental_damage);
		
		return view;
	}
	
	private void updateUI() {
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
        
		// Draw Drawable
		mCutImageView.setImageResource(R.drawable.cut);
		mImpactImageView.setImageResource(R.drawable.impact);
		mShotImageView.setImageResource(R.drawable.shot);
		mKOImageView.setImageResource(R.drawable.stun);

		mFireImageView.setImageResource(R.drawable.fire);
		mWaterImageView.setImageResource(R.drawable.water);
		mIceImageView.setImageResource(R.drawable.ice);
		mThunderImageView.setImageResource(R.drawable.thunder);
		mDragonImageView.setImageResource(R.drawable.dragon);
		
		ArrayList<MonsterDamage> damages = 
				DataManager.get(getActivity()).queryMonsterDamageArray(mMonster.getId());

		MonsterDamage damage = null;
		String body_part, cut, impact, shot, ko, fire, water, ice, thunder, dragon;
		
//		body_part_tv1
//		body_part_tv2
//		cut_tv
//		impact_tv
//		shot_tv
//		ko_tv
//		fire_tv
//		water_tv
//		ice_tv
//		thunder_tv
//		dragon_tv
		
		LayoutInflater inflater = getLayoutInflater(mBundle);
		
		for(int i = 0; i < damages.size(); i++) {
			TableRow wdRow = (TableRow) inflater.inflate(
					R.layout.fragment_monster_detail_listitem, mWeaponDamageTL, false);
			TableRow edRow = (TableRow) inflater.inflate(
					R.layout.fragment_monster_detail_listitem, mElementalDamageTL, false);
			  
			damage = damages.get(i);
			
			body_part = checkDamageValue(damage.getBodyPart());
			cut = checkDamageValue("" + damage.getCut());
			impact = checkDamageValue("" + damage.getImpact());
			shot = checkDamageValue("" + damage.getShot());
			ko = checkDamageValue("" + damage.getKo());
			fire = checkDamageValue("" + damage.getFire());
			water = checkDamageValue("" + damage.getWater());
			ice = checkDamageValue("" + damage.getIce());
			thunder = checkDamageValue("" + damage.getThunder());
			dragon = checkDamageValue("" + damage.getDragon());
			
			TextView body_part_tv1 = (TextView) wdRow.findViewById(R.id.body_part);
			TextView cut_tv = (TextView) wdRow.findViewById(R.id.dmg1);
			TextView impact_tv = (TextView) wdRow.findViewById(R.id.dmg2);
			TextView shot_tv = (TextView) wdRow.findViewById(R.id.dmg3);
			TextView ko_tv = (TextView) wdRow.findViewById(R.id.dmg4);
			TextView dummy_tv = (TextView) wdRow.findViewById(R.id.dmg5);
			
			TextView body_part_tv2 = (TextView) edRow.findViewById(R.id.body_part);
			TextView fire_tv = (TextView) edRow.findViewById(R.id.dmg1);
			TextView water_tv = (TextView) edRow.findViewById(R.id.dmg2);
			TextView ice_tv = (TextView) edRow.findViewById(R.id.dmg3);
			TextView thunder_tv = (TextView) edRow.findViewById(R.id.dmg4);
			TextView dragon_tv = (TextView) edRow.findViewById(R.id.dmg5);
			
			body_part_tv1.setText(body_part);
			body_part_tv2.setText(body_part);
			cut_tv.setText(cut);
			impact_tv.setText(impact);
			shot_tv.setText(shot);
			ko_tv.setText(ko);
			fire_tv.setText(fire);
			water_tv.setText(water);
			ice_tv.setText(ice);
			thunder_tv.setText(thunder);
			dragon_tv.setText(dragon);
			dummy_tv.setText("");
			
			mWeaponDamageTL.addView(wdRow);
			mElementalDamageTL.addView(edRow);
		}
	}
	
	private class MonsterLoaderCallbacks implements LoaderCallbacks<Monster> {
		
		@Override
		public Loader<Monster> onCreateLoader(int id, Bundle args) {
			return new MonsterLoader(getActivity(), args.getLong(ARG_MONSTER_ID));
		}
		
		@Override
		public void onLoadFinished(Loader<Monster> loader, Monster run) {
			mMonster = run;
			updateUI();
		}
		
		@Override
		public void onLoaderReset(Loader<Monster> loader) {
			// Do nothing
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
