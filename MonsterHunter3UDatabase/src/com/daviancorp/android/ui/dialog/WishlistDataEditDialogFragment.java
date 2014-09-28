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

public class WishlistDataEditDialogFragment extends DialogFragment {
	public static final String EXTRA_EDIT =
			"com.daviancorp.android.ui.general.wishlist_data_edit";
	
	private static final String ARG_WISHLIST_DATA_ID = "WISHLIST_DATA_ID";
	private static final String ARG_WISHLIST_DATA_NAME = "WISHLIST_DATA_NAME";

	public static WishlistDataEditDialogFragment newInstance(long id, String name) {
		Bundle args = new Bundle();
		args.putLong(ARG_WISHLIST_DATA_ID, id);
		args.putString(ARG_WISHLIST_DATA_NAME, name);
		WishlistDataEditDialogFragment f = new WishlistDataEditDialogFragment();
		f.setArguments(args);
		return f;
	}
	
	private void sendResult(int resultCode, boolean edit) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_EDIT, edit);
		
		getTargetFragment()
			.onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View v = inflater.inflate(R.layout.dialog_wishlist_data_edit, null);
		final EditText quantityInput = (EditText) v.findViewById(R.id.edit);
		
		final String name = getArguments().getString(ARG_WISHLIST_DATA_NAME);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle("Set quantity for '" + name + "'")
			.setView(v)
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   String input = quantityInput.getText().toString();
	            	   if (input.equals("") || input.equals("0")) {
		   				   Toast.makeText(getActivity(), "Please put a quantity!", 
		   						   Toast.LENGTH_SHORT).show();
		   				   return;
	            	   }
	            	   
            		   int quantity = Integer.parseInt(input);
            		   
            		   if (quantity > 99) {
            			   quantity = 99;
            		   }
            		   
            		   DataManager.get(getActivity()).queryUpdateWishlistData(
            				   getArguments().getLong(ARG_WISHLIST_DATA_ID), quantity);
            		   
	   				   Toast.makeText(getActivity(), "Edited '" + name + "'", Toast.LENGTH_SHORT).show();
	            	   sendResult(Activity.RESULT_OK, true);
	               }
			})
			.create();
	}
}
