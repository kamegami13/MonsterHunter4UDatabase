package com.daviancorp.android.data.database;

import java.util.ArrayList;

import android.content.Context;

import com.daviancorp.android.data.object.ArenaQuest;
import com.daviancorp.android.data.object.ArenaReward;
import com.daviancorp.android.data.object.Armor;
import com.daviancorp.android.data.object.Combining;
import com.daviancorp.android.data.object.Component;
import com.daviancorp.android.data.object.Decoration;
import com.daviancorp.android.data.object.Gathering;
import com.daviancorp.android.data.object.HuntingFleet;
import com.daviancorp.android.data.object.HuntingReward;
import com.daviancorp.android.data.object.Item;
import com.daviancorp.android.data.object.ItemToSkillTree;
import com.daviancorp.android.data.object.Location;
import com.daviancorp.android.data.object.MogaWoodsReward;
import com.daviancorp.android.data.object.Monster;
import com.daviancorp.android.data.object.MonsterDamage;
import com.daviancorp.android.data.object.MonsterToArena;
import com.daviancorp.android.data.object.MonsterToQuest;
import com.daviancorp.android.data.object.Quest;
import com.daviancorp.android.data.object.QuestReward;
import com.daviancorp.android.data.object.Skill;
import com.daviancorp.android.data.object.SkillTree;
import com.daviancorp.android.data.object.Weapon;
import com.daviancorp.android.data.object.Wishlist;
import com.daviancorp.android.data.object.WishlistComponent;
import com.daviancorp.android.data.object.WishlistData;

public class DataManager {
	private static final String TAG = "DataManager";

	
	private static DataManager sDataManager;
	private Context mAppContext;
	private MonsterHunterDatabaseHelper mHelper;
	
	private DataManager(Context appContext) {
		mAppContext = appContext;
		mHelper = new MonsterHunterDatabaseHelper(mAppContext);
	}
	
	public static DataManager get(Context c) {
		if (sDataManager == null) {
			// Use the application context to avoid leaking activities
			sDataManager = new DataManager(c.getApplicationContext());
		}
		return sDataManager;
	}
	
/********************************* ARENA QUESTS QUERIES ******************************************/	
	
	public ArenaQuestCursor queryArenaQuests() {
		return mHelper.queryArenaQuests();
	}
	
