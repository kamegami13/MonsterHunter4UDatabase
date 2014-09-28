package com.daviancorp.android.ui.detail;

import java.io.IOException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daviancorp.android.monsterhunter3udatabase.R;

public class WeaponBowgunDetailFragment extends WeaponDetailFragment {

	private TextView mWeaponReloadTextView, mWeaponRecoilTextView,
			mWeaponSteadinessTextView, mWeaponSpecialTypeTextView;

	private TextView mNormal1, mNormal2, mNormal3, mPierce1, mPierce2, mPierce3,
			mPellet1, mPellet2, mPellet3, mCrag1, mCrag2, mCrag3, mClust1,
			mClust2, mClust3, mFlaming, mWater, mThunder, mFreeze, mDragon,
			mPoison1, mPoison2, mPara1, mPara2, mSleep1, mSleep2, mSub1, mSub2,
			mExhaust1, mExhaust2, mSlicing, mWyvernfire, mSlime, mRecov1, mRecov2,
			mDemon, mArmor, mPaint, mTranq;
	
	private TextView mSpecial1, mSpecial2, mSpecial3, mSpecial4, mSpecial5,
			mValue1, mValue2, mValue3, mValue4, mValue5;
	
	public static WeaponBowgunDetailFragment newInstance(long weaponId) {
		Bundle args = new Bundle();
		args.putLong(WeaponDetailFragment.ARG_WEAPON_ID, weaponId);
		WeaponBowgunDetailFragment f = new WeaponBowgunDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon_bowgun_detail,
				container, false);

		mWeaponLabelTextView = (TextView) view
				.findViewById(R.id.detail_weapon_name);
		mWeaponTypeTextView = (TextView) view
				.findViewById(R.id.detail_weapon_type);
		mWeaponAttackTextView = (TextView) view
				.findViewById(R.id.detail_weapon_attack);
		mWeaponElementTextView = (TextView) view
				.findViewById(R.id.detail_weapon_element);
		mWeaponRarityTextView = (TextView) view
				.findViewById(R.id.detail_weapon_rarity);
		mWeaponSlotTextView = (TextView) view
				.findViewById(R.id.detail_weapon_slot);
		mWeaponAffinityTextView = (TextView) view
				.findViewById(R.id.detail_weapon_affinity);
		mWeaponDefenseTextView = (TextView) view
				.findViewById(R.id.detail_weapon_defense);
		mWeaponCreationTextView = (TextView) view
				.findViewById(R.id.detail_weapon_creation);
		mWeaponUpgradeTextView = (TextView) view
				.findViewById(R.id.detail_weapon_upgrade);

		mWeaponReloadTextView = (TextView) view
				.findViewById(R.id.detail_weapon_bowgun_reload);
		mWeaponRecoilTextView = (TextView) view
				.findViewById(R.id.detail_weapon_bowgun_recoil);
		mWeaponSteadinessTextView = (TextView) view
				.findViewById(R.id.detail_weapon_bowgun_steadiness);
		mWeaponSpecialTypeTextView = (TextView) view
				.findViewById(R.id.detail_weapon_bowgun_special);
		
