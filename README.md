# Shape2OhdmTool


## zweiter Schritt OhdmImportTool

Here you can import the shapefile data into an Ohdm database. For this it must be ensured that the schema with the name: **intermediateosm **contains the desired table of the shapefile in the ohdm database.

JDBC_DRIVER_PATH= C:\dev\ohdm\postgresql-42.1.1.jar

OHDM_CONVERTER_PATH=C:\dev\ohdm\OHDMConverter.jar

TABLENAME=20210307214941

SHP_TO_PGSQL_FILE_PATH = C:\Program Files\PostgreSQL\9.6\bin\shp2pgsql.exe

PGSQL_FILE_PATH = C:\Program Files\PostgreSQL\9.6\bin\psql.exe


1-	The last created table is saved here as the table name. If necessary, you can address a selected table.

2-	The path of the JDBC DRIVER have to be set on **JDBC_DRIVER_PATH **in " [C:\Program Files\Apache Software Foundation\Tomcat 9.X\bin\configFiles\GenConfigs](C:\Program Files\Apache Software Foundation\Tomcat 9.X\bin\configFiles\GenConfigs) " after the first Step of the import process

3-	As with the JDBC DRIVER, the path of the OhdmConverter.jar must be specified as OHDM_CONVERTER_PATH.

4-	Then comes the path to the Postgresql database as **PGSQL_FILE_PATH**.

### Very Important !: Some extensions have to be installed in the ohdm database: hstore etc…

The following command must be selected to perform the second step of the import process:
Java -jar Shape2OhdmTool.jar <CONFIG_PATH> <TABLENAME>
Entering the table Name is optional, if none is specified, the entered table is considered in GenConfigs.
After the import comes “Importing Shape into Ohdm Database : DONE !"


## Launch:

1.	Create CATALINA_HOME env. Variable like: 

C:> echo% CATALINA_HOME%
C:\Program Files (x86)\Apache Software Foundation\Tomcat 9.x;

2.	Build the Artifacts of the Projekt OhdmImportTool into C:\Program Files\Apache Software Foundation\Tomcat 9.x\webapps

3.	Restart the Tomcat Webserver and launch [http://localhost:8080](http://localhost:8080)

4.	Here you can navigate to upload the Shapefile (some samples at: [https://github.com/OpenHistoricalMap/data](https://github.com/OpenHistoricalMap/data) )

 
Please make sure that the zip file only contain this files: .cpg, .dbf ect…

5.	Load the file, enter your username and press open, the shapefile will be load on a browser like.
 

Here the shapefile is already in the intermediateohdm scheme.

If it fails, please see the paths in GenConfigs above.

6.	Start the import to ohdm database 

Open the project, set the config directory and start the solution the ohdm entry will be created on the database


## Important links:

[https://github.com/OpenHistoricalDataMap/OHDMConverter/wiki/H2D](https://github.com/OpenHistoricalDataMap/OHDMConverter/wiki/H2D)

[https://github.com/OpenHistoricalDataMap](https://github.com/OpenHistoricalDataMap)

[https://github.com/OpenHistoricalDataMap/OHDMImportTool](https://github.com/OpenHistoricalDataMap/OHDMImportTool)

[https://github.com/fakit/Shape2OhdmTool](https://github.com/fakit/Shape2OhdmTool)

[https://github.com/OpenHistoricalMap/data](https://github.com/OpenHistoricalMap/data)

