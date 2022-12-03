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

import com.project.ofcourse.MyItemRecyclerViewAdapter;
import com.project.ofcourse.R;

import java.util.ArrayList;

public class CheckboxAdapter extends RecyclerView.Adapter<CheckboxAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CheckboxClass> checks;


    public CheckboxAdapter(Activity context, ArrayList<CheckboxClass> checks) {
        this.context = context;
        this.checks = checks;
    }

    @NonNull
    @Override
     public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
         View view = layoutInflater.inflate(R.layout.checkboxitem, parent, false);
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
            this.checkboxitem = itemView.findViewById(R.id.checkboxitem);
            this.checkBox = itemView.findViewById(R.id.checkBox);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean selected = ((CheckBox) view).isChecked();

                    if (selected) {
                        checks.get(getAdapterPosition()).select(true);
                    }
                    else {
                        checks.get(getAdapterPosition()).select(false);
                    }
                    notify();
                    // for?
                }
            });
        }
    }


}
