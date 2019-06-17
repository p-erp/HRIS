package com.example.hr_payroll.hr.fragment.workday;

import com.example.hr_payroll.model.WorkDay;

import java.util.ArrayList;

public interface WorkDay_FragmentMVP {
    interface Fragment{
        void AddDaySuccess(String title,String message);
        void AddDayFailed(String title,String message);
        void WorkDayFailed(String title,String message);
        void WorkDaySuccess(ArrayList<WorkDay> workDays);

    }
    interface Presenter{
       void onAddDay(String day,String start,String end,String year,String user);
       void onAddDaySuccess(String title,String message);
       void onAddDayFailed(String title,String message);
       void onGetWorkDays(String userid);
       void onGetWorkDayFailed(String title,String message);
       void onGetWorkDaySuccess(ArrayList<WorkDay> workDays);

    }
}
