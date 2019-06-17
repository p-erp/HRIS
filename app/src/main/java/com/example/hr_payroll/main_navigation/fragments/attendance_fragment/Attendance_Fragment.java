package com.example.hr_payroll.main_navigation.fragments.attendance_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.manager.AttendanceManager;
import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.model.Holiday;
import com.example.hr_payroll.utilities.AppSharedPref;
import com.example.hr_payroll.utilities.Functions;
import com.google.common.collect.Lists;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Attendance_Fragment extends Fragment  implements OnDateSelectedListener, OnMonthChangedListener
    , Attendance_FragmentMVP.Fragment
{
    private AttendanceManager attendanceManager;
    private Attendance_Attendance_Adapter attendance_fragment_adapter;
    private Attendance_Holiday_Adapter attendance_holiday_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        attendanceManager = new AttendanceManager(getActivity());
        attendance_fragment_adapter = new Attendance_Attendance_Adapter(getActivity());
        attendance_holiday_adapter = new Attendance_Holiday_Adapter(getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.main_navigation_attendance_fragment,container,false);
    }


    @BindView(R.id.rv_holidays)
    RecyclerView rv_holidays;
    @BindView(R.id.rv_attendance)
    RecyclerView rv_attendance;

    @BindView(R.id.tv_noholiday)
    TextView tv_noholiday;
    @BindView(R.id.tv_noattendance)
    TextView tv_noattendace;

    private MaterialCalendarView mcv;
    private Attendance_Fragment_Presenter presenter;
    private Functions functions;
    private AppSharedPref appSharedPref;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        presenter = new Attendance_Fragment_Presenter(this,getActivity());
        functions = new Functions(getActivity());
        appSharedPref = new AppSharedPref(getActivity());
        /** HOLIDAY **/
        rv_holidays.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        rv_holidays.setAdapter(attendance_holiday_adapter);
        /** ATTENDANCE **/
        rv_attendance.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        rv_attendance.setAdapter(attendance_fragment_adapter);

        mcv =view.findViewById(R.id.calendarView);
        mcv.setOnDateChangedListener(this);
        mcv.setOnMonthChangedListener(this);
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        month = month+1;
        String sDay = null;
        String sMonth = null;

        sDay = ""+day;
        sMonth = ""+month;
        if(day<10){
            sDay = "0"+day;
        }
        if(month<10){
            sMonth = "0"+month;
        }

        Attendance attendance = new Attendance();
        attendance.setUser_id(appSharedPref.getUserInfo());
        attendance.setDay_in(""+sDay);
        attendance.setYear_in(""+year);
        attendance.setMonth_in(""+sMonth);

        presenter.onGetHoliday(""+(month+1),""+year);
        presenter.onAttendance(attendance);


    }

    @Override
    public void onResume() {
        super.onResume();
        mcv.setSelectedDate(CalendarDay.today());


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
   /*     ArrayList<Attendance> attendances =
                Lists.newArrayList(attendanceManager.GetAttendace(""+date.getDay(),""+date.getMonth(),""+date.getYear()));
        attendance_fragment_adapter.SetData(attendances);*/
        Attendance attendance = new Attendance();
        attendance.setUser_id(appSharedPref.getUserInfo());
        int day = date.getDay();
        int month = date.getMonth();
        String sDay = null,sMonth = null;
        month = month + 1;
        sMonth = ""+month;
        sDay = ""+day;
        if(day<10){
            sDay = "0"+day;
        }
        if(month<10){
            sMonth = "0"+month;
        }
        attendance.setDay_in(""+sDay);
        attendance.setYear_in(""+date.getYear());
        attendance.setMonth_in(""+sMonth);

        presenter.onAttendance(attendance);

    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        presenter.onGetHoliday(""+(date.getMonth()+1),""+date.getYear());

    }

    @Override
    public void onHolidaySuccess(ArrayList<Holiday> holidays) {
        tv_noholiday.setVisibility(View.GONE);
        attendance_holiday_adapter.SetData(holidays);
    }

    @Override
    public void onHolidayFailed(String title, String message) {
        if(message.equals("nodata")){
            attendance_holiday_adapter.DeleteData();
            tv_noholiday.setVisibility(View.VISIBLE);
        }else{
            functions.showMessage(title,message);
        }
    }

    @Override
    public void onAttendanceSuccess(ArrayList<Attendance> attendanceArrayList) {
        tv_noattendace.setVisibility(View.GONE);
        attendance_fragment_adapter.SetData(attendanceArrayList);
    }

    @Override
    public void onAttendanceFailed(HashMap<String, String> hashMap, String message) {

        if(message.equals("nodata")){
            attendance_fragment_adapter.DeleteData();
            String month = hashMap.get("month");
            String day = hashMap.get("day");
            String year = hashMap.get("year");
            if(month.substring(0,1).equals("0")){
                month = month.substring(1,2);
            }
            if(day.substring(0,1).equals("0")){
                day = day.substring(1,2);
            }

            String date = year+"-"+month+"-"+day;
            tv_noattendace.setText("No Attendance on "+formatDate(date));
            tv_noattendace.setVisibility(View.VISIBLE);


        }
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

}
