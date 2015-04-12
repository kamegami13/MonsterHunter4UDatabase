package com.daviancorp.android.ui.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
import android.view.*;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.data.database.ASBSetCursor;
import com.daviancorp.android.loader.ASBSetListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ASBActivity;
import com.daviancorp.android.ui.dialog.ASBSetAddDialogFragment;

public class ASBSetListFragment extends ListFragment implements LoaderCallbacks<Cursor> {

    public static final String EXTRA_ASB_SET_ID = "com.daviancorp.android.ui.general.asb_set_id";

    public static final String DIALOG_ADD_ASB_SET = "add_asb_set";

    public static final int REQUEST_ADD_ASB_SET = 420;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getLoaderManager().initLoader(R.id.asb_set_list_fragment, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_generic, container, false);
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
                ASBSetAddDialogFragment dialog = new ASBSetAddDialogFragment();
                dialog.setTargetFragment(this, REQUEST_ADD_ASB_SET);
                dialog.show(fm, DIALOG_ADD_ASB_SET);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_ADD_ASB_SET) {
                if (data.getBooleanExtra(ASBSetAddDialogFragment.EXTRA_ADD, false)) {
                    updateUI();
                }
            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), ASBActivity.class);
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

    private static class ASBSetListCursorAdapter extends CursorAdapter {

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
            ASBSession session = this.cursor.getASBSet(context);

            TextView textView = (TextView) view.findViewById(R.id.name_text);
            textView.setText(session.getName());

            TextView propertiesText = (TextView) view.findViewById(R.id.properties_text);
            String rank = context.getResources().getStringArray(R.array.rank)[session.getRank()] + " Rank";
            String hunterType = context.getResources().getStringArray(R.array.hunter_type)[session.getHunterType()];

            propertiesText.setText(rank + ", " + hunterType);
        }
    }
}
