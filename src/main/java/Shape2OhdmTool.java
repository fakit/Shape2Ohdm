import static utils.Config.*;

public class Shape2OhdmTool {

    public static void main(String[] args) throws Exception {
        if(args.length > 0) {
            CONFIG_PATH = args[0];
        }
        if(args.length > 1) {
            TABLENAME = args[1];
        }
        if(args.length > 2) {
            System.err.println("at most two parameter required: CONFIG PATH and TABLENAME");
            System.exit(1);
        }
        System.out.println("Start Importing Shape into Ohdm Database");
        OhdmLoader ohdmLoader = new OhdmLoader();
        ohdmLoader.importFromIntermediateIntoOhdm();
        System.out.println("Importing Shape into Ohdm Database : DONE !");
    }
}
