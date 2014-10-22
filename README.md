MonsterHunter3UDatabase
=======================

Source code for the Android app Monster Hunter 3U Database.

App can be found here in the Google Play Store:

https://play.google.com/store/apps/details?id=com.daviancorp.android.mh3udatabase&hl=en

### Database

Located in MonsterHunter3UDatabase\MonsterHunter3UDatabase\assets.

To modify database:
  1) .sql to modify directly.
  2) Import .sql file to any SQLite database (SQLite Database Browser)
  3) Save database as .sqlite
  4) Split .sqlite to files < 1 MB in db folder
  
  or
  
  1) Open .sqlite file to any SQLite database
  2) Make changes through queries
  3) Save database
  4) Splite .sqlite to files < 1 MB in db folder
  
DO NOT PUT ANY EXTRA FILES IN DB FOLDER

### Package Layout

com.daviancorp.android.data.classes
  - Contains classes for data objects

com.daviancorp.android.data.database
  - Contains Cursors to return rows/tuples from the database queries.
  - MonsterHunterDatabaseHelper.java: direct queries to database
  - DataHelper.java: Used by loaders to query database; uses MonsterHunterDatabaseHelper for queries

com.daviancorp.android.loader
  - Contains loaders for UI to load data

com.daviancorp.android.ui.adapter
  - Pager adapters

com.daviancorp.android.ui.detail
  - Detail activities + fragments to display a specific object and related data

com.daviancorp.android.ui.dialog
  - 'About' dialog
  - Wishlist-related dialogs

com.daviancorp.android.ui.general
  - Base activities
  - Home activity
  - 'Sharpness' code to draw sharpness using values (unused)

com.daviancorp.android.ui.list
  - List activities + fragments
    - Navigated through the Home activity
