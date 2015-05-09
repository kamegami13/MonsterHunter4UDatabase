package com.daviancorp.android.data.database;

/*
 * Class that only has constant variables
 * 
 * Note: Do not need to instantiate to use
 */
public class S {
	// Item sections ---------- DEPRECATED AND A BAD IDEA FROM THE START
	public static final long SECTION_DECORATIONS = 1118;
	
	public static final long SECTION_HEAD = 1314;
	public static final long SECTION_BODY = 1646;
	public static final long SECTION_ARMS = 1983;
	public static final long SECTION_WAIST = 2303;
	public static final long SECTION_LEGS = 2623;
	public static final long SECTION_ARMOR = SECTION_HEAD;
	
	public static final long SECTION_GREAT_SWORD = 2955;
	public static final long SECTION_HUNTING_HORN = 3090;
	public static final long SECTION_LONG_SWORD = 3191;
	public static final long SECTION_SWORD_AND_SHIELD = 3305;
	public static final long SECTION_DUAL_BLADES = 3445;
	public static final long SECTION_HAMMER = 3570;
	public static final long SECTION_LANCE = 3704;
	public static final long SECTION_GUNLANCE = 3849;
	public static final long SECTION_SWITCH_AXE = 3961;
	public static final long SECTION_LIGHT_BOWGUN = 4074;
	public static final long SECTION_HEAVY_BOWGUN = 4170;
	public static final long SECTION_BOW = 4261;
	public static final long SECTION_WEAPON = SECTION_GREAT_SWORD;
	public static final long SECTION_BLADE = SECTION_GREAT_SWORD;
	public static final long SECTION_BOWGUN = SECTION_LIGHT_BOWGUN;
	
	// Arena Quests
	static final String TABLE_ARENA_QUESTS = "arena_quests";
	static final String COLUMN_ARENA_QUESTS_ID = "_id";
	static final String COLUMN_ARENA_QUESTS_NAME = "name";
	static final String COLUMN_ARENA_QUESTS_GOAL = "goal";
	static final String COLUMN_ARENA_QUESTS_LOCATION_ID = "location_id";
	static final String COLUMN_ARENA_QUESTS_REWARD = "reward";
	static final String COLUMN_ARENA_QUESTS_NUM_PARTICIPANTS = "num_participants";
	static final String COLUMN_ARENA_QUESTS_TIME_S = "time_s";
	static final String COLUMN_ARENA_QUESTS_TIME_A = "time_a";
	static final String COLUMN_ARENA_QUESTS_TIME_B = "time_b";

	// Arena Rewards
	static final String TABLE_ARENA_REWARDS = "arena_rewards";
	static final String COLUMN_ARENA_REWARDS_ID = "_id";
	static final String COLUMN_ARENA_REWARDS_ARENA_ID = "arena_id";
	static final String COLUMN_ARENA_REWARDS_ITEM_ID = "item_id";
	static final String COLUMN_ARENA_REWARDS_PERCENTAGE = "percentage";
	static final String COLUMN_ARENA_REWARDS_STACK_SIZE = "stack_size";
	
	// Armor
	static final String TABLE_ARMOR = "armor";
	static final String COLUMN_ARMOR_ID = "_id";
	static final String COLUMN_ARMOR_SLOT = "slot";
	static final String COLUMN_ARMOR_DEFENSE = "defense";
	static final String COLUMN_ARMOR_MAX_DEFENSE = "max_defense";
	static final String COLUMN_ARMOR_FIRE_RES = "fire_res";
	static final String COLUMN_ARMOR_THUNDER_RES = "thunder_res";
	static final String COLUMN_ARMOR_DRAGON_RES = "dragon_res";
	static final String COLUMN_ARMOR_WATER_RES = "water_res";
	static final String COLUMN_ARMOR_ICE_RES = "ice_res";
	static final String COLUMN_ARMOR_GENDER = "gender";
	static final String COLUMN_ARMOR_HUNTER_TYPE = "hunter_type";
	static final String COLUMN_ARMOR_NUM_SLOTS = "num_slots";

