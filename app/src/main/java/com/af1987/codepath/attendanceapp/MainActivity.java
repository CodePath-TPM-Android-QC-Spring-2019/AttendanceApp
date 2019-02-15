package com.af1987.codepath.attendanceapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvAttendance = findViewById(R.id.rvAttendance);
        students = Student.allStudents();
        AttendanceAdapter adapter = new AttendanceAdapter(this, students);
        rvAttendance.setLayoutManager(new LinearLayoutManager(this));
        rvAttendance.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
