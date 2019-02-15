package com.af1987.codepath.attendanceapp;

import java.util.Arrays;
import java.util.List;

public class Student {

    private String name;
    private boolean attendance;
    private int experience;

    public Student(String name) {
        this(name,1);
    }

    public Student(String name, int experience) {
        this.name = name;
        this.attendance = false;
        this.experience = experience;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void togglePresent() {attendance = !attendance;}
    public boolean present() {return attendance;}
    public int getExperience() {return experience;}
    public void setExperience(int experience) {this.experience = experience;}

    public static List<Student> allStudents(){
        return Arrays.asList(
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez"),
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez"),
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez"),
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez"),
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez"),
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez"),
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez"),
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez")
        );
    }

    public static class Group {

    }

}
