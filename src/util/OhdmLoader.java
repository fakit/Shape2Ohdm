package util;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static util.Config.*;

public class OhdmLoader {

    public OhdmLoader()
    {
        LoadConfigs();
    }

    public void importFromIntermediateIntoOhdm() throws Exception
    {

        CreateConfigFiles();
        CreateStep2Schema();

        ProcessBuilder processBuilder = new ProcessBuilder();
        // Windows

        String cmd="java -classpath "+JDBC_DRIVER_PATH+" -jar "+OHDM_CONVERTER_PATH +" -h \""+CONFIG_PATH+"\\db_ohdm_historic_local\" -d \""+CONFIG_PATH+"\\db_shape_import\"";
        Process pr =processBuilder.command("cmd.exe", "/c", cmd).start();

        pr.waitFor();
        try(BufferedReader brErr = new BufferedReader(new InputStreamReader(pr.getErrorStream())))
        {
            String errorLine;
            while((errorLine = brErr.readLine()) != null)
            {
                System.out.println(errorLine);
            }
        }

    }
    private void CreateStep2Schema() throws Exception
    {
        String SqlCmd=" CREATE SCHEMA IF NOT EXISTS "+SCHEME_INTERMEDIATE+"; CREATE SCHEMA IF NOT EXISTS "+SCHEME_OHDM+";";

        Process pr = new ProcessBuilder(PGSQL_FILE_PATH, "-d", DB_NAME,"-U", DB_USER, "-h", DB_HOST,"-c", SqlCmd).start();

        pr.waitFor();
        try(BufferedReader brErr = new BufferedReader(new InputStreamReader(pr.getErrorStream())))
        {
            String errorLine;
            while((errorLine = brErr.readLine()) != null)
            {
                System.out.println(errorLine);
            }
        }
    }
    private void CreateConfigFiles( ) throws Exception
    {

        String OHDM_PARAMETER="servername:"+  DB_HOST_OHDM+
                "\nportnumber:"+ DB_PORT_OHDM+
                "\nusername:"+ DB_USER_OHDM+
                "\npwd:"+DB_PASS_OHDM+
                "\ndbname:"+DB_NAME_OHDM+
                "\nschema:"+ SCHEME_OHDM;

        String HISTORIC_IMPORT_PARAMETER="servername:"+  DB_HOST_OHDM+
                "\nportnumber:"+ DB_PORT_OHDM+
                "\nusername:"+ DB_USER_OHDM+
                "\npwd:"+DB_PASS_OHDM+
                "\ndbname:"+DB_NAME_INTERMEDIATE+
                "\nschema:"+ SCHEME_INTERMEDIATE+
                "\ncolumnNameObjectName:"+GID+
                "\ncolumnNameGeometry:"+ GEOM+
                "\ntableName:"+ TABLENAME+
                "\ncolumnValidSinceDay:" +COLUMNVALIDSINCEDAY+
                "\ncolumnValidSinceMonth:" +COLUMNVALIDSINCEMONTH+
                "\ncolumnValidSinceYear:" +COLUMNVALIDSINCEYEAR+
                "\ncolumnValidUntilDay:" +COLUMNVALIDUNTILDAY+
                "\ncolumnValidUntilMonth:" +COLUMNVALIDUNTILMONTH+
                "\ncolumnValidUntilYear:" +COLUMNVALIDUNTILYEAR+
                "\nvalidSince:"+ VALIDSINCE+
                "\nvalidUntil:"+ VALIDUNTIL+
                "\nclassificationID:"+ CLASSIFICATION_ID;
        if(!IF_CONFIG_PATH){
            System.err.println("No Config Directory seted, unsing Standard dir: \n CONFIG_PATH = " +CONFIG_PATH);
            File ConfigFileDir = new File(CONFIG_PATH);
            ConfigFileDir.mkdirs();
        }
        PrintWriter writer = new PrintWriter(CONFIG_PATH+"\\db_shape_import", "UTF-8");
        writer.println(OHDM_PARAMETER);
        writer.close();
        writer = new PrintWriter(CONFIG_PATH+"\\db_ohdm_historic_local", "UTF-8");
        writer.println(HISTORIC_IMPORT_PARAMETER);
        writer.close();
    }

