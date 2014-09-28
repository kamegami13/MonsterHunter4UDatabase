package com.daviancorp.android.ui.detail;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.monsterhunter3udatabase.R;

public class WeaponBowDetailFragment extends WeaponDetailFragment {

	private TextView mWeaponArcTextView, mWeaponCharge1TextView,
			mWeaponCharge2TextView, mWeaponCharge3TextView,
			mWeaponCharge4TextView;
	private ImageView mWeaponCoating1ImageView, mWeaponCoating2ImageView,
			mWeaponCoating3ImageView, mWeaponCoating4ImageView,
			mWeaponCoating5ImageView, mWeaponCoating6ImageView,
			mWeaponCoating7ImageView, mWeaponCoating8ImageView;

	public static WeaponBowDetailFragment newInstance(long weaponId) {
		Bundle args = new Bundle();
		args.putLong(WeaponDetailFragment.ARG_WEAPON_ID, weaponId);
		WeaponBowDetailFragment f = new WeaponBowDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon_bow_detail,
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

		mWeaponArcTextView = (TextView) view
				.findViewById(R.id.detail_weapon_bow_arc);
		mWeaponCharge1TextView = (TextView) view
				.findViewById(R.id.detail_weapon_bow_charge1);
		mWeaponCharge2TextView = (TextView) view
				.findViewById(R.id.detail_weapon_bow_charge2);
		mWeaponCharge3TextView = (TextView) view
				.findViewById(R.id.detail_weapon_bow_charge3);
		mWeaponCharge4TextView = (TextView) view
				.findViewById(R.id.detail_weapon_bow_charge4);

		mWeaponCoating1ImageView = (ImageView) view.findViewById(R.id.power);
		mWeaponCoating2ImageView = (ImageView) view.findViewById(R.id.crange);
		mWeaponCoating3ImageView = (ImageView) view.findViewById(R.id.poison);
		mWeaponCoating4ImageView = (ImageView) view.findViewById(R.id.para);
		mWeaponCoating5ImageView = (ImageView) view.findViewById(R.id.sleep);
		mWeaponCoating6ImageView = (ImageView) view.findViewById(R.id.exhaust);
		mWeaponCoating7ImageView = (ImageView) view.findViewById(R.id.slime);
		mWeaponCoating8ImageView = (ImageView) view.findViewById(R.id.paint);

		return view;
	}

	@Override
	protected void updateUI() throws IOException {
		super.updateUI();

		mWeaponArcTextView.setText(mWeapon.getRecoil());

		// Charges
		String[] charges = mWeapon.getCharges().split("\\|");

		mWeaponCharge1TextView.setText(charges[0]);
		mWeaponCharge2TextView.setText(charges[1]);
		mWeaponCharge3TextView.setText(charges[2]);
		mWeaponCharge4TextView.setText(charges[3]);

		// Read a Bitmap from Assets
		AssetManager manager = getActivity().getAssets();
		InputStream open = null;
		Bitmap bitmap = null;

		/* Coatings */
		String[] coatings = mWeapon.getCoatings().split(" ");

		try {
			if (!coatings[1].startsWith("0")) {
				open = manager.open("icons_items/Bottle-Red.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating1ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
			if (!coatings[2].startsWith("0")) {
				open = manager.open("icons_items/Bottle-White.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating2ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
			if (!coatings[3].startsWith("0")) {
				open = manager.open("icons_items/Bottle-Purple.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating3ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
			if (!coatings[4].startsWith("0")) {
				open = manager.open("icons_items/Bottle-Yellow.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating4ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
			if (!coatings[5].startsWith("0")) {
				open = manager.open("icons_items/Bottle-Cyan.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating5ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
			if (!coatings[6].startsWith("0")) {
				open = manager.open("icons_items/Bottle-Blue.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating6ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
			if (!coatings[7].startsWith("0")) {
				open = manager.open("icons_items/Bottle-Slime.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating7ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
			if (!coatings[8].startsWith("0")) {
				open = manager.open("icons_items/Bottle-Pink.png");
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponCoating8ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (open != null) {
				open.close();
			}
		}
	}
}
