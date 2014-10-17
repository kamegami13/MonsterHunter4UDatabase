package com.daviancorp.android.loader;

import android.content.Context;
import android.database.Cursor;

import com.daviancorp.android.data.database.DataManager;

public class ItemToSkillTreeListCursorLoader extends SQLiteCursorLoader {
	public static String FROM_ITEM = "item";
	public static String FROM_SKILL_TREE = "skillTree";

	public static String TYPE_DECORATION = "Decoration";
	public static String TYPE_HEAD = "Head";
	public static String TYPE_BODY = "Body";
	public static String TYPE_ARMS = "Arms";
	public static String TYPE_WAIST = "Waist";
	public static String TYPE_LEGS = "Legs";
	
	private String from;	// "item" or "skillTree"
	private long id; 		// Item or Quest id
	private String type;	// for SkillTree	
							// "Decoration", "Head", "Body", "Arms", "Waist", "Legs"

	public ItemToSkillTreeListCursorLoader(Context context, String from, long id, String type) {
		super(context);
		this.from = from;
		this.id = id;
		this.type = type;
	}

	@Override
	protected Cursor loadCursor() {
		if (from.equals(FROM_ITEM)) {
			// Query the list of skill trees based on item
			return DataManager.get(getContext()).queryItemToSkillTreeItem(id);
		}
		else if(from.equals(FROM_SKILL_TREE)) {
			// Query the list of items based on skill trees
			return DataManager.get(getContext()).queryItemToSkillTreeSkillTree(id, type);
		}
		else {
			return null;
		}
	}
}