	// Charms
	static final String TABLE_CHARMS = "charms";
	static final String COLUMN_CHARMS_ID = "_id";
	static final String COLUMN_CHARMS_NUM_SLOTS = "num_slots";
	static final String COLUMN_CHARMS_SKILL_TREE_1_ID = "skill_tree_1_id";
	static final String COLUMN_CHARMS_SKILL_TREE_1_AMOUNT = "skill_tree_1_amount";
	static final String COLUMN_CHARMS_SKILL_TREE_2_ID = "skill_tree_2_id";
	static final String COLUMN_CHARMS_SKILL_TREE_2_AMOUNT = "skill_tree_2_amount";
	
	// Combining
	static final String TABLE_COMBINING = "combining";
	static final String COLUMN_COMBINING_ID = "_id";
	static final String COLUMN_COMBINING_CREATED_ITEM_ID = "created_item_id";
	static final String COLUMN_COMBINING_ITEM_1_ID = "item_1_id";
	static final String COLUMN_COMBINING_ITEM_2_ID = "item_2_id";
	static final String COLUMN_COMBINING_AMOUNT_MADE_MIN = "amount_made_min";
	static final String COLUMN_COMBINING_AMOUNT_MADE_MAX = "amount_made_max";
	static final String COLUMN_COMBINING_PERCENTAGE = "percentage";
	
	// Components
	static final String TABLE_COMPONENTS = "components";
	static final String COLUMN_COMPONENTS_ID = "_id";
	static final String COLUMN_COMPONENTS_CREATED_ITEM_ID = "created_item_id";
	static final String COLUMN_COMPONENTS_COMPONENT_ITEM_ID = "component_item_id";
	static final String COLUMN_COMPONENTS_QUANTITY = "quantity";
	static final String COLUMN_COMPONENTS_TYPE = "type";
	
	// Decorations
	static final String TABLE_DECORATIONS = "decorations";
	static final String COLUMN_DECORATIONS_ID = "_id";
	static final String COLUMN_DECORATIONS_NUM_SLOTS = "num_slots";
	
	// Gathering
	static final String TABLE_GATHERING = "gathering";
	static final String COLUMN_GATHERING_ID = "_id";
	static final String COLUMN_GATHERING_ITEM_ID = "item_id";
	static final String COLUMN_GATHERING_LOCATION_ID = "location_id";
	static final String COLUMN_GATHERING_AREA = "area";
	static final String COLUMN_GATHERING_SITE = "site";
	static final String COLUMN_GATHERING_RANK = "rank";
    static final String COLUMN_GATHERING_RATE = "percentage";
	
	// Hunting Fleet
	static final String TABLE_HUNTING_FLEET = "hunting_fleet";
	static final String COLUMN_HUNTING_FLEET_ID = "_id";
	static final String COLUMN_HUNTING_FLEET_TYPE = "type";
	static final String COLUMN_HUNTING_FLEET_LEVEL = "level";
	static final String COLUMN_HUNTING_FLEET_LOCATION = "location";
	static final String COLUMN_HUNTING_FLEET_ITEM_ID = "item_id";
	static final String COLUMN_HUNTING_FLEET_AMOUNT = "amount";
	static final String COLUMN_HUNTING_FLEET_PERCENTAGE = "percentage";
	static final String COLUMN_HUNTING_FLEET_RANK = "rank";
	
	// Hunting Rewards
	static final String TABLE_HUNTING_REWARDS = "hunting_rewards";
	static final String COLUMN_HUNTING_REWARDS_ID = "_id";
	static final String COLUMN_HUNTING_REWARDS_ITEM_ID = "item_id";
	static final String COLUMN_HUNTING_REWARDS_CONDITION = "condition";
	static final String COLUMN_HUNTING_REWARDS_MONSTER_ID = "monster_id";
	static final String COLUMN_HUNTING_REWARDS_RANK = "rank";
	static final String COLUMN_HUNTING_REWARDS_STACK_SIZE = "stack_size";
	static final String COLUMN_HUNTING_REWARDS_PERCENTAGE = "percentage";
	
