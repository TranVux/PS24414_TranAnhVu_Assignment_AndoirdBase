package com.example.ps24414_assignmentgd1_trananhvu.Model;

import java.io.Serializable;

public class ClassSt implements Serializable {
    private String Id;
    private String className;

    public ClassSt(){

    }

    public ClassSt(String className){
        this.className = className;
    }

    public ClassSt(String Id, String className){
        this.Id = Id;
        this.className = className;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
