package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.MogaWoodsReward;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.data.object.Monster;

/**
 * A convenience class to wrap a cursor that returns rows from the "moga_woods_reward"
 * table. The {@link getMogaWoodsReward()} method will give you a MogaWoodsReward instance
 * representing the current row.
 */
public class MogaWoodsRewardCursor extends CursorWrapper {

	public MogaWoodsRewardCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a MogaWoodsReward object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public MogaWoodsReward getMogaWoodsReward() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		MogaWoodsReward mogaWoodsReward = new MogaWoodsReward();
		
		long id = getLong(getColumnIndex(S.COLUMN_MOGA_WOODS_REWARDS_ID));
		String time = getString(getColumnIndex(S.COLUMN_MOGA_WOODS_REWARDS_TIME));
		int commodity_stars = getInt(getColumnIndex(S.COLUMN_MOGA_WOODS_REWARDS_COMMODITY_STARS));
		int kill_percentage = getInt(getColumnIndex(S.COLUMN_MOGA_WOODS_REWARDS_KILL_PERCENTAGE));
		int capture_percentage = getInt(getColumnIndex(S.COLUMN_MOGA_WOODS_REWARDS_CAPTURE_PERCENTAGE));
		
		mogaWoodsReward.setId(id);
		mogaWoodsReward.setTime(time);
		mogaWoodsReward.setCommodityStars(commodity_stars);
		mogaWoodsReward.setKillPercentage(kill_percentage);
		mogaWoodsReward.setCapturePercentage(capture_percentage);

		Item item = new Item();
		
		long itemId = getLong(getColumnIndex(S.COLUMN_MOGA_WOODS_REWARDS_ITEM_ID));
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
		
		mogaWoodsReward.setItem(item);

		Monster monster = new Monster();

		long monsterId = getLong(getColumnIndex(S.COLUMN_MOGA_WOODS_REWARDS_MONSTER_ID));
		String monsterName = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_NAME));
//			String monsterClass = getString(getColumnIndex(S.COLUMN_MONSTERS_CLASS));
//			String trait = getString(getColumnIndex(S.COLUMN_MONSTERS_TRAIT));
			String file_location2 = getString(getColumnIndex("m" + S.COLUMN_MONSTERS_FILE_LOCATION)); 
		
		monster.setId(monsterId);
		monster.setName(monsterName);
//			monster.setMonsterClass(monsterClass);
//			monster.setTrait(trait);
		monster.setFileLocation(file_location2); 
		
		mogaWoodsReward.setMonster(monster);
		
		return mogaWoodsReward;
	}

}