	// Items
	static final String TABLE_ITEMS = "items";
	static final String COLUMN_ITEMS_ID = "_id";
	static final String COLUMN_ITEMS_NAME = "name";
	static final String COLUMN_ITEMS_JPN_NAME = "name_jp";
	static final String COLUMN_ITEMS_TYPE = "type";
    static final String COLUMN_ITEMS_SUB_TYPE = "sub_type";
	static final String COLUMN_ITEMS_RARITY = "rarity";
	static final String COLUMN_ITEMS_CARRY_CAPACITY = "carry_capacity";
	static final String COLUMN_ITEMS_BUY = "buy";
	static final String COLUMN_ITEMS_SELL = "sell";
	static final String COLUMN_ITEMS_DESCRIPTION = "description";
	static final String COLUMN_ITEMS_ICON_NAME = "icon_name";
	static final String COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX = "armor_dupe_name_fix";
	
	// Item to Skill Tree
	static final String TABLE_ITEM_TO_SKILL_TREE = "item_to_skill_tree";
	static final String COLUMN_ITEM_TO_SKILL_TREE_ID = "_id";
	static final String COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID = "item_id";
	static final String COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID = "skill_tree_id";
	static final String COLUMN_ITEM_TO_SKILL_TREE_POINT_VALUE = "point_value";
	
	// Locations
	static final String TABLE_LOCATIONS = "locations";
	static final String COLUMN_LOCATIONS_ID = "_id";
	static final String COLUMN_LOCATIONS_NAME = "name";
	static final String COLUMN_LOCATIONS_MAP = "map";
	
	// Moga Woods Rewards
	static final String TABLE_MOGA_WOODS_REWARDS = "moga_woods_rewards";
	static final String COLUMN_MOGA_WOODS_REWARDS_ID = "_id";
	static final String COLUMN_MOGA_WOODS_REWARDS_MONSTER_ID = "monster_id";
	static final String COLUMN_MOGA_WOODS_REWARDS_TIME = "time";
	static final String COLUMN_MOGA_WOODS_REWARDS_ITEM_ID = "item_id";
	static final String COLUMN_MOGA_WOODS_REWARDS_COMMODITY_STARS = "commodity_stars";
	static final String COLUMN_MOGA_WOODS_REWARDS_KILL_PERCENTAGE = "kill_percentage";
	static final String COLUMN_MOGA_WOODS_REWARDS_CAPTURE_PERCENTAGE = "capture_percentage";
	
	// Monster
	static final String TABLE_MONSTERS = "monsters";
	static final String COLUMN_MONSTERS_ID = "_id";
	static final String COLUMN_MONSTERS_NAME = "name";
	static final String COLUMN_MONSTERS_CLASS = "class";
	static final String COLUMN_MONSTERS_TRAIT = "trait";
	static final String COLUMN_MONSTERS_FILE_LOCATION = "icon_name";
	static final String COLUMN_MONSTERS_SORT_NAME = "sort_name";

    // Monster Habitat
    static final String TABLE_HABITAT = "monster_habitat";
    static final String COLUMN_HABITAT_ID = "_id";
    static final String COLUMN_HABITAT_LOCATION_ID = "location_id";
    static final String COLUMN_HABITAT_MONSTER_ID = "monster_id";
    static final String COLUMN_HABITAT_START = "start_area";
    static final String COLUMN_HABITAT_AREAS = "move_area";
    static final String COLUMN_HABITAT_REST = "rest_area";

	// Monster Ailment
	static final String TABLE_AILMENT = "monster_ailment";
	static final String COLUMN_AILMENT_ID = "_id";
	static final String COLUMN_AILMENT_MONSTER_ID = "monster_id";
	static final String COLUMN_AILMENT_MONSTER_NAME = "monster_name";
	static final String COLUMN_AILMENT_AILMENT = "ailment";

