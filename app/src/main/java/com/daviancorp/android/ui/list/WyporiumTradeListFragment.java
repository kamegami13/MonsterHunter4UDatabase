package com.daviancorp.android.ui.list;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;
import androidx.cursoradapter.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.WyporiumTrade;
import com.daviancorp.android.data.database.WyporiumTradeCursor;
import com.daviancorp.android.loader.WyporiumTradeListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.WyporiumTradeClickListener;

import java.io.IOException;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WyporiumTradeListFragment extends ListFragment implements
        LoaderCallbacks<Cursor> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(false);
        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.wyporium_trade_list_fragment, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_generic_list, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // You only ever load the runs, so assume this is the case
        return new WyporiumTradeListCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Create an adapter to point at this cursor
        WyporiumTradeListCursorAdapter adapter = new WyporiumTradeListCursorAdapter(
                getActivity(), (WyporiumTradeCursor) cursor);
        setListAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Stop using the cursor (via the adapter)
        setListAdapter(null);
    }


    private static class WyporiumTradeListCursorAdapter extends CursorAdapter {

        private WyporiumTradeCursor mWyporiumTradeCursor;

        public WyporiumTradeListCursorAdapter(Context context,
                                           WyporiumTradeCursor cursor) {
            super(context, cursor, 0);
            mWyporiumTradeCursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.fragment_wyporiumtrade_listitem,
                    parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the trade for the current row
            WyporiumTrade wyporiumTrade = mWyporiumTradeCursor.getWyporiumTrade();

            RelativeLayout itemLayout = (RelativeLayout) view.findViewById(R.id.listitem);

            // Set up the text view
            ImageView itemInImageView = (ImageView) view.findViewById(R.id.wt_item_in_image);
            TextView itemInNameTextView = (TextView) view.findViewById(R.id.wt_item_in_name);
            ImageView itemOutImageView = (ImageView) view.findViewById(R.id.wt_item_out_image);
            TextView itemOutNameTextView = (TextView) view.findViewById(R.id.wt_item_out_name);

            // Clickable layouts
            LinearLayout btnItemIn = (LinearLayout) view.findViewById(R.id.btn_in);
            LinearLayout btnItemOut = (LinearLayout) view.findViewById(R.id.btn_out);

            String itemInNameText = wyporiumTrade.getItemInName();
            String itemOutNameText = wyporiumTrade.getItemOutName();

            Drawable i = null;
            String cellImageIn = "icons_items/" + wyporiumTrade.getItemInIconName();
            String cellImageOut = "icons_items/" + wyporiumTrade.getItemOutIconName();
            try {
                i = Drawable.createFromStream(
                        context.getAssets().open(cellImageIn), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            itemInImageView.setImageDrawable(i);
            i = null;

            try {
                i = Drawable.createFromStream(
                        context.getAssets().open(cellImageOut), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            itemOutImageView.setImageDrawable(i);

            // Set text
            itemInNameTextView.setText(itemInNameText);
            itemOutNameTextView.setText(itemOutNameText);

            // Set up clickthroughs
            btnItemIn.setTag(wyporiumTrade.getId());
            btnItemIn.setOnClickListener(new WyporiumTradeClickListener(context, wyporiumTrade.getItemInId()));
            btnItemOut.setTag(wyporiumTrade.getId());
            btnItemOut.setOnClickListener(new WyporiumTradeClickListener(context, wyporiumTrade.getItemOutId()));

        }
    }
}
