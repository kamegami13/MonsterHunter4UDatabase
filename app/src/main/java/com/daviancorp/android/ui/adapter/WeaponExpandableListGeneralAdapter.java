package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.general.WeaponListEntry;
import com.oissela.software.multilevelexpindlistview.MultiLevelExpIndListAdapter;
import com.oissela.software.multilevelexpindlistview.Utils;

import java.io.IOException;

/**
 * Created by Mark on 3/3/2015.
 */
public abstract class WeaponExpandableListGeneralAdapter extends MultiLevelExpIndListAdapter {

    /**
     * This is called when the user click on an item or group.
     */
    protected final View.OnClickListener mListener;

    protected final Context mContext;

    /**
     * Unit of indentation.
     */
    private final int mPaddingDP = 4;

    public WeaponExpandableListGeneralAdapter(Context context, View.OnClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    protected static class WeaponViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public RelativeLayout weaponLayout;

        public TextView nameView;
        public TextView attackView;
        public TextView slotView;
        public TextView affinityView;
        public TextView defenseView;

        public ImageView iconView;

        public WeaponViewHolder(View weaponView) {
            super(weaponView);
            view = weaponView;

            //
            // GENERAL VIEWS
            //

            // Set the layout id
            weaponLayout = (RelativeLayout) weaponView.findViewById(R.id.main_layout);

            // Find all views
            nameView = (TextView) weaponView.findViewById(R.id.name_text);
            attackView = (TextView) weaponView.findViewById(R.id.attack_text);
            slotView = (TextView) weaponView.findViewById(R.id.slots_text);
            affinityView = (TextView) weaponView.findViewById(R.id.affinity_text);
            defenseView = (TextView) weaponView.findViewById(R.id.defense_text);
            iconView = (ImageView) weaponView.findViewById(R.id.weapon_icon);
            
            //holder.lineLayout = (View) weaponView.findViewById(R.id.tree_lines);
        }

        public void setPaddingLeft(int paddingLeft) {
            view.setPadding(paddingLeft,0,0,0);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        WeaponViewHolder holder = (WeaponViewHolder) viewHolder;
        WeaponListEntry weaponEntry = (WeaponListEntry) getItemAt(position);
        Weapon weapon = weaponEntry.weapon;

        //
        // Set the image for the weapon
        //
        holder.iconView.setTag(weapon.getId());
        new LoadImage(holder.iconView, weapon.getFileLocation()).execute();

        //
        // Get the weapons name
        //
        String name = "";
        int wFinal = weapon.getWFinal();
        name = name + weapon.getName();

        // Get the weapons attack
        String attack = Long.toString(weapon.getAttack());

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
        holder.nameView.setText(name);
        holder.attackView.setText(attack);
        holder.slotView.setText(weapon.getSlotString());
        holder.affinityView.setText(affinity);
        holder.defenseView.setText(defense);

        //
        // Handle indentation
        //

        if (weaponEntry.getIndentation() == 0) {
            holder.setPaddingLeft(0);
        } else {
            int leftPadding = Utils.getPaddingPixels(mContext, mPaddingDP)
                    * (weaponEntry.getIndentation());
            holder.setPaddingLeft(leftPadding);
        }
    }

    protected class LoadImage extends AsyncTask<Void,Void,Drawable> {
        private ImageView mImage;
        private String path;
        private String imagePath;

        public LoadImage(ImageView imv, String imagePath) {
            this.mImage = imv;
            this.path = imv.getTag().toString();
            this.imagePath = imagePath;
        }

        @Override
        protected Drawable doInBackground(Void... arg0) {
            Drawable d = null;

            try {
                d = Drawable.createFromStream(mImage.getContext().getAssets().open(imagePath),
                        null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return d;
        }

        protected void onPostExecute(Drawable result) {
            if (mImage.getTag().toString().equals(path)) {
                mImage.setImageDrawable(result);
            }
        }
    }
}
