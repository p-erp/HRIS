package com.example.hr_payroll.main_navigation.fragments.account_fragment;

import com.example.hr_payroll.model.User;

public interface Account_FragmentMVP {
    interface Fragment{
        void initUI();
        void userDetails(User user);
        void errorDetaile(String title,String message);
    }
    interface Presenter{
        void getDetails(String user_id);
        void getDetailSuccess(User user);
        void getDetailFailed(String title,String message);
    }
}
