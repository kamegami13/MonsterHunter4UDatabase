package com.daviancorp.android.ui.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;

import java.io.IOException;

public class ArmorSetBuilderDecorationsContainer extends LinearLayout {

    private int pieceIndex;
    
    private ImageView[] decorationIcons;
    private Button buttonAdd;

    public ArmorSetBuilderDecorationsContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_armor_set_builder_decorations_container, this);

        decorationIcons = new ImageView[3];
        decorationIcons[0] = (ImageView) findViewById(R.id.decoration_1);
        decorationIcons[1] = (ImageView) findViewById(R.id.decoration_2);
        decorationIcons[2] = (ImageView) findViewById(R.id.decoration_3);
        buttonAdd = (Button) findViewById(R.id.add_button);

        decorationIcons[2].setImageDrawable(getResources().getDrawable(R.drawable.decoration_empty));
    }

    public void initialize(int pieceIndex) {
        this.pieceIndex = pieceIndex;
    }

    public void updateContents(ArmorSetBuilderSession s) {
        updateSockets(s, pieceIndex);
    }

    private void updateSockets(ArmorSetBuilderSession s, int pieceIndex) {
        
        for (int i = 0; i < 3; i++) {
            
            if (s.decorationIsReal(pieceIndex, i)) {

                Drawable drawable = null;

                String cellImage = "icons_items/" + s.getDecoration(pieceIndex, i).getFileLocation();
                try {
                    drawable = Drawable.createFromStream(getContext().getAssets().open(cellImage), null); // TODO: ERROR HERE
                } catch (IOException e) {
                    e.printStackTrace();
                }

                decorationIcons[i].setImageDrawable(drawable);
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