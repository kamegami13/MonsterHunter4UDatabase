package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.data.classes.Decoration;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;
import com.daviancorp.android.ui.list.DecorationListActivity;

import java.io.IOException;

/**
 * A dialog that allows the user to view, add, and remove decorations.
 */
public class ArmorSetBuilderDecorationsDialogFragment extends DialogFragment implements ArmorSetBuilderSession.OnArmorSetChangedListener {

    private ArmorSetBuilderSession session;
    private int pieceIndex;

    TextView[] decorations;
    ImageView[] decorationIcons;
    ImageView[] menuButtons;

    public static ArmorSetBuilderDecorationsDialogFragment newInstance(ArmorSetBuilderSession session, int pieceIndex) {
        ArmorSetBuilderDecorationsDialogFragment f = new ArmorSetBuilderDecorationsDialogFragment();
        f.session = session;
        f.pieceIndex = pieceIndex;
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View addView = inflater.inflate(R.layout.dialog_armor_set_builder_decoration_list, null);

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
                .setTitle(R.string.armor_set_builder_decoration_info)
                .setView(addView)
                .setNeutralButton(android.R.string.ok, null)
                .create();

        updateDialog();

        session.addOnArmorSetChangedListener(this);

        return d;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        session.detachOnArmorSetChangedListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ArmorSetBuilderActivity.BUILDER_REQUEST_CODE) {

            long decorationId = data.getLongExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, -1);
            Decoration decoration = DataManager.get(getActivity()).getDecoration(decorationId);

            session.addDecoration(pieceIndex, decoration);
            updateDialog();
        }
    }

    @Override
    public void onArmorSetChanged() {
        updateDialog();
    }

    /** Helper method that updates the contents of the dialog based on what's in the armor set builder session. */
    private void updateDialog() {

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
            }
            else if (session.decorationIsDummy(pieceIndex, i)) { // If it's a dummy we just set its icon to a white decoration
                Drawable icon = null;
                String cellImage = "icons_items/Jewel-Unknown.png";
                try {
                    icon = Drawable.createFromStream(getTargetFragment().getActivity().getApplicationContext().getAssets().open(cellImage), null);
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
                decorations[i].setOnClickListener(null);
            }
            else if (session.decorationIsDummy(pieceIndex, i)) {
                decorations[i].setText(session.findRealDecorationOfDummy(pieceIndex, i).getName());
                decorations[i].setOnClickListener(null);
            }
            else if (session.getEquipment(pieceIndex).getNumSlots() > i) {
                decorations[i].setText(R.string.armor_set_builder_empty_slot);
                decorations[i].setOnClickListener(new EmptySlotClickListener(i));
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
        popup.inflate(R.menu.menu_set_builder_decoration);

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
        if (getTargetFragment() == null) {
            return;
        }


        Intent i = new Intent(getActivity().getApplicationContext(), DecorationListActivity.class);
        i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, true);
        i.putExtra(ArmorSetBuilderActivity.EXTRA_PIECE_INDEX, pieceIndex);
        i.putExtra(ArmorSetBuilderActivity.EXTRA_DECORATION_INDEX, decorationIndex);

        startActivityForResult(i, ArmorSetBuilderActivity.BUILDER_REQUEST_CODE);

        getTargetFragment().onActivityResult(ArmorSetBuilderActivity.BUILDER_REQUEST_CODE, Activity.RESULT_OK, i);
    }

    private void onRemoveDecorationClicked(int decorationIndex) {
        session.removeDecoration(pieceIndex, decorationIndex);
    }

    private void onDecorationInfoClicked(int decorationIndex) {
        Intent i = new Intent(getActivity().getApplicationContext(), DecorationDetailActivity.class);

        i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, session.getDecoration(pieceIndex, decorationIndex).getId());

        startActivity(i);
    }

    /** Used for slots that do not yet have a decoration socketed in them. */
    private class EmptySlotClickListener implements View.OnClickListener {

        private int decorationIndex;

        public EmptySlotClickListener(int decorationIndex) {
            this.decorationIndex = decorationIndex;
        }

        @Override
        public void onClick(View v) {
            if (getTargetFragment() == null) {
                return;
            }

            Intent i = new Intent(getActivity().getApplicationContext(), DecorationListActivity.class);
            i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, true);
            i.putExtra(ArmorSetBuilderActivity.EXTRA_PIECE_INDEX, pieceIndex);
            i.putExtra(ArmorSetBuilderActivity.EXTRA_DECORATION_INDEX, decorationIndex);

            startActivityForResult(i, ArmorSetBuilderActivity.BUILDER_REQUEST_CODE);

            getTargetFragment().onActivityResult(ArmorSetBuilderActivity.BUILDER_REQUEST_CODE, Activity.RESULT_OK, i);
        }
    }
}