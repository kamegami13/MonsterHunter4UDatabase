package com.daviancorp.android.ui.compound;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.data.classes.ASBTalisman;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ASBFragment;
import com.daviancorp.android.ui.detail.ArmorDetailActivity;
import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.dialog.ASBDecorationsDialogFragment;
import com.daviancorp.android.ui.dialog.ASBTalismanDialogFragment;
import com.daviancorp.android.ui.list.ArmorListActivity;

import java.io.IOException;
import java.io.InputStream;

public class ASBPieceContainer extends LinearLayout {

    private ImageView icon;
    private TextView text;

    private ImageView[] decorationIcons;

    private ImageView popupMenuButton;

    private ASBSession session;
    private int pieceIndex;
    private Fragment parentFragment;

    /**
     * Default constructor.
     * <p/>
     * It is required to call {@code initialize} after instantiating this class.
     */
    public ASBPieceContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_armor_set_builder_piece_container, this);

        icon = (ImageView) findViewById(R.id.armor_builder_item_icon);
        text = (TextView) findViewById(R.id.armor_builder_item_name);

        decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) findViewById(R.id.decoration_1);
        decorationIcons[1] = (ImageView) findViewById(R.id.decoration_2);
        decorationIcons[2] = (ImageView) findViewById(R.id.decoration_3);

        popupMenuButton = (ImageView) findViewById(R.id.popup_menu_button);
        popupMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopupMenu().show();
            }
        });
    }

    /**
     * Provides necessary external initialization logic.
     * <p/>
     * Should always be called after the container's constructor.
     */
    public void initialize(ASBSession session, int pieceIndex, Fragment parentFragment) {
        this.session = session;
        this.pieceIndex = pieceIndex;
        this.parentFragment = parentFragment;

        updateContents();
    }

    /** Refreshes the contents of the piece container based on the contents of the {@code ASBSession}. */
    public void updateContents() {
        updateArmorPiece();
        updateDecorations();
    }

    private void updateArmorPiece() {
        if (session.isEquipmentSelected(pieceIndex)) {
            text.setText(session.getEquipment(pieceIndex).getName());
            icon.setImageBitmap(fetchIcon(session.getEquipment(pieceIndex).getRarity()));
        }
        else {
            onArmorRemoved();
        }
    }

    private void updateDecorations() {
        for (int i = 0; i < 3; i++) {
            if (session.decorationIsReal(pieceIndex, i)) {
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_real));
            }
            else if (session.decorationIsDummy(pieceIndex, i)) { // The socket index in question is a ummy
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_dummy));
            }
            else if (session.getEquipment(pieceIndex).getNumSlots() > i) { // The socket in question is empty
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_empty));
            }
            else { // The armor piece has no more sockets
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_none));
            }
        }
    }

    /** Resets the container to its default state. */
    private void onArmorRemoved() {
        text.setText("");
        icon.setImageBitmap(fetchIcon(1));
    }

    /** Helper method that retrieves a rarity-appropriate equipment icon. */
    private Bitmap fetchIcon(int rarity) {

        String slot = "";
        switch (pieceIndex) {
            case ASBSession.HEAD:
                slot = "head";
                break;
            case ASBSession.BODY:
                slot = "body";
                break;
            case ASBSession.ARMS:
                slot = "arms";
                break;
            case ASBSession.WAIST:
                slot = "waist";
                break;
            case ASBSession.LEGS:
                slot = "legs";
                break;
            case ASBSession.TALISMAN:
                String imageRes;
                try {
                    imageRes = "icons_items/" + getResources().getStringArray(R.array.talisman_names)[session.getTalisman().getTypeIndex()].split(",")[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    Log.e("ASB", "Image not found for " + getResources().getStringArray(R.array.talisman_names)[session.getTalisman().getTypeIndex()]);
                    imageRes = "icons_items/Talisman-White.png";
                }

                Log.d("ASB", "Attempting to open " + imageRes);
                AssetManager manager = getContext().getAssets();
                InputStream stream;

                try {
                    stream = manager.open(imageRes);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);

                    stream.close();

                    return bitmap;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
        }

        String imageRes = "icons_armor/icons_" + slot + "/" + slot + String.valueOf(rarity) + ".png";

        Log.d("SetBuilder", "Attempting to open " + imageRes);
        AssetManager manager = getContext().getAssets();
        InputStream stream;

        try {
            stream = manager.open(imageRes);
            Bitmap bitmap = BitmapFactory.decodeStream(stream);

            stream.close();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The order of the menu items in this menu can be changed by modifying the order in which {@code popup.getMenu().add} is called
     * @return A {@code PopupMenu} that allows the user to modify the armor piece.
     */
    private PopupMenu createPopupMenu() {

        PopupMenu popup = new PopupMenu(getContext(), popupMenuButton); // Because we're not in the fragment, we have to use a theme wrapper

        boolean pieceSelected = session.isEquipmentSelected(pieceIndex);
        boolean hasSlotsAvailable = session.getAvailableSlots(pieceIndex) > 0;
        boolean hasDecorations = session.hasDecorations(pieceIndex);

        if (pieceIndex != ASBSession.TALISMAN) {

            popup.inflate(R.menu.menu_asb_equipment);

            if (!pieceSelected) {
                popup.getMenu().findItem(R.id.armor_set_builder_add_piece).setVisible(true);
            }
            else {
                popup.getMenu().findItem(R.id.armor_set_builder_remove_piece).setVisible(true);
                popup.getMenu().findItem(R.id.armor_set_builder_piece_info).setVisible(true);
            }
        }
        else {
            popup.inflate(R.menu.menu_asb_talisman);

            if (!pieceSelected) {
                popup.getMenu().findItem(R.id.armor_set_builder_talisman_create).setVisible(true);
            }
            else {
                popup.getMenu().findItem(R.id.armor_set_builder_talisman_edit).setVisible(true);
                popup.getMenu().findItem(R.id.armor_set_builder_talisman_remove).setVisible(true);
            }
        }

        if (hasDecorations || hasSlotsAvailable) {
            popup.getMenu().findItem(R.id.armor_set_builder_decorations).setVisible(true);
        }

        popup.setOnMenuItemClickListener(new PiecePopupMenuClickListener());

        return popup;
    }

    /** Listens for when the user clicks on an element in the {@code PopupMenu}. */
    private class PiecePopupMenuClickListener implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.armor_set_builder_add_piece:
                    onMenuAddPieceSelected();
                    break;
                case R.id.armor_set_builder_remove_piece:
                    onMenuRemovePieceSelected();
                    break;
                case R.id.armor_set_builder_decorations:
                    onMenuDecorationsSelected();
                    break;
                case R.id.armor_set_builder_piece_info:
                    onMenuGetPieceInfoSelected();
                    break;
                case R.id.armor_set_builder_talisman_create:
                    onMenuCreateTalismanSelected();
                    break;
                case R.id.armor_set_builder_talisman_edit:
                    onMenuEditTalismanSelected();
                    break;
                case R.id.armor_set_builder_talisman_remove:
                    onMenuRemoveTalismanSelected();
                    break;
                default:
                    return false;
            }
            return true;
        }

        /** Called when the user chooses to add an armor piece. */
        private void onMenuAddPieceSelected() {
            Intent i = new Intent(getContext(), ArmorListActivity.class);
            i.putExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, true);
            i.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
            i.putExtra(ASBActivity.EXTRA_SET_RANK, parentFragment.getArguments().getInt(ASBFragment.ARG_SET_RANK));
            i.putExtra(ASBActivity.EXTRA_SET_HUNTER_TYPE, parentFragment.getArguments().getInt(ASBFragment.ARG_SET_HUNTER_TYPE));

            ((Activity) getContext()).startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_PIECE);
        }

        /** Called when the user chooses to remove an armor piece. */
        private void onMenuRemovePieceSelected() {
            session.removeEquipment(pieceIndex);
            DataManager.get(getContext()).queryRemoveASBSetArmor(session.getId(), pieceIndex);
            updateArmorPiece();
        }

        /** Called when the user chooses to edit their decorations. */
        private void onMenuDecorationsSelected() {
            ASBDecorationsDialogFragment d = ASBDecorationsDialogFragment.newInstance(session, pieceIndex);
            d.show(parentFragment.getActivity().getSupportFragmentManager(), "DECORATIONS");
        }

        /** Called when the user chooses to retrieve info about their armor piece. */
        private void onMenuGetPieceInfoSelected() {
            Intent i = new Intent(getContext(), ArmorDetailActivity.class);
            i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, session.getEquipment(pieceIndex).getId());
            getContext().startActivity(i);
        }

        private void onMenuCreateTalismanSelected() {
            ASBTalismanDialogFragment d = ASBTalismanDialogFragment.newInstance();
            d.setTargetFragment(parentFragment, ASBActivity.REQUEST_CODE_CREATE_TALISMAN);
            d.show(parentFragment.getActivity().getSupportFragmentManager(), "TALISMAN");
        }

        private void onMenuEditTalismanSelected() {
            ASBTalisman t = session.getTalisman();
            ASBTalismanDialogFragment d = ASBTalismanDialogFragment.newInstance(t.getTypeIndex(),
                    t.getNumSlots(),
                    t.getSkill1().getId(),
                    t.getSkill1Points(), t.hasTwoSkills() ? t.getSkill2().getId() : -1, // If the talisman only has one skill, we want to pass -1 as the id for the second skill
                    t.getSkill2Points());
            d.setTargetFragment(parentFragment, ASBActivity.REQUEST_CODE_CREATE_TALISMAN);
            d.show(parentFragment.getActivity().getSupportFragmentManager(), "TALISMAN");
        }

        private void onMenuRemoveTalismanSelected() {
            session.removeEquipment(ASBSession.TALISMAN);
            DataManager.get(getContext()).queryRemoveASBSetTalisman(session.getId());
            updateArmorPiece();
            updateDecorations();
        }
    }

}
