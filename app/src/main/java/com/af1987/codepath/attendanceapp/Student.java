package com.af1987.codepath.attendanceapp;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class Student {

    private String name;
    private BitSet attendance;

    public Student(String name) {
        this.name = name;
        this.attendance = new BitSet();
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public void togglePresent(int classNum) {attendance.flip(classNum);}

    public boolean presentOn(int classNum) {return attendance.get(classNum);}

    public String getAttendance(){
        //todo: unpack bitset into string representation
        return "";
    }

    public static List<Student> allStudents(){
        return Arrays.asList(
                new Student("Alex Feaser"),
                new Student("Logan Le"),
                new Student("Jia Yu Lun"),
                new Student("Claudia Martinez")
        );
    }


}
