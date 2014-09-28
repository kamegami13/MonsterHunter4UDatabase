package com.daviancorp.android.data.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.daviancorp.android.data.object.Weapon;

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
		String elemental_attack = getString(getColumnIndex(S.COLUMN_WEAPONS_ELEMENTAL_ATTACK));
		String awakened_elemental_attack = getString(getColumnIndex(S.COLUMN_WEAPONS_AWAKENED_ELEMENTAL_ATTACK));
		int defense = getInt(getColumnIndex(S.COLUMN_WEAPONS_DEFENSE));
		String sharpness = getString(getColumnIndex(S.COLUMN_WEAPONS_SHARPNESS));
		int affinity = getInt(getColumnIndex(S.COLUMN_WEAPONS_AFFINITY));
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
		String sharpness_file = getString(getColumnIndex(S.COLUMN_WEAPONS_SHARPNESS_FILE));
		int wfinal = getInt(getColumnIndex(S.COLUMN_WEAPONS_FINAL));

		weapon.setWtype(wtype);
		weapon.setCreationCost(creation_cost);
		weapon.setUpgradeCost(upgrade_cost);
		weapon.setAttack(attack);
		weapon.setMaxAttack(max_attack);
		weapon.setElementalAttack(elemental_attack);
		weapon.setAwakenedElementalAttack(awakened_elemental_attack);
		weapon.setDefense(defense);
		weapon.setSharpness(sharpness);
		weapon.setAffinity(affinity);
		weapon.setHornNotes(horn_notes);
		weapon.setShellingType(shelling_type);
		weapon.setPhial(phial);
		weapon.setCharges(charges);
		weapon.setCoatings(coatings);
		weapon.setRecoil(recoil);
		weapon.setReloadSpeed(reload_speed);
		weapon.setRapidFire(rapid_fire);
		weapon.setDeviation(deviation);
		weapon.setAmmo(ammo);
		weapon.setNumSlots(num_slots);
		weapon.setSharpnessFile(sharpness_file);
		weapon.setWFinal(wfinal);

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
		weapon.setFileLocation(fileLocation);
		weapon.setArmorDupeNameFix(armor_dupe_name_fix);

		return weapon;
	}

}