package com.daviancorp.android.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.daviancorp.android.data.classes.Rank;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.list.ArmorExpandableListFragment;

public class ArmorFilterDialogFragment extends DialogFragment {

    public static final String EXTRA_RANK = "com.daviancorp.android.ui.detail.Rank";

    private void sendResult(int resultCode, Rank rank) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_RANK, rank);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View addView = inflater.inflate(R.layout.dialog_armor_filter, null);

        Rank rank = (Rank) getArguments().getSerializable(ArmorExpandableListFragment.KEY_FILTER_RANK);

        final Spinner spinner = (Spinner) addView.findViewById(R.id.rank_spinner);
        spinner.setAdapter(new ArrayAdapter<Rank>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, Rank.values()));

        if (rank != null) {
            spinner.setSelection(rank.ordinal());
        }

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_armor_filter_title)
                .setView(addView)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        sendResult(Activity.RESULT_OK, (Rank) spinner.getSelectedItem());
                    }
                })
                .create();
    }
}
