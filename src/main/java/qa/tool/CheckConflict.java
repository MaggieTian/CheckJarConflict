package qa.tool;


import qa.tool.util.JarFileUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class CheckConflict {


    private Map<String, Set<String>> jarClassMap = new HashMap<String, Set<String>>();
    private Map<String, Set<String>> conflictJarMap = new HashMap<String, Set<String>>();
    private String dirName =null;
    private String propertyPath = null;


    public CheckConflict(String dirName,String propertyPath) {

        this.dirName = dirName;
        this.propertyPath = propertyPath;

    }

    public Map<String, Set<String>> getConflict() {

        long classCount = 0;

        // use hashSet check ir there are duplicate class or not
        Set<String> allClassSet = new HashSet<String>();
        JarFileUtil jfUtil = new JarFileUtil(this.propertyPath);
        ArrayList<String> jarFiles = jfUtil.getJarFiles(this.dirName);

        for (String jarFile : jarFiles) {

            Set<String> classSet = jfUtil.getClassNames(jarFile);
            allClassSet.addAll(classSet);
            classCount +=classSet.size();
            jarClassMap.put(jarFile,classSet);


        }

        // if classCount != allClassSet.size, then it shows there are duplicate classes
        if (classCount != allClassSet.size()) {
            for (String className : allClassSet) {

                // find duplicate class in which jar
                Set<String> jarList = jfUtil.getJarNames(className, jarClassMap);
                // if jarList length is greater than 1, it shows this jar is conflict jar
                if (jarList != null && jarList.size() > 1) {
                    //append these conflict jars
                    if (conflictJarMap.keySet().contains(className)) {
                        conflictJarMap.get(className).addAll(jarList);
                    } else {
                        conflictJarMap.put(className, jarList);
                    }

                }
            }

        }
        return conflictJarMap;

    }

    public void printConflictInfo() {


        Map<String, Set<String>> conflictInfo = this.getConflict();
        JarFileUtil jfUtil = new JarFileUtil(this.propertyPath);
        Set<String> conflictJars = new HashSet<String>();

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);

//        for(Map.Entry<String,Set<String>> entry : conflictInfo.entrySet()){
//            printWriter.printf("Conflict jars:\n" + jfUtil.getConflitJarList(entry.getValue(),"\n").toString());
//            printWriter.printf("==============================================");
//            conflictJars.addAll(entry.getValue());
//        }

        printWriter.printf(String.format("have fount %d conflict class\n", conflictInfo.keySet().size()));
        printWriter.printf("Details info:\n");

        for (Map.Entry<String, Set<String>> entry : conflictInfo.entrySet()) {
            StringBuilder jars = jfUtil.getConflitJarList(entry.getValue(),";");
            printWriter.printf("==============================================\n");
            printWriter.printf(String.format("class %s conflict  in these jars:\n", entry.getKey()));
            printWriter.printf(jars+"\n");

        }
    }
}
