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

public class WishlistAddDialogFragment extends DialogFragment {
	public static final String EXTRA_ADD =
			"com.daviancorp.android.ui.general.wishlist_add";
	
	private void sendResult(int resultCode, boolean add) {
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_ADD, add);
		
		getTargetFragment()
			.onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View addView = inflater.inflate(R.layout.dialog_wishlist_add, null);
		final EditText nameInput = (EditText) addView.findViewById(R.id.name);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(R.string.option_wishlist_add)
			.setView(addView)
			.setNegativeButton(android.R.string.cancel, null)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   String name = nameInput.getText().toString();
	            	   DataManager.get(getActivity()).queryAddWishlist(name);

	   				   Toast.makeText(getActivity(), "Added '" + name + "'", Toast.LENGTH_SHORT).show();
	            	   sendResult(Activity.RESULT_OK, true);
	               }
			})
			.create();
	}
}
