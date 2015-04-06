MonsterHunter4UDatabase
=======================

Source code for the Android app Monster Hunter 4U Database. 

[![Download from Google Play](http://www.android.com/images/brand/android_app_on_play_large.png "Download from Google Play")](https://play.google.com/store/apps/details?id=com.daviancorp.android.mh4udatabase)
[![MH4U Database on fdroid.org](https://camo.githubusercontent.com/7df0eafa4433fa4919a56f87c3d99cf81b68d01c/68747470733a2f2f662d64726f69642e6f72672f77696b692f696d616765732f632f63342f462d44726f69642d627574746f6e5f617661696c61626c652d6f6e2e706e67 "Download from fdroid.org")](https://f-droid.org/app/com.daviancorp.android.mh4udatabase)

iOS Port available here:

https://github.com/nshetty26/MH4UDatabase

https://appsto.re/us/IxHh6.i
[![Download on the App Store](https://devimages.apple.com.edgekey.net/app-store/marketing/guidelines/images/badge-download-on-the-app-store.svg "Download on the App Store")](https://appsto.re/us/IxHh6.i)

List of To-Dos can be found here:

https://trello.com/b/OdgrIacq/monster-hunter-4u-database

### Building

Source runs in Android Studio.

### Database

Located in MonsterHunter4UDatabase\app\src\main\assets\databases.

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
  - 'Sharpness' code to draw sharpness using values

com.daviancorp.android.ui.list
  - List activities + fragments
    - Navigated through the Home activity
