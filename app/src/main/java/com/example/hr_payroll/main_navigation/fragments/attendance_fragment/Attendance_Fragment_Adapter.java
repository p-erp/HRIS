package com.example.hr_payroll.main_navigation.fragments.attendance_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.Attendance;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Attendance_Fragment_Adapter extends RecyclerView.Adapter<Attendance_Fragment_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Attendance> attendanceArrayList;
    public Attendance_Fragment_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<Attendance> attendanceArrayList){
        this.attendanceArrayList = attendanceArrayList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_fragment_attendance,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance attendance = attendanceArrayList.get(position);
        holder.time_in.setText(attendance.getTime_in());
        holder.time_in_date.setText(attendance.getLocation_out_lat());

    }

    @Override
    public int getItemCount() {
        return attendanceArrayList!=null? attendanceArrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time_in)
        TextView time_in;
        @BindView(R.id.time_in_date)
        TextView time_in_date;
        @BindView(R.id.time_out)
        TextView time_out;
        @BindView(R.id.time_out_date)
        TextView time_out_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
