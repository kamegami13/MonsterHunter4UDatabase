package com.daviancorp.android.ui.list;

import android.app.*;
import android.content.*;
import android.content.DialogInterface.*;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.fragment.app.ListFragment;
import androidx.loader.content.Loader;
import android.view.*;
import android.widget.*;
import com.daviancorp.android.data.classes.ASBSet;
import com.daviancorp.android.data.database.ASBSetCursor;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.loader.ASBSetListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.*;
import com.daviancorp.android.ui.dialog.ASBSetAddDialogFragment;

public class ASBSetListFragment extends ListFragment implements LoaderCallbacks<Cursor> {

    public static final String EXTRA_ASB_SET_ID = "com.daviancorp.android.ui.general.asb_set_id";
    public static final String EXTRA_ASB_SET_NAME = "com.daviancorp.android.ui.general.asb_set_name";
    public static final String EXTRA_ASB_SET_RANK = "com.daviancorp.android.ui.general.asb_set_rank";
    public static final String EXTRA_ASB_SET_HUNTER_TYPE = "com.daviancorp.android.ui.general.asb_set_hunter_type";

    public static final String DIALOG_ADD_ASB_SET = "add_asb_set";

    public static final int REQUEST_ADD_ASB_SET = 0;
    public static final int REQUEST_EDIT_ASB_SET = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getLoaderManager().initLoader(R.id.asb_set_list_fragment, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_generic_context, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_asb_set_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.asb_set_add:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ASBSetAddDialogFragment dialog = ASBSetAddDialogFragment.newInstance();
                dialog.setTargetFragment(this, REQUEST_ADD_ASB_SET);
                dialog.show(fm, DIALOG_ADD_ASB_SET);
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD_ASB_SET: {
                    String name = data.getStringExtra(EXTRA_ASB_SET_NAME);
                    int rank = data.getIntExtra(EXTRA_ASB_SET_RANK, -1);
                    int hunterType = data.getIntExtra(EXTRA_ASB_SET_HUNTER_TYPE, -1);

                    DataManager.get(getActivity()).queryAddASBSet(
                            name,
                            rank,
                            hunterType
                    );

                    updateUI();
                    break;
                }

                case REQUEST_EDIT_ASB_SET: {
                    long id = data.getLongExtra(EXTRA_ASB_SET_ID, -1);
                    String name = data.getStringExtra(EXTRA_ASB_SET_NAME);
                    int rank = data.getIntExtra(EXTRA_ASB_SET_RANK, -1);
                    int hunterType = data.getIntExtra(EXTRA_ASB_SET_HUNTER_TYPE, -1);

                    DataManager.get(getActivity()).queryUpdateASBSet(id, name, rank, hunterType);

                    updateUI();
                    break;
                }
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), ASBActivity.class);
        i.putExtra(EXTRA_ASB_SET_NAME, v.getTag().toString());
        i.putExtra(EXTRA_ASB_SET_ID, id);
        startActivity(i);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ASBSetListCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ASBSetListCursorAdapter adapter = new ASBSetListCursorAdapter(getActivity(), (ASBSetCursor) cursor);
        setListAdapter(adapter);

        final ListView l = getListView();
        l.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(l.getCheckedItemCount() + " selected");

                if (l.getCheckedItemCount() > 1) {
                    // We hide the edit button since you can only edit one set at a time
                    mode.getMenu().findItem(R.id.action_edit_data).setVisible(false);
                }
                else {
                    mode.getMenu().findItem(R.id.action_edit_data).setVisible(true);
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu_asb_list, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit_data:
                        ASBSet set = DataManager.get(getActivity()).getASBSet(l.getCheckedItemIds()[0]);

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        ASBSetAddDialogFragment dialog = ASBSetAddDialogFragment.newInstance(
                                set.getId(),
                                set.getName(),
                                set.getRank(),
                                set.getHunterType()
                        );
                        dialog.setTargetFragment(ASBSetListFragment.this, REQUEST_EDIT_ASB_SET);
                        dialog.show(fm, DIALOG_ADD_ASB_SET);
                        break;

                    case R.id.action_delete:
                        createConfirmDeleteDialog().show();
                        break;

                    case R.id.action_copy:
                        createConfirmCopyDialog().show();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) { }

            private AlertDialog.Builder createConfirmDeleteDialog() {
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                        .setPositiveButton(R.string.delete, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (long id : l.getCheckedItemIds()) {
                                    DataManager.get(getActivity()).queryDeleteASBSet(id);
                                }
                                updateUI();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);

                if (l.getCheckedItemCount() == 1) {
                    b.setMessage(getResources().getString(R.string.dialog_message_delete, DataManager.get(getActivity()).getASBSet(l.getCheckedItemIds()[0]).getName()))
                            .setTitle(R.string.asb_dialog_title_delete_set);
                }
                else {
                    b.setMessage(getResources().getString(R.string.dialog_message_delete_multi, l.getCheckedItemCount()))
                            .setTitle(R.string.asb_dialog_title_delete_set_multi);
                }

                return b;
            }

            private AlertDialog.Builder createConfirmCopyDialog() {
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                        .setPositiveButton(R.string.copy, new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (long id : l.getCheckedItemIds()) {
                                    DataManager.get(getActivity()).queryAddASBSet(id);
                                }
                                updateUI();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);

                if (l.getCheckedItemCount() == 1) {
                    b.setMessage(getResources().getString(R.string.dialog_message_copy, DataManager.get(getActivity()).getASBSet(l.getCheckedItemIds()[0]).getName()))
                            .setTitle(R.string.asb_dialog_title_copy_set);
                }
                else {
                    b.setMessage(getResources().getString(R.string.dialog_message_delete_multi, l.getCheckedItemCount()))
                            .setTitle(R.string.asb_dialog_title_copy_set_multi);
                }

                return b;
            }
        });

        l.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Stop using the cursor (via the adapter)
        setListAdapter(null);
    }

    private void updateUI() {
        getLoaderManager().getLoader(R.id.asb_set_list_fragment).forceLoad();
        ASBSetListCursorAdapter adapter = (ASBSetListCursorAdapter) getListAdapter();
        adapter.notifyDataSetChanged();
    }

    private class ASBSetListCursorAdapter extends CursorAdapter {

        private ASBSetCursor cursor;

        public ASBSetListCursorAdapter(Context context, ASBSetCursor cursor) {
            super(context, cursor, 0);

            this.cursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.fragment_asb_sets_list_item, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final ASBSet set = this.cursor.getASBSet();

            TextView textView = (TextView) view.findViewById(R.id.name_text);
            textView.setText(set.getName());

            TextView propertiesText = (TextView) view.findViewById(R.id.properties_text);
            String rank = context.getResources().getStringArray(R.array.rank)[set.getRank()] + " Rank";
            String hunterType = context.getResources().getStringArray(R.array.hunter_type)[set.getHunterType()];

            propertiesText.setText(rank + ", " + hunterType);

            view.setTag(set.getName());
        }
    }
}
