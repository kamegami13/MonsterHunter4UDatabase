package com.daviancorp.android.ui.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.widget.*;
import android.widget.ExpandableListView.OnChildClickListener;

import com.daviancorp.android.data.classes.Armor;
import com.daviancorp.android.data.classes.Item;
import com.daviancorp.android.data.classes.Rank;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.ArmorClickListener;
import com.daviancorp.android.ui.detail.ArmorDetailActivity;
import com.daviancorp.android.ui.dialog.ArmorFilterDialogFragment;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Pieced together from: Android samples:
 * com.example.android.apis.view.ExpandableList1
 * http://androidword.blogspot.com/2012/01/how-to-use-expandablelistview.html
 * http://stackoverflow.com/questions/6938560/android-fragments-setcontentview-
 * alternative
 * http://stackoverflow.com/questions/6495898/findviewbyid-in-fragment-android
 */
public class ArmorExpandableListFragment extends Fragment implements ArmorListActivity.OnRarityLimitsChangedListener {
    private static final String ARG_TYPE = "ARMOR_TYPE";

    public static final String KEY_FILTER_RANK = "FILTER_RANK";

    //	private static final String DIALOG_WISHLIST_DATA_ADD_MULTI = "wishlist_data_add_multi";
//	private static final int REQUEST_ADD_MULTI = 0;
    private static final String DIALOG_FILTER = "filter";
    private static final int REQUEST_FILTER = 0;

    private String mType;
    private ArrayList<Armor> armors;
    private String[] slots = {"Head", "Body", "Arms", "Waist", "Legs"};

    private ArrayList<ArrayList<Armor>> children;

    private ExpandableListView elv;
    private ArmorListAdapter adapter;

    private ArmorFilter filter;

    public static ArmorExpandableListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        ArmorExpandableListFragment f = new ArmorExpandableListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = null;
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getString(ARG_TYPE);
        }
        populateList();

        setHasOptionsMenu(true);

        filter = new ArmorFilter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            ((ArmorListActivity) activity).addOnRarityLimitsChangedListener(this);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the list of armors according to the filter's criteria.
     */
    private void populateList() {
        children = new ArrayList<ArrayList<Armor>>();
        armors = DataManager.get(getActivity()).queryArmorArrayType(mType);

        ArrayList<Armor> g1 = new ArrayList<Armor>();
        ArrayList<Armor> g2 = new ArrayList<Armor>();
        ArrayList<Armor> g3 = new ArrayList<Armor>();
        ArrayList<Armor> g4 = new ArrayList<Armor>();
        ArrayList<Armor> g5 = new ArrayList<Armor>();

        for (int i = 0; i < armors.size(); i++) {
            if (filter == null || (armors.get(i).getRarity() >= filter.rank.getArmorMinimumRarity() && armors.get(i).getRarity() <= filter.rank.getArmorMaximumRarity())) {
                switch (armors.get(i).getSlot()) {

                    case "Head":
                        g1.add(armors.get(i));
                        break;
                    case "Body":
                        g2.add(armors.get(i));
                        break;
                    case "Arms":
                        g3.add(armors.get(i));
                        break;
                    case "Waist":
                        g4.add(armors.get(i));
                        break;
                    case "Legs":
                        g5.add(armors.get(i));
                        break;
                    default:
                        break;
                }
            }
        }

        children.add(g1);
        children.add(g2);
        children.add(g3);
        children.add(g4);
        children.add(g5);

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_generic_expandable_list, container, false);
//		setContextMenu(v);

        elv = (ExpandableListView) v
                .findViewById(R.id.expandableListView);

        adapter = new ArmorListAdapter(slots);
        elv.setAdapter(adapter);

        elv.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1,
                                        int arg2, int arg3, long id) {

                Intent i = new Intent(getActivity(), ArmorDetailActivity.class);
                i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, (long) arg1.getTag());
                startActivity(i);

                return false;
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_armor_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_armor:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ArmorFilterDialogFragment dialog = new ArmorFilterDialogFragment();

                Bundle b = new Bundle();
                b.putSerializable(KEY_FILTER_RANK, filter.rank);

                dialog.setArguments(b);
                dialog.setTargetFragment(this, REQUEST_FILTER);
                dialog.show(fm, DIALOG_FILTER);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_FILTER:
                    Rank rank = (Rank) data.getSerializableExtra(ArmorFilterDialogFragment.EXTRA_RANK);
                    filter.rank = rank;
                    if (rank != Rank.NONE) {
                        populateList();
                    }
                    attemptUpdateOtherFragments(filter);
                    break;
            }
        }
    }

    @Override
    public void onRarityLimitsChanged(ArmorFilter filter) {
        if (this.filter != filter) { // This one's filter has not yet been updated to match the new one
            this.filter = filter;
            populateList();
        } // Otherwise, it was the fragment whose action bar set the filter
    }

    /** A helper method that attempts to update the other fragments of this fragment's activity to the new filter. */
    private void attemptUpdateOtherFragments(ArmorFilter filter) {
        try {
            ((ArmorListActivity) getActivity()).notifyRarityLimitsChangedListeners(filter);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public class ArmorListAdapter extends BaseExpandableListAdapter {

        private String[] armors;

        public ArmorListAdapter(String[] armors) {
            super();
            this.armors = armors;

        }

        @Override
        public int getGroupCount() {
            return armors.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return children.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return armors[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return children.get(i).get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view,
                                 ViewGroup viewGroup) {
//			TextView textView = new TextView(
//					ArmorExpandableListFragment.this.getActivity());
//			textView.setText(getGroup(i).toString());
//			return textView;

            View v = view;
            Context context = viewGroup.getContext();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.fragment_armor_expandablelist_group_item, viewGroup,
                    false);

            TextView armorGroupTextView = (TextView) v.findViewById(R.id.name_text);

            armorGroupTextView.setText(getGroup(i).toString());

            return v;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            View v = convertView;
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.fragment_armor_expandablelist_child_item, parent,
                    false);

            LinearLayout root = (LinearLayout) v.findViewById(R.id.root);
            TextView armorTextView = (TextView) v.findViewById(R.id.name_text);
            ImageView armorImageView = (ImageView) v.findViewById(R.id.icon);

            armorTextView.setText(getChild(groupPosition, childPosition)
                    .toString());

            String slot = ((Armor) getChild(groupPosition, childPosition))
                    .getSlot();

            String cellImage = "icons_armor/icons_"
                    + slot.toLowerCase()
                    + "/"
                    + slot.toLowerCase()
                    + ((Item) getChild(groupPosition, childPosition))
                    .getRarity() + ".png";

            Drawable armorImage = null;

            try {
                armorImage = Drawable.createFromStream(context.getAssets()
                        .open(cellImage), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            armorImageView.setImageDrawable(armorImage);

            long armorId = ((Armor) getChild(groupPosition, childPosition)).getId();

            root.setTag(armorId);
            root.setOnClickListener(new ArmorClickListener(context, armorId));

            return v;

//			TextView textView = new TextView(
//					ArmorExpandableListFragment.this.getActivity());
//			textView.setText(getChild(groupPosition, childPosition).toString());
//			return textView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }

    public static class ArmorFilter {

        public ArmorFilter() {
            rank = Rank.NONE;
        }

        Rank rank;
    }
}