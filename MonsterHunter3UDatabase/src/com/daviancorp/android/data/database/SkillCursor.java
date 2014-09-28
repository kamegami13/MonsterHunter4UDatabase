package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.Skill;

/**
 * A convenience class to wrap a cursor that returns rows from the "skill"
 * table. The {@link getSkill()} method will give you a Skill instance
 * representing the current row.
 */
public class SkillCursor extends CursorWrapper {

	public SkillCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a Skill object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Skill getSkill() {
		if (isBeforeFirst() || isAfterLast())
			return null;
		
		Skill skill = new Skill();
		
		long id = getLong(getColumnIndex(S.COLUMN_SKILLS_ID));
		int required_points = getInt(getColumnIndex(S.COLUMN_SKILLS_REQUIRED_SKILL_TREE_POINTS));
		String name = getString(getColumnIndex(S.COLUMN_SKILLS_NAME));
		String jpn_name = getString(getColumnIndex(S.COLUMN_SKILLS_JPN_NAME));
		String description = getString(getColumnIndex(S.COLUMN_SKILLS_DESCRIPTION));
		
		skill.setId(id);
		skill.setRequiredPoints(required_points);
		skill.setName(name);
		skill.setJpnName(jpn_name);
		skill.setDescription(description);

		return skill;
	}
}