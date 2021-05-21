package com.daviancorp.android.ui.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.FragmentManager;
import android.view.*;

import com.daviancorp.android.data.classes.Armor;
import com.daviancorp.android.data.classes.Item;
import com.daviancorp.android.data.classes.Rank;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.ArmorClickListener;
import com.daviancorp.android.ui.detail.ASBActivity;
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
public class ArmorExpandableListFragment extends Fragment {
    
    private static final String ARG_TYPE = "ARMOR_TYPE";

    public static final String KEY_FILTER_RANK = "FILTER_RANK";
    public static final String KEY_FILTER_SLOTS = "FILTER_SLOTS";
    public static final String KEY_FILTER_SLOTS_SPECIFICATION = "FILTER_SLOTS_SPEC";

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

        filter = new ArmorFilter();

        if (getActivity().getIntent().getBooleanExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, false)) {
            filter.setRank(Rank.values()[getActivity().getIntent().getIntExtra(ASBActivity.EXTRA_SET_RANK, -1)]);
        }

        populateList();

        setHasOptionsMenu(true);
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
            if (filter == null || filter.armorPassesFilter(armors.get(i))) {
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

//        elv.setOnChildClickListener(new OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView arg0, View arg1,
//                                        int arg2, int arg3, long id) {
//
//                Intent i = new Intent(getActivity(), ArmorDetailActivity.class);
//                i.putExtra(ArmorDetailActivity.EXTRA_ARMOR_ID, (long) arg1.getTag());
//
//                startActivity(i);
//
//                return false;
//            }
//        });

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
                b.putSerializable(KEY_FILTER_RANK, filter.getRank());
                b.putInt(KEY_FILTER_SLOTS, filter.getSlots());
                b.putSerializable(KEY_FILTER_SLOTS_SPECIFICATION, filter.getSlotsSpecification());

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
                    filter.setRank(rank);

                    int slots = data.getIntExtra(ArmorFilterDialogFragment.EXTRA_SLOTS, -1);
                    filter.setSlots(slots);

                    ArmorFilterDialogFragment.FilterSpecification slotsSpecificiation = (ArmorFilterDialogFragment.FilterSpecification) data.getSerializableExtra(ArmorFilterDialogFragment.EXTRA_SLOTS_SPEC);
                    filter.setSlotsSpecification(slotsSpecificiation);

                    populateList();

                    break;
            }
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

            if (getActivity().getIntent().getBooleanExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, false)) {
                int piece = getActivity().getIntent().getIntExtra(ASBActivity.EXTRA_PIECE_INDEX, -1);

                if (piece != -1) {
                    elv.setDividerHeight(0);
                    if (i != piece) {
                        v = new FrameLayout(context); // We hide the group if it's not the type of armor we're looking for
                    }
                    else {
                        elv.expandGroup(i);
                    }
                }
            }

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

            if (getActivity().getIntent().getBooleanExtra(ASBActivity.EXTRA_FROM_SET_BUILDER, false)) {
                root.setOnClickListener(new ArmorClickListener(context, armorId, getActivity(), ASBActivity.REQUEST_CODE_ADD_PIECE));
            }
            else {
                root.setOnClickListener(new ArmorClickListener(context, armorId));
            }

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
            rank = null;
            slots = -1;
            slotsSpecification = null;
        }

        private Rank rank;
        private int slots;
        private ArmorFilterDialogFragment.FilterSpecification slotsSpecification;

        public Rank getRank() {
            return rank;
        }

        public void setRank(Rank rank) {
            this.rank = rank;
        }

        public int getSlots() {
            return slots;
        }

        public void setSlots(int slots) {
            this.slots = slots;
        }

        public ArmorFilterDialogFragment.FilterSpecification getSlotsSpecification() {
            return slotsSpecification;
        }

        public void setSlotsSpecification(ArmorFilterDialogFragment.FilterSpecification slotsSpecification) {
            this.slotsSpecification = slotsSpecification;
        }

        public boolean armorPassesFilter(Armor armor) {
            boolean passes = true;
            if (rank != null) {
                passes = armor.getRarity() <= rank.getArmorMaximumRarity() && armor.getRarity() >= rank.getArmorMinimumRarity();
            }

            if (passes && slots != -1) {
                passes = armor.getNumSlots() >= slots;
            }

            if (passes && slotsSpecification != null) {
                passes = slotsSpecification.qualifies(armor.getNumSlots(), slots);
            }

            return passes;
        }
    }
}