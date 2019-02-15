package com.af1987.codepath.attendanceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

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
                .inflate(R.layout.rv_attendance_row, viewGroup, false));
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
            swAttendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    student.togglePresent(1);
                    Toast.makeText(context, student.getName() + ": " +
                            (student.presentOn(1) ? "present!" : "absent."),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
