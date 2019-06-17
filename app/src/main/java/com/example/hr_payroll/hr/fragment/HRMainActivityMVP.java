package com.example.hr_payroll.hr.fragment;


import androidx.fragment.app.Fragment;

public interface  HRMainActivityMVP {
    interface View{
        void changeFragment(androidx.fragment.app.Fragment fragment);
        void initFragment();
        void initUI();
    }
    interface Presenter{

    }

}
