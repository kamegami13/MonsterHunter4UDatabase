package com.daviancorp.android.ui.list;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.daviancorp.android.data.classes.ArmorSetBuilderSession;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;

import java.util.List;

public class ArmorSetBuilderSkillsListFragment extends Fragment implements ArmorSetBuilderActivity.ArmorSetChangedListener {

    private ListView listView;

    public static ArmorSetBuilderSkillsListFragment newInstance() {
        Bundle args = new Bundle();
        ArmorSetBuilderSkillsListFragment f = new ArmorSetBuilderSkillsListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_armor_set_builder_skills, container, false);
        listView = (ListView) v.findViewById(R.id.list);
        return v;
    }

    @Override
    public void updateContents(ArmorSetBuilderSession s) {
        ArmorSetBuilderSkillsAdapter adapter = new ArmorSetBuilderSkillsAdapter(getActivity().getApplicationContext(), s.generateSkillTreePoints(getActivity().getApplicationContext()));
        listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((ArmorSetBuilderActivity) getActivity()).setOnArmorSetChangedSkillListener(this);
    }

    private static class ArmorSetBuilderSkillsAdapter extends ArrayAdapter<ArmorSetBuilderSession.ArmorSetSkillTreePoints> {
        
        public ArmorSetBuilderSkillsAdapter(Context context, List<ArmorSetBuilderSession.ArmorSetSkillTreePoints> trees) {
            super(context, R.layout.fragment_armor_set_builder_skills_item, trees);
            skills = trees;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater.from(getContext()));
            View itemView = inflater.inflate(R.layout.fragment_armor_set_builder_skills_item, parent, false);

            TextView treeName = (TextView) itemView.findViewById(R.id.skill_tree_name);
            TextView headPoints = (TextView) itemView.findViewById(R.id.helmet);
            TextView bodyPoints = (TextView) itemView.findViewById(R.id.body);
            TextView armsPoints = (TextView) itemView.findViewById(R.id.arms);
            TextView waistPoints = (TextView) itemView.findViewById(R.id.waist);
            TextView legsPoints = (TextView) itemView.findViewById(R.id.legs);

            treeName.setText(getItem(position).getSkillTree().getName());
            headPoints.setText(getItem(position).getHeadPoints());
            bodyPoints.setText(getItem(position).getBodyPoints());
            armsPoints.setText(getItem(position).getArmsPoints());
            waistPoints.setText(getItem(position).getWaistPoints());
            legsPoints.setText(getItem(position).getLegsPoints());

            return itemView;
        }
    }
}
