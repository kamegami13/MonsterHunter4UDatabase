package com.daviancorp.android.data.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Xml;

import com.daviancorp.android.data.classes.ASBSession;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/*
   QUERY REFERENCE:

For queries with no JOINs:
	- call wrapHelper()
	- set values for
			_Distinct
			_Table
			_Columns
			_Selection
			_SelectionArgs
			_GroupBy
			_Having
			_OrderBy
			_Limit

For queries with JOINs:
	- call wrapJoinHelper(SQLiteQueryBuilder qb)
	= set values for
		_Columns
		_Selection
		_SelectionArgs
		_GroupBy
		_Having
		_OrderBy
		_Limit

*/

class MonsterHunterDatabaseHelper extends SQLiteAssetHelper {
    private static final String TAG = "MonsterHunterDatabaseHelper";

    private static MonsterHunterDatabaseHelper mInstance = null;

    private static final String DATABASE_NAME = "mh4u.db";
    private static final int DATABASE_VERSION = 12;

    private final Context myContext;
    private SQLiteDatabase myDataBase;

    /**
     * Returns Singleton instance of the helper object
     * @param c Application context
     * @return Singleton instance of helper
     */
    public static MonsterHunterDatabaseHelper getInstance(Context c) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new MonsterHunterDatabaseHelper(c.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Initialize the helper object
     * @param context
     */
    private MonsterHunterDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;

        setForcedUpgrade();

		/*try {
            createDatabase();
		} catch (IOException e) {
			throw new Error("Error copying database");
		}*/
    }

    public boolean isTableExists(String tableName, SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    private String replaceNull(String str) {
        return str == null ? "" : str;
    }

    @Override
    protected void preCopyDatabase(SQLiteDatabase db) {
        //Log.w(TAG, "Pre forcing database upgrade!");
        String filename = "wishlist.xml";
        FileOutputStream fos;

        try {
            String[] asb_set_columns = {S.COLUMN_ASB_SET_NAME, S.COLUMN_ASB_SET_RANK, S.COLUMN_ASB_SET_HUNTER_TYPE, S.COLUMN_HEAD_ARMOR_ID, S.COLUMN_HEAD_DECORATION_1_ID, S.COLUMN_HEAD_DECORATION_2_ID,
                    S.COLUMN_HEAD_DECORATION_3_ID, S.COLUMN_BODY_ARMOR_ID, S.COLUMN_BODY_DECORATION_1_ID, S.COLUMN_BODY_DECORATION_2_ID, S.COLUMN_BODY_DECORATION_3_ID, S.COLUMN_ARMS_ARMOR_ID,
                    S.COLUMN_ARMS_DECORATION_1_ID, S.COLUMN_ARMS_DECORATION_2_ID, S.COLUMN_ARMS_DECORATION_3_ID, S.COLUMN_WAIST_ARMOR_ID, S.COLUMN_WAIST_DECORATION_1_ID, S.COLUMN_WAIST_DECORATION_2_ID, S.COLUMN_WAIST_DECORATION_3_ID,
                    S.COLUMN_LEGS_ARMOR_ID, S.COLUMN_LEGS_DECORATION_1_ID, S.COLUMN_LEGS_DECORATION_2_ID, S.COLUMN_LEGS_DECORATION_3_ID, S.COLUMN_TALISMAN_EXISTS, S.COLUMN_TALISMAN_TYPE, S.COLUMN_TALISMAN_SLOTS, S.COLUMN_TALISMAN_DECORATION_1_ID,
                    S.COLUMN_TALISMAN_DECORATION_2_ID, S.COLUMN_TALISMAN_DECORATION_3_ID, S.COLUMN_TALISMAN_SKILL_1_ID, S.COLUMN_TALISMAN_SKILL_1_POINTS, S.COLUMN_TALISMAN_SKILL_2_ID, S.COLUMN_TALISMAN_SKILL_2_POINTS};
            List<String> asb_set_columns_list = Arrays.asList(asb_set_columns);

            String[] wishlist_columns = {S.COLUMN_WISHLIST_ID, S.COLUMN_WISHLIST_NAME};
            List<String> wishlist_columns_list = Arrays.asList(wishlist_columns);

            String[] wishlist_data_columns = {S.COLUMN_WISHLIST_DATA_ID, S.COLUMN_WISHLIST_DATA_WISHLIST_ID, S.COLUMN_WISHLIST_DATA_ITEM_ID, S.COLUMN_WISHLIST_DATA_QUANTITY, S.COLUMN_WISHLIST_DATA_SATISFIED, S.COLUMN_WISHLIST_DATA_PATH};
            List<String> wishlist_data_columns_list = Arrays.asList(wishlist_data_columns);

            String[] wishlist_component_columns = {S.COLUMN_WISHLIST_COMPONENT_ID, S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID, S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID, S.COLUMN_WISHLIST_COMPONENT_QUANTITY, S.COLUMN_WISHLIST_COMPONENT_NOTES};
            List<String> wishlist_component_columns_list = Arrays.asList(wishlist_component_columns);

            fos = myContext.openFileOutput(filename, Context.MODE_PRIVATE);
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "UTF-8");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            if (isTableExists(S.TABLE_WISHLIST, db)) {
                Cursor wc = db.rawQuery("SELECT * FROM " + S.TABLE_WISHLIST, null);
                wc.moveToFirst();

                serializer.startTag(null, "wishlists");
                while (!wc.isAfterLast()) {
                    serializer.startTag(null, "wishlist");

                    for (String wishlist_column : wishlist_columns_list) {
                        serializer.startTag(null, wishlist_column);
                        if (wc.isNull(wc.getColumnIndex(wishlist_column))) {
                            serializer.text("");
                        } else {
                            if (wishlist_column.equals(S.COLUMN_WISHLIST_NAME)) {
                                serializer.text(wc.getString(wc.getColumnIndex(wishlist_column)));
                            } else {
                                serializer.text(Integer.toString(wc.getInt(wc.getColumnIndex(wishlist_column))));
                            }
                        }
                        serializer.endTag(null, wishlist_column);
                    }

                    serializer.endTag(null, "wishlist");

                    wc.moveToNext();
                }
                serializer.endTag(null, "wishlists");
                wc.close();
            }

            if (isTableExists(S.TABLE_WISHLIST_DATA, db)) {
                Cursor wdc = db.rawQuery("SELECT * FROM " + S.TABLE_WISHLIST_DATA, null);
                wdc.moveToFirst();

                serializer.startTag(null, "wishlist_data");
                while (!wdc.isAfterLast()) {
                    serializer.startTag(null, "data");

                    for (String data_column : wishlist_data_columns_list) {
                        serializer.startTag(null, data_column);
                        if (wdc.isNull(wdc.getColumnIndex(data_column))) {
                            serializer.text("");
                        } else {
                            if (data_column.equals(S.COLUMN_WISHLIST_DATA_PATH)) {
                                serializer.text(wdc.getString(wdc.getColumnIndex(data_column)));
                            } else {
                                serializer.text(Integer.toString(wdc.getInt(wdc.getColumnIndex(data_column))));
                            }
                        }
                        serializer.endTag(null, data_column);
                    }

                    serializer.endTag(null, "data");

                    wdc.moveToNext();
                }
                serializer.endTag(null, "wishlist_data");
                wdc.close();
            }

            if (isTableExists(S.TABLE_WISHLIST_COMPONENT, db)) {
                Cursor wcc = db.rawQuery("SELECT * FROM " + S.TABLE_WISHLIST_COMPONENT, null);
                wcc.moveToFirst();

                serializer.startTag(null, "wishlist_components");
                while (!wcc.isAfterLast()) {
                    serializer.startTag(null, "component");

                    for (String component_column : wishlist_component_columns_list) {
                        serializer.startTag(null, component_column);
                        if (wcc.isNull(wcc.getColumnIndex(component_column))) {
                            serializer.text("");
                        } else {
                            serializer.text(Integer.toString(wcc.getInt(wcc.getColumnIndex(component_column))));
                        }

                        serializer.endTag(null, component_column);
                    }

                    serializer.endTag(null, "component");

                    wcc.moveToNext();
                }
                serializer.endTag(null, "wishlist_components");
                wcc.close();
            }

            if (isTableExists(S.TABLE_ASB_SETS, db)) {
                Cursor asbc = db.rawQuery("SELECT * FROM " + S.TABLE_ASB_SETS, null);
                asbc.moveToFirst();

                serializer.startTag(null, "asb_sets");
                while (!asbc.isAfterLast()) {
                    serializer.startTag(null, "asb_set");

                    for (String asb_column : asb_set_columns_list) {
                        serializer.startTag(null, asb_column);
                        if (asbc.isNull(asbc.getColumnIndex(asb_column))) {
                            serializer.text("");
                        } else {
                            if (asb_column.equals(S.COLUMN_ASB_SET_NAME)) {
                                serializer.text(asbc.getString(asbc.getColumnIndex(asb_column)));
                            } else {
                                serializer.text(Integer.toString(asbc.getInt(asbc.getColumnIndex(asb_column))));
                            }
                        }

                        serializer.endTag(null, asb_column);
                    }
                    serializer.endTag(null, "asb_set");

                    asbc.moveToNext();
                }
                serializer.endTag(null, "asb_sets");
                asbc.close();
            }

            serializer.endDocument();
            serializer.flush();
            fos.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    protected enum Tags {
        WISHLIST, WISHLIST_DATA, WISHLIST_COMPONENTS, ASB_SET, OTHER
    }

    @Override
    protected void postCopyDatabase(SQLiteDatabase db) {
        //Log.w(TAG, "Post forcing database upgrade!");
        String filename = "wishlist.xml";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        String data;

        try {
            fis = myContext.openFileInput(filename);

            long wishlist_id = 0;
            String name = "";
            long item_id = 0;
            int quantity = 0;
            int satisfied = 0;
            String path = "";
            int notes = 0;
            String text = "";
            boolean clear_wishlist = true;

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(fis, null);

            Tags current_tag = Tags.OTHER;

            String[] asb_set_tables = {S.COLUMN_ASB_SET_NAME, S.COLUMN_ASB_SET_RANK, S.COLUMN_ASB_SET_HUNTER_TYPE, S.COLUMN_HEAD_ARMOR_ID, S.COLUMN_HEAD_DECORATION_1_ID, S.COLUMN_HEAD_DECORATION_2_ID,
                    S.COLUMN_HEAD_DECORATION_3_ID, S.COLUMN_BODY_ARMOR_ID, S.COLUMN_BODY_DECORATION_1_ID, S.COLUMN_BODY_DECORATION_2_ID, S.COLUMN_BODY_DECORATION_3_ID, S.COLUMN_ARMS_ARMOR_ID,
                    S.COLUMN_ARMS_DECORATION_1_ID, S.COLUMN_ARMS_DECORATION_2_ID, S.COLUMN_ARMS_DECORATION_3_ID, S.COLUMN_WAIST_ARMOR_ID, S.COLUMN_WAIST_DECORATION_1_ID, S.COLUMN_WAIST_DECORATION_2_ID, S.COLUMN_WAIST_DECORATION_3_ID,
                    S.COLUMN_LEGS_ARMOR_ID, S.COLUMN_LEGS_DECORATION_1_ID, S.COLUMN_LEGS_DECORATION_2_ID, S.COLUMN_LEGS_DECORATION_3_ID, S.COLUMN_TALISMAN_EXISTS, S.COLUMN_TALISMAN_TYPE, S.COLUMN_TALISMAN_SLOTS, S.COLUMN_TALISMAN_DECORATION_1_ID,
                    S.COLUMN_TALISMAN_DECORATION_2_ID, S.COLUMN_TALISMAN_DECORATION_3_ID, S.COLUMN_TALISMAN_SKILL_1_ID, S.COLUMN_TALISMAN_SKILL_1_POINTS, S.COLUMN_TALISMAN_SKILL_2_ID, S.COLUMN_TALISMAN_SKILL_2_POINTS};
            List<String> asb_set_tables_list = Arrays.asList(asb_set_tables);

            String[] wishlist_columns = {S.COLUMN_WISHLIST_ID, S.COLUMN_WISHLIST_NAME};
            List<String> wishlist_columns_list = Arrays.asList(wishlist_columns);

            String[] wishlist_data_columns = {S.COLUMN_WISHLIST_DATA_ID, S.COLUMN_WISHLIST_DATA_WISHLIST_ID, S.COLUMN_WISHLIST_DATA_ITEM_ID, S.COLUMN_WISHLIST_DATA_QUANTITY, S.COLUMN_WISHLIST_DATA_SATISFIED, S.COLUMN_WISHLIST_DATA_PATH};
            List<String> wishlist_data_columns_list = Arrays.asList(wishlist_data_columns);

            String[] wishlist_component_columns = {S.COLUMN_WISHLIST_COMPONENT_ID, S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID, S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID, S.COLUMN_WISHLIST_COMPONENT_QUANTITY, S.COLUMN_WISHLIST_COMPONENT_NOTES};
            List<String> wishlist_component_columns_list = Arrays.asList(wishlist_component_columns);

            //HashMap<String, String> row_hash = new HashMap<String, String>();
            ContentValues row_values = new ContentValues();

            int event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String tagName = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equals("asb_set")) {
                            row_values.clear();
                            //row_hash.clear();
                            current_tag = Tags.ASB_SET;
                        } else if (tagName.equals("wishlist")) {
                            row_values.clear();
                            //row_hash.clear();
                            current_tag = Tags.WISHLIST;
                        } else if (tagName.equals("data")) {
                            row_values.clear();
                            //row_hash.clear();
                            current_tag = Tags.WISHLIST_DATA;
                        } else if (tagName.equals("component")) {
                            row_values.clear();
                            //row_hash.clear();
                            current_tag = Tags.WISHLIST_COMPONENTS;
                        } else if (tagName.equals("asb_sets")) {
                            db.delete(S.TABLE_ASB_SETS, null, null);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equals("asb_set")) {
                            current_tag = Tags.OTHER;
                            db.insert(S.TABLE_ASB_SETS, null, row_values);
                        } else if (tagName.equals("wishlist")) {
                            current_tag = Tags.OTHER;
                            if (clear_wishlist) {
                                db.delete(S.TABLE_WISHLIST, null, null);
                                //only clear the table once if there is data to load
                                clear_wishlist = false;
                            }
                            db.insert(S.TABLE_WISHLIST, null, row_values);
                        } else if (tagName.equals("data")) {
                            current_tag = Tags.OTHER;
                            db.insert(S.TABLE_WISHLIST_DATA, null, row_values);
                        } else if (tagName.equals("component")) {
                            current_tag = Tags.OTHER;
                            db.insert(S.TABLE_WISHLIST_COMPONENT, null, row_values);
                        }

                        if (current_tag == Tags.ASB_SET) {
                            if (asb_set_tables_list.contains(tagName)) {
                                if (tagName.equals(S.COLUMN_ASB_SET_NAME)) {
                                    row_values.put(tagName, text);
                                } else if (text.trim().equals("")) {
                                    row_values.putNull(tagName);
                                } else {
                                    try {
                                        row_values.put(tagName, Integer.valueOf(text));
                                    } catch (NumberFormatException e) {
                                        row_values.putNull(tagName);
                                    }
                                }
                            }
                        } else if (current_tag == Tags.WISHLIST) {
                            if (wishlist_columns_list.contains(tagName)) {
                                if (tagName.equals(S.COLUMN_WISHLIST_NAME)) {
                                    row_values.put(tagName, text);
                                } else if (text.trim().equals("")) {
                                    row_values.putNull(tagName);
                                } else {
                                    try {
                                        row_values.put(tagName, Integer.valueOf(text));
                                    } catch (NumberFormatException e) {
                                        row_values.putNull(tagName);
                                    }
                                }
                            }
                        } else if (current_tag == Tags.WISHLIST_DATA) {
                            if (wishlist_data_columns_list.contains(tagName)) {
                                if (tagName.equals(S.COLUMN_WISHLIST_DATA_PATH)) {
                                    row_values.put(tagName, text);
                                } else if (text.trim().equals("")) {
                                    row_values.putNull(tagName);
                                } else {
                                    try {
                                        row_values.put(tagName, Integer.valueOf(text));
                                    } catch (NumberFormatException e) {
                                        row_values.putNull(tagName);
                                    }
                                }
                            }
                        } else if (current_tag == Tags.WISHLIST_COMPONENTS) {
                            if (wishlist_component_columns_list.contains(tagName)) {
                                if (text.trim().equals("")) {
                                    row_values.putNull(tagName);
                                } else {
                                    try {
                                        row_values.put(tagName, Integer.valueOf(text));
                                    } catch (NumberFormatException e) {
                                        row_values.putNull(tagName);
                                    }
                                }
                            }
                        }
                        break;
                }
                event = myParser.next();
            }
            fis.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * Set database instance
     * @throws SQLException
     */
    public void openDatabase() throws SQLException {
        myDataBase = getWritableDatabase();
    }

    /**
     * Close database
     */
    @Override
    public synchronized void close() {
        if (myDataBase != null) myDataBase.close();
        super.close();
    }

    private String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }

