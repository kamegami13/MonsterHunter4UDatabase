package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.ArenaReward;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.data.object.ArenaQuest;

/**
 * A convenience class to wrap a cursor that returns rows from the "arena_reward"
 * table. The {@link getArenaReward()} method will give you a ArenaReward instance
 * representing the current row.
 */
public class ArenaRewardCursor extends CursorWrapper {

	public ArenaRewardCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a ArenaReward object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public ArenaReward getArenaReward() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		ArenaReward arenaReward = new ArenaReward();
		
		long id = getLong(getColumnIndex(S.COLUMN_ARENA_REWARDS_ID));
		int percentage = getInt(getColumnIndex(S.COLUMN_ARENA_REWARDS_PERCENTAGE));
		int stack_size = getInt(getColumnIndex(S.COLUMN_ARENA_REWARDS_STACK_SIZE));
		
		arenaReward.setId(id);
		arenaReward.setPercentage(percentage);
		arenaReward.setStackSize(stack_size);

		Item item = new Item();
		
		long itemId = getLong(getColumnIndex(S.COLUMN_ARENA_REWARDS_ITEM_ID));
		String itemName = getString(getColumnIndex("i" + S.COLUMN_ITEMS_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
//			String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
//			int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
//			int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
//			int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
//			int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
//			String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
			String fileLocation = getString(getColumnIndex(S.COLUMN_ITEMS_ICON_NAME));
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
			item.setFileLocation(fileLocation);
//			item.setArmorDupeNameFix(armor_dupe_name_fix);
		
		arenaReward.setItem(item);
		
		ArenaQuest arenaQuest = new ArenaQuest();
		long arenaId = getLong(getColumnIndex(S.COLUMN_ARENA_REWARDS_ARENA_ID));
		String arenaName = getString(getColumnIndex("a" + S.COLUMN_ARENA_QUESTS_NAME));
		arenaQuest.setId(arenaId);
		arenaQuest.setName(arenaName);

		arenaReward.setArenaQuest(arenaQuest);
		
		return arenaReward;
	}

}
