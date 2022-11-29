package com.project.ofcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Course> list;

    public MyAdapter(Context context, ArrayList<Course> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Course course = list.get(position);
        holder.courseName.setText(course.getName());
        holder.courseCode.setText(course.getCode());
        holder.offeringSession.setText(course.getSession());
        holder.prerequisites.setText(course.getPrereq());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView courseName, courseCode, offeringSession, prerequisites;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.tvCourseName);
            courseCode = itemView.findViewById(R.id.tvCourseCode);
            offeringSession = itemView.findViewById(R.id.tvOfferingSession);
            prerequisites = itemView.findViewById(R.id.tvPrerequisites);
        }
    }
}
