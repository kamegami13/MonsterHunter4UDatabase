package com.daviancorp.android.data.database;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.util.Xml;

import com.daviancorp.android.data.classes.Wishlist;
import com.daviancorp.android.data.classes.WishlistComponent;
import com.daviancorp.android.data.classes.WishlistData;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	
	//The Android's default system path of your application database.
	// /data/data/com.daviancorp.android.monsterhunter4udatabase/databases/
    /*private static String DB_PATH = "/data/data/com.daviancorp.android.mh4udatabase/databases/";
	private static String DB_NAME = "mh4u.db";
	private static String DB_TEMP_NAME = "mh4u_temp.db";
	private static String ASSETS_DB_FOLDER = "db";
	private static final int VERSION = 16; // EDIT*/

    private static final String DATABASE_NAME = "mh4u.db";
    private static final int DATABASE_VERSION = 8;

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

    @Override
    protected void preCopyDatabase(SQLiteDatabase db) {
        //Log.w(TAG, "Pre forcing database upgrade!");
        String filename = "wishlist.xml";
        FileOutputStream fos;

        try {
            fos = myContext.openFileOutput(filename, Context.MODE_PRIVATE);
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(fos, "UTF-8");
            serializer.startDocument(null, Boolean.valueOf(true));
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

            WishlistComponentCursor wcc = queryWishlistsComponent(db);
            WishlistDataCursor wdc = queryWishlistsData(db);
            WishlistCursor wc = queryWishlists(db);

            wc.moveToFirst();
            wdc.moveToFirst();
            wcc.moveToFirst();

            serializer.startTag(null, "wishlist_tables");

            serializer.startTag(null, "wishlists");
            while (!wc.isAfterLast()) {
                Wishlist wishlist = wc.getWishlist();
                serializer.startTag(null, "wishlist");
                serializer.startTag(null, "wishlist_id");
                serializer.text(Long.toString(wishlist.getId()));
                serializer.endTag(null, "wishlist_id");
                serializer.startTag(null, "name");
                serializer.text(wishlist.getName());
                serializer.endTag(null, "name");
                serializer.endTag(null, "wishlist");
                wc.moveToNext();
            }
            serializer.endTag(null, "wishlists");
            wc.close();

            serializer.startTag(null, "wishlist_data");
            while (!wdc.isAfterLast()) {
                WishlistData wishlistData = wdc.getWishlistData();
                serializer.startTag(null, "data");
                serializer.startTag(null, "wishlist_id");
                serializer.text(Long.toString(wishlistData.getWishlistId()));
                serializer.endTag(null, "wishlist_id");
                serializer.startTag(null, "item_id");
                serializer.text(Long.toString(wishlistData.getItem().getId()));
                serializer.endTag(null, "item_id");
                serializer.startTag(null, "quantity");
                serializer.text(Integer.toString(wishlistData.getQuantity()));
                serializer.endTag(null, "quantity");
                serializer.startTag(null, "satisfied");
                serializer.text(Integer.toString(wishlistData.getSatisfied()));
                serializer.endTag(null, "satisfied");
                serializer.startTag(null, "path");
                serializer.text(wishlistData.getPath());
                serializer.endTag(null, "path");
                serializer.endTag(null, "data");

                /*queryAddWishlistDataAll(newDb, wishlistData.getWishlistId(), wishlistData.getItem().getId(),
                        wishlistData.getQuantity(), wishlistData.getSatisfied(), wishlistData.getPath());*/
                wdc.moveToNext();
            }
            serializer.endTag(null, "wishlist_data");
            wdc.close();

            serializer.startTag(null, "wishlist_components");
            while (!wcc.isAfterLast()) {
                WishlistComponent wishlistComponent = wcc.getWishlistComponent();
                serializer.startTag(null, "component");
                serializer.startTag(null, "wishlist_id");
                serializer.text(Long.toString(wishlistComponent.getWishlistId()));
                serializer.endTag(null, "wishlist_id");
                serializer.startTag(null, "item_id");
                serializer.text(Long.toString(wishlistComponent.getItem().getId()));
                serializer.endTag(null, "item_id");
                serializer.startTag(null, "quantity");
                serializer.text(Integer.toString(wishlistComponent.getQuantity()));
                serializer.endTag(null, "quantity");
                serializer.startTag(null, "notes");
                serializer.text(Integer.toString(wishlistComponent.getNotes()));
                serializer.endTag(null, "notes");
                serializer.endTag(null, "component");

                /*queryAddWishlistComponentAll(newDb, wishlistComponent.getWishlistId(),
                        wishlistComponent.getItem().getId(), wishlistComponent.getQuantity(), wishlistComponent.getNotes());*/
                wcc.moveToNext();
            }
            serializer.endTag(null, "wishlist_components");
            wcc.close();

            serializer.endDocument();
            serializer.flush();
            fos.close();
        }
        catch (IOException e) {
            //e.printStackTrace();
        }
        finally {

        }
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

            int event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String tagName = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(tagName.equals("wishlist_id")) {
                            wishlist_id = Long.parseLong(text);
                        }
                        else if(tagName.equals("name")) {
                            name = text;
                        }
                        else if(tagName.equals("item_id")) {
                            item_id = Long.parseLong(text);
                        }
                        else if(tagName.equals("quantity")) {
                            quantity = Integer.parseInt(text);
                        }
                        else if(tagName.equals("satisfied")) {
                            satisfied = Integer.parseInt(text);
                        }
                        else if(tagName.equals("path")) {
                            path = text;
                        }
                        else if(tagName.equals("notes")) {
                            notes = Integer.parseInt(text);
                        }
                        else if(tagName.equals("wishlist")) {
                            if(clear_wishlist) {
                                db.delete(S.TABLE_WISHLIST, null, null);
                                //only clear the table once if there is data to load
                                clear_wishlist = false;
                            }
                            queryAddWishlistAll(db, wishlist_id, name);
                        }
                        else if(tagName.equals("data")) {
                            queryAddWishlistDataAll(db, wishlist_id, item_id,
                                    quantity, satisfied, path);
                        }
                        else if(tagName.equals("component")) {
                            queryAddWishlistComponentAll(db, wishlist_id,
                                    item_id, quantity, notes);
                        }
                        else{

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
	 * Creates a empty database on the system and overwrite it with your own
	 * database.
	 **/
	/*public void createDatabase() throws IOException {
	    boolean dbExist = checkDatabase();
		if (!dbExist) {
			super.getReadableDatabase();
			try {
				copyDatabase();
			}
			catch (IOException e) {
				throw new Error("Error copying database");
			}
			
			try {
				getWritableDatabase().execSQL("INSERT INTO 'wishlist' (`_id`, `name`) VALUES (1, 'My Wishlist');");
			}
			finally {
				close();
			}
		}
	}*/

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	/*private boolean checkDatabase() {
        File file = new File(DB_PATH + DB_NAME);
        return file.exists() && !file.isDirectory();
    }*/

	/**
	 * Copy distributed db in assets folder to data folder
	 * @throws IOException
	 */
	/*private void copyDatabase() throws IOException {
		String[] dbFiles = myContext.getAssets().list(ASSETS_DB_FOLDER);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);		
		
		for(int i =0; i < dbFiles.length; i++) {
			InputStream myInput = myContext.getAssets().open(ASSETS_DB_FOLDER+"/"+dbFiles[i]);
			byte[] buffer = new byte[1024];
			int length;
			
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			
			myInput.close();
		}
		myOutput.flush();
		myOutput.close();
	}*/
	
	/**
	 * Copy distributed db in assets folder to temp file for upgrade
	 * @throws IOException
	 */
	/*private void copyTempDatabase() throws IOException {
		String[] dbFiles = myContext.getAssets().list(ASSETS_DB_FOLDER);
		String outFileName = DB_PATH + DB_TEMP_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);		
		
		for(int i =0; i < dbFiles.length; i++) {
			InputStream myInput = myContext.getAssets().open(ASSETS_DB_FOLDER+"/"+dbFiles[i]);
			byte[] buffer = new byte[1024];
			int length;
			
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			
			myInput.close();
		}
		myOutput.flush();
		myOutput.close();
	}*/

	/**
	 * Set database instance
	 * @throws SQLException
	 */
	public void openDatabase() throws SQLException {
		myDataBase = getWritableDatabase();
	}
	
	/**
	 * Returns an opened instance of the temp database
	 * @return The temp database object
	 * @throws SQLException
	 */
	/*public SQLiteDatabase openTempDatabase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DB_TEMP_NAME;
		return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.NO_LOCALIZED_COLLATORS);
	}*/

	/**
	 * Close database
	 */
	@Override
	public synchronized void close() {
		if (myDataBase != null) myDataBase.close();
		super.close();
	}

	//@Override
	//public void onCreate(SQLiteDatabase db) { }

	/**
	 * Copy the new database and transfer the wishlist data
	 */
	/*@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Transfer over the wishlist data
		if (newVersion > oldVersion) {

            File file = new File(DB_PATH + DB_TEMP_NAME);
            if(file.exists())
                file.delete();
			
			//query wishlist data with provided db instance
			//Must NOT call getReadableDatabase() or loop will occur
			WishlistCursor wc = queryWishlists(db);
			WishlistDataCursor wdc = queryWishlistsData(db);
			WishlistComponentCursor wcc = queryWishlistsComponent(db);
			
			//Pull the new database to a temp file
			try {
				copyTempDatabase();
			}
			catch (IOException e) {
				throw new Error("Error copying database");
			}
			
			//get connection to temp database 
			SQLiteDatabase newDb = null;
			try {
				newDb = openTempDatabase();
			}
			catch (SQLException e) {
				
			}
			
			//Copy the wishlast tables from current db to new db
			if(newDb != null)
			{
				wc.moveToFirst();
				wdc.moveToFirst();
				wcc.moveToFirst();
				
				while (!wc.isAfterLast()) {
					Wishlist wishlist = wc.getWishlist();
					queryAddWishlistAll(newDb, wishlist.getId(), wishlist.getName());
					wc.moveToNext();
				}
				wc.close();
				
				while (!wdc.isAfterLast()) {
					WishlistData wishlistData = wdc.getWishlistData();
					queryAddWishlistDataAll(newDb, wishlistData.getWishlistId(), wishlistData.getItem().getId(), 
							wishlistData.getQuantity(), wishlistData.getSatisfied(), wishlistData.getPath());
					wdc.moveToNext();
				}
				wdc.close();
				
				while (!wcc.isAfterLast()) {
					WishlistComponent wishlistComponent = wcc.getWishlistComponent();
					queryAddWishlistComponentAll(newDb, wishlistComponent.getWishlistId(), 
							wishlistComponent.getItem().getId(), wishlistComponent.getQuantity(), wishlistComponent.getNotes());
					wcc.moveToNext();
				}
				wcc.close();
				
				newDb.close();
			}

            db.close();

            File file2 = new File(DB_PATH + DB_NAME);
            if(file2.exists())
                file2.delete();
			
			//Overwrite current db with temp db
			//Overwriting with file streams as delete/rename doesn't seem to work correctly
			try {
				InputStream myInput = new FileInputStream(DB_PATH + DB_TEMP_NAME);
				OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);		

				byte[] buffer = new byte[1024];
				int length;

				while ((length = myInput.read(buffer)) > 0) {
					myOutput.write(buffer, 0, length);
				}

				myOutput.flush();
				myOutput.close();
				myInput.close();
				
			} catch (IOException e) {
				throw new Error("Error overwritting database");
			}

            File file3 = new File(DB_PATH + DB_TEMP_NAME);
            if(file3.exists())
                file3.delete();
		}
	}*/
	
	//@Override
	//public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
	
	//removed getWritableDatabase() and getReadableDatabase() overrides as they broke
	//functionality such as onUpgrade()
    
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
		return getReadableDatabase().query(qh.Distinct, qh.Table, qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
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
		return qb.query(getReadableDatabase(), qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
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
	
/********************************* ARENA QUEST QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
	
/********************************* ARENA REWARD QUERIES ******************************************/
	
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
	
/********************************* ARMOR QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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

/********************************* COMBINING QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
        qh.SelectionArgs = new String[]{ String.valueOf(id),
                                         String.valueOf(id),
                                         String.valueOf(id)};
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new CombiningCursor(wrapJoinHelper(builderCursor(), qh));
    }
	
	private SQLiteQueryBuilder builderCursor()  {
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
		String[] items = new String[] {"crt", "mat1", "mat2"};

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
	
/********************************* COMPONENT QUERIES ******************************************/
	
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

/********************************* DECORATION QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
		        " ON " + "its2." + S.COLUMN_ITEM_TO_SKILL_TREE_SKILL_TREE_ID + " = " + "s2." + S.COLUMN_SKILL_TREES_ID );

		QB.setProjectionMap(projectionMap);
		return QB;
	}
	
/********************************* GATHERING QUERIES ******************************************/
	
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
	
/********************************* HUNTING FLEET QUERIES ******************************************/
	
	/*
	 * Get all hunting fleets
	 */
	public HuntingFleetCursor queryHuntingFleets() {

		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Table = S.TABLE_HUNTING_FLEET;
		qh.Selection = null;
		qh.SelectionArgs = null;
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;

		return new HuntingFleetCursor(wrapJoinHelper(builderHuntingFleet(), qh));
	}
	
	/*
	 * Get a specific hunting fleet
	 */
	public HuntingFleetCursor queryHuntingFleet(long id) {

		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Table = S.TABLE_HUNTING_FLEET;
		qh.Selection = "h." + S.COLUMN_HUNTING_FLEET_ID + " = ?";
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = "1";
		
		return new HuntingFleetCursor(wrapJoinHelper(builderHuntingFleet(), qh));
	}	
	
	/*
	 * Get a specific hunting fleet based on type
	 */
	public HuntingFleetCursor queryHuntingFleetType(String type) {

		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Table = S.TABLE_HUNTING_FLEET;
		qh.Selection = "h." + S.COLUMN_HUNTING_FLEET_TYPE + " = ?";
		qh.SelectionArgs = new String[]{ type };
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new HuntingFleetCursor(wrapJoinHelper(builderHuntingFleet(), qh));
	}
	
	/*
	 * Get a specific hunting fleet based on location
	 */
	public HuntingFleetCursor queryHuntingFleetLocation(String location) {

		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Selection = "h." + S.COLUMN_HUNTING_FLEET_LOCATION + " = ?";
		qh.SelectionArgs = new String[]{ location };
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new HuntingFleetCursor(wrapJoinHelper(builderHuntingFleet(), qh));
	}
	
	/*
	 * Helper method to query for hunting fleets
	 */
	private SQLiteQueryBuilder builderHuntingFleet() {
//		SELECT h._id AS _id, h.type AS htype, h.level, h.location, h.amount, h.percentage, h.rank,
//		h.item_id, i.name, i.jpn_name, i.type, i.rarity, i.carry_capacity, i.buy, i.sell,
//		i.description, i.icon_name, i.armor_dupe_name_fix
//		FROM hunting_fleet AS h LEFT OUTER JOIN items AS i ON h.item_id = i._id;

		String h = "h";
		String i = "i";
		
		HashMap<String, String> projectionMap = new HashMap<String, String>();
		
		projectionMap.put("_id", h + "." + S.COLUMN_HUNTING_FLEET_ID + " AS " + "_id");
		projectionMap.put(h + S.COLUMN_HUNTING_FLEET_TYPE, h + "." + S.COLUMN_HUNTING_FLEET_TYPE + " AS " + h + S.COLUMN_HUNTING_FLEET_TYPE);
		projectionMap.put(S.COLUMN_HUNTING_FLEET_LEVEL, h + "." + S.COLUMN_HUNTING_FLEET_LEVEL);
		projectionMap.put(S.COLUMN_HUNTING_FLEET_LOCATION, h + "." + S.COLUMN_HUNTING_FLEET_LOCATION);
		projectionMap.put(S.COLUMN_HUNTING_FLEET_AMOUNT, h + "." + S.COLUMN_HUNTING_FLEET_AMOUNT);
		projectionMap.put(S.COLUMN_HUNTING_FLEET_PERCENTAGE, h + "." + S.COLUMN_HUNTING_FLEET_PERCENTAGE);
		projectionMap.put(S.COLUMN_HUNTING_FLEET_RANK, h + "." + S.COLUMN_HUNTING_FLEET_RANK);
		projectionMap.put(S.COLUMN_HUNTING_FLEET_ITEM_ID, h + "." + S.COLUMN_HUNTING_FLEET_ITEM_ID);
		
		projectionMap.put(S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME);
		projectionMap.put(S.COLUMN_ITEMS_JPN_NAME, i + "." + S.COLUMN_ITEMS_JPN_NAME);
		projectionMap.put(i + S.COLUMN_ITEMS_TYPE, i + "." + S.COLUMN_ITEMS_TYPE + " AS " + i + S.COLUMN_ITEMS_TYPE);
		projectionMap.put(S.COLUMN_ITEMS_RARITY, i + "." + S.COLUMN_ITEMS_RARITY);
		projectionMap.put(S.COLUMN_ITEMS_CARRY_CAPACITY, i + "." + S.COLUMN_ITEMS_CARRY_CAPACITY);
		projectionMap.put(S.COLUMN_ITEMS_BUY, i + "." + S.COLUMN_ITEMS_BUY);
		projectionMap.put(S.COLUMN_ITEMS_SELL, i + "." + S.COLUMN_ITEMS_SELL);
		projectionMap.put(S.COLUMN_ITEMS_DESCRIPTION, i + "." + S.COLUMN_ITEMS_DESCRIPTION);
		projectionMap.put(S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME);
		projectionMap.put(S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX, i + "." + S.COLUMN_ITEMS_ARMOR_DUPE_NAME_FIX);
		
		//Create new querybuilder
		SQLiteQueryBuilder QB = new SQLiteQueryBuilder();
		 
		QB.setTables(S.TABLE_HUNTING_FLEET + " AS h" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "h." +
				S.COLUMN_HUNTING_FLEET_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID);

		QB.setProjectionMap(projectionMap);
		return QB;
	}
	
/********************************* HUNTING REWARD QUERIES ******************************************/
	
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
		for(int i = 0; i < ids.length; i++){
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
		for(int i = 0; i < ids.length; i++){
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
	
/********************************* ITEM QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
		qh.SelectionArgs = new String[]{ '%' + search  + '%'};
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new ItemCursor(wrapHelper(qh));
	}	
	
	
/********************************* ITEM TO SKILL TREE QUERIES ******************************************/
	
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
		}
		else {
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

/********************************* LOCATION QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = "1";
		
		return new LocationCursor(wrapHelper(qh));
	}

    /******************************** HORN MELODIES QUERIES ***********************************************/

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

    /********************************* MOGA WOODS REWARD QUERIES ******************************************/
	
	/*
	 * Get all moga woods reward monsters based on item
	 */
	public MogaWoodsRewardCursor queryMogaWoodsRewardItem(long id) {
		
		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Table = S.TABLE_MOGA_WOODS_REWARDS;
		qh.Selection = "mwr." + S.COLUMN_MOGA_WOODS_REWARDS_ITEM_ID + " = ? ";
		qh.SelectionArgs = new String[]{"" + id};
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new MogaWoodsRewardCursor(wrapJoinHelper(builderMogaWoodsReward(), qh));
	}
	
	/*
	 * Get all moga woods reward items based on monster
	 */
	public MogaWoodsRewardCursor queryMogaWoodsRewardMonster(long id) {
		
		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Selection = "mwr." + S.COLUMN_MOGA_WOODS_REWARDS_MONSTER_ID + " = ? ";
		qh.SelectionArgs = new String[]{"" + id};
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new MogaWoodsRewardCursor(wrapJoinHelper(builderMogaWoodsReward(), qh));
	}
	
	/*
	 * Get all moga woods reward items based on monster and time
	 */
	public MogaWoodsRewardCursor queryMogaWoodsRewardMonsterTime(long id, String time) {
		
		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Table = S.TABLE_MOGA_WOODS_REWARDS;
		qh.Selection = "mwr." + S.COLUMN_MOGA_WOODS_REWARDS_MONSTER_ID + " = ? " + "AND " + 
				"mwr." + S.COLUMN_MOGA_WOODS_REWARDS_TIME + " = ? ";
		qh.SelectionArgs = new String[]{"" + id, time};
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new MogaWoodsRewardCursor(wrapJoinHelper(builderMogaWoodsReward(), qh));
	}
	
	/*
	 * Helper method to query for MogaWoods
	 */
	private SQLiteQueryBuilder builderMogaWoodsReward() {
//		SELECT mwr._id AS _id, mwr.monster_id, mwr.item_id,
//		mwr.time, mwr.commodity_stars, mwr.kill_percentage,
//		mwr.capture_percentage, 
//		i.name AS iname, m.name AS mname 
//		FROM moga_woods_rewards AS mwr
//		LEFT OUTER JOIN monsters AS m ON mwr.monster_id = m._id 
//		LEFT OUTER JOIN items AS i ON mwr.item_id = i._id;

		String mwr = "mwr";
		String i = "i";
		String m = "m";
		
		HashMap<String, String> projectionMap = new HashMap<String, String>();
		
		projectionMap.put("_id", mwr + "." + S.COLUMN_MOGA_WOODS_REWARDS_ID + " AS " + "_id");
		projectionMap.put(S.COLUMN_MOGA_WOODS_REWARDS_ITEM_ID, mwr + "." + S.COLUMN_MOGA_WOODS_REWARDS_ITEM_ID);
		projectionMap.put(S.COLUMN_MOGA_WOODS_REWARDS_MONSTER_ID, mwr + "." + S.COLUMN_MOGA_WOODS_REWARDS_MONSTER_ID);
		projectionMap.put(S.COLUMN_MOGA_WOODS_REWARDS_TIME, mwr + "." + S.COLUMN_MOGA_WOODS_REWARDS_TIME);
		projectionMap.put(S.COLUMN_MOGA_WOODS_REWARDS_COMMODITY_STARS, mwr + "." + S.COLUMN_MOGA_WOODS_REWARDS_COMMODITY_STARS);
		projectionMap.put(S.COLUMN_MOGA_WOODS_REWARDS_KILL_PERCENTAGE, mwr + "." + S.COLUMN_MOGA_WOODS_REWARDS_KILL_PERCENTAGE);
		projectionMap.put(S.COLUMN_MOGA_WOODS_REWARDS_CAPTURE_PERCENTAGE, mwr + "." + S.COLUMN_MOGA_WOODS_REWARDS_CAPTURE_PERCENTAGE);
		
		projectionMap.put(i + S.COLUMN_ITEMS_NAME, i + "." + S.COLUMN_ITEMS_NAME + " AS " + i + S.COLUMN_ITEMS_NAME);
		projectionMap.put(i + S.COLUMN_ITEMS_ICON_NAME, i + "." + S.COLUMN_ITEMS_ICON_NAME + " AS " + i + S.COLUMN_ITEMS_ICON_NAME);
		projectionMap.put(m + S.COLUMN_MONSTERS_NAME, m + "." + S.COLUMN_MONSTERS_NAME + " AS " + m + S.COLUMN_MONSTERS_NAME);
		projectionMap.put(m + S.COLUMN_MONSTERS_FILE_LOCATION, m + "." + S.COLUMN_MONSTERS_FILE_LOCATION + " AS " + m + S.COLUMN_MONSTERS_FILE_LOCATION);

		//Create new querybuilder
		SQLiteQueryBuilder QB = new SQLiteQueryBuilder();
		
		QB.setTables(S.TABLE_MOGA_WOODS_REWARDS + " AS mwr" + " LEFT OUTER JOIN " + S.TABLE_ITEMS + " AS i" + " ON " + "mwr." +
				S.COLUMN_MOGA_WOODS_REWARDS_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID + " LEFT OUTER JOIN " + S.TABLE_MONSTERS +
				" AS m " + " ON " + "mwr." + S.COLUMN_MOGA_WOODS_REWARDS_MONSTER_ID + " = " + "m." + S.COLUMN_MONSTERS_ID);

		QB.setProjectionMap(projectionMap);
		return QB;
	}
	
/********************************* MONSTER QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[] {"Minion"};
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
		qh.SelectionArgs = new String[] {"Boss"};
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
		qh.SelectionArgs = new String[] {name};
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new MonsterCursor(wrapHelper(qh));
	}

/********************************* MONSTER AILMENT QUERIES ******************************************/
    /* Get all ailments a from a particular monster */
    public MonsterAilmentCursor queryAilmentsFromMonster(long id){
        // SELECT * FROM monster_ailment WHERE monster_id = id

        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_AILMENT;
        qh.Columns = null;
        qh.Selection = S.COLUMN_AILMENT_MONSTER_ID + " = ?";
        qh.SelectionArgs = new String[] {String.valueOf(id)};
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
    public MonsterHabitatCursor queryHabitatMonster(long id)
    {
        // Select * FROM monster_habitat WHERE monster_id = id
        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_HABITAT;
        qh.Columns = null;
        qh.Selection = S.COLUMN_HABITAT_MONSTER_ID + " = ?";
        qh.SelectionArgs = new String[]{ String.valueOf(id) };
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterHabitatCursor(wrapJoinHelper(builderHabitat(qh.Distinct),qh));
    }

    /**
     * Get a cursor with a query to grab all monsters by a location
     * @param id id of the location to query
     * @return A habitat cursor
     */
    public MonsterHabitatCursor queryHabitatLocation(long id)
    {
        // Select * FROM monster_habitat WHERE location_id = id
        QueryHelper qh = new QueryHelper();
        qh.Distinct = true;
        qh.Table = S.TABLE_HABITAT;
        qh.Columns = null;
        qh.Selection = S.COLUMN_HABITAT_LOCATION_ID + " = ?";
        qh.SelectionArgs = new String[]{ String.valueOf(id) };
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = "m" + S.COLUMN_MONSTERS_SORT_NAME + " ASC";
        qh.Limit = null;

        return new MonsterHabitatCursor(wrapJoinHelper(builderHabitat(qh.Distinct),qh));
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

        projectionMap.put(l + S.COLUMN_LOCATIONS_ID, l + "." + S.COLUMN_LOCATIONS_ID + " AS " + l + S.COLUMN_LOCATIONS_ID );
        projectionMap.put(l + S.COLUMN_LOCATIONS_NAME, l + "." + S.COLUMN_LOCATIONS_NAME + " AS " + l + S.COLUMN_LOCATIONS_NAME );
        projectionMap.put(l + S.COLUMN_LOCATIONS_MAP, l + "." + S.COLUMN_LOCATIONS_MAP + " AS " + l + S.COLUMN_LOCATIONS_MAP );

        projectionMap.put(m + S.COLUMN_MONSTERS_ID, m+ "." + S.COLUMN_MONSTERS_ID + " AS " + m + S.COLUMN_MONSTERS_ID);
        projectionMap.put(m + S.COLUMN_MONSTERS_SORT_NAME, m + "." + S.COLUMN_MONSTERS_SORT_NAME + " AS " + m + S.COLUMN_MONSTERS_SORT_NAME);
        projectionMap.put(m + S.COLUMN_MONSTERS_NAME, m + "." + S.COLUMN_MONSTERS_NAME + " AS " + m + S.COLUMN_MONSTERS_NAME);
        projectionMap.put(m + S.COLUMN_MONSTERS_CLASS, m + "." + S.COLUMN_MONSTERS_CLASS + " AS " + m + S.COLUMN_MONSTERS_CLASS );
        projectionMap.put(m + S.COLUMN_MONSTERS_FILE_LOCATION, m + "." + S.COLUMN_MONSTERS_FILE_LOCATION + " AS " + m + S.COLUMN_MONSTERS_FILE_LOCATION );

        //Create new querybuilder
        SQLiteQueryBuilder QB = new SQLiteQueryBuilder();

        QB.setTables(S.TABLE_HABITAT + " AS h" + " LEFT OUTER JOIN " + S.TABLE_MONSTERS + " AS m" + " ON " + "h." +
                S.COLUMN_HABITAT_MONSTER_ID + " = " + "m." + S.COLUMN_MONSTERS_ID + " LEFT OUTER JOIN " + S.TABLE_LOCATIONS +
                " AS l " + " ON " + "h." + S.COLUMN_HABITAT_LOCATION_ID + " = " + "l." + S.COLUMN_LOCATIONS_ID);

        QB.setDistinct(Distinct);
        QB.setProjectionMap(projectionMap);
        return QB;
    }

    /********************************* MONSTER STATUS QUERIES ******************************************/

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
        qh.SelectionArgs = new String[]{ String.valueOf(id) };
        qh.GroupBy = null;
        qh.Having = null;
        qh.OrderBy = null;
        qh.Limit = null;

        return new MonsterStatusCursor(wrapHelper(qh));
    }

    /********************************* MONSTER DAMAGE QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new MonsterDamageCursor(wrapHelper(qh));
	}	
	
/********************************* MONSTER TO ARENA QUERIES ******************************************/
	
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
		
/********************************* MONSTER TO QUEST QUERIES ******************************************/
	
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
		
/********************************* QUEST QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
		qh.Selection = "q." + S.COLUMN_QUESTS_HUB + " = ?";
		qh.SelectionArgs = new String[]{ hub };
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
					"q." + S.COLUMN_QUESTS_STARS + " = ?";
		qh.SelectionArgs = new String[]{ hub, stars };
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
	
/********************************* QUEST REWARD QUERIES ******************************************/
	
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
	
/********************************* SKILL QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = null;
		
		return new SkillCursor(wrapHelper(qh));
	}	
	
/********************************* SKILL TREE QUERIES ******************************************/	

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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
		qh.GroupBy = null;
		qh.Having = null;
		qh.OrderBy = null;
		qh.Limit = "1";
		
		return new SkillTreeCursor(wrapHelper(qh));
	}	
	
/********************************* WEAPON QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
		for(int i = 0; i < ids.length; i++){
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
		projectionMap.put(S.COLUMN_WEAPONS_SHARPNESS_FILE, w + "." + S.COLUMN_WEAPONS_SHARPNESS_FILE);
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
	
/********************************* WEAPON TREE QUERIES ******************************************/
	
	/*
	 * Get the parent weapon
	 */
	public WeaponTreeCursor queryWeaponTreeParent(long id) {

		QueryHelper qh = new QueryHelper();
		qh.Columns = null;
		qh.Selection = "i1." + S.COLUMN_ITEMS_ID + " = ?";
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
	
/********************************* WISHLIST QUERIES ******************************************/
	
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
		qh.SelectionArgs = new String[]{ String.valueOf(id) };
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
		String strFilter = S.COLUMN_WISHLIST_ID + " = "  + id;
		
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
	
/********************************* WISHLIST DATA QUERIES ******************************************/
	
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
				getReadableDatabase(), qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
		
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
		String[] wdSelectionArgs = new String[]{ String.valueOf(id) };
		String wdGroupBy = null;
		String wdHaving = null;
		String wdOrderBy = "wd." + S.COLUMN_WISHLIST_DATA_ITEM_ID + " ASC";
		String wdLimit = null;
		
		// Multithread issues workaround
		SQLiteQueryBuilder qb = builderWishlistData();
		Cursor cursor = qb.query(
				getReadableDatabase(), wdColumns, wdSelection, wdSelectionArgs, wdGroupBy, wdHaving, wdOrderBy, wdLimit);
		
		return new WishlistDataCursor(cursor);
	}


	/*
	 * Get all wishlist data for a specific wishlist data id
	 */
	public WishlistDataCursor queryWishlistDataId(long id) {
		
		String[] wdColumns = null;
		String wdSelection = "wd." + S.COLUMN_WISHLIST_DATA_ID + " = ?";
		String[] wdSelectionArgs = new String[]{ String.valueOf(id) };
		String wdGroupBy = null;
		String wdHaving = null;
		String wdOrderBy = null;
		String wdLimit = null;
		
		// Multithread issues workaround
		SQLiteQueryBuilder qb = builderWishlistData();
		Cursor cursor = qb.query(
				getReadableDatabase(), wdColumns, wdSelection, wdSelectionArgs, wdGroupBy, wdHaving, wdOrderBy, wdLimit);
		
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
		String[] wdSelectionArgs = new String[]{ String.valueOf(wd_id), String.valueOf(item_id), path };
		String wdGroupBy = null;
		String wdHaving = null;
		String wdOrderBy = null;
		String wdLimit = null;

		// Multithread issues workaround
		SQLiteQueryBuilder qb = builderWishlistData();
		Cursor cursor = qb.query(
				getReadableDatabase(), wdColumns, wdSelection, wdSelectionArgs, wdGroupBy, wdHaving, wdOrderBy, wdLimit);
		
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
		String strFilter = S.COLUMN_WISHLIST_DATA_ID + " = "  + id;
		
		ContentValues values = new ContentValues();
		values.put(S.COLUMN_WISHLIST_DATA_QUANTITY, quantity);
		
		return updateRecord(S.TABLE_WISHLIST_DATA, strFilter, values);
	}
	
	/*
	 * Update a wishlist data to a specific wishlist
	 */
	public int queryUpdateWishlistDataSatisfied(long id, int satisfied) {
		String strFilter = S.COLUMN_WISHLIST_DATA_ID + " = "  + id;
		
		ContentValues values = new ContentValues();
		values.put(S.COLUMN_WISHLIST_DATA_SATISFIED, satisfied);
		
		return updateRecord(S.TABLE_WISHLIST_DATA, strFilter, values);
	}
	
	public boolean queryDeleteWishlistData(long id) {
		String where = S.COLUMN_WISHLIST_DATA_ID + " = ?";
		String[] args = new String[]{ "" + id };
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
		
		QB.setTables(S.TABLE_WISHLIST_DATA + " AS wd" + " LEFT OUTER JOIN " + S.TABLE_WISHLIST + " AS w" + " ON " +
				"wd." + S.COLUMN_WISHLIST_DATA_WISHLIST_ID + " = " + "w." + S.COLUMN_WISHLIST_ID + " LEFT OUTER JOIN " +
				S.TABLE_ITEMS + " AS i" + " ON " + "wd." + S.COLUMN_WISHLIST_DATA_ITEM_ID + " = " + "i." + S.COLUMN_ITEMS_ID);

		QB.setProjectionMap(projectionMap);
		return QB;
	}
	
/********************************* WISHLIST COMPONENT QUERIES ******************************************/
	
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
				getReadableDatabase(), qh.Columns, qh.Selection, qh.SelectionArgs, qh.GroupBy, qh.Having, qh.OrderBy, qh.Limit);
		
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
		String[] wcSelectionArgs = new String[]{ String.valueOf(id) };
		String wcGroupBy = null;
		String wcHaving = null;
		String wcOrderBy = "wc." + S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID + " ASC";
		String wcLimit = null;
		
		// Multithread issues workaround
		SQLiteQueryBuilder qb = builderWishlistComponent();
		Cursor cursor = qb.query(
				getReadableDatabase(), wcColumns, wcSelection, wcSelectionArgs, wcGroupBy, wcHaving, wcOrderBy, wcLimit);
		
		return new WishlistComponentCursor(cursor);
	}	

	/*
	 * Get all data for a specific wishlist and item
	 */
	public WishlistComponentCursor queryWishlistComponent(long wc_id, long item_id) {

		String[] wcColumns = null;
		String wcSelection = "wc." + S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID + " = ?" + " AND " +
				"wc." + S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID + " = ?";
		String[] wcSelectionArgs = new String[]{ String.valueOf(wc_id), String.valueOf(item_id) };
		String wcGroupBy = null;
		String wcHaving = null;
		String wcOrderBy = null;
		String wcLimit = null;

		// Multithread issues workaround
		SQLiteQueryBuilder qb = builderWishlistComponent();
		Cursor cursor = qb.query(
				getReadableDatabase(), wcColumns, wcSelection, wcSelectionArgs, wcGroupBy, wcHaving, wcOrderBy, wcLimit);
		
		return new WishlistComponentCursor(cursor);
	}

	/*
	 * Get all wishlist components for a specific id
	 */
	public WishlistComponentCursor queryWishlistComponentId(long id) {
		
		String[] wcColumns = null;
		String wcSelection = "wc." + S.COLUMN_WISHLIST_COMPONENT_ID + " = ?";
		String[] wcSelectionArgs = new String[]{ String.valueOf(id) };
		String wcGroupBy = null;
		String wcHaving = null;
		String wcOrderBy = null;
		String wcLimit = null;
		
		// Multithread issues workaround
		SQLiteQueryBuilder qb = builderWishlistComponent();
		Cursor cursor = qb.query(
				getReadableDatabase(), wcColumns, wcSelection, wcSelectionArgs, wcGroupBy, wcHaving, wcOrderBy, wcLimit);
		
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
		String strFilter = S.COLUMN_WISHLIST_COMPONENT_ID + " = "  + id;
		
		ContentValues values = new ContentValues();
		values.put(S.COLUMN_WISHLIST_COMPONENT_QUANTITY, quantity);
		
		return updateRecord(S.TABLE_WISHLIST_COMPONENT, strFilter, values);
	}
	
	public boolean queryDeleteWishlistComponent(long id) {
		String where = S.COLUMN_WISHLIST_COMPONENT_ID + " = ?";
		String[] args = new String[]{ "" + id };
		return deleteRecord(S.TABLE_WISHLIST_COMPONENT, where, args);
	}
	
	/*
	 * Update a wishlist component to a specific wishlist
	 */
	public int queryUpdateWishlistComponentNotes(long id, int notes) {
		String strFilter = S.COLUMN_WISHLIST_COMPONENT_ID + " = "  + id;
		
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
		
		QB.setTables(S.TABLE_WISHLIST_COMPONENT + " AS wc" + " LEFT OUTER JOIN " + S.TABLE_WISHLIST + " AS w" + " ON " +
				"wc." + S.COLUMN_WISHLIST_COMPONENT_WISHLIST_ID + " = " + "w." + S.COLUMN_WISHLIST_ID + " LEFT OUTER JOIN " +
				S.TABLE_ITEMS + " AS i" + " ON " + "wc." + S.COLUMN_WISHLIST_COMPONENT_COMPONENT_ID + " = " + 
				"i." + S.COLUMN_ITEMS_ID);

		QB.setProjectionMap(projectionMap);
		return QB;
	}
	
}
