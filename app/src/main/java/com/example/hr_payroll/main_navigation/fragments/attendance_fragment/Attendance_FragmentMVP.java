package com.example.hr_payroll.main_navigation.fragments.attendance_fragment;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public interface Attendance_FragmentMVP{
    interface Fragment{
        void onHolidaySuccess(ArrayList<Holiday> holidays);
        void onHolidayFailed(String title,String message);

        void onAttendanceSuccess(ArrayList<Attendance> attendanceArrayList);
        void onAttendanceFailed(HashMap<String,String> hashMap,String message);
    }
    interface Presenter{
        void onGetHoliday(String month,String year);
        void onGetHolidaySuccess(ArrayList<Holiday> holidays);
        void onGetHolidayFailed(String title,String message);
        void onAttendanceListSuccess(ArrayList<Attendance> attendanceArrayList);
        void onAttendanceFailed(HashMap<String,String> hashMap, String message);
        void onAttendance(Attendance attendance);

    }
}
