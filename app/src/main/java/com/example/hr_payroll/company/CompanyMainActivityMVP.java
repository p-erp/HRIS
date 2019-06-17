package com.example.hr_payroll.company;

public interface CompanyMainActivityMVP {
    interface View{
        void changeFragment(androidx.fragment.app.Fragment fragment);
        void initFragment();
        void initUI();
    }
    interface Presenter{

    }
}
