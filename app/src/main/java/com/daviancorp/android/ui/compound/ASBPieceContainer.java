package com.daviancorp.android.ui.compound;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ASBFragment;
import com.daviancorp.android.ui.detail.ArmorDetailActivity;
import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;
import com.daviancorp.android.ui.dialog.ASBTalismanDialogFragment;
import com.daviancorp.android.ui.list.ArmorListActivity;
import com.daviancorp.android.ui.list.DecorationListActivity;

import java.io.IOException;
import java.io.InputStream;

public class ASBPieceContainer extends LinearLayout {

    private ImageView icon;
    private TextView text;

    private ImageView[] decorationStates;

    TextView[] decorationNames;
    ImageView[] decorationIcons;
    ImageView[] decorationMenuButtons;

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

        decorationStates = new ImageView[3];
        decorationStates[0] = (ImageView) findViewById(R.id.decoration_1_state);
        decorationStates[1] = (ImageView) findViewById(R.id.decoration_2_state);
        decorationStates[2] = (ImageView) findViewById(R.id.decoration_3_state);

        popupMenuButton = (ImageView) findViewById(R.id.popup_menu_button);
        popupMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createArmorPopupMenu().show();
            }
        });

        final View decorationMenu = findViewById(R.id.decorations);

        final ImageView dropDownArrow = (ImageView) findViewById(R.id.drop_down_arrow);
        dropDownArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (decorationMenu.getVisibility() == GONE) {
                    decorationMenu.setVisibility(VISIBLE);
                    dropDownArrow.setImageDrawable(parentFragment.getActivity().getResources().getDrawable(R.drawable.ic_drop_up_arrow));
                } else {
                    decorationMenu.setVisibility(GONE);
                    dropDownArrow.setImageDrawable(parentFragment.getActivity().getResources().getDrawable(R.drawable.ic_drop_down_arrow));
                }
            }
        });

        decorationNames = new TextView[3];
        decorationNames[0] = (TextView) findViewById(R.id.decoration_1_name);
        decorationNames[1] = (TextView) findViewById(R.id.decoration_2_name);
        decorationNames[2] = (TextView) findViewById(R.id.decoration_3_name);

        decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) findViewById(R.id.decoration_1_icon);
        decorationIcons[1] = (ImageView) findViewById(R.id.decoration_2_icon);
        decorationIcons[2] = (ImageView) findViewById(R.id.decoration_3_icon);

        decorationMenuButtons = new ImageView[3];
        decorationMenuButtons[0] = (ImageView) findViewById(R.id.decoration_1_menu);
        decorationMenuButtons[1] = (ImageView) findViewById(R.id.decoration_2_menu);
        decorationMenuButtons[2] = (ImageView) findViewById(R.id.decoration_3_menu);

        for (int i = 0; i < 3; i++) {
            final int index = i;
            decorationMenuButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDecorationPopupMenu(index).show();
                }
            });
        }

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
        updateDecorationsView();
    }

    /**
     * Refreshes the contents of the piece container based on the contents of the {@code ASBSession}.
     */
    public void updateContents() {
        updateArmorPiece();
        updateDecorationsPreview();
        updateDecorationsView();
    }

    private void updateArmorPiece() {
        if (session.isEquipmentSelected(pieceIndex)) {
            text.setText(session.getEquipment(pieceIndex).getName());
            icon.setImageBitmap(fetchIcon(session.getEquipment(pieceIndex).getRarity()));
        } else {
            onArmorRemoved();
        }
    }

    private void updateDecorationsPreview() {
        for (int i = 0; i < 3; i++) {
            if (!session.isEquipmentSelected(pieceIndex)) {
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_none));
            } else if (session.decorationIsReal(pieceIndex, i)) {
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_real));
            } else if (session.decorationIsDummy(pieceIndex, i)) { // The socket index in question is a ummy
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_dummy));
            } else if (session.getEquipment(pieceIndex).getNumSlots() > i) { // The socket in question is empty
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_empty));
            } else { // The armor piece has no more sockets
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_none));
            }
        }
    }

    /**
     * Resets the container to its default state.
     */
    private void onArmorRemoved() {
        text.setText("");
        icon.setImageBitmap(fetchIcon(1));
    }

    /**
     * Helper method that retrieves a rarity-appropriate equipment icon.
     */
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
                    if (session.isEquipmentSelected(ASBSession.TALISMAN)) {
                        imageRes = "icons_items/" + getResources().getStringArray(R.array.talisman_names)[session.getTalisman().getTypeIndex()].split(",")[1];
                    } else {
                        imageRes = "icons_items/Talisman-White.png";
                    }
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
     * Helper method that updates the contents of the dialog based on what's in the armor set builder session.
     */
    private void updateDecorationsView() {
        if (session.getEquipment(pieceIndex) != null) {
            for (int i = 0; i < decorationNames.length; i++) {

                if (session.decorationIsReal(pieceIndex, i)) { // If it's real we set its icon to its actual icon
                    Drawable icon = null;
                    String cellImage = "icons_items/" + session.getDecoration(pieceIndex, i).getFileLocation();
                    try {
                        icon = Drawable.createFromStream(parentFragment.getActivity().getAssets().open(cellImage), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    decorationIcons[i].setImageDrawable(icon);
                } else if (session.decorationIsDummy(pieceIndex, i)) { // If it's a dummy we just set its icon to a white decoration
                    Drawable icon = null;
                    String cellImage = "icons_items/Jewel-Unknown.png";
                    try {
                        icon = Drawable.createFromStream(parentFragment.getActivity().getAssets().open(cellImage), null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    decorationIcons[i].setImageDrawable(icon);
                } else {
                    decorationIcons[i].setImageDrawable(null);
                }

                if (session.decorationIsReal(pieceIndex, i)) {
                    decorationNames[i].setText(session.getDecoration(pieceIndex, i).getName());
                } else if (session.decorationIsDummy(pieceIndex, i)) {
                    decorationNames[i].setText(session.findRealDecorationOfDummy(pieceIndex, i).getName());
                } else if (session.getEquipment(pieceIndex).getNumSlots() > i) {
                    decorationNames[i].setText(R.string.asb_empty_slot);
                }

                if (session.decorationIsReal(pieceIndex, i)) {
                    decorationNames[i].setTextColor(getResources().getColor(R.color.text_color));
                } else {
                    decorationNames[i].setTextColor(getResources().getColor(R.color.text_color_secondary));
                }
            }
        }
    }

    /**
     * The order of the menu items in this menu can be changed by modifying the order in which {@code popup.getMenu().add} is called
     *
     * @return A {@code PopupMenu} that allows the user to modify the armor piece.
     */
    private PopupMenu createArmorPopupMenu() {

        PopupMenu popup = new PopupMenu(getContext(), popupMenuButton); // Because we're not in the fragment, we have to use a theme wrapper

        boolean pieceSelected = session.isEquipmentSelected(pieceIndex);

        if (pieceIndex != ASBSession.TALISMAN) {

            popup.inflate(R.menu.menu_asb_equipment);

            if (!pieceSelected) {
                popup.getMenu().findItem(R.id.armor_set_builder_add_piece).setVisible(true);
            } else {
                popup.getMenu().findItem(R.id.armor_set_builder_remove_piece).setVisible(true);
                popup.getMenu().findItem(R.id.armor_set_builder_piece_info).setVisible(true);
            }
        } else {
            popup.inflate(R.menu.menu_asb_talisman);

            if (!pieceSelected) {
                popup.getMenu().findItem(R.id.armor_set_builder_talisman_create).setVisible(true);
            } else {
                popup.getMenu().findItem(R.id.armor_set_builder_talisman_edit).setVisible(true);
                popup.getMenu().findItem(R.id.armor_set_builder_talisman_remove).setVisible(true);
            }
        }

        popup.setOnMenuItemClickListener(new PiecePopupMenuClickListener());

        return popup;
    }

    private PopupMenu createDecorationPopupMenu(int decorationIndex) {
        PopupMenu popup = new PopupMenu(parentFragment.getActivity(), decorationMenuButtons[decorationIndex]);
        popup.inflate(R.menu.menu_asb_decoration);

        if (session.decorationIsReal(pieceIndex, decorationIndex) && !session.decorationIsDummy(pieceIndex, decorationIndex)) {
            popup.getMenu().findItem(R.id.armor_set_builder_decoration_remove).setVisible(true);
            popup.getMenu().findItem(R.id.armor_set_builder_decoration_info).setVisible(true);
        } else if (!session.decorationIsDummy(pieceIndex, decorationIndex)) {
            popup.getMenu().findItem(R.id.armor_set_builder_decoration_add).setVisible(true);
        }

        popup.setOnMenuItemClickListener(new DecorationPopupMenuClickListener(decorationIndex));

        return popup;
    }

    /**
     * Listens for when the user clicks on an element in the {@code PopupMenu}.
     */
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

        /**
         * Called when the user chooses to add an armor piece.
         */
        private void onMenuAddPieceSelected() {
            Intent i = new Intent(getContext(), ArmorListActivity.class);
            i.putExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, true);
            i.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
            i.putExtra(ASBActivity.EXTRA_SET_RANK, parentFragment.getArguments().getInt(ASBFragment.ARG_SET_RANK));
            i.putExtra(ASBActivity.EXTRA_SET_HUNTER_TYPE, parentFragment.getArguments().getInt(ASBFragment.ARG_SET_HUNTER_TYPE));

            parentFragment.startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_PIECE);
        }

        /**
         * Called when the user chooses to remove an armor piece.
         */
        private void onMenuRemovePieceSelected() {
            Log.d("ASB", "Remove clicked.");
            Intent data = new Intent();
            data.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
            parentFragment.onActivityResult(ASBActivity.REQUEST_CODE_REMOVE_PIECE, Activity.RESULT_OK, data);
        }

        /**
         * Called when the user chooses to retrieve info about their armor piece.
         */
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
                    t.getSkill1Points(), t.getSkill2() != null ? t.getSkill2().getId() : -1, // If the talisman only has one skill, we want to pass -1 as the id for the second skill
                    t.getSkill2Points());
            d.setTargetFragment(parentFragment, ASBActivity.REQUEST_CODE_CREATE_TALISMAN);
            d.show(parentFragment.getActivity().getSupportFragmentManager(), "TALISMAN");
        }

        private void onMenuRemoveTalismanSelected() {
            parentFragment.onActivityResult(ASBActivity.REQUEST_CODE_REMOVE_TALISMAN, Activity.RESULT_OK, null);
        }
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

        private void onAddDecorationClicked(int decorationIndex) {
            Intent i = new Intent(parentFragment.getActivity(), DecorationListActivity.class);
            i.putExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, true);
            i.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
            i.putExtra(ASBActivity.EXTRA_DECORATION_INDEX, decorationIndex);

            parentFragment.startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_DECORATION);
        }

        private void onRemoveDecorationClicked(int decorationIndex) {
//            session.addSessionChangeListener(this);

            Intent data = new Intent();
            data.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
            data.putExtra(ASBActivity.EXTRA_DECORATION_INDEX, decorationIndex);

            parentFragment.onActivityResult(ASBActivity.REQUEST_CODE_REMOVE_DECORATION, Activity.RESULT_OK, data);
        }

        private void onDecorationInfoClicked(int decorationIndex) {
            Intent i = new Intent(parentFragment.getActivity(), DecorationDetailActivity.class);

            i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID, session.getDecoration(pieceIndex, decorationIndex).getId());

            parentFragment.startActivity(i);
        }
    }

}
