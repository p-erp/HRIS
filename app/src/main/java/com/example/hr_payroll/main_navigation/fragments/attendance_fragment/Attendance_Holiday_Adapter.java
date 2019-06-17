package com.example.hr_payroll.main_navigation.fragments.attendance_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.Holiday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Attendance_Holiday_Adapter extends RecyclerView.Adapter<Attendance_Holiday_Adapter.ViewHolder> {
    private ArrayList<Holiday> holidays;
    private Context context;
    public Attendance_Holiday_Adapter(Context context){
        this.context = context;
    }
    public void SetData(ArrayList<Holiday> holidays){
        this.holidays = holidays;
        notifyDataSetChanged();
    }
    public void DeleteData(){
        holidays = new ArrayList<>();
        this.holidays = null;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_admin_fragment_holidays,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Holiday holiday = holidays.get(position);
        holder.tv_day.setText(holiday.getDay());
        holder.tv_holiday_name.setText(holiday.getName());
        holder.tv_holiday_date.setText(formatDate(holiday.getYear()+"-"+holiday.getMonth()+"-"+holiday.getDay()));


    }
    private String formatDate(String deliveryDate){
        SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateFormatprev.parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
        String changedDate = dateFormat.format(d);

        return changedDate;
    }
    @Override
    public int getItemCount() {
        return holidays!=null? holidays.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_day)
        TextView tv_day;
        @BindView(R.id.tv_holiday_date)
        TextView tv_holiday_date;
        @BindView(R.id.tv_holiday_name)
        TextView tv_holiday_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
