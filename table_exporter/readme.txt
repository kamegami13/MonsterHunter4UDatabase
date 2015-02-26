UPDATING THE DATABASE:

install sqlite3: http://mislav.uniqpath.com/rails/install-sqlite3/
install python 2.7 and pip
Run:
pip install xlrd
pip install unicodecsv

Run generate_sql.bat which will run the export_tables.py script. This will 

1. Updating the sheet
	a. Download the latest .xlsx from both google docs, place in data folder
	b. Run generate_sql.bat. This will Run the python script in the data folder to generate the tsv files for each table. Then it inserts the tsv files into the database file mh4u.db

https://docs.google.com/spreadsheets/d/17R3IMDUOonsPDXLGmEvnLP3ulK4QtBMAQ7HvL6M_zzA/edit#gid=510000930

https://docs.google.com/spreadsheets/d/1JAcWAPpcajwbBL05PmEE23L4Q6VVl9o-ZSBLpT8jusk/edit#gid=2066102403