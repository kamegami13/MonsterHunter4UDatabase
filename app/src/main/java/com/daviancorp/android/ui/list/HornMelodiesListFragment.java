package com.daviancorp.android.ui.list;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daviancorp.android.data.classes.Melody;
import com.daviancorp.android.data.database.HornMelodiesCursor;
import com.daviancorp.android.loader.MelodyListCursorLoader;
import com.daviancorp.android.mh4udatabase.R;

import java.io.IOException;
import java.io.InputStream;

public class HornMelodiesAdapter extends Adapter implements
        LoaderCallbacks<Cursor> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the loader to load the list of runs
        getLoaderManager().initLoader(R.id.horn_melodies_list, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // You only ever load the runs, so assume this is the case
        // GET NOTES FROM ARGS BUNDLE
        return new MelodyListCursorLoader(getActivity(), "WBR");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Create an adapter to point at this cursor
        HornMelodiesCursorAdapter adapter = new HornMelodiesCursorAdapter(
                getActivity(), (HornMelodiesCursor) cursor);
        setListAdapter(adapter); // need to create HornMelodiesCursorAdapter

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Stop using the cursor (via the adapter)
        setListAdapter(null);
    }

    private static class HornMelodiesCursorAdapter extends CursorAdapter {

        private HornMelodiesCursor mHornMelodiesCursor;

        public HornMelodiesCursorAdapter(Context context,
                                          HornMelodiesCursor cursor) {
            super(context, cursor, 0);
            mHornMelodiesCursor = cursor;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // Use a layout inflater to get a row view
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.fragment_horn_melody_listitem,
                    parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Get the Melody for the current row
            Melody melody = mHornMelodiesCursor.getMelody();
            RelativeLayout itemLayout = (RelativeLayout) view.findViewById(R.id.melody_listitem);

            // Get assignable TextViews
            TextView effect1TextView = (TextView) view.findViewById(R.id.effect1);
            TextView effect2TextView = (TextView) view.findViewById(R.id.effect2);
            TextView durationTextView = (TextView) view.findViewById(R.id.duration);
            TextView extensionTextView = (TextView) view.findViewById(R.id.extension);

            // Get assignable ImageViews
            ImageView note1ImageView = (ImageView) view.findViewById(R.id.horn_note1);
            ImageView note2ImageView = (ImageView) view.findViewById(R.id.horn_note2);
            ImageView note3ImageView = (ImageView) view.findViewById(R.id.horn_note3);
            ImageView note4ImageView = (ImageView) view.findViewById(R.id.horn_note4);

            // Assign Effect 1
            String cellText = melody.getEffect1();
            effect1TextView.setText(cellText);

            // Assign Effect 2
            cellText = melody.getEffect2();
            effect2TextView.setText(cellText);

            // Assign Duration
            cellText = melody.getDuration();
            durationTextView.setText(cellText);

            // Assign Extension
            cellText = melody.getExtension();
            extensionTextView.setText(cellText);

            // Get string version of song
            String song = melody.getSong();

            // Read a Bitmap from Assets
            AssetManager manager = getActivity().getAssets();
            InputStream open = null;
            Bitmap bitmap = null;
            try {
                // Note 1
                if(song.length()>=1) {
                    open = manager.open(getNoteImage(song.charAt(0)));
                    bitmap = BitmapFactory.decodeStream(open);
                    note1ImageView.setImageBitmap(Bitmap.createScaledBitmap(
                            bitmap, 50, 50, false));
                }
                // Note 2
                if(song.length()>=2) {
                    open = manager.open(getNoteImage(song.charAt(1)));
                    bitmap = BitmapFactory.decodeStream(open);
                    note2ImageView.setImageBitmap(Bitmap.createScaledBitmap(
                            bitmap, 50, 50, false));
                }
                // Note 3
                if(song.length()>=3) {
                    open = manager.open(getNoteImage(song.charAt(2)));
                    bitmap = BitmapFactory.decodeStream(open);
                    note3ImageView.setImageBitmap(Bitmap.createScaledBitmap(
                            bitmap, 50, 50, false));
                }
                // Note 4
                if(song.length()>=4) {
                    open = manager.open(getNoteImage(song.charAt(3)));
                    bitmap = BitmapFactory.decodeStream(open);
                    note4ImageView.setImageBitmap(Bitmap.createScaledBitmap(
                            bitmap, 50, 50, false));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (open != null) {
                    // Close input stream
                    try{open.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

        }

        private String getNoteImage(char note) {
            String file = "icons_monster_info/";

            switch (note) {
                case 'B':
                    return file + "Note.blue.png";
                case 'C':
                    return file + "Note.aqua.png";
                case 'G':
                    return file + "Note.green.png";
                case 'O':
                    return file + "Note.orange.png";
                case 'P':
                    return file + "Note.purple.png";
                case 'R':
                    return file + "Note.red.png";
                case 'W':
                    return file + "Note.white.png";
                case 'Y':
                    return file + "Note.yellow.png";
            }
            return "";
        }
    }

}