		mNormal1 = (TextView) view.findViewById(R.id.normal1);
		mNormal2 = (TextView) view.findViewById(R.id.normal2);
		mNormal3 = (TextView) view.findViewById(R.id.normal3);
		mPierce1 = (TextView) view.findViewById(R.id.pierce1);
		mPierce2 = (TextView) view.findViewById(R.id.pierce2);
		mPierce3 = (TextView) view.findViewById(R.id.pierce3);
		mPellet1 = (TextView) view.findViewById(R.id.pellet1);
		mPellet2 = (TextView) view.findViewById(R.id.pellet2);
		mPellet3 = (TextView) view.findViewById(R.id.pellet3);
		mCrag1 = (TextView) view.findViewById(R.id.crag1);
		mCrag2 = (TextView) view.findViewById(R.id.crag2);
		mCrag3 = (TextView) view.findViewById(R.id.crag3);
		mClust1 = (TextView) view.findViewById(R.id.clust1);
		mClust2 = (TextView) view.findViewById(R.id.clust2);
		mClust3 = (TextView) view.findViewById(R.id.clust3);
		mFlaming = (TextView) view.findViewById(R.id.flaming);
		mWater = (TextView) view.findViewById(R.id.water);
		mThunder = (TextView) view.findViewById(R.id.thunder);
		mFreeze = (TextView) view.findViewById(R.id.freeze);
		mDragon = (TextView) view.findViewById(R.id.dragon);
		mPoison1 = (TextView) view.findViewById(R.id.poison1);
		mPoison2 = (TextView) view.findViewById(R.id.poison2);
		mPara1 = (TextView) view.findViewById(R.id.para1);
		mPara2 = (TextView) view.findViewById(R.id.para2);
		mSleep1 = (TextView) view.findViewById(R.id.sleep1);
		mSleep2 = (TextView) view.findViewById(R.id.sleep2);
		mSub1 = (TextView) view.findViewById(R.id.sub1);
		mSub2 = (TextView) view.findViewById(R.id.sub2);
		mExhaust1 = (TextView) view.findViewById(R.id.exhaust1);
		mExhaust2 = (TextView) view.findViewById(R.id.exhaust2);
		mSlicing = (TextView) view.findViewById(R.id.slicing);
		mWyvernfire = (TextView) view.findViewById(R.id.wyvernfire);
		mSlime = (TextView) view.findViewById(R.id.slime);
		mRecov1 = (TextView) view.findViewById(R.id.recov1);
		mRecov2 = (TextView) view.findViewById(R.id.recov2);
		mDemon = (TextView) view.findViewById(R.id.demon);
		mArmor = (TextView) view.findViewById(R.id.armor);
		mPaint = (TextView) view.findViewById(R.id.paint);
		mTranq = (TextView) view.findViewById(R.id.tranq);
		
		mSpecial1 = (TextView) view.findViewById(R.id.special1);
		mSpecial2 = (TextView) view.findViewById(R.id.special2);
		mSpecial3 = (TextView) view.findViewById(R.id.special3);
		mSpecial4 = (TextView) view.findViewById(R.id.special4);
		mSpecial5 = (TextView) view.findViewById(R.id.special5);
		
		mValue1 = (TextView) view.findViewById(R.id.value1);
		mValue2 = (TextView) view.findViewById(R.id.value2);
		mValue3 = (TextView) view.findViewById(R.id.value3);
		mValue4 = (TextView) view.findViewById(R.id.value4);
		mValue5 = (TextView) view.findViewById(R.id.value5);
		
