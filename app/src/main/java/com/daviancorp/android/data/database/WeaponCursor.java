package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.classes.Weapon;

/**
 * A convenience class to wrap a cursor that returns rows from the "weapon"
 * table. The {@link getWeapon()} method will give you a Weapon instance
 * representing the current row.
 */
public class WeaponCursor extends CursorWrapper {

	public WeaponCursor(Cursor c) {
		super(c);
	}

	/**
	 * Returns a Weapon object configured for the current row, or null if the
	 * current row is invalid.
	 */
	public Weapon getWeapon() {
		if (isBeforeFirst() || isAfterLast())
			return null;

		Weapon weapon = new Weapon();

		String wtype = getString(getColumnIndex(S.COLUMN_WEAPONS_WTYPE));
		int creation_cost = getInt(getColumnIndex(S.COLUMN_WEAPONS_CREATION_COST));
		int upgrade_cost = getInt(getColumnIndex(S.COLUMN_WEAPONS_UPGRADE_COST));
		int attack = getInt(getColumnIndex(S.COLUMN_WEAPONS_ATTACK));
		int max_attack = getInt(getColumnIndex(S.COLUMN_WEAPONS_MAX_ATTACK));
		String element = getString(getColumnIndex(S.COLUMN_WEAPONS_ELEMENT));
		String awaken = getString(getColumnIndex(S.COLUMN_WEAPONS_AWAKEN));
        String element_2 = getString(getColumnIndex(S.COLUMN_WEAPONS_ELEMENT_2));
        long element_2_attack = getLong(getColumnIndex(S.COLUMN_WEAPONS_ELEMENT_2_ATTACK));
        long element_attack = getLong(getColumnIndex(S.COLUMN_WEAPONS_ELEMENT_ATTACK));
        long awaken_attack = getLong(getColumnIndex(S.COLUMN_WEAPONS_AWAKEN_ATTACK));
		int defense = getInt(getColumnIndex(S.COLUMN_WEAPONS_DEFENSE));
		String sharpness = getString(getColumnIndex(S.COLUMN_WEAPONS_SHARPNESS));
		String affinity = getString(getColumnIndex(S.COLUMN_WEAPONS_AFFINITY));
		String horn_notes = getString(getColumnIndex(S.COLUMN_WEAPONS_HORN_NOTES));
		String shelling_type = getString(getColumnIndex(S.COLUMN_WEAPONS_SHELLING_TYPE));
		String phial = getString(getColumnIndex(S.COLUMN_WEAPONS_PHIAL));
		String charges = getString(getColumnIndex(S.COLUMN_WEAPONS_CHARGES));
		String coatings = getString(getColumnIndex(S.COLUMN_WEAPONS_COATINGS));
		String recoil = getString(getColumnIndex(S.COLUMN_WEAPONS_RECOIL));
		String reload_speed = getString(getColumnIndex(S.COLUMN_WEAPONS_RELOAD_SPEED));
		String rapid_fire = getString(getColumnIndex(S.COLUMN_WEAPONS_RAPID_FIRE));
		String deviation = getString(getColumnIndex(S.COLUMN_WEAPONS_DEVIATION));
		String ammo = getString(getColumnIndex(S.COLUMN_WEAPONS_AMMO));
		int num_slots = getInt(getColumnIndex(S.COLUMN_WEAPONS_NUM_SLOTS));
		int wfinal = getInt(getColumnIndex(S.COLUMN_WEAPONS_FINAL));
        int tree_depth = getInt(getColumnIndex(S.COLUMN_WEAPONS_TREE_DEPTH));
        int parent_id = getInt(getColumnIndex(S.COLUMN_WEAPONS_PARENT_ID));

		weapon.setWtype(wtype);
		weapon.setCreationCost(creation_cost);
		weapon.setUpgradeCost(upgrade_cost);
		weapon.setAttack(attack);
		weapon.setMaxAttack(max_attack);
		weapon.setElement(element);
		weapon.setAwaken(awaken);
        weapon.setElement2(element_2);
        weapon.setElementAttack(element_attack);
        weapon.setElement2Attack(element_2_attack);
        weapon.setAwakenAttack(awaken_attack);
		weapon.setDefense(defense);
		weapon.setSharpness(sharpness);
		weapon.setAffinity(affinity);
		weapon.setHornNotes(horn_notes);
		weapon.setShellingType(shelling_type);
		weapon.setPhial(phial);
        if(weapon.getWtype().equals("Bow")) {
            weapon.setCharges(charges);
            weapon.setCoatings(coatings);
        }
		weapon.setRecoil(recoil);
		weapon.setReloadSpeed(reload_speed);
		weapon.setRapidFire(rapid_fire);
		weapon.setDeviation(deviation);
		weapon.setAmmo(ammo);
		weapon.setNumSlots(num_slots);
		weapon.setWFinal(wfinal);
        weapon.setTree_Depth(tree_depth);

		long itemId = getLong(getColumnIndex(S.COLUMN_ITEMS_ID));
		String name = getString(getColumnIndex(S.COLUMN_ITEMS_NAME));
		String jpnName = getString(getColumnIndex(S.COLUMN_ITEMS_JPN_NAME));
		String type = getString(getColumnIndex(S.COLUMN_ITEMS_TYPE));
		int rarity = getInt(getColumnIndex(S.COLUMN_ITEMS_RARITY));
		int carry_capacity = getInt(getColumnIndex(S.COLUMN_ITEMS_CARRY_CAPACITY));
		int buy = getInt(getColumnIndex(S.COLUMN_ITEMS_BUY));
		int sell = getInt(getColumnIndex(S.COLUMN_ITEMS_SELL));
		String description = getString(getColumnIndex(S.COLUMN_ITEMS_DESCRIPTION));
		String fileLocation = getString(getColumnIndex(S.COLUMN_ITEMS_ICON_NAME));
		String armor_dupe_name_fix = getString(getColumnIndex(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX));

		weapon.setId(itemId);
		weapon.setName(name);
		weapon.setJpnName(jpnName);
		weapon.setType(type);
		weapon.setRarity(rarity);
		weapon.setCarryCapacity(carry_capacity);
		weapon.setBuy(buy);
		weapon.setSell(sell);
		weapon.setDescription(description);
		weapon.setFileLocation();
		weapon.setArmorDupeNameFix(armor_dupe_name_fix);
        weapon.setParentId(parent_id);

        if (!weapon.getWtype().equals("Bow") && !weapon.getWtype().equals("Light Bowgun")
            && !weapon.getWtype().equals("Heavy Bowgun")) {
            weapon.initializeSharpness();
        }

		return weapon;
	}

}