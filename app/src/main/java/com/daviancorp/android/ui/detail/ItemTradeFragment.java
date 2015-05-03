package com.daviancorp.android.ui.detail;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.WyporiumTrade;
import com.daviancorp.android.loader.WyporiumTradeLoader;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.ItemClickListener;

import org.w3c.dom.Text;

public class ItemTradeFragment extends Fragment {
    private static final String ARG_ITEM_IN_ID = "ITEM_IN_ID";

    private WyporiumTrade mTrade;

    private TextView mWyporiumTradeItemOutTextView;
    private ImageView mWyporiumTradeOutIconImageView;
    private TextView mWyporiumTradeQuestNameTextView;
    private View mWyporiumTradeItemOutView;
    private TextView mWyporiumTradeUnlockTextView;
    private TextView mWyporiumTradeRequiredTextView;

    public static ItemTradeFragment newInstance(long itemInId) {
        Bundle args = new Bundle();
        args.putLong(ARG_ITEM_IN_ID, itemInId);
        ItemTradeFragment f = new ItemTradeFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        // Check for a Item ID as an argument, and find the item
        Bundle args = getArguments();
        if (args != null) {
            long itemInId = args.getLong(ARG_ITEM_IN_ID, -1);
            if (itemInId != -1) {
                LoaderManager lm = getLoaderManager();
                lm.initLoader(R.id.wyporium_trade_detail_fragment, args,
                        new WyporiumTradeLoaderCallbacks());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wyporiumtrade_detail,
                container, false);

        mWyporiumTradeItemOutTextView = (TextView) view.findViewById(R.id.detail_wt_out_label);
        mWyporiumTradeOutIconImageView = (ImageView) view.findViewById(R.id.detail_wt_out_image);
        mWyporiumTradeQuestNameTextView = (TextView) view.findViewById(R.id.detail_wt_quest);
        mWyporiumTradeItemOutView = view.findViewById(R.id.detail_wt_out_item);
        mWyporiumTradeUnlockTextView = (TextView) view.findViewById(R.id.detail_wt_required);
        mWyporiumTradeRequiredTextView = (TextView) view.findViewById(R.id.detail_wt_unlock);

        mWyporiumTradeItemOutView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // The id argument will be the Monster ID; CursorAdapter gives us this
                // for free
                Intent i = new Intent(getActivity(), ItemDetailActivity.class);
                i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, (long) v.getTag());
                startActivity(i);
            }
        });

        mWyporiumTradeQuestNameTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // The id argument will be the Monster ID; CursorAdapter gives us this
                // for free
                Intent i = new Intent(getActivity(), QuestDetailActivity.class);
                i.putExtra(QuestDetailActivity.EXTRA_QUEST_ID, (long) v.getTag());
                startActivity(i);
            }
        });

        return view;
    }

    private void updateUI() throws IOException {
        if(mTrade == null) {
            mWyporiumTradeItemOutTextView.setVisibility(View.GONE);
            mWyporiumTradeOutIconImageView.setVisibility(View.GONE);
            mWyporiumTradeQuestNameTextView.setVisibility(View.GONE);
            mWyporiumTradeItemOutView.setVisibility(View.GONE);
            mWyporiumTradeUnlockTextView.setVisibility(View.GONE);
            mWyporiumTradeRequiredTextView.setVisibility(View.GONE);
            return;
        }

        String cellOutText = mTrade.getItemOutName();
        String cellInText = mTrade.getItemInName();
        String cellImageOut = "icons_items/" + mTrade.getItemOutIconName();
        String cellImageIn = "icons_items/" + mTrade.getItemInIconName();
        String cellQuestText = mTrade.getUnlockQuestName();

        mWyporiumTradeItemOutTextView.setText(cellOutText);
        mWyporiumTradeItemOutView.setTag(mTrade.getItemOutId());
        mWyporiumTradeQuestNameTextView.setText(cellQuestText);
        mWyporiumTradeQuestNameTextView.setTag(mTrade.getUnlockQuestId());


        // Read a Bitmap from Assets
        AssetManager manager = getActivity().getAssets();
        InputStream open = null;

        try {
            open = manager.open(cellImageOut);
            Bitmap bitmap_out = BitmapFactory.decodeStream(open);
            // Assign the bitmap to an ImageView in this layout
            mWyporiumTradeOutIconImageView.setImageBitmap(bitmap_out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                open.close();
            }
        }
    }

    private class WyporiumTradeLoaderCallbacks implements
            LoaderCallbacks<WyporiumTrade> {

        @Override
        public Loader<WyporiumTrade> onCreateLoader(int id, Bundle args) {
            return new WyporiumTradeLoader(getActivity(),
                    args.getLong(ARG_ITEM_IN_ID));
        }

        @Override
        public void onLoadFinished(Loader<WyporiumTrade> loader, WyporiumTrade run) {
            mTrade = run;
            try {
                updateUI();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onLoaderReset(Loader<WyporiumTrade> loader) {
            // Do nothing
        }
    }
}
