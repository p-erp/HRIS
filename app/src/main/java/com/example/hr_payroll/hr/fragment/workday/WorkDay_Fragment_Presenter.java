package com.example.hr_payroll.hr.fragment.workday;

import android.content.Context;

import com.example.hr_payroll.model.WorkDay;

import java.util.ArrayList;

public class WorkDay_Fragment_Presenter implements WorkDay_FragmentMVP.Presenter {
    private WorkDay_FragmentMVP.Fragment fragment;
    private WorkDay_Fragment_InterActor interActor;
    public  WorkDay_Fragment_Presenter(WorkDay_FragmentMVP.Fragment fragment, Context context){
        this.fragment = fragment;
        interActor = new WorkDay_Fragment_InterActor(context);

    }
    @Override
    public void onAddDay(String day, String start, String end,String year,String user) {
        interActor.AddWorkDay(this,day,start,end,year,user);
    }

    @Override
    public void onAddDaySuccess(String title, String message) {
            fragment.AddDaySuccess(title,message);
    }

    @Override
    public void onAddDayFailed(String title, String message) {
        fragment.AddDayFailed(title,message);

    }

    @Override
    public void onGetWorkDays(String userid) {
        interActor.GetAllWorkDays(this,userid);
    }

    @Override
    public void onGetWorkDayFailed(String title, String message) {

    }

    @Override
    public void onGetWorkDaySuccess(ArrayList<WorkDay> workDays) {
            fragment.WorkDaySuccess(workDays);
    }
}
