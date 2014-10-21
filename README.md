MonsterHunter3UDatabase
=======================

Source code for the Android app Monster Hunter 3U Database.

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
  - Contains classes for data objects obtained from database
