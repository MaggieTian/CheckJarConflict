package io.transwarp;

import org.apache.commons.cli.*;
public class Client {
    public Client(){

    }

    static Options opts = new Options();
    static{
        opts.addOption("h","help",false,"the command help");
        opts.addOption("d","directory",true,"the command specify the directory which include jar file");
        opts.addOption("c","config path",false,"the ignore list path which can skip check some package,jar,class");
    }

    /**
     * print the program help about runing opt
     * @param opts
     */
    static void printHelp(Options opts){
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("the program running options is as the following:\n",opts);
    }

}
