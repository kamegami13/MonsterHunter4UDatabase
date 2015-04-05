package com.daviancorp.android.ui.compound;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daviancorp.android.data.classes.SkillTree;
import com.daviancorp.android.data.database.DataManager;
import com.daviancorp.android.mh4udatabase.R;
import com.daviancorp.android.ui.detail.ArmorSetBuilderActivity;
import com.daviancorp.android.ui.list.SkillTreeListActivity;

public class ArmorSetBuilderTalismanSkillContainer extends LinearLayout {

    private static final int TALISMAN_SKILL_POINTS_MIN = -10;
    private static final int TALISMAN_SKILL_POINTS_MAX = 14;

    private Fragment parent;

    private int skillIndex;

    private SkillTree skillTree;

    private TextView skillTreeText;
    private EditText skillPointsText;
    private ImageView selectSkillButton;

    private ChangeListener changeListener;

    public ArmorSetBuilderTalismanSkillContainer(Context context, AttributeSet attrs) {

        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TalismanSkill, 0, 0);

        skillIndex = a.getInteger(R.styleable.TalismanSkill_skillNumber, 0);
        String labelText = "Skill " + skillIndex;

        a.recycle();

        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_armor_set_builder_talisman_skill, this, true);

        TextView label = (TextView) findViewById(R.id.label_skill);
        label.setText(labelText);

        skillTreeText = (TextView) findViewById(R.id.skill_name);

        selectSkillButton = (ImageView) findViewById(R.id.select_skill_button);
        selectSkillButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSkillButtonClicked();
            }
        });

        skillPointsText = (EditText) findViewById(R.id.skill_points);
        skillPointsText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    clampSkillPointsField(skillPointsText);
                }
            }
        });
        skillPointsText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                changeListener.onTalismanSkillPointsChanged();
            }
        });

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        selectSkillButton.setEnabled(enabled);

        if (!enabled) {
            skillPointsText.setText("");
            skillPointsText.clearFocus();
        }
    }

    public SkillTree getSkillTree() {
        return skillTree;
    }

    public void setSkillTree(SkillTree skillTree) {
        this.skillTree = skillTree;

        if (skillTree != null) {
            skillTreeText.setText(skillTree.getName());
            skillPointsText.setEnabled(true);
            selectSkillButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));
        }
        else {
            skillTreeText.setText("");
            skillPointsText.clearFocus();
            skillPointsText.setEnabled(false);
            selectSkillButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
        }

        if (changeListener != null) {
            changeListener.onTalismanSkillChanged();
        }
    }

    public void setSkillTree(long id) {
        SkillTree s = DataManager.get(getContext()).getSkillTree(id);
        setSkillTree(s);
    }

    public String getSkillPoints() {
        return skillPointsText.getText().toString();
    }

    public void setSkillPoints(int skillPoints) {
        skillPointsText.setText(String.valueOf(skillPoints));
    }

    public void setParent(Fragment parent) {
        this.parent = parent;
    }

    public void setChangeListener(ChangeListener l) {
        changeListener = l;
    }

    public boolean skillPointsIsValid() {
        return !getSkillPoints().equals("") &&
                !getSkillPoints().equals("-") &&
                Integer.parseInt(getSkillPoints()) <= TALISMAN_SKILL_POINTS_MAX &&
                Integer.parseInt(getSkillPoints()) >= TALISMAN_SKILL_POINTS_MIN;
    }

    /** Called when the user clicks the button next to the skill tree. */
    private void onSkillButtonClicked() {
        if (skillTree == null) {
            Intent i = new Intent(getContext(), SkillTreeListActivity.class);

            i.putExtra(ArmorSetBuilderActivity.EXTRA_FROM_TALISMAN_EDITOR, true);
            i.putExtra(ArmorSetBuilderActivity.EXTRA_TALISMAN_SKILL_INDEX, skillIndex - 1);

            parent.startActivityForResult(i, ArmorSetBuilderActivity.REQUEST_CODE_CREATE_TALISMAN);
        }
        else {
            setSkillTree(null);
        }
    }

    private void clampSkillPointsField(TextView v) {
        if (v.getText().toString().equals("") || v.getText().toString().equals("-") || v.getText().toString().equals("0")) {
            v.setText("1");
        }
        else if (Integer.parseInt(v.getText().toString()) > TALISMAN_SKILL_POINTS_MAX) {
            v.setText(String.valueOf(TALISMAN_SKILL_POINTS_MAX));
        }
        else if (Integer.parseInt(v.getText().toString()) < TALISMAN_SKILL_POINTS_MIN) {
            v.setText(String.valueOf(TALISMAN_SKILL_POINTS_MIN));
        }

        changeListener.onTalismanSkillPointsChanged();
    }

    /** An interface allowing the talisman skill container to communicate with other talisman skill containers around it. */
    public static interface ChangeListener {

        public void onTalismanSkillChanged();

        public void onTalismanSkillPointsChanged();

    }
}
