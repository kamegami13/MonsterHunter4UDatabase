package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.DrawSharpness;

/**
 * Created by Mark on 2/26/2015.
 * Abstract adapter for weapons with element (blade, bow)
 */
public abstract class WeaponListElementAdapter extends WeaponListGeneralAdapter {

    protected static class ElementViewHolder extends GeneralViewHolder {
        // Element
        public TextView elementtv;
        public TextView elementtv2;
        public TextView awakentv;
        public ImageView elementIcon;
        public ImageView element2Icon;
    }

    public WeaponListElementAdapter(Context context, WeaponCursor cursor) {
        super(context, cursor);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ElementViewHolder holder = (ElementViewHolder) view.getTag();

        // Get the weapon for the current row
        Weapon weapon = mWeaponCursor.getWeapon();

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

            // Load drawable
            //dEle = getDrawable(context, elementData[1]);
            //elementIcon.setImageDrawable(dEle);

            holder.elementIcon.setTag(weapon.getId());
            new LoadImage(holder.elementIcon, elementData[1]).execute();
            holder.elementIcon.setVisibility(view.VISIBLE);

            if (element.contains(",")) {
                String[] twoElements = elementText.split(",");
                elementText = twoElements[0];
                dualElement = twoElements[1];

                String[] dualElementData = getElementData(dualElement);


                holder.elementtv2.setText(dualElementData[0]);

                // Load drawable
                holder.element2Icon.setTag(weapon.getId());
                new LoadImage(holder.element2Icon, dualElementData[1]).execute();
                holder.element2Icon.setVisibility(view.VISIBLE);
            } else {
                holder.elementtv2.setText("");
                holder.element2Icon.setImageDrawable(null);
                holder.element2Icon.setVisibility(view.GONE);
            }

            if (!"".equals(awakenedElement)) {
                elementText = elementText + ")";
            }

            holder.elementtv.setText(elementText);
        } else {
            holder.elementtv.setText("");
            holder.elementIcon.setImageDrawable(null);
            holder.elementtv2.setText("");
            holder.element2Icon.setImageDrawable(null);
            holder.elementIcon.setVisibility(view.GONE);
            holder.element2Icon.setVisibility(view.GONE);
        }

        holder.awakentv.setText(awakenText);
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
