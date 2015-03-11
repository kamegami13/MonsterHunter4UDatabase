package com.daviancorp.android.ui.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.list.DecorationListActivity;

import java.io.IOException;

public class ArmorSetBuilderDecorationsContainer extends LinearLayout {

    private ImageView[] decorationIcons;

    public ArmorSetBuilderDecorationsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_armor_set_builder_decorations_container, this);

        decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) findViewById(R.id.decoration_1);
        decorationIcons[1] = (ImageView) findViewById(R.id.decoration_2);
        decorationIcons[2] = (ImageView) findViewById(R.id.decoration_3);
    }

    public void updateContents(ArmorSetBuilderSession s, int pieceIndex) {

        for (int i = 0; i < 3; i++) {

            if (s.decorationIsReal(pieceIndex, i)) {

                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_real));
            }
            else if (s.decorationIsDummy(pieceIndex, i)) { // The socket index in question is a dummy
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_dummy));
            }
            else if (s.getArmor(pieceIndex).getNumSlots() > i) { // The socket in question is empty
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_empty));
            }
            else { // The armor piece has no more sockets
                decorationIcons[i].setImageDrawable(getResources().getDrawable(R.drawable.decoration_none));
            }
        }
    }
}