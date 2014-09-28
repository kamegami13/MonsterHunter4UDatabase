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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.object.Location;
import com.daviancorp.android.loader.LocationLoader;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class LocationDetailFragment extends Fragment {
	private static final String ARG_LOCATION_ID = "LOCATION_ID";
	
	private Location mLocation;
	
	private TextView mLocationLabelTextView;
	private ImageView mLocationIconImageView;

	public static LocationDetailFragment newInstance(long locationId) {
		Bundle args = new Bundle();
		args.putLong(ARG_LOCATION_ID, locationId);
		LocationDetailFragment f = new LocationDetailFragment();
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
			long locationId = args.getLong(ARG_LOCATION_ID, -1);
			if (locationId != -1) {
				LoaderManager lm = getLoaderManager();
				lm.initLoader(R.id.location_detail_fragment, args, new LocationLoaderCallbacks());
			}
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_location_detail, container, false);
		
		mLocationLabelTextView = (TextView) view.findViewById(R.id.map_text);
		mLocationIconImageView = (ImageView) view.findViewById(R.id.map_image);
		
		return view;
	}
	
	private void updateUI() throws IOException {
		String cellText = mLocation.getName();
		String cellImage = "icons_location/" + mLocation.getFileLocation();
		
		mLocationLabelTextView.setText(cellText);
		
		// Read a Bitmap from Assets
        AssetManager manager = getActivity().getAssets();
        InputStream open = null;
        
        Log.d("heyo", "location = " + cellImage);
        
        try {
            open = manager.open(cellImage);
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            // Assign the bitmap to an ImageView in this layout
            mLocationIconImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        finally{
        	if(open != null){
        		open.close();
        	}
        }
	}
	
	private class LocationLoaderCallbacks implements LoaderCallbacks<Location> {
		
		@Override
		public Loader<Location> onCreateLoader(int id, Bundle args) {
			return new LocationLoader(getActivity(), args.getLong(ARG_LOCATION_ID));
		}
		
		@Override
		public void onLoadFinished(Loader<Location> loader, Location run) {
			mLocation = run;
			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void onLoaderReset(Loader<Location> loader) {
			// Do nothing
		}
	}
}