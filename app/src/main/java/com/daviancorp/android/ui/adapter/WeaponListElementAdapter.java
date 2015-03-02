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
        String element = weapon.getElement();
        String awakenedElement = weapon.getAwaken();
        String element2 = weapon.getElement2();
        long element_attack = weapon.getElementAttack();
        long element_2_attack = weapon.getElement2Attack();
        long awaken_attack = weapon.getAwakenAttack();
        String awakenText = "";


        holder.elementtv.setText("");
        holder.elementtv2.setText("");
        holder.elementIcon.setVisibility(view.GONE);
        holder.element2Icon.setVisibility(view.GONE);

        if (!"".equals(element) || !"".equals(awakenedElement)) {
            if (!"".equals(awakenedElement)) {
                element = awakenedElement;
                element_attack = awaken_attack;
                awakenText = "(";
                holder.elementtv.setText("" + element_attack + ")");
            } else {
                holder.elementtv.setText("" + element_attack);
            }

            holder.elementIcon.setTag(weapon.getId());
            new LoadImage(holder.elementIcon, "icons_monster_info/" + element + ".png").execute();
            holder.elementIcon.setVisibility(view.VISIBLE);

        }
        if(!"".equals(element2)) {
            holder.element2Icon.setTag(weapon.getId());
            new LoadImage(holder.element2Icon, "icons_monster_info/" + element2 + ".png").execute();
            holder.element2Icon.setVisibility(view.VISIBLE);

            holder.elementtv2.setText("" + element_2_attack);
        }
        holder.awakentv.setText(awakenText);

    }
}
