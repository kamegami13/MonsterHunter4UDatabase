package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.daviancorp.android.data.database.DataManager;

public class WishlistDataDeleteDialogFragment extends DialogFragment {
	public static final String EXTRA_DELETE =
			"com.daviancorp.android.ui.general.wishlist_data_delete";
	
	private static final String ARG_WISHLIST_DATA_ID = "WISHLIST_DATA_ID";
	private static final String ARG_WISHLIST_DATA_NAME = "WISHLIST_DATA_NAME";

	public static WishlistDataDeleteDialogFragment newInstance(long id, String name) {
		Bundle args = new Bundle();
		args.putLong(ARG_WISHLIST_DATA_ID, id);
		args.putString(ARG_WISHLIST_DATA_NAME, name);
		WishlistDataDeleteDialogFragment f = new WishlistDataDeleteDialogFragment();
		f.setArguments(args);
		return f;
	}
	
	private void sendResult(int resultCode, boolean delete) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_DELETE, delete);
		
		getTargetFragment()
			.onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		final String name = getArguments().getString(ARG_WISHLIST_DATA_NAME);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle("Delete '" + name + "' from wishlist?")
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   DataManager.get(getActivity()).queryDeleteWishlistData(
	            			   getArguments().getLong(ARG_WISHLIST_DATA_ID));
	            	   
	   				   Toast.makeText(getActivity(), "Deleted '" + name + "'", Toast.LENGTH_SHORT).show();
	            	   sendResult(Activity.RESULT_OK, true);
	               }
			})
			.create();
	}
}
