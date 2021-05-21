package com.daviancorp.android.ui.detail;

import java.io.IOException;
import java.io.InputStream;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManager.LoaderCallbacks;
import androidx.loader.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daviancorp.android.data.classes.WyporiumTrade;
import com.daviancorp.android.loader.WyporiumTradeLoader;
import com.daviancorp.android.mh4udatabase.R;

public class ItemTradeFragment extends Fragment {
    private static final String ARG_ITEM_IN_ID = "ITEM_IN_ID";

    private WyporiumTrade mTrade;

    private View mWyporiumTradeItemOutView;
    private TextView mWyporiumTradeItemOutTextView;
    private ImageView mWyporiumTradeOutIconImageView;

    private View mWyporiumTradeItemInView;
    private TextView mWyporiumTradeItemInTextView;
    private ImageView mWyporiumTradeInIconImageView;

    private TextView mWyporiumTradeQuestNameTextView;
    private TextView mWyporiumTradeUnlockTextView;
    private TextView mWyporiumTradeRequiredTextView;
    private TextView mWyporiumTradeReceivedTextView;

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

        mWyporiumTradeItemOutView = view.findViewById(R.id.detail_wt_out_item);
        mWyporiumTradeItemOutTextView = (TextView) view.findViewById(R.id.detail_wt_out_label);
        mWyporiumTradeOutIconImageView = (ImageView) view.findViewById(R.id.detail_wt_out_image);

        mWyporiumTradeItemInView = view.findViewById(R.id.detail_wt_in_item);
        mWyporiumTradeItemInTextView = (TextView) view.findViewById(R.id.detail_wt_in_label);
        mWyporiumTradeInIconImageView = (ImageView) view.findViewById(R.id.detail_wt_in_image);

        mWyporiumTradeQuestNameTextView = (TextView) view.findViewById(R.id.detail_wt_quest);
        mWyporiumTradeRequiredTextView = (TextView) view.findViewById(R.id.detail_wt_required);
        mWyporiumTradeReceivedTextView = (TextView) view.findViewById(R.id.detail_wt_received);
        mWyporiumTradeUnlockTextView = (TextView) view.findViewById(R.id.detail_wt_unlock);

        mWyporiumTradeItemOutView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemDetailActivity.class);
                i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, (long) v.getTag());
                startActivity(i);
            }
        });

        mWyporiumTradeItemInView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ItemDetailActivity.class);
                i.putExtra(ItemDetailActivity.EXTRA_ITEM_ID, (long) v.getTag());
                startActivity(i);
            }
        });

        mWyporiumTradeQuestNameTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QuestDetailActivity.class);
                i.putExtra(QuestDetailActivity.EXTRA_QUEST_ID, (long) v.getTag());
                startActivity(i);
            }
        });

        return view;
    }

    private void updateUI() throws IOException {
        if(mTrade == null) {
            mWyporiumTradeItemOutView.setVisibility(View.GONE);
            mWyporiumTradeItemOutTextView.setVisibility(View.GONE);
            mWyporiumTradeOutIconImageView.setVisibility(View.GONE);

            mWyporiumTradeItemInView.setVisibility(View.GONE);
            mWyporiumTradeItemInTextView.setVisibility(View.GONE);
            mWyporiumTradeInIconImageView.setVisibility(View.GONE);

            mWyporiumTradeQuestNameTextView.setVisibility(View.GONE);
            mWyporiumTradeUnlockTextView.setVisibility(View.GONE);
            mWyporiumTradeRequiredTextView.setVisibility(View.GONE);
            mWyporiumTradeReceivedTextView.setVisibility(View.GONE);
            return;
        }

        String cellOutText = mTrade.getItemOutName();
        String cellInText = mTrade.getItemInName();
        String cellImageOut = "icons_items/" + mTrade.getItemOutIconName();
        String cellImageIn = "icons_items/" + mTrade.getItemInIconName();
        String cellQuestText = mTrade.getUnlockQuestName();

        mWyporiumTradeItemOutTextView.setText(cellOutText);
        mWyporiumTradeItemOutView.setTag(mTrade.getItemOutId());
        mWyporiumTradeItemInTextView.setText(cellInText);
        mWyporiumTradeItemInView.setTag(mTrade.getItemInId());
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
        try {
            open = manager.open(cellImageIn);
            Bitmap bitmap_in = BitmapFactory.decodeStream(open);
            // Assign the bitmap to an ImageView in this layout
            mWyporiumTradeInIconImageView.setImageBitmap(bitmap_in);
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
