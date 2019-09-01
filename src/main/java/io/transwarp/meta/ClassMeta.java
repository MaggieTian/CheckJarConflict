package io.transwarp.meta;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author transwarp
 * @date 2019-08-29 下午4:45
 */
public class ClassMeta {


    private String className;
    private String location;
    private Integer classModifier;
    private Method[] methods;
    private Constructor[] constructors;
    private Field[] fields;


    public ClassMeta(String className){
        this.className = className;
    }
    public String getClassName(){
        return this.className;
    }
    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public Integer getClassModifier() {
        return classModifier;
    }

    public void setClassModifier(Integer classModifier) {
        this.classModifier = classModifier;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public Constructor[] getConstructors() {
        return constructors;
    }

    public void setConstructors(Constructor[] constructors) {
        this.constructors = constructors;
    }
}
