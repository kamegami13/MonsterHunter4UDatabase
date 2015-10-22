package com.daviancorp.android.ui.compound;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.detail.ASBFragment;
import com.daviancorp.android.ui.detail.ArmorDetailActivity;
import com.daviancorp.android.ui.detail.DecorationDetailActivity;
import com.daviancorp.android.ui.dialog.ASBTalismanDialogFragment;
import com.daviancorp.android.ui.list.ArmorListActivity;
import com.daviancorp.android.ui.list.DecorationListActivity;

import java.io.IOException;
import java.io.InputStream;

public class ASBPieceContainer extends LinearLayout {
    private ASBFragment parentFragment;

    private ImageView icon;
    private TextView text;
    private ImageView[] decorationStates;
    private ImageView equipmentButton;

    private ImageView dropDownArrow;
    private DecorationView decorationView;

    private ASBSession session;
    private int pieceIndex;

    /**
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

        text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.isEquipmentSelected(pieceIndex)) {
                    requestPieceInfo();
                }
            }
        });

        equipmentButton = (ImageView) findViewById(R.id.add_equipment_button);
        equipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!session.isEquipmentSelected(pieceIndex)) {
                    onAddEquipment();
                } else {
                    onRemoveEquipment();
                }
            }
        });

        decorationView = new DecorationView();


        dropDownArrow = (ImageView) findViewById(R.id.drop_down_arrow);
        dropDownArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (decorationView.container.getVisibility() == GONE) {
                    showDecorations();
                } else {
                    hideDecorations();
                }
            }
        });
    }

    /**
     * Provides necessary external initialization logic.
     * Should always be called after the container's constructor.
     */
    public void initialize(ASBSession session, int pieceIndex, ASBFragment parentFragment) {
        this.session = session;
        this.pieceIndex = pieceIndex;
        this.parentFragment = parentFragment;
    }

    /**
     * Refreshes the contents of the piece container based on the {@code ASBSession}.
     */
    public void updateContents() {
        updateArmorPiece();
        updateDecorationsPreview();
        decorationView.update();
    }

