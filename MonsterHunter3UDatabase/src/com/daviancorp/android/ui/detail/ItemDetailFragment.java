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

import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.loader.ItemLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

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
		String cellImage = "icons_items/" + mItem.getFileLocation();
		String cellRare = "" + mItem.getRarity();
		String cellMax = "" + mItem.getCarryCapacity();
		String cellSell = "" + mItem.getSell() + "z";
		String cellBuy = "" + mItem.getBuy() + "z";
		String cellDescription = "" + mItem.getDescription();
		
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
