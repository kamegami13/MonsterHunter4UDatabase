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

    ArmorSetBuilderPieceContainer headView;

    ArmorSetBuilderPieceContainer bodyView;

    ArmorSetBuilderPieceContainer armsView;

    ArmorSetBuilderPieceContainer waistView;

    ArmorSetBuilderPieceContainer legsView;

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
        headView = (ArmorSetBuilderPieceContainer) view.findViewById(R.id.armor_builder_head);
        bodyView = (ArmorSetBuilderPieceContainer) view.findViewById(R.id.armor_builder_body);
        armsView = (ArmorSetBuilderPieceContainer) view.findViewById(R.id.armor_builder_arms);
        waistView = (ArmorSetBuilderPieceContainer) view.findViewById(R.id.armor_builder_waist);
        legsView = (ArmorSetBuilderPieceContainer) view.findViewById(R.id.armor_builder_legs);

        headView.initialize(0, createEmptyPieceClickListener(0));
        bodyView.initialize(1, createEmptyPieceClickListener(1));
        armsView.initialize(2, createEmptyPieceClickListener(2));
        waistView.initialize(3, createEmptyPieceClickListener(3));
        legsView.initialize(4, createEmptyPieceClickListener(4));

        return view;
    }

    @Override
    public void updateContents(ArmorSetBuilderSession s) {
        if (s.isHeadSelected()) {
            headView.updateContents(s, 0);
            headView.setOnClickListener(createPieceClickListener(0));

        }
        if (s.isBodySelected()) {
            bodyView.updateContents(s, 1);
            bodyView.setOnClickListener(createPieceClickListener(1));

        }
        if (s.isArmsSelected()) {
            armsView.updateContents(s, 2);
            armsView.setOnClickListener(createPieceClickListener(2));
        }
        if (s.isWaistSelected()) {
            waistView.updateContents(s, 3);
            waistView.setOnClickListener(createPieceClickListener(3));
        }
        if (s.isLegsSelected()) {
            legsView.updateContents(s, 4);
            legsView.setOnClickListener(createPieceClickListener(4));
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