package util;
import static util.Config.*;

public class Shape2OhdmTool {


    public static void main(String[] args)  {

        if (args.length > 0) {
            CONFIG_PATH = args[0];
            System.out.println("CONFIG_PATH = "+CONFIG_PATH);
            IF_CONFIG_PATH=true;
        }
        if (args.length > 1) {
            TABLENAME = args[1];
            System.out.println("TABLENAME = "+TABLENAME);
        }
        if (args.length > 2) {
            System.err.println("at most two parameter required: CONFIG PATH and TABLENAME");
            System.exit(1);
        }
        try {
            ohdmexecute();
        } catch(
                Exception e)

        {
            e.printStackTrace();
        }

    }
    public static void ohdmexecute() throws Exception {
        System.out.println("Start Importing Shape into Ohdm Database");
        OhdmLoader ohdmLoader = new OhdmLoader();
        ohdmLoader.importFromIntermediateIntoOhdm();
        System.out.println("Importing Shape into Ohdm Database : DONE !");
    }
}
