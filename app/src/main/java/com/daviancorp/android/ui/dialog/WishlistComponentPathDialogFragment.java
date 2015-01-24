package com.daviancorp.android.ui.dialog;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class WishlistComponentPathDialogFragment extends DialogFragment {
	public static final String EXTRA_PATH =
			"com.daviancorp.android.ui.general.wishlist_path";
	private static final String ARG_WISHLIST_NAME = "WISHLIST_NAME";
	private static final String ARG_WEAPON_NAME = "WEAPON_NAME";
	private static final String ARG_WEAPON_PATHS = "WEAPON_PATHS";

	private String choice;
	
	public static WishlistComponentPathDialogFragment newInstance(
			String wishlistName, String weaponName, ArrayList<String> paths) {
		Bundle args = new Bundle();
		args.putString(ARG_WISHLIST_NAME, wishlistName);
		args.putString(ARG_WEAPON_NAME, weaponName);
		args.putStringArrayList(ARG_WEAPON_PATHS, paths);
		WishlistComponentPathDialogFragment f = new WishlistComponentPathDialogFragment();
		f.setArguments(args);
		return f;
	}
	
	private void sendResult(int resultCode, String path) {
		if (getTargetFragment() == null)
			return;

		Intent i = new Intent();
		i.putExtra(EXTRA_PATH, path);
		
		getTargetFragment()
			.onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {	
		final String wishlistName = getArguments().getString(ARG_WEAPON_NAME);
		final String weaponName = getArguments().getString(ARG_WEAPON_NAME);
		ArrayList<String> paths = getArguments().getStringArrayList(ARG_WEAPON_PATHS);
		
		final CharSequence[] choices = new CharSequence[paths.size()];
		
		for(int i = 0; i < choices.length; i++) {
			choices[i] = paths.get(i);
		}
		choice = choices[0].toString();
		
		return new AlertDialog.Builder(getActivity())
			.setTitle("How to make '" + weaponName + "'?")
			.setSingleChoiceItems(choices, 0, new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   choice = choices[id].toString();
	               }
			})
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   sendResult(Activity.RESULT_OK, choice);

           				Toast.makeText(getActivity(), "Added to '" + wishlistName + "' wishlist", 
        					   Toast.LENGTH_SHORT).show();
	               }
			})
			.create();
	}
}
