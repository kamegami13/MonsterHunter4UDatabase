package com.daviancorp.android.ui.detail;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is where the magic happens baby. Users can define a custom armor set in this fragment.
 */
public class ArmorSetBuilderFragment extends Fragment implements ArmorSetBuilderActivity.ArmorSetChangedListener {
    
    ImageView headImage;
    TextView headText;

    ImageView bodyImage;
    TextView bodyText;

    ImageView armsImage;
    TextView armsText;

    ImageView waistImage;
    TextView waistText;

    ImageView legsImage;
    TextView legsText;

    public static ArmorSetBuilderFragment newInstance() {
        Bundle args = new Bundle();
        ArmorSetBuilderFragment f = new ArmorSetBuilderFragment();
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_armor_set_builder, container, false);
        View headView = view.findViewById(R.id.armor_builder_helmet);
        View bodyView = view.findViewById(R.id.armor_builder_body);
        View armsView = view.findViewById(R.id.armor_builder_arms);
        View waistView = view.findViewById(R.id.armor_builder_waist);
        View legsView = view.findViewById(R.id.armor_builder_legs);
        
        headText = (TextView) headView.findViewById(R.id.armor_builder_item_name);
        headImage = (ImageView) headView.findViewById(R.id.armor_builder_item_icon);
        headImage.setImageBitmap(fetchIcon("Head", 1));

        bodyText = (TextView) bodyView.findViewById(R.id.armor_builder_item_name);
        bodyImage = (ImageView) bodyView.findViewById(R.id.armor_builder_item_icon);
        bodyImage.setImageBitmap(fetchIcon("Body", 1));

        armsText = (TextView) armsView.findViewById(R.id.armor_builder_item_name);
        armsImage = (ImageView) armsView.findViewById(R.id.armor_builder_item_icon);
        armsImage.setImageBitmap(fetchIcon("Arms", 1));

        waistText = (TextView) waistView.findViewById(R.id.armor_builder_item_name);
        waistImage = (ImageView) waistView.findViewById(R.id.armor_builder_item_icon);
        waistImage.setImageBitmap(fetchIcon("Waist", 1));

        legsText = (TextView) legsView.findViewById(R.id.armor_builder_item_name);
        legsImage = (ImageView) legsView.findViewById(R.id.armor_builder_item_icon);
        legsImage.setImageBitmap(fetchIcon("Legs", 1));

        return view;
    }

    @Override
    public void updateContents(ArmorSetBuilderSession s) {
        if (s.isHeadSelected()) {
            headText.setText(s.getHead().getName());
            headImage.setImageBitmap(fetchIcon("Head", s.getHead().getRarity()));
        }
        if (s.isBodySelected()) {
            bodyText.setText(s.getBody().getName());
            bodyImage.setImageBitmap(fetchIcon("Body", s.getBody().getRarity()));
        }
        if (s.isArmsSelected()) {
            armsText.setText(s.getArms().getName());
            armsImage.setImageBitmap(fetchIcon("Arms", s.getArms().getRarity()));
        }
        if (s.isWaistSelected()) {
            waistText.setText(s.getWaist().getName());
            waistImage.setImageBitmap(fetchIcon("Waist", s.getWaist().getRarity()));
        }
        if (s.isLegsSelected()) {
            legsText.setText(s.getLegs().getName());
            legsImage.setImageBitmap(fetchIcon("Legs", s.getLegs().getRarity()));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // We have to check to make sure that the Activity that this is being attached to is connected to the callback interface for this fragment.
        try {
            ((ArmorSetBuilderActivity) getActivity()).setOnArmorSetChangedListener(this);
        }
        catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must be a ArmorSetBuilderActivity.");
        }
    }

    /** Helper method that retrieves a rarity-appropriate equipment icon. */
    private Bitmap fetchIcon(String slot, int rarity) {
        if (slot.equals("Head") || slot.equals("Body") || slot.equals("Arms") || slot.equals("Waist") || slot.equals("Legs")) {
            String imageRes = "icons_armor/icons_" + slot.toLowerCase() + "/" + slot.toLowerCase() + String.valueOf(rarity) + ".png";
            AssetManager manager = getActivity().getAssets();
            InputStream stream;

            try {
                stream = manager.open(imageRes);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);

                stream.close();

                return bitmap;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.e("SET BUILDER", "Invalid slot argument!");
        }
        return null;
    }
}