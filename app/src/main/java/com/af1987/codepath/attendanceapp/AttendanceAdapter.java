package com.af1987.codepath.attendanceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {
    private List<Student> students;
    private Context context;

    public AttendanceAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_attendance, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(students.get(i));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        Switch swAttendance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            swAttendance = itemView.findViewById(R.id.swAttendance);
        }

        public void bind(final Student student) {
            swAttendance.setText(student.getName());
            swAttendance.setChecked(student.present());
            swAttendance.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isPressed()) {
                    Realm.getDefaultInstance().executeTransaction(r -> {
                        student.togglePresent();
                        r.insertOrUpdate(student);
                    });
                    Toast.makeText(context, student.getName() + ":  " +
                                    (student.present() ? "Present!" : "Absent."),
                                    Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
