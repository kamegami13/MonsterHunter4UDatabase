package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;

/**
 * Created by Mark on 2/26/2015.
 * Abstract adapter for weapons with element (blade, bow)
 */
public abstract class WeaponListElementAdapter extends WeaponListGeneralAdapter {

    public WeaponListElementAdapter(Context context, WeaponCursor cursor) {
        super(context, cursor);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        // Get the weapon for the current row
        Weapon weapon = mWeaponCursor.getWeapon();

        // Find all views
        TextView elementtv = (TextView) view.findViewById(R.id.element_text);
        TextView elementtv2 = (TextView) view.findViewById(R.id.element_text2);
        TextView awakentv = (TextView) view.findViewById(R.id.awaken_text);

        ImageView elementIcon = (ImageView) view.findViewById(R.id.element_image);
        ImageView element2Icon = (ImageView) view.findViewById(R.id.element_image2);

        // Set the element to view
        String element = weapon.getElementalAttack();
        String awakenedElement = weapon.getAwakenedElementalAttack();
        String dualElement = null;
        String elementText = "";
        String awakenText = "";
        Drawable dEle = null;
        Drawable dDualEle = null;

        // Check to see if we should grab awaken
        if (!"".equals(awakenedElement)) {
            element = awakenedElement;
            awakenText = "(";
        } else {
            awakenText = "";
        }

        //
        // Set element text and images
        //
        if (!"".equals(element)) {
            String[] elementData = getElementData(element);
            elementText = elementData[0];
            dEle = getDrawable(context, elementData[1]);
            elementIcon.setImageDrawable(dEle);
            elementIcon.setVisibility(view.VISIBLE);

            if (element.contains(",")) {
                String[] twoElements = elementText.split(",");
                elementText = twoElements[0];
                dualElement = twoElements[1];

                String[] dualElementData = getElementData(dualElement);
                dDualEle = getDrawable(context, dualElementData[1]);

                elementtv2.setText(dualElementData[0]);
                element2Icon.setImageDrawable(dDualEle);
                element2Icon.setVisibility(view.VISIBLE);
            } else {
                elementtv2.setText("");
                element2Icon.setImageDrawable(null);
                element2Icon.setVisibility(view.GONE);
            }

            if (!"".equals(awakenedElement)) {
                elementText = elementText + ")";
            }

            elementtv.setText(elementText);
        } else {
            elementtv.setText("");
            elementIcon.setImageDrawable(null);
            elementtv2.setText("");
            element2Icon.setImageDrawable(null);
            elementIcon.setVisibility(view.GONE);
            element2Icon.setVisibility(view.GONE);
        }

        awakentv.setText(awakenText);
    }

    private String[] getElementData(String element) {
        if (element.startsWith("FI")) {
            return new String[] {element.substring(2), "icons_monster_info/Fire.png"};
        } else if (element.startsWith("WA")) {
            return new String[] {element.substring(2), "icons_monster_info/Water.png"};
        } else if (element.startsWith("IC")) {
            return new String[] {element.substring(2), "icons_monster_info/Ice.png"};
        } else if (element.startsWith("TH")) {
            return new String[] {element.substring(2), "icons_monster_info/Thunder.png"};
        } else if (element.startsWith("DR")) {
            return new String[] {element.substring(2), "icons_monster_info/Dragon.png"};
        } else if (element.startsWith("PA")) {
            return new String[] {element.substring(2), "icons_monster_info/Paralysis.png"};
        } else if (element.startsWith("PO")) {
            return new String[] {element.substring(2), "icons_monster_info/Poison.png"};
        } else if (element.startsWith("SL")) {
            return new String[] {element.substring(2), "icons_monster_info/Sleep.png"};
        } else if (element.startsWith("BL")) {
            return new String[] {element.substring(2), "icons_monster_info/Blastblight.png"};
        } else {
            return null;
        }
    }
}
