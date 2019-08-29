package io.transwarp.util;

import org.apache.commons.cli.*;


public class Check{



    public static  void main(String[] args) throws ParseException {

        Boolean isClasspath = false;
        String dirName = "";
        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(Client.opts,args);

        if(cl.hasOption("h")){

            Client.printHelp(Client.opts);
        }
        if(cl.hasOption("c")){
            isClasspath = true;
        }
        if(cl.hasOption("d")){
            dirName = cl.getOptionValue("d");
        }
        // step1: get class from jar
        // step2: check class conflict
    }

}