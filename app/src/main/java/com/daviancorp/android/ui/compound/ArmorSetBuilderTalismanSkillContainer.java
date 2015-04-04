package com.daviancorp.android.ui.compound;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daviancorp.android.data.classes.SkillTree;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.list.SkillTreeListActivity;

public class ArmorSetBuilderTalismanSkillContainer extends LinearLayout {

    private Fragment container;

    private int skillNumber;

    private SkillTree skillTree;

    private TextView skillTreeText;
    private EditText skillTreePointsText;

    public ArmorSetBuilderTalismanSkillContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TalismanSkill, 0, 0);

        skillNumber = a.getInteger(R.styleable.TalismanSkill_skillNumber, 0);
        String labelText = "Skill " + skillNumber;

        a.recycle();

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_armor_set_builder_talisman_skill, this, true);

        TextView label = (TextView) getChildAt(0);
        label.setText(labelText);

        skillTreeText = (TextView) getChildAt(1);

        ImageView selectSkillButton = (ImageView) getChildAt(2);
        selectSkillButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), SkillTreeListActivity.class);

                i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_TALISMAN_EDITOR, true);
                i.putExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_SKILL_INDEX, skillNumber - 1);

                container.startActivityForResult(i, ArmorSetBuilderActivity.REQUEST_CODE_CREATE_TALISMAN);
            }
        });

        skillTreePointsText = (EditText) getChildAt(3);
    }

    public EditText getPointsField() {
        return skillTreePointsText;
    }

    public SkillTree getSkillTree() {
        return skillTree;
    }

    public void setSkillTree(SkillTree skillTree) {
        this.skillTree = skillTree;
        skillTreeText.setText(skillTree.getName());
    }

    public int getSkillPoints() {
        return Integer.parseInt(skillTreePointsText.getText().toString());
    }

    public void setSkillPoints(int skillPoints) {
        skillTreePointsText.setText(skillPoints);
    }

    public boolean hasSkillDefined() {
        return skillTree != null;
    }

    public void setContainer(Fragment container) {
        this.container = container;
    }
}
