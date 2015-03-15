package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;

public class ArmorSetBuilderDecorationsDialogFragment extends DialogFragment {

    private ArmorSetBuilderSession session;
    private int pieceIndex;

    public static ArmorSetBuilderDecorationsDialogFragment newInstance(ArmorSetBuilderSession session, int pieceIndex) {
        ArmorSetBuilderDecorationsDialogFragment f = new ArmorSetBuilderDecorationsDialogFragment();
        f.session = session;
        f.pieceIndex = pieceIndex;
        return f;
    }

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) { // TODO: This is the dialog that will show when the user wishes to delete a decoration
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View addView = inflater.inflate(R.layout.dialog_armor_set_builder_decoration_list, null);
		
		TextView decoration1 = (TextView) addView.findViewById(R.id.decoration_1);
		TextView decoration2 = (TextView) addView.findViewById(R.id.decoration_2);
		TextView decoration3 = (TextView) addView.findViewById(R.id.decoration_3);

        decoration1.setText(session.getDecoration(pieceIndex, 0).getName());
        decoration2.setText(session.getDecoration(pieceIndex, 1).getName());
        decoration3.setText(session.getDecoration(pieceIndex, 2).getName());
		
		Dialog d = new AlertDialog.Builder(getActivity())
			.setTitle(R.string.option_wishlist_add)
			.setView(addView)
			.setNegativeButton(android.R.string.cancel, null)
			.create();

        decoration1.setOnClickListener(new DecorationInListClickListener(0, d));
        decoration2.setOnClickListener(new DecorationInListClickListener(1, d));
        decoration3.setOnClickListener(new DecorationInListClickListener(2, d));

        return d;
	}

    private class DecorationInListClickListener implements View.OnClickListener {

        private int decorationIndex;
        private Dialog dialog;

        public DecorationInListClickListener(int decorationIndex, Dialog dialog) {
            this.decorationIndex = decorationIndex;
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            if (getTargetFragment() == null)
                return;

            Intent i = new Intent();
            i.putExtra(ArmorSetBuilderActivity.EXTRA_PIECE_INDEX, pieceIndex);
            i.putExtra(ArmorSetBuilderActivity.EXTRA_DECORATION_INDEX, decorationIndex);

            getTargetFragment().onActivityResult(ArmorSetBuilderActivity.REMOVE_DECORATION_REQUEST_CODE, Activity.RESULT_OK, i);
            dialog.dismiss();
        }
    }
}