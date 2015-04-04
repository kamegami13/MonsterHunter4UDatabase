package com.daviancorp.android.ui.dialog;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.compound.ArmorSetBuilderTalismanSkillContainer;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.detail.SkillTreeDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ArmorSetBuilderTalismanDialogFragment extends DialogFragment {

    private ArmorSetBuilderTalismanSkillContainer[] skillTreeViews;
    private int talismanTypeIndex;

    public static ArmorSetBuilderTalismanDialogFragment newInstance() {
        ArmorSetBuilderTalismanDialogFragment f = new ArmorSetBuilderTalismanDialogFragment();
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View addView = inflater.inflate(R.layout.dialog_armor_set_builder_edit_talisman, null);

        skillTreeViews = new ArmorSetBuilderTalismanSkillContainer[2];

        skillTreeViews[0] = (ArmorSetBuilderTalismanSkillContainer) addView.findViewById(R.id.skill_1_view);
        skillTreeViews[0].setContainer(this);

        skillTreeViews[1] = (ArmorSetBuilderTalismanSkillContainer) addView.findViewById(R.id.skill_2_view);
        skillTreeViews[1].setContainer(this);

        initializeTypeSpinner(addView);

        Dialog d = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.armor_set_builder_talisman_dialog_title)
                .setView(addView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (skillTreeViews[0].hasSkillDefined()) {

                            Intent i = new Intent();

                            long skill1Id = skillTreeViews[0].getSkillTree().getId();
                            int skill1Points = skillTreeViews[0].getSkillPoints();

                            i.putExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_SKILL_TREE_1, skill1Id);
                            i.putExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_SKILL_POINTS_1, skill1Points);
                            i.putExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_TYPE_INDEX, talismanTypeIndex);

                            if (skillTreeViews[1].hasSkillDefined()) {
                                Log.d("SetBuilder", "Skill 2 is defined.");

                                long skill2Id = skillTreeViews[1].getSkillTree().getId();
                                int skill2Points = skillTreeViews[1].getSkillPoints();

                                i.putExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_SKILL_TREE_2, skill2Id);
                                i.putExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_SKILL_POINTS_2, skill2Points);
                            }

                            getTargetFragment().onActivityResult(ArmorSetBuilderActivity.REQUEST_CODE_CREATE_TALISMAN, Activity.RESULT_OK, i);
                        }
                    }
                })
                .create();

        return d;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ArmorSetBuilderActivity.REQUEST_CODE_CREATE_TALISMAN) {

            int talismanSkillNumber = data.getIntExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_SKILL_INDEX, -1);

            switch(talismanSkillNumber) {
                case 0:
                    skillTreeViews[0].getPointsField().setEnabled(true);
                    break;
                case 1:
                    skillTreeViews[1].getPointsField().setEnabled(true);
                    break;
                default:
                    Log.e("SetBuilder", "Attempted to change an out-of-bounds skill on a talisman.");
                    break;
            }

            long skillTreeId = data.getLongExtra(SkillTreeDetailActivity.EXTRA_SKILLTREE_ID, -1);

            skillTreeViews[talismanSkillNumber].setSkillTree(DataManager.get(getActivity()).getSkillTree(skillTreeId));
        }
    }

    /** Helper method that performs initialization logic on the type of talisman spinner. */
    private void initializeTypeSpinner(View view) {
        List<String> talismanNames = new ArrayList<>();

        for (String s : getResources().getStringArray(R.array.talisman_names)) {
            String name = s.split(",")[0];
            talismanNames.add(name);
        }

        Spinner spinner = (Spinner) view.findViewById(R.id.talisman_rank_spinner);
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, talismanNames));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                talismanTypeIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner.setSelection(0);

    }
}
