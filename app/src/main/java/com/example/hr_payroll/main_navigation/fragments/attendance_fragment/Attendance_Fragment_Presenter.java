package com.example.hr_payroll.main_navigation.fragments.attendance_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hr_payroll.R;
import com.example.hr_payroll.manager.AttendanceManager;
import com.example.hr_payroll.model.Attendance;
import com.example.hr_payroll.model.Holiday;
import com.google.common.collect.Lists;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Attendance_Fragment_Presenter implements Attendance_FragmentMVP.Presenter {
 private Attendance_FragmentMVP.Fragment fragment;
 private Attendance_Fragment_InterActor interActor;

 public Attendance_Fragment_Presenter(Attendance_FragmentMVP.Fragment fragment, Context context){
   this.fragment = fragment;
   interActor = new Attendance_Fragment_InterActor(context);

 }
 @Override
 public void onGetHoliday(String month, String year) {
   interActor.GetHolidays(this,month,year);
 }

 @Override
 public void onGetHolidaySuccess(ArrayList<Holiday> holidays) {
    fragment.onHolidaySuccess(holidays);
 }

 @Override
 public void onGetHolidayFailed(String title, String message) {
    fragment.onHolidayFailed(title,message);
 }

    @Override
    public void onAttendanceListSuccess(ArrayList<Attendance> attendanceArrayList) {
        fragment.onAttendanceSuccess(attendanceArrayList);
    }

    @Override
    public void onAttendanceFailed(HashMap<String, String> hashMap, String message) {
        fragment.onAttendanceFailed(hashMap,message);

    }



    @Override
    public void onAttendance(Attendance attendance) {
        interActor.GetAttendanceInfo(this,attendance.getUser_id(),attendance.getMonth_in(),attendance.getDay_in(),attendance.getYear_in());
    }
}