    /*
     * Helper method: used for queries that has no JOINs
     */
    private Cursor wrapHelper(QueryHelper qh) {
        return getWritableDatabase().query(qh.Distinct, qh.Table, qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
    }

    /*
     * Helper method: used for queries that has no JOINs
     */
    private Cursor wrapHelper(SQLiteDatabase db, QueryHelper qh) {
        return db.query(qh.Distinct, qh.Table, qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
    }

    /*
     * Helper method: used for queries that has JOINs
     */
    private Cursor wrapJoinHelper(SQLiteQueryBuilder qb, QueryHelper qh) {
//		Log.d(TAG, "qb: " + qb.buildQuery(_Columns, _Selection, _SelectionArgs, _GroupBy, _Having, _OrderBy, _Limit));
        return qb.query(getWritableDatabase(), qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
    }

    /*
     * Helper method: used for queries that has JOINs
     */
    private Cursor wrapJoinHelper(SQLiteDatabase db, SQLiteQueryBuilder qb, QueryHelper qh) {
//		Log.d(TAG, "qb: " + qb.buildQuery(_Columns, _Selection, _SelectionArgs, _GroupBy, _Having, _OrderBy, _Limit));
        return qb.query(db, qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
    }

    /*
     * Insert data to a table
     */
    public long insertRecord(String table, ContentValues values) {
        long l = getWritableDatabase().insert(table, null, values);
        return l;
    }

    /*
     * Insert data to a table
     */
    public long insertRecord(SQLiteDatabase db, String table, ContentValues values) {
        long l = db.insert(table, null, values);
        return l;
    }

    /*
     * Update data in a table
     */
    public int updateRecord(String table, String strFilter, ContentValues values) {
        int i = getWritableDatabase().update(table, values, strFilter, null);
        return i;
    }

    /*
     * Delete data in a table
     */
    public boolean deleteRecord(String table, String where, String[] args) {
        boolean b = getWritableDatabase().delete(table, where, args) > 0;
        return b;
    }

    /**
     * ****************************** ARENA QUEST QUERIES *****************************************
     */

	/*
     * Get all arena quests
	 */
    public ArenaQuestCursor queryArenaQuests() {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARENA_QUESTS;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArenaQuestCursor(wrapJoinHelper(builderArenaQuest(), qh));
    }

    /*
     * Get a specific arena quest
     */
    public ArenaQuestCursor queryArenaQuest(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_ARENA_QUESTS;
        qh.Columns = null;
        qh.Selection = "a." + S.COLUMN_ARENA_QUESTS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArenaQuestCursor(wrapJoinHelper(builderArenaQuest(), qh));
    }

    /*
     * Get all arena quests based on location
     */
    public ArenaQuestCursor queryArenaQuestLocation(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARENA_QUESTS;
        qh.Selection = "a." + S.COLUMN_ARENA_QUESTS_LOCATION_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArenaQuestCursor(wrapJoinHelper(builderArenaQuest(), qh));
    }

    /*
     * Helper method to query for ArenaQuest
     */
    private SQLiteQueryBuilder builderArenaQuest() {
//		SELECT a._id AS _id, a.name AS aname, a.location_id, a.reward.,
//		a.num_participants, a.time_s, a.time_a, a.time_b,
//		l.name AS lname
//		FROM arena_quests AS a
//		LEFT OUTER JOIN locations AS l on a.location_id = l._id;

        String a = "a";
        String l = "l";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", a + "." + S.COLUMN_ARENA_QUESTS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_ARENA_QUESTS_NAME, a + "." + S.COLUMN_ARENA_QUESTS_NAME + " AS " + a + S.COLUMN_ARENA_QUESTS_NAME);
        projectionMap.put(S.COLUMN_ARENA_QUESTS_GOAL, a + "." + S.COLUMN_ARENA_QUESTS_GOAL);
        projectionMap.put(S.COLUMN_ARENA_QUESTS_LOCATION_ID, a + "." + S.COLUMN_ARENA_QUESTS_LOCATION_ID);
        projectionMap.put(S.COLUMN_ARENA_QUESTS_REWARD, a + "." + S.COLUMN_ARENA_QUESTS_REWARD);
        projectionMap.put(S.COLUMN_ARENA_QUESTS_NUM_PARTICIPANTS, a + "." + S.COLUMN_ARENA_QUESTS_NUM_PARTICIPANTS);
        projectionMap.put(S.COLUMN_ARENA_QUESTS_TIME_S, a + "." + S.COLUMN_ARENA_QUESTS_TIME_S);
        projectionMap.put(S.COLUMN_ARENA_QUESTS_TIME_A, a + "." + S.COLUMN_ARENA_QUESTS_TIME_A);
        projectionMap.put(S.COLUMN_ARENA_QUESTS_TIME_B, a + "." + S.COLUMN_ARENA_QUESTS_TIME_B);

        projectionMap.put(l + S.COLUMN_LOCATIONS_NAME, l + "." + S.COLUMN_LOCATIONS_NAME + " AS " + l + S.COLUMN_LOCATIONS_NAME);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_ARENA_QUESTS + " AS a" + " LEFT OUTER JOIN " + S.TABLE_LOCATIONS +
                " AS l " + " ON " + "a." + S.COLUMN_ARENA_QUESTS_LOCATION_ID + " = " + "l." + S.COLUMN_LOCATIONS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** ARENA REWARD QUERIES *****************************************
     */

	/*
	 * Get all reward arena quests based on item
	 */
    public ArenaRewardCursor queryArenaRewardItem(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARENA_REWARDS;
        qh.Selection = "ar." + S.COLUMN_ARENA_REWARDS_ITEM_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = "ar." + S.COLUMN_ARENA_REWARDS_PERCENTAGE + " DESC";
        qh.Limit = null;

        return new ArenaRewardCursor(wrapJoinHelper(builderArenaReward(), qh));
    }

    /*
     * Get all arena quest reward items based on arena quest
     */
    public ArenaRewardCursor queryArenaRewardArena(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARENA_REWARDS;
        qh.Selection = "ar." + S.COLUMN_ARENA_REWARDS_ARENA_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArenaRewardCursor(wrapJoinHelper(builderArenaReward(), qh));
    }

    /*
     * Helper method to query for ArenaReward
     */
    private SQLiteQueryBuilder builderArenaReward() {
//		SELECT ar._id AS _id, ar.arena_id, ar.item_id,
//		ar.percentage, ar.stack_size,
//		a.name AS aname, i.name AS iname
//		FROM quest_rewards AS ar
//		LEFT OUTER JOIN arena_quests AS a ON ar.arena_id = q._id
//		LEFT OUTER JOIN items AS i ON ar.item_id = i._id;

        String ar = "ar";
        String i = "i";
        String a = "a";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", ar + "." + S.COLUMN_ARENA_REWARDS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_ARENA_REWARDS_ITEM_ID, ar + "." + S.COLUMN_ARENA_REWARDS_ITEM_ID);
        projectionMap.put(S.COLUMN_ARENA_REWARDS_ARENA_ID, ar + "." + S.COLUMN_ARENA_REWARDS_ARENA_ID);
        projectionMap.put(S.COLUMN_ARENA_REWARDS_PERCENTAGE, ar + "." + S.COLUMN_ARENA_REWARDS_PERCENTAGE);
        projectionMap.put(S.COLUMN_ARENA_REWARDS_STACK_SIZE, ar + "." + S.COLUMN_ARENA_REWARDS_STACK_SIZE);

        projectionMap.put(i + S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME + " AS " + i + S.COLUMN_ITEMS_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(a + S.COLUMN_ARENA_QUESTS_NAME, a + "." + S.COLUMN_ARENA_QUESTS_NAME + " AS " + a + S.COLUMN_ARENA_QUESTS_NAME);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_ARENA_REWARDS + " AS ar" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "ar." +
                S.COLUMN_ARENA_REWARDS_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_ARENA_QUESTS +
                " AS a " + " ON " + "ar." + S.COLUMN_ARENA_REWARDS_ARENA_ID + " = " + "a." + S.COLUMN_ARENA_QUESTS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** ARMOR QUERIES *****************************************
     */

    /*
	 * Get armor filtered by search
	 */
    public ArmorCursor queryArmorSearch(String search) {
        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Columns = null;
        qh.Selection = "i." + S.COLUMN_ITEMS_NAME + " LIKE ?";
        qh.SelectionArgs = new String[]{'%' + search + '%'};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArmorCursor(wrapJoinHelper(builderArmor(), qh));
    }

    /*
     * Get all armor
     */
    public ArmorCursor queryArmor() {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARMOR;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArmorCursor(wrapJoinHelper(builderArmor(), qh));
    }

    /*
     * Get a specific armor
     */
    public ArmorCursor queryArmor(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARMOR;
        qh.Selection = "a." + S.COLUMN_ARMOR_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new ArmorCursor(wrapJoinHelper(builderArmor(), qh));
    }

    /*
     * Get a specific armor based on hunter type
     */
    public ArmorCursor queryArmorType(String type) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARMOR;
        qh.Selection = "a." + S.COLUMN_ARMOR_HUNTER_TYPE + " = ? " + " OR " +
                "a." + S.COLUMN_ARMOR_HUNTER_TYPE + " = 'Both'";
        qh.SelectionArgs = new String[]{type};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArmorCursor(wrapJoinHelper(builderArmor(), qh));
    }

    /*
     * Get a specific armor based on slot
     */
    public ArmorCursor queryArmorSlot(String slot) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARMOR;
        qh.Selection = "a." + S.COLUMN_ARMOR_SLOT + " = ?";
        qh.SelectionArgs = new String[]{slot};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArmorCursor(wrapJoinHelper(builderArmor(), qh));
    }

    /*
     * Get a specific armor based on hunter type and slot
     */
    public ArmorCursor queryArmorTypeSlot(String type, String slot) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ARMOR;
        qh.Selection = "(a." + S.COLUMN_ARMOR_HUNTER_TYPE + " = ?" + " OR " +
                "a." + S.COLUMN_ARMOR_HUNTER_TYPE + " = 'Both') " + " AND " +
                "a." + S.COLUMN_ARMOR_SLOT + " = ?";
        qh.SelectionArgs = new String[]{type, slot};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ArmorCursor(wrapJoinHelper(builderArmor(), qh));
    }

    /*
     * Helper method to query for armor
     */
    private SQLiteQueryBuilder builderArmor() {
//		SELECT a._id AS _id, a.slot, a.defense, a.max_defense, a.fire_res, a.thunder_res,
//		a.dragon_res, a.water_res, a.ice_res, a.gender, a.hunter_type, a.num_slots,
//		i.name, i.jpn_name, i.type, i.rarity, i.carry_capacity, i.buy, i.sell, i.description,
//		i.icon_name, i.armor_dupe_name_fix
//		FROM armor AS a LEFT OUTER JOIN	items AS i ON a._id = i._id;

        String a = "a";
        String i = "i";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", a + "." + S.COLUMN_ARMOR_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_ARMOR_SLOT, a + "." + S.COLUMN_ARMOR_SLOT);
        projectionMap.put(S.COLUMN_ARMOR_DEFENSE, a + "." + S.COLUMN_ARMOR_DEFENSE);
        projectionMap.put(S.COLUMN_ARMOR_MAX_DEFENSE, a + "." + S.COLUMN_ARMOR_MAX_DEFENSE);
        projectionMap.put(S.COLUMN_ARMOR_FIRE_RES, a + "." + S.COLUMN_ARMOR_FIRE_RES);
        projectionMap.put(S.COLUMN_ARMOR_THUNDER_RES, a + "." + S.COLUMN_ARMOR_THUNDER_RES);
        projectionMap.put(S.COLUMN_ARMOR_DRAGON_RES, a + "." + S.COLUMN_ARMOR_DRAGON_RES);
        projectionMap.put(S.COLUMN_ARMOR_WATER_RES, a + "." + S.COLUMN_ARMOR_WATER_RES);
        projectionMap.put(S.COLUMN_ARMOR_ICE_RES, a + "." + S.COLUMN_ARMOR_ICE_RES);
        projectionMap.put(S.COLUMN_ARMOR_GENDER, a + "." + S.COLUMN_ARMOR_GENDER);
        projectionMap.put(S.COLUMN_ARMOR_HUNTER_TYPE, a + "." + S.COLUMN_ARMOR_HUNTER_TYPE);
        projectionMap.put(S.COLUMN_ARMOR_NUM_SLOTS, a + "." + S.COLUMN_ARMOR_NUM_SLOTS);
        projectionMap.put(S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME);
        projectionMap.put(S.COLUMN_ITEMS_JPN_NAME, i + "." + S.COLUMN_ITEMS_JPN_NAME);
        projectionMap.put(S.COLUMN_ITEMS_TYPE, i + "." + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_SUB_TYPE, i + "." + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_RARITY, i + "." + S.COLUMN_ITEMS_RARITY);
        projectionMap.put(S.COLUMN_ITEMS_CARRY_CAPACITY, i + "." + S.COLUMN_ITEMS_CARRY_CAPACITY);
        projectionMap.put(S.COLUMN_ITEMS_BUY, i + "." + S.COLUMN_ITEMS_BUY);
        projectionMap.put(S.COLUMN_ITEMS_SELL, i + "." + S.COLUMN_ITEMS_SELL);
        projectionMap.put(S.COLUMN_ITEMS_DESCRIPTION, i + "." + S.COLUMN_ITEMS_DESCRIPTION);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX, i + "." + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_ARMOR + " AS a" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "a." +
                S.COLUMN_ARMOR_ID + " = " + "i." + S.COLUMN_ITEMS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** COMBINING QUERIES *****************************************
     */

	/*
	 * Get all combinings
	 */
    public CombiningCursor queryCombinings() {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_COMBINING;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new CombiningCursor(wrapJoinHelper(builderCursor(), qh));
    }

    /*
     * Get a specific combining
     */
    public CombiningCursor queryCombining(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_COMBINING;
        qh.Selection = "c._id" + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new CombiningCursor(wrapJoinHelper(builderCursor(), qh));
    }

    public CombiningCursor queryCombinationsOnItemID(long id) {
        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_COMBINING;
        qh.Selection = "crt._id" + " = ?" +
                " OR mat1._id" + " = ?" +
                " OR mat2._id" + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id),
                String.valueOf(id),
                String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new CombiningCursor(wrapJoinHelper(builderCursor(), qh));
    }

    private SQLiteQueryBuilder builderCursor() {
//		SELECT c._id AS _id, c.amount_made_min,  c.amount_made_max, c.percentage,
//		crt._id AS crt__id, crt.name AS crt_name, crt.jpn_name AS crt_jpn_name, crt.type AS crt_type, crt.rarity AS crt_rarity,
//		crt.carry_capacity AS crt_carry_capacity, crt.buy AS crt_buy, crt.sell AS crt_sell, crt.description AS crt_description,
//		crt.icon_name AS crt_icon_name, crt.armor_dupe_name_fix AS crt_armor_dupe_name,
//
//		mat1._id AS mat1__id, mat1.name AS mat1_name, mat1.jpn_name AS mat1_jpn_name, mat1.type AS mat1_type, mat1.rarity AS mat1_rarity,
//		mat1.carry_capacity AS mat1_carry_capacity, mat1.buy AS mat1_buy, mat1.sell AS mat1_sell, mat1.description AS mat1_description,
//		mat1.icon_name AS mat1_icon_name, mat1.armor_dupe_name_fix AS mat1_armor_dupe_name,
//
//
//		mat2._id AS mat2__id, mat2.name AS mat2_name, mat2.jpn_name AS mat2_jpn_name, mat2.type AS mat2_type, mat2.rarity AS mat2_rarity,
//		mat2.carry_capacity AS mat2_carry_capacity, mat2.buy AS mat2_buy, mat2.sell AS mat2_sell, mat2.description AS mat2_description,
//		mat2.icon_name AS mat2_icon_name, mat2.armor_dupe_name_fix AS mat2_armor_dupe_name
//
//		FROM combining AS c LEFT OUTER JOIN items AS crt ON c.created_item_id = crt._id
//		LEFT OUTER JOIN items AS mat1 ON c.item_1_id = mat1._id
//		LEFT OUTER JOIN items AS mat2 ON c.item_2_id = mat2._id;

        String comb = "c.";
        String[] items = new String[]{"crt", "mat1", "mat2"};

        HashMap<String, String> projectionMap = new HashMap<String, String>();
        projectionMap.put("_id", comb + S.COLUMN_ITEMS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_COMBINING_AMOUNT_MADE_MIN, comb + S.COLUMN_COMBINING_AMOUNT_MADE_MIN);
        projectionMap.put(S.COLUMN_COMBINING_AMOUNT_MADE_MAX, comb + S.COLUMN_COMBINING_AMOUNT_MADE_MAX);
        projectionMap.put(S.COLUMN_COMBINING_PERCENTAGE, comb + S.COLUMN_COMBINING_PERCENTAGE);

        for (String i : items) {
            projectionMap.put(i + S.COLUMN_ITEMS_ID, i + "." + S.COLUMN_ITEMS_ID + " AS " + i + S.COLUMN_ITEMS_ID);

            projectionMap.put(i + S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME + " AS " + i + S.COLUMN_ITEMS_NAME);
            projectionMap.put(i + S.COLUMN_ITEMS_JPN_NAME, i + "." + S.COLUMN_ITEMS_JPN_NAME + " AS " + i + S.COLUMN_ITEMS_JPN_NAME);
            projectionMap.put(i + S.COLUMN_ITEMS_TYPE, i + "." + S.COLUMN_ITEMS_TYPE + " AS " + i + S.COLUMN_ITEMS_TYPE);
            projectionMap.put(i + S.COLUMN_ITEMS_SUB_TYPE, i + "." + S.COLUMN_ITEMS_SUB_TYPE + " AS " + i + S.COLUMN_ITEMS_SUB_TYPE);
            projectionMap.put(i + S.COLUMN_ITEMS_RARITY, i + "." + S.COLUMN_ITEMS_RARITY + " AS " + i + S.COLUMN_ITEMS_RARITY);
            projectionMap.put(i + S.COLUMN_ITEMS_CARRY_CAPACITY, i + "." + S.COLUMN_ITEMS_CARRY_CAPACITY + " AS " + i + S.COLUMN_ITEMS_CARRY_CAPACITY);
            projectionMap.put(i + S.COLUMN_ITEMS_BUY, i + "." + S.COLUMN_ITEMS_BUY + " AS " + i + S.COLUMN_ITEMS_BUY);
            projectionMap.put(i + S.COLUMN_ITEMS_SELL, i + "." + S.COLUMN_ITEMS_SELL + " AS " + i + S.COLUMN_ITEMS_SELL);
            projectionMap.put(i + S.COLUMN_ITEMS_DESCRIPTION, i + "." + S.COLUMN_ITEMS_DESCRIPTION + " AS " + i + S.COLUMN_ITEMS_DESCRIPTION);
            projectionMap.put(i + S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME + " AS " + i + S.COLUMN_ITEMS_ICON_NAME);
            projectionMap.put(i + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX, i + "." + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX + " AS " + i + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX);
        }

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_COMBINING + " AS c" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS crt" + " ON " + "c." +
                S.COLUMN_COMBINING_CREATED_ITEM_ID + " = " + "crt." + S.COLUMN_ITEMS_ID +
                " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS mat1" + " ON " + "c." +
                S.COLUMN_COMBINING_ITEM_1_ID + " = " + "mat1." + S.COLUMN_ITEMS_ID +
                " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS mat2" + " ON " + "c." +
                S.COLUMN_COMBINING_ITEM_2_ID + " = " + "mat2." + S.COLUMN_ITEMS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;

    }

    /**
     * ****************************** COMPONENT QUERIES *****************************************
     */

	/*
	 * Get all components for a created item
	 */
    public ComponentCursor queryComponentCreated(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_COMPONENTS;
        qh.Selection = "c." + S.COLUMN_COMPONENTS_CREATED_ITEM_ID + " = ? ";
        //s" AND " + "c." + S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID + " < " + S.SECTION_ARMOR;
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ComponentCursor(wrapJoinHelper(builderComponent(), qh));
    }

    /*
     * Get all components for a component item
     */
    public ComponentCursor queryComponentComponent(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_COMPONENTS;
        qh.Selection = "c." + S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ComponentCursor(wrapJoinHelper(builderComponent(), qh));
    }

    /*
     * Get all components for a created item and type
     */
    public ComponentCursor queryComponentCreatedType(long id, String type) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_COMPONENTS;
        qh.Selection = "c." + S.COLUMN_COMPONENTS_CREATED_ITEM_ID + " = ? " +
                " AND " + "c." + S.COLUMN_COMPONENTS_TYPE + " = ?";
        qh.SelectionArgs = new String[]{"" + id, type};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ComponentCursor(wrapJoinHelper(builderComponent(), qh));
    }

    /*
     * Helper method to query for component
     */
    private SQLiteQueryBuilder builderComponent() {
//		SELECT c._id AS _id, c.created_item_id, c.component_item_id,
//		c.quantity, c.type, cr.name AS crname, co.name AS coname
//		FROM components AS c
//		LEFT OUTER JOIN items AS cr ON c.created_item_id = cr._id
//		LEFT OUTER JOIN items AS co ON c.component_item_id = co._id;

        String c = "c";
        String cr = "cr";
        String co = "co";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", c + "." + S.COLUMN_COMPONENTS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_COMPONENTS_CREATED_ITEM_ID, c + "." + S.COLUMN_COMPONENTS_CREATED_ITEM_ID);
        projectionMap.put(S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID, c + "." + S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID);
        projectionMap.put(S.COLUMN_COMPONENTS_QUANTITY, c + "." + S.COLUMN_COMPONENTS_QUANTITY);
        projectionMap.put(S.COLUMN_COMPONENTS_TYPE, c + "." + S.COLUMN_COMPONENTS_TYPE);

        projectionMap.put(cr + S.COLUMN_ITEMS_NAME, cr + "." + S.COLUMN_ITEMS_NAME + " AS " + cr + S.COLUMN_ITEMS_NAME);
        projectionMap.put(cr + S.COLUMN_ITEMS_TYPE, cr + "." + S.COLUMN_ITEMS_TYPE + " AS " + cr + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(cr + S.COLUMN_ITEMS_SUB_TYPE, cr + "." + S.COLUMN_ITEMS_SUB_TYPE + " AS " + cr + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(cr + S.COLUMN_ITEMS_RARITY, cr + "." + S.COLUMN_ITEMS_RARITY + " AS " + cr + S.COLUMN_ITEMS_RARITY);
        projectionMap.put(cr + S.COLUMN_ITEMS_ICON_NAME, cr + "." + S.COLUMN_ITEMS_ICON_NAME + " AS " + cr + S.COLUMN_ITEMS_ICON_NAME);

        projectionMap.put(co + S.COLUMN_ITEMS_NAME, co + "." + S.COLUMN_ITEMS_NAME + " AS " + co + S.COLUMN_ITEMS_NAME);
        projectionMap.put(co + S.COLUMN_ITEMS_TYPE, co + "." + S.COLUMN_ITEMS_TYPE + " AS " + co + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(co + S.COLUMN_ITEMS_ICON_NAME, co + "." + S.COLUMN_ITEMS_ICON_NAME + " AS " + co + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(co + S.COLUMN_ITEMS_SUB_TYPE, co + "." + S.COLUMN_ITEMS_SUB_TYPE + " AS " + co + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(co + S.COLUMN_ITEMS_RARITY, co + "." + S.COLUMN_ITEMS_RARITY + " AS " + co + S.COLUMN_ITEMS_RARITY);


        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_COMPONENTS + " AS c" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS cr" + " ON " + "c." +
                S.COLUMN_COMPONENTS_CREATED_ITEM_ID + " = " + "cr." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_ITEMS +
                " AS co " + " ON " + "c." + S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID + " = " + "co." + S.COLUMN_ITEMS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** DECORATION QUERIES *****************************************
     */

	/*
	 * Get all decorations
	 */
    public DecorationCursor queryDecorations() {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_DECORATIONS;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = "skill_1_name ASC";
        qh.Limit = null;

        return new DecorationCursor(wrapJoinHelper(builderDecoration(), qh));
    }

    /*
     * Get a specific decoration
     */
    public DecorationCursor queryDecoration(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_DECORATIONS;
        qh.Selection = "i._id" + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new DecorationCursor(wrapJoinHelper(builderDecoration(), qh));
    }

    /*
     * Helper method to query for decorations
     */
    private SQLiteQueryBuilder builderDecoration() {
//		 SELECT i._id AS item_id, i.name, i.jpn_name, i.type, i.rarity, i.carry_capacity, i.buy, i.sell, i.description,
//		 i.icon_name, i.armor_dupe_name_fix, d.num_slots, s1._id AS skill_1_id, s1.name AS skill_1_name, its1.point_value
//		 AS skill_1_point, s2._id AS skill_1_id, s2.name AS skill_2_name, its2.point_value AS skill_2_point
//		 FROM decorations AS d LEFT OUTER JOIN items AS i ON d._id = i._id
//		 LEFT OUTER JOIN item_to_skill_tree AS its1 ON i._id = its1.item_id and its1.point_value > 0
//		 LEFT OUTER JOIN skill_trees AS s1 ON its1.skill_tree_id = s1._id
//		 LEFT OUTER JOIN item_to_skill_tree AS its2 ON i._id = its2.item_id and s1._id != its2.skill_tree_id
//		 LEFT OUTER JOIN skill_trees AS s2 ON its2.skill_tree_id = s2._id;

        HashMap<String, String> projectionMap = new HashMap<String, String>();
        projectionMap.put("_id", "i." + S.COLUMN_ITEMS_ID + " AS " + "_id");
        projectionMap.put("item_name", "i." + S.COLUMN_ITEMS_NAME + " AS " + "item_name");
        projectionMap.put(S.COLUMN_ITEMS_JPN_NAME, "i." + S.COLUMN_ITEMS_JPN_NAME);
        projectionMap.put(S.COLUMN_ITEMS_TYPE, "i." + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_SUB_TYPE, "i." + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_RARITY, "i." + S.COLUMN_ITEMS_RARITY);
        projectionMap.put(S.COLUMN_ITEMS_CARRY_CAPACITY, "i." + S.COLUMN_ITEMS_CARRY_CAPACITY);
        projectionMap.put(S.COLUMN_ITEMS_BUY, "i." + S.COLUMN_ITEMS_BUY);
        projectionMap.put(S.COLUMN_ITEMS_SELL, "i." + S.COLUMN_ITEMS_SELL);
        projectionMap.put(S.COLUMN_ITEMS_DESCRIPTION, "i." + S.COLUMN_ITEMS_DESCRIPTION);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, "i." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX, "i." + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX);
        projectionMap.put(S.COLUMN_DECORATIONS_NUM_SLOTS, "d." + S.COLUMN_DECORATIONS_NUM_SLOTS);
        projectionMap.put("skill_1_id", "s1." + S.COLUMN_SKILL_TREES_ID + " AS " + "skill_1_id");
        projectionMap.put("skill_1_name", "s1." + S.COLUMN_SKILL_TREES_NAME + " AS " + "skill_1_name");
        projectionMap.put("skill_1_point_value", "its1." + S.COLUMN_ITEM_TO_SKILL_TREE_POINT_VALUE + " AS " + "skill_1_point_value");
        projectionMap.put("skill_2_id", "s2." + S.COLUMN_SKILL_TREES_ID + " AS " + "skill_2_id");
        projectionMap.put("skill_2_name", "s2." + S.COLUMN_SKILL_TREES_NAME + " AS " + "skill_2_name");
        projectionMap.put("skill_2_point_value", "its2." + S.COLUMN_ITEM_TO_SKILL_TREE_POINT_VALUE + " AS " + "skill_2_point_value");

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_DECORATIONS + " AS d" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "d." +
                S.COLUMN_DECORATIONS_ID + " = " + "i." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_ITEM_TO_SKILL_TREE +
                " AS its1 " + " ON " + "i." + S.COLUMN_ITEMS_ID + " = " + "its1." + S.COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID + " AND " +
                "its1." + S.COLUMN_ITEM_TO_SKILL_TREE_POINT_VALUE + " > 0 " + " LEFT OUTER JOIN " + S.TABLE_SKILL_TREES + " AS s1" +
                " ON " + "its1." + S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID + " = " + "s1." + S.COLUMN_SKILL_TREES_ID +
                " LEFT OUTER JOIN " + S.TABLE_ITEM_TO_SKILL_TREE + " AS its2 " + " ON " + "i." + S.COLUMN_ITEMS_ID + " = " +
                "its2." + S.COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID + " AND " + "s1." + S.COLUMN_SKILL_TREES_ID + " != " +
                "its2." + S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID + " LEFT OUTER JOIN " + S.TABLE_SKILL_TREES + " AS s2" +
                " ON " + "its2." + S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID + " = " + "s2." + S.COLUMN_SKILL_TREES_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** GATHERING QUERIES *****************************************
     */

	/*
	 * Get all gathering locations based on item
	 */
    public GatheringCursor queryGatheringItem(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_GATHERING;
        qh.Selection = "g." + S.COLUMN_GATHERING_ITEM_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = "g." + S.COLUMN_GATHERING_RANK + " DESC, " + "l." + S.COLUMN_LOCATIONS_MAP
                + " ASC";
        qh.Limit = null;

        return new GatheringCursor(wrapJoinHelper(builderGathering(), qh));
    }

    /*
     * Get all gathering items based on location
     */
    public GatheringCursor queryGatheringLocation(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_GATHERING;
        qh.Selection = "g." + S.COLUMN_GATHERING_LOCATION_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new GatheringCursor(wrapJoinHelper(builderGathering(), qh));
    }

    /*
     * Get all gathering items based on location and rank
     */
    public GatheringCursor queryGatheringLocationRank(long id, String rank) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_GATHERING;
        qh.Selection = "g." + S.COLUMN_GATHERING_LOCATION_ID + " = ? " + "AND " +
                "g." + S.COLUMN_GATHERING_RANK + " = ? ";
        qh.SelectionArgs = new String[]{"" + id, rank};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new GatheringCursor(wrapJoinHelper(builderGathering(), qh));
    }

    /*
     * Helper method to query for Gathering
     */
    private SQLiteQueryBuilder builderGathering() {
//		SELECT g._id AS _id, g.item_id, g.location_id, g.area,
//		g.site, g.site_set, g.site_set_percentage,
//		g.site_set_gathers_min, g.site_set_gathers_max, g.rank,
//		g.percentage, i.name AS iname, l.name AS lname
//		FROM gathering AS g
//		LEFT OUTER JOIN items AS i ON g.item_id = i._id
//		LEFT OUTER JOIN locations AS l on g.location_id = l._id;

        String g = "g";
        String i = "i";
        String l = "l";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", g + "." + S.COLUMN_GATHERING_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_GATHERING_ITEM_ID, g + "." + S.COLUMN_GATHERING_ITEM_ID);
        projectionMap.put(S.COLUMN_GATHERING_LOCATION_ID, g + "." + S.COLUMN_GATHERING_LOCATION_ID);
        projectionMap.put(S.COLUMN_GATHERING_AREA, g + "." + S.COLUMN_GATHERING_AREA);
        projectionMap.put(S.COLUMN_GATHERING_SITE, g + "." + S.COLUMN_GATHERING_SITE);
        projectionMap.put(S.COLUMN_GATHERING_RANK, g + "." + S.COLUMN_GATHERING_RANK);
        projectionMap.put(S.COLUMN_GATHERING_RATE, g + "." + S.COLUMN_GATHERING_RATE);

        projectionMap.put(i + S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME + " AS " + i + S.COLUMN_ITEMS_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(l + S.COLUMN_LOCATIONS_NAME, l + "." + S.COLUMN_LOCATIONS_NAME + " AS " + l + S.COLUMN_LOCATIONS_NAME);
        projectionMap.put(l + S.COLUMN_LOCATIONS_MAP, l + "." + S.COLUMN_LOCATIONS_MAP + " AS " + l + S.COLUMN_LOCATIONS_MAP);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_GATHERING + " AS g" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "g." +
                S.COLUMN_GATHERING_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_LOCATIONS +
                " AS l " + " ON " + "g." + S.COLUMN_GATHERING_LOCATION_ID + " = " + "l." + S.COLUMN_LOCATIONS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** HUNTING REWARD QUERIES *****************************************
     */

	/*
	 * Get all hunting reward monsters based on item
	 */
    public HuntingRewardCursor queryHuntingRewardItem(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_HUNTING_REWARDS;
        qh.Selection = "h." + S.COLUMN_HUNTING_REWARDS_ITEM_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = "m." + S.COLUMN_MONSTERS_ID + " ASC, " + "h." + S.COLUMN_HUNTING_REWARDS_RANK +
                " DESC, " + "h." + S.COLUMN_HUNTING_REWARDS_ID + " ASC";
        qh.Limit = null;

        return new HuntingRewardCursor(wrapJoinHelper(builderHuntingReward(), qh));
    }

    /*
     * Get all hunting reward items based on monster
     */
    public HuntingRewardCursor queryHuntingRewardMonster(long[] ids) {

        String[] string_list = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            string_list[i] = String.valueOf(ids[i]);
        }

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_HUNTING_REWARDS;
        qh.Selection = "h." + S.COLUMN_HUNTING_REWARDS_MONSTER_ID + " IN (" + makePlaceholders(ids.length) + ")";
        qh.SelectionArgs = string_list;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new HuntingRewardCursor(wrapJoinHelper(builderHuntingReward(), qh));
    }

    /*
     * Get all hunting reward items based on monster and rank
     */
    public HuntingRewardCursor queryHuntingRewardMonsterRank(long[] ids, String rank) {

        String[] string_list = new String[ids.length + 1];
        for (int i = 0; i < ids.length; i++) {
            string_list[i] = String.valueOf(ids[i]);
        }
        string_list[ids.length] = rank;

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_HUNTING_REWARDS;
        qh.Selection = "h." + S.COLUMN_HUNTING_REWARDS_MONSTER_ID + " IN (" + makePlaceholders(ids.length) + ")"
                + " AND " + "h." + S.COLUMN_HUNTING_REWARDS_RANK + " = ? ";
        qh.SelectionArgs = string_list;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new HuntingRewardCursor(wrapJoinHelper(builderHuntingReward(), qh));
    }

    /*
     * Helper method to query for HuntingReward
     */
    private SQLiteQueryBuilder builderHuntingReward() {
//		SELECT h._id AS _id, h.item_id, h.monster_id,
//		h.condition, h.rank, h.stack_size, h.percentage,
//		i.name AS iname, m.name AS mname
//		FROM hunting_rewards AS h
//		LEFT OUTER JOIN items AS i ON h.item_id = i._id
//		LEFT OUTER JOIN monsters AS m ON h.monster_id = m._id;

        String h = "h";
        String i = "i";
        String m = "m";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", h + "." + S.COLUMN_HUNTING_REWARDS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_HUNTING_REWARDS_ITEM_ID, h + "." + S.COLUMN_HUNTING_REWARDS_ITEM_ID);
        projectionMap.put(S.COLUMN_HUNTING_REWARDS_MONSTER_ID, h + "." + S.COLUMN_HUNTING_REWARDS_MONSTER_ID);
        projectionMap.put(S.COLUMN_HUNTING_REWARDS_CONDITION, h + "." + S.COLUMN_HUNTING_REWARDS_CONDITION);
        projectionMap.put(S.COLUMN_HUNTING_REWARDS_RANK, h + "." + S.COLUMN_HUNTING_REWARDS_RANK);
        projectionMap.put(S.COLUMN_HUNTING_REWARDS_STACK_SIZE, h + "." + S.COLUMN_HUNTING_REWARDS_STACK_SIZE);
        projectionMap.put(S.COLUMN_HUNTING_REWARDS_PERCENTAGE, h + "." + S.COLUMN_HUNTING_REWARDS_PERCENTAGE);

        projectionMap.put(i + S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME + " AS " + i + S.COLUMN_ITEMS_NAME);
        projectionMap.put(i + S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME + " AS " + i + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(m + S.COLUMN_MONSTERS_NAME, m + "." + S.COLUMN_MONSTERS_NAME + " AS " + m + S.COLUMN_MONSTERS_NAME);
        projectionMap.put(S.COLUMN_MONSTERS_TRAIT, m + "." + S.COLUMN_MONSTERS_TRAIT);
        projectionMap.put(m + S.COLUMN_MONSTERS_FILE_LOCATION, m + "." + S.COLUMN_MONSTERS_FILE_LOCATION + " AS " + m + S.COLUMN_MONSTERS_FILE_LOCATION);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_HUNTING_REWARDS + " AS h" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "h." +
                S.COLUMN_HUNTING_REWARDS_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_MONSTERS +
                " AS m " + " ON " + "h." + S.COLUMN_HUNTING_REWARDS_MONSTER_ID + " = " + "m." + S.COLUMN_MONSTERS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** ITEM QUERIES *****************************************
     */

	/*
	 * Get all items
	 */
    public ItemCursor queryItems() {
        // SELECT DISTINCT * FROM items ORDER BY _id

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_ITEMS;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = S.COLUMN_ITEMS_ID;
        qh.Limit = null;

        return new ItemCursor(wrapHelper(qh));
    }

    /*
     * Get a specific item
     */
    public ItemCursor queryItem(long id) {
        // "SELECT DISTINCT * FROM items WHERE _id = id LIMIT 1"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_ITEMS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_ITEMS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new ItemCursor(wrapHelper(qh));
    }

    /*
     * Get items based on search text
     */
    public ItemCursor queryItemSearch(String search) {
        // "SELECT * FROM items WHERE name LIKE %?%"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_ITEMS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_ITEMS_NAME + " LIKE ?";
        qh.SelectionArgs = new String[]{'%' + search + '%'};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ItemCursor(wrapHelper(qh));
    }


    /**
     * ****************************** ITEM TO SKILL TREE QUERIES *****************************************
     */

	/*
	 * Get all skills based on item
	 */
    public ItemToSkillTreeCursor queryItemToSkillTreeItem(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Selection = "itst." + S.COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ItemToSkillTreeCursor(wrapJoinHelper(builderItemToSkillTree(), qh));
    }

    /*
     * Get all items based on skill tree
     */
    public ItemToSkillTreeCursor queryItemToSkillTreeSkillTree(long id, String type) {

        String queryType = "";
        if (type.equals("Decoration")) {
            queryType = "i." + S.COLUMN_ITEMS_TYPE;
        } else {
            queryType = "a." + S.COLUMN_ARMOR_SLOT;
        }

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ITEM_TO_SKILL_TREE;
        qh.Selection = "itst." + S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID + " = ? " + " AND " +
                queryType + " = ? ";
        qh.SelectionArgs = new String[]{"" + id, type};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ItemToSkillTreeCursor(wrapJoinHelper(builderItemToSkillTree(), qh));
    }

    /*
     * Helper method to query for ItemToSkillTree
     */
    private SQLiteQueryBuilder builderItemToSkillTree() {
//		SELECT itst._id AS _id, itst.item_id, itst.skill_tree_id,
//		itst.point_value, i.name AS iname, s.name AS sname
//		FROM item_to_skill_tree AS itst
//		LEFT OUTER JOIN items AS i ON itst.item_id = i._id
//		LEFT OUTER JOIN skill_trees AS s ON itst.skill_tree_id = s._id;
//		LEFT OUTER JOIN armor AS a ON i._id = a._id
//		LEFT OUTER JOIN decorations AS d ON i._id = d._id;

        String itst = "itst";
        String i = "i";
        String s = "s";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", itst + "." + S.COLUMN_ITEM_TO_SKILL_TREE_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID, itst + "." + S.COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID);
        projectionMap.put(S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID, itst + "." + S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID);
        projectionMap.put(S.COLUMN_ITEM_TO_SKILL_TREE_POINT_VALUE, itst + "." + S.COLUMN_ITEM_TO_SKILL_TREE_POINT_VALUE);

        projectionMap.put(i + S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME + " AS " + i + S.COLUMN_ITEMS_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(S.COLUMN_ITEMS_TYPE, i + "." + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_SUB_TYPE, i + "." + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_RARITY, i + "." + S.COLUMN_ITEMS_RARITY);
        projectionMap.put(s + S.COLUMN_SKILL_TREES_NAME, s + "." + S.COLUMN_SKILL_TREES_NAME + " AS " + s + S.COLUMN_SKILL_TREES_NAME);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_ITEM_TO_SKILL_TREE + " AS itst" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "itst." +
                S.COLUMN_ITEM_TO_SKILL_TREE_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_SKILL_TREES +
                " AS s " + " ON " + "itst." + S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID + " = " + "s." + S.COLUMN_SKILL_TREES_ID +
                " LEFT OUTER JOIN " + S.TABLE_ARMOR + " AS a" + " ON " + "i." + S.COLUMN_ITEMS_ID + " = " + "a." + S.COLUMN_ARMOR_ID +
                " LEFT OUTER JOIN " + S.TABLE_DECORATIONS + " AS d" + " ON " + "i." + S.COLUMN_ITEMS_ID + " = " + "d." +
                S.COLUMN_DECORATIONS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** LOCATION QUERIES *****************************************
     */

	/*
	 * Get all locations
	 */
    public LocationCursor queryLocations() {
        // "SELECT DISTINCT * FROM locations GROUP BY name"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_LOCATIONS;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new LocationCursor(wrapHelper(qh));
    }

    /*
     * Get a specific location
     */
    public LocationCursor queryLocation(long id) {
        // "SELECT DISTINCT * FROM locations WHERE _id = id LIMIT 1"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_LOCATIONS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_LOCATIONS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new LocationCursor(wrapHelper(qh));
    }

    /**
     * ***************************** HORN MELODIES QUERIES **********************************************
     */

    /*
     * Get all melodies available from a given set of notes
     */
    public HornMelodiesCursor queryMelodiesFromNotes(String notes) {
        // "SELECT * FROM horn_melodies WHERE notes = notes"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_HORN_MELODIES;
        qh.Columns = null;
        qh.Selection = S.COLUMN_HORN_MELODIES_NOTES + " = ?";
        qh.SelectionArgs = new String[]{notes};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new HornMelodiesCursor(wrapHelper(qh));
    }

    /**
     * ****************************** MONSTER QUERIES *****************************************
     */

	/*
	 * Get all monsters
	 */
    public MonsterCursor queryMonsters() {
        // "SELECT DISTINCT * FROM monsters GROUP BY name"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_MONSTERS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_MONSTERS_TRAIT + " = '' ";
        qh.SelectionArgs = null;
        qh.GroupBy = S.COLUMN_MONSTERS_SORT_NAME;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterCursor(wrapHelper(qh));
    }

    /*
     * Get all small monsters
     */
    public MonsterCursor querySmallMonsters() {
        // "SELECT DISTINCT * FROM monsters WHERE class = 'Minion' GROUP BY name"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_MONSTERS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_MONSTERS_CLASS + " = ?" + " AND " + S.COLUMN_MONSTERS_TRAIT + " = '' ";
        qh.SelectionArgs = new String[]{"Minion"};
        qh.GroupBy = S.COLUMN_MONSTERS_SORT_NAME;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterCursor(wrapHelper(qh));
    }

    /*
     * Get all large monsters
     */
    public MonsterCursor queryLargeMonsters() {
        // "SELECT DISTINCT * FROM monsters WHERE class = 'Boss' GROUP BY name"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_MONSTERS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_MONSTERS_CLASS + " = ?" + " AND " + S.COLUMN_MONSTERS_TRAIT + " = '' ";
        qh.SelectionArgs = new String[]{"Boss"};
        qh.GroupBy = S.COLUMN_MONSTERS_SORT_NAME;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterCursor(wrapHelper(qh));
    }

    /*
     * Get a specific monster
     */
    public MonsterCursor queryMonster(long id) {
        // "SELECT DISTINCT * FROM monsters WHERE _id = id LIMIT 1"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_MONSTERS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_MONSTERS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new MonsterCursor(wrapHelper(qh));
    }

    /*
     * Get all traits from same monsters
     */
    public MonsterCursor queryMonsterTrait(String name) {
        // "SELECT * FROM monsters WHERE _id = ? AND trait != ''"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_MONSTERS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_MONSTERS_NAME + " = ?" + " AND " + S.COLUMN_MONSTERS_TRAIT + " != '' ";
        qh.SelectionArgs = new String[]{name};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterCursor(wrapHelper(qh));
    }

    /********************************
     * MONSTER AILMENT QUERIES
     ******************************************/
    /* Get all ailments a from a particular monster */
    public MonsterAilmentCursor queryAilmentsFromMonster(long id) {
        // SELECT * FROM monster_ailment WHERE monster_id = id

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_AILMENT;
        qh.Columns = null;
        qh.Selection = S.COLUMN_AILMENT_MONSTER_ID + " = " + id;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterAilmentCursor(wrapHelper(qh));
    }

/********************************* MONSTER HABITAT QUERIES ******************************************/

    /**
     * Get a cursor with a query to grab all habitats of a monster
     * @param id id of the monster to query
     * @return A habitat cursor
     */
    public MonsterHabitatCursor queryHabitatMonster(long id) {
        // Select * FROM monster_habitat WHERE monster_id = id
        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_HABITAT;
        qh.Columns = null;
        qh.Selection = S.COLUMN_HABITAT_MONSTER_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterHabitatCursor(wrapJoinHelper(builderHabitat(qh.Distinct), qh));
    }

    /**
     * Get a cursor with a query to grab all monsters by a location
     * @param id id of the location to query
     * @return A habitat cursor
     */
    public MonsterHabitatCursor queryHabitatLocation(long id) {
        // Select * FROM monster_habitat WHERE location_id = id
        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_HABITAT;
        qh.Columns = null;
        qh.Selection = S.COLUMN_HABITAT_LOCATION_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = "m" + S.COLUMN_MONSTERS_SORT_NAME + " ASC";
        qh.Limit = null;

        return new MonsterHabitatCursor(wrapJoinHelper(builderHabitat(qh.Distinct), qh));
    }

    /*
 * Helper method to query for Habitat/Monster/Location
 */
    private SQLiteQueryBuilder builderHabitat(boolean Distinct) {
        String h = "h";
        String m = "m";
        String l = "l";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", h + "." + S.COLUMN_HABITAT_ID + " AS " + "_id");
        projectionMap.put("start_area", h + "." + S.COLUMN_HABITAT_START + " AS " + "start_area");
        projectionMap.put("move_area", h + "." + S.COLUMN_HABITAT_AREAS + " AS " + "move_area");
        projectionMap.put("rest_area", h + "." + S.COLUMN_HABITAT_REST + " AS " + "rest_area");

        projectionMap.put(l + S.COLUMN_LOCATIONS_ID, l + "." + S.COLUMN_LOCATIONS_ID + " AS " + l + S.COLUMN_LOCATIONS_ID);
        projectionMap.put(l + S.COLUMN_LOCATIONS_NAME, l + "." + S.COLUMN_LOCATIONS_NAME + " AS " + l + S.COLUMN_LOCATIONS_NAME);
        projectionMap.put(l + S.COLUMN_LOCATIONS_MAP, l + "." + S.COLUMN_LOCATIONS_MAP + " AS " + l + S.COLUMN_LOCATIONS_MAP);

        projectionMap.put(m + S.COLUMN_MONSTERS_ID, m + "." + S.COLUMN_MONSTERS_ID + " AS " + m + S.COLUMN_MONSTERS_ID);
        projectionMap.put(m + S.COLUMN_MONSTERS_SORT_NAME, m + "." + S.COLUMN_MONSTERS_SORT_NAME + " AS " + m + S.COLUMN_MONSTERS_SORT_NAME);
        projectionMap.put(m + S.COLUMN_MONSTERS_NAME, m + "." + S.COLUMN_MONSTERS_NAME + " AS " + m + S.COLUMN_MONSTERS_NAME);
        projectionMap.put(m + S.COLUMN_MONSTERS_CLASS, m + "." + S.COLUMN_MONSTERS_CLASS + " AS " + m + S.COLUMN_MONSTERS_CLASS);
        projectionMap.put(m + S.COLUMN_MONSTERS_FILE_LOCATION, m + "." + S.COLUMN_MONSTERS_FILE_LOCATION + " AS " + m + S.COLUMN_MONSTERS_FILE_LOCATION);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_HABITAT + " AS h" + " LEFT OUTER JOIN " + S.TABLE_MONSTERS + " AS m" + " ON " + "h." +
                S.COLUMN_HABITAT_MONSTER_ID + " = " + "m." + S.COLUMN_MONSTERS_ID + " LEFT OUTER JOIN " + S.TABLE_LOCATIONS +
                " AS l " + " ON " + "h." + S.COLUMN_HABITAT_LOCATION_ID + " = " + "l." + S.COLUMN_LOCATIONS_ID);

        QB.setDistinct(Distinct);
        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** MONSTER STATUS QUERIES *****************************************
     */

	/*
	 * Get all monster status info for a monster
	 * @param id The monster id
	 */
    public MonsterStatusCursor queryMonsterStatus(long id) {
        // "SELECT * FROM monster_status WHERE monster_id = id"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_MONSTER_STATUS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_MONSTER_STATUS_MONSTER_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterStatusCursor(wrapHelper(qh));
    }

    /**
     * ****************************** MONSTER DAMAGE QUERIES *****************************************
     */

	/*
	 * Get all monster damage for a monster
	 */
    public MonsterDamageCursor queryMonsterDamage(long id) {
        // "SELECT * FROM monster_damage WHERE monster_id = id"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_MONSTER_DAMAGE;
        qh.Columns = null;
        qh.Selection = S.COLUMN_MONSTER_DAMAGE_MONSTER_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterDamageCursor(wrapHelper(qh));
    }

    /**
     * ****************************** MONSTER TO ARENA QUERIES *****************************************
     */

	/*
	 * Get all arena quests based on monster
	 */
    public MonsterToArenaCursor queryMonsterToArenaMonster(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_MONSTER_TO_ARENA;
        qh.Columns = null;
        qh.Selection = "mta." + S.COLUMN_MONSTER_TO_ARENA_MONSTER_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = "a." + S.COLUMN_ARENA_QUESTS_NAME;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterToArenaCursor(wrapJoinHelper(builderMonsterToArena(qh.Distinct), qh));
    }

    /*
     * Get all monsters based on arena quest
     */
    public MonsterToArenaCursor queryMonsterToArenaArena(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_MONSTER_TO_ARENA;
        qh.Columns = null;
        qh.Selection = "mta." + S.COLUMN_MONSTER_TO_ARENA_ARENA_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterToArenaCursor(wrapJoinHelper(builderMonsterToArena(qh.Distinct), qh));
    }

    /*
     * Helper method to query for MonsterToArena
     */
    private SQLiteQueryBuilder builderMonsterToArena(boolean Distinct) {
//		SELECT mta._id AS _id, mta.monster_id, mta.arena_id,
//		m.name AS mname, a.name AS aname,
//		FROM monster_to_arena AS mta
//		LEFT OUTER JOIN monsters AS m ON mta.monster_id = m._id
//		LEFT OUTER JOIN arena_quests AS a ON mta.arena_id = a._id;

        String mta = "mta";
        String m = "m";
        String a = "a";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", mta + "." + S.COLUMN_MONSTER_TO_ARENA_ID + " AS " + "_id");

        projectionMap.put(S.COLUMN_MONSTER_TO_ARENA_ID, mta + "." + S.COLUMN_MONSTER_TO_ARENA_ID);
        projectionMap.put(S.COLUMN_MONSTER_TO_ARENA_MONSTER_ID, mta + "." + S.COLUMN_MONSTER_TO_ARENA_MONSTER_ID);
        projectionMap.put(S.COLUMN_MONSTER_TO_ARENA_ARENA_ID, mta + "." + S.COLUMN_MONSTER_TO_ARENA_ARENA_ID);

        projectionMap.put(m + S.COLUMN_MONSTERS_NAME, m + "." + S.COLUMN_MONSTERS_NAME + " AS " + m + S.COLUMN_MONSTERS_NAME);
        projectionMap.put(S.COLUMN_MONSTERS_TRAIT, m + "." + S.COLUMN_MONSTERS_TRAIT);
        projectionMap.put(S.COLUMN_MONSTERS_FILE_LOCATION, m + "." + S.COLUMN_MONSTERS_FILE_LOCATION);
        projectionMap.put(a + S.COLUMN_ARENA_QUESTS_NAME, a + "." + S.COLUMN_ARENA_QUESTS_NAME + " AS " + a + S.COLUMN_ARENA_QUESTS_NAME);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_MONSTER_TO_ARENA + " AS mta" + " LEFT OUTER JOIN " + S.TABLE_MONSTERS + " AS m" + " ON " + "mta." +
                S.COLUMN_MONSTER_TO_ARENA_MONSTER_ID + " = " + "m." + S.COLUMN_MONSTERS_ID + " LEFT OUTER JOIN " + S.TABLE_ARENA_QUESTS +
                " AS a " + " ON " + "mta." + S.COLUMN_MONSTER_TO_ARENA_ARENA_ID + " = " + "a." + S.COLUMN_ARENA_QUESTS_ID);

        QB.setDistinct(Distinct);
        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** MONSTER TO QUEST QUERIES *****************************************
     */

	/*
	 * Get all quests based on monster
	 */
    public MonsterToQuestCursor queryMonsterToQuestMonster(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_MONSTER_TO_QUEST;
        qh.Columns = null;
        qh.Selection = "mtq." + S.COLUMN_MONSTER_TO_QUEST_MONSTER_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = "q." + S.COLUMN_QUESTS_NAME;
        qh.Having = null;
        qh.OrderBy = "q." + S.COLUMN_QUESTS_HUB + " ASC, " + "q." + S.COLUMN_QUESTS_STARS + " ASC";
        qh.Limit = null;

        return new MonsterToQuestCursor(wrapJoinHelper(builderMonsterToQuest(qh.Distinct), qh));
    }

    /*
     * Get all monsters based on quest
     */
    public MonsterToQuestCursor queryMonsterToQuestQuest(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_MONSTER_TO_QUEST;
        qh.Columns = null;
        qh.Selection = "mtq." + S.COLUMN_MONSTER_TO_QUEST_QUEST_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterToQuestCursor(wrapJoinHelper(builderMonsterToQuest(qh.Distinct), qh));
    }

    /*
     * Helper method to query for MonsterToQuest
     */
    private SQLiteQueryBuilder builderMonsterToQuest(boolean Distinct) {
//		SELECT mtq._id AS _id, mtq.monster_id, mtq.quest_id,
//		mtq.unstable, m.name AS mname, q.name AS qname,
//		q.hub, q.stars
//		FROM monster_to_quest AS mtq
//		LEFT OUTER JOIN monsters AS m ON mtq.monster_id = m._id
//		LEFT OUTER JOIN quests AS q ON mtq.quest_id = q._id;

        String mtq = "mtq";
        String m = "m";
        String q = "q";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", mtq + "." + S.COLUMN_MONSTER_TO_QUEST_ID + " AS " + "_id");

        projectionMap.put(S.COLUMN_MONSTER_TO_QUEST_MONSTER_ID, mtq + "." + S.COLUMN_MONSTER_TO_QUEST_MONSTER_ID);
        projectionMap.put(S.COLUMN_MONSTER_TO_QUEST_QUEST_ID, mtq + "." + S.COLUMN_MONSTER_TO_QUEST_QUEST_ID);
        projectionMap.put(S.COLUMN_MONSTER_TO_QUEST_UNSTABLE, mtq + "." + S.COLUMN_MONSTER_TO_QUEST_UNSTABLE);

        projectionMap.put(m + S.COLUMN_MONSTERS_NAME, m + "." + S.COLUMN_MONSTERS_NAME + " AS " + m + S.COLUMN_MONSTERS_NAME);
        projectionMap.put(S.COLUMN_MONSTERS_TRAIT, m + "." + S.COLUMN_MONSTERS_TRAIT);
        projectionMap.put(S.COLUMN_MONSTERS_FILE_LOCATION, m + "." + S.COLUMN_MONSTERS_FILE_LOCATION);
        projectionMap.put(q + S.COLUMN_QUESTS_NAME, q + "." + S.COLUMN_QUESTS_NAME + " AS " + q + S.COLUMN_QUESTS_NAME);
        projectionMap.put(S.COLUMN_QUESTS_HUB, q + "." + S.COLUMN_QUESTS_HUB);
        projectionMap.put(S.COLUMN_QUESTS_STARS, q + "." + S.COLUMN_QUESTS_STARS);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_MONSTER_TO_QUEST + " AS mtq" + " LEFT OUTER JOIN " + S.TABLE_MONSTERS + " AS m" + " ON " + "mtq." +
                S.COLUMN_MONSTER_TO_QUEST_MONSTER_ID + " = " + "m." + S.COLUMN_MONSTERS_ID + " LEFT OUTER JOIN " + S.TABLE_QUESTS +
                " AS q " + " ON " + "mtq." + S.COLUMN_MONSTER_TO_QUEST_QUEST_ID + " = " + "q." + S.COLUMN_QUESTS_ID);

        QB.setDistinct(Distinct);
        QB.setProjectionMap(projectionMap);
        return QB;
    }


    /*********************************
     * MONSTER WEAKNESS QUERIES
     ******************************************/
    /* Get all weaknesses a from a particular monster */
    public MonsterWeaknessCursor queryWeaknessFromMonster(long id) {
        // SELECT * FROM monster_ailment WHERE monster_id = id

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_WEAKNESS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_WEAKNESS_MONSTER_ID + " = " + id;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterWeaknessCursor(wrapHelper(qh));
    }

    /**
     * ****************************** QUEST QUERIES *****************************************
     */

	/*
	 * Get all quests
	 */
    public QuestCursor queryQuests() {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_QUESTS;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new QuestCursor(wrapJoinHelper(builderQuest(), qh));
    }

    /*
     * Get a specific quest
     */
    public QuestCursor queryQuest(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_QUESTS;
        qh.Selection = "q." + S.COLUMN_QUESTS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new QuestCursor(wrapJoinHelper(builderQuest(), qh));
    }

    /*
     * Get a specific quest based on hub
     */
    public QuestCursor queryQuestHub(String hub) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_QUESTS;
        qh.Selection = "q." + S.COLUMN_QUESTS_HUB + " = ? AND q." + S.COLUMN_QUESTS_NAME + " <> ''";
        qh.SelectionArgs = new String[]{hub};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new QuestCursor(wrapJoinHelper(builderQuest(), qh));
    }

    /*
     * Get a specific quest based on hub and stars
     */
    public QuestCursor queryQuestHubStar(String hub, String stars) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_QUESTS;
        qh.Selection = "q." + S.COLUMN_QUESTS_HUB + " = ?" + " AND " +
                "q." + S.COLUMN_QUESTS_STARS + " = ?" + " AND " +
                "q." + S.COLUMN_QUESTS_NAME + " <> ''";
        qh.SelectionArgs = new String[]{hub, stars};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new QuestCursor(wrapJoinHelper(builderQuest(), qh));
    }

    /*
     * Helper method to query for quests
     */
    private SQLiteQueryBuilder builderQuest() {
//		SELECT q._id AS _id, q.name AS qname, q.goal, q.hub, q.type, q.stars, q.location_id, q.time_limit,
//		q.fee, q.reward, q.hrp,	l.name AS lname, l.map
//		FROM quests AS q LEFT OUTER JOIN locations AS l ON q.location_id = l._id;

        String q = "q";
        String l = "l";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", q + "." + S.COLUMN_QUESTS_ID + " AS " + "_id");
        projectionMap.put(q + S.COLUMN_QUESTS_NAME, q + "." + S.COLUMN_QUESTS_NAME + " AS " + q + S.COLUMN_QUESTS_NAME);
        projectionMap.put(S.COLUMN_QUESTS_GOAL, q + "." + S.COLUMN_QUESTS_GOAL);
        projectionMap.put(S.COLUMN_QUESTS_HUB, q + "." + S.COLUMN_QUESTS_HUB);
        projectionMap.put(S.COLUMN_QUESTS_TYPE, q + "." + S.COLUMN_QUESTS_TYPE);
        projectionMap.put(S.COLUMN_QUESTS_STARS, q + "." + S.COLUMN_QUESTS_STARS);
        projectionMap.put(S.COLUMN_QUESTS_LOCATION_ID, q + "." + S.COLUMN_QUESTS_LOCATION_ID);
        //projectionMap.put(S.COLUMN_QUESTS_LOCATION_TIME, q + "." + S.COLUMN_QUESTS_LOCATION_TIME);
        projectionMap.put(S.COLUMN_QUESTS_TIME_LIMIT, q + "." + S.COLUMN_QUESTS_TIME_LIMIT);
        projectionMap.put(S.COLUMN_QUESTS_FEE, q + "." + S.COLUMN_QUESTS_FEE);
        projectionMap.put(S.COLUMN_QUESTS_REWARD, q + "." + S.COLUMN_QUESTS_REWARD);
        projectionMap.put(S.COLUMN_QUESTS_HRP, q + "." + S.COLUMN_QUESTS_HRP);
        projectionMap.put(S.COLUMN_QUESTS_SUB_GOAL, q + "." + S.COLUMN_QUESTS_SUB_GOAL);
        projectionMap.put(S.COLUMN_QUESTS_SUB_REWARD, q + "." + S.COLUMN_QUESTS_SUB_REWARD);
        projectionMap.put(S.COLUMN_QUESTS_SUB_HRP, q + "." + S.COLUMN_QUESTS_SUB_HRP);
        projectionMap.put(l + S.COLUMN_LOCATIONS_NAME, l + "." + S.COLUMN_LOCATIONS_NAME + " AS " + l + S.COLUMN_LOCATIONS_NAME);
        projectionMap.put(S.COLUMN_LOCATIONS_MAP, l + "." + S.COLUMN_LOCATIONS_MAP);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_QUESTS + " AS q" + " LEFT OUTER JOIN " + S.TABLE_LOCATIONS + " AS l" + " ON " + "q." +
                S.COLUMN_QUESTS_LOCATION_ID + " = " + "l." + S.COLUMN_LOCATIONS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** QUEST REWARD QUERIES *****************************************
     */

	/*
	 * Get all quest reward quests based on item
	 */
    public QuestRewardCursor queryQuestRewardItem(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_QUEST_REWARDS;
        qh.Selection = "qr." + S.COLUMN_QUEST_REWARDS_ITEM_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = "q." + S.COLUMN_QUESTS_HUB + " ASC, " + "q." + S.COLUMN_QUESTS_STARS + " ASC";
        qh.Limit = null;

        return new QuestRewardCursor(wrapJoinHelper(builderQuestReward(), qh));
    }

    /*
     * Get all quest reward items based on quest
     */
    public QuestRewardCursor queryQuestRewardQuest(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_QUEST_REWARDS;
        qh.Selection = "qr." + S.COLUMN_QUEST_REWARDS_QUEST_ID + " = ? ";
        qh.SelectionArgs = new String[]{"" + id};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new QuestRewardCursor(wrapJoinHelper(builderQuestReward(), qh));
    }

    /*
     * Helper method to query for QuestReward
     */
    private SQLiteQueryBuilder builderQuestReward() {
//		SELECT qr._id AS _id, qr.quest_id, qr.item_id,
//		qr.reward_slot, qr.percentage, qr.stack_size,
//		q.name AS qname, q.hub, q.stars, i.name AS iname
//		FROM quest_rewards AS qr
//		LEFT OUTER JOIN quests AS q ON qr.quest_id = q._id
//		LEFT OUTER JOIN items AS i ON qr.item_id = i._id;

        String qr = "qr";
        String i = "i";
        String q = "q";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", qr + "." + S.COLUMN_QUEST_REWARDS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_QUEST_REWARDS_ITEM_ID, qr + "." + S.COLUMN_QUEST_REWARDS_ITEM_ID);
        projectionMap.put(S.COLUMN_QUEST_REWARDS_QUEST_ID, qr + "." + S.COLUMN_QUEST_REWARDS_QUEST_ID);
        projectionMap.put(S.COLUMN_QUEST_REWARDS_REWARD_SLOT, qr + "." + S.COLUMN_QUEST_REWARDS_REWARD_SLOT);
        projectionMap.put(S.COLUMN_QUEST_REWARDS_PERCENTAGE, qr + "." + S.COLUMN_QUEST_REWARDS_PERCENTAGE);
        projectionMap.put(S.COLUMN_QUEST_REWARDS_STACK_SIZE, qr + "." + S.COLUMN_QUEST_REWARDS_STACK_SIZE);

        projectionMap.put(i + S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME + " AS " + i + S.COLUMN_ITEMS_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(q + S.COLUMN_QUESTS_NAME, q + "." + S.COLUMN_QUESTS_NAME + " AS " + q + S.COLUMN_QUESTS_NAME);
        projectionMap.put(S.COLUMN_QUESTS_HUB, q + "." + S.COLUMN_QUESTS_HUB);
        projectionMap.put(S.COLUMN_QUESTS_STARS, q + "." + S.COLUMN_QUESTS_STARS);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_QUEST_REWARDS + " AS qr" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "qr." +
                S.COLUMN_QUEST_REWARDS_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_QUESTS +
                " AS q " + " ON " + "qr." + S.COLUMN_QUEST_REWARDS_QUEST_ID + " = " + "q." + S.COLUMN_QUESTS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** SKILL QUERIES *****************************************
     */

//	public SkillCursor querySkill(long id) {
//		// "SELECT * FROM skills WHERE skill_id = id"
//
//		_Distinct = false;
//		_Table = S.TABLE_SKILLS;
//		_Columns = null;
//		_Selection = S.COLUMN_SKILLS_ID + " = ?";
//		_SelectionArgs = new String[]{ String.valueOf(id) };
//		_GroupBy = null;
//		_Having = null;
//		_OrderBy = null;
//		_Limit = null;
//
//		return new SkillCursor(wrapHelper());
//	}

	/*
	 * Get all skills for a skill tree
	 */
    public SkillCursor querySkillFromTree(long id) {
        // "SELECT * FROM skills WHERE skill_tree_id = id"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_SKILLS;
        qh.Columns = null;
        qh.Selection = S.COLUMN_SKILLS_SKILL_TREE_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new SkillCursor(wrapHelper(qh));
    }

    /**
     * ****************************** SKILL TREE QUERIES *****************************************
     */

	/*
	 * Get all skill tress
	 */
    public SkillTreeCursor querySkillTrees() {
        // "SELECT DISTINCT * FROM skill_trees GROUP BY name"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_SKILL_TREES;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = S.COLUMN_SKILL_TREES_NAME;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new SkillTreeCursor(wrapHelper(qh));
    }

    /*
     * Get a specific skill tree
     */
    public SkillTreeCursor querySkillTree(long id) {
        // "SELECT DISTINCT * FROM skill_trees WHERE _id = id LIMIT 1"

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_SKILL_TREES;
        qh.Columns = null;
        qh.Selection = S.COLUMN_SKILL_TREES_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new SkillTreeCursor(wrapHelper(qh));
    }

    /**
     * ****************************** WEAPON QUERIES *****************************************
     */

	/*
	 * Get all weapon
	 */
    public WeaponCursor queryWeapon() {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_WEAPONS;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WeaponCursor(wrapJoinHelper(builderWeapon(), qh));
    }

    /*
     * Get a specific weapon
     */
    public WeaponCursor queryWeapon(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_WEAPONS;
        qh.Selection = "w." + S.COLUMN_WEAPONS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new WeaponCursor(wrapJoinHelper(builderWeapon(), qh));
    }

    /*
     * Get multiple specific weapon
     */
    public WeaponCursor queryWeapons(long[] ids) {

        String[] string_list = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            string_list[i] = String.valueOf(ids[i]);
        }

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_WEAPONS;
        qh.Selection = "w." + S.COLUMN_WEAPONS_ID + " IN (" + makePlaceholders(ids.length) + ")";
        qh.SelectionArgs = string_list;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WeaponCursor(wrapJoinHelper(builderWeapon(), qh));
    }

    /*
     * Get a specific weapon based on weapon type
     */
    public WeaponCursor queryWeaponType(String type) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_WEAPONS;
        qh.Selection = "w." + S.COLUMN_WEAPONS_WTYPE + " = ? ";
        qh.SelectionArgs = new String[]{type};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WeaponCursor(wrapJoinHelper(builderWeapon(), qh));
    }

    /*
     * Helper method to query for weapon
     */
    private SQLiteQueryBuilder builderWeapon() {
//		SELECT w._id AS _id, w.wtype, w.creation_cost, w.upgrade_cost, w.attack, w.max_attack,
//		w.elemental_attack, w.awakened_elemental_attack, w.defense, w.sharpness, w.affinity,
//		w.horn_notes, w.shelling_type, w.charge_levels, w.allowed_coatings, w.recoil, w.reload_speed,
//		w.rapid_fire, w.normal_shots, w.status_shots, w.elemental_shots, w.tool_shots, w.num_slots,
//		w.sharpness_file,
//		i.name, i.jpn_name, i.type, i.rarity, i.carry_capacity, i.buy, i.sell, i.description,
//		i.icon_name, i.armor_dupe_name_fix
//		FROM weapons AS w LEFT OUTER JOIN	items AS i ON w._id = i._id;

        String w = "w";
        String i = "i";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", w + "." + S.COLUMN_WEAPONS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_WEAPONS_WTYPE, w + "." + S.COLUMN_WEAPONS_WTYPE);
        projectionMap.put(S.COLUMN_WEAPONS_CREATION_COST, w + "." + S.COLUMN_WEAPONS_CREATION_COST);
        projectionMap.put(S.COLUMN_WEAPONS_UPGRADE_COST, w + "." + S.COLUMN_WEAPONS_UPGRADE_COST);
        projectionMap.put(S.COLUMN_WEAPONS_ATTACK, w + "." + S.COLUMN_WEAPONS_ATTACK);
        projectionMap.put(S.COLUMN_WEAPONS_MAX_ATTACK, w + "." + S.COLUMN_WEAPONS_MAX_ATTACK);
        projectionMap.put(S.COLUMN_WEAPONS_ELEMENT, w + "." + S.COLUMN_WEAPONS_ELEMENT);
        projectionMap.put(S.COLUMN_WEAPONS_AWAKEN, w + "." + S.COLUMN_WEAPONS_AWAKEN);
        projectionMap.put(S.COLUMN_WEAPONS_ELEMENT_2, w + "." + S.COLUMN_WEAPONS_ELEMENT_2);
        projectionMap.put(S.COLUMN_WEAPONS_AWAKEN_ATTACK, w + "." + S.COLUMN_WEAPONS_AWAKEN_ATTACK);
        projectionMap.put(S.COLUMN_WEAPONS_ELEMENT_ATTACK, w + "." + S.COLUMN_WEAPONS_ELEMENT_ATTACK);
        projectionMap.put(S.COLUMN_WEAPONS_ELEMENT_2_ATTACK, w + "." + S.COLUMN_WEAPONS_ELEMENT_2_ATTACK);
        projectionMap.put(S.COLUMN_WEAPONS_DEFENSE, w + "." + S.COLUMN_WEAPONS_DEFENSE);
        projectionMap.put(S.COLUMN_WEAPONS_SHARPNESS, w + "." + S.COLUMN_WEAPONS_SHARPNESS);
        projectionMap.put(S.COLUMN_WEAPONS_AFFINITY, w + "." + S.COLUMN_WEAPONS_AFFINITY);
        projectionMap.put(S.COLUMN_WEAPONS_HORN_NOTES, w + "." + S.COLUMN_WEAPONS_HORN_NOTES);
        projectionMap.put(S.COLUMN_WEAPONS_SHELLING_TYPE, w + "." + S.COLUMN_WEAPONS_SHELLING_TYPE);
        projectionMap.put(S.COLUMN_WEAPONS_PHIAL, w + "." + S.COLUMN_WEAPONS_PHIAL);
        projectionMap.put(S.COLUMN_WEAPONS_CHARGES, w + "." + S.COLUMN_WEAPONS_CHARGES);
        projectionMap.put(S.COLUMN_WEAPONS_COATINGS, w + "." + S.COLUMN_WEAPONS_COATINGS);
        projectionMap.put(S.COLUMN_WEAPONS_RECOIL, w + "." + S.COLUMN_WEAPONS_RECOIL);
        projectionMap.put(S.COLUMN_WEAPONS_RELOAD_SPEED, w + "." + S.COLUMN_WEAPONS_RELOAD_SPEED);
        projectionMap.put(S.COLUMN_WEAPONS_RAPID_FIRE, w + "." + S.COLUMN_WEAPONS_RAPID_FIRE);
        projectionMap.put(S.COLUMN_WEAPONS_DEVIATION, w + "." + S.COLUMN_WEAPONS_DEVIATION);
        projectionMap.put(S.COLUMN_WEAPONS_AMMO, w + "." + S.COLUMN_WEAPONS_AMMO);
        projectionMap.put(S.COLUMN_WEAPONS_NUM_SLOTS, w + "." + S.COLUMN_WEAPONS_NUM_SLOTS);
        projectionMap.put(S.COLUMN_WEAPONS_FINAL, w + "." + S.COLUMN_WEAPONS_FINAL);
        projectionMap.put(S.COLUMN_WEAPONS_TREE_DEPTH, w + "." + S.COLUMN_WEAPONS_TREE_DEPTH);
        projectionMap.put(S.COLUMN_WEAPONS_PARENT_ID, w + "." + S.COLUMN_WEAPONS_PARENT_ID);

        projectionMap.put(S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME);
        projectionMap.put(S.COLUMN_ITEMS_JPN_NAME, i + "." + S.COLUMN_ITEMS_JPN_NAME);
        projectionMap.put(S.COLUMN_ITEMS_TYPE, i + "." + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_SUB_TYPE, i + "." + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_RARITY, i + "." + S.COLUMN_ITEMS_RARITY);
        projectionMap.put(S.COLUMN_ITEMS_CARRY_CAPACITY, i + "." + S.COLUMN_ITEMS_CARRY_CAPACITY);
        projectionMap.put(S.COLUMN_ITEMS_BUY, i + "." + S.COLUMN_ITEMS_BUY);
        projectionMap.put(S.COLUMN_ITEMS_SELL, i + "." + S.COLUMN_ITEMS_SELL);
        projectionMap.put(S.COLUMN_ITEMS_DESCRIPTION, i + "." + S.COLUMN_ITEMS_DESCRIPTION);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX, i + "." + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_WEAPONS + " AS w" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "w." +
                S.COLUMN_WEAPONS_ID + " = " + "i." + S.COLUMN_ITEMS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** WEAPON TREE QUERIES *****************************************
     */

	/*
	 * Get the parent weapon
	 */
    public WeaponTreeCursor queryWeaponTreeParent(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Selection = "i1." + S.COLUMN_ITEMS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WeaponTreeCursor(wrapJoinHelper(builderWeaponTreeParent(), qh));
    }

    /*
     * Get the child weapon
     */
    public WeaponTreeCursor queryWeaponTreeChild(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Selection = "i1." + S.COLUMN_ITEMS_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WeaponTreeCursor(wrapJoinHelper(builderWeaponTreeChild(), qh));
    }

    /*
     * Helper method to query for weapon tree parent
     */
    private SQLiteQueryBuilder builderWeaponTreeParent() {
//		SELECT i2._id, i2.name
//		FROM items AS i1
//		LEFT OUTER JOIN components AS c ON i1._id = c.created_item_id
//		JOIN weapons AS w2 ON w2._id = c.component_item_id
//		LEFT OUTER JOIN items AS i2 ON i2._id = w2._id
//
//		WHERE i1._id = 'id';

        String i1 = "i1";
        String i2 = "i2";
        String w2 = "w2";
        String c = "c";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", i2 + "." + S.COLUMN_ITEMS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_ITEMS_NAME, i2 + "." + S.COLUMN_ITEMS_NAME);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_ITEMS + " AS i1" + " LEFT OUTER JOIN " + S.TABLE_COMPONENTS + " AS c" +
                        " ON " + "i1." + S.COLUMN_ITEMS_ID + " = " + "c." + S.COLUMN_COMPONENTS_CREATED_ITEM_ID +
                        " JOIN " + S.TABLE_WEAPONS + " AS w2" + " ON " + "w2." + S.COLUMN_WEAPONS_ID + " = " +
                        "c." + S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID + " LEFT OUTER JOIN " + S.TABLE_ITEMS +
                        " AS i2" + " ON " + "i2." + S.COLUMN_ITEMS_ID + " = " + "w2." + S.COLUMN_WEAPONS_ID
        );

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /*
     * Helper method to query for weapon tree child
     */
    private SQLiteQueryBuilder builderWeaponTreeChild() {
//		SELECT i2._id, i2.name
//		FROM items AS i1
//		LEFT OUTER JOIN components AS c ON i1._id = c.component_item_id
//		JOIN weapons AS w2 ON w2._id = c.created_item_id
//		LEFT OUTER JOIN items AS i2 ON i2._id = w2._id
//
//		WHERE i1._id = '_id';

        String i1 = "i1";
        String i2 = "i2";
        String w2 = "w2";
        String c = "c";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", i2 + "." + S.COLUMN_ITEMS_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_ITEMS_NAME, i2 + "." + S.COLUMN_ITEMS_NAME);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_ITEMS + " AS i1" + " LEFT OUTER JOIN " + S.TABLE_COMPONENTS + " AS c" +
                        " ON " + "i1." + S.COLUMN_ITEMS_ID + " = " + "c." + S.COLUMN_COMPONENTS_COMPONENT_ITEM_ID +
                        " JOIN " + S.TABLE_WEAPONS + " AS w2" + " ON " + "w2." + S.COLUMN_WEAPONS_ID + " = " +
                        "c." + S.COLUMN_COMPONENTS_CREATED_ITEM_ID + " LEFT OUTER JOIN " + S.TABLE_ITEMS +
                        " AS i2" + " ON " + "i2." + S.COLUMN_ITEMS_ID + " = " + "w2." + S.COLUMN_WEAPONS_ID
        );

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** WISHLIST QUERIES *****************************************
     */

	/*
	 * Get all wishlist
	 */
    public WishlistCursor queryWishlists() {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_WISHLIST;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WishlistCursor(wrapHelper(qh));
    }

    /*
     * Get all wishlist using a specific db instance
     */
    public WishlistCursor queryWishlists(SQLiteDatabase db) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_WISHLIST;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WishlistCursor(wrapHelper(db, qh));
    }

    /*
     * Get a specific wishlist
     */
    public WishlistCursor queryWishlist(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_WISHLIST;
        qh.Columns = null;
        qh.Selection = S.COLUMN_WISHLIST_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new WishlistCursor(wrapHelper(qh));
    }

    /*
     * Add a wishlist
     */
    public long queryAddWishlist(String name) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_NAME, name);

        return insertRecord(S.TABLE_WISHLIST, values);
    }

    /*
     * Add a wishlist with all info
     */
    public long queryAddWishlistAll(SQLiteDatabase db, long id, String name) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_ID, id);
        values.put(S.COLUMN_WISHLIST_NAME, name);

        return insertRecord(db, S.TABLE_WISHLIST, values);
    }

    public int queryUpdateWishlist(long id, String name) {
        String strFilter = S.COLUMN_WISHLIST_ID + " = " + id;

        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_NAME, name);

        return updateRecord(S.TABLE_WISHLIST, strFilter, values);
    }

    public boolean queryDeleteWishlist(long id) {
        String where = S.COLUMN_WISHLIST_ID + " = ?";
        String[] args = new String[]{"" + id};
        boolean w1 = deleteRecord(S.TABLE_WISHLIST, where, args);

        where = S.COLUMN_WISHLIST_DATA_WISHLIST_ID + " = ?";
        boolean w2 = deleteRecord(S.TABLE_WISHLIST_DATA, where, args);

        return (w1 && w2);
    }

    /**
     * ****************************** WISHLIST DATA QUERIES *****************************************
     */

	/*
	 * Get all wishlist data
	 */
    public WishlistDataCursor queryWishlistsData() {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_WISHLIST_DATA;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistData();
        Cursor cursor = qb.query(
                getWritableDatabase(), qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);

        return new WishlistDataCursor(cursor);
    }

    /*
     * Get all wishlist data using specific db instance
     */
    public WishlistDataCursor queryWishlistsData(SQLiteDatabase db) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_WISHLIST_DATA;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistData();
        Cursor cursor = qb.query(
                db, qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);

        return new WishlistDataCursor(cursor);
    }

    /*
     * Get all wishlist data for a specific wishlist
     */
    public WishlistDataCursor queryWishlistData(long id) {

        String[] wdColumns = null;
        String wdSelection = "wd." + S.COLUMN_WISHLIST_DATA_WISHLIST_ID + " = ?";
        String[] wdSelectionArgs = new String[]{String.valueOf(id)};
        String wdGroupBy = null;
        String wdHaving = null;
        String wdOrderBy = "wd." + S.COLUMN_WISHLIST_DATA_ITEM_ID + " ASC";
        String wdLimit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistData();
        Cursor cursor = qb.query(
                getWritableDatabase(), wdColumns, wdSelection, wdSelectionArgs, wdGroupBy, wdHaving, wdOrderBy, wdLimit);

        return new WishlistDataCursor(cursor);
    }


    /*
     * Get all wishlist data for a specific wishlist data id
     */
    public WishlistDataCursor queryWishlistDataId(long id) {

        String[] wdColumns = null;
        String wdSelection = "wd." + S.COLUMN_WISHLIST_DATA_ID + " = ?";
        String[] wdSelectionArgs = new String[]{String.valueOf(id)};
        String wdGroupBy = null;
        String wdHaving = null;
        String wdOrderBy = null;
        String wdLimit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistData();
        Cursor cursor = qb.query(
                getWritableDatabase(), wdColumns, wdSelection, wdSelectionArgs, wdGroupBy, wdHaving, wdOrderBy, wdLimit);

        return new WishlistDataCursor(cursor);
    }

    /*
     * Get all data for a specific wishlist and item
     */
    public WishlistDataCursor queryWishlistData(long wd_id, long item_id, String path) {

        String[] wdColumns = null;
        String wdSelection = "wd." + S.COLUMN_WISHLIST_DATA_WISHLIST_ID + " = ?" +
                " AND " + "wd." + S.COLUMN_WISHLIST_DATA_ITEM_ID + " = ?" +
                " AND " + "wd." + S.COLUMN_WISHLIST_DATA_PATH + " = ?";
        String[] wdSelectionArgs = new String[]{String.valueOf(wd_id), String.valueOf(item_id), path};
        String wdGroupBy = null;
        String wdHaving = null;
        String wdOrderBy = null;
        String wdLimit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistData();
        Cursor cursor = qb.query(
                getWritableDatabase(), wdColumns, wdSelection, wdSelectionArgs, wdGroupBy, wdHaving, wdOrderBy, wdLimit);

        return new WishlistDataCursor(cursor);
    }

    /*
     * Add a wishlist data to a specific wishlist
     */
    public long queryAddWishlistData(long wishlist_id, long item_id,
                                     int quantity, String path) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_DATA_WISHLIST_ID, wishlist_id);
        values.put(S.COLUMN_WISHLIST_DATA_ITEM_ID, item_id);
        values.put(S.COLUMN_WISHLIST_DATA_QUANTITY, quantity);
        values.put(S.COLUMN_WISHLIST_DATA_PATH, path);

        return insertRecord(S.TABLE_WISHLIST_DATA, values);
    }

    /*
     * Add a wishlist data to a specific wishlist for copying
     */
    public long queryAddWishlistDataAll(long wishlist_id, long item_id,
                                        int quantity, int satisfied, String path) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_DATA_WISHLIST_ID, wishlist_id);
        values.put(S.COLUMN_WISHLIST_DATA_ITEM_ID, item_id);
        values.put(S.COLUMN_WISHLIST_DATA_QUANTITY, quantity);
        values.put(S.COLUMN_WISHLIST_DATA_SATISFIED, satisfied);
        values.put(S.COLUMN_WISHLIST_DATA_PATH, path);

        return insertRecord(S.TABLE_WISHLIST_DATA, values);
    }

    /*
     * Add a wishlist data to a specific wishlist for copying
     */
    public long queryAddWishlistDataAll(SQLiteDatabase db, long wishlist_id, long item_id,
                                        int quantity, int satisfied, String path) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_DATA_WISHLIST_ID, wishlist_id);
        values.put(S.COLUMN_WISHLIST_DATA_ITEM_ID, item_id);
        values.put(S.COLUMN_WISHLIST_DATA_QUANTITY, quantity);
        values.put(S.COLUMN_WISHLIST_DATA_SATISFIED, satisfied);
        values.put(S.COLUMN_WISHLIST_DATA_PATH, path);

