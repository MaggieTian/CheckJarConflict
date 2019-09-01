package qa.tool;

import org.apache.commons.cli.*;


public class Check{


    public static  void main(String[] args) throws ParseException {

        String propertyPath = null;
        String dirName = "";
        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(Client.opts,args);

        if(cl.hasOption("h")){

            Client.printHelp(Client.opts);
        }
        if(cl.hasOption("c")){
            propertyPath = cl.getOptionValue("c");
        }
        if(cl.hasOption("d")){
            dirName = cl.getOptionValue("d");
        }

        CheckConflict check = new CheckConflict(dirName,propertyPath);
        check.printConflictInfo();
    }

}