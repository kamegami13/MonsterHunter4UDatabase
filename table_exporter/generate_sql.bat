@Echo OFF

cd data

python export_tables.py

ECHO Finished exporting tables

cd ..

sqlite3 mh4u.db < sqlcode

ECHO Finished importing tables

pause