        return insertRecord(db, S.TABLE_WISHLIST_DATA, values);
    }

    /*
     * Update a wishlist data to a specific wishlist
     */
    public int queryUpdateWishlistDataQuantity(long id, int quantity) {
        String strFilter = S.COLUMN_WISHLIST_DATA_ID + " = " + id;

        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_DATA_QUANTITY, quantity);

        return updateRecord(S.TABLE_WISHLIST_DATA, strFilter, values);
    }

    /*
     * Update a wishlist data to a specific wishlist
     */
    public int queryUpdateWishlistDataSatisfied(long id, int satisfied) {
        String strFilter = S.COLUMN_WISHLIST_DATA_ID + " = " + id;

        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_DATA_SATISFIED, satisfied);

        return updateRecord(S.TABLE_WISHLIST_DATA, strFilter, values);
    }

    public boolean queryDeleteWishlistData(long id) {
        String where = S.COLUMN_WISHLIST_DATA_ID + " = ?";
        String[] args = new String[]{"" + id};
        return deleteRecord(S.TABLE_WISHLIST_DATA, where, args);
    }

    /*
     * Helper method to query for wishlistData
     */
    private SQLiteQueryBuilder builderWishlistData() {
//		SELECT wd._id AS _id, wd.wishlist_id, wd.item_id, wd.quantity, wd.satisfied, wd.path
//		i.name, i.jpn_name, i.type, i.rarity, i.carry_capacity, i.buy, i.sell, i.description,
//		i.icon_name, i.armor_dupe_name_fix
//		FROM wishlist_data AS wd
//		LEFT OUTER JOIN wishlist AS w ON wd.wishlist_id = w._id
//		LEFT OUTER JOIN	items AS i ON wd.item_id = i._id;

        String wd = "wd";
        String w = "w";
        String i = "i";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", wd + "." + S.COLUMN_WISHLIST_DATA_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_WISHLIST_DATA_WISHLIST_ID, wd + "." + S.COLUMN_WISHLIST_DATA_WISHLIST_ID);
        projectionMap.put(S.COLUMN_WISHLIST_DATA_ITEM_ID, wd + "." + S.COLUMN_WISHLIST_DATA_ITEM_ID);
        projectionMap.put(S.COLUMN_WISHLIST_DATA_QUANTITY, wd + "." + S.COLUMN_WISHLIST_DATA_QUANTITY);
        projectionMap.put(S.COLUMN_WISHLIST_DATA_SATISFIED, wd + "." + S.COLUMN_WISHLIST_DATA_SATISFIED);
        projectionMap.put(S.COLUMN_WISHLIST_DATA_PATH, wd + "." + S.COLUMN_WISHLIST_DATA_PATH);

        projectionMap.put(S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME);
        //projectionMap.put(S.COLUMN_ITEMS_JPN_NAME, i + "." + S.COLUMN_ITEMS_JPN_NAME);
        projectionMap.put(S.COLUMN_ITEMS_TYPE, i + "." + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_SUB_TYPE, i + "." + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_RARITY, i + "." + S.COLUMN_ITEMS_RARITY);
        projectionMap.put(S.COLUMN_ITEMS_CARRY_CAPACITY, i + "." + S.COLUMN_ITEMS_CARRY_CAPACITY);
        projectionMap.put(S.COLUMN_ITEMS_BUY, i + "." + S.COLUMN_ITEMS_BUY);
        projectionMap.put(S.COLUMN_ITEMS_SELL, i + "." + S.COLUMN_ITEMS_SELL);
        projectionMap.put(S.COLUMN_ITEMS_DESCRIPTION, i + "." + S.COLUMN_ITEMS_DESCRIPTION);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX, i + "." + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_WISHLIST_DATA + " AS wd" + " LEFT OUTER JOIN " + S.TABLE_WISHLIST + " AS w" + " ON " +
                "wd." + S.COLUMN_WISHLIST_DATA_WISHLIST_ID + " = " + "w." + S.COLUMN_WISHLIST_ID + " LEFT OUTER JOIN " +
                S.TABLE_ITEMS + " AS i" + " ON " + "wd." + S.COLUMN_WISHLIST_DATA_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * ****************************** WISHLIST COMPONENT QUERIES *****************************************
     */

	/*
	 * Get all wishlist components
	 */
    public WishlistComponentCursor queryWishlistsComponent() {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_WISHLIST_COMPONENT;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistComponent();
        Cursor cursor = qb.query(
                getWritableDatabase(), qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);

        return new WishlistComponentCursor(cursor);
    }

    /**
     * Get all wishlist components using a specific db instance
     * @param db
     * @return
     */
    public WishlistComponentCursor queryWishlistsComponent(SQLiteDatabase db) {

        QueryHelper qh = new QueryHelper();
        qh.Distinct = false;
        qh.Table = S.TABLE_WISHLIST_COMPONENT;
        qh.Columns = null;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistComponent();
        Cursor cursor = qb.query(
                db, qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);

        return new WishlistComponentCursor(cursor);
    }

    /*
     * Get all wishlist components for a specific wishlist
     */
    public WishlistComponentCursor queryWishlistComponents(long id) {

        String[] wcColumns = null;
        String wcSelection = "wc." + S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID + " = ?";
        String[] wcSelectionArgs = new String[]{String.valueOf(id)};
        String wcGroupBy = null;
        String wcHaving = null;
        String wcOrderBy = "wc." + S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID + " ASC";
        String wcLimit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistComponent();
        Cursor cursor = qb.query(
                getWritableDatabase(), wcColumns, wcSelection, wcSelectionArgs, wcGroupBy, wcHaving, wcOrderBy, wcLimit);

        return new WishlistComponentCursor(cursor);
    }

    /*
     * Get all data for a specific wishlist and item
     */
    public WishlistComponentCursor queryWishlistComponent(long wc_id, long item_id) {

        String[] wcColumns = null;
        String wcSelection = "wc." + S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID + " = ?" + " AND " +
                "wc." + S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID + " = ?";
        String[] wcSelectionArgs = new String[]{String.valueOf(wc_id), String.valueOf(item_id)};
        String wcGroupBy = null;
        String wcHaving = null;
        String wcOrderBy = null;
        String wcLimit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistComponent();
        Cursor cursor = qb.query(
                getWritableDatabase(), wcColumns, wcSelection, wcSelectionArgs, wcGroupBy, wcHaving, wcOrderBy, wcLimit);

        return new WishlistComponentCursor(cursor);
    }

    /*
     * Get all wishlist components for a specific id
     */
    public WishlistComponentCursor queryWishlistComponentId(long id) {

        String[] wcColumns = null;
        String wcSelection = "wc." + S.COLUMN_WISHLIST_COMPONENT_ID + " = ?";
        String[] wcSelectionArgs = new String[]{String.valueOf(id)};
        String wcGroupBy = null;
        String wcHaving = null;
        String wcOrderBy = null;
        String wcLimit = null;

        // Multithread issues workaround
        SQLiteQueryBuilder qb = builderWishlistComponent();
        Cursor cursor = qb.query(
                getWritableDatabase(), wcColumns, wcSelection, wcSelectionArgs, wcGroupBy, wcHaving, wcOrderBy, wcLimit);

        return new WishlistComponentCursor(cursor);
    }

    /*
     * Add a wishlist component to a specific wishlist
     */
    public long queryAddWishlistComponent(long wishlist_id, long component_id, int quantity) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID, wishlist_id);
        values.put(S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID, component_id);
        values.put(S.COLUMN_WISHLIST_COMPONENT_QUANTITY, quantity);

        return insertRecord(S.TABLE_WISHLIST_COMPONENT, values);
    }

    /*
     * Add a wishlist component to a specific wishlist
     */
    public long queryAddWishlistComponentAll(long wishlist_id, long component_id, int quantity, int notes) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID, wishlist_id);
        values.put(S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID, component_id);
        values.put(S.COLUMN_WISHLIST_COMPONENT_QUANTITY, quantity);
        values.put(S.COLUMN_WISHLIST_COMPONENT_NOTES, notes);

        return insertRecord(S.TABLE_WISHLIST_COMPONENT, values);
    }

    /*
     * Add a wishlist component to a specific wishlist
     */
    public long queryAddWishlistComponentAll(SQLiteDatabase db, long wishlist_id, long component_id, int quantity, int notes) {
        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID, wishlist_id);
        values.put(S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID, component_id);
        values.put(S.COLUMN_WISHLIST_COMPONENT_QUANTITY, quantity);
        values.put(S.COLUMN_WISHLIST_COMPONENT_NOTES, notes);

        return insertRecord(db, S.TABLE_WISHLIST_COMPONENT, values);
    }

    /*
     * Update a wishlist component to a specific wishlist
     */
    public int queryUpdateWishlistComponentQuantity(long id, int quantity) {
        String strFilter = S.COLUMN_WISHLIST_COMPONENT_ID + " = " + id;

        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_COMPONENT_QUANTITY, quantity);

        return updateRecord(S.TABLE_WISHLIST_COMPONENT, strFilter, values);
    }

    public boolean queryDeleteWishlistComponent(long id) {
        String where = S.COLUMN_WISHLIST_COMPONENT_ID + " = ?";
        String[] args = new String[]{"" + id};
        return deleteRecord(S.TABLE_WISHLIST_COMPONENT, where, args);
    }

    /*
     * Update a wishlist component to a specific wishlist
     */
    public int queryUpdateWishlistComponentNotes(long id, int notes) {
        String strFilter = S.COLUMN_WISHLIST_COMPONENT_ID + " = " + id;

        ContentValues values = new ContentValues();
        values.put(S.COLUMN_WISHLIST_COMPONENT_NOTES, notes);

        return updateRecord(S.TABLE_WISHLIST_COMPONENT, strFilter, values);
    }

    /*
     * Helper method to query components for wishlistData
     */
    private SQLiteQueryBuilder builderWishlistComponent() {

//		SELECT wc._id AS _id, wc.wishlist_id, wc.component_id, wc.quantity, wc.notes
//		i.name, i.jpn_name, i.type, i.sub_type, i.rarity, i.carry_capacity, i.buy, i.sell, i.description,
//		i.icon_name, i.armor_dupe_name_fix
//		FROM wishlist_component AS wc
//		LEFT OUTER JOIN wishlist AS w ON wd.wishlist_id = w._ic
//		LEFT OUTER JOIN	items AS i ON wc.component_id = i._id;

        String wc = "wc";
        String w = "w";
        String i = "i";

        HashMap<String, String> projectionMap = new HashMap<String, String>();

        projectionMap.put("_id", wc + "." + S.COLUMN_WISHLIST_COMPONENT_ID + " AS " + "_id");
        projectionMap.put(S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID, wc + "." + S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID);
        projectionMap.put(S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID, wc + "." + S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID);
        projectionMap.put(S.COLUMN_WISHLIST_COMPONENT_QUANTITY, wc + "." + S.COLUMN_WISHLIST_COMPONENT_QUANTITY);
        projectionMap.put(S.COLUMN_WISHLIST_COMPONENT_NOTES, wc + "." + S.COLUMN_WISHLIST_COMPONENT_NOTES);

        projectionMap.put(S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME);
        //projectionMap.put(S.COLUMN_ITEMS_JPN_NAME, i + "." + S.COLUMN_ITEMS_JPN_NAME);
        projectionMap.put(S.COLUMN_ITEMS_TYPE, i + "." + S.COLUMN_ITEMS_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_SUB_TYPE, i + "." + S.COLUMN_ITEMS_SUB_TYPE);
        projectionMap.put(S.COLUMN_ITEMS_RARITY, i + "." + S.COLUMN_ITEMS_RARITY);
        projectionMap.put(S.COLUMN_ITEMS_CARRY_CAPACITY, i + "." + S.COLUMN_ITEMS_CARRY_CAPACITY);
        projectionMap.put(S.COLUMN_ITEMS_BUY, i + "." + S.COLUMN_ITEMS_BUY);
        projectionMap.put(S.COLUMN_ITEMS_SELL, i + "." + S.COLUMN_ITEMS_SELL);
        projectionMap.put(S.COLUMN_ITEMS_DESCRIPTION, i + "." + S.COLUMN_ITEMS_DESCRIPTION);
        projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
        projectionMap.put(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX, i + "." + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX);

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_WISHLIST_COMPONENT + " AS wc" + " LEFT OUTER JOIN " + S.TABLE_WISHLIST + " AS w" + " ON " +
                "wc." + S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID + " = " + "w." + S.COLUMN_WISHLIST_ID + " LEFT OUTER JOIN " +
                S.TABLE_ITEMS + " AS i" + " ON " + "wc." + S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID + " = " +
                "i." + S.COLUMN_ITEMS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /**
     * *************************** WYPORIUM TRADE QUERIES ***************************************
     */

	/*
	 * Get all trades
	 */
    public WyporiumTradeCursor queryWyporiumTrades() {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_WYPORIUM_TRADE;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new WyporiumTradeCursor(wrapJoinHelper(builderWyporiumTrade(), qh));
    }

    /*
	 * Get a specific wyporium trade
	 */
    public WyporiumTradeCursor queryWyporiumTrades(long id) {

        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_WYPORIUM_TRADE;
        qh.Selection = "wt.item_in_id = ? OR wt.item_out_id = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id), String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new WyporiumTradeCursor(wrapJoinHelper(builderWyporiumTrade(), qh));
    }

    /*
     * Helper method to query for wyporium trades
     */
    private SQLiteQueryBuilder builderWyporiumTrade() {
//      SELECT wt._id AS trade_id, wt.item_in_id AS in_id, wt.item_out_id AS out_id, wt.unlock_quest_id AS q_id,
//      i1.name AS in_name, i1.icon_name AS in_icon_name, i2.name AS out_name, i2.icon_name AS out_icon_name,
//      q.name AS q_name
//      FROM wyporium AS wt LEFT OUTER JOIN items AS i1 ON wt.item_in_id = i1._id
//      LEFT OUTER JOIN items AS i2 ON wt.item_out_id = i2._id
//      LEFT OUTER JOIN quests AS q ON wt.unlock_quest_id = q._id;

        HashMap<String, String> projectionMap = new HashMap<String, String>();
        projectionMap.put("trade_id", "wt." + S.COLUMN_WYPORIUM_TRADE_ID + " AS " + "trade_id");
        projectionMap.put("in_id", "wt." + S.COLUMN_WYPORIUM_TRADE_ITEM_IN_ID + " AS " + "in_id");
        projectionMap.put("out_id", "wt." + S.COLUMN_WYPORIUM_TRADE_ITEM_OUT_ID + " AS " + "out_id");
        projectionMap.put("q_id", "wt." + S.COLUMN_WYPORIUM_TRADE_UNLOCK_QUEST_ID + " AS " + "q_id");
        projectionMap.put(S.COLUMN_ITEMS_ID, "i1." + S.COLUMN_ITEMS_ID);
        projectionMap.put("in_name", "i1." + S.COLUMN_ITEMS_NAME + " AS " + "in_name");
        projectionMap.put("in_icon_name", "i1." + S.COLUMN_ITEMS_ICON_NAME + " AS " + "in_icon_name");
        projectionMap.put(S.COLUMN_ITEMS_ID, "i2." + S.COLUMN_ITEMS_ID);
        projectionMap.put("out_name", "i2." + S.COLUMN_ITEMS_NAME + " AS " + "out_name");
        projectionMap.put("out_icon_name", "i2." + S.COLUMN_ITEMS_ICON_NAME + " AS " + "out_icon_name");
        projectionMap.put(S.COLUMN_QUESTS_ID, "q." + S.COLUMN_QUESTS_ID);
        projectionMap.put("q_name", "q." + S.COLUMN_QUESTS_NAME + " AS " + "q_name");

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_WYPORIUM_TRADE + " AS wt" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i1" + " ON " + "wt." +
                S.COLUMN_WYPORIUM_TRADE_ITEM_IN_ID + " = " + "i1." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_ITEMS +
                " AS i2 " + " ON " + "wt." + S.COLUMN_WYPORIUM_TRADE_ITEM_OUT_ID + " = " + "i2." + S.COLUMN_ITEMS_ID +
                " LEFT OUTER JOIN " + S.TABLE_QUESTS + " AS q " + " ON " + "wt." + S.COLUMN_WYPORIUM_TRADE_UNLOCK_QUEST_ID + " = " +
                "q." + S.COLUMN_QUESTS_ID);

        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /********************************* ARMOR SET BUILDER QUERIES ******************************************/

    /**
     * Get all armor sets.
     */
    public ASBSetCursor queryASBSets() {
        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ASB_SETS;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ASBSetCursor(wrapJoinHelper(builderASBSet(), qh));
    }

    /**
     * Retrieves a specific Armor Set Builder set in the database.
     */
    public ASBSetCursor queryASBSet(long id) {
        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ASB_SETS;
        qh.Selection = "ar." + S.COLUMN_ASB_SET_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new ASBSetCursor(wrapJoinHelper(builderASBSet(), qh));
    }

    /**
     * Get all armor sets.
     */
    public ASBSessionCursor queryASBSessions() {
        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ASB_SETS;
        qh.Selection = null;
        qh.SelectionArgs = null;
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new ASBSessionCursor(wrapJoinHelper(builderASBSession(), qh));
    }

    /**
     * Get all armor sets.
     */
    public Cursor queryASBSessions(SQLiteDatabase db) {
        return db.rawQuery("SELECT * FROM " + S.TABLE_ASB_SETS, null);
    }

    /**
     * Retrieves a specific Armor Set Builder set in the database.
     */
    public ASBSessionCursor queryASBSession(long id) {
        QueryHelper qh = new QueryHelper();
        qh.Columns = null;
        qh.Table = S.TABLE_ASB_SETS;
        qh.Selection = "ar." + S.COLUMN_ASB_SET_ID + " = ?";
        qh.SelectionArgs = new String[]{String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = "1";

        return new ASBSessionCursor(wrapJoinHelper(builderASBSession(), qh));
    }

    /**
     * Creates a new Armor Set Builder set in the entries of the database.
     */
    public long queryAddASBSet(String name, int rank, int hunterType) {
        ContentValues values = new ContentValues();

        values.put(S.COLUMN_ASB_SET_NAME, name);
        values.put(S.COLUMN_ASB_SET_RANK, rank);
        values.put(S.COLUMN_ASB_SET_HUNTER_TYPE, hunterType);
        values.put(S.COLUMN_TALISMAN_EXISTS, 0);

        return insertRecord(S.TABLE_ASB_SETS, values);
    }

    public long queryUpdateASBSet(long asbSetId, String name, int rank, int hunterType) {
        String filter = S.COLUMN_ASB_SET_ID + " = " + asbSetId;

        ContentValues values = new ContentValues();

        values.put(S.COLUMN_ASB_SET_NAME, name);
        values.put(S.COLUMN_ASB_SET_RANK, rank);
        values.put(S.COLUMN_ASB_SET_HUNTER_TYPE, hunterType);
        values.put(S.COLUMN_TALISMAN_EXISTS, 0);

        return updateRecord(S.TABLE_ASB_SETS, filter, values);
    }

    public boolean queryDeleteASBSet(long setId) {
        String filter = S.COLUMN_ASB_SET_ID + " = " + setId;

        return deleteRecord(S.TABLE_ASB_SETS, filter, new String[0]);
    }

    public long queryAddASBSessionArmor(long asbSetId, long pieceId, int pieceIndex) {
        String filter = S.COLUMN_ASB_SET_ID + " = " + asbSetId;

        ContentValues values = new ContentValues();

        switch (pieceIndex) {
            case ASBSession.HEAD:
                putASBSessionItemOrNull(values, S.COLUMN_HEAD_ARMOR_ID, pieceId);
                break;
            case ASBSession.BODY:
                putASBSessionItemOrNull(values, S.COLUMN_BODY_ARMOR_ID, pieceId);
                break;
            case ASBSession.ARMS:
                putASBSessionItemOrNull(values, S.COLUMN_ARMS_ARMOR_ID, pieceId);
                break;
            case ASBSession.WAIST:
                putASBSessionItemOrNull(values, S.COLUMN_WAIST_ARMOR_ID, pieceId);
                break;
            case ASBSession.LEGS:
                putASBSessionItemOrNull(values, S.COLUMN_LEGS_ARMOR_ID, pieceId);
                break;
        }

        return updateRecord(S.TABLE_ASB_SETS, filter, values);
    }

    public long queryPutASBSessionDecoration(long asbSetId, long decorationId, int pieceIndex, int decorationIndex) {
        String filter = S.COLUMN_ASB_SET_ID + " = " + asbSetId;

        ContentValues values = new ContentValues();

        switch (pieceIndex) {
            case ASBSession.HEAD:
                if (decorationIndex == 0) {
                    putASBSessionItemOrNull(values, S.COLUMN_HEAD_DECORATION_1_ID, decorationId);
                } else if (decorationIndex == 1) {
                    putASBSessionItemOrNull(values, S.COLUMN_HEAD_DECORATION_2_ID, decorationId);
                } else if (decorationIndex == 2) {
                    putASBSessionItemOrNull(values, S.COLUMN_HEAD_DECORATION_3_ID, decorationId);
                }
                break;
            case ASBSession.BODY:
                if (decorationIndex == 0) {
                    putASBSessionItemOrNull(values, S.COLUMN_BODY_DECORATION_1_ID, decorationId);
                } else if (decorationIndex == 1) {
                    putASBSessionItemOrNull(values, S.COLUMN_BODY_DECORATION_2_ID, decorationId);
                } else if (decorationIndex == 2) {
                    putASBSessionItemOrNull(values, S.COLUMN_BODY_DECORATION_3_ID, decorationId);
                }
                break;
            case ASBSession.ARMS:
                if (decorationIndex == 0) {
                    putASBSessionItemOrNull(values, S.COLUMN_ARMS_DECORATION_1_ID, decorationId);
                } else if (decorationIndex == 1) {
                    putASBSessionItemOrNull(values, S.COLUMN_ARMS_DECORATION_2_ID, decorationId);
                } else if (decorationIndex == 2) {
                    putASBSessionItemOrNull(values, S.COLUMN_ARMS_DECORATION_3_ID, decorationId);
                }
                break;
            case ASBSession.WAIST:
                if (decorationIndex == 0) {
                    putASBSessionItemOrNull(values, S.COLUMN_WAIST_DECORATION_1_ID, decorationId);
                } else if (decorationIndex == 1) {
                    putASBSessionItemOrNull(values, S.COLUMN_WAIST_DECORATION_2_ID, decorationId);
                } else if (decorationIndex == 2) {
                    putASBSessionItemOrNull(values, S.COLUMN_WAIST_DECORATION_3_ID, decorationId);
                }
                break;
            case ASBSession.LEGS:
                if (decorationIndex == 0) {
                    putASBSessionItemOrNull(values, S.COLUMN_LEGS_DECORATION_1_ID, decorationId);
                } else if (decorationIndex == 1) {
                    putASBSessionItemOrNull(values, S.COLUMN_LEGS_DECORATION_2_ID, decorationId);
                } else if (decorationIndex == 2) {
                    putASBSessionItemOrNull(values, S.COLUMN_LEGS_DECORATION_3_ID, decorationId);
                }
                break;
            case ASBSession.TALISMAN:
                if (decorationIndex == 0) {
                    putASBSessionItemOrNull(values, S.COLUMN_TALISMAN_DECORATION_1_ID, decorationId);
                } else if (decorationIndex == 1) {
                    putASBSessionItemOrNull(values, S.COLUMN_TALISMAN_DECORATION_2_ID, decorationId);
                } else if (decorationIndex == 2) {
                    putASBSessionItemOrNull(values, S.COLUMN_TALISMAN_DECORATION_3_ID, decorationId);
                }
                break;
        }

        return updateRecord(S.TABLE_ASB_SETS, filter, values);
    }

    public long queryCreateASBSessionTalisman(long asbSetId, int type, int slots, long skill1Id, int skill1Points, long skill2Id, int skill2Points) {
        String filter = S.COLUMN_ASB_SET_ID + " = " + asbSetId;

        ContentValues values = new ContentValues();

        values.put(S.COLUMN_TALISMAN_EXISTS, 1);

        values.put(S.COLUMN_TALISMAN_TYPE, type);
        values.put(S.COLUMN_TALISMAN_SLOTS, slots);
        values.put(S.COLUMN_TALISMAN_SKILL_1_ID, skill1Id);
        values.put(S.COLUMN_TALISMAN_SKILL_1_POINTS, skill1Points);

        if (skill2Id != -1) {
            values.put(S.COLUMN_TALISMAN_SKILL_2_ID, skill2Id);
            values.put(S.COLUMN_TALISMAN_SKILL_2_POINTS, skill2Points);
        } else {
            values.putNull(S.COLUMN_TALISMAN_SKILL_2_ID);
            values.putNull(S.COLUMN_TALISMAN_SKILL_2_POINTS);
        }

        return updateRecord(S.TABLE_ASB_SETS, filter, values);
    }

    public long queryRemoveASBSessionTalisman(long asbSetId) {
        String filter = S.COLUMN_ASB_SET_ID + " = " + asbSetId;

        ContentValues values = new ContentValues();

        values.put(S.COLUMN_TALISMAN_EXISTS, 0);

        return updateRecord(S.TABLE_ASB_SETS, filter, values);
    }

    /**
     * Builds an SQL query that gives us all information about the {@code ASBSet} in question.
     */
    private SQLiteQueryBuilder builderASBSet() {
        HashMap<String, String> projectionMap = new HashMap<>();

        String set = "ar";

        projectionMap.put("_id", set + "." + S.COLUMN_ASB_SET_ID + " AS " + "_id");

        projectionMap.put(S.COLUMN_ASB_SET_NAME, set + "." + S.COLUMN_ASB_SET_NAME);
        projectionMap.put(S.COLUMN_ASB_SET_RANK, set + "." + S.COLUMN_ASB_SET_RANK);
        projectionMap.put(S.COLUMN_ASB_SET_HUNTER_TYPE, set + "." + S.COLUMN_ASB_SET_HUNTER_TYPE);

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(S.TABLE_ASB_SETS + " AS " + set);
        qb.setProjectionMap(projectionMap);

        return qb;
    }

    /**
     * Builds an SQL query that gives us all information about the {@code ASBSession} in question.
     */
    private SQLiteQueryBuilder builderASBSession() {
        HashMap<String, String> projectionMap = new HashMap<>();

        String set = "ar";

        projectionMap.put("_id", set + "." + S.COLUMN_ASB_SET_ID + " AS " + "_id");

        projectionMap.put(S.COLUMN_HEAD_ARMOR_ID, set + "." + S.COLUMN_HEAD_ARMOR_ID);
        projectionMap.put(S.COLUMN_HEAD_DECORATION_1_ID, set + "." + S.COLUMN_HEAD_DECORATION_1_ID);
        projectionMap.put(S.COLUMN_HEAD_DECORATION_2_ID, set + "." + S.COLUMN_HEAD_DECORATION_2_ID);
        projectionMap.put(S.COLUMN_HEAD_DECORATION_3_ID, set + "." + S.COLUMN_HEAD_DECORATION_3_ID);

        projectionMap.put(S.COLUMN_BODY_ARMOR_ID, set + "." + S.COLUMN_BODY_ARMOR_ID);
        projectionMap.put(S.COLUMN_BODY_DECORATION_1_ID, set + "." + S.COLUMN_BODY_DECORATION_1_ID);
        projectionMap.put(S.COLUMN_BODY_DECORATION_2_ID, set + "." + S.COLUMN_BODY_DECORATION_2_ID);
        projectionMap.put(S.COLUMN_BODY_DECORATION_3_ID, set + "." + S.COLUMN_BODY_DECORATION_3_ID);

        projectionMap.put(S.COLUMN_ARMS_ARMOR_ID, set + "." + S.COLUMN_ARMS_ARMOR_ID);
        projectionMap.put(S.COLUMN_ARMS_DECORATION_1_ID, set + "." + S.COLUMN_ARMS_DECORATION_1_ID);
        projectionMap.put(S.COLUMN_ARMS_DECORATION_2_ID, set + "." + S.COLUMN_ARMS_DECORATION_2_ID);
        projectionMap.put(S.COLUMN_ARMS_DECORATION_3_ID, set + "." + S.COLUMN_ARMS_DECORATION_3_ID);

        projectionMap.put(S.COLUMN_WAIST_ARMOR_ID, set + "." + S.COLUMN_WAIST_ARMOR_ID);
        projectionMap.put(S.COLUMN_WAIST_DECORATION_1_ID, set + "." + S.COLUMN_WAIST_DECORATION_1_ID);
        projectionMap.put(S.COLUMN_WAIST_DECORATION_2_ID, set + "." + S.COLUMN_WAIST_DECORATION_2_ID);
        projectionMap.put(S.COLUMN_WAIST_DECORATION_3_ID, set + "." + S.COLUMN_WAIST_DECORATION_3_ID);

        projectionMap.put(S.COLUMN_LEGS_ARMOR_ID, set + "." + S.COLUMN_LEGS_ARMOR_ID);
        projectionMap.put(S.COLUMN_LEGS_DECORATION_1_ID, set + "." + S.COLUMN_LEGS_DECORATION_1_ID);
        projectionMap.put(S.COLUMN_LEGS_DECORATION_2_ID, set + "." + S.COLUMN_LEGS_DECORATION_2_ID);
        projectionMap.put(S.COLUMN_LEGS_DECORATION_3_ID, set + "." + S.COLUMN_LEGS_DECORATION_3_ID);

        projectionMap.put(S.COLUMN_TALISMAN_EXISTS, set + "." + S.COLUMN_TALISMAN_EXISTS);
        projectionMap.put(S.COLUMN_TALISMAN_TYPE, set + "." + S.COLUMN_TALISMAN_TYPE);
        projectionMap.put(S.COLUMN_TALISMAN_SLOTS, set + "." + S.COLUMN_TALISMAN_SLOTS);
        projectionMap.put(S.COLUMN_TALISMAN_SKILL_1_ID, set + "." + S.COLUMN_TALISMAN_SKILL_1_ID);
        projectionMap.put(S.COLUMN_TALISMAN_SKILL_1_POINTS, set + "." + S.COLUMN_TALISMAN_SKILL_1_POINTS);
        projectionMap.put(S.COLUMN_TALISMAN_SKILL_2_ID, set + "." + S.COLUMN_TALISMAN_SKILL_2_ID);
        projectionMap.put(S.COLUMN_TALISMAN_SKILL_2_POINTS, set + "." + S.COLUMN_TALISMAN_SKILL_2_POINTS);
        projectionMap.put(S.COLUMN_TALISMAN_DECORATION_1_ID, set + "." + S.COLUMN_TALISMAN_DECORATION_1_ID);
        projectionMap.put(S.COLUMN_TALISMAN_DECORATION_2_ID, set + "." + S.COLUMN_TALISMAN_DECORATION_2_ID);
        projectionMap.put(S.COLUMN_TALISMAN_DECORATION_3_ID, set + "." + S.COLUMN_TALISMAN_DECORATION_3_ID);

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(S.TABLE_ASB_SETS + " AS " + set);
        qb.setProjectionMap(projectionMap);

        return qb;
    }

    /**
     * A helper method that determines whether to put {@code null} or the actual armor id into the table.
     */
    private void putASBSessionItemOrNull(ContentValues cv, String column, long pieceId) {
        if (pieceId != -1) {
            cv.put(column, pieceId);
        } else {
            cv.putNull(column);
        }
    }
}
