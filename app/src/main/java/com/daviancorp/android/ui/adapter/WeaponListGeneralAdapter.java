package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    protected static class GeneralViewHolder {
        // General
        public RelativeLayout weaponLayout;
        public TextView nametv;
        public TextView attacktv;
        public TextView slottv;
        public TextView affinitytv;
        public TextView defensetv;
        public ImageView weaponIcon;
        public View lineLayout;
    }

    protected WeaponCursor mWeaponCursor;

    public WeaponListGeneralAdapter(Context context, WeaponCursor cursor) {
        super(context, cursor, 0);
        mWeaponCursor = cursor;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get the monster for the current row
        Weapon weapon = mWeaponCursor.getWeapon();

        GeneralViewHolder holder = (GeneralViewHolder) view.getTag();

        // Set the layout id
        holder.weaponLayout.setOnClickListener(new WeaponClickListener(context, weapon.getId()));

        //
        // Set the image for the weapon
        //
        holder.weaponIcon.setTag(weapon.getId());
        new LoadImage(holder.weaponIcon, weapon.getFileLocation()).execute();

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
        holder.nametv.setText(name);
        holder.attacktv.setText(attack);
        holder.slottv.setText(weapon.getSlotString());
        holder.affinitytv.setText(affinity);
        holder.defensetv.setText(defense);


        // Indent the tree
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.weaponIcon.getLayoutParams();
        int spacing = (int) (4.0f * Resources.getSystem().getDisplayMetrics().density + 0.5)
                * weapon.getTree_Depth();

        params.leftMargin = 0;
        params.leftMargin = params.leftMargin + spacing;

        holder.lineLayout.getLayoutParams().width = spacing;
        holder.lineLayout.setBackgroundColor(context.getResources().getColor(R.color.divider_color));
    }


    protected Drawable getDrawable(Context c, String location) {
        Drawable d = null;
        d = Drawable.createFromPath(location);
        return d;
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