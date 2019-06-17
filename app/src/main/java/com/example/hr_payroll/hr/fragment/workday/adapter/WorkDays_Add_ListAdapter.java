package com.example.hr_payroll.hr.fragment.workday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.hr.fragment.workday.OnCall;
import com.example.hr_payroll.model.WorkDay;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkDays_Add_ListAdapter extends RecyclerView.Adapter<WorkDays_Add_ListAdapter.ViewHolder> {
    private  ArrayList<String> days;
    private Context context;
    private OnCall onCall;
    public WorkDays_Add_ListAdapter(Context context,OnCall onCall){
        this.context = context;
        this.onCall = onCall;
        days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_hr_workdays_list_days,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day.setText(days.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall.setDay(days.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.day)
        public TextView day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
