package com.af1987.codepath.attendanceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class GroupActivity extends AppCompatActivity {
    List<Student.Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        RecyclerView rvGroups = findViewById(R.id.rvGroups);
        RealmResults<Student> allStudents =
                Realm.getDefaultInstance().where(Student.class).findAll();
        groups = Student.Group.makeGroups(allStudents);
        GroupAdapter adapter = new GroupAdapter(groups);
        rvGroups.setLayoutManager(new GridLayoutManager(this, 2));
        rvGroups.setAdapter(adapter);
    }
}
