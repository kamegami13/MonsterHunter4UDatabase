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

        ArmorSetBuilderSession s = ((ArmorSetBuilderActivity) getActivity()).getArmorSetBuilderSession();

        headView.initialize(s, 0);
        bodyView.initialize(s, 1);
        armsView.initialize(s, 2);
        waistView.initialize(s, 3);
        legsView.initialize(s, 4);

        return view;
    }

    @Override
    public void updateContents(ArmorSetBuilderSession s) {
        headView.updateContents();
        bodyView.updateContents();
        armsView.updateContents();
        waistView.updateContents();
        legsView.updateContents();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // We have to check to make sure that the Activity that this is being attached to is connected to the callback interface for this fragment.
        try {
            ArmorSetBuilderActivity a = (ArmorSetBuilderActivity) getActivity();
            a.addArmorSetChangedListener(this);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must be a ArmorSetBuilderActivity.");
        }
    }
}