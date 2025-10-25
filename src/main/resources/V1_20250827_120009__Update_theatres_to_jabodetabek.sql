-- Update existing theatres to use JABODETABEK area (Jakarta, Bogor, Depok, Tangerang, Bekasi) theatres

UPDATE THEATRE SET THEATRE_NAME = 'CGV Grand Indonesia', THEATRE_CITY = 'Jakarta Pusat' WHERE THEATRE_ID = 1;
UPDATE THEATRE SET THEATRE_NAME = 'XXI Pondok Indah', THEATRE_CITY = 'Jakarta Selatan' WHERE THEATRE_ID = 2;
UPDATE THEATRE SET THEATRE_NAME = 'Cinepolis Depok Town Square', THEATRE_CITY = 'Depok' WHERE THEATRE_ID = 3;
UPDATE THEATRE SET THEATRE_NAME = 'XXI Bogor Trade Mall', THEATRE_CITY = 'Bogor' WHERE THEATRE_ID = 4;
UPDATE THEATRE SET THEATRE_NAME = 'CGV Summarecon Bekasi', THEATRE_CITY = 'Bekasi' WHERE THEATRE_ID = 5;
