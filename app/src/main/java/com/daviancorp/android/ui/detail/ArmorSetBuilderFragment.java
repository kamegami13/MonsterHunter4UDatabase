package com.daviancorp.android.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.compound.ArmorSetBuilderPieceContainer;

/**
 * This is where the magic happens baby. Users can define a custom armor set in this fragment.
 */
public class ArmorSetBuilderFragment extends Fragment implements ArmorSetBuilderActivity.OnArmorSetActivityUpdateListener {

    ArmorSetBuilderPieceContainer headView;
    ArmorSetBuilderPieceContainer bodyView;
    ArmorSetBuilderPieceContainer armsView;
    ArmorSetBuilderPieceContainer waistView;
    ArmorSetBuilderPieceContainer legsView;
    ArmorSetBuilderPieceContainer talismanView;

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
        talismanView = (ArmorSetBuilderPieceContainer) view.findViewById(R.id.armor_builder_talisman);

        ArmorSetBuilderSession s = ((ArmorSetBuilderActivity) getActivity()).getArmorSetBuilderSession();

        headView.initialize(s, 0, this);
        bodyView.initialize(s, 1, this);
        armsView.initialize(s, 2, this);
        waistView.initialize(s, 3, this);
        legsView.initialize(s, 4, this);
        talismanView.initialize(s, 5, this);

        return view;
    }

    @Override
    public void onArmorSetActivityUpdated(ArmorSetBuilderSession s) {
        headView.updateContents();
        bodyView.updateContents();
        armsView.updateContents();
        waistView.updateContents();
        legsView.updateContents();
        talismanView.updateContents();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // We have to check to make sure that the Activity that this is being attached to is connected to the callback interface for this fragment.
        try {
            ArmorSetBuilderActivity a = (ArmorSetBuilderActivity) getActivity();
            a.addArmorSetChangedListener(this);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must be an ArmorSetBuilderActivity.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((ArmorSetBuilderActivity)getActivity()).fragmentResultReceived(requestCode, resultCode, data);
    }
}