package io.transwarp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * used to checkout whitelist jar,class,package in CheckConfig.properties
 */

public class CheckConfig {


    private List<String> ignorePackages = new ArrayList<String>();
    private List<String> ignoreJars = new ArrayList<String>();
    private List<String> ignoreClasses = new ArrayList<String>();



    public CheckConfig(String propertyPath){

        String properties_file_name;
        if(propertyPath != null && ! "".equals(propertyPath)){
            properties_file_name = propertyPath;
        }
        else {
            properties_file_name = "CheckConfig";
        }
        setCheckConfigByProperty(properties_file_name);

    }

    public void setCheckConfigByProperty(String propertyFile){

        ResourceBundle localResourceBundle = ResourceBundle.getBundle(propertyFile);
        String ignorePackagesString = localResourceBundle.getString("ignorePackages");
        String ignoreJarsString = localResourceBundle.getString("ingoreJars");
        String ignoreClassString = localResourceBundle.getString("ignoreClass");
        this.setCheckConfig(ignorePackagesString, ignoreJarsString, ignoreClassString);

    }

    public void setCheckConfig(String ignorePackagesString, String ignoreJarsString, String  ignoreClassString){

        if(ignoreJarsString != null && ! "".equals(ignoreJarsString) && !"null".equals(ignoreJarsString)){
            this.ignoreJars = Arrays.asList(ignoreJarsString.split(";"));
        }
        if(ignoreClassString != null && ! "".equals(ignoreClassString) && !"null".equals(ignoreClassString)){
            this.ignoreClasses = Arrays.asList(ignoreClassString.split(";"));
        }

        if (ignorePackagesString != null && !"".equals(ignorePackagesString) && !"null".equals(ignorePackagesString)) {

            this.ignorePackages = Arrays.asList(ignorePackagesString.split(";"));
        }
    }

    public Boolean isIgnoreJar(String jarFile){

        for(String ignoreJar : ignoreJars){
            if(jarFile.matches(ignoreJar)){
                return true;
            }
        }
        return false;


    }

    public Boolean isIgnoreClass(String className){
        for(String ignoreClass : ignoreClasses){
            if(className.matches(ignoreClass)){
                return true;
            }
        }
        return false;
    }

    public  Boolean isIgnorePackage(String className){

        for(String ignorePackage: ignorePackages){

            if(className.startsWith(ignorePackage)){
                return true;
            }
        }
        return false;
    }
}
