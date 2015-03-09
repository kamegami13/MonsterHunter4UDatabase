package com.daviancorp.android.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.WeaponClickListener;
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
    protected final View.OnLongClickListener mListener;

    protected final Context mContext;

    /**
     * Unit of indentation.
     */
    private final int mPaddingDP = 4;

    // Image cache
    protected LruCache<String, Bitmap> mImageCache;

    public WeaponExpandableListGeneralAdapter(Context context, View.OnLongClickListener listener) {
        this.mContext = context;
        this.mListener = listener;

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap draw) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return draw.getByteCount() / 1024;
            }
        };
    }


    public Bitmap getBitmapFromMemCache(String key) {
        return mImageCache.get(key);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mImageCache.put(key, bitmap);
        }
    }

    protected static class WeaponViewHolder extends RecyclerView.ViewHolder {
        private View view;

        public LinearLayout weaponLayout;
        public RelativeLayout clickableLayout;

        public TextView nameView;
        public TextView attackView;
        public TextView slotView;
        public TextView affinityView;
        public TextView defenseView;

        public ImageView iconView;

        public View colorBand;
        public View indentView;
        public View arrow;

        private static final int[] indColors = {R.color.rare_1, R.color.rare_2,
                R.color.rare_3, R.color.rare_4, R.color.rare_5,
                R.color.rare_6, R.color.rare_7, R.color.rare_8, R.color.rare_9,
                R.color.rare_10};

        public WeaponViewHolder(View weaponView) {
            super(weaponView);
            view = weaponView;

            //
            // GENERAL VIEWS
            //

            // Set the layout id
            weaponLayout = (LinearLayout) weaponView.findViewById(R.id.main_layout);
            clickableLayout = (RelativeLayout) weaponView.findViewById(R.id.clickable_layout);

            // Find all views
            nameView = (TextView) weaponView.findViewById(R.id.name_text);
            attackView = (TextView) weaponView.findViewById(R.id.attack_text);
            slotView = (TextView) weaponView.findViewById(R.id.slots_text);
            affinityView = (TextView) weaponView.findViewById(R.id.affinity_text);
            defenseView = (TextView) weaponView.findViewById(R.id.defense_text);
            
            colorBand = weaponView.findViewById(R.id.color_band);
            indentView = weaponView.findViewById(R.id.indent_view);
            arrow = weaponView.findViewById(R.id.arrow);
        }

        public void setPaddingLeft(int paddingLeft) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indentView.getLayoutParams();
            params.width = paddingLeft;
        }

        public void setColorBandColor(int indentation) {
            int color = view.getContext().getResources().getColor(indColors[indentation]);
            colorBand.setBackgroundColor(color);
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
        /*
        holder.iconView.setTag(weapon.getId());
        final Bitmap bitmap = getBitmapFromMemCache(weapon.getFileLocation());
        if(bitmap != null) {
            holder.iconView.setImageBitmap(bitmap);
        } else {
            new LoadImage(holder.iconView, weapon.getFileLocation()).execute();
        }
        */
        //
        // Get the weapons name
        //
        String name = "";
        int wFinal = weapon.getWFinal();
        name = name + weapon.getName();

        // Get the weapons attack
        String attack = "DMG: " + weapon.getAttackString();

        //
        // Get affinity and defense
        //
        String affinity = "";
        if (weapon.getAffinity().length() > 0) {
            affinity = weapon.getAffinity() + "%";
        }


        String defense = "";
        if (weapon.getDefense() != 0) {
            defense = "DEF: " + weapon.getDefense();
        }

        //
        // Set remaining items
        //
        holder.nameView.setText(name);
        holder.attackView.setText(attack);
        holder.slotView.setText(weapon.getSlotString());
        holder.affinityView.setText(affinity);
        holder.defenseView.setText(defense);

        holder.view.setOnClickListener(new WeaponClickListener(mContext, weapon.getId()));

        //
        // Handle indentation
        //

        //holder.colorBand.setVisibility(View.VISIBLE);
        holder.setColorBandColor(weapon.getRarity()-1);

        int leftPadding = Utils.getPaddingPixels(mContext, mPaddingDP)
                * (weaponEntry.getIndentation());
        holder.setPaddingLeft(leftPadding);

        //
        // Handle groups
        //
        holder.arrow.setVisibility(View.INVISIBLE);
        if (weaponEntry.isGroup() && weaponEntry.getGroupSize() > 0) {
            holder.arrow.setVisibility(View.VISIBLE);
        }
    }

    protected class LoadImage extends AsyncTask<Void,Void,Bitmap> {
        private ImageView mImage;
        private String path;
        private String imagePath;

        public LoadImage(ImageView imv, String imagePath) {
            this.mImage = imv;
            this.path = imv.getTag().toString();
            this.imagePath = imagePath;
        }

        @Override
        protected Bitmap doInBackground(Void... arg0) {
            Bitmap d = null;
            try {
                d = BitmapFactory.decodeStream(mImage.getContext().getAssets().open(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            addBitmapToMemoryCache(imagePath, d);

            return d;
        }

        protected void onPostExecute(Bitmap result) {
            if (mImage.getTag().toString().equals(path)) {
                mImage.setImageBitmap(result);
            }
        }
    }
}