	// Monster Weakness
	static final String TABLE_WEAKNESS = "monster_weakness";
	static final String COLUMN_WEAKNESS_ID = "_id";
	static final String COLUMN_WEAKNESS_MONSTER_ID = "monster_id";
	static final String COLUMN_WEAKNESS_MONSTER_NAME = "monster_name";
	static final String COLUMN_WEAKNESS_STATE = "state";
	static final String COLUMN_WEAKNESS_FIRE = "fire";
	static final String COLUMN_WEAKNESS_WATER = "water";
	static final String COLUMN_WEAKNESS_THUNDER = "thunder";
	static final String COLUMN_WEAKNESS_ICE = "ice";
	static final String COLUMN_WEAKNESS_DRAGON = "dragon";
	static final String COLUMN_WEAKNESS_POISON = "poison";
	static final String COLUMN_WEAKNESS_PARALYSIS = "paralysis";
	static final String COLUMN_WEAKNESS_SLEEP = "sleep";
	static final String COLUMN_WEAKNESS_PITFALL_TRAP = "pitfall_trap";
	static final String COLUMN_WEAKNESS_SHOCK_TRAP = "shock_trap";
	static final String COLUMN_WEAKNESS_FLASH_BOMB = "flash_bomb";
	static final String COLUMN_WEAKNESS_SONIC_BOMB = "sonic_bomb";
	static final String COLUMN_WEAKNESS_DUNG_BOMB = "dung_bomb";
	static final String COLUMN_WEAKNESS_MEAT = "meat";

    // Monster Status
    static final String TABLE_MONSTER_STATUS = "monster_status";
    static final String COLUMN_MONSTER_STATUS_MONSTER_ID = "monster_id";
    static final String COLUMN_MONSTER_STATUS_STATUS = "status";
    static final String COLUMN_MONSTER_STATUS_INITIAL = "initial";
    static final String COLUMN_MONSTER_STATUS_INCREASE = "increase";
    static final String COLUMN_MONSTER_STATUS_MAX = "max";
    static final String COLUMN_MONSTER_STATUS_DURATION = "duration";
    static final String COLUMN_MONSTER_STATUS_DAMAGE = "damage";

	// Monster Damage
	static final String TABLE_MONSTER_DAMAGE = "monster_damage";
	static final String COLUMN_MONSTER_DAMAGE_ID = "_id";
	static final String COLUMN_MONSTER_DAMAGE_MONSTER_ID = "monster_id";
	static final String COLUMN_MONSTER_DAMAGE_BODY_PART = "body_part";
	static final String COLUMN_MONSTER_DAMAGE_CUT = "cut";
	static final String COLUMN_MONSTER_DAMAGE_IMPACT = "impact";
	static final String COLUMN_MONSTER_DAMAGE_SHOT = "shot";
	static final String COLUMN_MONSTER_DAMAGE_FIRE = "fire";
	static final String COLUMN_MONSTER_DAMAGE_WATER = "water";
	static final String COLUMN_MONSTER_DAMAGE_ICE = "ice";
	static final String COLUMN_MONSTER_DAMAGE_THUNDER = "thunder";
	static final String COLUMN_MONSTER_DAMAGE_DRAGON = "dragon";
	static final String COLUMN_MONSTER_DAMAGE_KO = "ko";

	// Monster to Arena
	static final String TABLE_MONSTER_TO_ARENA = "monster_to_arena";
	static final String COLUMN_MONSTER_TO_ARENA_ID = "_id";
	static final String COLUMN_MONSTER_TO_ARENA_MONSTER_ID = "monster_id";
	static final String COLUMN_MONSTER_TO_ARENA_ARENA_ID = "arena_id";
	
	// Monster to Quest
	static final String TABLE_MONSTER_TO_QUEST = "monster_to_quest";
	static final String COLUMN_MONSTER_TO_QUEST_ID = "_id";
	static final String COLUMN_MONSTER_TO_QUEST_MONSTER_ID = "monster_id";
	static final String COLUMN_MONSTER_TO_QUEST_QUEST_ID = "quest_id";
	static final String COLUMN_MONSTER_TO_QUEST_UNSTABLE = "unstable"; 
	
