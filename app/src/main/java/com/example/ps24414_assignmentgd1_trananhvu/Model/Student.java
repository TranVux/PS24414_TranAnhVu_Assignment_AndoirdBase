package com.example.ps24414_assignmentgd1_trananhvu.Model;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String birthDay;
    private String classID;
    private String id;

    public Student() {
    }

    public Student(String id, String name, String birthDay, String classID){
        this.id = id;
        this.classID = classID;
        this.name = name;
        this.birthDay = birthDay;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
