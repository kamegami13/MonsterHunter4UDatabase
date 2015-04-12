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
import android.widget.EditText;
import android.widget.Spinner;
import com.daviancorp.android.data.classes.HunterType;
import com.daviancorp.android.data.classes.Rank;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;

public class ASBSetAddDialogFragment extends DialogFragment {

    public static final String EXTRA_ADD = "com.daviancorp.android.ui.general.asb_set_add";

    private void sendResult(int resultCode, boolean add) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_ADD, add);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View addView = inflater.inflate(R.layout.dialog_asb_set_add, null);
        final EditText nameInput = (EditText) addView.findViewById(R.id.name_text);

        final Spinner rankSpinner = (Spinner) addView.findViewById(R.id.spinner_rank);
        rankSpinner.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.rank, R.layout.view_spinner_item));
        ((ArrayAdapter) rankSpinner.getAdapter()).setDropDownViewResource(R.layout.view_spinner_dropdown_item);

        final Spinner hunterTypeSpinner = (Spinner) addView.findViewById(R.id.spinner_hunter_type);
        hunterTypeSpinner.setAdapter(ArrayAdapter.createFromResource(getActivity(), R.array.hunter_type, R.layout.view_spinner_item));
        ((ArrayAdapter) hunterTypeSpinner.getAdapter()).setDropDownViewResource(R.layout.view_spinner_dropdown_item);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.asb_option_set_add)
                .setView(addView)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = nameInput.getText().toString();
                        DataManager.get(getActivity()).queryAddASBSet(
                                name,
                                rankSpinner.getSelectedItemPosition(),
                                hunterTypeSpinner.getSelectedItemPosition()
                        );

                        sendResult(Activity.RESULT_OK, true);
                    }
                })
                .create();
    }
}
