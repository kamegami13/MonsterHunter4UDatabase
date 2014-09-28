package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.monsterhunter3udatabase.R;
import com.daviancorp.android.ui.detail.WeaponDetailFragment;

public class WishlistCopyDialogFragment extends DialogFragment {
	public static final String EXTRA_COPY =
			"com.daviancorp.android.ui.general.wishlist_copy";
	private static final String ARG_WISHLIST_ID = "WISHLIST_ID";
	private static final String ARG_WISHLIST_NAME = "WISHLIST_NAME";

	public static WishlistCopyDialogFragment newInstance(long id, String name) {
		Bundle args = new Bundle();
		args.putLong(ARG_WISHLIST_ID, id);
		args.putString(ARG_WISHLIST_NAME, name);
		WishlistCopyDialogFragment f = new WishlistCopyDialogFragment();
		f.setArguments(args);
		return f;
	}
	
	private void sendResult(int resultCode, boolean copy) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_COPY, copy);
		
		getTargetFragment()
			.onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View addView = inflater.inflate(R.layout.dialog_wishlist_rename, null);
		final EditText nameInput = (EditText) addView.findViewById(R.id.rename);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle("Copy '" + getArguments().getString(ARG_WISHLIST_NAME) + "' wishlist?")
			.setView(addView)
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   String name = nameInput.getText().toString();
	            	   DataManager.get(getActivity()).queryCopyWishlist(
	            			   getArguments().getLong(ARG_WISHLIST_ID), name);
	            	   
	   				   Toast.makeText(getActivity(), "Copied as '" + name + "'", Toast.LENGTH_SHORT).show();
	            	   sendResult(Activity.RESULT_OK, true);
	               }
			})
			.create();
	}
}
