package com.example.hr_payroll.main_navigation.fragments.home_fragment.adapter;

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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Attendance_Adapter extends RecyclerView.Adapter<Attendance_Adapter.ViewHolder> {
    private Context context;
    private Functions functions;
    private ArrayList<Attendance> attendanceArrayList;
    public void SetData(ArrayList<Attendance> attendanceArrayList){
        this.attendanceArrayList = attendanceArrayList;
        notifyDataSetChanged();

    }
    public Attendance_Adapter(Context context){
        this.context = context;
        functions = new Functions(context);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_fragment_attendance,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attendance attendance  = attendanceArrayList.get(position);
        String dateIn =attendance.getYear_in()+"-"+(Integer.parseInt(attendance.getMonth_in()))+"-"+attendance.getDay_in();
        holder.time_in_date.setText(formatDate(dateIn));
        String tIn = attendance.getTime_in().substring(0,2);
        if(tIn.equals("00")){
            holder.time_in.setText("12"+attendance.getTime_in().substring(2,5)+attendance.getTime_in().substring(8,attendance.getTime_in().length()));
        }else{
            holder.time_in.setText(attendance.getTime_in().substring(0,5)+attendance.getTime_in().substring(8,attendance.getTime_in().length()));
        }
        if(!attendance.getTime_out().equals("")){
            String tOut = attendance.getTime_out().substring(0,2);
            if(tOut.equals("00")){
                holder.time_out.setText("12"+attendance.getTime_out().substring(2,5)+attendance.getTime_out().substring(8,attendance.getTime_out().length()));
            }else{
                holder.time_out.setText(attendance.getTime_out().substring(0,5)+attendance.getTime_out().substring(8,attendance.getTime_out().length()));
            }

        }else{
            holder.time_out.setText("None");


        }



        if(!attendance.getMonth_out().equals("")){
            String dateOut =attendance.getYear_out()+"-"+(Integer.parseInt(attendance.getMonth_out()))+"-"+attendance.getDay_out();
            holder.time_out_date.setText(formatDate(dateOut));

        }else {
            holder.time_out_date.setText("None");

        }
        holder.image_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   popUp(v,attendance);
            }
        });


    }
    private void popUp(View view,Attendance attendance){
        PopupMenu popupMenu = new PopupMenu(context,view);
        popupMenu.getMenuInflater().inflate(R.menu.itemview_attendace_menu, popupMenu.getMenu());
        try {
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            Object menuPopupHelper = field.get(popupMenu);
            Class<?> cls = Class.forName("com.android.internal.view.menu.MenuPopupHelper");
            Method method = cls.getDeclaredMethod("setForceShowIcon", new Class[]{boolean.class});
            method.setAccessible(true);
            method.invoke(menuPopupHelper, new Object[]{true});
        } catch (Exception e) {
            e.printStackTrace();
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.pop_timeinmap:
                        if(attendance.getLocation_in_lat().equals("")){
                            functions.showMessage("No Data","You dont have location data");
                        }else{
                            intentTo(AttendanceMapView.class,attendance.getLocation_in_lat(),attendance.getLocation_in_long());
                        }

                        break;
                    case R.id.pop_timeoutmap:
                        if(attendance.getLocation_out_lat().equals("")){
                            functions.showMessage("No Data","You dont have location data");
                        }else{
                            intentTo(AttendanceMapView.class,attendance.getLocation_out_lat(),attendance.getLocation_out_long());
                        }
                        break;

                }
                return false;
            }
        });
        popupMenu.show();
    }
    private void intentTo(Class classname,String lat,String lang){
        Intent intent = new Intent(context,classname);
        intent.putExtra("lat",lat);
        intent.putExtra("lang",lang);
        context.startActivity(intent);
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
        return attendanceArrayList != null  ? attendanceArrayList.size() :  0;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time_in)
        TextView time_in;
        @BindView(R.id.time_in_date)
        TextView time_in_date;
        @BindView(R.id.time_out)
        TextView time_out;
        @BindView(R.id.time_out_date)
        TextView time_out_date;
        @BindView(R.id.menu)
        ImageView image_menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
