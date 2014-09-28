package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.QuestReward;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.data.object.Quest;

/**
 * A convenience class to wrap a cursor that returns rows from the "quest_reward"
 * table. The {@link getQuestReward()} method will give you a QuestReward instance
 * representing the current row.
 */
public class QuestRewardCursor extends CursorWrapper {

	public QuestRewardCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a QuestReward object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public QuestReward getQuestReward() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		QuestReward questReward = new QuestReward();
		
		long id = getLong(getColumnIndex(S.COLUMN_QUEST_REWARDS_ID));
		String reward_slot = getString(getColumnIndex(S.COLUMN_QUEST_REWARDS_REWARD_SLOT));
		int percentage = getInt(getColumnIndex(S.COLUMN_QUEST_REWARDS_PERCENTAGE));
		int stack_size = getInt(getColumnIndex(S.COLUMN_QUEST_REWARDS_STACK_SIZE));
		
		questReward.setId(id);
		questReward.setRewardSlot(reward_slot);
		questReward.setPercentage(percentage);
		questReward.setStackSize(stack_size);

		Item item = new Item();
		
		long itemId = getLong(getColumnIndex(S.COLUMN_QUEST_REWARDS_ITEM_ID));
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
		
		questReward.setItem(item);
		
		Quest quest = new Quest();

		long questId = getLong(getColumnIndex(S.COLUMN_QUEST_REWARDS_QUEST_ID));
		String questName = getString(getColumnIndex("q" + S.COLUMN_QUESTS_NAME));
//			String goal = getString(getColumnIndex(S.COLUMN_QUESTS_GOAL));
		String hub = getString(getColumnIndex(S.COLUMN_QUESTS_HUB));
//			String type = getString(getColumnIndex(S.COLUMN_QUESTS_TYPE));
		String stars = getString(getColumnIndex(S.COLUMN_QUESTS_STARS));
//			int timeLimit = getInt(getColumnIndex(S.COLUMN_QUESTS_TIME_LIMIT));
//			int fee = getInt(getColumnIndex(S.COLUMN_QUESTS_FEE));
//			int reward = getInt(getColumnIndex(S.COLUMN_QUESTS_REWARD));
//			int hrp = getInt(getColumnIndex(S.COLUMN_QUESTS_HRP));

		quest.setId(questId);
		quest.setName(questName);
//			quest.setGoal(goal);
		quest.setHub(hub);
//			quest.setType(type);
		quest.setStars(stars);
//			quest.setTimeLimit(timeLimit);
//			quest.setFee(fee);
//			quest.setReward(reward);
//			quest.setHrp(hrp);

		questReward.setQuest(quest);
		
		return questReward;
	}

}
