package com.daviancorp.android.loader;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;

/*
 * Abstract class used for querying a specific data
 */
public abstract class DataLoader<D> extends AsyncTaskLoader<D> {
	private D mData;
	
	public DataLoader(Context context) {
		super(context);
	}
	
	@Override
	protected void onStartLoading() {
		if (mData != null) {
			deliverResult(mData);
		}
		else {
			forceLoad();
		}
	}
	
	@Override
	public void deliverResult(D data) {
		mData = data;
		if (isStarted())
			super.deliverResult(data);
	}
	
}
