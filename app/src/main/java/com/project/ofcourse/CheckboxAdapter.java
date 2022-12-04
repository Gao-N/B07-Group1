package com.project.ofcourse;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CheckboxClass> checks;
    public ArrayList<String> selectedCourses;


    public CheckboxAdapter(Activity context, ArrayList<CheckboxClass> checks) {
        this.context = context;
        this.checks = checks;
        this.selectedCourses = new ArrayList<>();
    }

    @NonNull
    @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         View view = layoutInflater.inflate(R.layout.past_row_item, parent, false);
         ViewHolder viewHolder = new ViewHolder(view);
         return viewHolder;
     }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckboxClass checkboxClass = checks.get(position);
        holder.textView6.setText(checkboxClass.getCourseCode());
        holder.checkBox.setChecked(checkboxClass.isSelected());
    }

     @Override
    public int getItemCount() {return checks.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView6;
        private CheckBox checkBox;
        private ConstraintLayout checkboxitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView6 = itemView.findViewById(R.id.textView6);
            this.checkboxitem = itemView.findViewById(R.id.past_row_item);
            this.checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean selected = ((CheckBox) view).isChecked();

                    if (selected) {
                        checks.get(getAdapterPosition()).select(true);
                        selectedCourses.remove(checks.get(getAdapterPosition()).getCourseCode());
                    }
                    else {
                        checks.get(getAdapterPosition()).select(false);
                        selectedCourses.add(checks.get(getAdapterPosition()).getCourseCode());
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }


}
