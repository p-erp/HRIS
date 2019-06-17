package com.example.hr_payroll.admin.admin_holidays_fragment;

import com.example.hr_payroll.model.Holiday;

import java.util.ArrayList;
import java.util.HashMap;

public interface Admin_Holidays_FragmentMVP {
    interface Fragment{
        void initUI();
        void onSaveSuccess(String title,String message);
        void onSaveFailed(String title,String message);
        void onHolidayFailed(String title,String message);
        void onHolidaySuccess(ArrayList<Holiday> holidays);
    }
    interface Presenter{
        void onSave(HashMap<String,String> hashMap,String name);
        void onSaveSuccess(String title,String message);
        void onSaveFailed(String title,String message);
        void onGetHoliday();
        void onGetHolidaySuccess(ArrayList<Holiday> holidays);
        void onGetHolidayFailed(String title,String message);
    }
}