		return view;
	}

	@Override
	protected void updateUI() throws IOException {
		super.updateUI();

		mWeaponReloadTextView.setText(mWeapon.getReloadSpeed());
		mWeaponRecoilTextView.setText(mWeapon.getRecoil());
		mWeaponSteadinessTextView.setText(mWeapon.getDeviation());
		
		String[] ammos = mWeapon.getAmmo().split("\\|");
		
		for(String a : ammos) {
			setAmmoText(a);
		}

		if (mWeapon.getWtype().equals("Light Bowgun")) {
			mWeaponSpecialTypeTextView.setText("Rapid Fire:");
		}
		else if (mWeapon.getWtype().equals("Heavy Bowgun")) {
			mWeaponSpecialTypeTextView.setText("Crouching Fire:");
		}
		
		String[] specials = mWeapon.getRapidFire().split("\\|");
		int numSpecial = specials.length;
		
		if (numSpecial >= 1) {
			String[] tempSpecial = specials[0].split(" ");
			mSpecial1.setText(tempSpecial[0]);
			mValue1.setText(tempSpecial[1]);
		}
		if (numSpecial >= 2) {
			String[] tempSpecial = specials[1].split(" ");
			mSpecial2.setText(tempSpecial[0]);
			mValue2.setText(tempSpecial[1]);
		}
		if (numSpecial >= 3) {
			String[] tempSpecial = specials[2].split(" ");
			mSpecial3.setText(tempSpecial[0]);
			mValue3.setText(tempSpecial[1]);
		}
		if (numSpecial >= 4) {
			String[] tempSpecial = specials[3].split(" ");
			mSpecial4.setText(tempSpecial[0]);
			mValue4.setText(tempSpecial[1]);
		}
		if (numSpecial >= 5) {
			String[] tempSpecial = specials[4].split(" ");
			mSpecial5.setText(tempSpecial[0]);
			mValue5.setText(tempSpecial[1]);
		}
	}
	
	private void setAmmoText(String a) {
		String[] temp = a.split(" ");
		String ammo = temp[0];
		String amt = temp[1];
		
		if (ammo.equals("Normal1")) {
			mNormal1.setText(amt);
		}
		else if (ammo.equals("Normal2")) {
			mNormal2.setText(amt);
		}
		else if (ammo.equals("Normal3")) {
			mNormal3.setText(amt);
		}
		else if (ammo.equals("Pierce1")) {
			mPierce1.setText(amt);
		}
		else if (ammo.equals("Pierce2")) {
			mPierce2.setText(amt);
		}
		else if (ammo.equals("Pierce3")) {
			mPierce3.setText(amt);
		}
		else if (ammo.equals("Pellet1")) {
			mPellet1.setText(amt);
		}
		else if (ammo.equals("Pellet2")) {
			mPellet2.setText(amt);
		}
		else if (ammo.equals("Pellet3")) {
			mPellet3.setText(amt);
		}
		else if (ammo.equals("Crag1")) {
			mCrag1.setText(amt);
		}
		else if (ammo.equals("Crag2")) {
			mCrag2.setText(amt);
		}
		else if (ammo.equals("Crag3")) {
			mCrag3.setText(amt);
		}
		else if (ammo.equals("Clust1")) {
			mClust1.setText(amt);
		}
		else if (ammo.equals("Clust2")) {
			mClust2.setText(amt);
		}
		else if (ammo.equals("Clust3")) {
			mClust3.setText(amt);
		}
		else if (ammo.equals("Flaming")) {
			mFlaming.setText(amt);
		}
		else if (ammo.equals("Water")) {
			mWater.setText(amt);
		}
		else if (ammo.equals("Thunder")) {
			mThunder.setText(amt);
		}
		else if (ammo.equals("Freeze")) {
			mFreeze.setText(amt);
		}
		else if (ammo.equals("Dragon")) {
			mDragon.setText(amt);
		}
		else if (ammo.equals("Poison1")) {
			mPoison1.setText(amt);
		}
		else if (ammo.equals("Poison2")) {
			mPoison2.setText(amt);
		}
		else if (ammo.equals("Para1")) {
			mPara1.setText(amt);
		}
		else if (ammo.equals("Para2")) {
			mPara2.setText(amt);
		}
		else if (ammo.equals("Sleep1")) {
			mSleep1.setText(amt);
		}
		else if (ammo.equals("Sleep2")) {
			mSleep2.setText(amt);
		}
		else if (ammo.equals("Sub1")) {
			mSub1.setText(amt);
		}
		else if (ammo.equals("Sub2")) {
			mSub2.setText(amt);
		}
		else if (ammo.equals("Exhaust1")) {
			mExhaust1.setText(amt);
		}
		else if (ammo.equals("Exhaust2")) {
			mExhaust2.setText(amt);
		}
		else if (ammo.equals("Slicing")) {
			mSlicing.setText(amt);
		}
		else if (ammo.equals("WyvernFire")) {
			mWyvernfire.setText(amt);
		}
		else if (ammo.equals("Slime")) {
			mSlime.setText(amt);
		}
		else if (ammo.equals("Recov1")) {
			mRecov1.setText(amt);
		}
		else if (ammo.equals("Recov2")) {
			mRecov2.setText(amt);
		}
		else if (ammo.equals("Demon")) {
			mDemon.setText(amt);
		}
		else if (ammo.equals("Armor")) {
			mArmor.setText(amt);
		}
		else if (ammo.equals("Paint")) {
			mPaint.setText(amt);
		}
		else if (ammo.equals("Tranq")) {
			mTranq.setText(amt);
		}
	}
}
