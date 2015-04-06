package com.daviancorp.android.ui.list;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.SkillClickListener;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;

import java.util.Comparator;
import java.util.List;

public class ArmorSetBuilderSkillsListFragment extends Fragment implements ArmorSetBuilderActivity.OnArmorSetActivityUpdateListener {

    private ArmorSetBuilderSession session;
    private ArmorSetBuilderSkillsAdapter adapter;

    public static ArmorSetBuilderSkillsListFragment newInstance(ArmorSetBuilderSession session) {
        Bundle args = new Bundle();
        ArmorSetBuilderSkillsListFragment f = new ArmorSetBuilderSkillsListFragment();
        f.setArguments(args);
        f.session = session;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_armor_set_builder_skills, container, false);

        ListView listView = (ListView) v.findViewById(R.id.list);

        adapter = new ArmorSetBuilderSkillsAdapter(getActivity().getApplicationContext(), session.getSkillTreePointsSets(), session);
        listView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onArmorSetActivityUpdated(ArmorSetBuilderSession s) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ArmorSetBuilderActivity) getActivity()).addArmorSetChangedListener(this);
    }

    private class ArmorSetBuilderSkillsAdapter extends ArrayAdapter<ArmorSetBuilderSession.SkillTreePointsSet> {

        private static final int MINIMUM_SKILL_ACTIVATION_POINTS = 10;

        public ArmorSetBuilderSkillsAdapter(Context context, List<ArmorSetBuilderSession.SkillTreePointsSet> trees, ArmorSetBuilderSession session) {
            super(context, R.layout.fragment_armor_set_builder_skills_item, trees);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater.from(getContext()));
            View itemView = inflater.inflate(R.layout.fragment_armor_set_builder_skills_item, parent, false); // Conditional inflation really isn't necessary simply because of how many skills you'd have to have.

            TextView treeName = (TextView) itemView.findViewById(R.id.skill_tree_name);
            TextView headPoints = (TextView) itemView.findViewById(R.id.helmet);
            TextView bodyPoints = (TextView) itemView.findViewById(R.id.body);
            TextView armsPoints = (TextView) itemView.findViewById(R.id.arms);
            TextView waistPoints = (TextView) itemView.findViewById(R.id.waist);
            TextView legsPoints = (TextView) itemView.findViewById(R.id.legs);
            TextView talismanPoints = (TextView) itemView.findViewById(R.id.talisman);
            TextView totalPoints = (TextView) itemView.findViewById(R.id.total);

            treeName.setText(getItem(position).getSkillTree().getName());

            if (session.isEquipmentSelected(ArmorSetBuilderSession.HEAD)) {
                headPoints.setText(String.valueOf(getItem(position).getHeadPoints()));
            }

            if (session.isEquipmentSelected(ArmorSetBuilderSession.BODY)) {
                bodyPoints.setText(String.valueOf(getItem(position).getBodyPoints()));
            }

            if (session.isEquipmentSelected(ArmorSetBuilderSession.ARMS)) {
                armsPoints.setText(String.valueOf(getItem(position).getArmsPoints()));
            }

            if (session.isEquipmentSelected(ArmorSetBuilderSession.WAIST)) {
                waistPoints.setText(String.valueOf(getItem(position).getWaistPoints()));
            }

            if (session.isEquipmentSelected(ArmorSetBuilderSession.LEGS)) {
                legsPoints.setText(String.valueOf(getItem(position).getLegsPoints()));
            }

            if (session.isEquipmentSelected(ArmorSetBuilderSession.TALISMAN)) {
                talismanPoints.setText(String.valueOf(getItem(position).getTalismanPoints()));
            }

            totalPoints.setText(String.valueOf(getItem(position).getTotal()));

            if (getItem(position).getTotal() >= MINIMUM_SKILL_ACTIVATION_POINTS) {
                totalPoints.setTypeface(null, Typeface.BOLD);
            }
            
            itemView.setOnClickListener(new SkillClickListener(ArmorSetBuilderSkillsListFragment.this.getActivity(), getItem(position).getSkillTree().getId()));

            return itemView;
        }

        @Override
        public void notifyDataSetChanged() {
            setNotifyOnChange(false);
            sort(comparator);

            super.notifyDataSetChanged(); // super#notifyDataSetChanged automatically sets notifyOnChange back to true.
        }

        Comparator<ArmorSetBuilderSession.SkillTreePointsSet> comparator = new Comparator<ArmorSetBuilderSession.SkillTreePointsSet>() {
            @Override
            public int compare(ArmorSetBuilderSession.SkillTreePointsSet lhs, ArmorSetBuilderSession.SkillTreePointsSet rhs) {
                return rhs.getTotal() - lhs.getTotal();
            }
        };
    }
}
