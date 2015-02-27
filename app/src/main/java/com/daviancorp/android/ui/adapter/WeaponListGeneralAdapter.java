package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.database.WeaponCursor;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;

import java.io.IOException;

/**
 * Created by Mark on 2/26/2015.
 * Does work that is common to all weapons for list items
 */
public abstract class WeaponListGeneralAdapter extends CursorAdapter {

    protected WeaponCursor mWeaponCursor;

    public WeaponListGeneralAdapter(Context context, WeaponCursor cursor) {
        super(context, cursor, 0);
        mWeaponCursor = cursor;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get the monster for the current row
        Weapon weapon = mWeaponCursor.getWeapon();

        // Set the layout id
        RelativeLayout weaponLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        weaponLayout.setTag(weapon.getId());
        weaponLayout.setOnClickListener(new WeaponClickListener(context, weapon.getId()));

        // Find all views
        TextView nametv = (TextView) view.findViewById(R.id.name_text);
        TextView attacktv = (TextView) view.findViewById(R.id.attack_text);
        TextView slottv = (TextView) view.findViewById(R.id.slots_text);
        TextView affinitytv = (TextView) view.findViewById(R.id.affinity_text);
        TextView defensetv = (TextView) view.findViewById(R.id.defense_text);

        ImageView weaponIcon = (ImageView) view.findViewById(R.id.weapon_icon);

        //
        // Set the image for the weapon
        //
        String cellImage = "";
        switch (weapon.getWtype()) {
            case "Great Sword":
                cellImage = "icons_weapons/icons_great_sword/great_sword" + weapon.getRarity() + ".png";
                break;
            case "Long Sword":
                cellImage = "icons_weapons/icons_long_sword/long_sword" + weapon.getRarity() + ".png";
                break;
            case "Sword and Shield":
                cellImage = "icons_weapons/icons_sword_and_shield/sword_and_shield" + weapon.getRarity() + ".png";
                break;
            case "Dual Blades":
                cellImage = "icons_weapons/icons_dual_blades/dual_blades" + weapon.getRarity() + ".png";
                break;
            case "Hammer":
                cellImage = "icons_weapons/icons_hammer/hammer" + weapon.getRarity() + ".png";
                break;
            case "Hunting Horn":
                cellImage = "icons_weapons/icons_hunting_horn/hunting_horn" + weapon.getRarity() + ".png";
                break;
            case "Lance":
                cellImage = "icons_weapons/icons_hammer/hammer" + weapon.getRarity() + ".png";
                break;
            case "Gunlance":
                cellImage = "icons_weapons/icons_gunlance/gunlance" + weapon.getRarity() + ".png";
                break;
            case "Switch Axe":
                cellImage = "icons_weapons/icons_switch_axe/switch_axe" + weapon.getRarity() + ".png";
                break;
            case "Charge Blade":
                cellImage = "icons_weapons/icons_charge_blade/charge_blade" + weapon.getRarity() + ".png";
                break;
            case "Insect Glaive":
                cellImage = "icons_weapons/icons_insect_glaive/insect_glaive" + weapon.getRarity() + ".png";
                break;
            case "Light Bowgun":
                cellImage = "icons_weapons/icons_light_bowgun/light_bowgun" + weapon.getRarity() + ".png";
                break;
            case "Heavy Bowgun":
                cellImage = "icons_weapons/icons_heavy_bowgun/heavy_bowgun" + weapon.getRarity() + ".png";
                break;
            case "Bow":
                cellImage = "icons_weapons/icons_bow/bow" + weapon.getRarity() + ".png";
                break;
        }
        weaponIcon.setImageDrawable(getDrawable(context, cellImage));


        //
        // Get the weapons name
        //
        String name = "";
        int wFinal = weapon.getWFinal();
        for (int i = 0; i < weapon.getTree_Depth(); i++) {
            name = name + "-";
        }
        name = name + weapon.getName();

        // Get the weapons attack
        String attack = Long.toString(weapon.getAttack());


        // Set the slot to view
        String slot = "";
        switch (weapon.getNumSlots()) {
            case 0:
                slot = "---";
                break;
            case 1:
                slot = "O--";
                break;
            case 2:
                slot = "OO-";
                break;
            case 3:
                slot = "OOO";
                break;
            default:
                slot = "error!!";
                break;
        }

        //
        // Get affinity and defense
        //
        String affinity = "";
        if (weapon.getAffinity().length() > 0) {
            affinity = weapon.getAffinity() + "%";
        }

        String defense = "";
        if (weapon.getDefense() != 0) {
            defense = "" + weapon.getDefense();
        }

        //
        // Set remaining items
        //
        nametv.setText(name);
        attacktv.setText(attack);
        slottv.setText(slot);
        affinitytv.setText(affinity);
        defensetv.setText(defense);
    }


    protected Drawable getDrawable(Context c, String location) {
        Drawable d = null;

        try {
            d = Drawable.createFromStream(c.getAssets().open(location),
                    null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return d;
    }
}