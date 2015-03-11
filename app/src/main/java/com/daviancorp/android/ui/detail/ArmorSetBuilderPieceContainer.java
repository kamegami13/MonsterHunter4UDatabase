package com.daviancorp.android.ui.detail;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;

import java.io.IOException;
import java.io.InputStream;

public class ArmorSetBuilderPieceContainer extends LinearLayout {

    private ImageView icon;
    private TextView text;

    private ImageView[] decorationIcons;

    public ArmorSetBuilderPieceContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_armor_set_builder_piece_container, this);

        icon = (ImageView) findViewById(R.id.armor_builder_item_icon);
        text = (TextView) findViewById(R.id.armor_builder_item_name);

        decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) findViewById(R.id.decoration_1);
        decorationIcons[1] = (ImageView) findViewById(R.id.decoration_2);
        decorationIcons[2] = (ImageView) findViewById(R.id.decoration_3);
    }

    public void initialize(int pieceIndex, OnClickListener clickListener) {
        icon.setImageBitmap(fetchIcon(pieceIndex, 1));
        setOnClickListener(clickListener);
    }

    public void updateContents(ArmorSetBuilderSession s, int pieceIndex) {
        updateArmorPiece(s, pieceIndex);
        updateDecorations(s, pieceIndex);
    }

    private void updateArmorPiece(ArmorSetBuilderSession s, int pieceIndex) {
        text.setText(s.getHead().getName());
        icon.setImageBitmap(fetchIcon(pieceIndex, s.getArmor(pieceIndex).getRarity()));
    }

    private void updateDecorations(ArmorSetBuilderSession s, int pieceIndex) {
        for (int i = 0; i < 3; i++) {
            if (s.decorationIsReal(pieceIndex, i)) {
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_real));
            } else if (s.decorationIsDummy(pieceIndex, i)) { // The socket index in question is a dummy
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_dummy));
            } else if (s.getArmor(pieceIndex).getNumSlots() > i) { // The socket in question is empty
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_empty));
            } else { // The armor piece has no more sockets
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_none));
            }
        }
    }

    /**
     * Helper method that retrieves a rarity-appropriate equipment icon.
     */
    private Bitmap fetchIcon(int pieceIndex, int rarity) {
        String slot = "";
        switch (pieceIndex) {
            case ArmorSetBuilderSession.HEAD:
                slot = "head";
                break;
            case ArmorSetBuilderSession.BODY:
                slot = "body";
                break;
            case ArmorSetBuilderSession.ARMS:
                slot = "arms";
                break;
            case ArmorSetBuilderSession.WAIST:
                slot = "waist";
                break;
            case ArmorSetBuilderSession.LEGS:
                slot = "legs";
                break;
        }

        String imageRes = "icons_armor/icons_" + slot + "/" + slot + String.valueOf(rarity) + ".png";
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
}
