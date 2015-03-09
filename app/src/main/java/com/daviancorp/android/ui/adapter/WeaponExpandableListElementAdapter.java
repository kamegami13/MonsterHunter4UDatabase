package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.WeaponListEntry;

/**
 * Created by Mark on 3/3/2015.
 */
public abstract class WeaponExpandableListElementAdapter extends WeaponExpandableListGeneralAdapter {

    public WeaponExpandableListElementAdapter(Context context, View.OnLongClickListener listener) {
        super(context, listener);
    }

    protected static class WeaponElementViewHolder extends WeaponViewHolder {

        // Element
        public TextView elementView;
        public TextView elementView2;
        public TextView awakenView;
        
        public ImageView elementIconView;
        public ImageView elementIconView2;

        public WeaponElementViewHolder(View weaponView) {
            super(weaponView);

            //
            // ELEMENT VIEWS
            //

            elementView = (TextView) weaponView.findViewById(R.id.element_text);
            elementView2 = (TextView) weaponView.findViewById(R.id.element_text2);
            awakenView = (TextView) weaponView.findViewById(R.id.awaken_text);
            elementIconView = (ImageView) weaponView.findViewById(R.id.element_image);
            elementIconView2 = (ImageView) weaponView.findViewById(R.id.element_image2);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        
        WeaponElementViewHolder holder = (WeaponElementViewHolder) viewHolder;
        Weapon weapon = ((WeaponListEntry) getItemAt(position)).weapon;

        // Set the element to view
        String element = weapon.getElement();
        String awakenedElement = weapon.getAwaken();
        String element2 = weapon.getElement2();
        long element_attack = weapon.getElementAttack();
        long element_2_attack = weapon.getElement2Attack();
        long awaken_attack = weapon.getAwakenAttack();
        String awakenText = "";


        holder.elementView.setText("");
        holder.elementView2.setText("");
        holder.elementIconView.setVisibility(View.INVISIBLE);
        holder.elementIconView2.setVisibility(View.INVISIBLE);

        if (!"".equals(element) || !"".equals(awakenedElement)) {
            if (!"".equals(awakenedElement)) {
                element = awakenedElement;
                element_attack = awaken_attack;
                awakenText = "(";
                holder.elementView.setText(Long.toString(element_attack) + ")");
            } else {
                holder.elementView.setText(Long.toString(element_attack));
            }

            holder.elementIconView.setTag(weapon.getId());
            holder.elementIconView.setVisibility(View.VISIBLE);

            final Bitmap bitmap = getBitmapFromMemCache("icons_monster_info/" + element + ".png");
            if(bitmap != null) {
                holder.elementIconView.setImageBitmap(bitmap);
            } else {
                new LoadImage(holder.elementIconView, "icons_monster_info/" + element + ".png").execute();
            }

        }
        if(!"".equals(element2)) {
            holder.elementIconView2.setTag(weapon.getId());
            final Bitmap bitmap = getBitmapFromMemCache("icons_monster_info/" + element2 + ".png");
            if(bitmap != null) {
                holder.elementIconView2.setImageBitmap(bitmap);
            } else {
                new LoadImage(holder.elementIconView2, "icons_monster_info/" + element2 + ".png").execute();
            }
            holder.elementIconView2.setVisibility(View.VISIBLE);

            holder.elementView2.setText("" + element_2_attack);
        }
        holder.awakenView.setText(awakenText);
    }
}
