package com.daviancorp.android.ui.detail;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.object.Armor;
import com.daviancorp.android.loader.ArmorLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class ArmorDetailFragment extends Fragment {
	private static final String ARG_ARMOR_ID = "ARMOR_ID";

	private Armor mArmor;

	private TextView mArmorLabelTextView;
	private ImageView mArmorIconImageView;
	private TextView partTextView;
	private TextView defenseTextView;
	private TextView slotTextView;
	private TextView rareTextView;
	private TextView buyTextView;
	
	private TextView fireResTextView;
	private TextView waterResTextView;
	private TextView iceResTextView;
	private TextView thunderResTextView;
	private TextView dragonResTextView;

	public static ArmorDetailFragment newInstance(long armorId) {
		Bundle args = new Bundle();
		args.putLong(ARG_ARMOR_ID, armorId);
		ArmorDetailFragment f = new ArmorDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
		setHasOptionsMenu(true);

		// Check for a Item ID as an argument, and find the item
		Bundle args = getArguments();
		if (args != null) {
			long armorId = args.getLong(ARG_ARMOR_ID, -1);
			if (armorId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.armor_detail_fragment, args,
						new ArmorLoaderCallbacks());
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_armor_detail,
				container, false);

		mArmorLabelTextView = (TextView) view
				.findViewById(R.id.detail_armor_label);
		mArmorIconImageView = (ImageView) view
				.findViewById(R.id.detail_armor_image);

		partTextView = (TextView) view.findViewById(R.id.part);
		defenseTextView = (TextView) view.findViewById(R.id.defense);
		slotTextView = (TextView) view.findViewById(R.id.slot);
		rareTextView = (TextView) view.findViewById(R.id.rare);
		buyTextView = (TextView) view.findViewById(R.id.buy);

		fireResTextView = (TextView) view.findViewById(R.id.fire_res);
		waterResTextView = (TextView) view.findViewById(R.id.water_res);
		iceResTextView = (TextView) view.findViewById(R.id.ice_res);
		thunderResTextView = (TextView) view.findViewById(R.id.thunder_res);
		dragonResTextView = (TextView) view.findViewById(R.id.dragon_res);
		
		return view;
	}

	private void updateUI() throws IOException {
		String cellText = mArmor.getName();
		String cellImage = "";
		String cellPart = "" + mArmor.getSlot();
		String cellDefense = "" + mArmor.getDefense() + " (min) - " + mArmor.getMaxDefense() + " (max)";
		String cellSlot = "" + mArmor.getNumSlots();
		String cellRare = "" + mArmor.getRarity();
		String cellBuy = "" + mArmor.getBuy() + "z";
		
		String cellFire = "" + mArmor.getFireRes();
		String cellWater = "" + mArmor.getWaterRes();
		String cellIce = "" + mArmor.getIceRes();
		String cellThunder = "" + mArmor.getThunderRes();
		String cellDragon = "" + mArmor.getDragonRes();

		if (cellBuy.equals("0z")) {
			cellBuy = "-";
		}
		
		mArmorLabelTextView.setText(cellText);
		partTextView.setText(cellPart);
		defenseTextView.setText(cellDefense);
		slotTextView.setText(cellSlot);
		rareTextView.setText(cellRare);
		buyTextView.setText(cellBuy);
		
		fireResTextView.setText(cellFire);
		waterResTextView.setText(cellWater);
		iceResTextView.setText(cellIce);
		thunderResTextView.setText(cellThunder);
		dragonResTextView.setText(cellDragon);
		
		long createdId = mArmor.getId();

		 if ((createdId >= 1314) && (createdId < 1646)) {
				cellImage = "icons_armor/icons_head/head" + cellRare + ".png";
			}
			else if ((createdId >= 1646) && (createdId < 1983)) {
				cellImage = "icons_armor/icons_body/body" + cellRare + ".png";
			}
			else if ((createdId >= 1983) && (createdId < 2303)) {
				cellImage = "icons_armor/icons_arms/arms" + cellRare + ".png";
			}
			else if ((createdId >= 2303) && (createdId < 2623)) {
				cellImage = "icons_armor/icons_waist/waist" + cellRare + ".png";
			}
			else if ((createdId >= 2623) && (createdId < 2955)) {
				cellImage = "icons_armor/icons_legs/legs" + cellRare + ".png";
			}
		 
		// Read a Bitmap from Assets
		AssetManager manager = getActivity().getAssets();
		InputStream open = null;

		try {
			open = manager.open(cellImage);
			Bitmap bitmap = BitmapFactory.decodeStream(open);
			// Assign the bitmap to an ImageView in this layout
			mArmorIconImageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (open != null) {
				open.close();
			}
		}
	}

	private class ArmorLoaderCallbacks implements
			LoaderCallbacks<Armor> {

		@Override
		public Loader<Armor> onCreateLoader(int id, Bundle args) {
			return new ArmorLoader(getActivity(),
					args.getLong(ARG_ARMOR_ID));
		}

		@Override
		public void onLoadFinished(Loader<Armor> loader, Armor run) {
			mArmor = run;
			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onLoaderReset(Loader<Armor> loader) {
			// Do nothing
		}
	}
}
