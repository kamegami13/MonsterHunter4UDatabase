package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.daviancorp.android.data.classes.Rank;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.list.ArmorExpandableListFragment;

public class ArmorFilterDialogFragment extends DialogFragment {

    public static final String EXTRA_RANK = "com.daviancorp.android.ui.detail.Rank";
    public static final String EXTRA_SLOTS = "com.daviancorp.android.ui.detail.Slots";
    public static final String EXTRA_SLOTS_SPEC = "com.daviancorp.android.ui.detail.SlotsSpec";

    private void sendResult(int resultCode, Rank rank, int slots, FilterSpecification slotsSpec) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_RANK, rank);
        i.putExtra(EXTRA_SLOTS, slots);
        i.putExtra(EXTRA_SLOTS_SPEC, slotsSpec);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addView = inflater.inflate(R.layout.dialog_armor_filter, null);

        ////// INITIALIZING RANK ///////

        Rank rank = (Rank) getArguments().getSerializable(ArmorExpandableListFragment.KEY_FILTER_RANK);

        final Spinner rankSpinner = (Spinner) addView.findViewById(R.id.filter_spinner_rank);
        rankSpinner.setAdapter(new ArrayAdapter<Rank>(getActivity().getApplicationContext(), R.layout.view_spinner_item, Rank.values()));

        final CheckBox rankCheckBox = (CheckBox) addView.findViewById(R.id.filter_checkbox_rank);

        rankCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rankSpinner.setEnabled(true);
                    rankSpinner.getBackground().setColorFilter(null);
                } else {
                    rankSpinner.setEnabled(false);
                    rankSpinner.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        if (rank == null) {
            rankSpinner.setEnabled(false);
            rankCheckBox.setChecked(false);
        }
        else {
            rankSpinner.setEnabled(true);
            rankSpinner.setSelection(rank.ordinal());
            rankCheckBox.setChecked(true);
        }

        ////// INITIALIZING SLOTS //////

        int[] slotValuesInt = getResources().getIntArray(R.array.slot_values);
        Integer[] slotValues = new Integer[4];
        for (int i = 0; i < slotValuesInt.length; i++) {
            slotValues[i] = slotValuesInt[i];
        }

        int slots = getArguments().getInt(ArmorExpandableListFragment.KEY_FILTER_SLOTS, -1);
        FilterSpecification slotsSpecification = (FilterSpecification) getArguments().getSerializable(ArmorExpandableListFragment.KEY_FILTER_SLOTS_SPECIFICATION);

        final Spinner slotsSpinner = (Spinner) addView.findViewById(R.id.filter_spinner_slots);
        slotsSpinner.setAdapter(new ArrayAdapter<Integer>(getActivity().getApplicationContext(), R.layout.view_spinner_item, slotValues));

        final Spinner slotsSpecSpinner = (Spinner) addView.findViewById(R.id.filter_spinner_slots_spec);
        slotsSpecSpinner.setAdapter(ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.filter_restriction, R.layout.view_spinner_item));

        final CheckBox slotsCheckBox = (CheckBox) addView.findViewById(R.id.filter_checkbox_slots);

        slotsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    slotsSpinner.setEnabled(true);
                    slotsSpecSpinner.setEnabled(true);
                } else {
                    slotsSpinner.setEnabled(false);
                    slotsSpecSpinner.setEnabled(false);
                }
            }
        });

        if (slots == -1) {
            slotsSpinner.setEnabled(false);
            slotsCheckBox.setChecked(false);
            slotsSpecSpinner.setEnabled(false);
        }
        else {
            slotsSpinner.setEnabled(true);
            slotsSpinner.setSelection(slots);
            slotsCheckBox.setChecked(true);
        }

        if (slotsSpecification != null) {
            slotsSpecSpinner.setSelection(slotsSpecification.ordinal());
        }

        ////// CREATING THE DIALOG //////

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_armor_filter_title)
                .setView(addView)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Rank sendRank = rankSpinner.isEnabled() ? (Rank) rankSpinner.getSelectedItem() : null;
                        int sendSlots = slotsSpinner.isEnabled() ? slotsSpinner.getSelectedItemPosition() : -1;
                        FilterSpecification sendSlotsSpec = slotsSpecSpinner.isEnabled() ? FilterSpecification.values()[slotsSpecSpinner.getSelectedItemPosition()] : null;

                        sendResult(Activity.RESULT_OK, sendRank, sendSlots, sendSlotsSpec);
                    }
                })
                .create();
    }

    public static enum FilterSpecification {
        MINIMUM {
            @Override
            public boolean qualifies(int value, int test) {
                return value >= test;
            }
        },

        EXACTLY {
            @Override
            public boolean qualifies(int value, int test) {
                return value == test;
            }
        },

        MAXIMUM {
            @Override
            public boolean qualifies(int value, int test) {
                return value <= test;
            }
        };

        /**
         * Evaluates whether or not the value meets the filter's specification.
         * @param value The input value, typically an armor's stat.
         * @param test The value against which to test the input, typically a fixed value.
         * @return True if the input value passes the {@code FilterSpecification}'s test, otherwise false.
         */
        public abstract boolean qualifies(int value, int test);
    }
}
