package com.daviancorp.android.ui.list;

import android.annotation.SuppressLint;
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
import com.daviancorp.android.data.classes.ASBSession;
import com.daviancorp.android.data.classes.ASBSession.*;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.ClickListeners.SkillClickListener;
import com.daviancorp.android.ui.detail.ASBActivity;

import java.util.Comparator;
import java.util.List;

public class ASBSkillsListFragment extends Fragment implements ASBActivity.OnASBSetActivityUpdateListener {

    private ASBSession session;
    private ASBSkillsAdapter adapter;

    public static ASBSkillsListFragment newInstance() {
        Bundle args = new Bundle();
        ASBSkillsListFragment f = new ASBSkillsListFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_asb_skills_list, container, false);

        ListView listView = (ListView) v.findViewById(R.id.list);

        adapter = new ASBSkillsAdapter(getActivity().getApplicationContext(), session.getSkillTreesInSet(), session);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onASBActivityUpdated(ASBSession s) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ASBActivity a = (ASBActivity) getActivity();
        a.addASBSetChangedListener(this);
        session = a.getASBSession();
    }

    private static class ASBSkillsAdapter extends ArrayAdapter<ASBSession.SkillTreeInSet> {

        private static final int MINIMUM_SKILL_ACTIVATION_POINTS = 10;

        ASBSession session;
        List<SkillTreeInSet> trees;

        public ASBSkillsAdapter(Context context, List<ASBSession.SkillTreeInSet> trees, ASBSession session) {
            super(context, R.layout.fragment_asb_skills_listitem, trees);
            this.session = session;
            this.trees = trees;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            @SuppressLint("ViewHolder")
            View itemView = inflater.inflate(R.layout.fragment_asb_skills_listitem, parent, false);
            // Conditional inflation really isn't necessary simply because of how many skills you'd have to have.

            TextView treeName = (TextView) itemView.findViewById(R.id.skill_tree_name);
            TextView headPoints = (TextView) itemView.findViewById(R.id.helmet);
            TextView bodyPoints = (TextView) itemView.findViewById(R.id.body);
            TextView armsPoints = (TextView) itemView.findViewById(R.id.arms);
            TextView waistPoints = (TextView) itemView.findViewById(R.id.waist);
            TextView legsPoints = (TextView) itemView.findViewById(R.id.legs);
            TextView talismanPoints = (TextView) itemView.findViewById(R.id.talisman);
            TextView totalPoints = (TextView) itemView.findViewById(R.id.total);

            treeName.setText(getItem(position).getSkillTree().getName());

            if (session.isEquipmentSelected(ASBSession.HEAD) && getItem(position).getPoints(ASBSession.HEAD) != 0) {
                headPoints.setText(String.valueOf(getItem(position).getPoints(ASBSession.HEAD)));
            }

            if (session.isEquipmentSelected(ASBSession.BODY) && getItem(position).getPoints(ASBSession.BODY, trees) != 0) { // NOTICE: We have to call the alternate getPoints method due to the possibility of Torso Up pieces.
                bodyPoints.setText(String.valueOf(getItem(position).getPoints(ASBSession.BODY, trees)));
            }

            if (session.isEquipmentSelected(ASBSession.ARMS) && getItem(position).getPoints(ASBSession.ARMS) != 0) {
                armsPoints.setText(String.valueOf(getItem(position).getPoints(ASBSession.ARMS)));
            }

            if (session.isEquipmentSelected(ASBSession.WAIST) && getItem(position).getPoints(ASBSession.WAIST) != 0) {
                waistPoints.setText(String.valueOf(getItem(position).getPoints(ASBSession.WAIST)));
            }

            if (session.isEquipmentSelected(ASBSession.LEGS) && getItem(position).getPoints(ASBSession.LEGS) != 0) {
                legsPoints.setText(String.valueOf(getItem(position).getPoints(ASBSession.LEGS)));
            }

            if (session.isEquipmentSelected(ASBSession.TALISMAN) && getItem(position).getPoints(ASBSession.TALISMAN) != 0) {
                talismanPoints.setText(String.valueOf(getItem(position).getPoints(ASBSession.TALISMAN)));
            }

            totalPoints.setText(String.valueOf(getItem(position).getTotal(trees)));

            if (getItem(position).getTotal(trees) >= MINIMUM_SKILL_ACTIVATION_POINTS) {
                totalPoints.setTypeface(null, Typeface.BOLD);
            }
            
            itemView.setOnClickListener(new SkillClickListener(parent.getContext(), getItem(position).getSkillTree().getId()));

            return itemView;
        }

        @Override
        public void notifyDataSetChanged() {
            setNotifyOnChange(false);
            sort(comparator);

            super.notifyDataSetChanged(); // super#notifyDataSetChanged automatically sets notifyOnChange back to true.
        }

        Comparator<ASBSession.SkillTreeInSet> comparator = new Comparator<ASBSession.SkillTreeInSet>() {
            @Override
            public int compare(ASBSession.SkillTreeInSet lhs, ASBSession.SkillTreeInSet rhs) {
                return rhs.getTotal(trees) - lhs.getTotal(trees);
            }
        };
    }
}
