package com.example.hr_payroll.admin.admin_holidays_fragment;

import android.app.Activity;

import com.example.hr_payroll.model.Holiday;

import java.util.ArrayList;
import java.util.HashMap;

public class Admin_Holidays_Fragment_Presenter implements Admin_Holidays_FragmentMVP.Presenter {
    private Admin_Holidays_FragmentMVP.Fragment fragment;
    private Admin_Holidays_Fragment_InterActor interActor;
    public Admin_Holidays_Fragment_Presenter(Admin_Holidays_FragmentMVP.Fragment fragment, Activity activity){
        this.fragment = fragment;
        this.fragment.initUI();
        interActor = new Admin_Holidays_Fragment_InterActor(activity);

    }

    @Override
    public void onSave(HashMap<String, String> hashMap, String name) {
        interActor.SaveDate(this,hashMap,name);
    }

    @Override
    public void onSaveSuccess(String title, String message) {
        fragment.onSaveSuccess(title,message);
    }

    @Override
    public void onSaveFailed(String title, String message) {
        fragment.onSaveFailed(title,message);

    }

    @Override
    public void onGetHoliday() {
        interActor.getAllHoliday(this);
    }

    @Override
    public void onGetHolidaySuccess(ArrayList<Holiday> holidays) {
        fragment.onHolidaySuccess(holidays);
    }

    @Override
    public void onGetHolidayFailed(String title, String message) {
        fragment.onHolidayFailed(title, message);
    }
}
