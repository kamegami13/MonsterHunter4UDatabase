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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Item;
import com.daviancorp.android.loader.ItemLoader;
import com.daviancorp.android.mh4udatabase.R;

public class ItemDetailFragment extends Fragment {
	private static final String ARG_ITEM_ID = "ITEM_ID";
	
	private Item mItem;
	
	private TextView mItemLabelTextView;
	private ImageView mItemIconImageView;
	private TextView rareTextView;
	private TextView maxTextView;
	private TextView buyTextView;
	private TextView sellTextView;
	private TextView descriptionTextView;

	public static ItemDetailFragment newInstance(long itemId) {
		Bundle args = new Bundle();
		args.putLong(ARG_ITEM_ID, itemId);
		ItemDetailFragment f = new ItemDetailFragment();
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRetainInstance(true);
		
		// Check for a Item ID as an argument, and find the item
		Bundle args = getArguments();
		if (args != null) {
			long itemId = args.getLong(ARG_ITEM_ID, -1);
			if (itemId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.item_detail_fragment, args, new ItemLoaderCallbacks());
			}
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
		
		mItemLabelTextView = (TextView) view.findViewById(R.id.detail_item_label);
		mItemIconImageView = (ImageView) view.findViewById(R.id.detail_item_image);
	
		rareTextView = (TextView) view.findViewById(R.id.rare);
		maxTextView = (TextView) view.findViewById(R.id.max);
		sellTextView = (TextView) view.findViewById(R.id.sell);
		buyTextView = (TextView) view.findViewById(R.id.buy);
		descriptionTextView = (TextView) view.findViewById(R.id.description);

		return view;
	}
	
	private void updateUI() throws IOException {
		String cellText = mItem.getName();
		String cellImage;
		String cellRare = "" + mItem.getRarity();
		String cellMax = "" + mItem.getCarryCapacity();
		String cellSell = "" + mItem.getSell() + "z";
		String cellBuy = "" + mItem.getBuy() + "z";
		String cellDescription = "" + mItem.getDescription();

        String sub_type =  mItem.getSubType();

        switch(sub_type){
            case "Head":
                cellImage = "icons_armor/icons_head/head" + mItem.getRarity() + ".png";
                break;
            case "Body":
                cellImage = "icons_armor/icons_body/body" + mItem.getRarity() + ".png";
                break;
            case "Arms":
                cellImage = "icons_armor/icons_body/body" + mItem.getRarity() + ".png";
                break;
            case "Waist":
                cellImage = "icons_armor/icons_waist/waist" + mItem.getRarity() + ".png";
                break;
            case "Legs":
                cellImage = "icons_armor/icons_legs/legs" + mItem.getRarity() + ".png";
                break;
            case "Great Sword":
                cellImage = "icons_weapons/icons_great_sword/great_sword" + mItem.getRarity() + ".png";
                break;
            case "Long Sword":
                cellImage = "icons_weapons/icons_long_sword/long_sword" + mItem.getRarity() + ".png";
                break;
            case "Sword and Shield":
                cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + mItem.getRarity() + ".png";
                break;
            case "Dual Blades":
                cellImage = "icons_weapons/icons_dual_blades/dual_blades" + mItem.getRarity() + ".png";
                break;
            case "Hammer":
                cellImage = "icons_weapons/icons_hammer/hammer" + mItem.getRarity() + ".png";
                break;
            case "Hunting Horn":
                cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + mItem.getRarity() + ".png";
                break;
            case "Lance":
                cellImage = "icons_weapons/icons_hammer/hammer" + mItem.getRarity() + ".png";
                break;
            case "Gunlance":
                cellImage = "icons_weapons/icons_gunlance/gunlance" + mItem.getRarity() + ".png";
                break;
            case "Switch Axe":
                cellImage = "icons_weapons/icons_switch_axe/switch_axe" + mItem.getRarity() + ".png";
                break;
            case "Charge Blade":
                cellImage = "icons_weapons/icons_charge_blade/charge_blade" + mItem.getRarity() + ".png";
                break;
            case "Insect Glaive":
                cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + mItem.getRarity() + ".png";
                break;
            case "Light Bowgun":
                cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + mItem.getRarity() + ".png";
                break;
            case "Heavy Bowgun":
                cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + mItem.getRarity() + ".png";
                break;
            case "Bow":
                cellImage = "icons_weapons/icons_bow/bow" + mItem.getRarity() + ".png";
                break;
            default:
                cellImage = "icons_items/" + mItem.getFileLocation();
        }
		
		if (cellBuy.equals("0z")) {
			cellBuy = "-";
		}
		if (cellSell.equals("0z")) {
			cellSell = "-";
		}
		
		if (cellDescription.equals("null")) {
			cellDescription = "";
		}
		
		mItemLabelTextView.setText(cellText);
		rareTextView.setText(cellRare);
		maxTextView.setText(cellMax);
		buyTextView.setText(cellBuy);
		sellTextView.setText(cellSell);
		descriptionTextView.setText(cellDescription);
		
		// Read a Bitmap from Assets
        AssetManager manager = getActivity().getAssets();
        InputStream open = null;
        
        try {
            open = manager.open(cellImage);
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            // Assign the bitmap to an ImageView in this layout
            mItemIconImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        finally{
        	if(open != null){
        		open.close();
        	}
        }
	}
	
	private class ItemLoaderCallbacks implements LoaderCallbacks<Item> {
		
		@Override
		public Loader<Item> onCreateLoader(int id, Bundle args) {
			return new ItemLoader(getActivity(), args.getLong(ARG_ITEM_ID));
		}
		
		@Override
		public void onLoadFinished(Loader<Item> loader, Item run) {
			mItem = run;
			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void onLoaderReset(Loader<Item> loader) {
			// Do nothing
		}
	}
}
