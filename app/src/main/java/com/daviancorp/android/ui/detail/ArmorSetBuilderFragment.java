package com.daviancorp.android.ui.detail;

import android.app.Activity;
import android.content.Intent;
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
import com.daviancorp.android.ui.list.ArmorListActivity;
import com.daviancorp.android.ui.list.DecorationListActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is where the magic happens baby. Users can define a custom armor set in this fragment.
 */
public class ArmorSetBuilderFragment extends Fragment implements ArmorSetBuilderActivity.ArmorSetChangedListener {

    ViewGroup headView;
    ImageView headImage;
    TextView headText;
    ArmorSetBuilderDecorationsContainer headDecorations;

    View bodyView;
    ImageView bodyImage;
    TextView bodyText;
    ArmorSetBuilderDecorationsContainer bodyDecorations;

    View armsView;
    ImageView armsImage;
    TextView armsText;
    ArmorSetBuilderDecorationsContainer armsDecorations;

    View waistView;
    ImageView waistImage;
    TextView waistText;
    ArmorSetBuilderDecorationsContainer waistDecorations;

    View legsView;
    ImageView legsImage;
    TextView legsText;
    ArmorSetBuilderDecorationsContainer legsDecorations;

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
        headView = (ViewGroup) view.findViewById(R.id.armor_builder_helmet);
        bodyView = view.findViewById(R.id.armor_builder_body);
        armsView = view.findViewById(R.id.armor_builder_arms);
        waistView = view.findViewById(R.id.armor_builder_waist);
        legsView = view.findViewById(R.id.armor_builder_legs);

        headText = (TextView) headView.findViewById(R.id.armor_builder_item_name);
        headImage = (ImageView) headView.findViewById(R.id.armor_builder_item_icon);
        headImage.setImageBitmap(fetchIcon("Head", 1));
        headDecorations = (ArmorSetBuilderDecorationsContainer) headView.findViewById(R.id.armor_set_builder_decoration_container);
        headView.setOnClickListener(createEmptyPieceClickListener(0));

        bodyText = (TextView) bodyView.findViewById(R.id.armor_builder_item_name);
        bodyImage = (ImageView) bodyView.findViewById(R.id.armor_builder_item_icon);
        bodyImage.setImageBitmap(fetchIcon("Body", 1));
        bodyDecorations = (ArmorSetBuilderDecorationsContainer) bodyView.findViewById(R.id.armor_set_builder_decoration_container);
        bodyView.setOnClickListener(createEmptyPieceClickListener(1));

        armsText = (TextView) armsView.findViewById(R.id.armor_builder_item_name);
        armsImage = (ImageView) armsView.findViewById(R.id.armor_builder_item_icon);
        armsImage.setImageBitmap(fetchIcon("Arms", 1));
        armsDecorations = (ArmorSetBuilderDecorationsContainer) armsView.findViewById(R.id.armor_set_builder_decoration_container);
        armsView.setOnClickListener(createEmptyPieceClickListener(2));

        waistText = (TextView) waistView.findViewById(R.id.armor_builder_item_name);
        waistImage = (ImageView) waistView.findViewById(R.id.armor_builder_item_icon);
        waistImage.setImageBitmap(fetchIcon("Waist", 1));
        waistDecorations = (ArmorSetBuilderDecorationsContainer) waistView.findViewById(R.id.armor_set_builder_decoration_container);
        waistView.setOnClickListener(createEmptyPieceClickListener(3));

        legsText = (TextView) legsView.findViewById(R.id.armor_builder_item_name);
        legsImage = (ImageView) legsView.findViewById(R.id.armor_builder_item_icon);
        legsImage.setImageBitmap(fetchIcon("Legs", 1));
        legsDecorations = (ArmorSetBuilderDecorationsContainer) legsView.findViewById(R.id.armor_set_builder_decoration_container);
        legsView.setOnClickListener(createEmptyPieceClickListener(4));

        return view;
    }

    @Override
    public void updateContents(ArmorSetBuilderSession s) {
        if (s.isHeadSelected()) {
            headText.setText(s.getHead().getName());
            headImage.setImageBitmap(fetchIcon("Head", s.getHead().getRarity()));
            headView.setOnClickListener(createPieceClickListener(0));

            headDecorations.updateContents(s, 0);
        }
        if (s.isBodySelected()) {
            bodyText.setText(s.getBody().getName());
            bodyImage.setImageBitmap(fetchIcon("Body", s.getBody().getRarity()));
            bodyView.setOnClickListener(createPieceClickListener(1));

            bodyDecorations.updateContents(s, 1);
        }
        if (s.isArmsSelected()) {
            armsText.setText(s.getArms().getName());
            armsImage.setImageBitmap(fetchIcon("Arms", s.getArms().getRarity()));
            armsView.setOnClickListener(createPieceClickListener(2));

            armsDecorations.updateContents(s, 2);
        }
        if (s.isWaistSelected()) {
            waistText.setText(s.getWaist().getName());
            waistImage.setImageBitmap(fetchIcon("Waist", s.getWaist().getRarity()));
            waistView.setOnClickListener(createPieceClickListener(3));

            waistDecorations.updateContents(s, 3);
        }
        if (s.isLegsSelected()) {
            legsText.setText(s.getLegs().getName());
            legsImage.setImageBitmap(fetchIcon("Legs", s.getLegs().getRarity()));
            legsView.setOnClickListener(createPieceClickListener(4));

            legsDecorations.updateContents(s, 4);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // We have to check to make sure that the Activity that this is being attached to is connected to the callback interface for this fragment.
        try {
            ArmorSetBuilderActivity a = (ArmorSetBuilderActivity) getActivity();
            a.addArmorSetChangedListener(this);
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
            Log.e("SetBuilder", "Invalid slot argument!");
        }
        return null;
    }

    private View.OnClickListener createPieceClickListener(final int pieceIndex) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DecorationListActivity.class);
                i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, true);
                i.putExtra(ArmorSetBuilderActivity.EXTRA_PIECE_INDEX, pieceIndex);

                getActivity().startActivityForResult(i, ArmorSetBuilderActivity.REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener createEmptyPieceClickListener(final int pieceIndex) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ArmorListActivity.class);
                i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_SET_BUILDER, true);
                i.putExtra(ArmorSetBuilderActivity.EXTRA_PIECE_INDEX, pieceIndex);

                getActivity().startActivityForResult(i, ArmorSetBuilderActivity.REQUEST_CODE);
            }
        };
    }
}