	public ArenaQuest getArenaQuest(long id) {
		ArenaQuest arenaQuest = null;
		ArenaQuestCursor cursor = mHelper.queryArenaQuest(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			arenaQuest = cursor.getArenaQuest();
		cursor.close();
		return arenaQuest;
	}
	
/********************************* ARENA REWARD QUERIES ******************************************/
	public ArenaRewardCursor queryArenaRewardItem(long id) {
		return mHelper.queryArenaRewardItem(id);
	}

	public ArenaRewardCursor queryArenaRewardArena(long id) {
		return mHelper.queryArenaRewardArena(id);
	}
	
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
	
	public ArmorCursor queryArmor() {
		return mHelper.queryArmor();
	}
	
	public Armor getArmor(long id) {
		Armor armor = null;
		ArmorCursor cursor = mHelper.queryArmor(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			armor = cursor.getArmor();
		cursor.close();
		return armor;
	}
	
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
	
	public ArmorCursor queryArmorType(String type) {
		return mHelper.queryArmorType(type);
	}

	public ArmorCursor queryArmorSlot(String slot) {
		return mHelper.queryArmorSlot(slot);
	}

	public ArmorCursor queryArmorTypeSlot(String type, String slot) {
		return mHelper.queryArmorTypeSlot(type, slot);
	}
	
/********************************* COMBINING QUERIES ******************************************/
	public CombiningCursor queryCombinings() {
		return mHelper.queryCombinings();
	}
	
	public Combining getCombining(long id) {
		Combining combining = null;
		CombiningCursor cursor = mHelper.queryCombining(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			combining = cursor.getCombining();
		cursor.close();
		return combining;
	}
	
/********************************* COMPONENT QUERIES ******************************************/
	public ComponentCursor queryComponentCreated(long id) {
		return mHelper.queryComponentCreated(id);
	}

	public ComponentCursor queryComponentComponent(long id) {
		return mHelper.queryComponentComponent(id);
	}

	public ComponentCursor queryComponentCreatedType(long id, String type) {
		return mHelper.queryComponentCreatedType(id, type);
	}
	
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

	public ArrayList<String> queryComponentCreateImprove(long id) {
		ComponentCursor cursor = mHelper.queryComponentCreated(id);
		cursor.moveToFirst();
		
		ArrayList<String> paths = new ArrayList<String>();
		
		while (!cursor.isAfterLast()) {
			String type = cursor.getComponent().getType();
			
			if(!paths.contains(type)) {
				paths.add(type);
			}
			
			cursor.moveToNext();
		}
		
		return paths;
	}
	
/********************************* DECORATION QUERIES ******************************************/
	public DecorationCursor queryDecorations() {
		return mHelper.queryDecorations();
	}
	
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
	public GatheringCursor queryGatheringItem(long id) {
		return mHelper.queryGatheringItem(id);
	}

	public GatheringCursor queryGatheringLocation(long id) {
		return mHelper.queryGatheringLocation(id);
	}
	
	public GatheringCursor queryGatheringLocationRank(long id, String rank) {
		return mHelper.queryGatheringLocationRank(id, rank);
	}
	
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
	
	public HuntingFleetCursor queryHuntingFleets() {
		return mHelper.queryHuntingFleets();
	}
	
	public HuntingFleet getHuntingFleet(long id) {
		HuntingFleet huntingFleet = null;
		HuntingFleetCursor cursor = mHelper.queryHuntingFleet(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			huntingFleet = cursor.getHuntingFleet();
		cursor.close();
		return huntingFleet;
	}
	
	public HuntingFleetCursor queryHuntingFleetType(String type) {
		return mHelper.queryHuntingFleetType(type);
	}

	public HuntingFleetCursor queryHuntingFleetLocation(String location) {
		return mHelper.queryHuntingFleetLocation(location);
	}
	
/********************************* HUNTING REWARD QUERIES ******************************************/
	private long[] helperHuntingRewardMonster(long id) {
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(id);
		
		MonsterCursor monsterCursor = mHelper.queryMonster(id);
		monsterCursor.moveToFirst();
		
		String name = monsterCursor.getMonster().getName();
		
		monsterCursor = mHelper.queryMonsterTrait(name);
		monsterCursor.moveToFirst();
		
		while(!monsterCursor.isAfterLast()) {
			ids.add(monsterCursor.getMonster().getId());
			monsterCursor.moveToNext();
		}

		long[] idArray = new long[ids.size()];
		for (int i = 0; i < idArray.length; i++) {
			idArray[i] = ids.get(i);
		}
		
		return idArray;
	}
	
	public HuntingRewardCursor queryHuntingRewardItem(long id) {
		return mHelper.queryHuntingRewardItem(id);
	}

	public HuntingRewardCursor queryHuntingRewardMonster(long id) {
//		return mHelper.queryHuntingRewardMonster(id);
		return mHelper.queryHuntingRewardMonster(helperHuntingRewardMonster(id));
	}
	
	public HuntingRewardCursor queryHuntingRewardMonsterRank(long id, String rank) {
		return mHelper.queryHuntingRewardMonsterRank(helperHuntingRewardMonster(id), rank);
	}
	
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
	public ItemCursor queryItems() {
		return mHelper.queryItems();
	}
	
	public Item getItem(long id) {
		Item item = null;
		ItemCursor cursor = mHelper.queryItem(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			item = cursor.getItem();
		cursor.close();
		return item;
	}
	
	public ItemCursor queryItemSearch(String search) {
		return mHelper.queryItemSearch(search);
	}
	
/********************************* ITEM TO SKILL TREE QUERIES ******************************************/
	public ItemToSkillTreeCursor queryItemToSkillTreeItem(long id) {
		return mHelper.queryItemToSkillTreeItem(id);
	}

	public ItemToSkillTreeCursor queryItemToSkillTreeSkillTree(long id, String type) {
		return mHelper.queryItemToSkillTreeSkillTree(id, type);
	}
	
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
	
	public LocationCursor queryLocations() {
		return mHelper.queryLocations();
	}
	
	public Location getLocation(long id) {
		Location location = null;
		LocationCursor cursor = mHelper.queryLocation(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			location = cursor.getLocation();
		cursor.close();
		return location;
	}
	
/********************************* MOGA WOODS REWARD QUERIES ******************************************/
	public MogaWoodsRewardCursor queryMogaWoodsRewardItem(long id) {
		return mHelper.queryMogaWoodsRewardItem(id);
	}

	public MogaWoodsRewardCursor queryMogaWoodsRewardMonster(long id) {
		return mHelper.queryMogaWoodsRewardMonster(id);
	}
	
	public MogaWoodsRewardCursor queryMogaWoodsRewardMonsterTime(long id, String time) {
		return mHelper.queryMogaWoodsRewardMonsterTime(id, time);
	}
	
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
	
	public MonsterCursor queryMonsters() {
		return mHelper.queryMonsters();
	}
	
	public MonsterCursor querySmallMonsters() {
		return mHelper.querySmallMonsters();
	}
	
	public MonsterCursor queryLargeMonsters() {
		return mHelper.queryLargeMonsters();
	}
	
	public Monster getMonster(long id) {
		Monster monster = null;
		MonsterCursor cursor = mHelper.queryMonster(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			monster = cursor.getMonster();
		cursor.close();
		return monster;
	}
	
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
	
/********************************* MONSTER DAMAGE QUERIES ******************************************/	
	
	public MonsterDamageCursor queryMonsterDamage(long id) {
		return mHelper.queryMonsterDamage(id);
	}
	
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
	
/********************************* MONSTER TO ARENA QUERIES ******************************************/
	public MonsterToArenaCursor queryMonsterToArenaMonster(long id) {
		return mHelper.queryMonsterToArenaMonster(id);
	}

	public MonsterToArenaCursor queryMonsterToArenaArena(long id) {
		return mHelper.queryMonsterToArenaArena(id);
	}
	
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
	public MonsterToQuestCursor queryMonsterToQuestMonster(long id) {
		return mHelper.queryMonsterToQuestMonster(id);
	}

	public MonsterToQuestCursor queryMonsterToQuestQuest(long id) {
		return mHelper.queryMonsterToQuestQuest(id);
	}
	
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
	
/********************************* QUEST QUERIES ******************************************/	
	
	public QuestCursor queryQuests() {
		return mHelper.queryQuests();
	}
	
	public Quest getQuest(long id) {
		Quest quest = null;
		QuestCursor cursor = mHelper.queryQuest(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			quest = cursor.getQuest();
		cursor.close();
		return quest;
	}
	
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
	
	public QuestCursor queryQuestHub(String hub) {
		return mHelper.queryQuestHub(hub);
	}

	public QuestCursor queryQuestHubStar(String hub, String stars) {
		return mHelper.queryQuestHubStar(hub, stars);
	}
	
/********************************* QUEST REWARD QUERIES ******************************************/
	public QuestRewardCursor queryQuestRewardItem(long id) {
		return mHelper.queryQuestRewardItem(id);
	}

	public QuestRewardCursor queryQuestRewardQuest(long id) {
		return mHelper.queryQuestRewardQuest(id);
	}
	
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
	
	public SkillCursor querySkill(long id) {
		return mHelper.querySkill(id);
	}
	
	public SkillCursor querySkillFromTree(long id) {
		return mHelper.querySkillFromTree(id);
	}
	
	public ArrayList<Skill> querySkillArray(long id) {
		ArrayList<Skill> skills = new ArrayList<Skill>();
		SkillCursor cursor = mHelper.querySkill(id);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			skills.add(cursor.getSkill());
			cursor.moveToNext();
		}
		cursor.close();
		return skills;
	}
		
/********************************* SKILL TREE QUERIES ******************************************/	
	
	public SkillTreeCursor querySkillTrees() {
		return mHelper.querySkillTrees();
	}
	
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
	
	public WeaponCursor queryWeapon() {
		return mHelper.queryWeapon();
	}
	
	public Weapon getWeapon(long id) {
		Weapon weapon = null;
		WeaponCursor cursor = mHelper.queryWeapon(id);
		cursor.moveToFirst();
		
		if (!cursor.isAfterLast())
			weapon = cursor.getWeapon();
		cursor.close();
		return weapon;
	}
	
	public WeaponCursor queryWeaponType(String type) {
		return mHelper.queryWeaponType(type);
	}
		
	public WeaponCursor queryWeaponTree(long id) {
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add(id);
		
		long currentId = id;
		WeaponTreeCursor cursor = null;
		
		// Get ancestors
		do {
			cursor = mHelper.queryWeaponTreeParent(currentId);
			cursor.moveToFirst();
			
			if(cursor.isAfterLast())
				break;
			
			currentId = cursor.getWeapon().getId();
			ids.add(0, currentId);

		}
		while (true);
		
		// Get children
		currentId = id;
		
		cursor = mHelper.queryWeaponTreeChild(currentId);
		cursor.moveToFirst();
		
		if(!cursor.isAfterLast()) {
			for (int i = 0; i < cursor.getCount(); i++) {
				ids.add(cursor.getWeapon().getId());
				cursor.moveToNext();
			}
		}

		long[] idArray = new long[ids.size()];
		for (int i = 0; i < idArray.length; i++) {
			idArray[i] = ids.get(i);
		}

		return mHelper.queryWeapons(idArray);
		
	}
	
/********************************* WISHLIST QUERIES ******************************************/	
	
	public WishlistCursor queryWishlists() {
		return mHelper.queryWishlists();
	}
	
	public WishlistCursor queryWishlist(long id) {
		return mHelper.queryWishlist(id);
	}

	public void queryAddWishlist(String name) {
		mHelper.queryAddWishlist(name);
	}

	public void queryUpdateWishlist(long id, String name) {
		mHelper.queryUpdateWishlist(id, name);
	}

	public void queryDeleteWishlist(long id) {
		mHelper.queryDeleteWishlist(id);
	}

	public void queryCopyWishlist(long id, String name) {
//		long newId = mHelper.queryAddWishlist(name);
//		
//		WishlistDataCursor cursor = mHelper.queryWishlistData(id);
//		cursor.moveToFirst();
//		
//		while(!cursor.isAfterLast()) {
//			WishlistData wishlist = cursor.getWishlistData();
//			mHelper.queryAddWishlistDataAll(newId, wishlist.getItem().getId(), 
//					wishlist.getQuantity(), wishlist.getSatisfied());
//			cursor.moveToNext();
//		}
//		cursor.close();
//		
//		WishlistComponentCursor wcCursor = mHelper.queryWishlistComponents(id);
//		wcCursor.moveToFirst();
//
//		while(!wcCursor.isAfterLast()) {
//			WishlistComponent wishlist = wcCursor.getWishlistComponent();
//			mHelper.queryAddWishlistComponentAll(newId, wishlist.getItem().getId(), 
//					wishlist.getQuantity(), wishlist.getNotes());
//			wcCursor.moveToNext();
//		}
//		wcCursor.close();
	}
	
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

	public WishlistDataCursor queryWishlistData(long id) {
		return mHelper.queryWishlistData(id);
	}

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

		helperQueryAddWishlistData(wishlist_id, item_id, quantity, path);
		helperQueryUpdateWishlistSatisfied(wishlist_id);
	}
	
	private void helperQueryAddWishlistData(long wishlist_id, long item_id, int quantity, String path) {
		ComponentCursor cc = mHelper.queryComponentCreatedType(item_id, path);
		cc.moveToFirst();
		
		WishlistComponentCursor wc = null;
		
		while (!cc.isAfterLast()) {
			long component_id = cc.getComponent().getComponent().getId();
			int c_amt = (cc.getComponent().getQuantity()) * (quantity);
			
			wc = mHelper.queryWishlistComponent(wishlist_id, component_id);
			wc.moveToFirst();
			
			if (wc.isAfterLast()) {
				// Add new entry to wishlist_component
				mHelper.queryAddWishlistComponent(wishlist_id, component_id, c_amt);
			}
			else {
				// Update entry to wishlist_component
				long wc_id = wc.getWishlistComponent().getId();
				int old_amt = wc.getWishlistComponent().getQuantity();

				mHelper.queryUpdateWishlistComponentQuantity(wc_id, old_amt + c_amt);
			}
			
			cc.moveToNext();
		}
	}

	public void queryUpdateWishlistData(long id, int quantity) {
		
		// Update wishlist_component
		WishlistDataCursor wdCursor = mHelper.queryWishlistDataId(id);
		wdCursor.moveToFirst();
		
		WishlistData wd = wdCursor.getWishlistData();
		long wishlist_id = wd.getWishlistId();
		long item_id = wd.getItem().getId();
		int wd_old_quantity = wd.getQuantity();
		String path = wd.getPath();
		
		int diff_quantity = quantity - wd_old_quantity;
		
		ComponentCursor cc = mHelper.queryComponentCreatedType(item_id, path);
		cc.moveToFirst();
		
		while (!cc.isAfterLast()) {
			long component_id = cc.getComponent().getComponent().getId();
			int c_amt = (cc.getComponent().getQuantity()) * (diff_quantity);
			
			WishlistComponentCursor wc = mHelper.queryWishlistComponent(wishlist_id, component_id);
			wc.moveToFirst();
			
			// Update entry to wishlist_component
			long wc_id = wc.getWishlistComponent().getId();
			int old_amt = wc.getWishlistComponent().getQuantity();
			
			mHelper.queryUpdateWishlistComponentQuantity(wc_id, old_amt + c_amt);
			
			cc.moveToNext();
		}

		mHelper.queryUpdateWishlistDataQuantity(id, quantity);
		helperQueryUpdateWishlistSatisfied(wishlist_id);
	}

	public void queryDeleteWishlistData(long id) {
		
		// Update wishlist_component
		WishlistDataCursor wdCursor = mHelper.queryWishlistDataId(id);
		wdCursor.moveToFirst();
		
		WishlistData wd = wdCursor.getWishlistData();
		long wishlist_id = wd.getWishlistId();
		long item_id = wd.getItem().getId();
		int wd_old_quantity = wd.getQuantity();
		String path = wd.getPath();
		
		ComponentCursor cc = mHelper.queryComponentCreatedType(item_id, path);
		cc.moveToFirst();
		
		while (!cc.isAfterLast()) {
			long component_id = cc.getComponent().getComponent().getId();
			int c_amt = (cc.getComponent().getQuantity()) * (wd_old_quantity);
			
			WishlistComponentCursor wc = mHelper.queryWishlistComponent(wishlist_id, component_id);
			wc.moveToFirst();
			
			// Update entry to wishlist_component
			long wc_id = wc.getWishlistComponent().getId();
			int old_amt = wc.getWishlistComponent().getQuantity();
			
			int new_amt = old_amt - c_amt;
			
			if (new_amt > 0) {
				mHelper.queryUpdateWishlistComponentQuantity(wc_id, old_amt - c_amt);
			}
			else {
				mHelper.queryDeleteWishlistComponent(wc_id);
			}
			
			cc.moveToNext();
		}
		
		mHelper.queryDeleteWishlistData(id);
	}
	
	
	public int queryWishlistPrice(long id) {
		int total = 0;
		WishlistDataCursor wdc = mHelper.queryWishlistData(id);
		wdc.moveToFirst();
		
		int buy;
		int quantity = 0;
		
		while(!wdc.isAfterLast()) {
			buy = -1;
			WishlistData wd = wdc.getWishlistData();
			Item i = wd.getItem();
			String type = wd.getPath();
			
			if ((i.getType()).equals("Weapon")) {
				WeaponCursor wc = mHelper.queryWeapon(i.getId());
				wc.moveToFirst();
				
				if (type.equals("Create"))
					buy = wc.getWeapon().getCreationCost();
				else if (type.equals("Improve")) {
					buy = wc.getWeapon().getUpgradeCost();
				}
			}
			else {
				buy = wd.getItem().getBuy();
			}
			
			if (buy == -1) {
				buy = 0;
			}
			
			quantity = wd.getQuantity();
			total = total + (buy * quantity);
			
			wdc.moveToNext();
		}
		
		return total;
	}
	
/********************************* WISHLIST COMPONENT QUERIES ******************************************/	

	public WishlistComponentCursor queryWishlistComponents(long id) {
		return mHelper.queryWishlistComponents(id);
	}	
	
	public void queryUpdateWishlistComponentNotes(long id, int notes) {	
		mHelper.queryUpdateWishlistComponentNotes(id, notes);
		WishlistComponentCursor wcc = mHelper.queryWishlistComponentId(id);
		wcc.moveToFirst();
		long w_id = wcc.getWishlistComponent().getWishlistId();
		
		helperQueryUpdateWishlistSatisfied(w_id);
	}
	
	private void helperQueryUpdateWishlistSatisfied(long wishlist_id) {
		WishlistDataCursor wdc = mHelper.queryWishlistData(wishlist_id);
		wdc.moveToFirst();
		
		WishlistCursor cur = mHelper.queryWishlist(wishlist_id);
		cur.moveToFirst();
		
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
		int satisfied = 1;
		
		while(!wdc.isAfterLast()) {
			wd = wdc.getWishlistData();
			created_id = wd.getItem().getId();
			path = wd.getPath();
			
			cc = mHelper.queryComponentCreatedType(created_id, path);
			cc.moveToFirst();
			
			while(!cc.isAfterLast()) {
				c = cc.getComponent();
				component_id = c.getComponent().getId();
				
				wcc = mHelper.queryWishlistComponent(wishlist_id, component_id);
				wcc.moveToFirst();
				wc = wcc.getWishlistComponent();
				
				required_amt = c.getQuantity();
				have_amt = wc.getNotes();

				// Check if created item has enough materials
				if (have_amt < required_amt) {
					satisfied = 0;
					break;
				}
				
				wcc.close();	
				cc.moveToNext();
			}
			
			cc.close();
			
			mHelper.queryUpdateWishlistDataSatisfied(wd.getId(), satisfied);
			wdc.moveToNext();
		}
		
		wdc.close();
	}
}
