package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.HuntingReward;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.data.object.Monster;

/**
 * A convenience class to wrap a cursor that returns rows from the "hunting_reward"
 * table. The {@link getHuntingReward()} method will give you a HuntingReward instance
 * representing the current row.
 */
public class HuntingRewardCursor extends CursorWrapper {

	public HuntingRewardCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a HuntingReward object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public HuntingReward getHuntingReward() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		HuntingReward huntingReward = new HuntingReward();
		
		long id = getLong(getColumnIndex(S.COLUMN_HUNTING_REWARDS_ID));
		String condition = getString(getColumnIndex(S.COLUMN_HUNTING_REWARDS_CONDITION));
		
		String rank = getString(getColumnIndex(S.COLUMN_HUNTING_REWARDS_RANK));
		int stack_size = getInt(getColumnIndex(S.COLUMN_HUNTING_REWARDS_STACK_SIZE));
		int percentage = getInt(getColumnIndex(S.COLUMN_HUNTING_REWARDS_PERCENTAGE));
		
		huntingReward.setId(id);
		huntingReward.setCondition(condition);
		huntingReward.setRank(rank);
		huntingReward.setStackSize(stack_size);
		huntingReward.setPercentage(percentage);

		Item item = new Item();
		
		long itemId = getLong(getColumnIndex(S.COLUMN_HUNTING_REWARDS_ITEM_ID));
		String itemName = getString(getColumnIndex("i" + S.COLUMN_ITEMS_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
//			String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
//			int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
//			int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
//			int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
//			int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
//			String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
			String fileLocation1 = getString(getColumnIndex("i" + S.COLUMN_ITEMS_ICON_NAME));
//			String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));

		item.setId(itemId);
		item.setName(itemName);
//			item.setJpnName(jpnName);
//			item.setType(type);
//			item.setRarity(rarity);
//			item.setCarryCapacity(carry_capacity);
//			item.setBuy(buy);
//			item.setSell(sell);
//			item.setDescription(description);
			item.setFileLocation(fileLocation1);
//			item.setArmorDupeNameFix(armor_dupe_name_fix);
		
		huntingReward.setItem(item);
		
		Monster monster = new Monster();

		long monsterId = getLong(getColumnIndex(S.COLUMN_HUNTING_REWARDS_MONSTER_ID));
		String monsterName = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_NAME));
//			String monsterClass = getString(getColumnIndex(S.COLUMN_MONSTERS_CLASS));
		String trait = getString(getColumnIndex(S.COLUMN_MONSTERS_TRAIT));
			String file_location2 = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_FILE_LOCATION)); 
		
		monster.setId(monsterId);
		monster.setName(monsterName);
//			monster.setMonsterClass(monsterClass);
		monster.setTrait(trait);
			monster.setFileLocation(file_location2); 
		
		huntingReward.setMonster(monster);
		
		return huntingReward;
	}

}