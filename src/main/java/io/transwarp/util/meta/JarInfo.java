package io.transwarp.util.meta;

import java.util.ArrayList;

/**
 * @author transwarp
 * @date 2019-08-29 下午5:32
 */
public class JarInfo {

    private String groupId;
    private String artifatcId;
    private String version;
    private String jarName;
    private ArrayList<ClassMeta> className = new ArrayList<ClassMeta>();

    public JarInfo(String jarName){
        this.jarName = jarName;
    }

    public String getJarName(){
        return this.jarName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifatcId() {
        return artifatcId;
    }

    public void setArtifatcId(String artifatcId) {
        this.artifatcId = artifatcId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public ArrayList<ClassMeta> getClassName() {
        return className;
    }

    public void setClassName(ArrayList<ClassMeta> className) {
        this.className = className;
    }
}
