package com.example.hr_payroll.hr.fragment.workday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.WorkDay;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkDays_Adapter extends RecyclerView.Adapter<WorkDays_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<WorkDay> workDayArrayList;
    public WorkDays_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<WorkDay> workDayArrayList){
        this.workDayArrayList = workDayArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_hr_workdays,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkDay workDay = workDayArrayList.get(position);
        holder.start_time.setText(workDay.getTime_start());
        holder.end_time.setText(workDay.getTime_end());
        holder.day.setText(workDay.getWork_days());
        holder.year.setText(workDay.getYear());

    }

    @Override
    public int getItemCount() {
        return workDayArrayList!=null?workDayArrayList.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.start_time)
        public TextView start_time;
        @BindView(R.id.end_time)
        public TextView end_time;
        @BindView(R.id.day)
        public TextView day;
        @BindView(R.id.year)
        public TextView year;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
