package com.daviancorp.android.ui.detail;

import java.io.IOException;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daviancorp.android.mh4udatabase.R;

public class WeaponBowgunDetailFragment extends WeaponDetailFragment {

    private TextView mWeaponReloadTextView, mWeaponRecoilTextView,
            mWeaponSteadinessTextView, mWeaponSpecialTypeTextView;

    private TextView mNormal1, mNormal2, mNormal3, mPierce1, mPierce2, mPierce3,
            mPellet1, mPellet2, mPellet3, mCrag1, mCrag2, mCrag3, mClust1,
            mClust2, mClust3, mFlaming, mWater, mThunder, mFreeze, mDragon,
            mPoison1, mPoison2, mPara1, mPara2, mSleep1, mSleep2, mSub1, mSub2,
            mExhaust1, mExhaust2, mSlicing, mWyvernfire, mBlast, mRecov1, mRecov2,
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
        mExhaust1 = (TextView) view.findViewById(R.id.exhaust1);
        mExhaust2 = (TextView) view.findViewById(R.id.exhaust2);
        mSlicing = (TextView) view.findViewById(R.id.slicing);
        mWyvernfire = (TextView) view.findViewById(R.id.wyvernfire);
        mBlast = (TextView) view.findViewById(R.id.blast);
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

        TextView ammoView;

        for (String a : ammos) {
            ammoView = getAmmoType(a);
            setAmmoText(a, ammoView);
        }

        if (mWeapon.getWtype().equals("Light Bowgun")) {
            mWeaponSpecialTypeTextView.setText("Rapid Fire:");
        } else if (mWeapon.getWtype().equals("Heavy Bowgun")) {
            mWeaponSpecialTypeTextView.setText("Crouching Fire:");
        }


        //TODO Reenable when rapid fire data makes sense
//		String[] specials = mWeapon.getRapidFire().split("\\|");
//		int numSpecial = specials.length;
//
//		if (numSpecial >= 1) {
//			String[] tempSpecial = specials[0].split(" ");
//			mSpecial1.setText(tempSpecial[0]);
//			mValue1.setText(tempSpecial[1]);
//		}
//		if (numSpecial >= 2) {
//			String[] tempSpecial = specials[1].split(" ");
//			mSpecial2.setText(tempSpecial[0]);
//			mValue2.setText(tempSpecial[1]);
//		}
//		if (numSpecial >= 3) {
//			String[] tempSpecial = specials[2].split(" ");
//			mSpecial3.setText(tempSpecial[0]);
//			mValue3.setText(tempSpecial[1]);
//		}
//		if (numSpecial >= 4) {
//			String[] tempSpecial = specials[3].split(" ");
//			mSpecial4.setText(tempSpecial[0]);
//			mValue4.setText(tempSpecial[1]);
//		}
//		if (numSpecial >= 5) {
//			String[] tempSpecial = specials[4].split(" ");
//			mSpecial5.setText(tempSpecial[0]);
//			mValue5.setText(tempSpecial[1]);
//		}
    }

    private TextView getAmmoType(String a) {
        TextView ammoView;
        String[] temp = a.split(" ");
        String ammo = temp[0];

        if (ammo.equals("Normal1")) {
            return mNormal1;
        } else if (ammo.equals("Normal2")) {
            return mNormal2;
        } else if (ammo.equals("Normal3")) {
            return mNormal3;
        } else if (ammo.equals("Pierce1")) {
            return mPierce1;
        } else if (ammo.equals("Pierce2")) {
            return mPierce2;
        } else if (ammo.equals("Pierce3")) {
            return mPierce3;
        } else if (ammo.equals("Pellet1")) {
            return mPellet1;
        } else if (ammo.equals("Pellet2")) {
            return mPellet2;
        } else if (ammo.equals("Pellet3")) {
            return mPellet3;
        } else if (ammo.equals("Crag1")) {
            return mCrag1;
        } else if (ammo.equals("Crag2")) {
            return mCrag2;
        } else if (ammo.equals("Crag3")) {
            return mCrag3;
        } else if (ammo.equals("Clust1")) {
            return mClust1;
        } else if (ammo.equals("Clust2")) {
            return mClust2;
        } else if (ammo.equals("Clust3")) {
            return mClust3;
        } else if (ammo.equals("Flaming")) {
            return mFlaming;
        } else if (ammo.equals("Water")) {
            return mWater;
        } else if (ammo.equals("Thunder")) {
            return mThunder;
        } else if (ammo.equals("Freeze")) {
            return mFreeze;
        } else if (ammo.equals("Dragon")) {
            return mDragon;
        } else if (ammo.equals("Poison1")) {
            return mPoison1;
        } else if (ammo.equals("Poison2")) {
            return mPoison2;
        } else if (ammo.equals("Para1")) {
            return mPara1;
        } else if (ammo.equals("Para2")) {
            return mPara2;
        } else if (ammo.equals("Sleep1")) {
            return mSleep1;
        } else if (ammo.equals("Sleep2")) {
            return mSleep2;
        } else if (ammo.equals("Exhaust1")) {
            return mExhaust1;
        } else if (ammo.equals("Exhaust2")) {
            return mExhaust2;
        } else if (ammo.equals("Slicing")) {
            return mSlicing;
        } else if (ammo.equals("WyvernFire")) {
            return mWyvernfire;
        } else if (ammo.equals("Blast")) {
            return mBlast;
        } else if (ammo.equals("Recov1")) {
            return mRecov1;
        } else if (ammo.equals("Recov2")) {
            return mRecov2;
        } else if (ammo.equals("Demon")) {
            return mDemon;
        } else if (ammo.equals("Armor")) {
            return mArmor;
        } else if (ammo.equals("Paint")) {
            return mPaint;
        } else {
            return mTranq;
        }
    }

    private void setAmmoText(String a, TextView ammoView) {
        String[] temp = a.split(" ");
        String amt = temp[1];
        Boolean loadUp = false;

        if (amt.contains("*")) {
            loadUp = true;
            amt = amt.substring(0, amt.length() - 1);
        }

        ammoView.setText(amt);

        if (loadUp == true) {
            ammoView.setTypeface(null, Typeface.BOLD);
        }
    }
}
