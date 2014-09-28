package com.daviancorp.android.ui.dialog;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.WishlistCursor;
import com.daviancorp.android.data.object.Wishlist;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class WishlistDataAddDialogFragment extends DialogFragment {
	public static final String EXTRA_ADD =
			"com.daviancorp.android.ui.general.wishlist_data_add";

	private static final int REQUEST_PATH = 1;
	
	private static final String ARG_WISHLIST_DATA_ID = "WISHLIST_DATA_ID";
	private static final String ARG_WISHLIST_DATA_WEAPON_NAME = "WISHLIST_DATA_WEAPON_NAME";
	private static final String DIALOG_WISHLIST_COMPONENT_PATH = "wishlist_component_path";
	
	private long wishlistId = -1;
	private String wishlistName = "";
	
	private long item_id;
	private int quantity;

	public static WishlistDataAddDialogFragment newInstance(long id, String name) {
		Bundle args = new Bundle();
		args.putLong(ARG_WISHLIST_DATA_ID, id);
		args.putString(ARG_WISHLIST_DATA_WEAPON_NAME, name);
		WishlistDataAddDialogFragment f = new WishlistDataAddDialogFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {			
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View v = inflater.inflate(R.layout.dialog_wishlist_data_add, null);
		final EditText quantityInput = (EditText) v.findViewById(R.id.add);
		quantityInput.setText("1");
		
		final WishlistCursor cursor = DataManager.get(getActivity()).queryWishlists();
		
		return new AlertDialog.Builder(getActivity())
			.setTitle("Add to which wishlist?")
			.setView(v)
			.setSingleChoiceItems(cursor, -1, "name", new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   cursor.moveToPosition(id);
	            	   Wishlist wishlist = cursor.getWishlist();
	            	   wishlistId = wishlist.getId();
	            	   wishlistName = wishlist.getName();
	               }
			})
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok,  new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	            	   if (wishlistId == -1) {
	            		   Toast.makeText(getActivity(), "Please select a wishlist!", 
		   						   Toast.LENGTH_SHORT).show();
		   				   return;
	            	   }
	            	   
	            	   String input = quantityInput.getText().toString();
	            	   
	            	   if (input.equals("") || input.equals("0")) {
		   				   Toast.makeText(getActivity(), "Please put a quantity!", 
		   						   Toast.LENGTH_SHORT).show();
		   				   return;
	            	   }
	            	   
	            	   quantity = Integer.parseInt(input);
	            	   
	         		   if (quantity > 99) {
	         			   quantity = 99;
	         		   }
		            		   
	            	   Bundle args = getArguments();
	            	   item_id = args.getLong(ARG_WISHLIST_DATA_ID);
	            	   
	            	   ArrayList<String> paths = DataManager.get(getActivity()).queryComponentCreateImprove(item_id);
	            	   
	            	   if (paths.size() > 1) {
	            		   String weaponName = args.getString(ARG_WISHLIST_DATA_WEAPON_NAME);

	            			FragmentManager fm = getActivity().getSupportFragmentManager();
		       				WishlistComponentPathDialogFragment dialogPath = 
		       						WishlistComponentPathDialogFragment.newInstance(wishlistName, weaponName, paths);
		       				dialogPath.setTargetFragment(WishlistDataAddDialogFragment.this, REQUEST_PATH);
		       				dialogPath.show(fm, DIALOG_WISHLIST_COMPONENT_PATH);
	            	   }
	            	   else {
	            		   DataManager.get(getActivity()).queryAddWishlistData(
	            				   wishlistId, item_id, quantity, paths.get(0));
	            			Toast.makeText(getActivity(), "Added to '" + wishlistName + "' wishlist", 
	         					   Toast.LENGTH_SHORT).show();
	            	   }
	   			 	}
			})
			.create();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_PATH) {
			String path = data.getStringExtra(WishlistComponentPathDialogFragment.EXTRA_PATH);
			DataManager.get(getActivity()).queryAddWishlistData(wishlistId, item_id, quantity, path);
		}
	}
}
