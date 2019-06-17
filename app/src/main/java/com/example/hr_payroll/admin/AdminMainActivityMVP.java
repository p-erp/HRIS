package com.example.hr_payroll.admin;

import androidx.fragment.app.Fragment;

public interface  AdminMainActivityMVP {
    interface View{
        void initUI();
        void initFragment();
        void changeFragment(Fragment fragment);
    }
    interface  Presenter{

    }
}