	// Planting
	static final String TABLE_PLANTING = "planting";
	static final String COLUMN_PLANTING_ID = "_id";
	static final String COLUMN_PLANTING_PLANTED_ITEM_ID = "planted_item_id";
	static final String COLUMN_PLANTING_RECEIVED_ITEM_ID = "received_item_id";
	static final String COLUMN_PLANTING_STACK_SIZE = "stack_size";
	static final String COLUMN_PLANTING_PERCENTAGE = "percentage";
	static final String COLUMN_PLANTING_POOL_TYPE = "pool_type";
	
	// Quests
	static final String TABLE_QUESTS = "quests";
	static final String COLUMN_QUESTS_ID = "_id";
	static final String COLUMN_QUESTS_NAME = "name";
	static final String COLUMN_QUESTS_GOAL = "goal";
	static final String COLUMN_QUESTS_HUB = "hub";
	static final String COLUMN_QUESTS_TYPE = "type";
	static final String COLUMN_QUESTS_STARS = "stars";
	static final String COLUMN_QUESTS_LOCATION_ID = "location_id";
	static final String COLUMN_QUESTS_LOCATION_TIME = "location_time";
	static final String COLUMN_QUESTS_TIME_LIMIT = "time_limit";
	static final String COLUMN_QUESTS_FEE = "fee";
	static final String COLUMN_QUESTS_REWARD = "reward";
	static final String COLUMN_QUESTS_HRP = "hrp";
    static final String COLUMN_QUESTS_SUB_GOAL = "sub_goal";
    static final String COLUMN_QUESTS_SUB_REWARD = "sub_reward";
    static final String COLUMN_QUESTS_SUB_HRP = "sub_hrp";

    // Quest Pre-Requirements
    static final String TABLE_QUEST_PREREQS = "quest_prereqs";
    static final String COLUMN_QUEST_PREREQS_ID = "_id";
    static final String COLUMN_QUEST_PREREQS_QUEST_ID = "quest_id";
    static final String COLUMN_QUEST_PREREQS_PREREQ_ID = "prereq_id";
	
	// Quest Rewards
	static final String TABLE_QUEST_REWARDS = "quest_rewards";
	static final String COLUMN_QUEST_REWARDS_ID = "_id";
	static final String COLUMN_QUEST_REWARDS_QUEST_ID = "quest_id";
	static final String COLUMN_QUEST_REWARDS_ITEM_ID = "item_id";
	static final String COLUMN_QUEST_REWARDS_REWARD_SLOT = "reward_slot";
	static final String COLUMN_QUEST_REWARDS_PERCENTAGE = "percentage";
	static final String COLUMN_QUEST_REWARDS_STACK_SIZE = "stack_size";
	
	// Skills
	static final String TABLE_SKILLS = "skills";
	static final String COLUMN_SKILLS_ID = "_id";
	static final String COLUMN_SKILLS_SKILL_TREE_ID = "skill_tree_id";
	static final String COLUMN_SKILLS_REQUIRED_SKILL_TREE_POINTS = "required_skill_tree_points";
	static final String COLUMN_SKILLS_NAME = "name";
	static final String COLUMN_SKILLS_JPN_NAME = "name_jp";
	static final String COLUMN_SKILLS_DESCRIPTION = "description";
	
	// Skill Trees
	static final String TABLE_SKILL_TREES = "skill_trees";
	static final String COLUMN_SKILL_TREES_ID = "_id";
	static final String COLUMN_SKILL_TREES_NAME = "name";
	static final String COLUMN_SKILL_TREES_JPN_NAME = "name_jp";
	
	// Trading
	static final String TABLE_TRADING = "trading";
	static final String COLUMN_TRADING_ID = "_id";
	static final String COLUMN_TRADING_LOCATION_ID = "location_id";
	static final String COLUMN_TRADING_OFFER_ITEM_ID = "offer_item_id";
	static final String COLUMN_TRADING_RECEIVE_ITEM_ID = "receive_item_id";
	static final String COLUMN_TRADING_PERCENTAGE = "percentage";
	