    private void updateArmorPiece() {
        if (session.isEquipmentSelected(pieceIndex)) {
            text.setText(session.getEquipment(pieceIndex).getName());
            icon.setImageBitmap(fetchIcon(session.getEquipment(pieceIndex).getRarity()));
            equipmentButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));
        } else {
            text.setText(null);
            icon.setImageBitmap(fetchIcon(1));
            equipmentButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        }
    }

    private void updateDecorationsPreview() {
        for (int i = 0; i < 3; i++) {
            if (!session.isEquipmentSelected(pieceIndex)) {
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_none));
            } else if (session.decorationIsReal(pieceIndex, i)) {
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_real));
            } else if (session.decorationIsDummy(pieceIndex, i)) {
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_real));
            } else if (session.getEquipment(pieceIndex).getNumSlots() > i) {
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_empty));
            } else {
                decorationStates[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_none));
            }
        }
    }

    public void showDecorations() {
        parentFragment.onDecorationsMenuOpened();
        decorationView.container.setVisibility(VISIBLE);
        equipmentButton.setVisibility(INVISIBLE);
        dropDownArrow.setImageDrawable(parentFragment.getActivity().getResources().getDrawable(R.drawable.ic_drop_up_arrow));
    }

    public void hideDecorations() {
        decorationView.container.setVisibility(GONE);
        equipmentButton.setVisibility(VISIBLE);
        dropDownArrow.setImageDrawable(parentFragment.getActivity().getResources().getDrawable(R.drawable.ic_drop_down_arrow));
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
                    Log.e("ASB",
                            "Image not found for " + getResources().getStringArray(R.array.talisman_names)[session.getTalisman().getTypeIndex()]);
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

    private void onAddEquipment() {
        if (pieceIndex == ASBSession.TALISMAN) {
            ASBTalismanDialogFragment d = ASBTalismanDialogFragment.newInstance();
            d.setTargetFragment(parentFragment, ASBActivity.REQUEST_CODE_CREATE_TALISMAN);
            d.show(parentFragment.getActivity().getSupportFragmentManager(), "TALISMAN");
        } else {
            Intent i = new Intent(getContext(), ArmorListActivity.class);
            i.putExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, true);
            i.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
            i.putExtra(ASBActivity.EXTRA_SET_RANK, parentFragment.getArguments().getInt(ASBFragment.ARG_SET_RANK));
            i.putExtra(ASBActivity.EXTRA_SET_HUNTER_TYPE,
                    parentFragment.getArguments().getInt(ASBFragment.ARG_SET_HUNTER_TYPE));

            parentFragment.startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_PIECE);
        }
    }

    private void onRemoveEquipment() {
        Intent data = new Intent();
        data.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
        parentFragment.onActivityResult(ASBActivity.REQUEST_CODE_REMOVE_PIECE, Activity.RESULT_OK, data);
    }

    private void requestPieceInfo() {
        if (pieceIndex == ASBSession.TALISMAN) {
            onAddEquipment();
        } else {
            Intent i = new Intent(getContext(), ArmorDetailActivity.class);
            i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, session.getEquipment(pieceIndex).getId());
            getContext().startActivity(i);
        }
    }

    private class DecorationView {
        TextView[] decorationNames;
        ImageView[] decorationIcons;
        ImageView[] decorationMenuButtons;
        ViewGroup container;

        public DecorationView() {
            container = (ViewGroup) findViewById(R.id.decorations);

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
                final int decorationIndex = i;
                decorationNames[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (session.decorationIsReal(pieceIndex, decorationIndex)) {
                            requestDecorationInfo(decorationIndex);
                        }
                    }
                });

                decorationMenuButtons[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (session.decorationIsReal(pieceIndex, decorationIndex)) {
                            requestRemoveDecoration(decorationIndex);
                        } else {
                            requestAddDecoration();
                        }
                    }
                });
            }


        }

        private void update() {
            if (session.getEquipment(pieceIndex) != null) {
                boolean addButtonExists = false;
                for (int i = 0; i < decorationNames.length; i++) {
                    decorationIcons[i].setImageDrawable(fetchDecorationIcon(pieceIndex, i));

                    if (session.decorationIsReal(pieceIndex, i)) {
                        decorationNames[i].setText(session.getDecoration(pieceIndex, i).getName());
                        decorationNames[i].setTextColor(getResources().getColor(R.color.text_color));

                        decorationMenuButtons[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));
                    } else {
                        if (session.decorationIsDummy(pieceIndex, i)) {
                            decorationNames[i].setText(session.findRealDecorationOfDummy(pieceIndex, i).getName());

                            decorationMenuButtons[i].setImageDrawable(null);
                        } else if (session.getEquipment(pieceIndex).getNumSlots() > i) {
                            decorationNames[i].setText(R.string.asb_empty_slot);

                            if (!addButtonExists) {
                                decorationMenuButtons[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                                addButtonExists = true;
                            } else {
                                decorationMenuButtons[i].setImageDrawable(null);
                            }
                        } else {
                            decorationNames[i].setText(R.string.asb_no_slot);

                            decorationMenuButtons[i].setImageDrawable(null);
                        }

                        decorationNames[i].setTextColor(getResources().getColor(R.color.text_color_secondary));
                    }
                }
            } else {
                for (int i = 0; i < decorationNames.length; i++) {
                    decorationNames[i].setText(null);
                    decorationIcons[i].setImageDrawable(null);
                }
            }
        }

        private Drawable fetchDecorationIcon(int pieceIndex, int decorationIndex) {
            String imagePath = "icons_items/";
            if (session.decorationIsReal(pieceIndex, decorationIndex)) {
                imagePath += session.getDecoration(pieceIndex, decorationIndex).getFileLocation();
            } else if (session.decorationIsDummy(pieceIndex, decorationIndex)) {
                imagePath += "Jewel-Unknown.png";
            } else {
                return null;
            }

            try {
                return Drawable.createFromStream(getContext().getAssets().open(imagePath), null);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private void requestAddDecoration() {
            Intent i = new Intent(parentFragment.getActivity(), DecorationListActivity.class);
            i.putExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, true);
            i.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);

            parentFragment.startActivityForResult(i, ASBActivity.REQUEST_CODE_ADD_DECORATION);
        }

        private void requestRemoveDecoration(int decorationIndex) {
            Intent data = new Intent();
            data.putExtra(ASBActivity.EXTRA_PIECE_INDEX, pieceIndex);
            data.putExtra(ASBActivity.EXTRA_DECORATION_INDEX, decorationIndex);

            parentFragment.onActivityResult(ASBActivity.REQUEST_CODE_REMOVE_DECORATION, Activity.RESULT_OK, data);
        }

        private void requestDecorationInfo(int decorationIndex) {
            Intent i = new Intent(parentFragment.getActivity(), DecorationDetailActivity.class);

            i.putExtra(DecorationDetailActivity.EXTRA_DECORATION_ID,
                    session.getDecoration(pieceIndex, decorationIndex).getId());

            parentFragment.startActivity(i);
        }
    }
}
