package com.project.ofcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> list;

    public TimelineAdapter(Context context, ArrayList<String> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TimelineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.timeline_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.MyViewHolder holder, int position) {
        // not sure if this works
        String s = list.get(position);
        holder.courseList.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView session;
        TextView courseList;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            session = itemView.findViewById(R.id.session);
            courseList = itemView.findViewById(R.id.courseList);
        }
    }
}
