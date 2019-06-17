package com.example.hr_payroll.main_navigation.fragments.attendance_fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.mapview_attendance.AttendanceMapView;
import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.utilities.Functions;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Attendance_Attendance_Adapter extends RecyclerView.Adapter<Attendance_Attendance_Adapter.ViewHolder> {
    private Context context;
    private Functions functions;
    private ArrayList<Attendance> attendanceArrayList;
    public void SetData(ArrayList<Attendance> attendanceArrayList){
        this.attendanceArrayList = attendanceArrayList;
        notifyDataSetChanged();

    }
    public void DeleteData(){
        attendanceArrayList= new ArrayList<>();
        this.attendanceArrayList = null;
        notifyDataSetChanged();
    }
    public Attendance_Attendance_Adapter(Context context){
        this.context = context;
        functions = new Functions(context);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_fragment_attendance_attendance,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance attendance = attendanceArrayList.get(position);
        if(attendance.getTime_out().equals("")){
            holder.eventImage.setBackgroundResource(R.drawable.ic_event_busy_red_24dp);
        }else{

            holder.eventImage.setBackgroundResource(R.drawable.ic_event_busy_black_24dp);
        }

    }


    @Override
    public int getItemCount() {
        return attendanceArrayList != null  ? attendanceArrayList.size() :  0;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.event)
        ImageView eventImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