	// Weapons
	static final String TABLE_WEAPONS = "weapons";
	static final String COLUMN_WEAPONS_ID = "_id";
	static final String COLUMN_WEAPONS_WTYPE = "wtype";
	static final String COLUMN_WEAPONS_CREATION_COST = "creation_cost";
	static final String COLUMN_WEAPONS_UPGRADE_COST = "upgrade_cost";
	static final String COLUMN_WEAPONS_ATTACK = "attack";
	static final String COLUMN_WEAPONS_MAX_ATTACK = "max_attack";
	static final String COLUMN_WEAPONS_ELEMENT = "element";
	static final String COLUMN_WEAPONS_AWAKEN = "awaken";
    static final String COLUMN_WEAPONS_ELEMENT_2 = "element_2";
    static final String COLUMN_WEAPONS_AWAKEN_ATTACK = "awaken_attack";
    static final String COLUMN_WEAPONS_ELEMENT_ATTACK = "element_attack";
    static final String COLUMN_WEAPONS_ELEMENT_2_ATTACK = "element_2_attack";
	static final String COLUMN_WEAPONS_DEFENSE = "defense";
	static final String COLUMN_WEAPONS_SHARPNESS = "sharpness";
	static final String COLUMN_WEAPONS_AFFINITY = "affinity";
	static final String COLUMN_WEAPONS_HORN_NOTES = "horn_notes";
	static final String COLUMN_WEAPONS_SHELLING_TYPE = "shelling_type";
	static final String COLUMN_WEAPONS_PHIAL = "phial";
	static final String COLUMN_WEAPONS_CHARGES = "charges";
	static final String COLUMN_WEAPONS_COATINGS = "coatings";
	static final String COLUMN_WEAPONS_RECOIL = "recoil";
	static final String COLUMN_WEAPONS_RELOAD_SPEED = "reload_speed";
	static final String COLUMN_WEAPONS_RAPID_FIRE = "rapid_fire";
	static final String COLUMN_WEAPONS_DEVIATION = "deviation";
	static final String COLUMN_WEAPONS_AMMO = "ammo";
	static final String COLUMN_WEAPONS_NUM_SLOTS = "num_slots";
	static final String COLUMN_WEAPONS_SHARPNESS_FILE = "sharpness_file";
	static final String COLUMN_WEAPONS_FINAL = "final";
    static final String COLUMN_WEAPONS_TREE_DEPTH = "tree_depth";
    static final String COLUMN_WEAPONS_PARENT_ID = "parent_id";

    // Horn Melodies
    static final String TABLE_HORN_MELODIES = "horn_melodies";
    static final String COLUMN_HORN_MELODIES_ID = "_id";
    static final String COLUMN_HORN_MELODIES_NOTES = "notes";
    static final String COLUMN_HORN_MELODIES_SONG = "song";
    static final String COLUMN_HORN_MELODIES_EFFECT_1 = "effect1";
    static final String COLUMN_HORN_MELODIES_EFFECT_2 = "effect2";
    static final String COLUMN_HORN_MELODIES_DURATION = "duration";
    static final String COLUMN_HORN_MELODIES_EXTENSION = "extension";

	// Wishlist
	static final String TABLE_WISHLIST = "wishlist";
	static final String COLUMN_WISHLIST_ID = "_id";
	static final String COLUMN_WISHLIST_NAME = "name";

	// Wishlist Data
	static final String TABLE_WISHLIST_DATA = "wishlist_data";
	static final String COLUMN_WISHLIST_DATA_ID = "_id";
	static final String COLUMN_WISHLIST_DATA_WISHLIST_ID = "wishlist_id";
	static final String COLUMN_WISHLIST_DATA_ITEM_ID = "item_id";
	static final String COLUMN_WISHLIST_DATA_QUANTITY = "quantity";
	static final String COLUMN_WISHLIST_DATA_SATISFIED = "satisfied";
	static final String COLUMN_WISHLIST_DATA_PATH = "path";

