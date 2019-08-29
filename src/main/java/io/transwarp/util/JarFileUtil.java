package io.transwarp.util;

import io.transwarp.util.meta.ClassMeta;

import java.io.File;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Logger;

import io.transwarp.util.Log;


/**
 * @author transwarp
 * @date 2019-08-29 下午4:59
 */
public class JarFileUtil {

    private ArrayList<String> jarList = new ArrayList<String>();
    private ArrayList<String> classList = new ArrayList<String>();
    private Logger logger = Log.getLogger();

    public JarFileUtil(String dirName) {

        this.getJarFile(dirName);
    }


    /**
     * get alll jar file in drectory dirName
     * @param dirName
     * @return ArrayList<String>
     */
    public void getJarFile(String dirName) {
        logger.info("===now is get jar file from" + dirName);
        if (dirName.length() > 0) {
            File dir = new File(dirName);
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                  String fileName = file.getAbsolutePath();
                  // directly get jar file under dirName
                  if(file.isFile() && fileName.endsWith(".jar")){
                      this.jarList.add(fileName);
                  }
                  // if there is directory under dirName,then get all child directory jar file
                  else if(file.isDirectory()){
                      try {
                          this.getJarFile(fileName);
                      }catch (Exception e){
                          e.printStackTrace();
                      }
                  }
                }
            }
            else{
                logger.info("there is no jar file in" + dirName);
            }
        } else {
            logger.info("the dirName is empty");
        }
    }

    /**
     *  get class from a jar file
     * @param jarFile
     * @return ArrayList<ClassMeta>
     */
    public ArrayList<ClassMeta> getClassFromJar(String jarFile){
        ArrayList<ClassMeta> classNames = new ArrayList<ClassMeta>();
        try {

            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> jarEntries =  jar.entries();
            while (jarEntries.hasMoreElements()){
                JarEntry entry = jarEntries.nextElement();
                String entryName = entry.getName();
                logger.info("=====\njar entry name" + entryName);
                // excude META-INF
                if(entryName.contains("META-INF")){
                    if(entryName.endsWith(".class")){
                        String className = entryName.replace("/",".").replace(".class","");
                        logger.info(className + "...in "+ jarFile);
                        ClassMeta cm = new ClassMeta(className);
                        cm.setLocation(jarFile);
                        classNames.add(cm);
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return classNames;
    }
}