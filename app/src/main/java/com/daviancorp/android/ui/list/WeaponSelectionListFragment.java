package com.daviancorp.android.ui.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.mh4udatabase.R;

public class WeaponSelectionListFragment extends ListFragment {

    private WeaponItemAdapter mAdapter = null;

	private final static int GREAT_SWORD = 0;
	private final static int LONG_SWORD = 1;
	private final static int SWORD_AND_SHIELD = 2;
	private final static int DUAL_BLADES = 3;
	private final static int HAMMER = 4;
	private final static int HUNTING_HORN = 5;
	private final static int LANCE = 6;
	private final static int GUNLANCE = 7;
    private final static int SWITCH_AXE = 8;
	private final static int CHARGE_BLADE = 9;
    private final static int INSECT_GLAIVE = 10;
	private final static int LIGHT_BOWGUN = 11;
	private final static int HEAVY_BOWGUN = 12;
	private final static int BOW = 13;

	static final String[] weapons = new String[] { "Great Sword", "Long Sword",
			"Sword and Shield", "Dual Blades", "Hammer", "Hunting Horn",
			"Lance", "Gunlance", "Switch Axe", "Charge Blade", "Insect Glaive", "Light Bowgun", "Heavy Bowgun",
			"Bow" };

	static final Drawable[] drawables = new Drawable[14];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_generic_list, parent, false);

        String imageLocation = "";
        for(int i = 0; i < weapons.length; i++)
        {
            imageLocation = weapons[i].toLowerCase().replaceAll(" ","_");
            imageLocation = "icons_weapons/icons_" + imageLocation + "/" + imageLocation + "1.png";

            Drawable d = null;

            try {
                d = Drawable.createFromStream(parent.getContext().getAssets().open(imageLocation),
                        null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            drawables[i] = d;
        }

        ArrayList<Drawable> mItems = new ArrayList<Drawable>(
                Arrays.asList(drawables));

        mAdapter = new WeaponItemAdapter(mItems);

        setListAdapter(mAdapter);

		return v;
	}

	private class WeaponItemAdapter extends ArrayAdapter<Drawable> {
		public WeaponItemAdapter(ArrayList<Drawable> items) {
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
						R.layout.fragment_list_item_basic, parent, false);
			}

			Drawable item = getItem(position);

			TextView textView = (TextView) convertView.findViewById(R.id.item_label);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.item_image);

            RelativeLayout itemLayout = (RelativeLayout) convertView.findViewById(R.id.listitem);

			textView.setText(weapons[position]);
			imageView.setImageDrawable(item);

			itemLayout.setOnClickListener(new WeaponListClickListener(convertView.getContext(), position));

			return convertView;
		}

		private class WeaponListClickListener implements OnClickListener {
			private Context c;
			private int position;

			public WeaponListClickListener(Context context, int position) {
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
                case CHARGE_BLADE:
                    intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
                            "Charge Blade");
                    break;
                case INSECT_GLAIVE:
                    intent.putExtra(WeaponListActivity.EXTRA_WEAPON_TYPE,
                            "Insect Glaive");
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