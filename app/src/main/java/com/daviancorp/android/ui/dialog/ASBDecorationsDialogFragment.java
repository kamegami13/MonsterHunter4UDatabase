package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;
import com.daviancorp.android.ui.list.DecorationListActivity;

import java.io.IOException;

/**
 * A dialog that allows the user to view, add, and remove decorations.
 */
public class ASBDecorationsDialogFragment extends DialogFragment implements ASBSession.SessionChangeListener {

    private ASBSession session;
    private int pieceIndex;

    TextView[] decorations;
    ImageView[] decorationIcons;
    ImageView[] menuButtons;

    public static ASBDecorationsDialogFragment newInstance(ASBSession session, int pieceIndex) {
        ASBDecorationsDialogFragment f = new ASBDecorationsDialogFragment();
        f.session = session;
        f.pieceIndex = pieceIndex;
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View addView = inflater.inflate(R.layout.dialog_asb_decoration_list, null);

        decorations = new TextView[3];
        decorations[0] = (TextView) addView.findViewById(R.id.decoration_1);
        decorations[1] = (TextView) addView.findViewById(R.id.decoration_2);
        decorations[2] = (TextView) addView.findViewById(R.id.decoration_3);

        decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) addView.findViewById(R.id.decoration_1_icon);
        decorationIcons[1] = (ImageView) addView.findViewById(R.id.decoration_2_icon);
        decorationIcons[2] = (ImageView) addView.findViewById(R.id.decoration_3_icon);

        menuButtons = new ImageView[3];
        menuButtons[0] = (ImageView) addView.findViewById(R.id.decoration_1_menu);
        menuButtons[1] = (ImageView) addView.findViewById(R.id.decoration_2_menu);
        menuButtons[2] = (ImageView) addView.findViewById(R.id.decoration_3_menu);

        for (int i = 0; i < 3; i++) {
            final int decorationIndex = i;
            menuButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createPopupMenu(decorationIndex).show();
                }
            });
        }

        Dialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.asb_action_edit_piece_decorations)
                .setView(addView)
                .setNeutralButton(android.R.string.ok, null)
                .create();

        updateDialog();

        return d;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        session.addSessionChangeListener(this);
        getTargetFragment().onActivityResult(requestCode, resultCode, data);
    }

    /** Helper method that updates the contents of the dialog based on what's in the armor set builder session. */
    private void updateDialog() {
        for (int i = 0; i < decorations.length; i++) {

            if (session.decorationIsReal(pieceIndex, i)) { // If it's real we set its icon to its actual icon
                Drawable icon = null;
                String cellImage = "icons_items/" + session.getDecoration(pieceIndex, i).getFileLocation();
                try {
                    icon = Drawable.createFromStream(getActivity().getApplicationContext().getAssets().open(cellImage), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                decorationIcons[i].setImageDrawable(icon);
            }
            else if (session.decorationIsDummy(pieceIndex, i)) { // If it's a dummy we just set its icon to a white decoration
                Drawable icon = null;
                String cellImage = "icons_items/Jewel-Unknown.png";
                try {
                    icon = Drawable.createFromStream(getActivity().getApplicationContext().getAssets().open(cellImage), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                decorationIcons[i].setImageDrawable(icon);
            }
            else {
                decorationIcons[i].setImageDrawable(null);
            }

            if (session.decorationIsReal(pieceIndex, i)) {
                decorations[i].setText(session.getDecoration(pieceIndex, i).getName());
            }
            else if (session.decorationIsDummy(pieceIndex, i)) {
                decorations[i].setText(session.findRealDecorationOfDummy(pieceIndex, i).getName());
            }
            else if (session.getEquipment(pieceIndex).getNumSlots() > i) {
                decorations[i].setText(R.string.asb_empty_slot);
            }

            if (session.decorationIsReal(pieceIndex, i)) {
                decorations[i].setTextColor(getResources().getColor(R.color.text_color));
            }
            else {
                decorations[i].setTextColor(getResources().getColor(R.color.text_color_secondary));
            }
        }
    }

    private PopupMenu createPopupMenu(int decorationIndex) {
        PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), menuButtons[decorationIndex]);
        popup.inflate(R.menu.menu_asb_decoration);

        if (session.decorationIsReal(pieceIndex, decorationIndex) && !session.decorationIsDummy(pieceIndex, decorationIndex)) {
            popup.getMenu().findItem(R.id.armor_set_builder_decoration_remove).setVisible(true);
            popup.getMenu().findItem(R.id.armor_set_builder_decoration_info).setVisible(true);
        }
        else if (!session.decorationIsDummy(pieceIndex, decorationIndex)) {
            popup.getMenu().findItem(R.id.armor_set_builder_decoration_add).setVisible(true);
        }

        popup.setOnMenuItemClickListener(new DecorationPopupMenuClickListener(decorationIndex));

        return popup;
    }

    @Override
    public void onSessionChange() {
        updateDialog();
        session.removeSessionChangeListener(this);
    }

    private class DecorationPopupMenuClickListener implements PopupMenu.OnMenuItemClickListener {

        int decorationIndex;

        private DecorationPopupMenuClickListener(int decorationIndex) {
            this.decorationIndex = decorationIndex;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.armor_set_builder_decoration_add:
                    onAddDecorationClicked(decorationIndex);
                    break;
                case R.id.armor_set_builder_decoration_remove:
                    onRemoveDecorationClicked(decorationIndex);
                    break;
                case R.id.armor_set_builder_decoration_info:
                    onDecorationInfoClicked(decorationIndex);
                    break;
                default:
                    return false;
            }

            return true;
        }
    }

    private void onAddDecorationClicked(int decorationIndex) {
        Intent i = new Intent(getActivity().getApplicationContext(), DecorationListActivity.class);
        i.putExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, true);
        i.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
        i.putExtra(ASBActivity.EXTRA_DECORATION_INDEX, decorationIndex);

        startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_DECORATION);
    }

    private void onRemoveDecorationClicked(int decorationIndex) {
        session.addSessionChangeListener(this);

        Intent data = new Intent();
        data.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
        data.putExtra(ASBActivity.EXTRA_DECORATION_INDEX, decorationIndex);

        getTargetFragment().onActivityResult(ASBActivity.REQUEST_CODE_REMOVE_DECORATION, Activity.RESULT_OK, data);
    }

    private void onDecorationInfoClicked(int decorationIndex) {
        Intent i = new Intent(getActivity().getApplicationContext(), DecorationDetailActivity.class);

        i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, session.getDecoration(pieceIndex, decorationIndex).getId());

        startActivity(i);
    }
}