package com.daviancorp.android.ui.list;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Decoration;
import com.daviancorp.android.data.database.DecorationCursor;
import com.daviancorp.android.loader.DecorationListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.DecorationClickListener;
import com.daviancorp.android.ui.detail.ASBActivity;

import java.io.IOException;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DecorationListFragment extends ListFragment implements
        LoaderCallbacks<Cursor> {

//	private static final String DIALOG_WISHLIST_DATA_ADD_MULTI = "wishlist_data_add_multi";
//	private static final int REQUEST_ADD_MULTI = 0;

    private String mFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.decoration_list_fragment, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generic_list_search, container, false);

        EditText inputSearch = (EditText) v.findViewById(R.id.input_search);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mFilter = cs.toString();
                DecorationListFragment parent = DecorationListFragment.this;
                getLoaderManager().restartLoader(R.id.decoration_list_fragment, null, parent);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) { }
        });

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // You only ever load the runs, so assume this is the case
        return new DecorationListCursorLoader(getActivity(), mFilter);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Create an adapter to point at this cursor
        DecorationListCursorAdapter adapter = new DecorationListCursorAdapter(
                getActivity(), (DecorationCursor) cursor);
        setListAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Stop using the cursor (via the adapter)
        setListAdapter(null);
    }


    private static class DecorationListCursorAdapter extends CursorAdapter {

        private DecorationCursor mDecorationCursor;

        private Activity activity;
        private boolean fromAsb;
        private int maxSlots;

        public DecorationListCursorAdapter(Context context,
                                           DecorationCursor cursor) {
            super(context, cursor, 0);
            mDecorationCursor = cursor;

            // ASB stuff
            if (context instanceof Activity) {
                activity = (Activity) context;
                Intent intent = ((Activity) context).getIntent();
                if (intent.getBooleanExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, false)) {
                    fromAsb = true;
                    maxSlots = intent.getIntExtra(ASBActivity.EXTRA_DECORATION_MAX_SLOTS, 3);
                }
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.fragment_decoration_listitem,
                    parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the decoration for the current row
            Decoration decoration = mDecorationCursor.getDecoration();

            RelativeLayout itemLayout = (RelativeLayout) view.findViewById(R.id.listitem);

            // Set up the text view
            ImageView itemImageView = (ImageView) view.findViewById(R.id.item_image);
            TextView decorationNameTextView = (TextView) view.findViewById(R.id.item);
            TextView skill1TextView = (TextView) view.findViewById(R.id.skill1);
            TextView skill1amtTextView = (TextView) view.findViewById(R.id.skill1_amt);
            TextView skill2TextView = (TextView) view.findViewById(R.id.skill2);
            TextView skill2amtTextView = (TextView) view.findViewById(R.id.skill2_amt);

            String decorationNameText = decoration.getName();
            String skill1Text = decoration.getSkill1Name();
            String skill1amtText = "" + decoration.getSkill1Point();
            String skill2Text = decoration.getSkill2Name();
            String skill2amtText = "";
            if (decoration.getSkill2Point() != 0) {
                skill2amtText = skill2amtText + decoration.getSkill2Point();
            }

            Drawable i = null;
            String cellImage = "icons_items/" + decoration.getFileLocation();
            try {
                i = Drawable.createFromStream(
                        context.getAssets().open(cellImage), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            itemImageView.setImageDrawable(i);

            decorationNameTextView.setText(decorationNameText);
            skill1TextView.setText(skill1Text);
            skill1amtTextView.setText(skill1amtText);

            skill2TextView.setVisibility(View.GONE);
            skill2amtTextView.setVisibility(View.GONE);

            if (!skill2amtText.equals("")) {
                skill2TextView.setText(skill2Text);
                skill2amtTextView.setText(skill2amtText);
                skill2TextView.setVisibility(View.VISIBLE);
                skill2amtTextView.setVisibility(View.VISIBLE);
            }

            itemLayout.setTag(decoration.getId());

            if (fromAsb) {
                boolean fitsInArmor = (decoration.getNumSlots() <= maxSlots);
                view.setEnabled(fitsInArmor);
                itemLayout.setOnClickListener(new DecorationClickListener(context, decoration.getId(), true, activity));
            }
            else {
                itemLayout.setOnClickListener(new DecorationClickListener(context, decoration.getId()));
            }
        }
    }
}
