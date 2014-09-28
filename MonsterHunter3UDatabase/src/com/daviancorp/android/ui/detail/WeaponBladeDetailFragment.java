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

public class WeaponBladeDetailFragment extends WeaponDetailFragment {

	private TextView mWeaponSpecialTypeTextView, mWeaponSpecialTextView;
	private ImageView mWeaponSharpnessImageView, mWeaponNote1ImageView, 
			mWeaponNote2ImageView, mWeaponNote3ImageView;

	public static WeaponBladeDetailFragment newInstance(long weaponId) {
		Bundle args = new Bundle();
		args.putLong(WeaponDetailFragment.ARG_WEAPON_ID, weaponId);
		WeaponBladeDetailFragment f = new WeaponBladeDetailFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon_blade_detail,
				container, false);

		mWeaponLabelTextView = (TextView) view
				.findViewById(R.id.detail_weapon_name);
		mWeaponTypeTextView = (TextView) view
				.findViewById(R.id.detail_weapon_type);
		mWeaponAttackTextView = (TextView) view
				.findViewById(R.id.detail_weapon_attack);
		mWeaponElementTextView = (TextView) view
				.findViewById(R.id.detail_weapon_element);
		mWeaponSharpnessImageView = (ImageView) view
				.findViewById(R.id.detail_weapon_blade_sharpness);
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
		mWeaponSpecialTypeTextView = (TextView) view
				.findViewById(R.id.detail_weapon_blade_special);
		mWeaponSpecialTextView = (TextView) view
				.findViewById(R.id.detail_weapon_blade_special_value);
		
		mWeaponNote1ImageView = (ImageView) view
				.findViewById(R.id.detail_weapon_blade_note1);		
		mWeaponNote2ImageView = (ImageView) view
				.findViewById(R.id.detail_weapon_blade_note2);		
		mWeaponNote3ImageView = (ImageView) view
				.findViewById(R.id.detail_weapon_blade_note3);
		
		return view;
	}

	@Override
	protected void updateUI() throws IOException {
		super.updateUI();

		/* Sharpness */
		String cellImage = "icons_sharpness/" + mWeapon.getSharpnessFile();

		// Read a Bitmap from Assets
		AssetManager manager = getActivity().getAssets();
		InputStream open = null;
		Bitmap bitmap = null;

		try {
			open = manager.open(cellImage);
			bitmap = BitmapFactory.decodeStream(open);
			// Assign the bitmap to an ImageView in this layout
			mWeaponSharpnessImageView.setImageBitmap(Bitmap.createScaledBitmap(
					bitmap, 380, 50, false));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (open != null) {
				open.close();
			}
		}
		
		/* Hunting Horn notes */
		if (mWeapon.getWtype().equals("Hunting Horn")) {
			mWeaponSpecialTypeTextView.setText("Horn Notes:");
			
			String notes = mWeapon.getHornNotes();
			
			try {
				open = manager.open(getNoteImage(notes.charAt(0)));
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponNote1ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
				
				open = manager.open(getNoteImage(notes.charAt(1)));
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponNote2ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
				
				open = manager.open(getNoteImage(notes.charAt(2)));
				bitmap = BitmapFactory.decodeStream(open);
				mWeaponNote3ImageView.setImageBitmap(Bitmap.createScaledBitmap(
						bitmap, 50, 50, false));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (open != null) {
					open.close();
				}
			}
		}
		
		/* Gunlance */
		else if (mWeapon.getWtype().equals("Gunlance")) {
			mWeaponSpecialTypeTextView.setText("Shelling:");
			mWeaponSpecialTextView.setText(mWeapon.getShellingType());
		}
		else if (mWeapon.getWtype().equals("Switch Axe")) {
			mWeaponSpecialTypeTextView.setText("Phial:");
			mWeaponSpecialTextView.setText(mWeapon.getPhial());
		}
	}
	
	private String getNoteImage(char note) {
		String file = "icons_monster_info/";
		
		switch (note) {
			case 'B':
				return file + "Note.blue.png";
			case 'C':
				return file + "Note.aqua.png";
			case 'G':
				return file + "Note.green.png";
			case 'O':
				return file + "Note.orange.png";
			case 'P':
				return file + "Note.purple.png";
			case 'R':
				return file + "Note.red.png";
			case 'W':
				return file + "Note.white.png";
			case 'Y':
				return file + "Note.yellow.png";
		}
		return "";
	}
}
