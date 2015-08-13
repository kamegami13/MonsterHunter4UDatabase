package com.daviancorp.android.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daviancorp.android.data.classes.ASBTalisman;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.compound.ASBTalismanSkillContainer;
import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.detail.SkillTreeDetailActivity;

public class ASBTalismanDialogFragment extends DialogFragment implements ASBTalismanSkillContainer.ChangeListener {

    private static final String ARG_TYPE_INDEX = "type_index";
    private static final String ARG_SLOTS = "slots";
    private static final String ARG_SKILL_1_ID = "skill_1_id";
    private static final String ARG_SKILL_1_POINTS = "skill_1_points";
    private static final String ARG_SKILL_2_ID = "skill_2_id";
    private static final String ARG_SKILL_2_POINTS = "skill_2_points";

    private ASBTalismanSkillContainer[] talismanSkillContainers;

    public static ASBTalismanDialogFragment newInstance() {
        ASBTalismanDialogFragment f = new ASBTalismanDialogFragment();
        return f;
    }

    /**
     * Used when creating a talisman dialog for a talisman that has already been created.
     */
    public static ASBTalismanDialogFragment newInstance(ASBTalisman talisman) {
        ASBTalismanDialogFragment f = new ASBTalismanDialogFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_TYPE_INDEX, talisman.getTypeIndex());
        args.putInt(ARG_SLOTS, talisman.getNumSlots());
        args.putLong(ARG_SKILL_1_ID, talisman.getSkill1().getId());
        args.putInt(ARG_SKILL_1_POINTS, talisman.getSkill1Points());

        if (talisman.getSkill2() != null) {
            args.putLong(ARG_SKILL_2_ID, talisman.getSkill2().getId());
            args.putInt(ARG_SKILL_2_POINTS, talisman.getSkill2Points());
        } else {
            args.putLong(ARG_SKILL_2_ID, -1);
        }

        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View addView = inflater.inflate(R.layout.dialog_asb_edit_talisman, null);

        talismanSkillContainers = new ASBTalismanSkillContainer[2];
        talismanSkillContainers[0] = (ASBTalismanSkillContainer) addView.findViewById(R.id.skill_1_view);
        talismanSkillContainers[1] = (ASBTalismanSkillContainer) addView.findViewById(R.id.skill_2_view);
        for (ASBTalismanSkillContainer c : talismanSkillContainers) {
            c.setParent(this);
            c.setChangeListener(this);
        }

        final Spinner typeSpinner = initializeTypeSpinner(addView);
        final Spinner slotsSpinner = initializeSlotsSpinner(addView);

        if (getArguments() != null) { // If the talisman is already defined, we initialize it here.
            typeSpinner.setSelection(getArguments().getInt(ARG_TYPE_INDEX));
            slotsSpinner.setSelection(getArguments().getInt(ARG_SLOTS));
            talismanSkillContainers[0].setSkillTree(getArguments().getLong(ARG_SKILL_1_ID));
            talismanSkillContainers[0].setSkillPoints(getArguments().getInt(ARG_SKILL_1_POINTS));

            if (getArguments().getLong(ARG_SKILL_2_ID) != -1) {
                talismanSkillContainers[1].setSkillTree(getArguments().getLong(ARG_SKILL_2_ID));
                talismanSkillContainers[1].setSkillPoints(getArguments().getInt(ARG_SKILL_2_POINTS));
            }
        }

        updateSkillEnabledStates();

        Dialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.asb_dialog_talisman_title)
                .setView(addView)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (talismanSkillContainers[0].getSkillTree() != null) {

                            Intent i = new Intent();

                            long skill1Id = talismanSkillContainers[0].getSkillTree().getId();
                            int skill1Points = Integer.parseInt(talismanSkillContainers[0].getSkillPoints());

                            i.putExtra(ASBActivity.EXTRA_TALISMAN_TYPE_INDEX,
                                    typeSpinner.getSelectedItemPosition());
                            i.putExtra(ASBActivity.EXTRA_TALISMAN_SLOTS,
                                    slotsSpinner.getSelectedItemPosition());
                            i.putExtra(ASBActivity.EXTRA_TALISMAN_SKILL_TREE_1, skill1Id);
                            i.putExtra(ASBActivity.EXTRA_TALISMAN_SKILL_POINTS_1, skill1Points);

                            if (talismanSkillContainers[1].getSkillTree() != null) {
                                Log.d("SetBuilder", "Skill 2 is defined.");

                                long skill2Id = talismanSkillContainers[1].getSkillTree().getId();
                                int skill2Points = Integer.parseInt(talismanSkillContainers[1].getSkillPoints());

                                i.putExtra(ASBActivity.EXTRA_TALISMAN_SKILL_TREE_2, skill2Id);
                                i.putExtra(ASBActivity.EXTRA_TALISMAN_SKILL_POINTS_2, skill2Points);
                            }

                            getTargetFragment().onActivityResult(ASBActivity.REQUEST_CODE_CREATE_TALISMAN,
                                    Activity.RESULT_OK, i);
                        }
                    }
                })
                .create();
        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateOkButtonState(); // At first, there is no data in the dialog, but there may be if the user is choosing to edit
            }
        });

        return d;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ASBActivity.REQUEST_CODE_CREATE_TALISMAN) {

            int talismanSkillNumber = data.getIntExtra(ASBActivity.EXTRA_TALISMAN_SKILL_INDEX, -1);
            long skillTreeId = data.getLongExtra(SkillTreeDetailActivity.EXTRA_SKILLTREE_ID, -1);

            talismanSkillContainers[talismanSkillNumber].setSkillTree(DataManager.get(getActivity()).getSkillTree(skillTreeId));
            talismanSkillContainers[talismanSkillNumber].requestFocus();
        }
    }

    @Override
    public void onTalismanSkillChanged() {
        updateSkillEnabledStates();
        updateOkButtonState();
    }

    @Override
    public void onTalismanSkillPointsChanged() {
        if (getDialog() != null) {
            updateOkButtonState();
        }
    }

    /**
     * Updates the enabled status of the second skill tree based on the first.
     */
    private void updateSkillEnabledStates() {
        if (talismanSkillContainers[0].getSkillTree() != null) {
            talismanSkillContainers[1].setEnabled(true);
        } else {
            if (talismanSkillContainers[1].getSkillTree() != null) {
                talismanSkillContainers[1].setSkillTree(null);
            }
            talismanSkillContainers[1].setEnabled(false);
        }
    }

    /**
     * Checks to see that all necessary data is defined before the user attempts to submit their talisman.
     */
    private void updateOkButtonState() {
        AlertDialog d = (AlertDialog) getDialog();

        if (d != null) {
            if (talismanSkillContainers[0].getSkillTree() == null ||
                    !talismanSkillContainers[0].skillPointsIsValid()) {
                d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                return;
            } else if (talismanSkillContainers[1].getSkillTree() != null) {
                if (!talismanSkillContainers[1].skillPointsIsValid() ||
                        talismanSkillContainers[0].getSkillTree().getId() == talismanSkillContainers[1].getSkillTree().getId()) {
                    d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    return;
                }
            }

            d.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        }
    }

    /**
     * Helper method that performs initialization logic on the "type of talisman" spinner.
     */
    private Spinner initializeTypeSpinner(View view) {
        List<String> talismanNames = new ArrayList<>();

        for (String s : getResources().getStringArray(R.array.talisman_names)) {
            String name = s.split(",")[0];
            talismanNames.add(name);
        }

        Spinner spinner = (Spinner) view.findViewById(R.id.talisman_rank_spinner);
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                talismanNames));

        return spinner;
    }

    private Spinner initializeSlotsSpinner(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.talisman_slots_spinner);
        spinner.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.slot_values,
                android.R.layout.simple_spinner_dropdown_item));
        ((ArrayAdapter) spinner.getAdapter()).setDropDownViewResource(R.layout.view_spinner_dropdown_item);

        return spinner;
    }
}