	// Wishlist Component
	static final String TABLE_WISHLIST_COMPONENT = "wishlist_component";
	static final String COLUMN_WISHLIST_COMPONENT_ID = "_id";
	static final String COLUMN_WISHLIST_COMPONENT_WISHLIST_ID = "wishlist_id";
	static final String COLUMN_WISHLIST_COMPONENT_COMPONENT_ID = "component_id";
	static final String COLUMN_WISHLIST_COMPONENT_QUANTITY = "quantity";
	static final String COLUMN_WISHLIST_COMPONENT_NOTES = "notes";

    // Wyporium Trades
    static final String TABLE_WYPORIUM_TRADE = "wyporium";
    static final String COLUMN_WYPORIUM_TRADE_ID = "_id";
    static final String COLUMN_WYPORIUM_TRADE_ITEM_IN_ID = "item_in_id";
    static final String COLUMN_WYPORIUM_TRADE_ITEM_OUT_ID = "item_out_id";
    static final String COLUMN_WYPORIUM_TRADE_UNLOCK_QUEST_ID = "unlock_quest_id";

	// Armor Sets
	static final String TABLE_ASB_SETS = "asb_sets";

	static final String COLUMN_ASB_SET_ID = "_id";

	static final String COLUMN_ASB_SET_NAME = "name";
	static final String COLUMN_ASB_SET_RANK = "rank";
	static final String COLUMN_ASB_SET_HUNTER_TYPE = "hunter_type";
	
	static final String COLUMN_HEAD_ARMOR_ID = "head_armor";
	static final String COLUMN_HEAD_DECORATION_1_ID = "head_decoration_1";
	static final String COLUMN_HEAD_DECORATION_2_ID = "head_decoration_2";
	static final String COLUMN_HEAD_DECORATION_3_ID = "head_decoration_3";
	
	static final String COLUMN_BODY_ARMOR_ID = "body_armor";
	static final String COLUMN_BODY_DECORATION_1_ID = "body_decoration_1";
	static final String COLUMN_BODY_DECORATION_2_ID = "body_decoration_2";
	static final String COLUMN_BODY_DECORATION_3_ID = "body_decoration_3";
	
	static final String COLUMN_ARMS_ARMOR_ID = "arms_armor";
	static final String COLUMN_ARMS_DECORATION_1_ID = "arms_decoration_1";
	static final String COLUMN_ARMS_DECORATION_2_ID = "arms_decoration_2";
	static final String COLUMN_ARMS_DECORATION_3_ID = "arms_decoration_3";
	
	static final String COLUMN_WAIST_ARMOR_ID = "waist_armor";
	static final String COLUMN_WAIST_DECORATION_1_ID = "waist_decoration_1";
	static final String COLUMN_WAIST_DECORATION_2_ID = "waist_decoration_2";
	static final String COLUMN_WAIST_DECORATION_3_ID = "waist_decoration_3";
	
	static final String COLUMN_LEGS_ARMOR_ID = "legs_armor";
	static final String COLUMN_LEGS_DECORATION_1_ID = "legs_decoration_1";
	static final String COLUMN_LEGS_DECORATION_2_ID = "legs_decoration_2";
	static final String COLUMN_LEGS_DECORATION_3_ID = "legs_decoration_3";

	static final String COLUMN_TALISMAN_EXISTS = "talisman_exists";
	static final String COLUMN_TALISMAN_TYPE = "talisman_type";
	static final String COLUMN_TALISMAN_SLOTS = "talisman_slots";
	static final String COLUMN_TALISMAN_DECORATION_1_ID = "talisman_decoration_1";
	static final String COLUMN_TALISMAN_DECORATION_2_ID = "talisman_decoration_2";
	static final String COLUMN_TALISMAN_DECORATION_3_ID = "talisman_decoration_3";
	static final String COLUMN_TALISMAN_SKILL_1_ID = "talisman_skill_1";
	static final String COLUMN_TALISMAN_SKILL_1_POINTS = "talisman_skill_1_points";
	static final String COLUMN_TALISMAN_SKILL_2_ID = "talisman_skill_2";
	static final String COLUMN_TALISMAN_SKILL_2_POINTS = "talisman_skill_2_points";
}
