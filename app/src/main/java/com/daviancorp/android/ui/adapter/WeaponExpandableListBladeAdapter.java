package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.DrawSharpness;
import com.daviancorp.android.ui.general.WeaponListEntry;

/**
 * Created by Mark on 3/3/2015.
 */
public class WeaponExpandableListBladeAdapter extends WeaponExpandableListElementAdapter {

    public WeaponExpandableListBladeAdapter(Context context, View.OnLongClickListener listener) {
        super(context, listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder viewHolder;

        int resource = R.layout.fragment_weapon_tree_item_blademaster;
        v = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);

        viewHolder = new WeaponBladeViewHolder(v);

        v.setOnLongClickListener(mListener);

        return viewHolder;
    }

    private static class WeaponBladeViewHolder extends WeaponElementViewHolder {
        // Blade

        TextView specialView;
        DrawSharpness sharpnessDrawable;
        ImageView note1v;
        ImageView note2v;
        ImageView note3v;

        public WeaponBladeViewHolder(View weaponView) {
            super(weaponView);

            //
            // BLADE VIEWS

            specialView = (TextView) weaponView.findViewById(R.id.special_text);
            sharpnessDrawable = (DrawSharpness) weaponView.findViewById(R.id.sharpness);


            note1v = (ImageView) weaponView.findViewById(R.id.note_image_1);
            note2v = (ImageView) weaponView.findViewById(R.id.note_image_2);
            note3v = (ImageView) weaponView.findViewById(R.id.note_image_3);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);

        WeaponBladeViewHolder holder = (WeaponBladeViewHolder) viewHolder;
        Weapon weapon = ((WeaponListEntry) getItemAt(position)).weapon;

        //
        // Set special text fields
        //
        String type = weapon.getWtype();
        if (type.equals("Hunting Horn")) {
            String special = weapon.getHornNotes();

            holder.note1v.setTag(weapon.getId());
            holder.note2v.setTag(weapon.getId());
            holder.note3v.setTag(weapon.getId());

            holder.note1v.setVisibility(View.VISIBLE);
            holder.note2v.setVisibility(View.VISIBLE);
            holder.note3v.setVisibility(View.VISIBLE);
            holder.specialView.setVisibility(View.VISIBLE);
            holder.specialView.setText("NOTES: ");

            final Bitmap bitmap = getBitmapFromMemCache(getNoteDrawable(special.charAt(0)));
            if (bitmap != null) {
                holder.note1v.setImageBitmap(bitmap);
            } else {
                new LoadImage(holder.note1v, getNoteDrawable(special.charAt(0))).execute();
            }

            final Bitmap bitmap2 = getBitmapFromMemCache(getNoteDrawable(special.charAt(1)));
            if (bitmap2 != null) {
                holder.note2v.setImageBitmap(bitmap2);
            } else {
                new LoadImage(holder.note2v, getNoteDrawable(special.charAt(1))).execute();
            }

            final Bitmap bitmap3 = getBitmapFromMemCache(getNoteDrawable(special.charAt(2)));
            if (bitmap3 != null) {
                holder.note3v.setImageBitmap(bitmap3);
            } else {
                new LoadImage(holder.note3v, getNoteDrawable(special.charAt(2))).execute();
            }


        } else if (type.equals("Gunlance")) {
            holder.specialView.setVisibility(View.VISIBLE);
            String special = weapon.getShellingType();
            holder.specialView.setText(special);
        } else if (type.equals("Switch Axe") || type.equals("Charge Blade")) {
            holder.specialView.setVisibility(View.VISIBLE);
            String special = weapon.getPhial();
            holder.specialView.setText(special);
        }

        // Set sharpness
        holder.sharpnessDrawable.init(weapon.getSharpness1(), weapon.getSharpness2());
        holder.sharpnessDrawable.invalidate();
    }

    private String getNoteDrawable(char note) {
        String file = "icons_monster_info/";

        switch (note) {
            case 'B':
                file = file + "Note.blue.png";
                break;
            case 'C':
                file = file + "Note.aqua.png";
                break;
            case 'G':
                file = file + "Note.green.png";
                break;
            case 'O':
                file = file + "Note.orange.png";
                break;
            case 'P':
                file = file + "Note.purple.png";
                break;
            case 'R':
                file = file + "Note.red.png";
                break;
            case 'W':
                file = file + "Note.white.png";
                break;
            case 'Y':
                file = file + "Note.yellow.png";
                break;
        }

        return file;
    }
}
