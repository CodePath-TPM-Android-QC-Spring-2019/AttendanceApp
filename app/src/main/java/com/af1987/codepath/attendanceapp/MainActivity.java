package com.af1987.codepath.attendanceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    RealmResults<Student> students;
    AttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        RecyclerView rvAttendance = findViewById(R.id.rvAttendance);
        students = Student.allStudents(this);
        students.addChangeListener((students, changeSet) -> {
            int total = students.size(), present =
                    students.where().equalTo("present", true).findAll().size();
            getSupportActionBar().setTitle(
                    String.format(Locale.US, "Present: %d/%d", present, total));
        });
        adapter = new AttendanceAdapter(this, students);
        rvAttendance.setLayoutManager(new LinearLayoutManager(this));
        rvAttendance.setAdapter(adapter);
        createResetListener();
    }

    private void createResetListener() {
        findViewById(R.id.ivCodePath).setOnLongClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setMessage("Set all students to absent?")
                    .setPositiveButton("Reset", (dialog, i) -> new AlertDialog.Builder(this)
                            .setMessage("Are you sure??")
                            .setPositiveButton("Reset them damn", (dialog2, i2) -> {
                                dialog.dismiss();
                                Realm.getDefaultInstance().executeTransaction(realm -> {
                                    for (Student s : students)
                                        s.setPresent(false);
                                    realm.insertOrUpdate(students);
                                });
                                adapter.notifyDataSetChanged();
                            })
                            .setCancelable(true)
                            .create().show())
                    .setCancelable(true)
                    .create().show();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void exportToCSV(MenuItem item) {
        verifyStoragePermissions();
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "External storage is busy, please try again later.", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = getPublicStorageDir();
        try {
            FileUtils.writeLines(file, students);
            Toast.makeText(this, "Attendance file created successfully at: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {Log.e("_AF", "exportToCSV: " + e.getMessage());}
    }

    public void verifyStoragePermissions() {
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private static File getPublicStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "attendance.csv");
        if (!file.mkdirs())
            Log.e("_AF", "Directory not created.");
        return file;
    }

    public void launchGroupActivity(MenuItem item) {
        startActivity(new Intent(this, GroupActivity.class));
    }
}
