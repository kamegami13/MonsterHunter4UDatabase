package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.Component;
import com.daviancorp.android.data.object.Item;

/**
 * A convenience class to wrap a cursor that returns rows from the "component"
 * table. The {@link getComponent()} method will give you a Component instance
 * representing the current row.
 */
public class ComponentCursor extends CursorWrapper {

	public ComponentCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a Component object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Component getComponent() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		Component component = new Component();
		
		long id = getLong(getColumnIndex(S.COLUMN_COMPONENTS_ID));
		int quantity = getInt(getColumnIndex(S.COLUMN_COMPONENTS_QUANTITY));
		String ctype = getString(getColumnIndex(S.COLUMN_COMPONENTS_TYPE));
		
		component.setId(id);
		component.setQuantity(quantity);
		component.setType(ctype);

		Item created = new Item();
		
		long itemId1 = getLong(getColumnIndex(S.COLUMN_COMPONENTS_CREATED_ITEM_ID));
		String itemName1 = getString(getColumnIndex("cr" + S.COLUMN_ITEMS_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
			String type1 = getString(getColumnIndex("cr" + S.COLUMN_ITEMS_TYPE));
			int rarity1 = getInt(getColumnIndex("cr" + S.COLUMN_ITEMS_RARITY));
//			int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
//			int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
//			int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
//			String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
			String fileLocation1 = getString(getColumnIndex("cr" + S.COLUMN_ITEMS_ICON_NAME));
//			String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));

		created.setId(itemId1);
		created.setName(itemName1);
//			created.setJpnName(jpnName);
			created.setType(type1);
			created.setRarity(rarity1);
//			created.setCarryCapacity(carry_capacity);
//			created.setBuy(buy);
//			created.setSell(sell);
//			created.setDescription(description);
			created.setFileLocation(fileLocation1);
//			created.setArmorDupeNameFix(armor_dupe_name_fix);
		
		component.setCreated(created);
	
		Item comp = new Item();
		
		long itemId2 = getLong(getColumnIndex(S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID));
		String itemName2 = getString(getColumnIndex("co" + S.COLUMN_ITEMS_NAME));
//			String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
//			String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
//			int rarity2 = getInt(getColumnIndex("co" + S.COLUMN_ITEMS_RARITY));
//			int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
//			int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
//			int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
//			String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
			String fileLocation2 = getString(getColumnIndex("co" + S.COLUMN_ITEMS_ICON_NAME));
//			String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));

		comp.setId(itemId2);
		comp.setName(itemName2);
//			comp.setJpnName(jpnName);
//			comp.setType(type);
//			comp.setRarity(rarity2);
//			comp.setCarryCapacity(carry_capacity);
//			comp.setBuy(buy);
//			comp.setSell(sell);
//			comp.setDescription(description);
			comp.setFileLocation(fileLocation2);
//			comp.setArmorDupeNameFix(armor_dupe_name_fix);
		
		component.setComponent(comp);
		
		return component;
	}

}