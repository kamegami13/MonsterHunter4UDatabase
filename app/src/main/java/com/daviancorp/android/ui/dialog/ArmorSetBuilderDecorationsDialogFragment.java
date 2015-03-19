package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;

import java.io.IOException;

/**
 * The dialog that allows a user to remove decorations.
 */
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

        TextView[] decorations = new TextView[3];
        decorations[0] = (TextView) addView.findViewById(R.id.decoration_1);
        decorations[1] = (TextView) addView.findViewById(R.id.decoration_2);
        decorations[2] = (TextView) addView.findViewById(R.id.decoration_3);

        ImageView[] decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) addView.findViewById(R.id.decoration_1_icon);
        decorationIcons[1] = (ImageView) addView.findViewById(R.id.decoration_2_icon);
        decorationIcons[2] = (ImageView) addView.findViewById(R.id.decoration_3_icon);

        Dialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.armor_set_builder_remove_decoration)
                .setView(addView)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        for (int i = 0; i < decorations.length; i++) {

            if (session.decorationIsReal(pieceIndex, i)) { // If it's real we set its icon to its actual icon
                Drawable icon = null;
                String cellImage = "icons_items/" + session.getDecoration(pieceIndex, i).getFileLocation();
                try {
                    icon = Drawable.createFromStream(getTargetFragment().getActivity().getApplicationContext().getAssets().open(cellImage), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                decorationIcons[i].setImageDrawable(icon);
            } else if (session.decorationIsDummy(pieceIndex, i)) { // If it's a dummy we just set its icon to a white decoration
                Drawable icon = null;
                String cellImage = "icons_items/Jewel-Unknown.png";
                try {
                    icon = Drawable.createFromStream(getTargetFragment().getActivity().getApplicationContext().getAssets().open(cellImage), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                decorationIcons[i].setImageDrawable(icon);
            } // Else it's a blank decoration


            if (session.decorationIsReal(pieceIndex, i) || session.decorationIsDummy(pieceIndex, i)) {
                decorations[i].setText(session.getDecoration(pieceIndex, i).getName());
                decorations[i].setOnClickListener(new DecorationInListClickListener(i, d));


            }

            if (session.decorationIsDummy(pieceIndex, i) || !session.decorationIsReal(pieceIndex, i)) {
                decorations[i].setTextColor(getResources().getColor(R.color.text_color_secondary));
            }
        }

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