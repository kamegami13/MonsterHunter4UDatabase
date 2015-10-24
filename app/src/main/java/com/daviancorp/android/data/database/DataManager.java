package com.daviancorp.android.data.database;

import java.util.ArrayList;

import android.content.Context;

import com.daviancorp.android.data.classes.*;
import com.daviancorp.android.data.classes.ArenaQuest;
import com.daviancorp.android.data.classes.ArenaReward;
import com.daviancorp.android.data.classes.Armor;
import com.daviancorp.android.data.classes.Combining;
import com.daviancorp.android.data.classes.Component;
import com.daviancorp.android.data.classes.Decoration;
import com.daviancorp.android.data.classes.Gathering;
import com.daviancorp.android.data.classes.HuntingFleet;
import com.daviancorp.android.data.classes.HuntingReward;
import com.daviancorp.android.data.classes.Item;
import com.daviancorp.android.data.classes.ItemToSkillTree;
import com.daviancorp.android.data.classes.Location;
import com.daviancorp.android.data.classes.MogaWoodsReward;
import com.daviancorp.android.data.classes.Monster;
import com.daviancorp.android.data.classes.MonsterDamage;
import com.daviancorp.android.data.classes.MonsterStatus;
import com.daviancorp.android.data.classes.MonsterToArena;
import com.daviancorp.android.data.classes.MonsterToQuest;
import com.daviancorp.android.data.classes.MonsterWeakness;
import com.daviancorp.android.data.classes.Quest;
import com.daviancorp.android.data.classes.QuestReward;
import com.daviancorp.android.data.classes.Skill;
import com.daviancorp.android.data.classes.SkillTree;
import com.daviancorp.android.data.classes.Weapon;
import com.daviancorp.android.data.classes.Wishlist;
import com.daviancorp.android.data.classes.WishlistComponent;
import com.daviancorp.android.data.classes.WishlistData;
import com.daviancorp.android.data.classes.WyporiumTrade;
import com.daviancorp.android.ui.general.WeaponListEntry;


/*
 * Singleton class
 */
public class DataManager {
	private static final String TAG = "DataManager";

	private static DataManager sDataManager;		// Singleton design
	private Context mAppContext;
	private MonsterHunterDatabaseHelper mHelper;	// Used for queries
	
	/* Singleton design */
	private DataManager(Context appContext) {
		mAppContext = appContext;
		mHelper = MonsterHunterDatabaseHelper.getInstance(mAppContext);
	}
	
	public static DataManager get(Context c) {
		if (sDataManager == null) {
			// Use the application context to avoid leaking activities
			sDataManager = new DataManager(c.getApplicationContext());
		}
		return sDataManager;
	}
	
/********************************* ARENA QUESTS QUERIES ******************************************/	
	
	/* Get a Cursor that has a list of all ArenaQuests */
	public ArenaQuestCursor queryArenaQuests() {
		return mHelper.queryArenaQuests();
	}
	
	/* Get a specific ArenaQuest */
	public ArenaQuest getArenaQuest(long id) {
		ArenaQuest arenaQuest = null;
		ArenaQuestCursor cursor = mHelper.queryArenaQuest(id);
		cursor.moveToFirst();				// Point to first row
		
		if (!cursor.isAfterLast())			// Make sure cursor is not empty
			arenaQuest = cursor.getArenaQuest();
		cursor.close();
		return arenaQuest;
	}
	
/********************************* ARENA REWARD QUERIES ******************************************/
	/* Get a Cursor that has a list of ArenaReward based on Item */
	public ArenaRewardCursor queryArenaRewardItem(long id) {
		return mHelper.queryArenaRewardItem(id);
	}

	/* Get a Cursor that has a list of ArenaReward based on ArenaQuest */
	public ArenaRewardCursor queryArenaRewardArena(long id) {
		return mHelper.queryArenaRewardArena(id);
	}
	
