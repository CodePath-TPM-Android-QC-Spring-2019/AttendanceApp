package com.af1987.codepath.attendanceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;
import io.realm.exceptions.RealmException;

public class Student extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private boolean present;
    private int experience;

    public Student() {}

    public Student(String name) {
        this(name,1);
    }

    public Student(String name, int experience) {
        this.name = name;
        this.present = false;
        this.experience = experience;
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public void togglePresent() {
        present = !present;}
    public boolean present() {return present;}
    public int getExperience() {return experience;}
    public void setExperience(int experience) {this.experience = experience;}
    public String getId() {return id;}

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US,"%s,%s,%d", id, name, present ? 1 : 0);
    }

    public static RealmResults<Student> allStudents(Context context) {
        RealmResults<Student> students = null;
        try {
            Realm realm = Realm.getDefaultInstance();
            students = realm.where(Student.class).findAll();
            if (students == null || students.size() == 0) {
                Log.d("_AF", "allStudents: Populating...");
                List<Student> list = new ArrayList<>();
                for (String s : context.getResources().getStringArray(R.array.student_list))
                    list.add(new Student(s));
                realm.executeTransaction(r -> r.insertOrUpdate(list));
            }
        } catch (RealmException ex) {Log.e("_AF", "allStudents: " + ex.getMessage());}
        Log.d("_AF", "allStudents: From realm...");
        return students != null ? students.sort("name") : null;
    }

    static class Group extends ArrayList<Student> {
        static final int MAX_SIZE = 5;

//        private Group (Student s) {
//            super();
//            add(s);
//        }

        static List<Group> makeGroups(RealmResults<Student> allStudents) {
            List<Student> students = allStudents.where().equalTo("present", true)
                    .sort("experience", Sort.DESCENDING).findAll();
            Log.d("_AF", "makeGroups: Students present:  " + students.size());
            students = Arrays.asList(students.toArray(new Student[]{}));
            final int TOTAL_PRESENT = students.size(), MIN_SIZE = minGroupSize(TOTAL_PRESENT),
                    NUM_GROUPS = TOTAL_PRESENT / MIN_SIZE;
            List<Group> groups = new ArrayList<>();
            for (int i = 0; i < NUM_GROUPS; ++i)
                groups.add(new Group());
            while (!students.isEmpty()) {
                List<Student> batch = students.subList(0,
                        students.size() < NUM_GROUPS ? students.size() : NUM_GROUPS);
                students = students.subList(batch.size(), students.size());
                Collections.shuffle(batch);
                int i = -1;
                for (Student s : batch)
                    groups.get(i = (i + 1) % NUM_GROUPS).add(s);
            }
            Log.d("_AF", "makeGroups:\n" + TextUtils.join("\n", groups));
            return groups;
        }

//        @Override
//        public boolean add(Student student) {
//            return /* size() < MAX_SIZE  && */ super.add(student);
//        }

        @NonNull
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
//            sb.append("--[ ");
            for (Student s : this)
                sb.append(s.name).append(" / ");
            sb.setLength(sb.length() - 3);
            return sb
//                    .append(" ]--")
                    .toString();
        }

        private static int minGroupSize(
                int n
        ) {
            return
//                    n > 9 ? Math.min(n / 10,
                            n < MAX_SIZE - 1 ? n : MAX_SIZE - 1;
//                    ) : 1;
        }
    }

}
