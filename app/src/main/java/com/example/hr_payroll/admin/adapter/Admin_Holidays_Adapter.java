package com.example.hr_payroll.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hr_payroll.R;
import com.example.hr_payroll.model.Holiday;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Admin_Holidays_Adapter  extends RecyclerView.Adapter<Admin_Holidays_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Holiday> holidays;
    public Admin_Holidays_Adapter(Context context){
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
        holder.name.setText(holiday.getName());
        String dateOut =holiday.getYear()+"-"+holiday.getMonth()+"-"+holiday.getDay();
        holder.date.setText(formatDate(dateOut));

    }
    private String formatDate(String deliveryDate){
        SimpleDateFormat dateFormatprev = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateFormatprev.parse(deliveryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        String changedDate = dateFormat.format(d);

        return changedDate;
    }
    @Override
    public int getItemCount() {
        return holidays!=null?holidays.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_holiday_date)
        TextView date;
        @BindView(R.id.tv_holiday_name)
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
