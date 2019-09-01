package qa.tool.util;

import qa.tool.Log;
import qa.tool.meta.ClassMeta;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import java.util.logging.Logger;


/**
 * @author transwarp
 * @date 2019-08-29 下午4:59
 */
public class JarFileUtil {

    private ArrayList<String> jarList = new ArrayList<String>();
    private Logger logger = Log.getLogger();
    private  String propertyPath = null;

    public JarFileUtil(String propertyPath) {
        this.propertyPath = propertyPath;

    }

    /**
     * get alll jar file in drectory dirName
     *
     * @param dirName
     * @return ArrayList<String>
     */

    public ArrayList<String> getJarFiles(String dirName) {

        this.extractJarFile(dirName);
        return this.jarList;
    }

    public void extractJarFile(String dirName) {
        logger.info("===now is get jar file from" + dirName);
        CheckConfig checkConf = new CheckConfig(this.propertyPath);
        if (dirName.length() > 0) {
            File dir = new File(dirName);
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String fileName = file.getAbsolutePath();
                    // directly get jar file under dirName that not in ignore jar list
                    if (file.isFile() && fileName.endsWith(".jar") && ! checkConf.isIgnoreJar(file.getName()) ) {
                        this.jarList.add(fileName);
                    }
                    // if there is directory under dirName,then get all child directory jar file
                    else if (file.isDirectory()) {
                        try {
                            this.extractJarFile(fileName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                logger.info("there is no jar file in" + dirName);
            }
        } else {
            logger.info("the dirName is empty");
        }

    }

    /**
     * get class from a jar file
     *
     * @param jarFile
     * @return ArrayList<ClassMeta>
     */
    public ArrayList<ClassMeta> getClassFromJar(String jarFile) {
        ArrayList<ClassMeta> classNames = new ArrayList<ClassMeta>();
        try {

            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> jarEntries = jar.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry entry = jarEntries.nextElement();
                String entryName = entry.getName();
                logger.info("=====\njar entry name" + entryName);
                // excude META-INF
                if (!entryName.contains("META-INF")) {
                    if (entryName.endsWith(".class")) {
                        String className = entryName.replace("/", ".").replace(".class", "");
                        logger.info(className + "...in " + jarFile);
                        ClassMeta cm = new ClassMeta(className);
                        cm.setLocation(jarFile);
                        classNames.add(cm);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNames;
    }


    public Set<String> getClassNames(String jarFile) {
        Set<String> classSet = new HashSet<String>();
        CheckConfig checkConfig = new CheckConfig(this.propertyPath);
        try {

            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> jarEntries = jar.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry entry = jarEntries.nextElement();
                String entryName = entry.getName();
                if (!entryName.contains("META-INF")) {
                    if (entryName.endsWith(".class")){
                        String className = entryName.replace("/", ".");
                        // filter class by add class ignore class list
                        if(! checkConfig.isIgnoreClass(className) && ! checkConfig.isIgnorePackage(className)){
                            classSet.add(className);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return classSet;
    }

    public Set<String> getJarNames(String className, Map<String, Set<String>> jarClassMap) {
        try {
            Set<String> jarList = new HashSet<String>();
            for (String jar : jarClassMap.keySet()) {
                if (jarClassMap.get(jar).contains(className)) {
                    jarList.add(jar);
                }
            }
            return jarList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * contact jar file to a String for the given conflict jar Set
     *
     * @param conflictJarsSet
     * @return String
     */

    public StringBuilder getConflitJarList(Set<String> conflictJarsSet, String sep) {

        StringBuilder jars = new StringBuilder();

        for (String jar : conflictJarsSet) {
            if (jar != null) {
                jars.append(jar.substring(jar.lastIndexOf("/") + 1)).append(sep);
            }
        }
        return jars;
    }
}