	/* Get an array of ArenaReward based on Item */
	public ArrayList<ArenaReward> queryArenaRewardArrayItem(long id) {
		ArrayList<ArenaReward> rewards = new ArrayList<ArenaReward>();
		ArenaRewardCursor cursor = mHelper.queryArenaRewardItem(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getArenaReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}
	
	/* Get an array of ArenaReward based on ArenaQuet */
	public ArrayList<ArenaReward> queryArenaRewardArrayArena(long id) {
		ArrayList<ArenaReward> rewards = new ArrayList<ArenaReward>();
		ArenaRewardCursor cursor = mHelper.queryArenaRewardArena(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getArenaReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}
	
/********************************* ARMOR QUERIES ******************************************/	

	/* Get a cursor that has a list based on a search term */
	public ArmorCursor queryArmorSearch(String search) { return mHelper.queryArmorSearch(search); }

	/* Get a Cursor that has a list of all Armors */
	public ArmorCursor queryArmor() {
		return mHelper.queryArmor();
	}
	
	/* Get a specific Armor */
	public Armor getArmor(long id) {
		Armor armor = null;
		ArmorCursor cursor = mHelper.queryArmor(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			armor = cursor.getArmor();
		cursor.close();
		return armor;
	}
	
	/* Get an array of Armor based on hunter type */
	public ArrayList<Armor> queryArmorArrayType(String type) {
		ArrayList<Armor> armors = new ArrayList<Armor>();
		ArmorCursor cursor = mHelper.queryArmorType(type);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			armors.add(cursor.getArmor());
			cursor.moveToNext();
		}
		cursor.close();
		return armors;
	}
	
	/* Get a Cursor that has a list of Armor based on hunter type */
	public ArmorCursor queryArmorType(String type) {
		return mHelper.queryArmorType(type);
	}

	/* Get a Cursor that has a list of Armor based on equipment slot */
	public ArmorCursor queryArmorSlot(String slot) {
		return mHelper.queryArmorSlot(slot);
	}

	/* Get a Cursor that has a list of Armor based on hunter type and equipment slot */
	public ArmorCursor queryArmorTypeSlot(String type, String slot) {
		return mHelper.queryArmorTypeSlot(type, slot);
	}
	
/********************************* COMBINING QUERIES ******************************************/
	/* Get a Cursor that has a list of all Combinings */
	public CombiningCursor queryCombinings() {
		return mHelper.queryCombinings();
	}
	
	/* Get a specific Combining */
	public Combining getCombining(long id) {
		Combining combining = null;
		CombiningCursor cursor = mHelper.queryCombining(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			combining = cursor.getCombining();
		cursor.close();
		return combining;
	}

    public CombiningCursor queryCombiningOnItemID(long id) {
         return mHelper.queryCombinationsOnItemID(id);
    }
	
/********************************* COMPONENT QUERIES ******************************************/
	/* Get a Cursor that has a list of Components based on the created Item */
	public ComponentCursor queryComponentCreated(long id) {
		return mHelper.queryComponentCreated(id);
	}

	/* Get a Cursor that has a list of Components based on the component Item */
	public ComponentCursor queryComponentComponent(long id) {
		return mHelper.queryComponentComponent(id);
	}

	/* Get a Cursor that has a list of Components based on the created Item and creation type */
	public ComponentCursor queryComponentCreatedType(long id, String type) {
		return mHelper.queryComponentCreatedType(id, type);
	}
	
	/* Get an array of Components based on the created Item */
	public ArrayList<Component> queryComponentArrayCreated(long id) {
		ArrayList<Component> components = new ArrayList<Component>();
		ComponentCursor cursor = mHelper.queryComponentCreated(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			components.add(cursor.getComponent());
			cursor.moveToNext();
		}
		cursor.close();
		return components;
	}
	
	/* Get an array of Components based on the component Item */
	public ArrayList<Component> queryComponentArrayComponent(long id) {
		ArrayList<Component> components = new ArrayList<Component>();
		ComponentCursor cursor = mHelper.queryComponentComponent(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			components.add(cursor.getComponent());
			cursor.moveToNext();
		}
		cursor.close();
		return components;
	}

	/* Get an array of paths for a created Item */
	public ArrayList<String> queryComponentCreateImprove(long id) {
		// Gets all the component Items
		ComponentCursor cursor = mHelper.queryComponentCreated(id);
		cursor.moveToFirst();
		
		ArrayList<String> paths = new ArrayList<String>();
		
		// Only get distinct paths
		while (!cursor.isAfterLast()) {
			String type = cursor.getComponent().getType();
			
			// Check if not a duplicate
			if(!paths.contains(type)) {
				paths.add(type);
			}
			
			cursor.moveToNext();
		}
		
		cursor.close();
		return paths;
	}
	
/********************************* DECORATION QUERIES ******************************************/
	/* Get a Cursor that has a list of all Decorations */
	public DecorationCursor queryDecorations() {
		return mHelper.queryDecorations();
	}

	/**
	 * Gets a cursor that has a list of decorations that pass the filter.
	 * Having a null or empty filter is the same as calling without a filter
	 */
	public DecorationCursor queryDecorationsSearch(String filter) {
		filter = (filter == null) ? "" : filter.trim();
		if (filter.equals(""))
			return mHelper.queryDecorations();
		return mHelper.queryDecorationsSearch(filter);
	}

	/* Get a specific Decoration */
	public Decoration getDecoration(long id) {
		Decoration decoration = null;
		DecorationCursor cursor = mHelper.queryDecoration(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			decoration = cursor.getDecoration();
		cursor.close();
		return decoration;
	}
	
/********************************* GATHERING QUERIES ******************************************/
	/* Get a Cursor that has a list of Gathering based on Item */
	public GatheringCursor queryGatheringItem(long id) {
		return mHelper.queryGatheringItem(id);
	}

	/* Get a Cursor that has a list of Gathering based on Location */
	public GatheringCursor queryGatheringLocation(long id) {
		return mHelper.queryGatheringLocation(id);
	}
	
	/* Get a Cursor that has a list of Gathering based on Location and Quest rank */
	public GatheringCursor queryGatheringLocationRank(long id, String rank) {
		return mHelper.queryGatheringLocationRank(id, rank);
	}
	
	/* Get an array of Gathering based on Item */
	public ArrayList<Gathering> queryGatheringArrayItem(long id) {
		ArrayList<Gathering> gatherings = new ArrayList<Gathering>();
		GatheringCursor cursor = mHelper.queryGatheringItem(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			gatherings.add(cursor.getGathering());
			cursor.moveToNext();
		}
		cursor.close();
		return gatherings;
	}
	
	/* Get an array of Gathering based on Location */
	public ArrayList<Gathering> queryGatheringArrayLocation(long id) {
		ArrayList<Gathering> gatherings = new ArrayList<Gathering>();
		GatheringCursor cursor = mHelper.queryGatheringLocation(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			gatherings.add(cursor.getGathering());
			cursor.moveToNext();
		}
		cursor.close();
		return gatherings;
	}
	
	/* Get an array of Gathering based on Location and Quest rank */
	public ArrayList<Gathering> queryGatheringArrayLocationRank(long id, String rank) {
		ArrayList<Gathering> gatherings = new ArrayList<Gathering>();
		GatheringCursor cursor = mHelper.queryGatheringLocationRank(id, rank);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			gatherings.add(cursor.getGathering());
			cursor.moveToNext();
		}
		cursor.close();
		return gatherings;
	}
	
/********************************* HUNTING FLEET QUERIES ******************************************/	
	/* Get a Cursor that has a list of all HuntingFleets */
	public HuntingFleetCursor queryHuntingFleets() {
		return mHelper.queryHuntingFleets();
	}
	
	/* Get a specific HuntingFleet */
	public HuntingFleet getHuntingFleet(long id) {
		HuntingFleet huntingFleet = null;
		HuntingFleetCursor cursor = mHelper.queryHuntingFleet(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			huntingFleet = cursor.getHuntingFleet();
		cursor.close();
		return huntingFleet;
	}
	
	/* Get a Cursor that has a list of HuntingFleet based on type */
	public HuntingFleetCursor queryHuntingFleetType(String type) {
		return mHelper.queryHuntingFleetType(type);
	}

	/* Get a Cursor that has a list of HuntingFleet based on location */
	public HuntingFleetCursor queryHuntingFleetLocation(String location) {
		return mHelper.queryHuntingFleetLocation(location);
	}
	
/********************************* HUNTING REWARD QUERIES ******************************************/
	/* Helper method: Get an array of all ids for a certain Monster 
	 *		Note: Monsters may have multiple ids
	 */
	private long[] helperHuntingRewardMonster(long id) {
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(id);
		
		MonsterCursor monsterCursor = mHelper.queryMonster(id);
		monsterCursor.moveToFirst();
		
		// Get the monster name
		String name = monsterCursor.getMonster().getName();
		monsterCursor.close();
		
		// Find all of the Monster ids based on name
		monsterCursor = mHelper.queryMonsterTrait(name);
		monsterCursor.moveToFirst();
		
		while(!monsterCursor.isAfterLast()) {
			ids.add(monsterCursor.getMonster().getId());
			monsterCursor.moveToNext();
		}
		monsterCursor.close();
		
		long[] idArray = new long[ids.size()];
		for (int i = 0; i < idArray.length; i++) {
			idArray[i] = ids.get(i);
		}
		
		return idArray;
	}
	
	/* Get a Cursor that has a list of HuntingReward based on Item */
	public HuntingRewardCursor queryHuntingRewardItem(long id) {
		return mHelper.queryHuntingRewardItem(id);
	}

	/* Get a Cursor that has a list of HuntingReward based on Monster */
	public HuntingRewardCursor queryHuntingRewardMonster(long id) {
		return mHelper.queryHuntingRewardMonster(helperHuntingRewardMonster(id));
	}

	/* Get a Cursor that has a list of HuntingReward based on Monster and Rank */
	public HuntingRewardCursor queryHuntingRewardMonsterRank(long id, String rank) {
		return mHelper.queryHuntingRewardMonsterRank(helperHuntingRewardMonster(id), rank);
	}
	
	/* Get an array of HuntingReward based on Item */
	public ArrayList<HuntingReward> queryHuntingRewardArrayItem(long id) {
		ArrayList<HuntingReward> rewards = new ArrayList<HuntingReward>();
		HuntingRewardCursor cursor = mHelper.queryHuntingRewardItem(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getHuntingReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}

	/* Get an array of HuntingReward based on Monster */
	public ArrayList<HuntingReward> queryHuntingRewardArrayMonster(long id) {
		ArrayList<HuntingReward> rewards = new ArrayList<HuntingReward>();
		HuntingRewardCursor cursor = 
				mHelper.queryHuntingRewardMonster(helperHuntingRewardMonster(id));
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getHuntingReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}

	/* Get an array of HuntingReward based on Monster and Rank */
	public ArrayList<HuntingReward> queryHuntingRewardArrayMonsterRank(long id, String rank) {
		ArrayList<HuntingReward> rewards = new ArrayList<HuntingReward>();
		HuntingRewardCursor cursor = 
				mHelper.queryHuntingRewardMonsterRank(helperHuntingRewardMonster(id), rank);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getHuntingReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}
	
/********************************* ITEM QUERIES ******************************************/
	/* Get a Cursor that has a list of all Items */
	public ItemCursor queryItems() {
		return mHelper.queryItems();
	}
	
	/* Get a specific Item */
	public Item getItem(long id) {
		Item item = null;
		ItemCursor cursor = mHelper.queryItem(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			item = cursor.getItem();
		cursor.close();
		return item;
	}
	
	/* Get a Cursor that has a list of filtered Items through search */
	public ItemCursor queryItemSearch(String search) {
		return mHelper.queryItemSearch(search);
	}
	
/********************************* ITEM TO SKILL TREE QUERIES ******************************************/
	/* Get a Cursor that has a list of ItemToSkillTree based on Item */
	public ItemToSkillTreeCursor queryItemToSkillTreeItem(long id) {
		return mHelper.queryItemToSkillTreeItem(id);
	}
	
	/* Get a Cursor that has a list of ItemToSkillTree based on SkillTree */
	public ItemToSkillTreeCursor queryItemToSkillTreeSkillTree(long id, String type) {
		return mHelper.queryItemToSkillTreeSkillTree(id, type);
	}

	/* Get an array of ItemToSkillTree based on Item */
	public ArrayList<ItemToSkillTree> queryItemToSkillTreeArrayItem(long id) {
		ArrayList<ItemToSkillTree> itst = new ArrayList<ItemToSkillTree>();
		ItemToSkillTreeCursor cursor = mHelper.queryItemToSkillTreeItem(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			itst.add(cursor.getItemToSkillTree());
			cursor.moveToNext();
		}
		cursor.close();
		return itst;
	}

	/* Get an array of ItemToSkillTree based on SkillTree */
	public ArrayList<ItemToSkillTree> queryItemToSkillTreeArraySkillTree(long id, String type) {
		ArrayList<ItemToSkillTree> itst = new ArrayList<ItemToSkillTree>();
		ItemToSkillTreeCursor cursor = mHelper.queryItemToSkillTreeSkillTree(id, type);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			itst.add(cursor.getItemToSkillTree());
			cursor.moveToNext();
		}
		cursor.close();
		return itst;
	}
		
/********************************* LOCATION QUERIES ******************************************/
	/* Get a Cursor that has a list of all Locations */
	public LocationCursor queryLocations() {
		return mHelper.queryLocations();
	}

	/* Get a specific Location */
	public Location getLocation(long id) {
		Location location = null;
		LocationCursor cursor = mHelper.queryLocation(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			location = cursor.getLocation();
		cursor.close();
		return location;
	}

/********************************* MELODY QUERIES ******************************************/

	/* Get a Cursor that has a list of all Melodies from a specific set of notes */
    public HornMelodiesCursor queryMelodiesFromNotes(String notes) {
        return mHelper.queryMelodiesFromNotes(notes);
    }


/********************************* MOGA WOODS REWARD QUERIES ******************************************/
	/* Get a Cursor that has a list of MogaWoodsReward based on Item */
	public MogaWoodsRewardCursor queryMogaWoodsRewardItem(long id) {
		return mHelper.queryMogaWoodsRewardItem(id);
	}
	
	/* Get a Cursor that has a list of MogaWoodsReward based on Monster */
	public MogaWoodsRewardCursor queryMogaWoodsRewardMonster(long id) {
		return mHelper.queryMogaWoodsRewardMonster(id);
	}

	/* Get a Cursor that has a list of MogaWoodsReward based on Monster and time */
	public MogaWoodsRewardCursor queryMogaWoodsRewardMonsterTime(long id, String time) {
		return mHelper.queryMogaWoodsRewardMonsterTime(id, time);
	}

	/* Get an array of MogaWoodsReward based on Item */
	public ArrayList<MogaWoodsReward> queryMogaWoodsRewardArrayItem(long id) {
		ArrayList<MogaWoodsReward> rewards = new ArrayList<MogaWoodsReward>();
		MogaWoodsRewardCursor cursor = mHelper.queryMogaWoodsRewardItem(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getMogaWoodsReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}

	/* Get an array of MogaWoodsReward based on Monster */
	public ArrayList<MogaWoodsReward> queryMogaWoodsRewardArrayMonster(long id) {
		ArrayList<MogaWoodsReward> rewards = new ArrayList<MogaWoodsReward>();
		MogaWoodsRewardCursor cursor = mHelper.queryMogaWoodsRewardMonster(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getMogaWoodsReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}

	/* Get an array of MogaWoodsReward based on Monster and time */
	public ArrayList<MogaWoodsReward> queryHuntingRewardArrayMonsterTime(long id, String time) {
		ArrayList<MogaWoodsReward> rewards = new ArrayList<MogaWoodsReward>();
		MogaWoodsRewardCursor cursor = mHelper.queryMogaWoodsRewardMonsterTime(id, time);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getMogaWoodsReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}
	
/********************************* MONSTER QUERIES ******************************************/
	/* Get a Cursor that has a list of all Monster */
	public MonsterCursor queryMonsters() {
		return mHelper.queryMonsters();
	}
	
	/* Get a Cursor that has a list of all small Monster */	
	public MonsterCursor querySmallMonsters() {
		return mHelper.querySmallMonsters();
	}

	/* Get a Cursor that has a list of all large Monster */	
	public MonsterCursor queryLargeMonsters() {
		return mHelper.queryLargeMonsters();
	}

	/* Get a specific Monster */
	public Monster getMonster(long id) {
		Monster monster = null;
		MonsterCursor cursor = mHelper.queryMonster(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			monster = cursor.getMonster();
		cursor.close();
		return monster;
	}

	/* Get an array of every trait of a specific Monster */
	public ArrayList<Monster> getMonsterTraitArray(long id) {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		MonsterCursor cursor = mHelper.queryMonster(id);
		cursor.moveToFirst();
		
		String name = cursor.getMonster().getName();
		
		cursor = mHelper.queryMonsterTrait(name);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			monsters.add(cursor.getMonster());
		cursor.close();
		return monsters;
	}

/********************************* MONSTER AILMENT QUERIES ******************************************/
	/* Get a cursor that lists all the ailments a particular monster can inflict */
	public MonsterAilmentCursor queryAilmentsFromId(long id){
		return mHelper.queryAilmentsFromMonster(id);
	}

/********************************* MONSTER DAMAGE QUERIES ******************************************/	
	/* Get a Cursor that has a list of MonsterDamage for a specific Monster */
	public MonsterDamageCursor queryMonsterDamage(long id) {
		return mHelper.queryMonsterDamage(id);
	}
	
	/* Get an array of MonsterDamage for a specific Monster */
	public ArrayList<MonsterDamage> queryMonsterDamageArray(long id) {
		ArrayList<MonsterDamage> damages = new ArrayList<MonsterDamage>();
		MonsterDamageCursor cursor = mHelper.queryMonsterDamage(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			damages.add(cursor.getMonsterDamage());
			cursor.moveToNext();
		}
		cursor.close();
		return damages;
	}

/********************************* MONSTER STATUS QUERIES ******************************************/
    /* Get an array of status objects for a monster */
    public ArrayList<MonsterStatus> queryMonsterStatus(long id) {
        ArrayList<MonsterStatus> monsterStatuses = new ArrayList<MonsterStatus>();

        MonsterStatusCursor cursor = mHelper.queryMonsterStatus(id);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            monsterStatuses.add(cursor.getStatus());
            cursor.moveToNext();
        }
        cursor.close();
        return monsterStatuses;
    }

/********************************* MONSTER TO ARENA QUERIES ******************************************/
	/* Get a Cursor that has a list of MonsterToArena based on Monster */
	public MonsterToArenaCursor queryMonsterToArenaMonster(long id) {
		return mHelper.queryMonsterToArenaMonster(id);
	}
	
	/* Get a Cursor that has a list of MonsterToArena based on ArenaQuest */
	public MonsterToArenaCursor queryMonsterToArenaArena(long id) {
		return mHelper.queryMonsterToArenaArena(id);
	}
	
	/* Get an array of MonsterToArena based on Monster */
	public ArrayList<MonsterToArena> queryMonsterToArenaArrayMonster(long id) {
		ArrayList<MonsterToArena> mta = new ArrayList<MonsterToArena>();
		MonsterToArenaCursor cursor = mHelper.queryMonsterToArenaMonster(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			mta.add(cursor.getMonsterToArena());
			cursor.moveToNext();
		}
		cursor.close();
		return mta;
	}

	/* Get an array of MonsterToArena based on ArenaQuest */
	public ArrayList<MonsterToArena> queryMonsterToArenaArrayArena(long id) {
		ArrayList<MonsterToArena> mta = new ArrayList<MonsterToArena>();
		MonsterToArenaCursor cursor = mHelper.queryMonsterToArenaArena(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			mta.add(cursor.getMonsterToArena());
			cursor.moveToNext();
		}
		cursor.close();
		return mta;
	}
		
/********************************* MONSTER TO QUEST QUERIES ******************************************/
	/* Get a Cursor that has a list of MonsterToQuest based on Monster */
	public MonsterToQuestCursor queryMonsterToQuestMonster(long id) {
		return mHelper.queryMonsterToQuestMonster(id);
	}

	/* Get a Cursor that has a list of MonsterToQuest based on Quest */
	public MonsterToQuestCursor queryMonsterToQuestQuest(long id) {
		return mHelper.queryMonsterToQuestQuest(id);
	}
	
	/* Get an array of MonsterToQuest based on Monster */
	public ArrayList<MonsterToQuest> queryMonsterToQuestArrayMonster(long id) {
		ArrayList<MonsterToQuest> mtq = new ArrayList<MonsterToQuest>();
		MonsterToQuestCursor cursor = mHelper.queryMonsterToQuestMonster(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			mtq.add(cursor.getMonsterToQuest());
			cursor.moveToNext();
		}
		cursor.close();
		return mtq;
	}

	/* Get an array of MonsterToQuest based on Quest */
	public ArrayList<MonsterToQuest> queryMonsterToQuestArrayQuest(long id) {
		ArrayList<MonsterToQuest> mtq = new ArrayList<MonsterToQuest>();
		MonsterToQuestCursor cursor = mHelper.queryMonsterToQuestQuest(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			mtq.add(cursor.getMonsterToQuest());
			cursor.moveToNext();
		}
		cursor.close();
		return mtq;
	}

/********************************* MONSTER HABITAT QUERIES ******************************************/
	/* Get a Cursor that has a list of MonsterHabitats based on Monster */
    public MonsterHabitatCursor queryHabitatMonster(long id) {
        return mHelper.queryHabitatMonster(id);
    }

    /* Get a Cursor that has a list of MonsterHabitats based on Location */
    public MonsterHabitatCursor queryHabitatLocation(long id) {
        return mHelper.queryHabitatLocation(id);
    }

/********************************* MONSTER WEAKNESS QUERIES ******************************************/

	/* Get a cursor that has all a monsters weaknesses */
	public MonsterWeaknessCursor queryWeaknessFromMonster(long id){
		return mHelper.queryWeaknessFromMonster(id);
	}

	/* Get an array of MonsterWeakness for a specific Monster */
	public ArrayList<MonsterWeakness> queryMonsterWeaknessArray(long id) {
		ArrayList<MonsterWeakness> weaknesses = new ArrayList<MonsterWeakness>();
		MonsterWeaknessCursor cursor = mHelper.queryWeaknessFromMonster(id);
		cursor.moveToFirst();

		while(!cursor.isAfterLast()) {
			weaknesses.add(cursor.getWeakness());
			cursor.moveToNext();
		}
		cursor.close();
		return weaknesses;
	}

/********************************* QUEST QUERIES ******************************************/	

	/* Get a Cursor that has a list of all Quests */
	public QuestCursor queryQuests() {
		return mHelper.queryQuests();
	}

	/* Get a specific Quests */
	public Quest getQuest(long id) {
		Quest quest = null;
		QuestCursor cursor = mHelper.queryQuest(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			quest = cursor.getQuest();
		cursor.close();
		return quest;
	}
	
	/* Get an array of Quest based on hub */
	public ArrayList<Quest> queryQuestArrayHub(String hub) {
		ArrayList<Quest> quests = new ArrayList<Quest>();
		QuestCursor cursor = mHelper.queryQuestHub(hub);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			quests.add(cursor.getQuest());
			cursor.moveToNext();
		}
		cursor.close();
		return quests;
	}
	
	/* Get a Cursor that has a list of Quest based on hub */
		public QuestCursor queryQuestHub(String hub) {
		return mHelper.queryQuestHub(hub);
	}

		/* Get a Cursor that has a list of Quest based on hub and stars */
	public QuestCursor queryQuestHubStar(String hub, String stars) {
		return mHelper.queryQuestHubStar(hub, stars);
	}
	
/********************************* QUEST REWARD QUERIES ******************************************/
	/* Get a Cursor that has a list of QuestReward based on Item */
	public QuestRewardCursor queryQuestRewardItem(long id) {
		return mHelper.queryQuestRewardItem(id);
	}
	
	/* Get a Cursor that has a list of QuestReward based on Quest */
	public QuestRewardCursor queryQuestRewardQuest(long id) {
		return mHelper.queryQuestRewardQuest(id);
	}

	/* Get an array of QuestReward based on Item */
	public ArrayList<QuestReward> queryQuestRewardArrayItem(long id) {
		ArrayList<QuestReward> rewards = new ArrayList<QuestReward>();
		QuestRewardCursor cursor = mHelper.queryQuestRewardItem(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getQuestReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}

	/* Get an array of QuestReward based on Quest */
	public ArrayList<QuestReward> queryQuestRewardArrayQuest(long id) {
		ArrayList<QuestReward> rewards = new ArrayList<QuestReward>();
		QuestRewardCursor cursor = mHelper.queryQuestRewardQuest(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			rewards.add(cursor.getQuestReward());
			cursor.moveToNext();
		}
		cursor.close();
		return rewards;
	}
	
/********************************* SKILL QUERIES ******************************************/	

//	public SkillCursor querySkill(long id) {
//		return mHelper.querySkill(id);
//	}
	
	/* Get a Cursor that has a list of all Skills from a specific SkillTree */
	public SkillCursor querySkillFromTree(long id) {
		return mHelper.querySkillFromTree(id);
	}
	
	/* Get an array of Skill from a specific SkillTree */
	public ArrayList<Skill> querySkillArray(long id) {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		SkillCursor cursor = mHelper.querySkillFromTree(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			skills.add(cursor.getSkill());
			cursor.moveToNext();
		}
		cursor.close();
		return skills;
	}
		
/********************************* SKILL TREE QUERIES ******************************************/	
	/* Get a Cursor that has a list of all SkillTree */
	public SkillTreeCursor querySkillTrees() {
		return mHelper.querySkillTrees();
	}

	/* Get a specific SkillTree */
	public SkillTree getSkillTree(long id) {
		SkillTree skillTree = null;
		SkillTreeCursor cursor = mHelper.querySkillTree(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			skillTree = cursor.getSkillTree();
		cursor.close();
		return skillTree;
	}
	
/********************************* WEAPON QUERIES ******************************************/	
	/* Get a Cursor that has a list of all Weapons */
	public WeaponCursor queryWeapon() {
		return mHelper.queryWeapon();
	}

	/* Get a specific Weapon */
	public Weapon getWeapon(long id) {
		Weapon weapon = null;
		WeaponCursor cursor = mHelper.queryWeapon(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			weapon = cursor.getWeapon();
		cursor.close();
		return weapon;
	}

	/* Get a Cursor that has a list of Weapons based on weapon type */
	public WeaponCursor queryWeaponType(String type) {
		return mHelper.queryWeaponType(type, false);
	}

    /* Get an array that has a list of Weapons based on weapon type
    * This method is for preloading info for weapons to prevent lots of
    * work in binding a view to a list */
    public ArrayList<Weapon> queryWeaponTypeArray(String type) {
        WeaponCursor cursor = mHelper.queryWeaponType(type, false);

        cursor.moveToFirst();
        ArrayList<Weapon> weapons = new ArrayList<Weapon>();
        int i = 0;

        while(cursor.isAfterLast() == false) {
            weapons.add(cursor.getWeapon());
            cursor.moveToNext();
        }

        return weapons;
    }

    /* Get an array of weapon expandable list items
    * */
    public ArrayList<WeaponListEntry> queryWeaponTreeArray(String type) {
        WeaponCursor cursor = mHelper.queryWeaponType(type, false);

        cursor.moveToFirst();
        ArrayList<WeaponListEntry> weapons = new ArrayList<WeaponListEntry>();
        WeaponListEntry currentEntry;
        Weapon currentWeapon;

        int i = 0;
        int id_offset = (int) cursor.getWeapon().getId();
        int parent_id;
        int absolute_position;

        while(cursor.isAfterLast() == false) {
            currentWeapon = cursor.getWeapon();
            currentEntry = new WeaponListEntry(currentWeapon);

            parent_id = (int) currentWeapon.getParentId();

            if(parent_id != 0) {
                absolute_position = parent_id - id_offset;
                weapons.get(absolute_position).addChild(currentEntry);
            }

            weapons.add(currentEntry);
            cursor.moveToNext();
        }

        return weapons;
    }

	/*
	* Get an array of weapon expandable list items consisting only of the final upgrades
    */
	public ArrayList<WeaponListEntry> queryWeaponTreeArrayFinal(String type) {
		WeaponCursor cursor = mHelper.queryWeaponType(type, true);

		ArrayList<WeaponListEntry> weapons = new ArrayList<>();

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			weapons.add(new WeaponListEntry(cursor.getWeapon()));
			cursor.moveToNext();
		}

		return weapons;
	}
	
	/* Get a Cursor that has a list of Weapons in the weapon tree for a specified weapon */
	public WeaponCursor queryWeaponTree(long id) {
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(id);			// Add specified weapon to returned array
		
		long currentId = id;
		WeaponTreeCursor cursor = null;
		
		// Get ancestors and add them at the beginning of the tree
		do {
			cursor = mHelper.queryWeaponTreeParent(currentId);
			cursor.moveToFirst();
			
			if(cursor.isAfterLast())
				break;
			
			currentId = cursor.getWeapon().getId();
			ids.add(0, currentId);
			
			cursor.close();
		}
		while (true);
		
		currentId = id;		// set current id back to specified weapon

		// Get children only; exclude descendants of children
		cursor = mHelper.queryWeaponTreeChild(currentId);
		cursor.moveToFirst();
		
		if(!cursor.isAfterLast()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				ids.add(cursor.getWeapon().getId());
				cursor.moveToNext();
			}
		}
		cursor.close();
		
		// Convert Arraylist to a regular array to return
		long[] idArray = new long[ids.size()];
		for (int i = 0; i < idArray.length; i++) {
			idArray[i] = ids.get(i);
		}

		return mHelper.queryWeapons(idArray);
		
	}
	
/********************************* WISHLIST QUERIES ******************************************/	
	/* Get a Cursor that has a list of all Wishlists */
	public WishlistCursor queryWishlists() {
		return mHelper.queryWishlists();
	}

	/* Get a specific Wishlist */
	public WishlistCursor queryWishlist(long id) {
		return mHelper.queryWishlist(id);
	}

	/* Add a new Wishlist with a given name */
	public void queryAddWishlist(String name) {
		mHelper.queryAddWishlist(name);
	}

	/* Update a specific Wishlist with a new name */
	public void queryUpdateWishlist(long id, String name) {
		mHelper.queryUpdateWishlist(id, name);
	}

	/* Delete a specific Wishlist */
	public void queryDeleteWishlist(long id) {
		mHelper.queryDeleteWishlist(id);
	}

	/* Copy a specific Wishlist into a new wishlist, including its entries */
	public void queryCopyWishlist(long id, String name) {
		long newId = mHelper.queryAddWishlist(name);
		
		// Get all of the entries from the copied wishlist
		WishlistDataCursor cursor = mHelper.queryWishlistData(id);
		cursor.moveToFirst();

		// Add all of the retrieved entries into the new wishlist
		while(!cursor.isAfterLast()) {
			WishlistData wishlist = cursor.getWishlistData();
			mHelper.queryAddWishlistDataAll(newId, wishlist.getItem().getId(), 
					wishlist.getQuantity(), wishlist.getSatisfied(), wishlist.getPath());
			cursor.moveToNext();
		}
		cursor.close();

		// Get all of the components from the copied wishlist
		WishlistComponentCursor wcCursor = mHelper.queryWishlistComponents(id);
		wcCursor.moveToFirst();

		// Add all of the retrieved components into the new wishlist
		while(!wcCursor.isAfterLast()) {
			WishlistComponent wishlist = wcCursor.getWishlistComponent();
			mHelper.queryAddWishlistComponentAll(newId, wishlist.getItem().getId(), 
					wishlist.getQuantity(), wishlist.getNotes());
			wcCursor.moveToNext();
		}
		wcCursor.close();
	}

	/* Get a specific Wishlist */
	public Wishlist getWishlist(long id) {
		Wishlist wishlist = null;
		WishlistCursor cursor = mHelper.queryWishlist(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			wishlist = cursor.getWishlist();
		cursor.close();
		return wishlist;
	}
	
/********************************* WISHLIST DATA QUERIES ******************************************/	
	/* Get a Cursor that has a list of WishlistData based on Wishlist */
	public WishlistDataCursor queryWishlistData(long id) {
		return mHelper.queryWishlistData(id);
	}
	
	/* Add an entry to a specific wishlist with the given item and quantity */
	public void queryAddWishlistData(long wishlist_id, long item_id, int quantity, String path) {

		WishlistDataCursor cursor = mHelper.queryWishlistData(wishlist_id, item_id, path);
		cursor.moveToFirst();

		if (cursor.isAfterLast()) {
			// Add new entry to wishlist_data
			mHelper.queryAddWishlistData(wishlist_id, item_id, quantity, path);
		}
		else {
			// Update existing entry
			WishlistData data = cursor.getWishlistData();
			long id = data.getId();
			int total = data.getQuantity() + quantity;
			
			mHelper.queryUpdateWishlistDataQuantity(id, total);
		}
		cursor.close();
		
		helperQueryAddWishlistData(wishlist_id, item_id, quantity, path);
		helperQueryUpdateWishlistSatisfied(wishlist_id);
	}
	
	/* Helper method: Add an entry to a wishlist, 
	 *		and add the necessary components from the chosen path
	 */
	private void helperQueryAddWishlistData(long wishlist_id, long item_id, int quantity, String path) {
		// Get the components for the entry
		ComponentCursor cc = mHelper.queryComponentCreatedType(item_id, path);
		cc.moveToFirst();
		
		WishlistComponentCursor wc = null;
		
		// Add each component to the wishlist component list
		while (!cc.isAfterLast()) {
			long component_id = cc.getComponent().getComponent().getId();
			int c_amt = (cc.getComponent().getQuantity()) * (quantity);
			
			wc = mHelper.queryWishlistComponent(wishlist_id, component_id);
			wc.moveToFirst();
			
			if (wc.isAfterLast()) {
				// Add component entry to wishlist_component
				mHelper.queryAddWishlistComponent(wishlist_id, component_id, c_amt);
			}
			else {
				// Update component entry to wishlist_component
				long wc_id = wc.getWishlistComponent().getId();
				int old_amt = wc.getWishlistComponent().getQuantity();

				mHelper.queryUpdateWishlistComponentQuantity(wc_id, old_amt + c_amt);
			}
			wc.close();
			
			cc.moveToNext();
		}
		cc.close();
	}

	/* Update an entry to the given quantity */
	public void queryUpdateWishlistData(long id, int quantity) {
		
		// Get the existing entry from WishlistData
		WishlistDataCursor wdCursor = mHelper.queryWishlistDataId(id);
		wdCursor.moveToFirst();
		WishlistData wd = wdCursor.getWishlistData();
		wdCursor.close();
		
		long wishlist_id = wd.getWishlistId();
		long item_id = wd.getItem().getId();
		int wd_old_quantity = wd.getQuantity();
		String path = wd.getPath();
		
		// Find the different between new and old quantities
		int diff_quantity = quantity - wd_old_quantity;
		
		// Get the components for the WishlistData entry
		ComponentCursor cc = mHelper.queryComponentCreatedType(item_id, path);
		cc.moveToFirst();
		
		// Update those components in WishlistComponent
		while (!cc.isAfterLast()) {
			long component_id = cc.getComponent().getComponent().getId();
			int c_amt = (cc.getComponent().getQuantity()) * (diff_quantity);
			
			WishlistComponentCursor wc = mHelper.queryWishlistComponent(wishlist_id, component_id);
			wc.moveToFirst();
			
			// Update component entry to wishlist_component
			long wc_id = wc.getWishlistComponent().getId();
			int old_amt = wc.getWishlistComponent().getQuantity();
			
			mHelper.queryUpdateWishlistComponentQuantity(wc_id, old_amt + c_amt);
			
			wc.close();
			cc.moveToNext();
		}
		cc.close();
		
		mHelper.queryUpdateWishlistDataQuantity(id, quantity);
		
		// Check for any changes if any WishlistData is satisfied (can be build)
		helperQueryUpdateWishlistSatisfied(wishlist_id);
	}

	/* Delete an entry from WishlistData */
	public void queryDeleteWishlistData(long id) {

		// Get the existing entry from WishlistData
		WishlistDataCursor wdCursor = mHelper.queryWishlistDataId(id);
		wdCursor.moveToFirst();
		WishlistData wd = wdCursor.getWishlistData();
		wdCursor.close();
		
		long wishlist_id = wd.getWishlistId();
		long item_id = wd.getItem().getId();
		int wd_old_quantity = wd.getQuantity();
		String path = wd.getPath();

		// Get the components for the WishlistData entry
		ComponentCursor cc = mHelper.queryComponentCreatedType(item_id, path);
		cc.moveToFirst();
		
		// Update those components in WishlistComponent
		while (!cc.isAfterLast()) {
			long component_id = cc.getComponent().getComponent().getId();
			int c_amt = (cc.getComponent().getQuantity()) * (wd_old_quantity);
			
			WishlistComponentCursor wc = mHelper.queryWishlistComponent(wishlist_id, component_id);
			wc.moveToFirst();
			
			// Update component entry to wishlist_component
			long wc_id = wc.getWishlistComponent().getId();
			int old_amt = wc.getWishlistComponent().getQuantity();
			
			int new_amt = old_amt - c_amt;
			
			if (new_amt > 0) {
				// Update wishlist_component if component is still needed
				mHelper.queryUpdateWishlistComponentQuantity(wc_id, old_amt - c_amt);
			}
			else {
				// If component no longer needed, delete it from wishlist_component
				mHelper.queryDeleteWishlistComponent(wc_id);
			}
			
			wc.close();
			cc.moveToNext();
		}
		cc.close();
		
		mHelper.queryDeleteWishlistData(id);
	}
	
	/* Get the total price/cost for the specified wishlist */
	public int queryWishlistPrice(long id) {
		int total = 0;		// total cost
		
		// Get all of the WishlistData from the wishlist
		WishlistDataCursor wdc = mHelper.queryWishlistData(id);
		wdc.moveToFirst();
		
		int buy;
		int quantity = 0;
		
		// Calculate cost for each WishlistData entry
		while(!wdc.isAfterLast()) {
			buy = 0;		// cost for entry
			WishlistData wd = wdc.getWishlistData();
			Item i = wd.getItem();
			String type = wd.getPath();
			
			// Check path if the entry is a Weapon
			if ((i.getType()).equals("Weapon")) {
				WeaponCursor wc = mHelper.queryWeapon(i.getId());
				wc.moveToFirst();
				
				// Get the cost from the desired path
				if (type.equals("Create"))
					buy = wc.getWeapon().getCreationCost();
				else if (type.equals("Improve")) {
					buy = wc.getWeapon().getUpgradeCost();
				}
				wc.close();
			}
			// For Armor and Decoration
			else {
				buy = wd.getItem().getBuy();
			}
			
			// Add the entry cost to total cost
			quantity = wd.getQuantity();
			total = total + (buy * quantity);
			
			wdc.moveToNext();
		}
		wdc.close();
		return total;
	}
	
/********************************* WISHLIST COMPONENT QUERIES ******************************************/	
	/* Get a Cursor that has a list of WishlistComponent based on Wishlist */
	public WishlistComponentCursor queryWishlistComponents(long id) {
		return mHelper.queryWishlistComponents(id);
	}	
	
	/* Update the specified WishlistComponent by the given quantity */
	public void queryUpdateWishlistComponentNotes(long id, int notes) {	
		mHelper.queryUpdateWishlistComponentNotes(id, notes);
		WishlistComponentCursor wcc = mHelper.queryWishlistComponentId(id);
		wcc.moveToFirst();
		
		// Get the wishlist id to check for any satisfied entries
		long w_id = wcc.getWishlistComponent().getWishlistId();
		wcc.close();
		
		// Check for any changes if any WishlistData is satisfied (can be build)
		helperQueryUpdateWishlistSatisfied(w_id);
	}
	
	/* Helper method: From a specified , check if any WishlistData can be built */
	private void helperQueryUpdateWishlistSatisfied(long wishlist_id) {
		WishlistDataCursor wdc = mHelper.queryWishlistData(wishlist_id);
		wdc.moveToFirst();
		
		WishlistData wd = null;
		WishlistComponent wc = null;
		WishlistComponentCursor wcc = null;
		
		Component c = null;
		ComponentCursor cc = null;
		
		String path;
		long created_id;
		long component_id;
		int required_amt;
		int have_amt;
		int satisfied;
		
		// For every WishlistData
		while(!wdc.isAfterLast()) {
			satisfied  = 1;			// Set true until unsatisfied
			wd = wdc.getWishlistData();
			created_id = wd.getItem().getId();
			path = wd.getPath();
			
			cc = mHelper.queryComponentCreatedType(created_id, path);
			cc.moveToFirst();
			
			// For every component of the current WishlistData entry
			while(!cc.isAfterLast()) {
				c = cc.getComponent();
				component_id = c.getComponent().getId();
				
				wcc = mHelper.queryWishlistComponent(wishlist_id, component_id);
				wcc.moveToFirst();
				wc = wcc.getWishlistComponent();
				
				// Get the amounts
				required_amt = c.getQuantity();
				have_amt = wc.getNotes();

				// Check if user does not have enough materials
				if (have_amt < required_amt) {
					satisfied = 0;
					break;
				}
				
				wcc.close();	
				cc.moveToNext();
			}
			
			cc.close();
			
			// Update the WishlistData entry
			mHelper.queryUpdateWishlistDataSatisfied(wd.getId(), satisfied);
			wdc.moveToNext();
		}
		
		wdc.close();
	}

	/********************************* ARMOR SET BUILDER QUERIES ******************************************/

	public ASBSetCursor queryASBSets() {
		return mHelper.queryASBSets();
	}

	public ASBSet getASBSet(long id) {
		ASBSet set = null;
		ASBSetCursor cursor = mHelper.queryASBSet(id);
		cursor.moveToFirst();

		if (!cursor.isAfterLast())
			set = cursor.getASBSet();

		cursor.close();
		return set;
	}

	/** Get a cursor with a list of all armor sets. */
	public ASBSessionCursor queryASBSessions() {
		return mHelper.queryASBSessions();
	}

	/** Get a specific armor set. */
	public ASBSession getASBSession(long id) {
		ASBSession session = null;
		ASBSessionCursor cursor = mHelper.queryASBSession(id);
		cursor.moveToFirst();

		if (!cursor.isAfterLast())
			session = cursor.getASBSession(mAppContext);

		cursor.close();
		return session;
	}

	/** Adds a new ASB set to the list. */
	public void queryAddASBSet(String name, int rank,  int hunterType) {
		mHelper.queryAddASBSet(name, rank, hunterType);
	}

	/** Adds a new set that is a copy of the designated set to the list. */
	public void queryAddASBSet(long setId) {
		ASBSet set = getASBSet(setId);
		mHelper.queryAddASBSet(set.getName(), set.getRank(), set.getHunterType());
	}

	public void queryDeleteASBSet(long setId) {
		mHelper.queryDeleteASBSet(setId);
	}

	public void queryUpdateASBSet(long setId, String name, int rank, int hunterType) {
		mHelper.queryUpdateASBSet(setId, name, rank, hunterType);
	}

	public void queryPutASBSessionArmor(long asbSetId, long armorId, int pieceIndex) {
		mHelper.queryAddASBSessionArmor(asbSetId, armorId, pieceIndex);
	}

	public void queryRemoveASBSessionArmor(long asbSetId, int pieceIndex) {
		mHelper.queryAddASBSessionArmor(asbSetId, -1, pieceIndex);
	}

	public void queryPutASBSessionDecoration(long asbSetId, long decorationId, int pieceIndex, int decorationIndex) {
		mHelper.queryPutASBSessionDecoration(asbSetId, decorationId, pieceIndex, decorationIndex);
	}

	public void queryRemoveASBSessionDecoration(long asbSetId, int pieceIndex, int decorationIndex) {
		mHelper.queryPutASBSessionDecoration(asbSetId, -1, pieceIndex, decorationIndex);
	}

	public void queryCreateASBSessionTalisman(long asbSetId, int type, int slots, long skill1Id, int skill1Points, long skill2Id, int skill2Points) {
		mHelper.queryCreateASBSessionTalisman(asbSetId, type, slots, skill1Id, skill1Points, skill2Id, skill2Points);
	}

	public void queryRemoveASBSessionTalisman(long asbSetId) {
		mHelper.queryRemoveASBSessionTalisman(asbSetId);
	}

    /**************************** WYPORIUM TRADE DATA QUERIES *************************************/
    	/* Get a Cursor that has a list of all wyporium trades */
    public WyporiumTradeCursor queryWyporiumTrades() {
        return mHelper.queryWyporiumTrades();
    }

    /* Get a specific wyporium trade */
    public WyporiumTrade getWyporiumTrade(long id) {
        WyporiumTrade wyporiumTrade = null;
        WyporiumTradeCursor cursor = mHelper.queryWyporiumTrades(id);
        cursor.moveToFirst();

        if (!cursor.isAfterLast())
            wyporiumTrade = cursor.getWyporiumTrade();
        cursor.close();
        return wyporiumTrade;
    }
}
