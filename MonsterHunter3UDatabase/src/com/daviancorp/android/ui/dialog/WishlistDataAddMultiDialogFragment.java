package com.daviancorp.android.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.data.database.WishlistCursor;
import com.daviancorp.android.data.object.Wishlist;
import com.daviancorp.android.monsterhunter3udatabase.R;

public class WishlistDataAddMultiDialogFragment extends DialogFragment {
	public static final String EXTRA_ADD_MULTI =
			"com.daviancorp.android.ui.general.wishlist_add_multi";
	
	private static final String ARG_WISHLIST_DATA_IDS = "WISHLIST_DATA_IDS";
	private long wishlistId = -1;
	private String wishlistName = "";

	public static WishlistDataAddMultiDialogFragment newInstance(long[] ids) {
		Bundle args = new Bundle();
		args.putLongArray(ARG_WISHLIST_DATA_IDS, ids);
		WishlistDataAddMultiDialogFragment f = new WishlistDataAddMultiDialogFragment();
		f.setArguments(args);
		return f;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final WishlistCursor cursor = DataManager.get(getActivity()).queryWishlists();
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(R.string.option_wishlist_add)
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

	            	   Bundle args = getArguments();
	            	   long[] ids = args.getLongArray(ARG_WISHLIST_DATA_IDS);
	            	   
	            	   for (int i = 0; i < ids.length; i++) {
	            		   DataManager.get(getActivity()).queryAddWishlistData(wishlistId, ids[i], 1, null);
	            	   }
	            	   
	   				   Toast.makeText(getActivity(), 
	   						   "Added to '" + wishlistName + "' wishlist", 
	   						   Toast.LENGTH_SHORT).show();
	   			 	}
			})
			.create();
	}
}
