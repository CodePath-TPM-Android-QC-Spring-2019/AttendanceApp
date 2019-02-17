package com.af1987.codepath.attendanceapp;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    List<Student.Group> groups;

    public GroupAdapter(List<Student.Group> groups) {
        this.groups = groups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_group, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(groups.get(i), i + 1);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGroup = itemView.findViewById(R.id.tvGroup);
        }

        public void bind(final Student.Group group, int groupNum) {
            tvTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            tvTitle.setText(String.format(Locale.US, "Group %d", groupNum));
            tvGroup.setText(group.toString().replaceAll(" / ", "\n"));
        }

    }
}
