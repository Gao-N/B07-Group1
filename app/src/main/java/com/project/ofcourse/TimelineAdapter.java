package com.project.ofcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyViewHolder>{
    Context context;
    ArrayList<String> list;
    String session;
    HashMap<String, ArrayList<String>> map;

    public TimelineAdapter(Context context,  HashMap<String, ArrayList<String>> map){
        this.context = context;
        this.map = map;
    }

    @NonNull
    @Override
    public TimelineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.timeline_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.MyViewHolder holder, int position) {
        //session = map.get(position);

        List<String> sessionList = new ArrayList<String>(map.keySet());
        String key = sessionList.get(position);
        ArrayList<String> value = map.get(key);
        for (int i = value.size(); i < 6; i++) {
            value.add("");
        }
        holder.courseList1.setText(value.get(0));
        holder.courseList2.setText(value.get(1));
        holder.courseList3.setText(value.get(2));
        holder.courseList4.setText(value.get(3));
        holder.courseList5.setText(value.get(4));
        holder.courseList6.setText(value.get(5));


//        if (value.size() <= 1) {
//            holder.courseList1.setText(value.get(0));
//        }
//        if (value.size() <= 2) {
//            holder.courseList2.setText(value.get(1));
//        }
//        if (value.size() <= 3) {
//            holder.courseList3.setText(value.get(2));
//        }
//        if (value.size() <= 4) {
//            holder.courseList4.setText(value.get(3));
//        }
//        if (value.size() <= 5) {
//            holder.courseList5.setText(value.get(4));
//        }
//        if (value.size() <= 6) {
//            holder.courseList6.setText(value.get(5));
//        }
        System.out.println("This Runs");
//
//        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
//            session = entry.getKey();
//            list = entry.getValue();
//            holder.session.setText(session);
//            if (list.size() <= 1) {
//                holder.courseList1.setText(list.get(0));
//            }
//            if (list.size() <= 2) {
//                holder.courseList2.setText(list.get(1));
//            }
//            if (list.size() <= 3) {
//                holder.courseList3.setText(list.get(2));
//            }
//            if (list.size() <= 4) {
//                holder.courseList4.setText(list.get(3));
//            }
//            if (list.size() <= 5) {
//                holder.courseList5.setText(list.get(4));
//            }
//            if (list.size() <= 6) {
//                holder.courseList6.setText(list.get(5));
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return map.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView session;
        TextView courseList1;
        TextView courseList2;
        TextView courseList3;
        TextView courseList4;
        TextView courseList5;
        TextView courseList6;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            session = itemView.findViewById(R.id.session);
            courseList1 = itemView.findViewById(R.id.courseList1);
            courseList2 = itemView.findViewById(R.id.courseList2);
            courseList3 = itemView.findViewById(R.id.courseList3);
            courseList4 = itemView.findViewById(R.id.courseList4);
            courseList5 = itemView.findViewById(R.id.courseList5);
            courseList6 = itemView.findViewById(R.id.courseList6);
        }
    }
}
