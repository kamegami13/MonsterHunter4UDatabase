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

public class WishlistComponentEditDialogFragment extends DialogFragment {
	public static final String EXTRA_EDIT =
			"com.daviancorp.android.ui.general.wishlist_data_edit";
	
	private static final String ARG_WISHLIST_COMPONENT_ID = "WISHLIST_COMPONENT_ID";
	private static final String ARG_WISHLIST_COMPONENT_NAME = "WISHLIST_COMPONENT_NAME";

	public static WishlistComponentEditDialogFragment newInstance(long id, String name) {
		Bundle args = new Bundle();
		args.putLong(ARG_WISHLIST_COMPONENT_ID, id);
		args.putString(ARG_WISHLIST_COMPONENT_NAME, name);
		WishlistComponentEditDialogFragment f = new WishlistComponentEditDialogFragment();
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
		
		final String name = getArguments().getString(ARG_WISHLIST_COMPONENT_NAME);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle("Set your quantity for '" + name + "'")
			.setView(v)
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   String input = quantityInput.getText().toString();
	            	   if (input.equals("")) {
		   				   Toast.makeText(getActivity(), "Please put a quantity!", 
		   						   Toast.LENGTH_SHORT).show();
		   				   return;
	            	   }
	            	   
            		   int quantity = Integer.parseInt(input);
            		               		   
            		   DataManager.get(getActivity()).queryUpdateWishlistComponentNotes(
            				   getArguments().getLong(ARG_WISHLIST_COMPONENT_ID), quantity);
            		   
	   				   Toast.makeText(getActivity(), "Edited '" + name + "'", Toast.LENGTH_SHORT).show();
	            	   sendResult(Activity.RESULT_OK, true);
	               }
			})
			.create();
	}
}
