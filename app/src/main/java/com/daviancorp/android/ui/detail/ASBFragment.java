package com.daviancorp.android.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.compound.ASBPieceContainer;

/**
 * This is where the magic happens baby. Users can define a custom armor set in this fragment.
 */
public class ASBFragment extends Fragment implements ASBActivity.OnASBSetActivityUpdateListener {

    public static final String ARG_SET_RANK = "set_rank";
    public static final String ARG_SET_HUNTER_TYPE = "set_hunter_type";

    ASBPieceContainer headView;
    ASBPieceContainer bodyView;
    ASBPieceContainer armsView;
    ASBPieceContainer waistView;
    ASBPieceContainer legsView;
    ASBPieceContainer talismanView;

    public static ASBFragment newInstance(int setRank, int setHunterType) {
        Bundle args = new Bundle();
        ASBFragment f = new ASBFragment();
        args.putInt(ARG_SET_RANK, setRank);
        args.putInt(ARG_SET_HUNTER_TYPE, setHunterType);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_asb, container, false);
        headView = (ASBPieceContainer) view.findViewById(R.id.armor_builder_head);
        bodyView = (ASBPieceContainer) view.findViewById(R.id.armor_builder_body);
        armsView = (ASBPieceContainer) view.findViewById(R.id.armor_builder_arms);
        waistView = (ASBPieceContainer) view.findViewById(R.id.armor_builder_waist);
        legsView = (ASBPieceContainer) view.findViewById(R.id.armor_builder_legs);
        talismanView = (ASBPieceContainer) view.findViewById(R.id.armor_builder_talisman);

        ASBSession s = ((ASBActivity) getActivity()).getASBSession();

        headView.initialize(s, 0, this);
        bodyView.initialize(s, 1, this);
        armsView.initialize(s, 2, this);
        waistView.initialize(s, 3, this);
        legsView.initialize(s, 4, this);
        talismanView.initialize(s, 5, this);

        headView.updateContents();
        bodyView.updateContents();
        armsView.updateContents();
        waistView.updateContents();
        legsView.updateContents();
        talismanView.updateContents();

        return view;
    }

    @Override
    public void onASBActivityUpdated(ASBSession s) {
        headView.updateContents();
        bodyView.updateContents();
        armsView.updateContents();
        waistView.updateContents();
        legsView.updateContents();
        talismanView.updateContents();
    }

    @Override
    public void onASBActivityUpdated(ASBSession s, int pieceIndex) {
        switch (pieceIndex) {
            case ASBSession.HEAD:
                headView.updateContents();
                break;
            case ASBSession.BODY:
                bodyView.updateContents();
                break;
            case ASBSession.ARMS:
                armsView.updateContents();
                break;
            case ASBSession.WAIST:
                waistView.updateContents();
                break;
            case ASBSession.LEGS:
                legsView.updateContents();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // We have to check to make sure that the Activity that this is being attached to is connected to the callback interface for this fragment.
        try {
            ASBActivity a = (ASBActivity) getActivity();
            a.addASBSetChangedListener(this);
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must be an ASBActivity.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((ASBActivity)getActivity()).fragmentResultReceived(requestCode, resultCode, data);
    }
}