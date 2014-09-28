package com.daviancorp.android.ui.list;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.monsterhunter3udatabase.R;

public class WeaponGridFragment extends Fragment {

	private GridView gridView;
	private final static int GREAT_SWORD = 0;
	private final static int LONG_SWORD = 1;
	private final static int SWORD_AND_SHIELD = 2;
	private final static int DUAL_BLADES = 3;
	private final static int HAMMER = 4;
	private final static int HUNTING_HORN = 5;
	private final static int LANCE = 6;
	private final static int GUNLANCE = 7;
	private final static int SWITCH_AXE = 8;
	private final static int LIGHT_BOWGUN = 9;
	private final static int HEAVY_BOWGUN = 10;
	private final static int BOW = 11;

	static final String[] weapons = new String[] { "Great Sword", "Long Sword",
			"Sword and Shield", "Dual Blades", "Hammer", "Hunting Horn",
			"Lance", "Gunlance", "Switch Axe", "Light Bowgun", "Heavy Bowgun",
			"Bow" };

	static final Integer[] drawables = new Integer[] { R.drawable.great_sword1,
			R.drawable.long_sword1, R.drawable.sword_and_shield1,
			R.drawable.dual_blades1, R.drawable.hammer1,
			R.drawable.hunting_horn1, R.drawable.lance1, R.drawable.gunlance1,
			R.drawable.switch_axe1, R.drawable.light_bowgun1,
			R.drawable.heavy_bowgun1, R.drawable.bow1 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weapon_grid, parent, false);

		ArrayList<Integer> mItems = new ArrayList<Integer>(
				Arrays.asList(drawables));

		gridView = (GridView) v.findViewById(R.id.grid_home);
		gridView.setAdapter(new WeaponItemAdapter(mItems));

		// gridView.setOnItemClickListener(new OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, View v,
		// int position, long id) {
		//
		// Intent intent = new Intent(getActivity(),
		// WeaponListActivity.class);
		//
		// Log.d("helpme", "pos: " + position);
		// switch (position) {
		// case GREAT_SWORD:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Great Sword");
		// break;
		// case LONG_SWORD:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Long Sword");
		// break;
		// case SWORD_AND_SHIELD:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Sword and Shield");
		// break;
		// case DUAL_BLADES:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Dual Blades");
		// break;
		// case HAMMER:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Hammer");
		// break;
		// case HUNTING_HORN:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Hunting Horn");
		// break;
		// case LANCE:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Lance");
		// break;
		// case GUNLANCE:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Gunlance");
		// break;
		// case SWITCH_AXE:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Switch Axe");
		// break;
		// case LIGHT_BOWGUN:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Light Bowgun");
		// break;
		// case HEAVY_BOWGUN:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
		// "Heavy Bowgun");
		// break;
		// case BOW:
		// intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE, "Bow");
		// break;
		// }
		// Log.d("helpme", "before startActivity");
		// startActivity(intent);
		// }
		// });

		return v;
	}

	private class WeaponItemAdapter extends ArrayAdapter<Integer> {
		public WeaponItemAdapter(ArrayList<Integer> items) {
			super(getActivity(), 0, items);
		}

		@Override
		public boolean isEnabled(int position) {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.fragment_weapon_grid_item, parent, false);
			}

			Integer item = getItem(position);
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.image);

			textView.setText(weapons[position]);
			imageView.setImageResource(item);

			imageView.setOnClickListener(new GridItemClickListener(
					getContext(), position));

			return convertView;
		}

		private class GridItemClickListener implements OnClickListener {
			private Context c;
			private int position;

			public GridItemClickListener(Context context, int position) {
				super();
				this.position = position;
				this.c = context;
			}

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(c, WeaponListActivity.class);

				switch (position) {
				case GREAT_SWORD:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Great Sword");
					break;
				case LONG_SWORD:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Long Sword");
					break;
				case SWORD_AND_SHIELD:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Sword and Shield");
					break;
				case DUAL_BLADES:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Dual Blades");
					break;
				case HAMMER:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Hammer");
					break;
				case HUNTING_HORN:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Hunting Horn");
					break;
				case LANCE:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Lance");
					break;
				case GUNLANCE:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Gunlance");
					break;
				case SWITCH_AXE:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Switch Axe");
					break;
				case LIGHT_BOWGUN:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Light Bowgun");
					break;
				case HEAVY_BOWGUN:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
							"Heavy Bowgun");
					break;
				case BOW:
					intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE, "Bow");
					break;
				}
				c.startActivity(intent);
			}
		}
	}
}