    private void LoadConfigs() {
        try {

            File file = new File(CONFIG_PATH+"\\GenConfigs");

            if (file.createNewFile()){
                String[] GenConfigs={
                        "DB_USER = postgres",
                        "DB_PASS = root",
                        "DB_NAME = ohdm",
                        "DB_HOST = localhost",
                        "SCHEME_TEMP = temp",
                        "SCHEME_TEST = test",
                        "SCHEME_CACHE = intermediateosm",
                        "DB_GEO_USER=geoserver",
                        /*STEP 2*/
                        "DB_HOST_OHDM = localhost",
                        "DB_PORT_OHDM = 5432",
                        "DB_USER_OHDM = postgres",
                        "DB_PASS_OHDM = root",
                        //Intermediate
                        "DB_NAME_INTERMEDIATE =ohdm",
                        //ohdm
                        "DB_NAME_OHDM = ohdm",
                        "SCHEME_OHDM = ohdm",
                        "SCHEME_INTERMEDIATE = intermediateosm",
                        "GID=gid",
                        "GEOM=geom",
                        "COLUMNVALIDSINCEDAY=23",
                        "COLUMNVALIDSINCEMONTH=09",
                        "COLUMNVALIDSINCEYEAR=1930",
                        "COLUMNVALIDUNTILDAY=07",
                        "COLUMNVALIDUNTILMONTH=07",
                        "COLUMNVALIDUNTILYEAR=2017",
                        "VALIDSINCE=12-04-1999",
                        "VALIDUNTIL=12-04-2022",
                        "CLASSIFICATION_ID=13",
                        "JDBC_DRIVER_PATH= C:\\dev\\ohdm\\postgresql-42.1.1.jar",
                        "OHDM_CONVERTER_PATH=C:\\dev\\ohdm\\OHDMConverter.jar",
                        "TABLENAME=''",
                        "SHP_TO_PGSQL_FILE_PATH = C:\\Program Files\\PostgreSQL\\9.6\\bin\\shp2pgsql.exe",
                        "PGSQL_FILE_PATH = C:\\Program Files\\PostgreSQL\\9.6\\bin\\psql.exe",
                };
                Files.write(file.toPath(), Arrays.asList(GenConfigs));

            }

            List<String> fileLines = Files.readAllLines(file.toPath());

            for (String Param:fileLines)
            {
                String[] KeyValue= Param.split("=");
                String VarKey= KeyValue[0].trim();
                String VarValue= KeyValue[1].trim();
                switch (VarKey){

                    case "DB_USER":
                        DB_USER = VarValue;
                        break;
                    case "DB_PASS":
                        DB_PASS = VarValue;
                        break;
                    case "DB_NAME":
                        DB_NAME = VarValue;
                        break;
                    case "DB_HOST":
                        DB_HOST = VarValue;
                        break;
                    case "SCHEME_TEMP":
                        SCHEME_TEMP = VarValue;
                        break;
                    case "SCHEME_TEST":
                        SCHEME_TEST = VarValue;
                        break;
                    case "SCHEME_CACHE":
                        SCHEME_CACHE = VarValue;
                        break;
                    case "DB_GEO_USER":
                        DB_GEO_USER = VarValue;
                        break;
                    case "DB_HOST_OHDM":
                        DB_HOST_OHDM = VarValue;
                        break;
                    case "DB_PORT_OHDM":
                        DB_PORT_OHDM = VarValue;
                        break;
                    case "DB_USER_OHDM":
                        DB_USER_OHDM = VarValue;
                        break;
                    case "DB_PASS_OHDM":
                        DB_PASS_OHDM = VarValue;
                        break;
                    case "DB_NAME_INTERMEDIATE":
                        DB_NAME_INTERMEDIATE = VarValue;
                        break;
                    case "DB_NAME_OHDM":
                        DB_NAME_OHDM = VarValue;
                        break;
                    case "SCHEME_OHDM":
                        SCHEME_OHDM = VarValue;
                        break;
                    case "SCHEME_INTERMEDIATE":
                        SCHEME_INTERMEDIATE = VarValue;
                        break;
                    case "GID":
                        GID = VarValue;
                        break;
                    case "GEOM":
                        GEOM = VarValue;
                        break;
                    case "COLUMNVALIDSINCEDAY":
                        COLUMNVALIDSINCEDAY = VarValue;
                        break;
                    case "COLUMNVALIDSINCEMONTH":
                        COLUMNVALIDSINCEMONTH = VarValue;
                        break;
                    case "COLUMNVALIDSINCEYEAR":
                        COLUMNVALIDSINCEYEAR = VarValue;
                        break;
                    case "COLUMNVALIDUNTILDAY":
                        COLUMNVALIDUNTILDAY = VarValue;
                        break;
                    case "COLUMNVALIDUNTILMONTH":
                        COLUMNVALIDUNTILMONTH = VarValue;
                        break;
                    case "COLUMNVALIDUNTILYEAR":
                        COLUMNVALIDUNTILYEAR = VarValue;
                        break;
                    case "VALIDSINCE":
                        VALIDSINCE = VarValue;
                        break;
                    case "VALIDUNTIL":
                        VALIDUNTIL = VarValue;
                        break;
                    case "CLASSIFICATION_ID":
                        CLASSIFICATION_ID = VarValue;
                        break;
                    case "JDBC_DRIVER_PATH":
                        JDBC_DRIVER_PATH = VarValue;
                        break;
                    case "OHDM_CONVERTER_PATH":
                        OHDM_CONVERTER_PATH = VarValue;
                        break;
                    case "TABLENAME":
                        if (TABLENAME.length()<5)
                        { TABLENAME = VarValue;}
                        break;
                    case "SHP_TO_PGSQL_FILE_PATH":
                        SHP_TO_PGSQL_FILE_PATH = VarValue;
                        break;
                    case "PGSQL_FILE_PATH":
                        PGSQL_FILE_PATH = VarValue;
                        break;

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
