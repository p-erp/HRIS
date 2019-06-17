package com.example.hr_payroll.main_navigation;

import androidx.fragment.app.Fragment;

public interface MainNavigationMVP {
    interface View{
        void initUI();
        void initFragment();
        void changeFragment(Fragment fragment);
    }
    interface Presenter{

    }
}
