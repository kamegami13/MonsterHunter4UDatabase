package com.daviancorp.android.ui.detail;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Decoration;
import com.daviancorp.android.loader.DecorationLoader;
import com.daviancorp.android.mh4udatabase.R;

public class DecorationDetailFragment extends Fragment {
	private static final String ARG_DECORATION_ID = "DECORATION_ID";

	private Decoration mDecoration;

	private TextView mDecorationLabelTextView;
	private ImageView mDecorationIconImageView;
	private TextView rareTextView;
	private TextView maxTextView;
	private TextView buyTextView;
	private TextView sellTextView;
	private TextView slotsReqTextView;

	public static DecorationDetailFragment newInstance(long decorationId) {
		Bundle args = new Bundle();
		args.putLong(ARG_DECORATION_ID, decorationId);
		DecorationDetailFragment f = new DecorationDetailFragment();
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
			long decorationId = args.getLong(ARG_DECORATION_ID, -1);
			if (decorationId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.decoration_detail_fragment, args,
						new DecorationLoaderCallbacks());
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_decoration_detail,
				container, false);

		mDecorationLabelTextView = (TextView) view
				.findViewById(R.id.detail_decoration_label);
		mDecorationIconImageView = (ImageView) view
				.findViewById(R.id.detail_decoration_image);

		rareTextView = (TextView) view.findViewById(R.id.rare);
		maxTextView = (TextView) view.findViewById(R.id.max);
		sellTextView = (TextView) view.findViewById(R.id.sell);
		buyTextView = (TextView) view.findViewById(R.id.buy);
		slotsReqTextView = (TextView) view.findViewById(R.id.slots_req);

        // If the originator of this fragment's activity was the Armor Set Builder...
        if (getActivity().getIntent().getBooleanExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, false)) {
            Button selectButton = (Button) view.findViewById(R.id.select_button);
            selectButton.setVisibility(View.VISIBLE);
            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getActivity().getIntent();
                    intent.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, getArguments().getLong(ARG_DECORATION_ID)); // We put the armor's ID number as an extra of the intent.
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            });
        }

		return view;
	}

	private void updateUI() throws IOException {
		String cellText = mDecoration.getName();
		String cellImage = "icons_items/" + mDecoration.getFileLocation();
		String cellRare = "" + mDecoration.getRarity();
		String cellMax = "" + mDecoration.getCarryCapacity();
		String cellBuy = "" + mDecoration.getBuy() + "z";
		String cellSell = "" + mDecoration.getSell() + "z";
		String cellSlotsReq = "" + mDecoration.getSlotsString();

		if (cellBuy.equals("0z")) {
			cellBuy = "-";
		}
		if (cellSell.equals("0z")) {
			cellSell = "-";
		}
		
		mDecorationLabelTextView.setText(cellText);
		rareTextView.setText(cellRare);
		maxTextView.setText(cellMax);
		buyTextView.setText(cellBuy);
		sellTextView.setText(cellSell);
		slotsReqTextView.setText(cellSlotsReq);

		// Read a Bitmap from Assets
		AssetManager manager = getActivity().getAssets();
		InputStream open = null;

		try {
			open = manager.open(cellImage);
			Bitmap bitmap = BitmapFactory.decodeStream(open);
			// Assign the bitmap to an ImageView in this layout
			mDecorationIconImageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (open != null) {
				open.close();
			}
		}
	}

	private class DecorationLoaderCallbacks implements
			LoaderCallbacks<Decoration> {

		@Override
		public Loader<Decoration> onCreateLoader(int id, Bundle args) {
			return new DecorationLoader(getActivity(),
					args.getLong(ARG_DECORATION_ID));
		}

		@Override
		public void onLoadFinished(Loader<Decoration> loader, Decoration run) {
			mDecoration = run;
			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onLoaderReset(Loader<Decoration> loader) {
			// Do nothing
		}
	}
}
