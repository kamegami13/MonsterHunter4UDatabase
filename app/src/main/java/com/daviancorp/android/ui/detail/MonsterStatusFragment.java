package com.daviancorp.android.ui.detail;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

import com.daviancorp.android.data.classes.MonsterStatus;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonsterStatusFragment extends Fragment {
    private static final String ARG_MONSTER_ID = "MONSTER_ID";

    private Bundle mBundle;

    private TableLayout mStatusTable; // Location of table to add rows to

    /**
     * Default constructor
     */
    public MonsterStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of the fragment for a monster
     * @param monsterId id of monster for the fragment
     * @return The created fragment
     */
    public static MonsterStatusFragment newInstance(long monsterId) {
        Bundle args = new Bundle();
        args.putLong(ARG_MONSTER_ID, monsterId);
        MonsterStatusFragment f = new MonsterStatusFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monster_status, container, false);

        mStatusTable = (TableLayout) view.findViewById(R.id.statusTable);

        ArrayList<MonsterStatus> statuses =
                DataManager.get(getActivity()).queryMonsterStatus(getArguments().getLong(ARG_MONSTER_ID));

        MonsterStatus currentStatus = null;
        String status, initial, increase, max, duration, damage;
        String imageFile;

        for(int i = 0; i < statuses.size(); i++) {
            TableRow wdRow = (TableRow) inflater.inflate(
                    R.layout.fragment_monster_status_listitem, mStatusTable, false);

            currentStatus = statuses.get(i);

            // Get our strings and our views
            status = currentStatus.getStatus();
            initial = Long.toString(currentStatus.getInitial());
            increase = Long.toString(currentStatus.getIncrease());
            max = Long.toString(currentStatus.getMax());
            duration = Long.toString(currentStatus.getDuration());
            damage = Long.toString(currentStatus.getDamage());

            TextView statusView = (TextView) wdRow.findViewById(R.id.status);
            ImageView statusImage = (ImageView) wdRow.findViewById(R.id.statusImage);
            TextView initialView = (TextView) wdRow.findViewById(R.id.initial);
            TextView increaseView = (TextView) wdRow.findViewById(R.id.increase);
            TextView maxView = (TextView) wdRow.findViewById(R.id.max);
            TextView durationView = (TextView) wdRow.findViewById(R.id.duration);
            TextView damageView = (TextView) wdRow.findViewById(R.id.damage);

            // Check which image to load
            boolean image = true;
            imageFile = "icons_monster_info/";
            switch (status)
            {
                case "Poison":
                    imageFile = imageFile + "Poison.png";
                    break;
                case "Sleep":
                    imageFile = imageFile + "Sleep.png";
                    break;
                case "Para":
                    imageFile = imageFile + "Paralysis.png";
                    break;
                case "KO":
                    imageFile = imageFile + "Stun.png";
                    break;
                case "Exhaust":
                    //statusView.setText(status);
                    imageFile = imageFile + "exhaust.png";
                    break;
                case "Blast":
                    imageFile = imageFile + "Blastblight.png";
                    break;
                case "Jump":
                    //statusView.setText(status);
                    imageFile = imageFile + "jump.png";
                    break;
                case "Mount":
                    //statusView.setText(status);
                    imageFile = imageFile + "mount.png";
                    break;
            }

            // initialize our views
            initialView.setText(initial);
            increaseView.setText(increase);
            maxView.setText(max);
            durationView.setText(duration);
            damageView.setText(damage);

            if (image) {
                Drawable draw = null;
                try {
                    draw = Drawable.createFromStream(
                            getActivity().getAssets().open(imageFile), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                android.view.ViewGroup.LayoutParams layoutParams = statusImage.getLayoutParams();
                layoutParams.width = 80;
                layoutParams.height = 80;
                statusImage.setLayoutParams(layoutParams);

                statusImage.setImageDrawable(draw);
            }

            mStatusTable.addView(wdRow);
        }

        return view;
    }
}
