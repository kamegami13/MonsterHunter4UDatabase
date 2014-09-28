package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.ItemToSkillTree;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.data.object.SkillTree;

/**
 * A convenience class to wrap a cursor that returns rows from the "item_to_skill_tree"
 * table. The {@link getItemToSkillTree()} method will give you a ItemToSkillTree instance
 * representing the current row.
 */
public class ItemToSkillTreeCursor extends CursorWrapper {

	public ItemToSkillTreeCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a ItemToSkillTree object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public ItemToSkillTree getItemToSkillTree() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		ItemToSkillTree itemToSkillTree = new ItemToSkillTree();
		
		long id = getLong(getColumnIndex(S.COLUMN_ITEM_TO_SKILL_TREE_ID));
		int points = getInt(getColumnIndex(S.COLUMN_ITEM_TO_SKILL_TREE_POINT_VALUE));
		
		itemToSkillTree.setId(id);
		itemToSkillTree.setPoints(points);

		Item item = new Item();
		
		long itemId = getLong(getColumnIndex(S.COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID));
		String itemName = getString(getColumnIndex("i" + S.COLUMN_ITEMS_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
			String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
			int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
//			int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
//			int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
//			int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
//			String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
			String fileLocation = getString(getColumnIndex(S.COLUMN_ITEMS_ICON_NAME));
//			String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));

		item.setId(itemId);
		item.setName(itemName);
//			item.setJpnName(jpnName);
			item.setType(type);
			item.setRarity(rarity);
//			item.setCarryCapacity(carry_capacity);
//			item.setBuy(buy);
//			item.setSell(sell);
//			item.setDescription(description);
			item.setFileLocation(fileLocation);
//			item.setArmorDupeNameFix(armor_dupe_name_fix);
		
		itemToSkillTree.setItem(item);
		
		SkillTree skillTree = new SkillTree();

		long skillTreeId = getLong(getColumnIndex(S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID));
		String skillTreeName = getString(getColumnIndex("s" + S.COLUMN_SKILL_TREES_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_SKILL_TREES_JPN_NAME));
		
		skillTree.setId(skillTreeId);
		skillTree.setName(skillTreeName);
//			skillTree.setJpnName(jpnName);
		
		itemToSkillTree.setSkillTree(skillTree);
		
		return itemToSkillTree;
	}

}